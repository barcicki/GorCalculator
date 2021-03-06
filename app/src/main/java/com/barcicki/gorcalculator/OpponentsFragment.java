package com.barcicki.gorcalculator;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.barcicki.gorcalculator.core.Calculator;
import com.barcicki.gorcalculator.core.CommonFragment;
import com.barcicki.gorcalculator.core.Tournament;
import com.barcicki.gorcalculator.database.OpponentModel;
import com.barcicki.gorcalculator.database.OpponentModel.GameColor;
import com.barcicki.gorcalculator.database.OpponentModel.GameResult;
import com.barcicki.gorcalculator.database.PlayerModel;
import com.barcicki.gorcalculator.database.TournamentModel;
import com.barcicki.gorcalculator.libs.MathUtils;
import com.barcicki.gorcalculator.views.OpponentView;
import com.barcicki.gorcalculator.views.OpponentView.OpponentListener;

public class OpponentsFragment extends CommonFragment {

	private static final String TAG = "OpponentsFragment";
	
	private ViewGroup mContainer;
	private ViewGroup mOpponentsContainer;
	private OpponentsAdapter mOpponentsAdapter;

	private OpponentView mWaitingForPlayer = null;

	public OpponentsFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mContainer = (ViewGroup) inflater.inflate(R.layout.fragment_opponents,
				container, false);
		mOpponentsContainer = (ViewGroup) mContainer
				.findViewById(R.id.opponentsList);
		mOpponentsAdapter = new OpponentsAdapter();

		return mContainer;
	}

	public void updateGorChange() {
		TournamentModel tournament = Tournament.getTournament();
		double  gor = tournament.gor,
				change;

		for (OpponentModel op : mOpponentsAdapter) {

			change = Calculator.calculateRatingChange(tournament.gor, op.gor, op.result, op.color, op.handicap, tournament.tournamentClass);
			gor += MathUtils.round1000(change);

			mOpponentsAdapter.getOpponentView(op).updateGorChange(gor, change);
		}
	}

	@Override
	public void update(boolean opponentsChanged) {
		
		if (opponentsChanged) {
			mOpponentsAdapter.clear();
			mOpponentsAdapter.addAll(Tournament.getTournament().opponents());
		}
		
		updateGorChange();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == PlayerListActivity.REQUEST_DEFAULT
				&& resultCode == Activity.RESULT_OK) {

			long id = (long) data.getDoubleExtra(PlayerModel.ID, 0L);
			if (id > 0) {
				PlayerModel player = PlayerModel.load(PlayerModel.class, id);

				if (mWaitingForPlayer != null && player != null) {

					mWaitingForPlayer.updatePlayer(player);
					mWaitingForPlayer = null;
					
					Tournament.notifyObservers(false);

				} else {
					Log.e(TAG, "Player empty or no view is waiting");
				}

			} else {
				Log.e(TAG, "ID = 0!");
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public class OpponentsAdapter extends ArrayList<OpponentModel> {
		private static final long serialVersionUID = 5987215845409571452L;
		
		private LayoutInflater mInflater = LayoutInflater.from(getActivity());

		@Override
		public void clear() {
			super.clear();
			mOpponentsContainer.removeAllViews();
		}
		

		@Override
		public boolean addAll(Collection<? extends OpponentModel> collection) {
			boolean result = super.addAll(collection);

			for (OpponentModel op : this) {
				addOpponentView(op);
			}
			return result;
		}

		@Override
		public boolean add(OpponentModel object) {
			boolean result = super.add(object);
			addOpponentView(object);
			return result;
		}

		public OpponentView addOpponentView(OpponentModel opponent) {
			final OpponentView ov = (OpponentView) mInflater.inflate(
					R.layout.opponent_item, mContainer, false);

			ov.getPlayerView().setShowButtonChange(true);
			ov.getPlayerView().setShowPlayerDetails(false);

			OnClickListener swapView = new OnClickListener() {

				@Override
				public void onClick(View v) {

					mWaitingForPlayer = ov;

					Intent intent = new Intent(getActivity(),
							PlayerListActivity.class);
					startActivityForResult(intent,
							PlayerListActivity.REQUEST_DEFAULT);

				}
			};
			ov.setOnFindAndChangeClick(swapView);

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
					ov.setVisibility(View.GONE);
					new Handler().post(new Runnable() {
						@Override
						public void run() {
							Tournament.getTournament().removeOpponent(ov.getOpponent());
							Tournament.notifyObservers(false);							
						}
					});
					
				}
			});

			ov.setOpponent(opponent);
			ov.setOpponentListener(new OpponentListener() {

				@Override
				public void onPlayerGorChange(double newGor) {
					ov.getOpponent().gor = newGor;
					ov.getOpponent().save();
					Tournament.notifyObservers(false);
				}

				@Override
				public void onResultChange(GameResult result) {
					Tournament.notifyObservers(false);
				}

				@Override
				public void onHandicapChange(int newHandicap) {
					Tournament.notifyObservers(false);
				}

				@Override
				public void onColorChange(GameColor newColor) {
					Tournament.notifyObservers(false);
				}
			});

			mOpponentsContainer.addView(ov);

			ov.getPlayerView().setShowButtonChange(true);
			ov.getPlayerView().setShowPlayerDetails(opponent.getPlayer().pin > 0);
			ov.updateAttributes();

			return ov;
		}

		public OpponentView getOpponentView(OpponentModel opponent) {
			for (int i = 0; i < mOpponentsContainer.getChildCount(); i++) {
				OpponentView ov = (OpponentView) mOpponentsContainer
						.getChildAt(i);
				if (ov.getOpponent().equals(opponent)) {
					return ov;
				}
			}
			return null;
		}
	}

}
