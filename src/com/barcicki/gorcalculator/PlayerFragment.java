package com.barcicki.gorcalculator;

import java.util.Observable;

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
import com.barcicki.gorcalculator.core.Player;
import com.barcicki.gorcalculator.views.OpponentView;
import com.barcicki.gorcalculator.views.PlayerView;

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
		
		if (getTournament() != null) {
			Player player = getTournament().getPlayer();
			mPlayerView.setPlayer(player);
		}
		
		mPlayerView.setShowPlayerDetails(false);
		mPlayerView.setShowButtonChange(true);
		
		OnClickListener swapView = new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				int requestId = getApp().storePlayerRequest(mPlayerView);
				
				Intent intent = new Intent(getActivity(), PlayerListActivity.class);
				intent.putExtra(GorCalculator.REQUEST_PLAYER, requestId);
				
				startActivityForResult(intent, PlayerListActivity.REQUEST_DEFAULT);
			}
		};
		
		mPlayerView.getFindButton().setOnClickListener(swapView);
		mPlayerView.getChangeButton().setOnClickListener(swapView);
		
		update(getTournament(), null);
				
		return rootView;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PlayerListActivity.REQUEST_DEFAULT && resultCode == Activity.RESULT_OK) {
			int requestId = data.getIntExtra(GorCalculator.REQUEST_PLAYER, 0);
			
			if (requestId > 0) {
				GorCalculator.PlayerRequest request = getApp().getPlayerRequest(requestId);
				
				Log.d("PlayerFragment", "Received player: " + request.player.getName());

				mPlayerView.setPlayer(request.player);
				mPlayerView.setShowButtonChange(true);
				mPlayerView.setShowPlayerDetails(true);
				getTournament().setPlayer(request.player);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void update(Observable observable, Object data) {
		
		float previousGor = getTournament().getStartingGor(),
			  newGor = getTournament().getFinalGor();
		
		mPlayerGorChange.setText( getString(R.string.title_gor_change, previousGor, newGor));
	}
	
}
