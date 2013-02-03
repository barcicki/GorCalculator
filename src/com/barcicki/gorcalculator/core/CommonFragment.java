package com.barcicki.gorcalculator.core;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.barcicki.gorcalculator.core.Tournament.TournamentObserver;

public class CommonFragment extends Fragment implements TournamentObserver {
	
	private Settings mSettings;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSettings = new Settings(getActivity());
	}
	
	@Override
	public void onResume() {
		super.onResume();
		update();
	}
	
	public Settings getSettings() {
		return mSettings;
	}

	@Override
	public void update() {}
}
