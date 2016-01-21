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

public class AboutHotelFragment extends Fragment{
	
	
	WebView				aboutWebView;	
	Button				menuButton;
	Button				homeButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_about_hotel, container, false);
		
		TextView titleTextView = (TextView)v.findViewById(R.id.TitleTextView);
		
		aboutWebView = (WebView)v.findViewById(R.id.AboutWebView);
		aboutWebView.getSettings().setJavaScriptEnabled(true);
		aboutWebView.setWebChromeClient( new WebChromeClient());
		
		if (DataManager.languageType == 1) {
			titleTextView.setText("ABOUT THE HOTEL");
			aboutWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/English/Hong Kong Contemporary Hotel  Free WiFi and Mini Bar   Hotel ICON.html");			
		} else if (DataManager.languageType == 2) {
			titleTextView.setText("關於酒店");
			aboutWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Tranditional/香港現代設計酒店   免費無線上網WiFi及迷你吧   唯港薈.html");
		} else if (DataManager.languageType == 3) {
			titleTextView.setText("关于酒店");
			aboutWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Simplified/香港现代设计酒店   免费无线上网WiFi及迷你吧   唯港荟.html");
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
