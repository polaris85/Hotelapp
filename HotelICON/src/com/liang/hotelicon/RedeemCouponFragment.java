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
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.liang.adapter.CouponListAdapter;
import com.liang.model.DataManager;
import com.liang.model.ProgressHUD;

public class RedeemCouponFragment extends Fragment implements OnItemClickListener {	
	
	TextView 			titleTextView;
	Button 				menuButton;
	
	ArrayList<String> 	categoryList;
	LinearLayout		categoryLayout;
	Button				selectedCategoryButton;
	
	HashMap<String, ArrayList<HashMap<String, String>>> couponList;
	
	ListView			couponListView;	
	CouponListAdapter	couponListAdapter;
	ArrayList<HashMap<String, String>> subCouponList;
		
	String				status_code;
	ProgressHUD 		mProgressHUD;
	
	String 				language;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_redeem_coupon, container, false);
		
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
		
		couponListView = (ListView)v.findViewById(R.id.CouponListView);
		subCouponList = new ArrayList<HashMap<String,String>>();
		couponListAdapter = new CouponListAdapter(getActivity(), subCouponList, DataManager.languageType);
		couponListView.setAdapter(couponListAdapter);
		couponListView.setOnItemClickListener(this);
		
		categoryList = new ArrayList<String>();
		categoryLayout = (LinearLayout)v.findViewById(R.id.CategoryLayout);
		
		mProgressHUD = ProgressHUD.show(getActivity(), "", true, false, null);		
		new Thread( new Runnable() {			
			@Override
			public void run() {
				
				String parameters = "<root><session_id>" + DataManager.session_id + "</session_id><language>" + language + "</language></root>";
				SoapObject request = new SoapObject("http://103.9.244.6/soap/login", "get_category_list ");
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
				            	  String itemName = parser.getAttributeValue(0);
				            	  categoryList.add(itemName);
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
						if (status_code.equalsIgnoreCase("0")) {
							updateCategoryUI();
						}
					}
				});
			}
		}).start();
		
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
	
	public void updateCategoryUI() {
		
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		
		for (int i = 0; i < categoryList.size(); i++) {
			Button categoryButton = new Button(getActivity());
			categoryButton.setLayoutParams(new LayoutParams(displayMetrics.widthPixels / 3, LayoutParams.WRAP_CONTENT));
			if (i == 0) {
				selectedCategoryButton = categoryButton;
				categoryButton.setBackgroundColor(Color.rgb(238, 238, 238));
			} else {
				categoryButton.setBackgroundColor(Color.rgb(255, 255, 255));
			}
			categoryButton.setText(categoryList.get(i));
			categoryButton.setTag(categoryList.get(i));
			categoryButton.setOnClickListener(categoryButtonClickListener);
			categoryLayout.addView(categoryButton);
		}		
		if (categoryList.size() > 0) {
			getCouponList(categoryList.get(0));
		}
	}
	
	public void getCouponList( final String category) {
		
		mProgressHUD = ProgressHUD.show(getActivity(), "", true, false, null);		
		new Thread( new Runnable() {			
			@Override
			public void run() {
				
				ArrayList<HashMap<String, String>> categoryCouponList = new ArrayList<HashMap<String,String>>();
				
				String parameters = "<root><session_id>" + DataManager.session_id + "</session_id>" + "<category>" + category + "</category>" + "<language>" + language + "</language></root>";
				SoapObject request = new SoapObject("http://103.9.244.6/soap/login", "get_coupon_list");
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
				            	  for (int i = 0; i < 12; i++) {
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
						// TODO Auto-generated method stub
						mProgressHUD.dismiss();
						if (category.equalsIgnoreCase(selectedCategoryButton.getTag().toString())) {
							updateCouponUI(category);
						}
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
	
	public int dpToPx(int dp) {
	    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
	    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
	    return px;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		DataManager.selectedCoupon = subCouponList.get(arg2);
		Intent newIntent = new Intent(getActivity(), CouponDetailActivity.class);
		startActivity(newIntent);
	}
}
