package com.barcicki.gorcalculator.core;
// source: http://stackoverflow.com/questions/4458880/a-hashmap-of-weak-references-to-objects-in-android

import java.util.HashMap;
import java.util.Set;

import android.os.Parcel;
import android.os.Parcelable;

public class MyParcel implements Parcelable {
	
	public static String PARCEL = "parcel";
	public static String PLAYER = "player";
	public static String PLAYER_VIEW = "player_view";

	// A HASHMAP WHICH CAN HOLD ALL YOR OBJECTS
	private HashMap<String, Object> activityParcel;

	public MyParcel() {
		// INITIALIZING A HASHMAP
		activityParcel = new HashMap<String, Object>();

	}

	public void readFromParcel(Parcel in) {
		int count = in.readInt();
		for (int i = 0; i < count; i++) {
			activityParcel.put(in.readString(),
					in.readValue(Object.class.getClassLoader()));
		}
	}

	public MyParcel(Parcel in) {
		activityParcel = new HashMap<String, Object>();
		readFromParcel(in);
	}

	@Override
	public int describeContents() {
		return activityParcel != null ? activityParcel.size() : 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(activityParcel.size());
		for (String s : (Set<String>) activityParcel.keySet()) {
			dest.writeString(s);
			dest.writeValue(activityParcel.get(s));
		}

	}

	// MANDATORY CLASS MEMBER ,NAME CREATOR IS ALSO MANDATED
	public static final Parcelable.Creator<MyParcel> CREATOR = new Parcelable.Creator<MyParcel>() {

		@Override
		public MyParcel createFromParcel(Parcel source) {
			return new MyParcel(source);
		}

		@Override
		public MyParcel[] newArray(int size) {
			return new MyParcel[size];
		}

	};
	
	public void put(String key, Object object){
	    activityParcel.put(key, object);
	}

	public Object get(String key) {
		return activityParcel.get(key);
	}
}
