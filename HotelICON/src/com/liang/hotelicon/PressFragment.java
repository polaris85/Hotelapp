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

public class PressFragment extends Fragment {	
	
	WebView				pressWebView;	
	Button				menuButton;
	Button				homeButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_press, container, false);
		
		TextView titleTextView = (TextView)v.findViewById(R.id.TitleTextView);
		
		pressWebView = (WebView)v.findViewById(R.id.PressWebView);
		pressWebView.getSettings().setJavaScriptEnabled(true);
		pressWebView.setWebChromeClient( new WebChromeClient());
		
		if (DataManager.languageType == 1) {
			titleTextView.setText("PRESS");
			pressWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/English/Hotels Tsim Sha Tsui   Hotel ICON – Press   Hotels in Kowloon Hong Kong.html");
		} else if (DataManager.languageType == 2) {
			titleTextView.setText("傳媒");
			pressWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Tranditional/Hotels Tsim Sha Tsui   Hotel ICON – Press   Hotels in Kowloon Hong Kong.html");
		} else if (DataManager.languageType == 3) {
			titleTextView.setText("传媒");
			pressWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Simplified Chinese/Hotels Tsim Sha Tsui   Hotel ICON – Press   Hotels in Kowloon Hong Kong.html");
		}
		
		menuButton = (Button)v.findViewById(R.id.MenuButton);
		menuButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
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
