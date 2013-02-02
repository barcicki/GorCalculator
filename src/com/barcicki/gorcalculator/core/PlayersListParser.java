package com.barcicki.gorcalculator.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.core.PlayersListDownloader.PlayersUpdaterListener;
import com.barcicki.gorcalculator.database.PlayerModel;

public class PlayersListParser extends AsyncTask<String, Integer, String> {

	Activity mActivity; 
	ProgressDialog mProgressDialog;
	Pattern mPattern = Pattern
			.compile("([0-9]{8}) +([a-zA-Z,`._ -]+) +([A-Z]+) +([-a-zA-Z=?0-9]{1,4}) +([0-9]{1,2}k|[0-9]d|[0-9]p) +(--|[0-9]{1,2}k|[0-9]d|[0-9]p) +([0-9]{3,4})");
	Integer mTotal;
	PlayersUpdaterListener mListener;

	public PlayersListParser(Activity activity, PlayersUpdaterListener listener, Integer total) {
		mTotal = total;
		mActivity = activity;
		mListener = listener;
		mProgressDialog = new ProgressDialog(activity);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		
	}

	@Override
	protected String doInBackground(String... params) {

		Matcher matcher = mPattern.matcher(params[0]);
		Integer total = 0;

		ActiveAndroid.beginTransaction();

		new Delete().from(PlayerModel.class).where("1=1").execute();
		
		while (matcher.find() && !isCancelled()) {
			PlayerModel player = new PlayerModel();
			
			player.pin = Integer.parseInt(matcher.group(1));
			player.name = matcher.group(2);
			player.country = matcher.group(3);
			player.club = matcher.group(4);
			player.grade = Go.stringGradeToInt(matcher.group(5));
			player.gor = Integer.parseInt(matcher.group(7));
			
			player.save();

			if (++total % 50 == 0) {
				publishProgress(total);
			}
		}

		if (!isCancelled()) {
			ActiveAndroid.setTransactionSuccessful();
		} 
		
		ActiveAndroid.endTransaction();

		return total.toString();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		if (mProgressDialog != null) {
			mProgressDialog.setProgress(values[0]);
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (mProgressDialog != null) {
			mProgressDialog.setMessage(mActivity
					.getString(R.string.update_storing));
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.setMax(mTotal);
			mProgressDialog.show();
			mProgressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					Toast.makeText(
							mActivity,
							mActivity
									.getString(R.string.update_storing_cancelled),
							Toast.LENGTH_SHORT).show();
					cancel(true);
				}
			});
		}
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}

		new Settings(mActivity).storeDownloadedPlayerList(true);
		
		if (mListener != null) {
			mListener.onSaved(result);
		}
		
	}

}
