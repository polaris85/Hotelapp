package com.liang.hotelicon;


import com.liang.model.DataManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class OurCityFragment extends Fragment{	
	
	WebView				ourCityWebView;	
	Button				menuButton;
	Button				homeButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_ourcity, container, false);
		
		TextView titleTextView = (TextView)v.findViewById(R.id.TitleTextView);
		
		ourCityWebView = (WebView)v.findViewById(R.id.OurcityWebView);
		ourCityWebView.getSettings().setJavaScriptEnabled(true);
		ourCityWebView.setWebChromeClient( new WebChromeClient());
		
		if (DataManager.languageType == 1) {
			titleTextView.setText("OUR CITY");
			ourCityWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/English/Hotels in Hong Kong   Hotel ICON - Our City   Luxury Hong Kong Hotels.html");
		} else if (DataManager.languageType == 2) {
			titleTextView.setText("體驗我們的城市");
			ourCityWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Tranditional/Hotels in Hong Kong   Hotel ICON - Our City   Luxury Hong Kong Hotels.html");
		} else if (DataManager.languageType == 3) {
			titleTextView.setText("体验我们的城市");
			ourCityWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Simplified/Hotels in Hong Kong   Hotel ICON - Our City   Luxury Hong Kong Hotels.html");
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
}
