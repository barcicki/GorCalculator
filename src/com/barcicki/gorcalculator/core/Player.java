package com.barcicki.gorcalculator.core;

import java.util.ArrayList;
import java.util.Observable;

import com.barcicki.gorcalculator.libs.MathUtils;

import android.text.TextUtils;

public class Player extends Observable {

	public static ArrayList<String> STRENGTHS = new ArrayList<String>();
	public static int MIN_GOR = 100;
	public static int MAX_GOR = 2900;

	private String mName;
	private String mLastName;
	private String mClub;
	private String mCountry;
	private int mPin;
	private String mStrength;
	private int mGor;
	
	public Player(int gor) {
		this.setName("Shindo");
		this.setLastName("Hikaru");
		this.setClub("HnG");
		this.setCountry("JPN");
		this.setPin(1);
		this.setStrength(calculateRanking(gor));
		this.setGor(gor);
	}
	
	public Player(String name, String lastName, String club, String country,
			int pin, String strength, int gor) {
		super();
		this.setName(name);
		this.setLastName(lastName);
		this.setClub(club);
		this.setCountry(country);
		this.setPin(pin);
		this.setStrength(strength);
		this.setGor(gor);
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public String getLastName() {
		return mLastName;
	}

	public void setLastName(String lastName) {
		this.mLastName = lastName;
	}

	public String getFullName() {
		ArrayList<String> fullName = new ArrayList<String>();
		
		if (getName().length() > 0) {
			fullName.add(getName());
		}
		
		if (getLastName().length() > 0) {
			fullName.add(getLastName());
		}
		
		return TextUtils.join(" ", fullName);
	}
	
	public String toString() {
		return TextUtils.expandTemplate("^1 ^2 (^3)", getName(), getLastName(), getStrength())
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
		return STRENGTHS.get((int) Math.floor(MathUtils.constrain(gor, MIN_GOR,
				MAX_GOR) / 100.0) - 1);
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

	public int getPin() {
		return mPin;
	}

	public void setPin(int pin) {
		this.mPin = pin;
		this.setChanged();
	}

	public String getStrength() {
		return mStrength;
	}

	public void setStrength(String strength) {
		this.mStrength = strength;
	}

	public int getGor() {
		return mGor;
	}

	public void setGor(int gor) {
		if (mGor != gor) {
			this.mGor = gor;
			this.setChanged();
		}
	}

}
