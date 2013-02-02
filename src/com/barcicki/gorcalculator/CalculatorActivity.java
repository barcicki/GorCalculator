package com.barcicki.gorcalculator;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.barcicki.gorcalculator.core.Opponent;
import com.barcicki.gorcalculator.core.Opponent.GameColor;
import com.barcicki.gorcalculator.core.Opponent.GameResult;
import com.barcicki.gorcalculator.core.Player;
import com.barcicki.gorcalculator.core.PlayersListDownloader;
import com.barcicki.gorcalculator.core.PlayersListDownloader.PlayersUpdaterListener;
import com.barcicki.gorcalculator.core.Settings;
import com.barcicki.gorcalculator.core.Tournament;
import com.barcicki.gorcalculator.database.PlayerModel;
import com.barcicki.gorcalculator.database.TournamentModel;
import com.barcicki.gorcalculator.database.TournamentModel.TournamentClass;

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

//		PlayerModel p = new PlayerModel();
//		p.name = "Awesome";
//		p.save();
//		
////		new Delete().from(TournamentModel.class).where("1 = 1").execute();
//		
//		TournamentModel t = new TournamentModel();
//		t.name = "Default";
//		t.tournamentClass = TournamentClass.CLASS_A;
//		t.player = p;
//		
//		t.save();
//		
//		List<TournamentModel> ts = TournamentModel.getTournaments();
//		
//		Log.d("Test", t.name + " " + t.tournamentClass.toString());
////		Log.d("test 2", ts.get(0).name + " " + ts.get(0).tournamentClass);
		
		
		
		
		
		mScroll = ((ScrollView) findViewById(R.id.scroller));
		
		// restore player
		Player player = mSettings.getStoredPlayer();
		if (player == null) {
			player = new Player(1600);
		}
		
		mTournament = new Tournament(player, mSettings.getTournamentClass());
		
		// restore opponents
		ArrayList<Opponent> opponents = mSettings.getStoredOpponents();
		if (opponents.size() > 0) {
			mTournament.addOpponents(opponents);
		} else {
			
			// default opponent is of the same rank
			mTournament.addOpponent(new Opponent(player.getGor(), GameResult.WIN, GameColor.BLACK, Opponent.NO_HANDICAP));
		}
		
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
		mPlayerFragment.setTournament(mTournament);
		mTournamentFragment.setTournament(mTournament);
		mOpponentsFragment.setTournament(mTournament);
		mTournament.update(null, null);
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
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void onAddNewOpponentClicked(View v) {
		
		Opponent newOpponent = new Opponent( new Player(mTournament.getPlayer().getGor()), GameResult.WIN, GameColor.BLACK, Opponent.NO_HANDICAP);
		
		mTournament.addOpponent(newOpponent);
		mOpponentsFragment.addOpponent(newOpponent);
		
		mScroll.post(new Runnable() {
			
			@Override
			public void run() {
				mScroll.fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
	}
	
}
