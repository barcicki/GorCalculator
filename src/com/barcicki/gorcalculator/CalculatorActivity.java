package com.barcicki.gorcalculator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ScrollView;

import com.barcicki.gorcalculator.core.Opponent;
import com.barcicki.gorcalculator.core.Player;
import com.barcicki.gorcalculator.core.Tournament;

public class CalculatorActivity extends FragmentActivity {

	PlayerFragment mPlayer;
	TournamentFragment mTournament;
	OpponentsFragment mOpponents;
	
	ScrollView mScroll;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);

		getActionBar().setDisplayHomeAsUpEnabled(false);
		
		mScroll = ((ScrollView) findViewById(R.id.scroller));
		
		Tournament tournament;

		if (savedInstanceState == null) {
			
			tournament = new Tournament(new Player("Artur Barcicki", "POZ", "PL", "10 kyu", 1600), Tournament.CATEGORY_A);
			tournament.addOpponent(new Opponent(1700, Opponent.WIN, Opponent.BLACK, Opponent.NO_HANDICAP));
			
		} else {
			
			// restore state from saved instance
			tournament = new Tournament(new Player("Artur Barcicki", "POZ", "PL", "10 kyu", 1483), Tournament.CATEGORY_A);
			tournament.addOpponent(new Opponent(2580, Opponent.WIN, Opponent.BLACK, Opponent.HANDICAP_9));
		}
		
		mPlayer = new PlayerFragment();
		mPlayer.setTournament(tournament);
					
		mTournament = new TournamentFragment();
		mTournament.setTournament(tournament);
		
		mOpponents = new OpponentsFragment();
		mOpponents.setTournament(tournament);
		
		getSupportFragmentManager()
			.beginTransaction()
			.add(R.id.container_player, mPlayer)
			.add(R.id.container_tournament, mTournament)
			.add(R.id.container_opponents, mOpponents)
			.commit();
		
	}
	
	public void onAddNewOpponentClicked(View v) {
		mOpponents.addNewOpponent();
		mScroll.post(new Runnable() {
			
			@Override
			public void run() {
				mScroll.fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
	}
}
