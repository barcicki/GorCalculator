package com.barcicki.gorcalculator.core;

import android.os.Parcel;
import android.os.Parcelable;

import com.barcicki.gorcalculator.views.PlayerView;

public class PlayerViewParcel implements Parcelable{

	private PlayerView mPlayerView;
	
	public PlayerViewParcel() {};
	
	public PlayerViewParcel(Parcel inParcel) {
		mPlayerView = (PlayerView) inParcel.readValue(PlayerView.class.getClassLoader());
	}
	
	public PlayerView getPlayerView() {
		return mPlayerView;
	}

	public void setPlayerView(PlayerView mPlayerView) {
		this.mPlayerView = mPlayerView;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(mPlayerView);
	}
	
	public static final Parcelable.Creator<PlayerViewParcel> CREATOR = new Creator<PlayerViewParcel>() {
		
		@Override
		public PlayerViewParcel[] newArray(int size) {
			return new PlayerViewParcel[size];
		}
		
		@Override
		public PlayerViewParcel createFromParcel(Parcel source) {
			return new PlayerViewParcel(source);
		}
	};

}
