package com.barcicki.gorcalculator.views;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.core.Player;

public class GorDialog extends Dialog {
	
	private EditText mGor;
	private Player mPlayer; 
	
	public GorDialog(Context context) {
		super(context);
		setContentView(R.layout.dialog_gor);
		
		setTitle(getContext().getString(R.string.title_gor));
		setCancelable(true);
		
		mGor = (EditText) findViewById(R.id.dialog_gor);
		mGor.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				dismiss();
				return false;
			}
		});
	}
	
	public Player getPlayer() {
		return mPlayer;
	}

	public void setPlayer(Player mPlayer) {
		this.mPlayer = mPlayer;
	}
	
	@Override
	public void show() {
		super.show();
		if (mPlayer != null) {
			mGor.setText(Integer.toString(mPlayer.getGor()));
			      
		}
		mGor.selectAll();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}
	
	@Override
	public void dismiss() {
		String rank = mGor.getText().toString();
		if (rank.length() >= 3 && mPlayer != null) {
			mPlayer.setGor(Integer.parseInt(rank));
			mPlayer.notifyObservers();
		}
		super.dismiss();
	}

}
