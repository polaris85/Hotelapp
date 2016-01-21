package com.liang.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liang.hotelicon.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CouponListAdapter extends BaseAdapter {

	Activity 							act;
	ArrayList<HashMap<String, String>> 	couponList;	
	LayoutInflater						inflater;
	DisplayImageOptions 				couponOptions;
	int 								languageType;
	
	public CouponListAdapter() {
		
	}
	
	public CouponListAdapter(Activity act, ArrayList<HashMap<String, String>> 	couponList, int languageType) {
		this.act = act;
		this.couponList = couponList;
		inflater = (LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.languageType = languageType;
		
		couponOptions = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.mask)
		.showImageForEmptyUri(R.drawable.mask)
		.showImageOnFail(R.drawable.mask)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.build();
	}
	
	@Override
	public int getCount() {
		return couponList.size();
	}
	
	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View vi = convertView;		
		ViewHolder holder;
        
        if(convertView == null){        	
        	holder = new ViewHolder();
        	vi = inflater.inflate(R.layout.row_coupon, null);
        	TextView brTitleTextView = (TextView)vi.findViewById(R.id.BRTitleTextView);
        	TextView opTitleTextView = (TextView)vi.findViewById(R.id.OPTitleTextView);
        	TextView aqTitleTextView = (TextView)vi.findViewById(R.id.AQTitleTextView);
        	
        	if (languageType == 1) {
				brTitleTextView.setText("Bonus Required:");
				opTitleTextView.setText("Original Price:");
				aqTitleTextView.setText("Available Quantity:");
			} else {
				brTitleTextView.setText("需要積分:");
				opTitleTextView.setText("原價:");
				aqTitleTextView.setText("剩餘數量:");
			}
        	
        	holder.couponImageView = (ImageView)vi.findViewById(R.id.couponImageView);
        	holder.nameTextView = (TextView)vi.findViewById(R.id.NameTextView);
        	holder.brTextView = (TextView)vi.findViewById(R.id.BonusRequiredTextView);
        	holder.opTextView = (TextView)vi.findViewById(R.id.OriginalPriceTextView);
        	holder.aqTextView = (TextView)vi.findViewById(R.id.AQTextView);
        	vi.setTag( holder);
        } else {
        	 holder=(ViewHolder)vi.getTag();
        }
        
		HashMap<String, String> map = couponList.get(position);
		holder.nameTextView.setText(map.get("name"));
		holder.brTextView.setText(map.get("original_redeem_value"));
		holder.opTextView.setText(map.get("original_price"));
		holder.aqTextView.setText(map.get("available_qty"));
		ImageLoader.getInstance().displayImage(map.get("img_small_path"), holder.couponImageView, couponOptions);
		
		return vi;
	}
	
	public static class ViewHolder{        
        public ImageView	couponImageView;
        public TextView		nameTextView;
        public TextView 	brTextView;
        public TextView		opTextView;
        public TextView		aqTextView;
    }	
}
