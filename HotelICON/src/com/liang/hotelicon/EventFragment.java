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

public class EventFragment extends Fragment {	
	
	WebView				eventWebView;
	Button				menuButton;
	Button				homeButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_event, container, false);
		
		TextView titleTextView = (TextView)v.findViewById(R.id.TitleTextView);
		
		eventWebView = (WebView)v.findViewById(R.id.EventWebView);
		eventWebView.getSettings().setJavaScriptEnabled(true);
		eventWebView.setWebChromeClient( new WebChromeClient());
		
		if (DataManager.languageType == 1) {
			titleTextView.setText("EVENT");
			eventWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/English/Meeting and Conference Space   Hotel ICON   Hong Kong Wedding Venue.html");
		} else if (DataManager.languageType == 2) {
			titleTextView.setText("舉辦喜慶活動");
			eventWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Tranditional/香港婚禮擺酒場地   酒店宴會及會議服務   唯港薈.html");
		} else if (DataManager.languageType == 3) {
			titleTextView.setText("举办​​喜庆活动");
			eventWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Simplified/香港婚礼摆酒场地   酒店宴会及会议服务   唯港荟.html");
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
