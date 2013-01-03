package com.barcicki.gorcalculator.views;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.core.Player;

public class PlayerView extends ViewSwitcher implements Observer {
	
	private View mSimple;
	private View mComplex;
	
	private Player mPlayer;
	
	private EditText mSimpleGor;
	private TextView mComplexGor;
	private TextView mClub;
	private TextView mCountry;
	private TextView mGrade;
	private TextView mName;

	public PlayerView(Context context) {
		this(context, null);
	}
	
	public PlayerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater.from(context).inflate(R.layout.player_view, this);
		
		mSimple = findViewById(R.id.player_simple);
		mComplex = findViewById(R.id.player_complex);
		
		updateAssignments();
		attachListeners();
		
	}
	
	public Player getPlayer() {
		return mPlayer;
	}

	public void setPlayer(Player mPlayer) {
		this.mPlayer = mPlayer;
		mPlayer.addObserver(this);
		
		updateAttributes();
	}
	
	private void updateAssignments() {
		
		mSimpleGor = (EditText) mSimple.findViewById(R.id.playerGor);
		mComplexGor = (TextView) mComplex.findViewById(R.id.playerGor);
		mName = (TextView) mComplex.findViewById(R.id.playerName);
		mCountry = (TextView) mComplex.findViewById(R.id.playerCountry);
		mClub = (TextView) mComplex.findViewById(R.id.playerClub);
		mGrade = (TextView) mComplex.findViewById(R.id.playerStrength);
	}
	
	private void attachListeners() {
		mSimpleGor.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() >= 3) {
					mPlayer.setGor( Integer.parseInt( s.toString() ));
					mPlayer.notifyObservers();
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() >= 3) {
					mPlayer.setGor( Integer.parseInt( s.toString() ));
					mPlayer.notifyObservers();
				}
			}
		});
		
	}
	
	public void updateAttributes() {
		
		if (getCurrentView() == mComplex) {
			mName.setText(mPlayer.getName());
			mClub.setText(mPlayer.getClub());
			mCountry.setText(mPlayer.getCountry());
			mGrade.setText(mPlayer.getGrade());
			mComplexGor.setText( Integer.toString(mPlayer.getGor()) );
		} else {
			mSimpleGor.setTextKeepState( Integer.toString(mPlayer.getGor()) );
		}
	}
	
	public void showSimpleView() {
		if (getCurrentView() != mSimple) {
			showNext();
		}
	}
	
	public void showComplexView() {
		if (getCurrentView() != mComplex) {
			showNext();
		}
	}
	
	@Override
	public void showNext() {
		super.showNext();
		updateAttributes();
	}

	@Override
	public void showPrevious() {
		super.showPrevious();
		updateAttributes();
	}

	public Button getFindButton() {
		return (Button) mSimple.findViewById(R.id.buttonFindPlayer);
	}
	
	public Button getChangeButton() {
		return (Button) mComplex.findViewById(R.id.buttonChangePlayer);
	}

	@Override
	public void update(Observable observable, Object data) {
		updateAttributes();
	}

}
