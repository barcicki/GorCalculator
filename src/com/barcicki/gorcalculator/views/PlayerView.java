package com.barcicki.gorcalculator.views;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ViewSwitcher;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.core.Player;

public class PlayerView extends ViewSwitcher implements Observer {
	
	private View mSimple;
	private View mComplex;
	
	private Player mPlayer;
	
	private EditText mGor;

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
		
		if (getCurrentView() == mSimple) {
			mGor = (EditText) mSimple.findViewById(R.id.playerGor);
		}
	}
	
	private void attachListeners() {
		mGor.addTextChangedListener(new TextWatcher() {
			
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
				mPlayer.setGor( Integer.parseInt( s.toString() ));
				mPlayer.notifyObservers();
			}
		});
		
	}
	
	public void updateAttributes() {
		mGor.setTextKeepState( Integer.toString(mPlayer.getGor()) );
	}

	@Override
	public void update(Observable observable, Object data) {
		updateAttributes();
	}

}
