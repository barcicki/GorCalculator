package com.barcicki.gorcalculator.database;

import java.util.List;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "Tournaments")
public class TournamentModel extends DbModel {
	
	public enum TournamentClass {
		CLASS_A	 	(1f),
		CLASS_B 	(0.75f),
		CLASS_C		(0.5f);
		
		public final float value;
		private TournamentClass(float value) {
			this.value = value;
		}
	};
	
	@Column(name = "Name")
	public String name;
	
	@Column(name = "TournamentClass")
	public TournamentClass tournamentClass;
	
	@Column(name = "Player")
	public PlayerModel player;
	
	public List<OpponentModel> opponents() {
		return getMany(OpponentModel.class, "Tournament");
	}
	
	public static TournamentModel getTournament(long id) {
		return new Select()
			.from(TournamentModel.class)
			.where("Id = ?", id)
			.executeSingle();
	}
	
	public static List<TournamentModel> getTournaments() {
		return new Select()
			.from(TournamentModel.class)
			.orderBy("Name ASC")
			.execute();
	}
	
}
