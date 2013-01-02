package com.barcicki.gorcalculator.core;

import java.util.Observable;
import java.util.Observer;

import android.support.v4.app.Fragment;

public class CommonFragment extends Fragment implements Observer {
	
	private Tournament mTournament;
	
	public void setTournament(Tournament tournament) {
		mTournament = tournament;
		mTournament.addObserver(this);
	}
	
	public Tournament getTournament() {
		return mTournament;
	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		
	}
}
