package com.barcicki.gorcalculator.core;

import java.util.HashMap;
import java.util.Map;

import com.barcicki.gorcalculator.database.OpponentModel;
import com.barcicki.gorcalculator.database.OpponentModel.GameColor;
import com.barcicki.gorcalculator.database.OpponentModel.GameResult;
import com.barcicki.gorcalculator.database.PlayerModel;
import com.barcicki.gorcalculator.database.TournamentModel.TournamentClass;
import com.barcicki.gorcalculator.libs.MathUtils;

public class Calculator {

	public static double EPSILON = 0.016;

	public static Map<Double, Integer> CON_MAP = new HashMap<Double, Integer>();

	public static double MAX_GOR = 3000;
	public static double MAX_RANK_GOR = 2700;
	public static double MIN_GOR = 100;

	private static double RANK_STEP = 100;

	static {
		CON_MAP.put(100.0, 116);
		CON_MAP.put(200.0, 110);
		CON_MAP.put(300.0, 105);
		CON_MAP.put(400.0, 100);
		CON_MAP.put(500.0, 95);
		CON_MAP.put(600.0, 90);
		CON_MAP.put(700.0, 85);
		CON_MAP.put(800.0, 80);
		CON_MAP.put(900.0, 75);
		CON_MAP.put(1000.0, 70);
		CON_MAP.put(1100.0, 65);
		CON_MAP.put(1200.0, 60);
		CON_MAP.put(1300.0, 55);
		CON_MAP.put(1400.0, 51);
		CON_MAP.put(1500.0, 47);
		CON_MAP.put(1600.0, 43);
		CON_MAP.put(1700.0, 39);
		CON_MAP.put(1800.0, 35);
		CON_MAP.put(1900.0, 31);
		CON_MAP.put(2000.0, 27);
		CON_MAP.put(2100.0, 24);
		CON_MAP.put(2200.0, 21);
		CON_MAP.put(2300.0, 18);
		CON_MAP.put(2400.0, 15);
		CON_MAP.put(2500.0, 13);
		CON_MAP.put(2600.0, 11);
		CON_MAP.put(2700.0, 10);
	}

	private static double ratingBase(double rating) {
		return (float) Math.floor(rating / 100.0) * 100f;
	}

	private static double ratingProgress(double rating) {
		return (rating - ratingBase(rating)) / 100;
	}

	static public double calculateRatingChange(double ratingA, double ratingB,
			double result, double handicap, double modifier) {
		return (formulaCon(ratingA) * (result - formulaSe(ratingA, ratingB,
				handicap))) * modifier;
	}

	static public double formulaSe(double ratingA, double ratingB,
			double handicap) {
		double formulaA;

		if (handicap > 0) {
			ratingA += 100 * (handicap - 0.5);
			formulaA = formulaA(ratingA);

		} else if (handicap < 0) {
			ratingB += 100 * (-handicap - 0.5);
			formulaA = formulaA(ratingB);

		} else {
			formulaA = formulaA(ratingA, ratingB);
		}

		double diff = ratingB - ratingA;
		return Math.max(1 / ((float) Math.exp(diff / formulaA) + 1) - EPSILON
				/ 2, 0);
	}

	static public double formulaA(double ratingA, double ratingB) {
		double aA = formulaA(ratingA), aB = formulaA(ratingB), result = Math
				.max(aA, aB);

		return result;
	}

	static public double formulaA(double rating) {
		return MathUtils.constrain(-0.05 * rating + 205, 70, 200);
	}

	static public double formulaCon(double rating) {
		double con;

		if (rating <= MIN_GOR) {
			con = 116;
		} else if (rating >= MAX_RANK_GOR) {
			con = 10;
		} else {

			double base = ratingBase(rating), baseCon = CON_MAP.get(base), nextCon = CON_MAP
					.get(Math.min(base + RANK_STEP, MAX_RANK_GOR));

			con = baseCon + (nextCon - baseCon) * ratingProgress(rating);

		}

		return con;
	}

	public static double calculate(PlayerModel player, OpponentModel opponent,
			TournamentClass tournamentClass) {
		return calculate(player, opponent.player, opponent.handicap,
				opponent.result, opponent.color, tournamentClass);
	}

	//
	public static double calculate(PlayerModel player1, PlayerModel player2,
			int handicap, GameResult result2, GameColor color,
			TournamentClass tournamentClass) {

		double gorA = player1.gor, gorB = player2.gor;

		handicap = color.equals(GameColor.BLACK) ? handicap : -handicap;

		float modifier = tournamentClass.value, result = result2.value;

		return calculateRatingChange(gorA, gorB, result, handicap, modifier);
	}

}
