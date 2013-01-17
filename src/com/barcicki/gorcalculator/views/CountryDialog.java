package com.barcicki.gorcalculator.views;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.core.CountriesAdapter;

public class CountryDialog extends Dialog {
	
	private ListView mList;
	private CountriesAdapter mAdapter;
	private String mCountry = "";
	
	public CountryDialog(Context context) {
		super(context);
		setContentView(R.layout.dialog_countries);
		
		setTitle(context.getString(R.string.title_country));
		setCancelable(true);
		
		mAdapter = new CountriesAdapter(context, android.R.layout.simple_list_item_1);
		
		mList = (ListView) findViewById(R.id.countryList);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				mCountry = mAdapter.getItem(position).initials;
				dismiss();
			}
		});
	}
	
	public String getCountry() {
		return mCountry;
	}

}
