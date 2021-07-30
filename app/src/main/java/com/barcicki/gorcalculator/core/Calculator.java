package com.barcicki.gorcalculator.core;

import java.util.HashMap;
import java.util.Map;

import com.barcicki.gorcalculator.database.OpponentModel;
import com.barcicki.gorcalculator.database.OpponentModel.GameColor;
import com.barcicki.gorcalculator.database.OpponentModel.GameResult;
import com.barcicki.gorcalculator.database.PlayerModel;
import com.barcicki.gorcalculator.database.TournamentModel.TournamentClass;
import com.barcicki.gorcalculator.libs.MathUtils;

// https://www.europeangodatabase.eu/EGD/EGF_rating_system.php
public class Calculator {
	public static double MAX_GOR = 3300; // way above 9p
	public static double MIN_GOR = -900; // 30 kyu
	public static double BONUS_GOR = 2300; // 3 dan, arbitrary

	private static double ratingBase(double rating) {
		return (float) Math.floor(rating / 100.0) * 100f;
	}

	private static double ratingProgress(double rating) {
		return (rating - ratingBase(rating)) / 100;
	}

	static public double calculateRatingChange(
		double ratingA,
		double ratingB,
		double result,
		double handicap,
		double modifier
	) {
		double con = formulaCon(ratingA);
		double bonus = formulaBonus(ratingA);
		double se = formulaSe(ratingA, ratingB, handicap);

		// since 04.2021
		return ((con * (result - se)) + bonus) * modifier;
	}

	static public double formulaSe(double ratingA, double ratingB, double handicap) {

		// apply handicap on ratings - not described on official page
		if (handicap > 0) {
			ratingA += (100 * (handicap - 0.5));
		} else if (handicap < 0){
			ratingB += 100 * (-handicap - 0.5);
		}

		return 1 / (1 + Math.exp(formulaBeta(ratingB) - formulaBeta(ratingA)));
	}

	static public double formulaBeta(double rating) {
		// apply constrain to avoid NaN when unreal handicap is used (e.g. 9p plays with 9p on 9 stones of handicap)
		return -7 * Math.log(MAX_GOR - MathUtils.constrain(rating, MIN_GOR, MAX_GOR));
	}

	static public double formulaCon(double rating) {
		return Math.pow((MAX_GOR - rating) / 200, 1.6);
	}

	static public double formulaBonus(double rating) {
		return Math.log(1 + Math.exp((BONUS_GOR - rating) / 80)) / 5;
	}

	public static double calculate(PlayerModel player, OpponentModel opponent,
			TournamentClass tournamentClass) {
		
		return calculateRatingChange(
			player.gor, 
			opponent.gor, 
			opponent.result.value, 
			opponent.color.equals(GameColor.BLACK) ? opponent.handicap : -opponent.handicap,
			tournamentClass.value
		);
	}
	
	public static double calculate(PlayerModel player, PlayerModel opponent, int handicap, GameResult gameResult, GameColor gameColor,
			TournamentClass tournamentClass) {
		
		return calculateRatingChange(
			player.gor, 
			opponent.gor,
			gameResult.value, 
			gameColor.equals(GameColor.BLACK) ? handicap : -handicap,
			tournamentClass.value
		);
	}

	public static double calculateRatingChange(double ratingA, double ratingB,
			GameResult result, GameColor color, int handicap,
			TournamentClass modifier) {

		return calculateRatingChange(ratingA, ratingB, result.value, color.equals(GameColor.BLACK) ? handicap : -handicap, modifier.value);
	}

}
