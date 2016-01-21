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

public class PrivacyFragment extends Fragment{	
	
	WebView				privacyWebView;	
	Button				menuButton;
	Button				homeButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_privacy, container, false);
		
		TextView titleTextView = (TextView)v.findViewById(R.id.TitleTextView);
		
		privacyWebView = (WebView)v.findViewById(R.id.PrivacyWebView);
		privacyWebView.getSettings().setJavaScriptEnabled(true);
		privacyWebView.setWebChromeClient( new WebChromeClient());
		
		if (DataManager.languageType == 1) {
			titleTextView.setText("PRIVACY");
			privacyWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/English/Kowloon Hong Kong Hotels   Hotel ICON - Privacy   Tsim Sha Tsui Hotels.html");
		} else if (DataManager.languageType == 2) {
			titleTextView.setText("私隱聲明");
			privacyWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Tranditional/Kowloon Hong Kong Hotels   Hotel ICON - Privacy   Tsim Sha Tsui Hotels.html");
		} else if (DataManager.languageType == 3) {
			titleTextView.setText("私隐声明");
			privacyWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Simplified/Kowloon Hong Kong Hotels   Hotel ICON - Privacy   Tsim Sha Tsui Hotels.html");
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
