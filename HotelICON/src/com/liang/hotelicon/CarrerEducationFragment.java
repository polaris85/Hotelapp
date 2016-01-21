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

public class CarrerEducationFragment extends Fragment {	
	
	WebView				careerEducationWebView;	
	Button				menuButton;
	Button				homeButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_career_education, container, false);
		
		TextView titleTextView = (TextView)v.findViewById(R.id.TitleTextView);
		
		careerEducationWebView = (WebView)v.findViewById(R.id.CarrerEducationWebView);
		careerEducationWebView.getSettings().setJavaScriptEnabled(true);
		careerEducationWebView.setWebChromeClient( new WebChromeClient());
		
		if (DataManager.languageType == 1) {
			titleTextView.setText("CAREERS & EDUCATION");
			careerEducationWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/English/Kowloon Hotels   Hotel ICON - Careers & Education   Hotels Kowloon Hong Kong.html");
		} else if (DataManager.languageType == 2) {
			titleTextView.setText("職業及教育");
			careerEducationWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Tranditional/Kowloon Hotels   Hotel ICON - Careers & Education   Hotels Kowloon Hong Kong.html");
		} else if (DataManager.languageType == 3) {
			titleTextView.setText("职业及教育");
			careerEducationWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Simplified/Kowloon Hotels   Hotel ICON - Careers & Education   Hotels Kowloon Hong Kong.html");
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
