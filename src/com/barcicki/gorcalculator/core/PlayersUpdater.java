package com.barcicki.gorcalculator.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.database.DatabaseHelper;

public class PlayersUpdater extends AsyncTask<String, Integer, String>{

	public final static String EGD_EUROPE_ZIP_URL = "http://www.europeangodatabase.eu/EGD/EGD_2_0/downloads/alleuro_lp.zip";
	
	public interface PlayersUpdaterListener {
		public void onSaved(String total);
		public void onDownloaded(String result);
	}

	Activity mActivity;
	ProgressDialog mProgressDialog;
	Pattern mSimplePattern = Pattern.compile("([0-9]{8})");
	
	
	private PlayersUpdaterListener mListener;
	
	public PlayersUpdater(Activity activity) {
		mActivity = activity;
		mProgressDialog = new ProgressDialog(activity);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				PlayersUpdater.this.cancel(true);
			}
		});
	}
	
	public PlayersUpdater(Activity activity, ProgressDialog progressDialog) {
		mActivity = activity;
		mProgressDialog = progressDialog;
	}
	
	public void download(PlayersUpdaterListener listener) {
		mListener = listener;
		execute(EGD_EUROPE_ZIP_URL);
	}
	
	@Override
	protected String doInBackground(String... params) {
				
		String result = "";
		
		try {
			URL url = new URL(params[0]);
			URLConnection connection = url.openConnection();
			connection.connect();
			
			ZipInputStream input = new ZipInputStream(url.openStream());
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			
			byte data[] = new byte[1024];
			long total = 0;
			int count;
			
			int fileLength = (int) input.getNextEntry().getSize();
			while ((count = input.read(data)) != -1 && !isCancelled()) {
				total += count;
				
				publishProgress((int) (total * 100 / fileLength));
				output.write(data, 0, count);
			}

			result = output.toString();

			output.close();
			input.close();
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (mProgressDialog != null) {
			mProgressDialog.setMessage(mActivity.getString(R.string.update_downloading));
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.setMax(100);
			mProgressDialog.show();
		}
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		if (mProgressDialog != null) {
			mProgressDialog.setProgress(values[0]);
		}
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
			
			Matcher matcher = mSimplePattern.matcher(result);
			Integer total = 0;
			
			while (matcher.find()) {
				total += 1;
			}
			
			if (mListener != null) {
				mListener.onDownloaded(result);
			}
			
			new PlayersParser(total).execute(result);
		}
	}
	
	private class PlayersParser extends AsyncTask<String, Integer, String> {

		Pattern mPattern = Pattern.compile("([0-9]{8}) +([a-zA-Z,`._ -]+) +([A-Z]+) +([-a-zA-Z=?0-9]{1,4}) +([0-9]{1,2}k|[0-9]d|[0-9]p) +(--|[0-9]{1,2}k|[0-9]d|[0-9]p) +([0-9]{3,4})");
		Integer mTotal;
		
		public PlayersParser(Integer total) {
			mTotal = total;
		}
		
		@Override
		protected String doInBackground(String... params) {
			
			Matcher matcher = mPattern.matcher(params[0]);
			Integer total = 0;
			
			DatabaseHelper helper = new DatabaseHelper(mActivity);
			SQLiteDatabase db = helper.getWritableDatabase();
			db.beginTransaction();
			
			helper.clearPlayers(db);
			while (matcher.find() &&  !PlayersUpdater.this.isCancelled()) {
				helper.insertPlayer(db, 
						Integer.parseInt(matcher.group(1)), 
						matcher.group(2), 
						matcher.group(3),
						matcher.group(4),
						Player.stringGradeToInt(matcher.group(5)),
						Integer.parseInt(matcher.group(7))
				);
				
				if (++total % 50 == 0) {
					publishProgress(total);
				}
			}
			
			if (!this.isCancelled()) {
				db.setTransactionSuccessful();
			} 
			db.endTransaction();
			db.close();
			
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
				mProgressDialog.setMessage(mActivity.getString(R.string.update_storing));
				mProgressDialog.setIndeterminate(false);
				mProgressDialog.setMax(mTotal);
				mProgressDialog.show();
			}
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			if (mProgressDialog != null) {
				mProgressDialog.dismiss();
			}
			
			if (mListener!= null) {
				mListener.onSaved(result);
			}
		}
		
	}
	
}
