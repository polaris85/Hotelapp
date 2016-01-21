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

import com.liang.adapter.PromotionListAdapter;
import com.liang.model.DataManager;
import com.liang.model.ProgressHUD;

public class PromotionFragment extends Fragment implements OnItemClickListener {	
	
	TextView 			titleTextView;
	Button 				menuButton;
	
	ListView			couponListView;	
	PromotionListAdapter	couponListAdapter;
	
	ArrayList<HashMap<String, String>> subCouponList;
	ArrayList<HashMap<String, String>> list;
	String				status_code;
	ProgressHUD 		mProgressHUD;
	
	String 				language;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_promotion, container, false);
		
		titleTextView = (TextView)v.findViewById(R.id.TitleTextView);
		
		if (DataManager.languageType == 1) {
			titleTextView.setText("Promotion");
			language = "EN";
		} else if (DataManager.languageType == 2) {
			titleTextView.setText("推廣");
			language = "ZH-HK";
		} else if (DataManager.languageType == 3) {
			titleTextView.setText("推广");
			language = "ZH-CN";
		}
		
		menuButton = (Button)v.findViewById(R.id.MenuButton);
		menuButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				DataManager.mainActivity.showMenu();
			}
		});	
		
		
		couponListView = (ListView)v.findViewById(R.id.CouponListView);
		subCouponList = new ArrayList<HashMap<String,String>>();
		couponListAdapter = new PromotionListAdapter(getActivity(), subCouponList, DataManager.languageType);
		couponListView.setAdapter(couponListAdapter);
		couponListView.setOnItemClickListener(this);
		
		getCouponList();
		return v;
	}	
	
	public void getCouponList( ) {
		
		mProgressHUD = ProgressHUD.show(getActivity(), "", true, false, null);		
		new Thread( new Runnable() {			
			@Override
			public void run() {
				
				list = new ArrayList<HashMap<String,String>>();
				
				String parameters = "<root><session_id>" + DataManager.session_id + "</session_id>" + "<language>" + language + "</language></root>";
				SoapObject request = new SoapObject("http://103.9.244.6/soap/login", "get_promotion_list");
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
				            	  for (int i = 0; i < 7; i++) {
									String key = parser.getAttributeName(i);
									String value = parser.getAttributeValue(i);
									coupon.put(key, value);
				            	  }
				            	  list.add(coupon);
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
						mProgressHUD.dismiss();
						updateCouponUI();
					}
				});
			}
		}).start();
	}
	
	public void updateCouponUI( ) {
		if (list.size() > 0) {
			subCouponList.clear();
			subCouponList.addAll(list);
			couponListAdapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		DataManager.selectedCoupon = subCouponList.get(arg2);
		Intent newIntent = new Intent(getActivity(), PromotionActivity.class);
		startActivity(newIntent);
	}
}
