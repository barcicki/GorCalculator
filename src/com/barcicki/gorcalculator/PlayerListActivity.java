package com.barcicki.gorcalculator;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.os.ParcelableCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.barcicki.gorcalculator.core.MyParcel;
import com.barcicki.gorcalculator.core.Player;
import com.barcicki.gorcalculator.database.DatabaseHelper;
import com.barcicki.gorcalculator.views.PlayerView;

public class PlayerListActivity extends Activity {
	
	public static int REQUEST_DEFAULT = 1;
	
	private ListView mPlayerList;
	private EditText mFilter;
	private PlayersAdapter mPlayersAdapter;
	
	private DatabaseHelper mDB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mDB = new DatabaseHelper(this);
		
		mPlayersAdapter =  new PlayersAdapter(this, mDB.getPlayers());
		mPlayerList = (ListView) findViewById(R.id.playerList);
		mFilter = (EditText) findViewById(R.id.playerFilter);
		
		mPlayerList.setAdapter(mPlayersAdapter);
		mPlayerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				
				Player p = mPlayersAdapter.getItem(position);
				Bundle bundle = getIntent().getExtras();

				if (bundle.containsKey(MyParcel.PARCEL)) {
					MyParcel parcel = bundle.getParcelable(MyParcel.PARCEL);
					parcel.put(MyParcel.PLAYER, p);
					
					Intent returnIntent = new Intent();
					returnIntent.putExtra(MyParcel.PARCEL, parcel);
					setResult(RESULT_OK, returnIntent);
					finish();
				} else {
					Toast.makeText(PlayerListActivity.this, "not ok", Toast.LENGTH_SHORT).show();
				}
				
			}
			
		});
		
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			NavUtils.navigateUpTo(this, new Intent(this, CalculatorActivity.class));
		}
		return super.onOptionsItemSelected(item);
	}
	
	public class PlayersAdapter extends ArrayAdapter<Player> {

		private LayoutInflater mInflater;
		private ArrayList<Player> mPlayers;
		
		public PlayersAdapter(Context context, ArrayList<Player> players) {
			this(context, R.layout.player_item, players);
		}
		
		public PlayersAdapter(Context context, int textViewResourceId,
				ArrayList<Player> objects) {
			super(context, textViewResourceId, objects);

			mInflater = LayoutInflater.from(context);
			mPlayers = objects;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			PlayerView pv;
			
			if (convertView == null) {
				pv = (PlayerView) mInflater.inflate(R.layout.player_item, parent, false);
				
				pv.setPlayer(mPlayers.get(position));
				pv.setShowButtonChange(false);
				pv.setShowPlayerDetails(true);
				pv.getGorButton().setEnabled(false);
				
				return pv;
			
			} 
			
			return convertView;
		}

		@Override
		public int getCount() {
			return mPlayers.size();
		}
		
		@Override
		public Player getItem(int position) {
			return mPlayers.get(position);
		}
		
	}
	
}
