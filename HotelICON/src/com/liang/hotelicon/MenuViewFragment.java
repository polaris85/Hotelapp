package com.liang.hotelicon;

import java.io.StringReader;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.liang.model.DataManager;
import com.liang.model.ProgressHUD;

public class MenuViewFragment extends Fragment implements OnClickListener {
	
	TextView	menuTextView;
	
	Button	memberButton;
	Button	loginButton;
	LinearLayout memberLayout;
	Button  memberProfileButton;
	Button	redeemCouponButton;
	Button	myCouponButton;
	Button	promotionButton;
	Button  logoutButton;
	
	Button  hotelInformationButton;
	LinearLayout hotelInformationLayout;
	Button	aboutHotelButton;
	Button 	roomsButton;
	Button 	aboveBeyondButton;
	Button 	dinigButton;
	Button 	facilitiesButton;
	Button 	eventButton;
	Button	ourCityButton;
	Button 	offersButton;
	
	Button  aboutUsButton;
	LinearLayout aboutUsLayout;
	Button 	locationContactsButton;
	Button 	pressButton;
	Button 	careesEducationButton;
	Button 	privacyButton;
	
	Button 	englishButton;
	Button 	traditionalChineseButton;
	Button 	simplifiedChineseButton;
	
	Button  languageButton;
			
	int 	currentIndex;
	
