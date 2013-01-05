package com.barcicki.gorcalculator;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

import com.barcicki.gorcalculator.core.Opponent;
import com.barcicki.gorcalculator.core.Player;
import com.barcicki.gorcalculator.core.PlayersDownloader;
import com.barcicki.gorcalculator.core.Tournament;
import com.barcicki.gorcalculator.database.DatabaseHelper;

public class CalculatorActivity extends FragmentActivity {

	PlayerFragment mPlayerFragment;
	TournamentFragment mTournamentFragment;
	OpponentsFragment mOpponentsFragment;
	
	Tournament mTournament;
	
	ScrollView mScroll;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mScroll = ((ScrollView) findViewById(R.id.scroller));
		
		if (savedInstanceState == null) {
			
			mTournament = new Tournament(new Player("Artur Barcicki", "POZ", "PL", "10 kyu", 1600), Tournament.CATEGORY_A);
			mTournament.addOpponent(new Opponent(1700, Opponent.WIN, Opponent.BLACK, Opponent.NO_HANDICAP));
			
		} else {
			
			// restore state from saved instance
			mTournament = new Tournament(new Player("Artur Barcicki", "POZ", "PL", "10 kyu", 1483), Tournament.CATEGORY_A);
			mTournament.addOpponent(new Opponent(2580, Opponent.WIN, Opponent.BLACK, Opponent.HANDICAP_9));
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
	
	public void onAddNewOpponentClicked(View v) {
		
		DatabaseHelper db = new DatabaseHelper(this);
		Opponent newOpponent = new Opponent( db.getRandomPlayer() , Opponent.WIN, Opponent.WHITE, Opponent.NO_HANDICAP);
		mOpponentsFragment.addOpponentView(newOpponent);
		mTournament.addOpponent(newOpponent);
		
		mScroll.post(new Runnable() {
			
			@Override
			public void run() {
				mScroll.fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			ProgressDialog mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			new PlayersDownloader(this, mProgressDialog).execute(PlayersDownloader.EGD_URL);
		}
		return super.onOptionsItemSelected(item);
	}
	
}
