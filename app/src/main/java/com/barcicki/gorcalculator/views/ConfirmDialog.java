package com.barcicki.gorcalculator.views;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.barcicki.gorcalculator.R;

public class ConfirmDialog extends Dialog {

	private TextView mMessage;
	private ConfirmDialogListener mListener = null;	 
	
	public ConfirmDialog(Context context) {
		super(context, R.style.AppDialog);
		setContentView(R.layout.dialog_confirm);
		setCancelable(false);
		
		mMessage = (TextView) findViewById(R.id.message);
		
		((Button) findViewById(R.id.buttonOk)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onConfirm();
				}
				dismiss();
			}
		});
		
		((Button) findViewById(R.id.buttonNo)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onCancel();
				}
				dismiss();
			}
		});
	}
	
	public ConfirmDialogListener getListener() {
		return mListener;
	}

	public void setListener(ConfirmDialogListener listener) {
		this.mListener = listener;
	}

	public void show(String message) {
		mMessage.setText(message);
		show();
	}
	
	public void show(String message, ConfirmDialogListener listener) {
		mMessage.setText(message);
		mListener = listener;
		show();
	}
	
	public interface ConfirmDialogListener {
		public void onConfirm();
		public void onCancel();
	}
	

}
