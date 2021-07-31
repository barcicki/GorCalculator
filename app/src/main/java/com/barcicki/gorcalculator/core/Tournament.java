package com.barcicki.gorcalculator.core;

import java.util.ArrayList;
import java.util.List;

import com.barcicki.gorcalculator.database.OpponentModel;
import com.barcicki.gorcalculator.database.TournamentModel;

public final class Tournament {

	private static TournamentModel currentTournament = null;
	private static List<TournamentObserver> observers = new ArrayList<TournamentObserver>();
	
	public static TournamentModel getTournament() {
		if (currentTournament == null) {
			currentTournament = TournamentModel.getActiveTournament();
		}
		return currentTournament;
	}
	
	public static TournamentModel refreshTournament() {
		currentTournament = null;
		return getTournament();
	}
	
	public static void update(boolean opponentsChanged) {
		getTournament().save();
		notifyObservers(opponentsChanged);
	}
	
	public static double calculateFinalGor() {
		TournamentModel tournament = getTournament();
		double change = 0;
		
		for (OpponentModel opponent : tournament.opponents()) {
			change += Calculator.calculateRatingChange(tournament.gor, opponent.gor, opponent.result, opponent.color, opponent.handicap, tournament.tournamentClass);
		}

		return Math.round((getTournament().gor + change) * 1000) / 1000f;
	}
	
	public static void addObserver(TournamentObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}
	
	public static void removeObserver(TournamentObserver observer) {
		if (observers.contains(observer)) {
			observers.remove(observer);
		}
	}
	
	public static void clearObservers() {
		observers.clear();
	}
	
	public static void notifyObservers(boolean opponentsChanged) {
		for (TournamentObserver observer : observers) {
			observer.update(opponentsChanged);
		}
	}
	
	public interface TournamentObserver {
		public void update(boolean opponentsChanged);
	}
	
	
	
}
