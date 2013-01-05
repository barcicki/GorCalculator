package com.barcicki.gorcalculator;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.barcicki.gorcalculator.core.Player;
import com.barcicki.gorcalculator.core.PlayersDownloader;
import com.barcicki.gorcalculator.database.DatabaseHelper;
import com.barcicki.gorcalculator.views.PlayerView;
import com.barcicki.gorcalculator.views.StringDialog;

public class PlayerListActivity extends Activity {
	
	public static int REQUEST_DEFAULT = 1;
	
//	public static String FILTER_NAME = "name";
//	public static String FILTER_CLUB = "club";
//	public static String FILTER_COUNTRY = "country";
	
	private ListView mPlayerList;
	private String mFilterName = null;
	private String mFilterClub = null;
	private String mFilterCountry = null;
	private String mFilterGrade = null;
	private PlayersAdapter mPlayersAdapter;
	
	private StringDialog mDialog;
	
	private DatabaseHelper mDB;
	private int mPage = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mDB = new DatabaseHelper(this);
		mDialog = new StringDialog(this);
		mPlayersAdapter =  new PlayersAdapter(this);
		
		getNextResults();
		
		
		if (mPlayersAdapter.getCount() == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Download EGD list?");
			builder.setMessage("You have no records in your database. Do you want to download player list from EGD?");
			builder.setPositiveButton("YES", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mDB.clearPlayers();
					
					new PlayersDownloader(PlayerListActivity.this).download(new Runnable() {
						
						@Override
						public void run() {
							getNextResults();
						}
					});
					
					dialog.dismiss();
				}
			});
			builder.setNegativeButton("NO", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					finish();
				}
			});
			
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		
		mPlayerList = (ListView) findViewById(R.id.playerList);
//		mFilterName = (Button) findViewById(R.id.playerName);
//		mFilterCountry = (Button) findViewById(R.id.playerCountry);
//		mFilterClub = (Button) findViewById(R.id.playerClub);
//		mFilterRank = (Button) findViewById(R.id.playerRank);
		
		mPlayerList.setAdapter(mPlayersAdapter);
		mPlayerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				
				PlayerView pv = (PlayerView) arg1;
				int requestId = getIntent().getIntExtra(GorCalculator.REQUEST_PLAYER, 0);

				if (requestId > 0) {

					((GorCalculator) getApplication()).respondToPlayerRequest(requestId, pv.getPlayer());
					
					Intent returnIntent = new Intent();
					returnIntent.putExtra(GorCalculator.REQUEST_PLAYER, requestId);
					setResult(RESULT_OK, returnIntent);
					finish();
					
					Log.d("ListActivity", "Player selected: " + pv.getPlayer().getName());
				
				} else {
					Toast.makeText(PlayerListActivity.this, "not ok ", Toast.LENGTH_SHORT).show();
				}
				
			}
			
		});
		
		mPlayerList.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
				if (firstVisibleItem + visibleItemCount >= totalItemCount
						&& totalItemCount >= mPage * DatabaseHelper.LIMIT) {
					
					getNextResults();
					
				}
			}
		});
		
	}
	
	public void getNextResults() {
		mPlayersAdapter.addAll(mDB.getPlayers(mPage, mFilterName, mFilterClub, mFilterCountry, mFilterGrade));
		mPlayersAdapter.notifyDataSetChanged();
		updateFilters();
	}
	
	public void updateFilters() {
		((Button) findViewById(R.id.buttonFilterName)).setText( (mFilterName != null && !mFilterName.isEmpty()) ? "Name: " + mFilterName : "Name");
		((Button) findViewById(R.id.buttonFilterClub)).setText( (mFilterClub != null && !mFilterClub.isEmpty()) ? "Club: " + mFilterClub : "Club");
		((Button) findViewById(R.id.buttonFilterCountry)).setText( (mFilterCountry != null && !mFilterCountry.isEmpty()) ? "Country: " + mFilterCountry : "Country");
		((Button) findViewById(R.id.buttonFilterGrade)).setText( (mFilterGrade != null && !mFilterGrade.isEmpty()) ? "Grade: " + mFilterGrade : "Grade");
	}
	
	public void onFilterNameClicked(View v) {
		
		mDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				mFilterName = mDialog.getResult();
				mPage = 1;
				mPlayersAdapter.clear();
				
				getNextResults();
			}
		});
		mDialog.show(mFilterName);
	}
	
	public void onFilterClubClicked(View v) {
		
		mDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				mFilterClub = mDialog.getResult();
				mPage = 1;
				mPlayersAdapter.clear();
				
				getNextResults();
			}
		});
		mDialog.show(mFilterClub);
	}
	
	public void onFilterCountryClicked(View v) {
		
		mDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				mFilterCountry = mDialog.getResult();
				mPage = 1;
				mPlayersAdapter.clear();
				
				getNextResults();
			}
		});
		mDialog.show(mFilterCountry);
	}
	
	public void onFilterRankClicked(View v) {
		
		mDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				mFilterGrade = mDialog.getResult();
				mPage = 1;
				mPlayersAdapter.clear();
				
				getNextResults();
			}
		});
		mDialog.show(mFilterGrade);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			NavUtils.navigateUpTo(this, new Intent(this, CalculatorActivity.class));
		}
		return super.onOptionsItemSelected(item);
	}
	
	public class PlayersAdapter extends ArrayAdapter<Player> {

		private LayoutInflater mInflater;
		
		public PlayersAdapter(Context context) {
			this(context, new ArrayList<Player>());
		}
		
		public PlayersAdapter(Context context, ArrayList<Player> players) {
			this(context, R.layout.player_item, players);
		}
		
		public PlayersAdapter(Context context, int textViewResourceId,
				ArrayList<Player> objects) {
			super(context, textViewResourceId, objects);

			mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			PlayerView pv;
			
			if (convertView == null) {
				pv = (PlayerView) mInflater.inflate(R.layout.player_item, parent, false);
				
				pv.setPlayer(this.getItem(position));
				pv.setShowButtonChange(false);
				pv.setShowPlayerDetails(true);
				pv.getGorButton().setEnabled(false);
				pv.getGorButton().setFocusable(false);
				
				return pv;
			
			} else {
				
				((PlayerView) convertView).setPlayer(this.getItem(position));
				
			}
			
			return convertView;
		}

	}
	
}
