package com.barcicki.gorcalculator.database;

import java.util.Date;
import java.util.List;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
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
		List<OpponentModel> opponents = getMany(OpponentModel.class, "Tournament");
		for (OpponentModel op : opponents) {
			if (op.player == null) {
				op.player = new PlayerModel(op.gor);
			}
		}
		return opponents;
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
		
		
		// getting active tournament
		if (activeTournament == null) {
			
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
		
		// validating player, e.g. database failed
		if (activeTournament.player == null) {
			activeTournament.player = PlayerModel.getDefaultPlayer();
			activeTournament.player.gor = activeTournament.gor;
			activeTournament.save();
		}
		
		
		return activeTournament;
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
	
	public static TournamentModel createNewTournament(PlayerModel player) {
		TournamentModel tournament = new TournamentModel();
		tournament.created = new Date();
		tournament.tournamentClass = TournamentClass.CLASS_A;
		tournament.player = player;
		tournament.gor = tournament.player.gor;
		tournament.save();
		return tournament;
	}

	public static TournamentModel createNewTournament() {
		return createNewTournament(PlayerModel.getDefaultPlayer());
	}
	
}