	ProgressHUD mProgressHUD;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.menu_frame, container, false);
		
		menuTextView = (TextView)v.findViewById(R.id.MenuLabel);
		
		memberButton = (Button)v.findViewById(R.id.MemberButton);
		memberButton.setOnClickListener(this);
		
		loginButton = (Button)v.findViewById(R.id.LoginButton);
		loginButton.setOnClickListener(this);
		
		memberLayout = (LinearLayout)v.findViewById(R.id.MemberLayout);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
		memberLayout.setLayoutParams(lp);
		
		memberProfileButton = (Button)v.findViewById(R.id.MemberProfileButton);
		memberProfileButton.setOnClickListener(this);
		
		redeemCouponButton = (Button)v.findViewById(R.id.RedeemCouponButton);
		redeemCouponButton.setOnClickListener(this);
		
		myCouponButton = (Button)v.findViewById(R.id.MyCouponButton);
		myCouponButton.setOnClickListener(this);
		
		promotionButton = (Button)v.findViewById(R.id.PromotionButton);
		promotionButton.setOnClickListener(this);
		
		logoutButton = (Button)v.findViewById(R.id.LogoutButton);
		logoutButton.setOnClickListener(this);
		
		hotelInformationButton = (Button)v.findViewById(R.id.HotelInformationButton);
		hotelInformationButton.setOnClickListener(this);
		
		hotelInformationLayout = (LinearLayout)v.findViewById(R.id.HotelInformationLayout);
		hotelInformationLayout.setLayoutParams(lp);
		
		aboutHotelButton = (Button)v.findViewById(R.id.AboutHotelButton);
		aboutHotelButton.setOnClickListener(this);
		
		roomsButton = (Button)v.findViewById(R.id.RoomsButton);
		roomsButton.setOnClickListener(this);
		
		aboveBeyondButton = (Button)v.findViewById(R.id.AboveBeyondButton);
		aboveBeyondButton.setOnClickListener(this);
		
		dinigButton = (Button)v.findViewById(R.id.DiningButton);
		dinigButton.setOnClickListener(this);
		
		facilitiesButton = (Button)v.findViewById(R.id.FacilitiesButton);
		facilitiesButton.setOnClickListener(this);
		
		eventButton = (Button)v.findViewById(R.id.EventsButton);
		eventButton.setOnClickListener(this);
		
		ourCityButton = (Button)v.findViewById(R.id.OurCityButton);
		ourCityButton.setOnClickListener(this);
		
		offersButton = (Button)v.findViewById(R.id.OfferButton);
		offersButton.setOnClickListener(this);
		
		aboutUsButton = (Button)v.findViewById(R.id.AboutUsButton);
		aboutUsButton.setOnClickListener(this);
		
		aboutUsLayout = (LinearLayout)v.findViewById(R.id.AboutUsLayout);
		aboutUsLayout.setLayoutParams(lp);
		
		locationContactsButton = (Button)v.findViewById(R.id.LocationContactsButton);
		locationContactsButton.setOnClickListener(this);
		
		pressButton = (Button)v.findViewById(R.id.PressButton);
		pressButton.setOnClickListener(this);
		
		careesEducationButton = (Button)v.findViewById(R.id.CareersEducationButton);
		careesEducationButton.setOnClickListener(this);
		
		privacyButton = (Button)v.findViewById(R.id.PrivacyButton);
		privacyButton.setOnClickListener(this);
		
		languageButton = (Button)v.findViewById(R.id.LanguageButton);
		
		englishButton = (Button)v.findViewById(R.id.EnglishButton);
		englishButton.setOnClickListener(this);
		
		traditionalChineseButton = (Button)v.findViewById(R.id.TraditionalChineseButton);
		traditionalChineseButton.setOnClickListener(this);
		
		simplifiedChineseButton = (Button)v.findViewById(R.id.SimplifiedChineseButton);
		simplifiedChineseButton.setOnClickListener(this);
				
		initializeButoon();
		DataManager.menuViewFragment = this;
		return v;		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if ( v == logoutButton) {
			
			mProgressHUD = ProgressHUD.show(getActivity(), "", true, false, null);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String parameters = "<root><session_id>" + DataManager.session_id + "</session_id></root>";
					
					SoapObject request = new SoapObject("http://103.9.244.6/soap/login", "logout");
					request.addProperty("parameters", parameters);
					
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.setOutputSoapObject(request);
					HttpTransportSE httpTransport = new HttpTransportSE("http://103.9.244.6/HotelCRM/webservice.php?wsdl");
					httpTransport.debug = true;
					
					try {
						httpTransport.call("http://103.9.244.6/HotelCRM/webservice.php/login", envelope);
						SoapObject result  = (SoapObject)envelope.bodyIn;
						String status_code = "";
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
					              }
					          } else if(eventType == XmlPullParser.END_TAG) {
					              System.out.println("End tag "+ parser.getName());
					          } 
					          eventType = parser.next();
					    }
						if (status_code.equalsIgnoreCase("0")) {
							DataManager.session_id = "";									
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					getActivity().runOnUiThread( new Runnable() {								
						@Override
						public void run() {
							// TODO Auto-generated method stub									
							mProgressHUD.dismiss();
							if (DataManager.session_id.length() == 0) {
								DataManager.loginFlag = false;
								refreshMenu();
								DataManager.mainActivity.fragmentReplace(18);
							} else {
								needShowAlert("Logout failed!.");
							}
						}
					});					
				}
			}).start();						
		} else if ( v == memberButton) {
			if (!DataManager.loginFlag) {
				return;
			}
			
			LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			LinearLayout.LayoutParams lp2= new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
			
			memberLayout.setLayoutParams(lp1);
			aboutUsLayout.setLayoutParams(lp2);
			hotelInformationLayout.setLayoutParams(lp2);
			
		} else if ( v == hotelInformationButton) {
				
			LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			LinearLayout.LayoutParams lp2= new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
			
			hotelInformationLayout.setLayoutParams(lp1);
			aboutUsLayout.setLayoutParams(lp2);
			memberLayout.setLayoutParams(lp2);
			
		} else if ( v == aboutUsButton) {			
		
			LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			LinearLayout.LayoutParams lp2= new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
			
			aboutUsLayout.setLayoutParams(lp1);
			hotelInformationLayout.setLayoutParams(lp2);
			memberLayout.setLayoutParams(lp2);
		} else if ( v == englishButton) {
			DataManager.languageType = 1;
			initializeButoon();
			DataManager.mainActivity.fragmentReplace(currentIndex);
		} else if ( v == traditionalChineseButton) {
			DataManager.languageType = 2;
			initializeButoon();
			DataManager.mainActivity.fragmentReplace(currentIndex);
		} else if ( v == simplifiedChineseButton) {
			DataManager.languageType = 3;
			initializeButoon();
			DataManager.mainActivity.fragmentReplace(currentIndex);
		} else {
			currentIndex = Integer.parseInt((String)v.getTag());
			DataManager.mainActivity.fragmentReplace(currentIndex);
		}
	}
	
	public void initializeButoon() {
		if (DataManager.languageType == 1) {
			
			menuTextView.setText("Menu");
			
			memberButton.setText("Member");
			loginButton.setText("Log in");
			memberProfileButton.setText("Member Profile");
			redeemCouponButton.setText("Redeem Coupon");
			myCouponButton.setText("My Coupon");
			promotionButton.setText("Promotion");
			logoutButton.setText("Log out");			
			
			hotelInformationButton.setText("Hotel Information");
			aboutHotelButton.setText("About the Hotel");
			roomsButton.setText("Rooms");
			aboveBeyondButton.setText("Above & Beyond");
			dinigButton.setText("Dining");
			facilitiesButton.setText("Facilities");
			eventButton.setText("Events");
			ourCityButton.setText("Our City");
			offersButton.setText("Offers");
			
			aboutUsButton.setText("About US");
			locationContactsButton.setText("Location & Contacts");
			pressButton.setText("Press");
			careesEducationButton.setText("Careers & Education");
			privacyButton.setText("Privacy");
			
			languageButton.setText("Language");
			
		} else if (DataManager.languageType == 2) {
			
			menuTextView.setText("菜單");
						
			memberButton.setText("會員");
			loginButton.setText("登入");
			memberProfileButton.setText("會員資料");
			redeemCouponButton.setText("兌換優惠券");
			myCouponButton.setText("我的優惠券");
			promotionButton.setText("推廣");
			logoutButton.setText("登出");
			
			hotelInformationButton.setText("酒店資訊");
			aboutHotelButton.setText("關於酒店");
			roomsButton.setText("客房");
			aboveBeyondButton.setText("天外天");
			dinigButton.setText("餐飲");
			facilitiesButton.setText("酒店設施");
			eventButton.setText("舉辦喜慶活動");
			ourCityButton.setText("體驗我們的城市");
			offersButton.setText("優惠");
			
			aboutUsButton.setText("關於我們");
			locationContactsButton.setText("位置和聯絡");
			pressButton.setText("傳媒");
			careesEducationButton.setText("職業及教育");
			privacyButton.setText("私隱聲明");
			
			languageButton.setText("語言");
			
		} else if (DataManager.languageType == 3) {
			
			menuTextView.setText("菜單");
			
			memberButton.setText("会员");
			loginButton.setText("登入");
			memberProfileButton.setText("会员资料");
			redeemCouponButton.setText("兑换优惠券");
			myCouponButton.setText("我的优惠券");
			promotionButton.setText("推广");
			logoutButton.setText("登出");
			
			hotelInformationButton.setText("酒店信息");
			aboutHotelButton.setText("关于酒店");
			roomsButton.setText("客房");
			aboveBeyondButton.setText("天外天");
			dinigButton.setText("餐饮");
			facilitiesButton.setText("酒店设施");
			eventButton.setText("举办​​喜庆活动");
			ourCityButton.setText("体验我们的城市");
			offersButton.setText("优惠");
			
			aboutUsButton.setText("关于我们");
			locationContactsButton.setText("位置和联络");
			pressButton.setText("传媒");
			careesEducationButton.setText("职业及教育");
			privacyButton.setText("私隐声明");
			
			languageButton.setText("语言");
		}
	}
	
	public void refreshMenu() {
		if (DataManager.loginFlag == true) {
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			memberLayout.setLayoutParams(lp);
			LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
			lp2.leftMargin = (int)( 30 * getActivity().getResources().getDisplayMetrics().density);
			loginButton.setLayoutParams(lp2);
		} else {
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
			memberLayout.setLayoutParams(lp);
			LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			lp2.leftMargin = (int)( 30 * getActivity().getResources().getDisplayMetrics().density);
			loginButton.setLayoutParams(lp2);
		}
	}
	
	public void needShowAlert(final String text) {
        if (text == null ) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Hotel-Icon");
        builder.setMessage(text);
        builder.setPositiveButton("Ok", null);
        builder.show().setCanceledOnTouchOutside(true);
    }
}
