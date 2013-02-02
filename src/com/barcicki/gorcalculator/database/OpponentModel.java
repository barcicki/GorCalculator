package com.barcicki.gorcalculator.database;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.barcicki.gorcalculator.database.DbModel.DbObserver;

@Table(name = "Opponents")
public class OpponentModel extends DbModel {
	
	final public static int NO_HANDICAP = 0;
	final public static int NO_KOMI = 1;

	public enum GameResult {
		WIN 	(1f),
		JIGO 	(0.5f),
		LOSS	(0f);
		
		public final float value;
		
		private GameResult(float value) {
			this.value = value;
		}
	};
	
	public enum GameColor {
		BLACK,
		WHITE
	}
	
	public OpponentModel() {
		super();
	}
	
	public OpponentModel(PlayerModel player, GameResult result,
			GameColor color, int handicap) {
		super();
		
		this.player = player;
		this.gor = player.gor;
		this.result = result;
		this.color = color;
		this.handicap = handicap;
	}
	
	@Column(name = "Player")
	public PlayerModel player;
	
	@Column(name = "Gor")
	public double gor;
	
	@Column(name = "Result")
	public GameResult result;
	
	@Column(name = "Color")
	public GameColor color;
	
	@Column(name = "Handicap")
	public int handicap;
	
	@Column(name = "Tournament")
	public TournamentModel tournament;
}
