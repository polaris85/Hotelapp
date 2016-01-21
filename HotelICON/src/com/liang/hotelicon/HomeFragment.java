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

public class HomeFragment extends Fragment {
	
	TextView			titleTextView;
	WebView				homeWebView;
	Button				menuButton;
	Button				homeButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_home, container, false);
		
		titleTextView = (TextView)v.findViewById(R.id.TitleTextView);
		
		homeWebView = (WebView)v.findViewById(R.id.HomeWebView);
		homeWebView.getSettings().setJavaScriptEnabled(true);
		homeWebView.setWebChromeClient( new WebChromeClient());
		
		if (DataManager.languageType == 1) {
			titleTextView.setText("Home");
			homeWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/English/Hong Kong Luxury Hotels   Tsim Sha Tsui Kowloon Hotel   Hotel ICON.html");
		} else if (DataManager.languageType == 2) {
			titleTextView.setText("Home");
			homeWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Tranditional/香港尖沙咀豪華酒店 唯港薈 九龍尖東酒店.html");
		} else if (DataManager.languageType == 3) {
			titleTextView.setText("Home");
			homeWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Simplified/香港尖沙咀豪华酒店 唯港荟 九龙尖东酒店.html");
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
