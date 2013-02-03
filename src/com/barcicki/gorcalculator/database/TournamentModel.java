package com.barcicki.gorcalculator.database;

import java.util.List;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.barcicki.gorcalculator.core.Calculator;

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
	
	public TournamentModel() {
		super();
	}
	
	public TournamentModel(String name, PlayerModel player, TournamentClass tournamentClass) {
		super();
		this.name = name;
		this.player = player;
		this.tournamentClass = tournamentClass;
	}
	
	@Column(name = "Name")
	public String name;
//	
	@Column(name = "TournamentClass")
	public TournamentClass tournamentClass;
	
	@Column(name = "Player")
	public PlayerModel player;
	
	@Column(name = "Gor")
	public double gor;
	
	@Column(name = "Active")
	public boolean active;
	
	public List<OpponentModel> opponents() {
		return getMany(OpponentModel.class, "Tournament");
	}
	
	public static TournamentModel getTournament(long id) {
		return new Select()
			.from(TournamentModel.class)
			.where("Id = ?", id)
			.executeSingle();
	}
	
	public static List<TournamentModel> getAll(int page) {
		return new Select()
			.all()
			.from(TournamentModel.class)
			.orderBy("Name ASC")
			.limit(LIMIT)
			.offset(page * LIMIT)
			.execute();
	}
	
	public static TournamentModel getActiveTournament() {
		List<TournamentModel> tournaments = new Select()
			.from(TournamentModel.class)
			.execute();
		TournamentModel tournament;
		
		if (tournaments.size() > 0) {
			tournament = tournaments.get(0);
			
			for (TournamentModel t : tournaments) {
				if (t.active) {
					if (t.player == null) {
						t.player = PlayerModel.getDefaultPlayer();
					}
					
					t.player.gor = t.gor;
					return t;
				}
			}
			
			tournament.active = true;
			tournament.save();
			
		} else {
			
			tournament = new TournamentModel("Default", PlayerModel.getDefaultPlayer(), TournamentClass.CLASS_A);
			tournament.gor = tournament.player.gor;
			tournament.active = true;
			tournament.save();
			
		}
		
		
		
		Log.d("TournamentModel", 
				"Name: " + tournament.name + "\n" +
				"Active: " + tournament.active+ "\n" +
				"Gor: " + tournament.gor+ "\n"
				);
		Log.d("TournamentModel", "Player: " + tournament.player.name);
		
		return tournament;
		
	}
	
	public double calculateFinalGor() {
		double change = 0;
		
		for (OpponentModel opponent : opponents()) {
			if (opponent != null) {
				change += Calculator.calculate(player, opponent, tournamentClass);
			}
		}
		
		if (change < -100) {
			change = -100;
		}
		
		return Math.round((gor + change) * 1000) / 1000f;
	}

	public void addOpponent(OpponentModel newOpponent) {
		newOpponent.tournament = this;
		newOpponent.save();
	}

	public void removeOpponent(OpponentModel opponent) {
		new Delete().from(OpponentModel.class)
			.where("Id = ?", opponent.getId())
			.where("Tournament = ?", getId())
			.execute();
	}

	public static void setActive(TournamentModel tournament) {
		List<TournamentModel> previous = new Select().from(TournamentModel.class).where("Active = ?", 1).execute();
		
		Log.d("TournamentModel", "Count: " + previous.size());
		
		ActiveAndroid.beginTransaction();
		for (TournamentModel t : previous) {
			t.active = false;
			t.save();
		}
		
		tournament.active = true;
		tournament.save();
		
		ActiveAndroid.setTransactionSuccessful();
		ActiveAndroid.endTransaction();
	}

	public static void createNewTournament(String name) {
		TournamentModel tournament = new TournamentModel();
		tournament.name = name;
		tournament.tournamentClass = TournamentClass.CLASS_A;
		tournament.player = PlayerModel.getDefaultPlayer();
		tournament.gor = tournament.player.gor;
		tournament.save();
	}
	
}
