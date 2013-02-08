package com.barcicki.gorcalculator.views;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ToggleButton;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.database.OpponentModel;
import com.barcicki.gorcalculator.database.OpponentModel.GameColor;

public class HandicapDialog extends Dialog {
	
	private ArrayList<ToggleButton> mHandicaps = new ArrayList<ToggleButton>();
	private ToggleButton mColorWhite;
	private ToggleButton mColorBlack;
	private OpponentModel mOpponent;
	
	public HandicapDialog(Context context) {
		super(context, R.style.AppDialog);
		setContentView(R.layout.dialog_handicap);
//		
		mColorWhite = (ToggleButton) findViewById(R.id.colorWhite);
		mColorWhite.setTag(GameColor.WHITE);
		mColorBlack = (ToggleButton) findViewById(R.id.colorBlack);
		mColorBlack.setTag(GameColor.BLACK);
		
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
			String label = res.getStringArray(R.array.handicaps)[i];
			mHandicaps.get(i).setText( label );
			mHandicaps.get(i).setTextOn( label );
			mHandicaps.get(i).setTextOff( label );
		}
//		
		attachListeners();
	}
	
	public OpponentModel getOpponent() {
		return mOpponent;
	}

	public void setOpponent(OpponentModel mOpponent) {
		this.mOpponent = mOpponent;
	}
	
	public void attachListeners() {
		
		for (ToggleButton b : new ToggleButton[] { mColorWhite, mColorBlack }) {
			b.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mOpponent.color = (GameColor) v.getTag();
					dismiss();
				}
			});
		}
		
		for (ToggleButton b : mHandicaps) {
			b.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mOpponent.handicap = mHandicaps.indexOf((ToggleButton) v);
					dismiss();
				}
			});
		}
		
	}
	
	public void updateColor() {
		for (ToggleButton b : new ToggleButton[] { mColorWhite, mColorBlack }) {
			b.setChecked(mOpponent.color.equals(b.getTag()));
		}
	}
	
	public void updateHandicap() {
		for (ToggleButton b : mHandicaps) {
			b.setChecked(mHandicaps.indexOf(b) == mOpponent.handicap);
		}
	}
	
	@Override
	public void show() {
		super.show();
		updateColor();
		updateHandicap();
	}
	
}
