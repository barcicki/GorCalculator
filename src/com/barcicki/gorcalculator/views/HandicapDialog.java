package com.barcicki.gorcalculator.views;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.core.Opponent;
import com.barcicki.gorcalculator.libs.Utils;

public class HandicapDialog extends Dialog {
	
	private ArrayList<ToggleButton> mColors = new ArrayList<ToggleButton>();
	private ArrayList<ToggleButton> mHandicaps = new ArrayList<ToggleButton>();
	private Opponent mOpponent;
	
	public HandicapDialog(Context context) {
		super(context);
		setContentView(R.layout.handicap_dialog);
//		
		mColors.add( (ToggleButton) findViewById(R.id.colorWhite));
		mColors.add( (ToggleButton) findViewById(R.id.colorBlack));
		
		mHandicaps.add( (ToggleButton) findViewById(R.id.handicap0));
		mHandicaps.add( (ToggleButton) findViewById(R.id.handicap1));
		mHandicaps.add( (ToggleButton) findViewById(R.id.handicap2));
		mHandicaps.add( (ToggleButton) findViewById(R.id.handicap3));
		mHandicaps.add( (ToggleButton) findViewById(R.id.handicap4));
		mHandicaps.add( (ToggleButton) findViewById(R.id.handicap5));
		mHandicaps.add( (ToggleButton) findViewById(R.id.handicap6));
		mHandicaps.add( (ToggleButton) findViewById(R.id.handicap7));
		mHandicaps.add( (ToggleButton) findViewById(R.id.handicap8));
		mHandicaps.add( (ToggleButton) findViewById(R.id.handicap9));
//		
		Resources res = getContext().getResources();
		for (int i = 0, l = mHandicaps.size(); i < l; i++) {
			String label = Utils.getHandicapString(res, i);
			mHandicaps.get(i).setText( label );
			mHandicaps.get(i).setTextOn( label );
			mHandicaps.get(i).setTextOff( label );
		}
//		
		attachListeners();
	}
	
	public Opponent getOpponent() {
		return mOpponent;
	}

	public void setOpponent(Opponent mOpponent) {
		this.mOpponent = mOpponent;
	}
	
	public void attachListeners() {
		
		for (ToggleButton b : mColors) {
			b.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mOpponent.setColor( mColors.indexOf((ToggleButton) v));
					dismiss();
				}
			});
		}
		
		for (ToggleButton b : mHandicaps) {
			b.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mOpponent.setHandicap( mHandicaps.indexOf((ToggleButton) v));
					dismiss();
				}
			});
		}
		
	}
	
	public void updateColor() {
		for (ToggleButton b : mColors) {
			b.setChecked( mOpponent.getColor() == mColors.indexOf(b) );
		}
	}
	
	public void updateHandicap() {
		for (ToggleButton b : mHandicaps) {
			b.setChecked(mHandicaps.indexOf(b) == mOpponent.getHandicap());
		}
	}
	
	@Override
	public void show() {
		super.show();
		updateColor();
		updateHandicap();
	}
	
	@Override
	public void dismiss() {
		mOpponent.notifyObservers();
		super.dismiss();
	}
	

}
