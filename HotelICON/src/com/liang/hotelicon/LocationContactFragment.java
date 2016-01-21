package com.liang.hotelicon;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.liang.model.DataManager;

public class LocationContactFragment extends Fragment {	
		
	WebView				locationContactWebView;	
	Button				menuButton;
	Button				homeButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_location_contact, container, false);
		
		TextView titleTextView = (TextView)v.findViewById(R.id.TitleTextView);
		
		locationContactWebView = (WebView)v.findViewById(R.id.LocationContactWebView);
		locationContactWebView.getSettings().setJavaScriptEnabled(true);
		locationContactWebView.setWebChromeClient( new WebChromeClient());	
		
		if (DataManager.languageType == 1) {
			titleTextView.setText("LOCATION & CONTACTS");
			locationContactWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/English/Luxury Tsim Sha Tsui Hotel   Hotel ICON   5 Star Hotels in Kowloon.html");
		} else if (DataManager.languageType == 2) {
			titleTextView.setText("位置和聯絡");
			locationContactWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Tranditional/尖沙咀東部酒店 唯港薈酒店 九龍五星級豪華酒店.html");
		} else if (DataManager.languageType == 3) {
			titleTextView.setText("位置和联络");
			locationContactWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Simplified/尖沙咀东部酒店 唯港荟酒店 九龙五星级豪华酒店.html");
		}
		
		menuButton = (Button)v.findViewById(R.id.MenuButton);
		menuButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DataManager.mainActivity.showMenu();
			}
		});
		
		homeButton = (Button)v.findViewById(R.id.HomeButton);
		homeButton.setOnClickListener( new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DataManager.mainActivity.fragmentReplace(0);
			}
		});
		
		return v;
	}	
	
	public class MyWebClient extends WebViewClient
	{

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub

			view.loadUrl(url);
			return true;

		}
	}
}
