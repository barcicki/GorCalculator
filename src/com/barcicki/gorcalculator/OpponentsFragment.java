package com.barcicki.gorcalculator;

import java.util.ArrayList;
import java.util.Observable;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;

import com.barcicki.gorcalculator.core.CommonFragment;
import com.barcicki.gorcalculator.core.Opponent;
import com.barcicki.gorcalculator.database.DatabaseHelper;
import com.barcicki.gorcalculator.views.OpponentView;

public class OpponentsFragment extends CommonFragment {

	private ViewGroup mContainer;
	private ArrayList<OpponentView> mOpponents = new ArrayList<OpponentView>();
	
	public OpponentsFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		mContainer = (ViewGroup) inflater.inflate(R.layout.fragment_opponents, container, false);
		
		if (getTournament() != null) {
			
			for (Opponent op : getTournament().getOpponents()) {
				
				final OpponentView opponentView = (OpponentView) inflater.inflate(R.layout.opponent_item, mContainer, false);
				mContainer.addView(opponentView);
				opponentView.setOpponent(op);
				opponentView.setOnGestureListener(new GestureListener(opponentView));
				mOpponents.add(opponentView);
				
				OnClickListener swapView = new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						opponentView.showNext();
					}
				};
				opponentView.getFindButton().setOnClickListener(swapView);
				opponentView.getChangeButton().setOnClickListener(swapView);
				
			}
			
			updateGorChange(getTournament().getStartingGor());
		}
		
		return mContainer;
	}
	
	public void addNewOpponent() {
		
		DatabaseHelper db = new DatabaseHelper(getActivity());
		Opponent newOpponent = new Opponent( db.getRandomPlayer() , Opponent.WIN, Opponent.WHITE, Opponent.NO_HANDICAP);
		final OpponentView opponentView = (OpponentView) getActivity().getLayoutInflater().inflate(R.layout.opponent_item, mContainer, false);
		mContainer.addView(opponentView);
		
		opponentView.setOpponent(newOpponent);
		opponentView.setOnGestureListener(new GestureListener(opponentView));
		
		OnClickListener swapView = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				opponentView.showNext();
			}
		};
		opponentView.getFindButton().setOnClickListener(swapView);
		opponentView.getChangeButton().setOnClickListener(swapView);
		
		mOpponents.add(opponentView);
		
		getTournament().addOpponent(newOpponent);
	}
	
	public void updateGorChange(float gor) {
		for (OpponentView ov : mOpponents) {
			float gorChange = (float) Math.round( getTournament().getGorChange(ov.getOpponent()) * 1000.0) / 1000;
			gor += gorChange;
			ov.updateGorChange(gor, gorChange);
		}
	}
	
	@Override
	public void update(Observable observable, Object data) {
		super.update(observable, data);
		updateGorChange(getTournament().getStartingGor());
	}
	
	private class GestureListener implements OnGestureListener {

		OpponentView mView;
		
		public GestureListener(OpponentView v) {
			mView = v;
		}
		
		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {

			Log.d("Scroll", "scrolled: " + distanceX);
			if (Math.abs(distanceX) > 40) {
				TranslateAnimation anim = new TranslateAnimation(0, (distanceX < 0) ? 500 : -500, 0, 0);
				anim.setDuration(200);
				anim.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						getTournament().removeOpponent(mView.getOpponent());
						mOpponents.remove(mView);
						mView.setVisibility(View.GONE);
					}
				});
				mView.startAnimation(anim);
			}
			
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
}
