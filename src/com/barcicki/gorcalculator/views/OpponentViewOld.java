package com.barcicki.gorcalculator.views;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ViewSwitcher;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.core.Opponent;
import com.barcicki.gorcalculator.libs.Utils;

public class OpponentViewOld extends ViewSwitcher implements Observer {
	
	private View mSimple;
	private View mComplex;
	
	private Opponent mOpponent;
	
	private EditText mGor;
	private Button mHandicap;
	private ToggleButton mWin;
	private TextView mHandicapColor;
	private TextView mHandicapStones;
	private TextView mGorChange;
	private HandicapDialog mDialog;
	
	private TextView mComplexGor;
	private TextView mClub;
	private TextView mCountry;
	private TextView mGrade;
	private TextView mName;
	
	private GestureDetector mGestureDetector;
	
	public OpponentViewOld(Context context) {
		this(context, null);
	}
	
	public OpponentViewOld(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater.from(context).inflate(R.layout.opponent_view, this);
		
		mSimple = findViewById(R.id.opponent_simple);
		mComplex = findViewById(R.id.player_complex);
		
		updateAssignments();
		attachListeners();
		
		mDialog = new HandicapDialog(context);
		mDialog.setTitle(context.getString(R.string.dialog_handicap));
		mDialog.setCancelable(true);
		
	}
	
	public Opponent getOpponent() {
		return mOpponent;
	}

	public void setOpponent(Opponent mOpponent) {
		this.mOpponent = mOpponent;
		updateAttributes();
		mDialog.setOpponent(mOpponent);
		mOpponent.addObserver(this);
	}
	
	public void setOnGestureListener(OnGestureListener listener) {
		mGestureDetector = new GestureDetector(getContext(), listener);
	}
	
	private void updateAssignments() {
		
		mGor = (EditText) mSimple.findViewById(R.id.playerGor);
		
		if (getCurrentView() == mSimple) {
			mHandicap = (Button) mSimple.findViewById(R.id.buttonHandicap);
			mWin = (ToggleButton) mSimple.findViewById(R.id.toggleWin);
			mHandicapColor = (TextView) mSimple.findViewById(R.id.handicapColor);
			mHandicapStones = (TextView) mSimple.findViewById(R.id.handicapStones);
			mGorChange = (TextView) mSimple.findViewById(R.id.playerGorChange);
		} else {
			mHandicap = (Button) mComplex.findViewById(R.id.buttonHandicap);
			mWin = (ToggleButton) mComplex.findViewById(R.id.toggleWin);
			mHandicapColor = (TextView) mComplex.findViewById(R.id.handicapColor);
			mHandicapStones = (TextView) mComplex.findViewById(R.id.handicapStones);
			mGorChange = (TextView) mComplex.findViewById(R.id.playerGorChange);
		}
		
		mComplexGor = (TextView) mComplex.findViewById(R.id.playerGor);
		mName = (TextView) mComplex.findViewById(R.id.playerName);
		mCountry = (TextView) mComplex.findViewById(R.id.playerCountry);
		mClub = (TextView) mComplex.findViewById(R.id.playerClub);
		mGrade = (TextView) mComplex.findViewById(R.id.playerStrength);
	}
	
	private void attachListeners() {
		mGor.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				if (s.length() >= 3) {
					mOpponent.setGor( Integer.parseInt( s.toString() ));
					mOpponent.notifyObservers();
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
					mOpponent.setGor( Integer.parseInt( s.toString() ));
					mOpponent.notifyObservers();
				}
			}
		});
		
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
				mDialog.show();
			}
		});
		
	}
	
	public void updateAttributes() {
		
		if (getCurrentView() == mComplex) {
			mName.setText(mOpponent.getName());
			mClub.setText(mOpponent.getClub());
			mCountry.setText(mOpponent.getCountry());
			mGrade.setText(mOpponent.getGrade());
			mComplexGor.setText( Integer.toString(mOpponent.getGor()) );
		} else {
			mGor.setTextKeepState( Integer.toString(mOpponent.getGor()) );
		}
		
		mHandicapColor.setText( mOpponent.getColor() == Opponent.BLACK ? getContext().getString(R.string.game_color_black) : getContext().getString(R.string.game_color_white));
		mHandicapStones.setText( Utils.getHandicapString(getContext().getResources(), mOpponent.getHandicap()));
		mWin.setChecked( mOpponent.getResult() == Opponent.WIN );
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
	public void update(Observable observable, Object data) {
		updateAttributes();
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
		updateAssignments();
		updateAttributes();
	}

	@Override
	public void showPrevious() {
		super.showPrevious();
		updateAssignments();
		updateAttributes();
	}
	
	public Button getFindButton() {
		return (Button) mSimple.findViewById(R.id.buttonFindPlayer);
	}
	
	public Button getChangeButton() {
		return (Button) mComplex.findViewById(R.id.buttonChangePlayer);
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
