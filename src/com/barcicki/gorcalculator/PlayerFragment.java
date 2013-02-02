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
import com.barcicki.gorcalculator.database.PlayerModel;
import com.barcicki.gorcalculator.libs.MathUtils;
import com.barcicki.gorcalculator.views.PlayerView;
import com.barcicki.gorcalculator.views.PlayerView.PlayerListener;

public class PlayerFragment extends CommonFragment {

	PlayerView mPlayerView;
	TextView mPlayerGorChange;

	private static String TAG = "Player";
	
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
			public void onPlayerUpdate(PlayerModel player) {
				getTournament().gor = player.gor;
				getTournament().save();
				getTournament().notifyObservers(null);
			}

			@Override
			public void onPlayerGorChange(double newGor) {
				getTournament().gor = newGor;
				getTournament().save();
				getTournament().notifyObservers(null);
			}
		});
		return rootView;
	}
	
	@Override
	public void onResume() {
		
		if (getTournament() != null) {
		
			PlayerModel player = getTournament().player;
			
//			Player player = getTournament().getPlayer();
			mPlayerView.setPlayer(player);
			mPlayerView.setShowButtonChange(true);
			mPlayerView.setShowPlayerDetails(player.pin > 0);
			
		}
		
		super.onResume();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PlayerListActivity.REQUEST_DEFAULT && resultCode == Activity.RESULT_OK) {
			
			long id = (long) data.getDoubleExtra(PlayerModel.ID, 0L);
			if (id > 0) {
				PlayerModel player = PlayerModel.load(PlayerModel.class, id); 
				
				if (player != null) {
					
					mPlayerView.setPlayer(player);
					mPlayerView.setShowButtonChange(true);
					mPlayerView.setShowPlayerDetails(player.pin > 0);
					
					getTournament().player = player;
					getTournament().gor = player.gor;
					getTournament().save();
					getTournament().notifyObservers(null);
					
					Log.d("PlayerFragment", "Receiver player: " + player.name);
				} else {
					Log.e("PlayerFragment", "Player empty");
				}
				
			} else {
				Log.e("PlayerFragment", "ID = 0!");
			}
			
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void update(Object data) {
		
		double 	previousGor = getTournament().gor,
				newGor = getTournament().calculateFinalGor();
		
		mPlayerGorChange.setText( getString(R.string.title_gor_change, previousGor, MathUtils.round1000(newGor)));
	}
	
}
