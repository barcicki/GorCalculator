package com.barcicki.gorcalculator.core;

import android.os.Parcel;
import android.os.Parcelable;

public class PlayerParcel implements Parcelable{

	private Player mPlayer;
	
	public PlayerParcel() {};
	
	public PlayerParcel(Parcel inParcel) {
		mPlayer = (Player) inParcel.readValue(Player.class.getClassLoader());
	}
	
	public Player getPlayer() {
		return mPlayer;
	}

	public void setPlayer(Player mPlayer) {
		this.mPlayer = mPlayer;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(mPlayer);
	}
	
	public static final Parcelable.Creator<PlayerParcel> CREATOR = new Creator<PlayerParcel>() {
		
		@Override
		public PlayerParcel[] newArray(int size) {
			return new PlayerParcel[size];
		}
		
		@Override
		public PlayerParcel createFromParcel(Parcel source) {
			return new PlayerParcel(source);
		}
	};

}
