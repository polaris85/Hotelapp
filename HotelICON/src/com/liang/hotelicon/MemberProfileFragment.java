package com.liang.hotelicon;


import java.io.StringReader;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.liang.model.DataManager;
import com.liang.model.ProgressHUD;

public class MemberProfileFragment extends Fragment {	
	
	TextView 	titleTextView;
	
	TextView 	memberNameTitleTextView;
	TextView 	membershipNoTitleTextView;
	TextView 	memberLevelTitleTextView;
	TextView 	bonusBalanceTitleTextView;
	TextView 	bonusExpiryDateTitleTextView;
	TextView 	cardNoTitleTextView;
	TextView 	cardExpiryDateTitleTextView;

	TextView memberNameTextView;
	TextView membershipNoTextView;
	TextView memberLevelTextView;
	TextView bonusBalanceTextView;
	TextView bonusExpiryDateTextView;
	TextView cardNoTextView;
	TextView cardExpiryDateTextView;
	
	Button 		menuButton;
	
	String		status_code = "";
	String  	memberName;
	String  	memberNumber;
	String		memberLevel;
	String 		bonusBalance;
	String		bonusExpiryDate;
	String		cardNo;
	String		cardExpiryDate;
	
	ProgressHUD mProgressHUD;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_member_profile, container, false);
		
		titleTextView = (TextView)v.findViewById(R.id.TitleTextView);
		memberNameTitleTextView = (TextView)v.findViewById(R.id.MemberNameTitleTextView);
		membershipNoTitleTextView = (TextView)v.findViewById(R.id.MembershipNoTitleTextView);
		memberLevelTitleTextView = (TextView)v.findViewById(R.id.MemberLevelTitleTextView);
		bonusBalanceTitleTextView = (TextView)v.findViewById(R.id.BonusBalanceTitleTextView);
		bonusExpiryDateTitleTextView = (TextView)v.findViewById(R.id.BonusExpiryDateTitleTextView);
		cardNoTitleTextView = (TextView)v.findViewById(R.id.CardNoTitleTextView);
		cardExpiryDateTitleTextView = (TextView)v.findViewById(R.id.CardExpiryDateTitleTextView);
		
		if (DataManager.languageType == 1) {
			titleTextView.setText("MEMBER PROFILE");
			memberNameTitleTextView.setText("Member Name:");
			membershipNoTitleTextView.setText("Member No:");
			memberLevelTitleTextView.setText("Member Level:");
			bonusBalanceTitleTextView.setText("Bonus Balance:");
			bonusExpiryDateTitleTextView.setText("Bonus Expiry Date:");
			cardNoTitleTextView.setText("Card No:");
			cardExpiryDateTitleTextView.setText("Card Expiry Date:");
		} else if (DataManager.languageType == 2) {
			titleTextView.setText("會員資料");
			memberNameTitleTextView.setText("會員姓名:");
			membershipNoTitleTextView.setText("會員號碼:");
			memberLevelTitleTextView.setText("會員級別:");
			bonusBalanceTitleTextView.setText("積分餘額:");
			bonusExpiryDateTitleTextView.setText("積分有效期:");
			cardNoTitleTextView.setText("會員卡號碼:");
			cardExpiryDateTitleTextView.setText("會員卡有效期:");
		} else if (DataManager.languageType == 3) {
			titleTextView.setText("会员资料");
			memberNameTitleTextView.setText("公司姓名:");
			membershipNoTitleTextView.setText("会员号:");
			memberLevelTitleTextView.setText("会員級別:");
			bonusBalanceTitleTextView.setText("奖分余额:");
			bonusExpiryDateTitleTextView.setText("奖分有效期:");
			cardNoTitleTextView.setText("会員卡号碼:");
			cardExpiryDateTitleTextView.setText("会員卡有效期:");
		}
		
		menuButton = (Button)v.findViewById(R.id.MenuButton);
		menuButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DataManager.mainActivity.showMenu();
			}
		});
		
		memberNameTextView = (TextView)v.findViewById(R.id.MemberNameTextView);
		membershipNoTextView = (TextView)v.findViewById(R.id.MembershipNoTextView);
		memberLevelTextView = (TextView)v.findViewById(R.id.MemberLevelTextView);
		bonusBalanceTextView = (TextView)v.findViewById(R.id.BonusBalanceTextView);
		bonusExpiryDateTextView = (TextView)v.findViewById(R.id.BonusExpiryDateTextView);
		cardNoTextView = (TextView)v.findViewById(R.id.CardNoTextView);
		cardExpiryDateTextView = (TextView)v.findViewById(R.id.CardExpiryDateTextView);
		
		mProgressHUD = ProgressHUD.show(getActivity(), "", true, false, null);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String parameters = "<root><session_id>" + DataManager.session_id + "</session_id></root>";
				
				SoapObject request = new SoapObject("http://103.9.244.6/soap/login", "get_member_info");
				request.addProperty("parameters", parameters);
				
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				HttpTransportSE httpTransport = new HttpTransportSE("http://103.9.244.6/HotelCRM/webservice.php?wsdl");
				httpTransport.debug = true;
				
				try {
					httpTransport.call("http://103.9.244.6/HotelCRM/webservice.php/login", envelope);
					SoapObject result  = (SoapObject)envelope.bodyIn;
					XmlPullParserFactory pullParserFactory;
					pullParserFactory = XmlPullParserFactory.newInstance();
					XmlPullParser parser = pullParserFactory.newPullParser();
					parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
					parser.setInput(new StringReader (result.getPropertyAsString(0)));
					int eventType = parser.getEventType();
					while (eventType != XmlPullParser.END_DOCUMENT) {
						String name = null;
				          if(eventType == XmlPullParser.START_DOCUMENT) {
				              System.out.println("Start document");
				          } else if(eventType == XmlPullParser.END_DOCUMENT) {
				              System.out.println("End document");
				          } else if(eventType == XmlPullParser.START_TAG) {
				              name = parser.getName();
				              if (name.equalsIgnoreCase("status_code")) {
				            	  status_code = parser.nextText();
				              } else if(name.equalsIgnoreCase("member")) {
				            	  memberName = parser.getAttributeValue(0);
				            	  memberNumber = parser.getAttributeValue(1);
				            	  memberLevel = parser.getAttributeValue(2);
				            	  cardNo  = parser.getAttributeValue(3);
				            	  cardExpiryDate = parser.getAttributeValue(4);
				            	  bonusBalance = parser.getAttributeValue(5);
				            	  bonusExpiryDate = parser.getAttributeValue(6);
				              }
				          } else if(eventType == XmlPullParser.END_TAG) {
				              System.out.println("End tag "+ parser.getName());
				          } 
				          eventType = parser.next();	
				    }
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				getActivity().runOnUiThread( new Runnable() {								
					@Override
					public void run() {
						// TODO Auto-generated method stub									
						mProgressHUD.dismiss();
						if (status_code.equalsIgnoreCase("0")) {
							memberNameTextView.setText(memberName);
							membershipNoTextView.setText(memberNumber);
							memberLevelTextView.setText(memberLevel);
							bonusBalanceTextView.setText(bonusBalance);
							bonusExpiryDateTextView.setText(bonusExpiryDate);
							cardNoTextView.setText(cardNo);
							cardExpiryDateTextView.setText(cardExpiryDate);
						}
					}
				});			
			}
		}).start();
		
		return v;
	}	
}
