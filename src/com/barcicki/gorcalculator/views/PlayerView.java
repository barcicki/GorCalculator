package com.barcicki.gorcalculator.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.database.PlayerModel;

public class PlayerView extends RelativeLayout {
	public String TAG = "PlayerView";

	private PlayerModel mPlayer;
	
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
	
	private PlayerListener mPlayerListener = null;
	
	public PlayerView(Context context) {
		this(context, null);
	}
	
	public PlayerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater.from(context).inflate(R.layout.player_view, this);
		
		// buttons
		mGorButton = (Button) findViewById(R.id.playerButtonGor);
		mFindButton = (Button) findViewById(R.id.playerButtonFind);
		mChangeButton  = (Button) findViewById(R.id.playerButtonChange);
		
		// textviews
		mName = (TextView) findViewById(R.id.playerName);
		mCountry = (TextView) findViewById(R.id.playerCountry);
		mClub = (TextView) findViewById(R.id.playerClub);
		mGrade = (TextView) findViewById(R.id.playerGrade);
		
		// dialogs
		mGorDialog = new GorDialog(getContext());
		
		attachListeners();
	}
	
	public PlayerModel getPlayer() {
		return mPlayer;
	}

	public void setPlayer(PlayerModel newPlayer) {
		mPlayer = newPlayer;
		updateAttributes();
	}
	
	private void attachListeners() {

		mGorButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mGorDialog.show(getGor());
			}
		});
		
		mGorDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				
				if (mPlayerListener != null) {
					mPlayerListener.onPlayerGorChange(mGorDialog.getResult());
				}
				
				updateAttributes();
				
			}
		});
	}
	
	public void updateAttributes() {
		
		if (mPlayer != null) {
			mName.setText(mPlayer.name);
			mClub.setText(mPlayer.club);
			mCountry.setText(mPlayer.country);
			mGrade.setText(mPlayer.getGrade());
			mGorButton.setText( Integer.toString( (int) getGor() ));
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

	public void setPlayerListener(PlayerListener listener) {
		this.mPlayerListener = listener;
	}
	
	private double getGor() {
		if (mPlayerListener != null) {
			return mPlayerListener.onGorUpdate();
		} else {
			return mPlayer.gor;
		}
	}
	
	public interface PlayerListener {
		public void onPlayerGorChange(double newGor);
		public double onGorUpdate();
	}
	
}
