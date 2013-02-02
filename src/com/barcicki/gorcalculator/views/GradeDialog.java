package com.barcicki.gorcalculator.views;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.core.Go;
import com.barcicki.gorcalculator.views.RangeSeekBar.OnRangeSeekBarChangeListener;

public class GradeDialog extends Dialog {
	
	private TextView mGradeMinLabel;
	private TextView mGradeMaxLabel;
	private int mGradeMin;
	private int mGradeMax;
	private int mTempGradeMin;
	private int mTempGradeMax;
	private RangeSeekBar<Integer> mSeekBar;
	
	public GradeDialog(Context context) {
		super(context);
		setContentView(R.layout.dialog_grade);
		
		setTitle(context.getString(R.string.title_grade));
		setCancelable(true);
		
		mGradeMinLabel = (TextView) findViewById(R.id.gradeMin);
		mGradeMaxLabel = (TextView) findViewById(R.id.gradeMax);
		
		mSeekBar = new RangeSeekBar<Integer>(0, Go.STRENGTHS.size() - 1, context);
		mSeekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

			@Override
			public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
					Integer minValue, Integer maxValue) {
				
				mTempGradeMin = minValue;
				mTempGradeMax = maxValue;
				updateLabels();
			}
			
		});
		
		ViewGroup layout = (ViewGroup) findViewById(R.id.masterLayout);
		layout.addView(mSeekBar, 1);
		
		((Button) findViewById(R.id.buttonApply)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mGradeMin = mTempGradeMin;
				mGradeMax = mTempGradeMax;
				dismiss();
			}
		});
	}
	
	public int getGradeMin() {
		return mGradeMin;
	}
	
	public int getGradeMax() {
		return mGradeMax;
	}

	public void show(int min, int max) {
		super.show();
		mTempGradeMin = mGradeMin = min;
		mTempGradeMax = mGradeMax = max;
		mSeekBar.setSelectedMinValue(min);
		mSeekBar.setSelectedMaxValue(max);
		updateLabels();
	}
	
	private void updateLabels() {
		mGradeMinLabel.setText(Go.STRENGTHS.get(mTempGradeMin));
		mGradeMaxLabel.setText(Go.STRENGTHS.get(mTempGradeMax));
	}
	
}
