package com.barcicki.gorcalculator.views;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.core.Settings;

public class AboutDialog extends Dialog {

	private WebView mWebView;
	
	public AboutDialog(Context context) {
		super(context, R.style.AppDialog);
		setContentView(R.layout.dialog_about);
		setCancelable(true);
		
		mWebView = (WebView) findViewById(R.id.webView);
		mWebView.getSettings().setJavaScriptEnabled(false);
		mWebView.loadUrl(context.getString(R.string.about_url));
		
	}
	

}
