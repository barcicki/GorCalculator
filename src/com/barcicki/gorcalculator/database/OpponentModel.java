package com.barcicki.gorcalculator.database;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Opponents")
public class OpponentModel extends DbModel {

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
	
	@Column(name = "Player")
	public PlayerModel player;
	
	@Column(name = "Result")
	public GameResult result;
	
	@Column(name = "Color")
	public GameColor color;
	
	@Column(name = "Handicap")
	public int handicap;
}
