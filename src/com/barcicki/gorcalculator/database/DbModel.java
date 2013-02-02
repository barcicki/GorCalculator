package com.barcicki.gorcalculator.database;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.Model;

public class DbModel extends Model {
	
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
	
	public void notifyObservers() {
		for (DbObserver o : mObservers) {
			o.update(this);
		}
	}
	
	public interface DbObserver {
		public void update(DbModel model);
	}
	
}
