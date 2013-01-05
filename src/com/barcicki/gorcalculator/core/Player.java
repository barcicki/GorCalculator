package com.barcicki.gorcalculator.core;

import java.util.ArrayList;
import java.util.Observable;

import android.text.TextUtils;

import com.barcicki.gorcalculator.libs.MathUtils;

public class Player extends Observable {

	public static ArrayList<String> STRENGTHS = new ArrayList<String>();

	private int mPin;
	private String mName;
	private String mClub;
	private String mCountry;
	private String mStrength;
	private int mGor;
	
	public Player(Player other) {
		this.setName(other.getName());
		this.setClub(other.getClub());
		this.setCountry(other.getCountry());
		this.setGrade(other.getGrade());
		this.setGor(other.getGor());
	}
	
	public Player(int gor) {
		this.setPin(0);
		this.setName("Shindo Hikaru");
		this.setClub("HnG");
		this.setCountry("JPN");
		this.setGrade(calculateRanking(gor));
		this.setGor(gor);
	}
	
	public Player(int pin, String name, String club, String country,
			String strength, int gor) {
		super();
		this.setPin(pin);
		this.setName(name);
		this.setClub(club);
		this.setCountry(country);
		this.setGrade(strength);
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
		int i;
		for (i = 20; i > 0; i--) {
			STRENGTHS.add(i + " kyu");
		}
		for (i = 1; i <= 9; i++) {
			STRENGTHS.add(i + " dan");
		}
	}

	public static String calculateRanking(int gor) {
		return STRENGTHS.get((int) Math.floor(MathUtils.constrain(gor, (int) Calculator.MIN_GOR, (int) Calculator.MAX_GOR) / 100.0) - 1);
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
		return mStrength;
	}

	public void setGrade(String strength) {
		this.mStrength = strength;
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
