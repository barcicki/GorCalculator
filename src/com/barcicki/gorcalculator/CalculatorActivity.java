package com.barcicki.gorcalculator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.barcicki.gorcalculator.core.Opponent;
import com.barcicki.gorcalculator.core.Player;
import com.barcicki.gorcalculator.core.PlayersUpdater;
import com.barcicki.gorcalculator.core.PlayersUpdater.PlayersUpdaterListener;
import com.barcicki.gorcalculator.core.Settings;
import com.barcicki.gorcalculator.core.Tournament;
import com.barcicki.gorcalculator.database.DatabaseHelper;

public class CalculatorActivity extends FragmentActivity {

	PlayerFragment mPlayerFragment;
	TournamentFragment mTournamentFragment;
	OpponentsFragment mOpponentsFragment;
	
	DatabaseHelper mDB;
	Settings mSettings;
	
	Tournament mTournament;
	
	ScrollView mScroll;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);

		getActionBar().setDisplayHomeAsUpEnabled(false);
		
		mDB = new DatabaseHelper(this);
		mSettings = new Settings(this);
		
		mScroll = ((ScrollView) findViewById(R.id.scroller));
		
		int playerPIN = mSettings.getPlayerPIN();
		Player player = null;
		if (playerPIN > 0) {
			player = mDB.getPlayerByPin(playerPIN);
		}
		if (player == null) {
			player = new Player(1600);
		}
		
		mTournament = new Tournament(player, Tournament.CATEGORY_A);
		mTournament.addOpponent(new Opponent(1700, Opponent.WIN, Opponent.BLACK, Opponent.NO_HANDICAP));
		
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
		
		Opponent newOpponent = new Opponent( new Player(mTournament.getPlayer().getGor()) , Opponent.WIN, Opponent.WHITE, Opponent.NO_HANDICAP);
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
