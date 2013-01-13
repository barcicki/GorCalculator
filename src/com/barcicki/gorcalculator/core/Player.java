package com.barcicki.gorcalculator.core;

import java.util.ArrayList;
import java.util.Observable;

import android.text.TextUtils;

import com.barcicki.gorcalculator.libs.MathUtils;

public class Player extends Observable {
	
	private static int LOWEST_KYU = 20;
	private static int HIGHEST_KYU = 1;
	private static int STEP_KYU = -1;
	private static String LONG_KYU = "kyu";
	private static String SHORT_KYU = "k";
	
	private static int LOWEST_DAN = 1;
	private static int HIGHEST_DAN = 7;
	private static int STEP_DAN = 1;
	private static String LONG_DAN = "dan";
	private static String SHORT_DAN = "d";
	
	private static int LOWEST_PRO = 1;
	private static int HIGHEST_PRO = 9;
	private static int STEP_PRO = 1;
	private static String LONG_PRO= "pro";
	private static String SHORT_PRO = "p";

	public static ArrayList<String> STRENGTHS = new ArrayList<String>();

	private int mPin;
	private String mName;
	private String mClub;
	private String mCountry;
	private int mGradeValue;
	private int mGor;
	
	public Player(Player other) {
		this.setName(other.getName());
		this.setClub(other.getClub());
		this.setCountry(other.getCountry());
		this.setGradeMapping(other.getGradeValue());
		this.setGor(other.getGor());
	}
	
	public Player(int gor) {
		this.setPin(0);
		this.setName("Shindo Hikaru");
		this.setClub("HnG");
		this.setCountry("JPN");
		this.setGradeMapping(gorToGradeValue(gor));
		this.setGor(gor);
	}
	
	public Player(int pin, String name, String club, String country,
			int gradeMapping, int gor) {
		super();
		this.setPin(pin);
		this.setName(name);
		this.setClub(club);
		this.setCountry(country);
		this.setGradeMapping(gradeMapping);
		this.setGor(gor);
	}
	
	public int getPin() {
		return mPin;
	}

	public void setPin(int mPin) {
		this.mPin = mPin;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public String toString() {
		return TextUtils.expandTemplate("^1 (^3)", getName(), getGrade())
				.toString();
	}

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
	
	public static int gorToGradeValue(int gor) {
		return (int) Math.floor(MathUtils.constrain(gor, (int) Calculator.MIN_GOR, (int) Calculator.MAX_GOR) / 100.0) - 1;
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

	public String getClub() {
		return mClub;
	}

	public void setClub(String club) {
		this.mClub = club;
	}

	public String getCountry() {
		return mCountry;
	}

	public void setCountry(String country) {
		this.mCountry = country;
	}

	public String getGrade() {
		return STRENGTHS.get(this.mGradeValue);
	}
	
	public void setGradeMapping(int gradeMapping) {
		this.mGradeValue = gradeMapping;
	}
	
	public int getGradeValue() {
		return this.mGradeValue;
	}

	public int getGor() {
		return mGor;
	}

	public void setGor(int gor) {
		if (mGor != gor) {
			this.mGor = MathUtils.constrain(gor, (int) Calculator.MIN_GOR, (int) Calculator.MAX_GOR) ;
			this.setChanged();
		}
	}

}
