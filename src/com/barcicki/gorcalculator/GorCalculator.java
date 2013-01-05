package com.barcicki.gorcalculator;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;
import android.util.SparseArray;

import com.barcicki.gorcalculator.core.Player;
import com.barcicki.gorcalculator.core.Tournament;
import com.barcicki.gorcalculator.views.PlayerView;

public class GorCalculator extends Application {

	public static final String REQUEST_PLAYER = "requestPlayer";
	
	private SparseArray<PlayerRequest> mRequests = new SparseArray<GorCalculator.PlayerRequest>();
	private ArrayList<Tournament> mTournaments = new ArrayList<Tournament>();
	private int mRequestId = 1;
	
	public Tournament getTournament(int position) { 
		return mTournaments.get(position);
	}
	
	public int storePlayerRequest(PlayerView playerView) {
		int requestId = getNextRequestId();
		mRequests.put(requestId, new PlayerRequest(playerView));
		return requestId;
	}
	
	public boolean respondToPlayerRequest(int requestId, Player player) {
		boolean success = false;
		
		PlayerRequest request = mRequests.get(requestId);
		if (request != null) {
			request.player = player;
			success = true;
		}
		
		return success;
	}
	
	public PlayerRequest getPlayerRequest(int requestId) {
		PlayerRequest request = mRequests.get(requestId);
		if (request != null) {
			mRequests.remove(requestId);
		}
		return request ;
	}
	
	class PlayerRequest {
		public PlayerView playerView;
		public Player player;
		public PlayerRequest(PlayerView pv) {
			playerView = pv;
		}
	}
	
	private int getNextRequestId() {
		return mRequestId++; 
	}
	
}
