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

public class PromotionListAdapter extends BaseAdapter {

	Activity 							act;
	ArrayList<HashMap<String, String>> 	couponList;	
	LayoutInflater						inflater;
	DisplayImageOptions 				couponOptions;
	int 								languageType;
	
	public PromotionListAdapter() {
		
	}
	
	public PromotionListAdapter(Activity act, ArrayList<HashMap<String, String>> 	couponList, int languageType) {
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
        	vi = inflater.inflate(R.layout.row_promotion, null);
        	holder.couponImageView = (ImageView)vi.findViewById(R.id.couponImageView);
        	holder.nameTextView = (TextView)vi.findViewById(R.id.NameTextView);
        	vi.setTag( holder);
        } else {
        	 holder=(ViewHolder)vi.getTag();
        }
        
		HashMap<String, String> map = couponList.get(position);
		holder.nameTextView.setText(map.get("name"));
		ImageLoader.getInstance().displayImage(map.get("img_path"), holder.couponImageView, couponOptions);
		return vi;
	}
	
	public static class ViewHolder{
        public ImageView	couponImageView;
        public TextView		nameTextView;
    }	
}
