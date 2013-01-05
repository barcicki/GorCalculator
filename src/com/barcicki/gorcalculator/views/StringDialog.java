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

public class StringDialog extends Dialog {
	
	private EditText mString;
	
	public StringDialog(Context context) {
		super(context);
		setContentView(R.layout.dialog_string);
		
		setTitle("Filter");
		setCancelable(true);
		
		mString = (EditText) findViewById(R.id.dialog_edit);
		mString.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				dismiss();
				return false;
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
