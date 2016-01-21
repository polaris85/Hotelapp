package com.liang.hotelicon;

import com.liang.model.DataManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CouponDetailActivity extends Activity {

	ImageView		couponImageView;
	TextView		nameTextView;
	TextView		brTitleTextView;
	TextView		brTextView;
	TextView		opTitleTextView;
	TextView		opTextView;
	TextView		aqTitleTextView;
	TextView		aqTextView;
	TextView		descriptionTitleTextView;
	TextView		descriptionTextView;
	TextView		expiryInfoTitleTextView;
	TextView		expiryInfoTextView;
	Button			redeemButton;
	
	DisplayImageOptions 				couponOptions;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon_detail);
		
		couponImageView = (ImageView)findViewById(R.id.CouponImageView);
		nameTextView = (TextView)findViewById(R.id.NameTextView);
		brTitleTextView = (TextView)findViewById(R.id.BonusRequiredTitleTextView);
		brTextView = (TextView)findViewById(R.id.BonusRequiredTextView);
		opTitleTextView = (TextView)findViewById(R.id.OriginalPriceTitleTextView);
		opTextView = (TextView)findViewById(R.id.OriginalPriceTextView);
		aqTitleTextView = (TextView)findViewById(R.id.AQTitleTextView);
		aqTextView = (TextView)findViewById(R.id.AQTextView);		
		descriptionTitleTextView = (TextView)findViewById(R.id.DescriptionTitleTextView);
		descriptionTextView = (TextView)findViewById(R.id.DescriptionTextView);
		expiryInfoTitleTextView = (TextView)findViewById(R.id.EITitleTextView);
		expiryInfoTextView = (TextView)findViewById(R.id.EITextView);
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
						
			brTitleTextView.setText("Bonus Required:");
			opTitleTextView.setText("Original Price:");
			aqTitleTextView.setText("Available Quantity:");
			descriptionTitleTextView.setText("Description:");
			expiryInfoTitleTextView.setText("Expiry Information:");
			redeemButton.setText("Redeem");
			
		} else if (DataManager.languageType == 2) {
			
			brTitleTextView.setText("需要積分:");
			opTitleTextView.setText("原價:");
			aqTitleTextView.setText("剩餘數量:");
			descriptionTitleTextView.setText("內容:");
			expiryInfoTitleTextView.setText("有效期資訊:");
			redeemButton.setText("換購");
			
		} else if (DataManager.languageType == 3) {
			
			brTitleTextView.setText("需要積分:");
			opTitleTextView.setText("原價:");
			aqTitleTextView.setText("剩餘數量:");
			descriptionTitleTextView.setText("內容:");
			expiryInfoTitleTextView.setText("有效期資訊:");
			redeemButton.setText("換購");
		}
		
		nameTextView.setText(DataManager.selectedCoupon.get("name"));
		brTextView.setText(DataManager.selectedCoupon.get("original_redeem_value"));
		opTextView.setText(DataManager.selectedCoupon.get("original_price"));
		aqTextView.setText(DataManager.selectedCoupon.get("available_qty"));
		descriptionTextView.setText(DataManager.selectedCoupon.get("description"));
		expiryInfoTextView.setText(DataManager.selectedCoupon.get("expiry_info"));
		ImageLoader.getInstance().displayImage(DataManager.selectedCoupon.get("img_large_path"), couponImageView, couponOptions);
		
	}
	
	public void onClickBack(View v) {
		finish();
	}	
	
	public void onClickRedeem(View v) {
		Intent newIntent = new Intent(CouponDetailActivity.this, RedeemActivity.class);
		startActivity(newIntent);
		finish();
	}
}
