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

public class AboveAndBeyongFragment extends Fragment {	
	
	WebView				roomWebView;
	Button				menuButton;
	Button				homeButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_above_beyond, container, false);
		
		TextView titleTextView = (TextView)v.findViewById(R.id.TitleTextView);
		
		roomWebView = (WebView)v.findViewById(R.id.AboveBeyondWebView);
		roomWebView.getSettings().setJavaScriptEnabled(true);
		roomWebView.setWebChromeClient( new WebChromeClient());
		
		if (DataManager.languageType == 1) {
			titleTextView.setText("Above & Beyond");
			roomWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/English/Hotels in Kowloon Hong Kong   Hotel ICON - Above & Beyond   Hotels in Tsim Sha Tsui.html");		
		} else if (DataManager.languageType == 2) {
			titleTextView.setText("天外天");
			roomWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Tranditional/Hotels in Kowloon Hong Kong   Hotel ICON - Above & Beyond   Hotels in Tsim Sha Tsui.html");		
		} else if (DataManager.languageType == 3) {
			titleTextView.setText("天外天");
			roomWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Simplified/Hotels in Kowloon Hong Kong   Hotel ICON - Above & Beyond   Hotels in Tsim Sha Tsui.html");		
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
