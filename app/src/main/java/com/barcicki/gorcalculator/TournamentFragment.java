package com.barcicki.gorcalculator;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.barcicki.gorcalculator.core.CommonFragment;
import com.barcicki.gorcalculator.core.Tournament;
import com.barcicki.gorcalculator.database.TournamentModel.TournamentClass;

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
		
		linkButton((ToggleButton) rootView.findViewById(R.id.tournamentClassA), TournamentClass.CLASS_A);
		linkButton((ToggleButton) rootView.findViewById(R.id.tournamentClassB), TournamentClass.CLASS_B);
		linkButton((ToggleButton) rootView.findViewById(R.id.tournamentClassC), TournamentClass.CLASS_C);
		linkButton((ToggleButton) rootView.findViewById(R.id.tournamentClassD), TournamentClass.CLASS_D);
		
		mModifier = (TextView) rootView.findViewById(R.id.tournamentModifier);
		
		for (ToggleButton button : mButtons) {
			button.setOnClickListener(this);
		}
		
		return rootView;
	}
	
	private void linkButton(ToggleButton button, TournamentClass tournamentClass) {
		button.setTag(tournamentClass);
		mButtons.add(button);
	}

	@Override
	public void onClick(View v) {
		TournamentClass newTournamentClass = (TournamentClass) v.getTag();
		Tournament.getTournament().tournamentClass = newTournamentClass;
		Tournament.update(false);
	}
	
	public void setChecked(TournamentClass tournamentClass) {
		for (ToggleButton button : mButtons) {
			TournamentClass linkedClass = (TournamentClass) button.getTag();
			if (linkedClass.equals(tournamentClass)) {
				button.setChecked(true);
				mModifier.setText( button.getHint() );
			} else {
				button.setChecked(false);				
			}
		}
	}
	
	@Override
	public void update(boolean opponentsChanged) {
		setChecked(Tournament.getTournament().tournamentClass);
	}
}
