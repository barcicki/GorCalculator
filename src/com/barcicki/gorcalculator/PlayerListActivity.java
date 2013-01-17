package com.barcicki.gorcalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
import android.view.Menu;
import android.view.MenuInflater;
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
import android.widget.ScrollView;
import android.widget.Toast;

import com.barcicki.gorcalculator.core.CountriesAdapter.Country;
import com.barcicki.gorcalculator.core.Player;
import com.barcicki.gorcalculator.core.PlayersUpdater;
import com.barcicki.gorcalculator.core.PlayersUpdater.PlayersUpdaterListener;
import com.barcicki.gorcalculator.core.Settings;
import com.barcicki.gorcalculator.database.DatabaseHelper;
import com.barcicki.gorcalculator.views.CountryDialog;
import com.barcicki.gorcalculator.views.GradeDialog;
import com.barcicki.gorcalculator.views.PlayerView;
import com.barcicki.gorcalculator.views.StringDialog;

public class PlayerListActivity extends Activity {
	
	public static int REQUEST_DEFAULT = 1;
	
	private ListView mPlayerList;
	private Bundle mFilters;
	private HashMap<String, Button> mFiltersAssignments;
	private HashMap<String, String> mFiltersLabels;
	
	private PlayersAdapter mPlayersAdapter;
	
	private StringDialog mDialog;
	private GradeDialog mGradeDialog;
	private CountryDialog mCountryDialog;
	
	private DatabaseHelper mDB;
	private int mPage = DatabaseHelper.FIRST_PAGE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mDB = new DatabaseHelper(this);
		mDialog = new StringDialog(this);
		mGradeDialog = new GradeDialog(this);
		mCountryDialog = new CountryDialog(this);
		
		mFilters = new Settings(this).getFilters();
		mFiltersAssignments = new HashMap<String, Button>();
		mFiltersAssignments.put(Settings.FILTER_NAME, (Button) findViewById(R.id.buttonFilterName));
		mFiltersAssignments.put(Settings.FILTER_COUNTRY, (Button) findViewById(R.id.buttonFilterCountry));
		mFiltersAssignments.put(Settings.FILTER_CLUB, (Button) findViewById(R.id.buttonFilterClub));
		mFiltersAssignments.put(Settings.FILTER_GRADE, (Button) findViewById(R.id.buttonFilterGrade));
		
		mFiltersLabels = new HashMap<String, String>();
		mFiltersLabels.put(Settings.FILTER_NAME, 	getString(R.string.filter_name));
		mFiltersLabels.put(Settings.FILTER_COUNTRY, getString(R.string.filter_country));
		mFiltersLabels.put(Settings.FILTER_CLUB, 	getString(R.string.filter_club));
		mFiltersLabels.put(Settings.FILTER_GRADE, 	getString(R.string.filter_grade));
		
		mPlayersAdapter =  new PlayersAdapter(this);
		
		getNextResults();
		
		
		if (mPlayersAdapter.getCount() == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getString(R.string.update_prompt));
			builder.setMessage(getString(R.string.update_reason));
			builder.setPositiveButton(getString(android.R.string.yes), new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					new PlayersUpdater(PlayerListActivity.this).download(new PlayersUpdaterListener() {
						
						@Override
						public void onSaved(String total) {
							getNextResults();
						}

						@Override
						public void onDownloaded(String result) {
							// TODO Auto-generated method stub
							
						}
					});
					
					dialog.dismiss();
				}
			});
			builder.setNegativeButton(getString(android.R.string.no), new OnClickListener() {
				
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
		mPlayersAdapter.addAll(
				mDB.getPlayers(
						mPage, 
						mFilters.getString(Settings.FILTER_NAME),
						mFilters.getString(Settings.FILTER_CLUB),
						mFilters.getString(Settings.FILTER_COUNTRY),
						mFilters.getInt(Settings.FILTER_GRADE_MIN),
						mFilters.getInt(Settings.FILTER_GRADE_MAX)));
						
		mPlayersAdapter.notifyDataSetChanged();
		updateFilters();
		
		mPage += 1;
	}
	
	public void updateFilters() {
		Iterator<Entry<String, Button>> it = mFiltersAssignments.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Button> e = it.next();
			
			String label, filter;
			
			if (e.getKey().equals(Settings.FILTER_GRADE)) {
				int min = mFilters.getInt(Settings.FILTER_GRADE_MIN),
					max = mFilters.getInt(Settings.FILTER_GRADE_MAX);
				
				if (min > 0 || max < Player.STRENGTHS.size() - 1) {
					label = Player.STRENGTHS.get(min) + " - " + Player.STRENGTHS.get(max);	
				} else {
					label = mFiltersLabels.get(Settings.FILTER_GRADE); 
				}
				
			} else {
				label = mFiltersLabels.get(e.getKey());
				filter = (String) mFilters.get(e.getKey());
				
				if (!filter.isEmpty()) {
					label += ": " + filter;
				}
			}
			
			e.getValue().setText(label);
		}
	}
	
	public void showFilterStringDialog(final String key) {
		mDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				mFilters.putString(key, mDialog.getResult());
				mPage = DatabaseHelper.FIRST_PAGE;
				mPlayersAdapter.clear();
				
				getNextResults();
			}
		});
		mDialog.show(mFilters.getString(key));
	}
	
	public void onFilterNameClicked(View v) {
		showFilterStringDialog(Settings.FILTER_NAME);
	}
	
	public void onFilterClubClicked(View v) {
		showFilterStringDialog(Settings.FILTER_CLUB);
	}
	
	public void onFilterCountryClicked(View v) {
		mCountryDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				mFilters.putString(Settings.FILTER_COUNTRY, mCountryDialog.getCountry());
				
				mPage = DatabaseHelper.FIRST_PAGE;
				mPlayersAdapter.clear();
				
				getNextResults();
				mPlayerList.post(new Runnable() {
					
					@Override
					public void run() {
						mPlayerList.scrollTo(0, 0);
					}
				});
			}
		});
//		mCountryDialog.show(mFilters.getString(Settings.FILTER_COUNTRY));
		mCountryDialog.show();
	}
	
	public void onFilterRankClicked(View v) {
		mGradeDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				mFilters.putInt(Settings.FILTER_GRADE_MIN, mGradeDialog.getGradeMin());
				mFilters.putInt(Settings.FILTER_GRADE_MAX, mGradeDialog.getGradeMax());
				
				mPage = DatabaseHelper.FIRST_PAGE;
				mPlayersAdapter.clear();
				
				getNextResults();
				mPlayerList.post(new Runnable() {
					
					@Override
					public void run() {
						mPlayerList.scrollTo(0, 0);
					}
				});
			}
		});
		mGradeDialog.show(mFilters.getInt(Settings.FILTER_GRADE_MIN), mFilters.getInt(Settings.FILTER_GRADE_MAX));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpTo(this, new Intent(this, CalculatorActivity.class));
				return true;
			case R.id.save_filter:
				if (new Settings(this).storeFilters(mFilters)) {
					Toast.makeText(this, getString(R.string.filters_saved), Toast.LENGTH_SHORT).show();
				}
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
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
