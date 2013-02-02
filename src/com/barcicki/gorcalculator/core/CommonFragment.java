package com.barcicki.gorcalculator.core;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.barcicki.gorcalculator.GorCalculator;
import com.barcicki.gorcalculator.database.DbModel.DbObserver;
import com.barcicki.gorcalculator.database.TournamentModel;

public class CommonFragment extends Fragment implements DbObserver {
	
	private TournamentModel mTournament;
	private GorCalculator mApp;
	private Settings mSettings;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApp = (GorCalculator) getActivity().getApplication();
		mSettings = new Settings(getActivity());
	}
	
	public void setTournament(TournamentModel tournament) {
		mTournament = tournament;
		mTournament.addObserver(this);
	}
	
	public GorCalculator getApp() {
		return mApp;
	}
	
	public TournamentModel getTournament() {
		return mTournament;
	}
	
	public Settings getSettings() {
		return mSettings;
	}

	@Override
	public void update(Object data) {
	}
}
