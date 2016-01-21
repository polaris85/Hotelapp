package com.liang.hotelicon;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.liang.model.DataManager;

public class OfferFragment extends Fragment{	
	
	WebView				offerWebView;	
	Button				menuButton;
	Button				homeButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_offer, container, false);
		
		TextView titleTextView = (TextView)v.findViewById(R.id.TitleTextView);
		
		offerWebView = (WebView)v.findViewById(R.id.OfferWebView);
		offerWebView.getSettings().setJavaScriptEnabled(true);
		offerWebView.setWebChromeClient( new WebChromeClient());
		
		if (DataManager.languageType == 1) {
			titleTextView.setText("OFFERS");
			offerWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath() + "/English/Hong Kong Hotel Deals   Hotel ICON   Hotel Offers in Tsim Sha Tsui.html");
		} else if (DataManager.languageType == 2) {
			titleTextView.setText("優惠");
			offerWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath() + "/Chinese Tranditional/香港酒店住宿優惠 唯港薈酒店 尖沙咀酒店最優惠的價格.html");
		} else if (DataManager.languageType == 3) {
			titleTextView.setText("优惠");
			offerWebView.loadUrl( "file:///" + getActivity().getExternalFilesDir(null).getPath() + "/Chinese Simplified/香港酒店住宿优惠 唯港荟酒店 尖沙咀酒店最佳价.html");
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
