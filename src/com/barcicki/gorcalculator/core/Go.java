package com.barcicki.gorcalculator.core;

import java.util.ArrayList;

import com.barcicki.gorcalculator.libs.MathUtils;

public final class Go {
	
	private static final int LOWEST_KYU = 20;
	private static final int HIGHEST_KYU = 1;
	private static final int STEP_KYU = -1;
	private static final String LONG_KYU = "kyu";
	private static final String SHORT_KYU = "k";
	
	private static final int LOWEST_DAN = 1;
	private static final int HIGHEST_DAN = 7;
	private static final int STEP_DAN = 1;
	private static final String LONG_DAN = "dan";
	private static final String SHORT_DAN = "d";
	
	private static final int LOWEST_PRO = 1;
	private static final int HIGHEST_PRO = 9;
	private static final int STEP_PRO = 1;
	private static final String LONG_PRO= "pro";
	private static final String SHORT_PRO = "p";

	public static final ArrayList<String> STRENGTHS = new ArrayList<String>();
	
	static {
		
		int rank = LOWEST_KYU;
		while (rank != HIGHEST_KYU) {
			STRENGTHS.add(rank + " " + LONG_KYU);
			rank += STEP_KYU;
		}
		STRENGTHS.add(HIGHEST_KYU + " " + LONG_KYU);
		
		rank = LOWEST_DAN;
		while (rank != HIGHEST_DAN) {
			STRENGTHS.add(rank + " " + LONG_DAN);
			rank += STEP_DAN;
		}
		STRENGTHS.add(HIGHEST_DAN + " " + LONG_DAN);
		
		rank = LOWEST_PRO;
		while (rank != HIGHEST_PRO) {
			STRENGTHS.add(rank + " " + LONG_PRO);
			rank += STEP_PRO;
		}
		STRENGTHS.add(HIGHEST_PRO + " " + LONG_PRO);
		
	}
	
	public static int gorToGradeValue(double gor) {
		return (int) Math.floor(MathUtils.constrain(gor, Calculator.MIN_GOR, Calculator.MAX_GOR) / 100.0) - 1;
	}
	
	public static String gorToGradeString(int gor) {
		return STRENGTHS.get(gorToGradeValue(gor));
	}
	
	public static int stringGradeToInt(String grade) {
		if (grade.contains(SHORT_KYU)) {
			return STRENGTHS.indexOf(grade.replace(SHORT_KYU, " " + LONG_KYU)); 
		} else if (grade.contains(SHORT_DAN)) {
			return STRENGTHS.indexOf(grade.replace(SHORT_DAN, " " + LONG_DAN));
		} else if (grade.contains(SHORT_PRO)) {
			return STRENGTHS.indexOf(grade.replace(SHORT_PRO, " " + LONG_PRO));
		} else return 0;
		
	}
}
