package com.barcicki.gorcalculator.core;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.barcicki.gorcalculator.libs.MathUtils;

public final class Go {

	private static final int LOWEST_KYU = 30;
	private static final int HIGHEST_KYU = 1;
	private static final String LONG_KYU = "kyu";
	private static final String SHORT_KYU = "k";
	
	private static final int LOWEST_DAN = 1;
	private static final int HIGHEST_DAN = 7;
	private static final String LONG_DAN = "dan";
	private static final String SHORT_DAN = "d";
	
	private static final int LOWEST_PRO = 1;
	private static final int HIGHEST_PRO = 9;
	private static final String LONG_PRO= "pro";
	private static final String SHORT_PRO = "p";

	public static final int LOWEST_GRADE = -10; // -10 = 30 kyu, 0 = 20 kyu
	public static final int HIGHEST_GRADE= 35; // 34 = 9 pro

	public static final HashMap<Integer, String> STRENGTHS;

	static {
		STRENGTHS = new HashMap<>();

		int grade = LOWEST_GRADE;

		for (int rank = LOWEST_KYU; rank >= HIGHEST_KYU; rank -= 1) {
			STRENGTHS.put(grade++, rank + " " + LONG_KYU);
		}

		for (int rank = LOWEST_DAN; rank <= HIGHEST_DAN; rank += 1) {
			STRENGTHS.put(grade++, rank + " " + LONG_DAN);
		}

		for (int rank = LOWEST_PRO; rank <= HIGHEST_PRO; rank += 1) {
			STRENGTHS.put(grade++, rank + " " + LONG_PRO);
		}
	}
	
	public static int gorToGradeValue(double gor) {
		return (int) MathUtils.constrain(Math.floor(gor / 100.0), LOWEST_GRADE, HIGHEST_GRADE);
	}
	
	public static String gorToGradeString(int gor) {
		return STRENGTHS.get(gorToGradeValue(gor));
	}
	
	public static int stringGradeToInt(String grade) {
		int gradeNum = Integer.parseInt(grade.replaceAll("[^0-9]", ""));

		if (grade.contains(SHORT_KYU)) {
			return Math.max(20 - gradeNum, LOWEST_GRADE); // 20 kyu = 0, 1 kyu = 19, 30 kyu = -9
		} else if (grade.contains(SHORT_DAN)) {
			return 19 + gradeNum; // 1 dan = 20, 7 dan = 26
		} else if (grade.contains(SHORT_PRO)) {
			return Math.min(26 + gradeNum, HIGHEST_GRADE); // 1 pro = 27, 9p = 35
		} else {
			return LOWEST_GRADE; // 30 kyu
		}
	}
}
