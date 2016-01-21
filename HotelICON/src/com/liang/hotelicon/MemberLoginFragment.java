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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.liang.model.DataManager;
import com.liang.model.ProgressHUD;

public class MemberLoginFragment extends Fragment {	
	
	TextView titleTextView;
	EditText memberEditText;
	EditText passwordEditText;
	CheckBox rememberCheckBox;
	
	Button menuButton;
	Button loginButton;
	Button forgotButton;
	
	ProgressHUD	mProgressHUD;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_member_login, container, false);
		
		titleTextView = (TextView)v.findViewById(R.id.TitleTextView);
		memberEditText = (EditText)v.findViewById(R.id.MemberEditText);
		passwordEditText = (EditText)v.findViewById(R.id.PasswordEditText);		
		rememberCheckBox = (CheckBox)v.findViewById(R.id.RemberCheckBox);
		
		SharedPreferences preference = getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
		if (preference.getString("email", "").length() > 0 ) {
			memberEditText.setText(preference.getString("email", ""));
			passwordEditText.setText(preference.getString("password", ""));
			rememberCheckBox.setChecked(true);
		}
				
		menuButton = (Button)v.findViewById(R.id.MenuButton);
		menuButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DataManager.mainActivity.showMenu();
			}
		});
		
		loginButton = (Button)v.findViewById(R.id.LoginButton);
		loginButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (memberEditText.getText().toString().length() == 0) {
					if (DataManager.languageType == 1) {
						needShowAlert("Please input the member number / e-mail!");
					} else if (DataManager.languageType == 2) {
						needShowAlert("Please input the member number / e-mail!");
					} else if (DataManager.languageType == 3) {
						needShowAlert("Please input the member number / e-mail!");
					}
					memberEditText.requestFocus();
				} else if (passwordEditText.getText().toString().length() == 0) {
					if (DataManager.languageType == 1) {
						needShowAlert("Please input the password!");
					} else if (DataManager.languageType == 2) {
						needShowAlert("Please input the password!");
					} else if (DataManager.languageType == 3) {
						needShowAlert("Please input the password!");
					}
					passwordEditText.requestFocus();
				} else {
					mProgressHUD = ProgressHUD.show(getActivity(), "", true, false, null);
					new Thread( new Runnable() {
						
						@Override
						public void run() {

							String parameters = "<root><user>" + memberEditText.getText().toString() + "</user><password>" + passwordEditText.getText().toString() + "</password><ip>127.0.0.1</ip></root>";
							SoapObject request = new SoapObject("http://103.9.244.6/soap/login", "login");
							request.addProperty("parameters", parameters);
							
							SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
							envelope.setOutputSoapObject(request);
							HttpTransportSE httpTransport = new HttpTransportSE("http://103.9.244.6/HotelCRM/webservice.php?wsdl");
							httpTransport.debug = true;
							
							try {
								httpTransport.call("http://103.9.244.6/HotelCRM/webservice.php/login", envelope);
								SoapObject result  = (SoapObject)envelope.bodyIn;
								String status_code = "";
								String session_id = "";
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
							              } else if ( name.equalsIgnoreCase("session_id")) {
							            	  session_id = parser.nextText();
							              }
							          } else if(eventType == XmlPullParser.END_TAG) {
							              System.out.println("End tag "+ parser.getName());
							          } 
							          eventType = parser.next();
							    }
								if (status_code.equalsIgnoreCase("0")) {
									DataManager.session_id = session_id;						
								} else {
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
									if (DataManager.session_id.length() > 0) {
										SharedPreferences preference = getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
										SharedPreferences.Editor editor = preference.edit();
										if (rememberCheckBox.isChecked()) {
											editor.putString("email", memberEditText.getText().toString());
											editor.putString("password", passwordEditText.getText().toString());
										} else {
											editor.putString("email", "");
											editor.putString("password", "");
										}editor.commit();
										
										DataManager.loginFlag = true;
										DataManager.username = memberEditText.getText().toString();
										DataManager.password = passwordEditText.getText().toString();
								    	DataManager.menuViewFragment.refreshMenu();
								    	DataManager.mainActivity.fragmentReplace(13);
									} else {
										needShowAlert("Login failed. User name or password is incorrect!.");
									}
								}
							});
						}
					}).start();
				}
			}
		});
		
		forgotButton = (Button)v.findViewById(R.id.ForgotButton);
		forgotButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
				
				String okButtonTitle = null;
				String cancelButtonTitle = null;
				if (DataManager.languageType == 1) {
					alert.setTitle("Hotel- Icon");
					alert.setMessage("Forget Password ?");
					okButtonTitle = "Forgot";
					cancelButtonTitle = "Cancel";
				} else if (DataManager.languageType == 2) {
					alert.setTitle("Hotel- Icon");
					alert.setMessage("忘記密碼 ?");
					okButtonTitle = "忘記";
					cancelButtonTitle = "取消";
				} else if (DataManager.languageType == 3) {
					alert.setTitle("Hotel- Icon");
					alert.setMessage("忘記密碼 ?");
					okButtonTitle = "忘記";
					cancelButtonTitle = "取消";
				}
				
				// Set an EditText view to get user input 
				final EditText input = new EditText(getActivity());
				alert.setView(input);
								
				alert.setPositiveButton( okButtonTitle, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
//						String value = input.getText().toString();
						dialog.dismiss();
						mProgressHUD = ProgressHUD.show(getActivity(), "", true, false, null);
						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
						    @Override
						    public void run() {						    	
						    	mProgressHUD.dismiss();
						    }
						}, 2500);
				  	}
				});

				alert.setNegativeButton( cancelButtonTitle, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});

				alert.show();
			}
		});
		
		if (DataManager.languageType == 1) {
			titleTextView.setText("LOG IN");
			memberEditText.setHint("Member Number / E-mail");
			passwordEditText.setHint("Password");
			rememberCheckBox.setText("Remember my password");
			loginButton.setText("Login");
			forgotButton.setText("Forgot password ?");
		} else if (DataManager.languageType == 2) {
			titleTextView.setText("登入");
			memberEditText.setHint("會員號碼 / 電郵");
			passwordEditText.setHint("密碼");
			rememberCheckBox.setText("記住我的密碼");
			loginButton.setText("登入");
			forgotButton.setText("忘記密碼 ?");
		} else if (DataManager.languageType == 3) {
			titleTextView.setText("登入");
			memberEditText.setHint("会員號碼 / 電郵");
			passwordEditText.setHint("密碼");
			rememberCheckBox.setText("記住我的密碼");
			loginButton.setText("登入");
			forgotButton.setText("忘記密碼 ?");
		}
		
		return v;
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


