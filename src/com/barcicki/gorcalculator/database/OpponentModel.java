package com.barcicki.gorcalculator.database;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Opponents")
public class OpponentModel extends DbModel {
	
	final public static int NO_HANDICAP = 0;
	final public static int NO_KOMI = 1;

	private PlayerModel player;
	
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
	
	@Column(name ="Pin")
	public int pin;
	
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
	
	public void setPlayer(PlayerModel player) {
		this.player = player;
		this.pin = player.pin;
	}
	
	public PlayerModel getPlayer() {
		if (player == null) {
			player = PlayerModel.findByPin(this.pin);
		}
		return player;
	}
}
