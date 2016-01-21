package com.liang.hotelicon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.liang.model.DataManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyCouponDetailActivity extends Activity {

	ImageView		couponImageView;
	TextView		nameTextView;
	TextView		statusTitleTextView;
	TextView		statusTextView;
	TextView		couponNoTitleTextView;
	TextView		couponNoTextView;
	TextView		descriptionTitleTextView;
	TextView		descriptionTextView;
	TextView		expiryDateTitleTextView;
	TextView		expiryDateTextView;
	TextView		redeemedShopTitleTextView;
	TextView		redeemedShopTextView;
	TextView		redeemedDateTitleTextView;
	TextView		redeemedDateTextView;
	Button			qrcodeButton;
	
	DisplayImageOptions 				couponOptions;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mycoupon_detail);
		
		couponImageView = (ImageView)findViewById(R.id.CouponImageView);
		nameTextView = (TextView)findViewById(R.id.NameTextView);
		statusTitleTextView = (TextView)findViewById(R.id.StatusTitleTextView);
		statusTextView = (TextView)findViewById(R.id.StatusTextView);
		couponNoTitleTextView = (TextView)findViewById(R.id.CouponNoTitleTextView);
		couponNoTextView = (TextView)findViewById(R.id.CouponNoTextView);	
		descriptionTitleTextView = (TextView)findViewById(R.id.DescriptionTitleTextView);
		descriptionTextView = (TextView)findViewById(R.id.DescriptionTextView);
		expiryDateTitleTextView = (TextView)findViewById(R.id.EDTitleTextView);
		expiryDateTextView = (TextView)findViewById(R.id.EDTextView);
		redeemedShopTitleTextView = (TextView)findViewById(R.id.RSTitleTextView);
		redeemedShopTextView =  (TextView)findViewById(R.id.RSTextView);
		redeemedDateTitleTextView = (TextView)findViewById(R.id.RDTitleTextView);
		redeemedDateTextView = (TextView)findViewById(R.id.RDTextView);
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
			
			statusTitleTextView.setText("Status:");
			couponNoTitleTextView.setText("Coupon No:");
			descriptionTitleTextView.setText("Description");
			expiryDateTitleTextView.setText("Expiry Date:");
			qrcodeButton.setText("QR Code");
			
		} else if (DataManager.languageType == 2) {
			
			statusTitleTextView.setText("狀態:");
			couponNoTitleTextView.setText("優惠券號碼:");
			descriptionTitleTextView.setText("內容");
			expiryDateTitleTextView.setText("有效期:");
			qrcodeButton.setText("QR 碼:");
			
		} else if (DataManager.languageType == 3) {
			
			statusTitleTextView.setText("狀態:");
			couponNoTitleTextView.setText("優惠券號碼:");
			descriptionTitleTextView.setText("內容");
			expiryDateTitleTextView.setText("有效期:");
			qrcodeButton.setText("QR碼:");
		}
		
		nameTextView.setText(DataManager.selectedCoupon.get("name"));
		statusTextView.setText(DataManager.selectedCoupon.get("status"));
		couponNoTextView.setText(DataManager.selectedCoupon.get("coupon_number"));
		descriptionTextView.setText(DataManager.selectedCoupon.get("description"));
		expiryDateTextView.setText(DataManager.selectedCoupon.get("expire_date"));
		redeemedShopTextView.setText(DataManager.selectedCoupon.get("redeem_shop"));
		redeemedDateTextView.setText(DataManager.selectedCoupon.get("redeem_date"));		
		ImageLoader.getInstance().displayImage(DataManager.selectedCoupon.get("img_large_path"), couponImageView, couponOptions);		
	}
	
	public void onClickBack(View v) {
		finish();
	}	
	
	public void onClickQRCode(View v) {
		
	}
}
