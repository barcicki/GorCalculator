package com.barcicki.gorcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.barcicki.gorcalculator.core.PlayersListDownloader;
import com.barcicki.gorcalculator.core.PlayersListDownloader.PlayersUpdaterListener;
import com.barcicki.gorcalculator.core.Settings;
import com.barcicki.gorcalculator.database.OpponentModel;
import com.barcicki.gorcalculator.database.OpponentModel.GameColor;
import com.barcicki.gorcalculator.database.OpponentModel.GameResult;
import com.barcicki.gorcalculator.database.PlayerModel;
import com.barcicki.gorcalculator.database.TournamentModel;

public class CalculatorActivity extends FragmentActivity {

	PlayerFragment mPlayerFragment;
	TournamentFragment mTournamentFragment;
	OpponentsFragment mOpponentsFragment;
	
	TournamentModel mTournament;
	
	Settings mSettings;
	ScrollView mScroll;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);

		getActionBar().setDisplayHomeAsUpEnabled(false);
		
		mTournament = TournamentModel.getActiveTournament();
		
		mSettings = new Settings(this);
		mScroll = ((ScrollView) findViewById(R.id.scroller));

		if (savedInstanceState == null) {
		
			mPlayerFragment = new PlayerFragment();
			mTournamentFragment = new TournamentFragment();
			mOpponentsFragment = new OpponentsFragment();
		
			getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.container_player, mPlayerFragment)
				.add(R.id.container_tournament, mTournamentFragment)
				.add(R.id.container_opponents, mOpponentsFragment)
				.commit();
		
		} else {
			
			mPlayerFragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.container_player);
			mTournamentFragment = (TournamentFragment) getSupportFragmentManager().findFragmentById(R.id.container_tournament);
			mOpponentsFragment = (OpponentsFragment) getSupportFragmentManager().findFragmentById(R.id.container_opponents);
			
		}
	}
	
	@Override
	protected void onResume() {
		
		mTournament = TournamentModel.getActiveTournament();
		
		mPlayerFragment.setTournament(mTournament);
		mTournamentFragment.setTournament(mTournament);
		mOpponentsFragment.setTournament(mTournament);
		
		mTournament.notifyObservers(null);
		super.onResume();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.calculator, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		
		switch (item.getItemId()) {
		case R.id.update_data:
			new PlayersListDownloader(this).download(new PlayersUpdaterListener() {
				
				@Override
				public void onSaved(String total) {
					Toast.makeText(CalculatorActivity.this, "Database updated. Saved " + total + " players", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onDownloaded(String result) {
					// TODO Auto-generated method stub
					
				}
			});
			return true;
		case R.id.manage_tournaments:
			startActivity(new Intent(this, TournamentsListActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void onAddNewOpponentClicked(View v) {
		
		OpponentModel newOpponent = new OpponentModel(PlayerModel.getDefaultPlayer(), GameResult.WIN, GameColor.BLACK, OpponentModel.NO_HANDICAP);
//		
		mTournament.addOpponent(newOpponent);
		mTournament.notifyObservers(null);
		
		mOpponentsFragment.addOpponent(newOpponent);
		
		mScroll.post(new Runnable() {
			
			@Override
			public void run() {
				mScroll.fullScroll(ScrollView.FOCUS_DOWN);
			}
			
		});
	}
	
}
