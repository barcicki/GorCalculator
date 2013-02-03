package com.barcicki.gorcalculator.database;

import java.util.Date;
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
	
	public TournamentModel(PlayerModel player, TournamentClass tournamentClass) {
		super();
		this.player = player;
		this.created = new Date();
		this.tournamentClass = tournamentClass;
	}
	
	@Column(name = "Created")
	public Date created; 
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
			.orderBy("Created DESC")
			.limit(LIMIT)
			.offset(page * LIMIT)
			.execute();
	}
	
	public static TournamentModel getActiveTournament() {
		TournamentModel activeTournament = new Select()
			.from(TournamentModel.class)
			.where("Active = ?", 1)
			.executeSingle();
		
		if (activeTournament != null) {
			return activeTournament;
			
		} else {
			
			activeTournament = new Select()
				.from(TournamentModel.class)
				.orderBy("Created DESC")
				.executeSingle();
			
			if (activeTournament != null) {
				activeTournament.active = true;
				activeTournament.save();
				
			} else {
				activeTournament = new TournamentModel(PlayerModel.getDefaultPlayer(), TournamentClass.CLASS_A);
				activeTournament.gor = activeTournament.player.gor;
				activeTournament.active = true;
				activeTournament.save();
			}
		}
		
		return activeTournament;
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

	public static TournamentModel createNewTournament() {
		TournamentModel tournament = new TournamentModel();
		tournament.created = new Date();
		tournament.tournamentClass = TournamentClass.CLASS_A;
		tournament.player = PlayerModel.getDefaultPlayer();
		tournament.gor = tournament.player.gor;
		tournament.save();
		return tournament;
	}
	
}
