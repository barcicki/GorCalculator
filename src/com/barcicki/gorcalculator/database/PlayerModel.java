package com.barcicki.gorcalculator.database;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

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
	
	
}
