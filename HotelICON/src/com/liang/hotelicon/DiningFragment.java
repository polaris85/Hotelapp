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

public class DiningFragment extends Fragment {	
		
	WebView				diningWebView;	
	Button				menuButton;
	Button				homeButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_dining, container, false);
		
		TextView titleTextView = (TextView)v.findViewById(R.id.TitleTextView);
		
		diningWebView = (WebView)v.findViewById(R.id.DiningWebView);
		diningWebView.getSettings().setJavaScriptEnabled(true);
		diningWebView.setWebChromeClient( new WebChromeClient());
		
		if (DataManager.languageType == 1) {
			titleTextView.setText("DINING");
			diningWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/English/Tsim Sha Tsui Restaurant   Hotel ICON   Fine Dining Hong Kong.html");
		} else if (DataManager.languageType == 2) {
			titleTextView.setText("餐飲");
			diningWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Tranditional/香港海鮮自助餐   唯港薈   高級中菜餐廳.html");
		} else if (DataManager.languageType == 3) {
			titleTextView.setText("餐饮");
			diningWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath()  + "/Chinese Simplified/香港海鲜自助餐   唯港荟   高级中菜餐厅.html");
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
