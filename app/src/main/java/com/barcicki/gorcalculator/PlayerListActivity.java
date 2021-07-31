package com.barcicki.gorcalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
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
import android.widget.Toast;

import com.barcicki.gorcalculator.core.Go;
import com.barcicki.gorcalculator.core.PlayersListDownloader;
import com.barcicki.gorcalculator.core.PlayersListDownloader.PlayersUpdaterListener;
import com.barcicki.gorcalculator.core.Settings;
import com.barcicki.gorcalculator.database.DbModel;
import com.barcicki.gorcalculator.database.PlayerModel;
import com.barcicki.gorcalculator.views.ConfirmDialog;
import com.barcicki.gorcalculator.views.ConfirmDialog.ConfirmDialogListener;
import com.barcicki.gorcalculator.views.CountryDialog;
import com.barcicki.gorcalculator.views.GradeDialog;
import com.barcicki.gorcalculator.views.HintDialog;
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
	private Settings mSettings;
	
	private HintDialog mHintDialog;
	
	private int mPage = DbModel.FIRST_PAGE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mDialog = new StringDialog(this);
		mGradeDialog = new GradeDialog(this);
		mCountryDialog = new CountryDialog(this);
		mSettings = new Settings(this);
		mHintDialog = new HintDialog(this);
		
		mFilters = mSettings.getFilters();
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
		
		mPlayerList = (ListView) findViewById(R.id.playerList);
		mPlayerList.setAdapter(mPlayersAdapter);
		
		getNextResults();
		
		
		if (!mSettings.hasDownloadedPlayerList()) {
			
			ConfirmDialog dialog = new ConfirmDialog(this);
			dialog.setListener(new ConfirmDialogListener() {
				@Override
				public void onConfirm() {
					new PlayersListDownloader(PlayerListActivity.this).download(new PlayersUpdaterListener() {
						
						@Override
						public void onSaved(String total) {
							getNextResults();
						}

						@Override
						public void onDownloaded(String result) {
							// TODO Auto-generated method stub
							
						}
					});
				}
				
				@Override
				public void onCancel() {
					finish();
				}
			});
			dialog.show(getString(R.string.update_reason));
		}
		
		
		mPlayerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				
				PlayerView pv = (PlayerView) arg1;
				
				Intent returnIntent = new Intent();
				returnIntent.putExtra(PlayerModel.ID, pv.getPlayer().getId().doubleValue());
				setResult(RESULT_OK, returnIntent);
				finish();
				
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
						&& totalItemCount >= mPage * DbModel.LIMIT) {
					
					getNextResults();
					
				}
			}
		});
	}
	
	public void getNextResults() {
		mPlayersAdapter.addAll( 
				PlayerModel.getPlayers(
						mPage, 
						mFilters.getString(Settings.FILTER_NAME),
						mFilters.getString(Settings.FILTER_CLUB),
						mFilters.getString(Settings.FILTER_COUNTRY),
						mFilters.getInt(Settings.FILTER_GRADE_MIN),
						mFilters.getInt(Settings.FILTER_GRADE_MAX)));
						
		if (mPlayersAdapter.getCount() == 0) {
			Toast.makeText(this, getString(R.string.no_results), Toast.LENGTH_SHORT).show();
		}
		
		mPlayersAdapter.notifyDataSetChanged();
		updateFilters();
		
		mPage += 1;
	}
	
	public void scrollToTop() {
		mPlayerList.post(new Runnable() {
			@Override
			public void run() {
				mPlayerList.smoothScrollToPosition(0);
			}
		});
	}
	
	public void updateFilters() {
		Iterator<Entry<String, Button>> it = mFiltersAssignments.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Button> e = it.next();
			
			String label, filter;
			
			if (e.getKey().equals(Settings.FILTER_GRADE)) {
				int min = mFilters.getInt(Settings.FILTER_GRADE_MIN),
					max = mFilters.getInt(Settings.FILTER_GRADE_MAX);
				
				if (min > Go.LOWEST_GRADE || max < Go.HIGHEST_GRADE) {
					label = Go.STRENGTHS.get(min) + " - " + Go.STRENGTHS.get(max);	
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

				resetList();
				
				mHintDialog.show(Settings.HINT_FILTER, getString(R.string.help_filter));
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
				
				resetList();
				
				mHintDialog.show(Settings.HINT_FILTER, getString(R.string.help_filter));	
			}
		});
		mCountryDialog.show(mFilters.getString(Settings.FILTER_COUNTRY));
	}
	
	public void onFilterRankClicked(View v) {
		mGradeDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				mFilters.putInt(Settings.FILTER_GRADE_MIN, mGradeDialog.getGradeMin());
				mFilters.putInt(Settings.FILTER_GRADE_MAX, mGradeDialog.getGradeMax());
				
				resetList();
				
				mHintDialog.show(Settings.HINT_FILTER, getString(R.string.help_filter));
			}
		});
		mGradeDialog.show(mFilters.getInt(Settings.FILTER_GRADE_MIN), mFilters.getInt(Settings.FILTER_GRADE_MAX));
	}

	public void resetList() {
		mPage = DbModel.FIRST_PAGE;
		mPlayersAdapter.clear();

		scrollToTop();
		getNextResults();
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
			case R.id.update_data:
				new PlayersListDownloader(this)
						.download(new PlayersUpdaterListener() {

							@Override
							public void onSaved(String total) {
								resetList();
								Toast.makeText(
									PlayerListActivity.this,
									getString(R.string.update_completed),
									Toast.LENGTH_SHORT).show();
							}

							@Override
							public void onDownloaded(String result) {
								// TODO Auto-generated method stub

							}
						});
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
	
	public class PlayersAdapter extends ArrayAdapter<PlayerModel> {

		private LayoutInflater mInflater;
		
		public PlayersAdapter(Context context) {
			this(context, new ArrayList<PlayerModel>());
		}
		
		public PlayersAdapter(Context context, ArrayList<PlayerModel> players) {
			this(context, R.layout.player_item, players);
		}
		
		public PlayersAdapter(Context context, int textViewResourceId,
				ArrayList<PlayerModel> objects) {
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
