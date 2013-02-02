package com.barcicki.gorcalculator.views;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.database.PlayerModel;

public class GorDialog extends Dialog {
	
	private EditText mGor;
	private double mResult;
	
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
	
	public double getResult() {
		return mResult;
	}
	
	public void show(double gor) {
		super.show();
		mResult = gor;
		mGor.setText(Integer.toString( (int) mResult));
		mGor.selectAll();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}
	
	@Override
	public void dismiss() {
		String gorString = mGor.getText().toString();
		if (gorString.length() >= 3) {
			mResult = Double.parseDouble(gorString);
		}
		super.dismiss();
	}

}
