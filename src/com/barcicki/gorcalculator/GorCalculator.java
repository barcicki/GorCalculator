package com.barcicki.gorcalculator;

import java.util.ArrayList;

import android.app.Application;

import com.barcicki.gorcalculator.core.Tournament;

public class GorCalculator extends Application {

	private ArrayList<Tournament> mTournaments = new ArrayList<Tournament>();
	
	public Tournament getTournament(int position) {
		return mTournaments.get(position);
	}
	
	
}
