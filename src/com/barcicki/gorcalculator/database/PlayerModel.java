package com.barcicki.gorcalculator.database;

import java.util.List;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.util.Log;
import com.barcicki.gorcalculator.core.Go;

@Table(name = "Players")
public class PlayerModel extends DbModel {

	@Column(name = "Pin")
	public int pin;
	
	@Column(name = "Name")
	public String name;
	
	@Column(name = "Gor")
	public double gor;
	
	@Column(name = "Grade")
	public int grade;
	
	@Column(name = "Club")
	public String club;
	
	@Column(name = "Country")
	public String country;
	
	public String getGrade() {
		return Go.STRENGTHS.get(this.grade);
	}
	
	public PlayerModel() {
		super();
	}
	
	public PlayerModel(double gor) {
		this(0, "Shindo Hikaru", gor, Go.gorToGradeValue(gor), "HnG", "JPN");
	}
	
	public PlayerModel(int pin, String name, double gor, int grade,
			String club, String country) {
		super();
		this.pin = pin;
		this.name = name;
		this.gor = gor;
		this.grade = grade;
		this.club = club;
		this.country = country;
	}
	
	private static PlayerModel mDefaultPlayer = null;
	public static PlayerModel getDefaultPlayer() {
		if (mDefaultPlayer == null) {
			mDefaultPlayer = new Select().from(PlayerModel.class).where("Pin = ?", 0).executeSingle();
			if (mDefaultPlayer == null) {
				mDefaultPlayer = new PlayerModel(2000);
				mDefaultPlayer.save();
			}
		}
		return mDefaultPlayer;
	}
	
	private static int LIMIT = 50;
	public static List<PlayerModel> getPlayers(int page, String name, String club, String country, int minGrade, int maxGrade) {
		return new Select().from(PlayerModel.class)
				.where(
						"(Pin > 0) AND " +
						"(Name LIKE ?) AND " + 
						"(Club LIKE ?) AND " +
						"(Country LIKE ?) AND " +
						"(Grade BETWEEN ? AND ?)", 
						"%" + name + "%", 
						"%" + club + "%", 
						"%" + country + "%", 
						minGrade, 
						maxGrade)
				.offset(LIMIT * page)
				.limit(LIMIT)
				.execute();
	}

	public static PlayerModel findByPin(int pin) {
		PlayerModel player = new Select().from(PlayerModel.class).where("Pin = ?", pin).executeSingle();
		if (player == null) {
			player = getDefaultPlayer();
		}
		return player;
	}

}
