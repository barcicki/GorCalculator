package com.barcicki.gorcalculator.views;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.core.Settings;

public class HintDialog extends Dialog {

	private TextView mMessage;
	private CheckBox mDontShowAgain;
	private Settings mSettings;
	private String mSetting;
	
	public HintDialog(Context context) {
		super(context, R.style.AppDialog);
		setContentView(R.layout.dialog_hint);
		setCancelable(false);
		
		mMessage = (TextView) findViewById(R.id.message);
		mDontShowAgain = (CheckBox) findViewById(R.id.dontShow);
		
		mSettings = new Settings(context);
		
		((Button) findViewById(R.id.buttonOk)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSettings.storeHint(mSetting, mDontShowAgain.isChecked());
				dismiss();
			}
		});
	}
	
	public void show(String setting, String message) {
		mSetting = setting;
		mMessage.setText(message);
		
		if (!mSettings.getHint(mSetting)) {
			show();
		}
	}
	
	

}
