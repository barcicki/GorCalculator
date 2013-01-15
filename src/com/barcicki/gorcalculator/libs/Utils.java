package com.barcicki.gorcalculator.libs;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.core.Opponent;

import android.content.res.Resources;

public class Utils {

	public static String getHandicapString(Resources res, int count) {
		int resId;
		switch (count) {
			case Opponent.NO_HANDICAP:
				resId = R.string.handicap_none;
				break;
			case Opponent.NO_KOMI:
				resId = R.string.handicap_no_komi;
				break;
			default:
				resId = R.string.handicap_stones;
				break;
		}
		return res.getString(resId, count);
	}
}
