package com.barcicki.gorcalculator.database;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.Model;

public class DbModel extends Model {
	
	public static final String ID = "id";
	public static final int FIRST_PAGE = 0;
	public static final int LIMIT = 50;
	
	private List<DbObserver> mObservers = new ArrayList<DbObserver>();
	
	public void addObserver(DbObserver observer) {
		if (!mObservers.contains(observer)) {
			mObservers.add(observer);
		}
	}
	
	public void removeObserver(DbObserver observer) {
		if (mObservers.contains(observer)) {
			mObservers.remove(observer);
		}
	}
	
	public void notifyObservers(Object data) {
		for (DbObserver o : mObservers) {
			o.update(data);
		}
	}
	
	public interface DbObserver {
		public void update(Object data);
	}
	
}
