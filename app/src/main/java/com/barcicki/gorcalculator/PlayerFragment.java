package com.barcicki.gorcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barcicki.gorcalculator.core.CommonFragment;
import com.barcicki.gorcalculator.core.Tournament;
import com.barcicki.gorcalculator.database.PlayerModel;
import com.barcicki.gorcalculator.views.PlayerView;
import com.barcicki.gorcalculator.views.PlayerView.PlayerListener;

public class PlayerFragment extends CommonFragment {

	PlayerView mPlayerView;
	TextView mPlayerGorChange;

	private static String TAG = "PlayerFragment";
	
	public PlayerFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_player, container,
				false);
		
		mPlayerView = (PlayerView) rootView.findViewById(R.id.player_details);
		mPlayerGorChange = (TextView) rootView.findViewById(R.id.playerGorChangePreview);
		
		final OnClickListener swapView = new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getActivity(), PlayerListActivity.class);
				startActivityForResult(intent, PlayerListActivity.REQUEST_DEFAULT);
			}
		};
		
		mPlayerView.getFindButton().setOnClickListener(swapView);
		mPlayerView.getChangeButton().setOnClickListener(swapView);
		
		mPlayerView.setPlayerListener(new PlayerListener() {
			@Override
			public void onPlayerGorChange(double newGor) {
				Tournament.getTournament().gor = newGor;
				Tournament.update(false);
			}

			@Override
			public double onGorUpdate() {
				return Tournament.getTournament().gor;
			}
		});
		return rootView;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PlayerListActivity.REQUEST_DEFAULT && resultCode == Activity.RESULT_OK) {
			
			long id = (long) data.getDoubleExtra(PlayerModel.ID, 0L);
			if (id > 0) {
				PlayerModel player = PlayerModel.load(PlayerModel.class, id); 
				
				if (player != null) {
					
					Tournament.getTournament().setPlayer(player);
					Tournament.getTournament().gor = player.gor;
					Tournament.update(false);
					
				} else {
					Log.e(TAG, "Player empty");
				}
				
			} else {
				Log.e(TAG, "ID = 0!");
			}
			
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void update(boolean opponentsChanged) {
		PlayerModel player = Tournament.getTournament().getPlayer();
		
		mPlayerView.setPlayer(player);
		mPlayerView.setShowButtonChange(true);
		mPlayerView.setShowPlayerDetails(player.pin > 0);
		
		double previousGor = Tournament.getTournament().gor;
		double newGor = Tournament.calculateFinalGor();
		int color = newGor >= previousGor ? android.R.color.holo_green_dark : android.R.color.holo_red_dark;
		
		mPlayerGorChange.setText(getString(R.string.title_gor_change, previousGor, newGor));
		mPlayerGorChange.setBackgroundColor(getResources().getColor(color));
	}
	
}
