package com.liang.hotelicon;

import java.io.StringReader;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.liang.model.DataManager;
import com.liang.model.ProgressHUD;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class RedeemActivity extends Activity {
	
	ImageView		couponImageView;
	TextView		sciTextView;
	TextView		rdTextView;
	TextView		quantityTitleTextView;
	TextView 		quantityTextView;
	TextView		subTotalTitleTextView;
	TextView		subTotalTextView;
	TextView		totalTitleTextView;
	TextView		totalTextView;
	
	Button			redeemButton;
		
	DisplayImageOptions 		couponOptions;
	ProgressHUD					mProgressHUD;
	
	int 			quantityNumber;
	
	String			status_code = "";
	String 			language;
	String			password = "";
	String 			message = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_redeem);
		
		couponImageView = (ImageView)findViewById(R.id.CouponImageView);
		sciTextView = (TextView)findViewById(R.id.SCITitleTextView);
		rdTextView = (TextView)findViewById(R.id.RDTitleTextView);
		quantityTextView = (TextView)findViewById(R.id.QuantityTextView);
		quantityTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alert = new AlertDialog.Builder(RedeemActivity.this);
				alert.setMessage("Please input the quantity number!");
				// Set an EditText view to get user input 
				final EditText input = new EditText(RedeemActivity.this);
				input.setInputType(InputType.TYPE_CLASS_NUMBER);
				alert.setView(input);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int whichButton) {
						try {
							String value = input.getText().toString();
							quantityNumber = Integer.parseInt(value);
							quantityTextView.setText(value);
							Double subTotalValue = Double.parseDouble(DataManager.selectedCoupon.get("original_redeem_value"));
							totalTitleTextView.setText("" + subTotalValue * quantityNumber);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});
				alert.show();
			}
		});
		quantityTitleTextView = (TextView)findViewById(R.id.QuantityTitleTextView);
		subTotalTextView = (TextView)findViewById(R.id.SubTotalTextView);
		subTotalTitleTextView = (TextView)findViewById(R.id.SubTotalTitleTextView);
		totalTextView = (TextView)findViewById(R.id.TotalTextView);
		totalTitleTextView = (TextView)findViewById(R.id.TotalTitleTextView);
		redeemButton = (Button)findViewById(R.id.RedeemButton);		
		
		couponOptions = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.mask)
		.showImageForEmptyUri(R.drawable.mask)
		.showImageOnFail(R.drawable.mask)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.build();
		
		if (DataManager.languageType == 1) {
						
			sciTextView.setText("Selected Coupon Information:");
			rdTextView.setText("Redeem Detail");
			quantityTitleTextView.setText("Quantity");
			subTotalTextView.setText("Sub-Total");
			totalTitleTextView.setText("Total");
			redeemButton.setText("Redeem");			
			language = "EN";
			
		} else if (DataManager.languageType == 2) {
			
			sciTextView.setText("已選優惠券資訊:");
			rdTextView.setText("換購詳情");
			quantityTitleTextView.setText("數量");
			subTotalTextView.setText("小計");
			totalTitleTextView.setText("總計");
			redeemButton.setText("換購");
			language = "ZH-HK";
			
		} else if (DataManager.languageType == 3) {
			
			sciTextView.setText("已選優惠券資訊:");
			rdTextView.setText("換購詳情");
			quantityTitleTextView.setText("數量");
			subTotalTextView.setText("小計");
			totalTitleTextView.setText("總計");
			redeemButton.setText("換購");
			language = "ZH-CN";
		}
		
		quantityNumber = 1;
		subTotalTextView.setText(DataManager.selectedCoupon.get("original_redeem_value"));
		totalTextView.setText(DataManager.selectedCoupon.get("original_redeem_value"));
		ImageLoader.getInstance().displayImage(DataManager.selectedCoupon.get("img_large_path"), couponImageView, couponOptions);
	}
	
	public void onClickBack(View v) {
		finish();
	}
	
	public void onClickRedeem(View v) {
		
		AlertDialog.Builder alert = new AlertDialog.Builder(RedeemActivity.this);
		alert.setTitle("Please input the password!");
		// Set an EditText view to get user input 
		final EditText input = new EditText(RedeemActivity.this);
		input.setTransformationMethod(PasswordTransformationMethod.getInstance());
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int whichButton) {
				try {
					password = input.getText().toString();					
					if (password.length() > 0) {
						redeem();
					} else {
						runOnUiThread(new Runnable() {
							public void run() {
								needShowAlert("Please input the password!");
							}
						});
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
			}
		});
		alert.show();
	}
	
	public void redeem() {
		mProgressHUD = ProgressHUD.show( RedeemActivity.this, "", true, false, null);		
		new Thread( new Runnable() {			
			@Override
			public void run() {

				String parameters = "<root><session_id>" + DataManager.session_id + "</session_id>" + "<username>" + DataManager.username + "</username>" + "<password>" + password + "</password>" + "<product_code>" + DataManager.selectedCoupon.get("product_code") + "</product_code>"  + "<qty>" + quantityNumber + "</qty>" + "</root>";
				SoapObject request = new SoapObject("http://103.9.244.6/soap/login", "redeem_coupon ");
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
				              } else if (name.equalsIgnoreCase("message")) {
				            	  message = parser.nextText();
				              }
				          } else if(eventType == XmlPullParser.END_TAG) {
				              System.out.println("End tag "+ parser.getName());
				          } 
				          eventType = parser.next();
				    }
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				runOnUiThread( new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mProgressHUD.dismiss();
						if (status_code.equalsIgnoreCase("0")) {
							AlertDialog.Builder builder = new AlertDialog.Builder(RedeemActivity.this);
					        builder.setTitle("Hotel-Icon");
					        builder.setMessage(message);
					        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									finish();
								}
							});
					        builder.show();
						} else {
							needShowAlert(message);
						}
					}
				});
			}
		}).start();
	}
	
	public void needShowAlert(final String text) {
        if (text == null ) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(RedeemActivity.this);
        builder.setTitle("Hotel-Icon");
        builder.setMessage(text);
        builder.setPositiveButton("Ok", null);
        builder.show().setCanceledOnTouchOutside(true);
    }
}
