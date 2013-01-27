package com.barcicki.gorcalculator.views;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.core.Player;

public class PlayerView extends RelativeLayout implements Observer {
	public String TAG = "PlayerView";

	private Player mPlayer;
	
	private Button mGorButton;
	private Button mFindButton;
	private Button mChangeButton;

	private TextView mClub;
	private TextView mCountry;
	private TextView mGrade;
	private TextView mName;
	
	private GorDialog mGorDialog;
	
	private boolean mShowPlayerDetails = true;
	private boolean mShowButtonChange = false;
	
	public PlayerView(Context context) {
		this(context, null);
	}
	
	public PlayerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater.from(context).inflate(getLayoutResource(), this);
		
		mGorButton = (Button) findViewById(R.id.playerButtonGor);
		mFindButton = (Button) findViewById(R.id.playerButtonFind);
		mChangeButton  = (Button) findViewById(R.id.playerButtonChange);
		
		mName = (TextView) findViewById(R.id.playerName);
		mCountry = (TextView) findViewById(R.id.playerCountry);
		mClub = (TextView) findViewById(R.id.playerClub);
		mGrade = (TextView) findViewById(R.id.playerGrade);
		
		attachListeners();
		
		mGorDialog = new GorDialog(getContext());
	}
	
	protected int getLayoutResource() {
		return R.layout.player_view;
	}
	
	public Player getPlayer() {
		return mPlayer;
	}

	public void setPlayer(Player player) {
		if (mPlayer != null) {
			mPlayer.deleteObserver(this);
		}
		
		mPlayer = player;
		mPlayer.addObserver(this);
		
		mGorDialog.setPlayer(player);
		
		updateAttributes();
	}
	
	private void attachListeners() {

		mGorButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mGorDialog.show();
			}
		});
	}
	
	public void updateAttributes() {
		
		if (mPlayer != null) {
			mName.setText(mPlayer.getName());
			mClub.setText(mPlayer.getClub());
			mCountry.setText(mPlayer.getCountry());
			mGrade.setText(mPlayer.getGrade());
			mGorButton.setText( Integer.toString(mPlayer.getGor()) );
		} else {
			Log.e(TAG, "No player set");
		}
	}
	
	public void setShowPlayerDetails(boolean value) {
		mShowPlayerDetails = value;
		if (mShowPlayerDetails) {
			mFindButton.setVisibility(View.INVISIBLE);
			mName.setVisibility(View.VISIBLE);
			mClub.setVisibility(View.VISIBLE);
			mCountry.setVisibility(View.VISIBLE);
			mGrade.setVisibility(View.VISIBLE);
			mChangeButton.setVisibility(mShowButtonChange ? View.VISIBLE : View.GONE);
		} else {
			mFindButton.setVisibility(View.VISIBLE);
			mName.setVisibility(View.INVISIBLE);
			mClub.setVisibility(View.GONE);
			mCountry.setVisibility(View.GONE);
			mGrade.setVisibility(View.GONE);
			mChangeButton.setVisibility(View.GONE);
		}
		
	}
	
	public boolean isShowingPlayerDetails() {
		return mShowPlayerDetails;
	}
	
	public Button getFindButton() {
		return mFindButton;
	}
	
	public boolean isShowingButtonChange() {
		return mShowButtonChange;
	}

	public void setShowButtonChange(boolean mShowButtonChange) {
		this.mShowButtonChange = mShowButtonChange;
	}

	public Button getChangeButton() {
		return mChangeButton;
	}
	
	public Button getGorButton() {
		return mGorButton;
	}

	@Override
	public void update(Observable observable, Object data) {
		updateAttributes();
	}
	
}
