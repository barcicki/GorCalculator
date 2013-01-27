package com.barcicki.gorcalculator.core;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.barcicki.gorcalculator.core.CountriesAdapter.Country;

public class CountriesAdapter extends ArrayAdapter<Country> {

	private static String RESOURCE_PREFIX = "country_";
	private static String[] SUPPORTED_COUNTRIES = new String[] { "AD", "AG",
			"AR", "AM", "AU", "AT", "AZ", "BY", "BE", "BA", "BR", "BN", "BG",
			"CA", "CN", "CL", "CO", "CR", "HR", "CU", "CY", "CZ", "DK", "EC",
			"EE", "FI", "FR", "GF", "DE", "GR", "GT", "HK", "HU", "IS", "IN",
			"ID", "IR", "IE", "IL", "IT", "JP", "KZ", "KP", "KR", "LV", "LT",
			"LU", "MO", "MK", "MG", "MY", "MX", "MD", "MN", "MA", "NP", "NL",
			"NZ", "NO", "PA", "PE", "PH", "PL", "PT", "RS", "RO", "RU", "SM",
			"SG", "SK", "SI", "ZA", "ES", "SE", "CH", "TA", "TW", "TH", "TO",
			"TR", "UA", "UK", "US", "XX", "UY", "UZ", "VE", "VN", "YU" };
	
	public CountriesAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		
		for (String initials : SUPPORTED_COUNTRIES) {
			int resId = context.getResources().getIdentifier(RESOURCE_PREFIX + initials, "string", context.getPackageName());
			add(new Country(initials,	context.getString(resId)));
		}

	}

	public class Country {
		public String initials;
		public String name;
		public Country(String initials, String name) {
			this.initials = initials;
			this.name = name;
		}
		public String toString() {
			return 
				new StringBuilder()
					.append(this.name)
					.append(" (")
					.append(this.initials)
					.append(")")
					.toString();
		}
	}
}
