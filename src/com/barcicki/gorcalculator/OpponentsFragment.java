package com.barcicki.gorcalculator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.barcicki.gorcalculator.core.CommonFragment;
import com.barcicki.gorcalculator.core.Opponent;
import com.barcicki.gorcalculator.libs.MathUtils;
import com.barcicki.gorcalculator.views.OpponentView;

public class OpponentsFragment extends CommonFragment {

	private ViewGroup mContainer;
	private ViewGroup mOpponentsContainer;
	private OpponentsAdapter mOpponentsAdapter;

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
		mOpponentsContainer = (ViewGroup) mContainer.findViewById(R.id.opponentsList);
		mOpponentsAdapter = new OpponentsAdapter();

		return mContainer;
	}

	@Override
	public void onResume() {
		mOpponentsAdapter.clear();
		mOpponentsAdapter.addAll(getTournament().getOpponents());
		updateGorChange();
		super.onResume();
	}
	
	

//	public void refreshOpponents() {
//		if (getTournament() != null) {
//
//			mOpponentsAdapter.clear();
//			mOpponentsAdapter.addAll(getTournament().getOpponents());
//			mOpponentsAdapter.notifyDataSetChanged();
//
//			updateGorChange(getTournament().getStartingGor());
//
//		}
//	}
//
	public void addOpponent(final Opponent newOpponent) {
		mOpponentsAdapter.add(newOpponent);
		update(null, null);
	}

	public void updateGorChange() {
		double gor = getTournament().getStartingGor(),
			   change;
				
		
		for (Opponent op : mOpponentsAdapter) {
			
			change = op.calculatePlayerGorChange(getTournament());
			gor += MathUtils.round1000(change);
			
			mOpponentsAdapter.getOpponentView(op).updateGorChange(gor, change);
		}
	}

	@Override
	public void update(Observable observable, Object data) {
		super.update(observable, data);
		updateGorChange();
		getSettings().storeOpponents(getTournament().getOpponents());
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PlayerListActivity.REQUEST_DEFAULT
				&& resultCode == Activity.RESULT_OK) {
			int requestId = data.getIntExtra(GorCalculator.REQUEST_PLAYER, 0);

			if (requestId > 0) {
				GorCalculator.PlayerRequest request = getApp()
						.getPlayerRequest(requestId);

				OpponentView ov = (OpponentView) request.playerView;

				Log.d("OpponentsFragment",
						"Received player: " + request.player.getName());

				ov.updatePlayer(request.player);
				ov.setShowButtonChange(true);
				ov.setShowPlayerDetails(true);

			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public class OpponentsAdapter extends ArrayList<Opponent> {
		private LayoutInflater mInflater = LayoutInflater.from(getActivity());
		
		@Override
		public void clear() {
			super.clear();
			mOpponentsContainer.removeAllViews();
		}
	
		@Override
		public boolean addAll(Collection<? extends Opponent> collection) {
			boolean result = super.addAll(collection);
			
			for (Opponent op : this) {
				addOpponentView(op);
			}
			return result;
		}
		
		@Override
		public boolean add(Opponent object) {
			boolean result = super.add(object);
			addOpponentView(object);
			return result;
		}
		
		public OpponentView addOpponentView(Opponent opponent) {
			final OpponentView ov = (OpponentView) mInflater.inflate(R.layout.opponent_item, mContainer, false);
			
			Log.d("Adapter", "Added player view"); 
			
			ov.setShowButtonChange(true);
			ov.setShowPlayerDetails(false);

			OnClickListener swapView = new OnClickListener() {

				@Override
				public void onClick(View v) {

					int requestId = getApp().storePlayerRequest(ov);

					Intent intent = new Intent(getActivity(),
							PlayerListActivity.class);
					intent.putExtra(GorCalculator.REQUEST_PLAYER, requestId);

					startActivityForResult(intent,
							PlayerListActivity.REQUEST_DEFAULT);

				}
			};
			ov.getFindButton().setOnClickListener(swapView);
			ov.getChangeButton().setOnClickListener(swapView);

			ov.setAnimationListener(new AnimationListener() {
				
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
					getTournament().removeOpponent(ov.getOpponent());
					ov.setVisibility(View.GONE);
					update(null, null);
				}
			});
			
			mOpponentsContainer.addView(ov);
			
			ov.setOpponent(opponent);
			ov.setShowButtonChange(true);
			ov.setShowPlayerDetails(opponent.getPlayer().getPin() > 0);
			ov.updateAttributes();
			
			return ov;
		}

		public OpponentView getOpponentView(Opponent opponent) {
			for (int i = 0; i < mOpponentsContainer.getChildCount(); i++) {
				OpponentView ov = (OpponentView) mOpponentsContainer.getChildAt(i);
				if (ov.getOpponent().equals(opponent)) {
					return ov;
				}
			}
			return null;
		}
	}

}
