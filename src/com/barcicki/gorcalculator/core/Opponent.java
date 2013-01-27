package com.barcicki.gorcalculator.core;

import java.util.Observable;

public class Opponent extends Observable {

	public enum GameResult {
		WIN 	(1f),
		JIGO 	(0.5f),
		LOSS	(0f);
		
		public static GameResult fromValue(float value) {
			for (GameResult g : GameResult.values()) {
				if (g.value == value) {
					return g;
				}
			}
			return GameResult.LOSS;
		}
		
		public final float value;
		
		private GameResult(float value) {
			this.value = value;
		}
	};
	
	public enum GameColor {
		BLACK,
		WHITE
	}
	
	final public static int NO_HANDICAP = 0;
	final public static int NO_KOMI = 1;
	
	private GameResult mResult;
	private GameColor mColor;
	private int mHandicap;
	
	private Player mPlayer;
	
	public Opponent(Player player, GameResult result, GameColor color, int handicap) {
		this.setPlayer(player);
		this.setResult(result);
		this.setColor(color);
		this.setHandicap(handicap);
	}
	
	public Opponent(int gor, GameResult result, GameColor color, int handicap) {
		this.setPlayer(new Player(gor));
		this.setResult(result);
		this.setColor(color);
		this.setHandicap(handicap);
	}

	public Player getPlayer() {
		return mPlayer;
	}

	public void setPlayer(Player player) {
		mPlayer = player;
		
		setChanged();
		notifyObservers();
	}

	public GameResult getResult() {
		return mResult;
	}

	public void setResult(GameResult result) {
		this.mResult = result;
		setChanged();
		notifyObservers();
	}

	public GameColor getColor() {
		return mColor;
	}

	public void setColor(GameColor color) {
		this.mColor = color;
		setChanged();
		notifyObservers();
	}

	public int getHandicap() {
		return mHandicap;
	}
	
	public int getRelativeHandicap() {
		return getColor() == GameColor.WHITE ? -getHandicap() : getHandicap();
	}

	public void setHandicap(int handicap) {
		this.mHandicap = handicap;
		setChanged();
		notifyObservers();
	}
	
	public double calculatePlayerGorChange(Tournament tournament) {
		return Calculator.calculate(tournament.getPlayer(), this, tournament.getTournamentClass());
	}
	
}
 