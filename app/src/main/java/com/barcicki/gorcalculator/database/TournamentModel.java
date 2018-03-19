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
	
	private PlayerModel player;
	
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
	
	@Column(name ="Pin")
	public int pin;
	
	@Column(name = "Gor")
	public double gor;
	
	@Column(name = "Active")
	public boolean active;
	
	public void setPlayer(PlayerModel newPlayer) {
		pin = newPlayer.pin;
		player = null;
	}
	
	public PlayerModel getPlayer() {
		if (player == null) {
			player = PlayerModel.findByPin(pin);
		}
		return player;
	}
	
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
		
		// getting active tournament
		TournamentModel activeTournament = new Select()
			.from(TournamentModel.class)
			.where("Active = ?", 1)
			.executeSingle();
		
		// if no active tournaments found, select first of any found
		if (activeTournament == null) {
			
			activeTournament = new Select()
				.from(TournamentModel.class)
				.orderBy("Created DESC")
				.executeSingle();
			
			if (activeTournament != null) {
				activeTournament.active = true;
				activeTournament.save();
				
			// if no tournaments at all, create one
			} else {
				activeTournament = new TournamentModel(PlayerModel.getDefaultPlayer(), TournamentClass.CLASS_A);
				activeTournament.gor = activeTournament.player.gor;
				activeTournament.active = true;
				activeTournament.save();
			}
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
		ActiveAndroid.beginTransaction();

		List<TournamentModel> previous = new Select().from(TournamentModel.class).where("Active = ?", 1).execute();

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
		tournament.pin = player.pin;
		tournament.gor = player.gor;
		tournament.save();
		return tournament;
	}

	public static TournamentModel createNewTournament() {
		return createNewTournament(PlayerModel.getDefaultPlayer());
	}
	
}
