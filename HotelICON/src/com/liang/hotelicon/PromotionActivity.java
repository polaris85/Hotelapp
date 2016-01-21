package com.liang.hotelicon;

import java.net.URL;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.liang.model.DataManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PromotionActivity extends Activity {

	ImageView		couponImageView;
	TextView		nameTextView;
	TextView		descriptionTitleTextView;
	TextView		descriptionTextView;
	TextView		expiryDateTitleTextView;
	TextView		expiryDateTextView;
	TextView		effectiveDateTitleTextView;
	TextView		effectiveDateTextView;
	Button			qrcodeButton;
	
	DisplayImageOptions 				couponOptions;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_promotion_detail);
		
		couponImageView = (ImageView)findViewById(R.id.CouponImageView);
		nameTextView = (TextView)findViewById(R.id.NameTextView);		
		descriptionTitleTextView = (TextView)findViewById(R.id.DescriptionTitleTextView);
		descriptionTextView = (TextView)findViewById(R.id.DescriptionTextView);
		expiryDateTitleTextView = (TextView)findViewById(R.id.EDTitleTextView);
		expiryDateTextView = (TextView)findViewById(R.id.EDTextView);
		effectiveDateTitleTextView = (TextView)findViewById(R.id.EFDTitleTextView);
		effectiveDateTextView =  (TextView)findViewById(R.id.EFDTextView);
		qrcodeButton = (Button)findViewById(R.id.QrcodeButton);
		
		couponOptions = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.mask)
		.showImageForEmptyUri(R.drawable.mask)
		.showImageOnFail(R.drawable.mask)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.build();
		
		if (DataManager.languageType == 1) {
			
			descriptionTitleTextView.setText("Description");
			expiryDateTitleTextView.setText("Expiry Date:");
			effectiveDateTitleTextView.setText("Effective Date:");
			qrcodeButton.setText("QR Code");
			
		} else if (DataManager.languageType == 2) {
			
			descriptionTitleTextView.setText("內容");
			effectiveDateTitleTextView.setText("生效日期");
			expiryDateTitleTextView.setText("有效期:");
			qrcodeButton.setText("QR 碼:");
			
		} else if (DataManager.languageType == 3) {
			
			descriptionTitleTextView.setText("內容");
			effectiveDateTitleTextView.setText("生效日期");
			expiryDateTitleTextView.setText("有效期:");
			qrcodeButton.setText("QR碼:");
		}
		
		nameTextView.setText(DataManager.selectedCoupon.get("name"));
		descriptionTextView.setText(DataManager.selectedCoupon.get("description"));
		expiryDateTextView.setText(DataManager.selectedCoupon.get("expiry_date"));
		effectiveDateTextView.setText(DataManager.selectedCoupon.get("effect_date"));
		ImageLoader.getInstance().displayImage(DataManager.selectedCoupon.get("img_large_path"), couponImageView, couponOptions);		
	}
	
	public void onClickBack(View v) {
		finish();
	}	
	
	public void onClickQRCode(View v) {
		final Dialog dialog = new Dialog( PromotionActivity.this );
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.alert_imageview);
		dialog.getWindow().setLayout( 600, 600);
		dialog.setTitle(null);

		// set the custom dialog components - text, image and button			
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ImageView imageView = (ImageView) dialog.findViewById(R.id.QRCodeImageView);
				URL url;
				try {
					url = new URL(DataManager.selectedCoupon.get("qr_code_path"));
					Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
					imageView.setImageBitmap(image);
					dialog.show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				
			}
		}).start();		
	}
}
