package com.barcicki.gorcalculator.views;

import java.util.Observable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.core.Opponent;
import com.barcicki.gorcalculator.core.Player;
import com.barcicki.gorcalculator.libs.Utils;

public class OpponentView extends PlayerView {

	private Opponent mOpponent;
	
	private Button mHandicap;
	private ToggleButton mWin;
	private TextView mHandicapColor;
	private TextView mHandicapStones;
	private TextView mGorChange;
	
	private HandicapDialog mHandicapDialog;
	
	private GestureDetector mGestureDetector;
	
	public OpponentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mHandicapDialog = new HandicapDialog(context);
		mHandicapDialog.setTitle(context.getString(R.string.dialog_handicap));
		mHandicapDialog.setCancelable(true);
		
		mHandicap = (Button) findViewById(R.id.buttonHandicap);
		mWin = (ToggleButton) findViewById(R.id.toggleWin);
		mHandicapColor = (TextView) findViewById(R.id.handicapColor);
		mHandicapStones = (TextView) findViewById(R.id.handicapStones);
		mGorChange = (TextView) findViewById(R.id.playerGorChange);
		
		attachListeners();
	}
	
	@Override
	protected int getLayoutResource() {
		return R.layout.opponent_view;
	}
	
	private void attachListeners() {
		
		mWin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mOpponent.setResult(mWin.isChecked() ? Opponent.WIN : Opponent.LOSS);
				mOpponent.notifyObservers();
			}
		});
		
		mHandicap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mHandicapDialog.show();
			}
		});
	}
	
	@Override
	public void updateAttributes() {
		super.updateAttributes();
		
		mHandicapColor.setText( mOpponent.getColor() == Opponent.BLACK ? getContext().getString(R.string.game_color_black) : getContext().getString(R.string.game_color_white));
		mHandicapStones.setText( Utils.getHandicapString(getContext().getResources(), mOpponent.getHandicap()));
		mWin.setChecked( mOpponent.getResult() == Opponent.WIN );
	}
	
	public void updatePlayer(Player player) {
		mOpponent.setPlayer(player);
		setPlayer(player);
	}

	public void setOpponent(Opponent opponent) {
		if (mOpponent != null) {
			mOpponent.deleteObserver(this);
		}
		
		mOpponent = opponent;
		mHandicapDialog.setOpponent(mOpponent);
		
		opponent.addObserver(this);
		super.setPlayer(opponent.getPlayer());
	}
	
	public Opponent getOpponent() {
		return mOpponent;
	}
	
	public void setOnGestureListener(OnGestureListener listener) {
		mGestureDetector = new GestureDetector(getContext(), listener);
	}
	
	public void updateGorChange(float newGor, float gorChange) {
		String text;
		int color;
		
		if (gorChange > 0) {
			text = "+" + gorChange;
			color = android.R.color.holo_green_dark;
		} else {
			text = Float.toString(gorChange);
			color = android.R.color.holo_red_dark;
		}
		
		mGorChange.setBackgroundColor(getResources().getColor(color));
		mGorChange.setText( getContext().getString(R.string.gor_change, Math.round(newGor), text));
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		onTouchEvent(ev);
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mGestureDetector != null) {
			mGestureDetector.onTouchEvent(event);
		}
		return super.onTouchEvent(event);
	}
	
}
