package com.barcicki.gorcalculator;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.barcicki.gorcalculator.core.Opponent;
import com.barcicki.gorcalculator.core.Opponent.GameColor;
import com.barcicki.gorcalculator.core.Opponent.GameResult;
import com.barcicki.gorcalculator.core.Player;
import com.barcicki.gorcalculator.core.PlayersUpdater;
import com.barcicki.gorcalculator.core.PlayersUpdater.PlayersUpdaterListener;
import com.barcicki.gorcalculator.core.Tournament.TournamentClass;
import com.barcicki.gorcalculator.core.Settings;
import com.barcicki.gorcalculator.core.Tournament;

public class CalculatorActivity extends FragmentActivity {

	PlayerFragment mPlayerFragment;
	TournamentFragment mTournamentFragment;
	OpponentsFragment mOpponentsFragment;
	
	Settings mSettings;
	
	Tournament mTournament;
	
	ScrollView mScroll;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);

		getActionBar().setDisplayHomeAsUpEnabled(false);
		
		mSettings = new Settings(this);
		
		mScroll = ((ScrollView) findViewById(R.id.scroller));
		
		// restore player
		Player player = mSettings.getStoredPlayer();
		if (player == null) {
			player = new Player(1600);
		}
		
		mTournament = new Tournament(player, TournamentClass.CLASS_A);
		
		// restore opponents
		ArrayList<Opponent> opponents = mSettings.getStoredOpponents();
		if (opponents.size() > 0) {
			mTournament.addOpponents(opponents);
		} else {
			
			// default opponent is of the same rank
			mTournament.addOpponent(new Opponent(player.getGor(), GameResult.WIN, GameColor.BLACK, Opponent.NO_HANDICAP));
		}
		
		mPlayerFragment = new PlayerFragment();
		mPlayerFragment.setTournament(mTournament);
					
		mTournamentFragment = new TournamentFragment();
		mTournamentFragment.setTournament(mTournament);
		
		mOpponentsFragment = new OpponentsFragment();
		mOpponentsFragment.setTournament(mTournament);
		
		getSupportFragmentManager()
			.beginTransaction()
			.add(R.id.container_player, mPlayerFragment)
			.add(R.id.container_tournament, mTournamentFragment)
			.add(R.id.container_opponents, mOpponentsFragment)
			.commit();
		
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
			new PlayersUpdater(this).download(new PlayersUpdaterListener() {
				
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
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void onAddNewOpponentClicked(View v) {
		
		Opponent newOpponent = new Opponent( new Player(mTournament.getPlayer().getGor()), GameResult.WIN, GameColor.BLACK, Opponent.NO_HANDICAP);
		mOpponentsFragment.addOpponentView(newOpponent);
		mTournament.addOpponent(newOpponent);
		
		mScroll.post(new Runnable() {
			
			@Override
			public void run() {
				mScroll.fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
	}
	
}
