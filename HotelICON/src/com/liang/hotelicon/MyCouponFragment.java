package com.liang.hotelicon;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.liang.adapter.MyCouponListAdapter;
import com.liang.model.DataManager;
import com.liang.model.ProgressHUD;

public class MyCouponFragment extends Fragment implements OnItemClickListener {	
	
	TextView 			titleTextView;
	Button 				menuButton;
	
	ArrayList<String> 	categoryList;
	
	Button				activeButton;
	Button				usedButton;
	Button				expiredButton;
	Button				selectedCategoryButton;
	
	HashMap<String, ArrayList<HashMap<String, String>>> couponList;
	
	ListView			couponListView;	
	MyCouponListAdapter	couponListAdapter;
	ArrayList<HashMap<String, String>> subCouponList;
		
	String				status_code;
	ProgressHUD 		mProgressHUD;
	
	String 				language;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_mycoupon, container, false);
		
		titleTextView = (TextView)v.findViewById(R.id.TitleTextView);
		
		if (DataManager.languageType == 1) {
			titleTextView.setText("Redeem Coupon");
			language = "EN";
		} else if (DataManager.languageType == 2) {
			titleTextView.setText("換購優惠券");
			language = "ZH-HK";
		} else if (DataManager.languageType == 3) {
			titleTextView.setText("換購優惠券");
			language = "ZH-CN";
		}
		
		menuButton = (Button)v.findViewById(R.id.MenuButton);
		menuButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				DataManager.mainActivity.showMenu();
			}
		});	
		
		couponList = new HashMap<String, ArrayList<HashMap<String,String>>>();
		activeButton = (Button)v.findViewById(R.id.ActiveButton);
		activeButton.setOnClickListener(categoryButtonClickListener);
		usedButton = (Button)v.findViewById(R.id.UsedButton);
		usedButton.setOnClickListener(categoryButtonClickListener);
		expiredButton = (Button)v.findViewById(R.id.ExpiredButton);
		expiredButton.setOnClickListener(categoryButtonClickListener);
		selectedCategoryButton = activeButton;
		
		couponListView = (ListView)v.findViewById(R.id.CouponListView);
		subCouponList = new ArrayList<HashMap<String,String>>();
		couponListAdapter = new MyCouponListAdapter(getActivity(), subCouponList, DataManager.languageType);
		couponListView.setAdapter(couponListAdapter);
		couponListView.setOnItemClickListener(this);
		
		categoryList = new ArrayList<String>();
		
		getCouponList("Active");
		
		return v;
	}	
	
	View.OnClickListener categoryButtonClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			selectedCategoryButton.setBackgroundColor(Color.rgb(255, 255, 255));
			selectedCategoryButton = (Button)v;
			selectedCategoryButton.setBackgroundColor(Color.rgb(238, 238, 238));
			ArrayList<HashMap<String, String>> list = couponList.get(selectedCategoryButton.getTag().toString());
			if (list != null && list.size() != 0) {
				updateCouponUI(selectedCategoryButton.getTag().toString());
			} else {
				getCouponList(selectedCategoryButton.getTag().toString());
			}
		}
	};
			
	public void getCouponList( final String category) {
		
		mProgressHUD = ProgressHUD.show(getActivity(), "", true, false, null);		
		new Thread( new Runnable() {			
			@Override
			public void run() {
				
				ArrayList<HashMap<String, String>> categoryCouponList = new ArrayList<HashMap<String,String>>();
				
				String parameters = "<root><session_id>" + DataManager.session_id + "</session_id>" + "<type>" + category + "</type>" + "<language>" + language + "</language></root>";
				SoapObject request = new SoapObject("http://103.9.244.6/soap/login", "get_member_coupon_list ");
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
				              } else if (name.equalsIgnoreCase("item")) {
				            	  HashMap<String, String> coupon = new HashMap<String, String>();
				            	  for (int i = 0; i < 13; i++) {
									String key = parser.getAttributeName(i);
									String value = parser.getAttributeValue(i);
									coupon.put(key, value);
				            	  }
				            	  categoryCouponList.add(coupon);
				              }
				          } else if(eventType == XmlPullParser.END_TAG) {
				              System.out.println("End tag "+ parser.getName());
				          } 
				          eventType = parser.next();
				    }
					couponList.put(category, categoryCouponList);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				getActivity().runOnUiThread( new Runnable() {
					@Override
					public void run() {
						mProgressHUD.dismiss();
						updateCouponUI(category);
					}
				});
			}
		}).start();
	}
	
	public void updateCouponUI( String category ) {
		ArrayList<HashMap<String, String>> list = couponList.get(category);
		if (list != null) {
			subCouponList.clear();
			subCouponList.addAll(list);
			couponListAdapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		DataManager.selectedCoupon = subCouponList.get(arg2);
		Intent newIntent = new Intent(getActivity(), MyCouponDetailActivity.class);
		startActivity(newIntent);
	}
}
