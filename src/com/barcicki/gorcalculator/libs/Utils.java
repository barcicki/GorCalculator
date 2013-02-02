package com.barcicki.gorcalculator.libs;

import android.content.res.Resources;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.database.OpponentModel;

public class Utils {

	public static String getHandicapString(Resources res, int count) {
		int resId;
		switch (count) {
			case OpponentModel.NO_HANDICAP:
				resId = R.string.handicap_none;
				break;
			case OpponentModel.NO_KOMI:
				resId = R.string.handicap_no_komi;
				break;
			default:
				resId = R.string.handicap_stones;
				break;
		}
		return res.getString(resId, count);
	}
}
