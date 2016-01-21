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

public class FacilitiesFragment extends Fragment {	
	
	WebView				homeWebView;
	Button				menuButton;
	Button				homeButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_facilities, container, false);
		
		TextView titleTextView = (TextView)v.findViewById(R.id.TitleTextView);
		
		homeWebView = (WebView)v.findViewById(R.id.FacilitiesWebView);
		homeWebView.getSettings().setJavaScriptEnabled(true);
		homeWebView.setWebChromeClient( new WebChromeClient());
		
		if (DataManager.languageType == 1) {
			titleTextView.setText("FACILITIES");
			homeWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/English/Design Hotel Hong Kong  Hong Kong Free WiFi   Hotel ICON.html");
		} else if (DataManager.languageType == 2) {
			titleTextView.setText("酒店設施");
			homeWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Tranditional/香港設計酒店   香港免費無線上網WiFi   唯港薈.html");
		} else if (DataManager.languageType == 3) {
			titleTextView.setText("酒店设施");
			homeWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Simplified/香港设计酒店   香港免费无线上网WiFi   唯港荟.html");
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
