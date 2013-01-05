package com.barcicki.gorcalculator.core;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

// ([0-9]+) +([a-zA-Z- ]+) +([a-zA-Z]{3,4}) +([0-9]p|[0-7]d|[0-9]{1,2}k) +([0-9]{3,4})
public class Calculator {

	public static float EPSILON = 0.016f;
	
	public static Map<Float, Integer> CON_MAP = new HashMap<Float, Integer>();
	
	public static float MAX_GOR = 3000f;
	public static float MAX_RANK_GOR = 2700f;
	public static float MIN_GOR = 100f;
	
	private static float RANK_STEP = 100f;
	private static String TAG = "Calculator";
	
	static {
		CON_MAP.put(100f, 116);
		CON_MAP.put(200f, 110);
		CON_MAP.put(300f, 105);
		CON_MAP.put(400f, 100);
		CON_MAP.put(500f, 95);
		CON_MAP.put(600f, 90);
		CON_MAP.put(700f, 85);
		CON_MAP.put(800f, 80);
		CON_MAP.put(900f, 75);
		CON_MAP.put(1000f, 70);
		CON_MAP.put(1100f, 65);
		CON_MAP.put(1200f, 60);
		CON_MAP.put(1300f, 55);
		CON_MAP.put(1400f, 51);
		CON_MAP.put(1500f, 47);
		CON_MAP.put(1600f, 43);
		CON_MAP.put(1700f, 39);
		CON_MAP.put(1800f, 35);
		CON_MAP.put(1900f, 31);
		CON_MAP.put(2000f, 27);
		CON_MAP.put(2100f, 24);
		CON_MAP.put(2200f, 21);
		CON_MAP.put(2300f, 18);
		CON_MAP.put(2400f, 15);
		CON_MAP.put(2500f, 13);
		CON_MAP.put(2600f, 11);
		CON_MAP.put(2700f, 10);
	}
	
	private static float ratingBase(float rating) {
		return (float) Math.floor(rating / 100.0) * 100f;
	}
	
	private static float ratingProgress(float rating) {
		return (rating - ratingBase(rating)) / 100;
	}
	
	static public float calculateRatingChange(float ratingA, float ratingB, float result, float handicap, float modifier) {
		Log.d(TAG, "Diff: " + (formulaCon(ratingA) * (result - formulaSe(ratingA, ratingB, handicap))));
		return (formulaCon(ratingA) * (result - formulaSe(ratingA, ratingB, handicap))) * modifier;
	}
	
	static public float formulaSe(float ratingA, float ratingB, float handicap) {
		float formulaA;
		
		if (handicap > 0) {
			ratingA += 100 * (handicap - 0.5);
			formulaA = formulaA(ratingA);
			
		} else if (handicap < 0) {
			ratingB += 100 * (-handicap - 0.5);
			formulaA = formulaA(ratingB);
			
		} else {
			formulaA = formulaA(ratingB);
		}
		
		Log.d(TAG, "Me: " + ratingA);
		Log.d(TAG, "Him: " + ratingB);
		
		float diff = ratingB - ratingA;
		
		Log.d(TAG, "Se: " + Math.max( 1 / ( (float) Math.exp( diff / formulaA ) + 1 ) - EPSILON / 2, 0));
		return Math.max( 1 / ( (float) Math.exp( diff / formulaA ) + 1 ) - EPSILON / 2, 0);
	}
	
	static public float formulaA(float ratingA, float ratingB) {
		float aA = formulaA(ratingA),
			  aB = formulaA(ratingB),
			  result = Math.min(aA, aB);
				
		Log.d(TAG, "Common a: " + result);
		return result;
	}
	
	static public float formulaA(float rating) {
		Log.d(TAG, "a: " + (- 0.05f * rating + 205));
		return - 0.05f * rating + 205;
	}
	
	static public float formulaCon(float rating) {
		float con;
		
		if (rating <= MIN_GOR) {
			con = 116;
		} else if (rating >= MAX_RANK_GOR) {
			con = 10;
		} else {
			
			float base = ratingBase(rating),
				  baseCon = CON_MAP.get(base),
				  nextCon = CON_MAP.get(Math.min(base + RANK_STEP, MAX_RANK_GOR));
			
			con = baseCon + (nextCon - baseCon) * ratingProgress(rating);
			
		}
		
		Log.d(TAG, "Con: " + con);
		return con;
	}

}
