package com.barcicki.gorcalculator.core;

public class Opponent extends Player {

	final public static float WIN = 1f;
	final public static float LOSS = 0f;
	final public static float JIGO = 0.5f;
	
	final public static int NO_HANDICAP = 0;
	final public static int HANDICAP_1 = 1;
	final public static int HANDICAP_2 = 2;
	final public static int HANDICAP_3 = 3;
	final public static int HANDICAP_4 = 4;
	final public static int HANDICAP_5 = 5;
	final public static int HANDICAP_6 = 6;
	final public static int HANDICAP_7 = 7;
	final public static int HANDICAP_8 = 8;
	final public static int HANDICAP_9 = 9;
	
	final public static int BLACK = 1;
	final public static int WHITE = 0;
	
	private float result;
	private int color;
	private int handicap;
	
	public Opponent(Player player, float result, int color, int handicap) {
		super(player);
		this.setResult(result);
		this.setColor(color);
		this.setHandicap(handicap);
	}
	
	public Opponent(int gor, float result, int color, int handicap) {
		super(gor);
		this.setResult(result);
		this.setColor(color);
		this.setHandicap(handicap);
	}

	public float getResult() {
		return result;
	}

	public void setResult(float result) {
		this.result = result;
		setChanged();
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
		setChanged();
	}

	public int getHandicap() {
		return handicap;
	}
	
	public int getRelativeHandicap() {
		return getColor() == Opponent.WHITE ? -getHandicap() : getHandicap();
	}

	public void setHandicap(int handicap) {
		this.handicap = handicap;
		setChanged();
	}
}
 