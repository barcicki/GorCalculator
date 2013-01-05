package com.barcicki.gorcalculator;

import java.util.Observable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barcicki.gorcalculator.core.CommonFragment;
import com.barcicki.gorcalculator.core.Player;
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
			player.addObserver(this);
		}
		
		mPlayerView.setShowButtonChange(true);
		
		OnClickListener swapView = new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPlayerView.setShowPlayerDetails(!mPlayerView.isShowingPlayerDetails());
			}
		};
		mPlayerView.getFindButton().setOnClickListener(swapView);
		mPlayerView.getChangeButton().setOnClickListener(swapView);
		
		update(getTournament(), null);
				
		return rootView;
	}
	
	@Override
	public void update(Observable observable, Object data) {
		float previousGor = getTournament().getStartingGor(),
			  newGor = getTournament().getFinalGor();
		
		mPlayerGorChange.setText( getString(R.string.title_gor_change, previousGor, newGor));
	}
	
}
