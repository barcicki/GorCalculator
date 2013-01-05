package com.barcicki.gorcalculator.core;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.barcicki.gorcalculator.database.DatabaseHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class PlayersDownloader extends AsyncTask<String, Integer, String>{

	public final static String DEMO_URL = "http://192.168.1.50/listas.htm";
	public final static String EGD_URL = "http://www.europeangodatabase.eu/EGD/EGD_2_0/downloads/alleuro_lp.html";
	
	Activity mActivity;
	ProgressDialog mProgressDialog;
	ArrayList<Player> mPlayers = new ArrayList<Player>();
	Pattern mSimplePattern = Pattern.compile("([0-9]{8})");
	
	public PlayersDownloader(Activity activity, ProgressDialog progressDialog) {
//	public PlayersDownloader(Activity activity, ProgressDialog progressDialog, ArrayList<Player> players) {
		mActivity = activity;
		mProgressDialog = progressDialog;
//		mPlayers = players;
	}
	
	@Override
	protected String doInBackground(String... params) {
				
		String result = "";
		
		try {
			URL url = new URL(params[0]);
			URLConnection connection = url.openConnection();
			connection.connect();
			
			int fileLength = connection.getContentLength();
			
			InputStream input = new BufferedInputStream(url.openStream());
			OutputStream output = new ByteArrayOutputStream();
			
			byte data[] = new byte[1024];
			long total = 0;
			int count;
			while ((count = input.read(data)) != -1) {
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
			mProgressDialog.setMessage("Downloading list");
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
			
			DatabaseHelper dbHelper = new DatabaseHelper(mActivity);
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			
			while (matcher.find()) {
//				Log.d("Player", 
//					"PIN:     " + matcher.group(1) + " \n" +
//					"Name:    " + matcher.group(2) + " \n" +
//					"Country: " + matcher.group(3) + " \n" +
//					"Club:    " + matcher.group(4) + " \n" +
//					"Grade:   " + matcher.group(5) + " \n" +
//					"GOR:     " + matcher.group(7)
//				); 
				
				dbHelper.insertPlayer(db, 
						Integer.parseInt(matcher.group(1)), 
						matcher.group(2), 
						matcher.group(3),
						matcher.group(4),
						matcher.group(5),
						Integer.parseInt(matcher.group(7))
				);
				
				publishProgress(++total);
			}
			
			return total.toString();
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mProgressDialog != null) {
				mProgressDialog.setMessage("Parsing players");
				mProgressDialog.setIndeterminate(false);
				mProgressDialog.setMax(mTotal);
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
			}
			
			Toast.makeText(mActivity, "Done: " + result + " from " + mTotal + " found" , Toast.LENGTH_LONG).show();
		}
		
	}
	
}
