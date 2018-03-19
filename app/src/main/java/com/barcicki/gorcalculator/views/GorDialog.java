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
import com.barcicki.gorcalculator.core.Calculator;
import com.barcicki.gorcalculator.libs.MathUtils;

public class GorDialog extends Dialog {
	
	private EditText mGor;
	private Button mReset;
	private Button mApply;
	private double mResult;
	private double mDefault;

	public GorDialog(Context context) {
		super(context, R.style.AppDialog);
		setContentView(R.layout.dialog_gor);
		
		setTitle(getContext().getString(R.string.title_gor));
		setCancelable(true);
		
		mReset = (Button) findViewById(R.id.buttonResetGor);
		mReset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mGor.setText(Integer.toString( (int) mDefault));
				mResult = mDefault;
				dismiss();
			}
		});

		mApply = (Button) findViewById(R.id.buttonApply);
		mApply.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setNewResult();
				dismiss();
			}
		});

		mGor = (EditText) findViewById(R.id.dialog_gor);
		mGor.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				setNewResult();
				dismiss();
				return false;
			}
		});
	}
	
	public double getResult() {
		return MathUtils.constrain(mResult, Calculator.MIN_GOR, Calculator.MAX_GOR);
	}
	
	public void show(double gor, double resetGor) {
		super.show();

		mResult = gor;
		mDefault = resetGor;

		mReset.setEnabled(gor != resetGor);

		mGor.setText(Integer.toString( (int) mResult));
		mGor.selectAll();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}
	
	private void setNewResult() {
		String gorString = mGor.getText().toString();
		if (gorString.length() >= 3) {
			mResult = Double.parseDouble(gorString);
		}
	};

}
