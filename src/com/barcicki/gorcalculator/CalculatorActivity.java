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
import com.barcicki.gorcalculator.core.Tournament;
import com.barcicki.gorcalculator.database.OpponentModel;
import com.barcicki.gorcalculator.database.OpponentModel.GameColor;
import com.barcicki.gorcalculator.database.OpponentModel.GameResult;
import com.barcicki.gorcalculator.database.PlayerModel;
import com.barcicki.gorcalculator.database.TournamentModel;
import com.barcicki.gorcalculator.views.HintDialog;

public class CalculatorActivity extends FragmentActivity {

	PlayerFragment mPlayerFragment;
	TournamentFragment mTournamentFragment;
	OpponentsFragment mOpponentsFragment;

	HintDialog mHintDialog;

	Settings mSettings;
	ScrollView mScroll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);

		getActionBar().setDisplayHomeAsUpEnabled(false);

		mHintDialog = new HintDialog(this);
		mSettings = new Settings(this);
		
		mScroll = ((ScrollView) findViewById(R.id.scroller));

		if (savedInstanceState == null) {

			mPlayerFragment = new PlayerFragment();
			mTournamentFragment = new TournamentFragment();
			mOpponentsFragment = new OpponentsFragment();

			getSupportFragmentManager().beginTransaction()
					.add(R.id.container_player, mPlayerFragment)
					.add(R.id.container_tournament, mTournamentFragment)
					.add(R.id.container_opponents, mOpponentsFragment).commit();

		} else {

			mPlayerFragment = (PlayerFragment) getSupportFragmentManager()
					.findFragmentById(R.id.container_player);
			mTournamentFragment = (TournamentFragment) getSupportFragmentManager()
					.findFragmentById(R.id.container_tournament);
			mOpponentsFragment = (OpponentsFragment) getSupportFragmentManager()
					.findFragmentById(R.id.container_opponents);

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		Tournament.clearObservers();
		
		Tournament.addObserver(mPlayerFragment);
		Tournament.addObserver(mTournamentFragment);
		Tournament.addObserver(mOpponentsFragment);
		
		Tournament.refreshTournament();
		
		mHintDialog.show(Settings.HINT_FIND_PLAYER, getString(R.string.help_find_player));
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
			new PlayersListDownloader(this)
					.download(new PlayersUpdaterListener() {

						@Override
						public void onSaved(String total) {
							Toast.makeText(CalculatorActivity.this,
									getString(R.string.update_completed),
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onDownloaded(String result) {
							// TODO Auto-generated method stub

						}
					});
			return true;
		case R.id.add_tournament:
			TournamentModel.setActive(TournamentModel.createNewTournament(Tournament.getTournament().getPlayer()));
			Tournament.refreshTournament();
			Tournament.notifyObservers();
			
			mHintDialog.show(Settings.HINT_ADD_TOURNAMENT, getString(R.string.help_add_tournaments));
			return true;
		case R.id.manage_tournaments:
			startActivity(new Intent(this, TournamentsListActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onAddNewOpponentClicked(View v) {

		OpponentModel newOpponent = new OpponentModel(
				PlayerModel.getDefaultPlayer(), GameResult.WIN,
				GameColor.BLACK, OpponentModel.NO_HANDICAP);

		Tournament.getTournament().addOpponent(newOpponent);
		Tournament.notifyObservers();

		mScroll.post(new Runnable() {

			@Override
			public void run() {
				mScroll.fullScroll(ScrollView.FOCUS_DOWN);
			}

		});
		
		mHintDialog.show(Settings.HINT_DELETE_OPPONENT, getString(R.string.help_delete_opponent));
	}

}
