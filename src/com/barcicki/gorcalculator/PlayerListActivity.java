package com.barcicki.gorcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class PlayerListActivity extends FragmentActivity implements
		PlayerListFragment.Callbacks {

	private boolean mTwoPane;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_list);

		if (findViewById(R.id.player_detail_container) != null) {
			mTwoPane = true;
			((PlayerListFragment) getSupportFragmentManager().findFragmentById(
					R.id.player_list)).setActivateOnItemClick(true);
		}
	}

	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			Bundle arguments = new Bundle();
			arguments.putString(PlayerDetailFragment.ARG_ITEM_ID, id);
			PlayerDetailFragment fragment = new PlayerDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.player_detail_container, fragment).commit();

		} else {
			Intent detailIntent = new Intent(this, CalculatorActivity.class);
			detailIntent.putExtra(PlayerDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
}
