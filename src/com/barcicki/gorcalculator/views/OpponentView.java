package com.barcicki.gorcalculator.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.database.OpponentModel;
import com.barcicki.gorcalculator.database.OpponentModel.GameColor;
import com.barcicki.gorcalculator.database.OpponentModel.GameResult;
import com.barcicki.gorcalculator.database.PlayerModel;
import com.barcicki.gorcalculator.libs.MathUtils;
import com.barcicki.gorcalculator.libs.Utils;
import com.barcicki.gorcalculator.views.PlayerView.PlayerListener;

public class OpponentView extends RelativeLayout {

	private OpponentModel mOpponent;
	
	private PlayerView mPlayerView;
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
	
	private OpponentListener mOpponentListener = null;
	
	public OpponentView(Context context) {
		this(context, null);
	}
	
	public OpponentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater.from(context).inflate(R.layout.opponent_view, this);
	
		// dialogs
		mHandicapDialog = new HandicapDialog(context);
		mHandicapDialog.setTitle(context.getString(R.string.dialog_handicap));
		mHandicapDialog.setCancelable(true);
		
		// buttons		
		mHandicap = (Button) findViewById(R.id.buttonHandicap);
		mWin = (ToggleButton) findViewById(R.id.toggleWin);
		
		// textviews
		mHandicapColor = (TextView) findViewById(R.id.handicapColor);
		mHandicapStones = (TextView) findViewById(R.id.handicapStones);
		mGorChange = (TextView) findViewById(R.id.playerGorChange);
		
		// playerview
		mPlayerView = (PlayerView) findViewById(R.id.playerView);
		
		// user experience
		mFadeAnimation.setDuration(ANIMATION_DURATION);
		mFadeAnimation.addAnimation(new AlphaAnimation(1.0f, 0.0f));
		mFadeAnimation.setFillBefore(true);
		
		attachListeners();
	}
	
	private void attachListeners() {
		
		mWin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				mOpponent.result = mWin.isChecked() ? GameResult.WIN : GameResult.LOSS;
				mOpponent.save();
				
				if (mOpponentListener != null) {
					mOpponentListener.onResultChange(mOpponent.result);
				}
				
				updateAttributes();
			}
		});
		
		mHandicap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mHandicapDialog.show();
			}
		});
		
		mHandicapDialog.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				mOpponent.save();
				
				if (mOpponentListener != null) {
					mOpponentListener.onHandicapChange(mOpponent.handicap);
				}
				
				updateAttributes();
			}
		});
		
		mPlayerView.setPlayerListener(new PlayerListener() {
			
			@Override
			public void onPlayerUpdate(PlayerModel newPlayer) {
				// do nothing as it probably came from this view
			}
			
			@Override
			public void onPlayerGorChange(double newGor) {
				mOpponent.gor = newGor;
				
				if (mOpponentListener != null) {
					mOpponentListener.onPlayerGorChange(newGor);
				}
				
			}

		});
		
	}
	
	public void updateAttributes() {
		
		mPlayerView.updateAttributes();
		
		mHandicapColor.setText( mOpponent.color.equals(GameColor.BLACK) ? getContext().getString(R.string.game_color_black) : getContext().getString(R.string.game_color_white));
		mHandicapStones.setText( Utils.getHandicapString(getContext().getResources(), mOpponent.handicap));
		mWin.setChecked( mOpponent.result.equals(GameResult.WIN));
	}
	
	public void updatePlayer(PlayerModel player) {
		mOpponent.player = player;
		mOpponent.gor = player.gor;
		mOpponent.save();
		
		mPlayerView.setPlayer(player);
		mPlayerView.setShowButtonChange(player.pin > 0);
		mPlayerView.setShowPlayerDetails(player.pin > 0);
	}

	public void setOpponent(OpponentModel newOpponent) {
		mOpponent = newOpponent;
		
		mHandicapDialog.setOpponent(newOpponent);
		mPlayerView.setPlayer(newOpponent.player);
		
		if (mOpponentListener != null) {
			mOpponentListener.onOpponentUpdate(newOpponent);
		}
	}
	
	public OpponentModel getOpponent() {
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
	
	public PlayerView getPlayerView() {
		return mPlayerView;
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
	
	public void setOnFindAndChangeClick(OnClickListener listener) {
		mPlayerView.getFindButton().setOnClickListener(listener);
		mPlayerView.getChangeButton().setOnClickListener(listener);
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
	
	public void setOpponentListener(OpponentListener listener) {
		this.mOpponentListener = listener;
	}
	
	public interface OpponentListener {
		public void onPlayerGorChange(double newGor);
		public void onOpponentUpdate(OpponentModel newOpponent);
		public void onResultChange(GameResult result);
		public void onHandicapChange(int newHandicap);
		public void onColorChange(GameColor newColor);
	}
	
}
