package com.barcicki.gorcalculator;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.barcicki.gorcalculator.core.Settings;
import com.barcicki.gorcalculator.database.DbModel;
import com.barcicki.gorcalculator.database.TournamentModel;
import com.barcicki.gorcalculator.views.TournamentDeleteDialog;
import com.barcicki.gorcalculator.views.TournamentDialog;

public class TournamentsListActivity extends Activity {

	public static int REQUEST_DEFAULT = 2;
	
	private ListView mTournamentsList;
	private TournamentsAdapter mTournamentsAdapter;
	
	private TournamentDialog mDialog;
	private TournamentDeleteDialog mDeleteDialog;
	private Settings mSettings;
	
	private int mPage = DbModel.FIRST_PAGE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tournament_list);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mDeleteDialog = new TournamentDeleteDialog(this);
		mDeleteDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				resetResults();
			}
		});
		
		mDialog = new TournamentDialog(this);
		mDialog.setTitle("New tournament");
		mDialog.setOnButtonClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TournamentModel.createNewTournament(mDialog.getResult());
				
				mPage = DbModel.FIRST_PAGE;
				
				resetResults();
			}
		});
		
		mSettings = new Settings(this);
		
		mTournamentsAdapter = new TournamentsAdapter(this);
		
		mTournamentsList = (ListView) findViewById(R.id.tournamentsList);
		mTournamentsList.setAdapter(mTournamentsAdapter);
		
		resetResults();
		
		mTournamentsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				
				TournamentModel tournament = mTournamentsAdapter.getItem(position);
				TournamentModel.setActive(tournament);
				
				finish();
				
			}
			
		});
		mTournamentsList.setOnScrollListener(new OnScrollListener() {

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
		mTournamentsAdapter.addAll(TournamentModel.getAll(mPage));
						
		if (mTournamentsAdapter.getCount() == 0) {
			Toast.makeText(this, getString(R.string.no_results), Toast.LENGTH_SHORT).show();
		}
		
		mTournamentsAdapter.notifyDataSetChanged();
		
		mPage += 1;
	}
	
	public void resetResults() {
		mTournamentsAdapter.clear();
		mPage = DbModel.FIRST_PAGE;
		
		getNextResults();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.tournaments, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpTo(this, new Intent(this, CalculatorActivity.class));
				return true;
			case R.id.add_tournament:
				mDialog.show("Default");
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
	
	public class TournamentsAdapter extends ArrayAdapter<TournamentModel> {

		private LayoutInflater mInflater;
		private Resources mRes;
		
		public TournamentsAdapter(Context context) {
			this(context, new ArrayList<TournamentModel>());
		}
		
		public TournamentsAdapter(Context context, ArrayList<TournamentModel> players) {
			this(context, R.layout.tournament_view, players);
		}
		
		public TournamentsAdapter(Context context, int textViewResourceId,
				ArrayList<TournamentModel> objects) {
			super(context, textViewResourceId, objects);
			mInflater = LayoutInflater.from(context);
			mRes = context.getResources();
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			final TournamentModel tournament = getItem(position);
			
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.tournament_view, parent, false);
			}
			
			convertView.setBackgroundColor(mRes.getColor(tournament.active ? android.R.color.holo_orange_light : android.R.color.background_light));
			
			((TextView) convertView.findViewById(R.id.playerName)).setText(tournament.player.name);
			((TextView) convertView.findViewById(R.id.tournamentName)).setText(tournament.name);
			((TextView) convertView.findViewById(R.id.tournamentClass)).setText(getResources().getStringArray(R.array.tournament_class)[tournament.tournamentClass.ordinal()]);
			((TextView) convertView.findViewById(R.id.opponentsCount)).setText(getString(R.string.tournament_opponents_count, tournament.opponents().size()));
			
			((Button) convertView.findViewById(R.id.buttonDeleteTournament)).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					mDeleteDialog.setTournament(tournament);
					mDeleteDialog.show();
				}
			});
			
			return convertView;
		}

	}
}
