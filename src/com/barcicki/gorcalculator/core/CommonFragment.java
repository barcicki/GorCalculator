package com.barcicki.gorcalculator.core;

import java.util.Observable;
import java.util.Observer;

import com.barcicki.gorcalculator.GorCalculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class CommonFragment extends Fragment implements Observer {
	
	private Tournament mTournament;
	private GorCalculator mApp;
	private Settings mSettings;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApp = (GorCalculator) getActivity().getApplication();
		mSettings = new Settings(getActivity());
	}
	
	public void setTournament(Tournament tournament) {
		mTournament = tournament;
		mTournament.addObserver(this);
	}
	
	public GorCalculator getApp() {
		return mApp;
	}
	
	public Tournament getTournament() {
		return mTournament;
	}
	
	public Settings getSettings() {
		return mSettings;
	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		
	}
}
