package com.barcicki.gorcalculator.views;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.barcicki.gorcalculator.R;

public class TournamentDialog extends Dialog {
	
	private EditText mString;
	private Button mButton;
	
	public TournamentDialog(Context context) {
		super(context);
		setContentView(R.layout.dialog_tournament);
		
		setTitle(context.getString(R.string.title_filter));
		setCancelable(true);
		
		mButton = (Button) findViewById(R.id.buttonSave);
		
		mString = (EditText) findViewById(R.id.dialog_edit);
		mString.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				mButton.performClick();
				return false;
			}
		});
	}
	
	public void setOnButtonClickListener(final android.view.View.OnClickListener listener) {
		mButton.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.onClick(v);
				}
				dismiss();
			}
		});
	}
	
	public String getResult() {
		return mString.getText().toString();
	}

	public void show(String current) {
		super.show();
		mString.setText(current);
		mString.selectAll();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}
	
}
