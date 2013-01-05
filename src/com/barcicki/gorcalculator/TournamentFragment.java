package com.barcicki.gorcalculator;

import java.util.ArrayList;
import java.util.Observable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.barcicki.gorcalculator.core.CommonFragment;

public class TournamentFragment extends CommonFragment implements OnClickListener {

	private ArrayList<ToggleButton> mButtons = new ArrayList<ToggleButton>();
	private TextView mModifier;
	
	public TournamentFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tournament, container,
				false);
		
		mButtons.add( (ToggleButton) rootView.findViewById(R.id.tournamentClassA) );
		mButtons.add( (ToggleButton) rootView.findViewById(R.id.tournamentClassB) );
		mButtons.add( (ToggleButton) rootView.findViewById(R.id.tournamentClassC) );
		mModifier = (TextView) rootView.findViewById(R.id.tournamentModifier);
		
		for (ToggleButton button : mButtons) {
			button.setOnClickListener(this);
		}
		
		update(getTournament(), null);
		return rootView;
	}

	@Override
	public void onClick(View v) {
		setChecked( (ToggleButton) v);
		
		getTournament().setCategory( mButtons.indexOf((ToggleButton) v) );
		getTournament().notifyObservers();
	}
	
	public void setChecked(ToggleButton current) {
		for (ToggleButton button : mButtons) {
			button.setChecked(false);
		}
		current.setChecked(true);
		mModifier.setText( current.getHint() );
	}
	
	@Override
	public void update(Observable observable, Object data) {
		super.update(observable, data);
		setChecked(mButtons.get( getTournament().getCategory()));
	}
}
