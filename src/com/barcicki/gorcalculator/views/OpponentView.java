package com.barcicki.gorcalculator.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.core.Opponent;
import com.barcicki.gorcalculator.core.Opponent.GameColor;
import com.barcicki.gorcalculator.core.Opponent.GameResult;
import com.barcicki.gorcalculator.core.Player;
import com.barcicki.gorcalculator.libs.MathUtils;
import com.barcicki.gorcalculator.libs.Utils;

public class OpponentView extends PlayerView {

	private Opponent mOpponent;
	
	private Button mHandicap;
	private ToggleButton mWin;
	private TextView mHandicapColor;
	private TextView mHandicapStones;
	private TextView mGorChange;
	
	private HandicapDialog mHandicapDialog;
	
	private final static int ANIMATION_DURATION = 500;
	private final static int ANIMATION_RETURN_TRIGGER = 10;
	private final static int ANIMATION_FADE_TRIGGER = 200;
	
	private int mGestureStartX = 0;
	private boolean mGestureDestroying = false;
	
	private boolean mAnimationsEnabled = true;
	private AnimationSet mFadeAnimation = new AnimationSet(true);
	private TranslateAnimation mReturnAnimation;
	private AnimationListener mAnimationListener;
	
	public OpponentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mHandicapDialog = new HandicapDialog(context);
		mHandicapDialog.setTitle(context.getString(R.string.dialog_handicap));
		mHandicapDialog.setCancelable(true);
//		
		mHandicap = (Button) findViewById(R.id.buttonHandicap);
		mWin = (ToggleButton) findViewById(R.id.toggleWin);
		mHandicapColor = (TextView) findViewById(R.id.handicapColor);
		mHandicapStones = (TextView) findViewById(R.id.handicapStones);
		mGorChange = (TextView) findViewById(R.id.playerGorChange);
		
		mFadeAnimation.setDuration(ANIMATION_DURATION);
		mFadeAnimation.addAnimation(new AlphaAnimation(1.0f, 0.0f));
		mFadeAnimation.setFillBefore(true);
		
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
				mOpponent.setResult(mWin.isChecked() ? GameResult.WIN : GameResult.LOSS);
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
		
		mHandicapColor.setText( mOpponent.getColor().equals(GameColor.BLACK) ? getContext().getString(R.string.game_color_black) : getContext().getString(R.string.game_color_white));
		mHandicapStones.setText( Utils.getHandicapString(getContext().getResources(), mOpponent.getHandicap()));
		mWin.setChecked( mOpponent.getResult().equals(GameResult.WIN));
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
	
	public void updateGorChange(double newGor, double gorChange) {
		String text;
		int color;
		
		gorChange = MathUtils.round1000(gorChange);
		
		if (gorChange > 0) {
			text = "+" + gorChange;
			color = android.R.color.holo_green_dark;
		} else {
			text = "" + gorChange;
			color = android.R.color.holo_red_dark;
		}
		
		mGorChange.setBackgroundColor(getResources().getColor(color));
		mGorChange.setText( getContext().getString(R.string.gor_change, MathUtils.round1000(newGor), text));
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		onTouchEvent(ev);
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if (mAnimationsEnabled) {
		
			final int x = (int) event.getRawX();
			final int y = (int) event.getRawY();
			final int diff = x - mGestureStartX;

			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mGestureStartX = x;
					break;
				case MotionEvent.ACTION_MOVE:
					if (Math.abs(diff) > ANIMATION_RETURN_TRIGGER) {
						mReturnAnimation = new TranslateAnimation(diff, 0, 0, 0);
						mReturnAnimation.setFillEnabled(true);
						mReturnAnimation.setFillBefore(true);
						mReturnAnimation.setDuration(ANIMATION_DURATION);
						startAnimation(mReturnAnimation);
					}
					break;
					
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					if (Math.abs(diff) > ANIMATION_FADE_TRIGGER && !mGestureDestroying) {
						TranslateAnimation slideAway = new TranslateAnimation(diff, diff * 1.5f, 0, 0);
						slideAway.setFillEnabled(true);
						slideAway.setFillBefore(true);
						mFadeAnimation.addAnimation(slideAway);
						startAnimation(mFadeAnimation);
						mGestureDestroying = true;
					} 
					break;
			}
		
		}
		
		return super.onTouchEvent(event);
	}

	public boolean isAnimationsEnabled() {
		return mAnimationsEnabled;
	}

	public void setAnimationsEnabled(boolean mAnimationsEnabled) {
		this.mAnimationsEnabled = mAnimationsEnabled;
	}

	public AnimationListener getAnimationListener() {
		return mAnimationListener;
	}

	public void setAnimationListener(AnimationListener mAnimationListener) {
		this.mAnimationListener = mAnimationListener;
		this.mFadeAnimation.setAnimationListener(mAnimationListener);
	}
	
	
	
}
