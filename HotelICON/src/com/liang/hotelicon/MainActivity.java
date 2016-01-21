package com.liang.hotelicon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.liang.model.DataManager;
import com.liang.model.Decompress;
import com.liang.model.ProgressHUD;

public class MainActivity extends SlidingFragmentActivity implements OnClosedListener {

	Fragment 			mFrag;
	ProgressHUD			mProgressHUD;
	
	DataManager			dm;
	public ArrayList<HashMap<String, String>> mAssetList = new ArrayList<HashMap<String, String>>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dm = new DataManager();
		DataManager.mainActivity = this;
		
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
            FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
            mFrag = new MenuViewFragment();
            t.replace(R.id.menu_frame, mFrag);
            t.commit();
        } else {
            mFrag = (Fragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
        }
		
		// customize the SlidingMenu
        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeDegree(0.35f);
        sm.setMode(SlidingMenu.RIGHT);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        sm.setOnClosedListener(this);
        
        SharedPreferences prefs = getSharedPreferences("assets", MODE_PRIVATE); 
        if(!prefs.getBoolean("save", false)) {
        	new LoadAssetDataTask().execute();
		} else {
			fragmentReplace(0);
		}        
        
        DataManager.languageType = 1;
	}
	
	public void fragmentReplace(int reqNewFragmentIndex) {
	        
	    Fragment newFragment = null;
	    newFragment = getFragment(reqNewFragmentIndex);  
	    final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();  
	    transaction.replace(R.id.fragment_mainContainer, newFragment);  
	    getSlidingMenu().showContent();
	    transaction.addToBackStack(null) ;
	    transaction.commit();
	}
	
	private Fragment getFragment(int idx) {
        Fragment newFragment = null;  
        switch (idx) {
        case 0:        	
        	newFragment = new HomeFragment();        	
            break;     
        case 1:        	
        	newFragment = new AboutHotelFragment();        	
            break; 
        case 2:        	
        	newFragment = new RoomFragment();        	
            break; 
        case 3:        	
        	newFragment = new AboveAndBeyongFragment();        	
            break; 
        case 4:        	
        	newFragment = new DiningFragment();        	
            break;
        case 5:        	
        	newFragment = new FacilitiesFragment();        	
            break;
        case 6:
        	newFragment = new EventFragment();
        	break;
        case 7:
        	newFragment = new OurCityFragment();
        	break;
        case 8:
        	newFragment = new OfferFragment();
        	break;
        case 9:
        	newFragment = new LocationContactFragment();
        	break;
        case 10:
        	newFragment = new PressFragment();
        	break;
        case 11:
        	newFragment = new CarrerEducationFragment();
        	break;
        case 12:
        	newFragment = new PrivacyFragment();
        	break;
        case 13:
        	newFragment = new MemberProfileFragment();
        	break;
        case 14:
        	newFragment = new RedeemCouponFragment();
        	break;
        case 15:
        	newFragment = new MyCouponFragment();
        	break;
        case 16:
        	newFragment = new PromotionFragment();
        	break;
        case 18:
        	newFragment = new MemberLoginFragment();
        	break;
        }  
        return newFragment;
    }
	 
	@Override
	public void onClosed() {
		// TODO Auto-generated method stub
		
	}
	
	private class LoadAssetDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressHUD = ProgressHUD.show(MainActivity.this, "unpacking...", true,false,null);		
		}
		
		@Override
		protected Void doInBackground(Void... paramVarArgs) {
			String[] files = null;
			try {
				files = getAssets().list("Files");
				for (String filename : files) {
					Log.i("", filename);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			InputStream in = null;
			OutputStream out = null;
			try {
				in = getAssets().open("Files/" + "files.zip");

				File outFile = new File(getExternalFilesDir(null), "files.zip");
				out = new FileOutputStream(outFile);
				copyFile(in, out);
				in.close();
				in = null;
				if (out != null) {
					out.flush();
					out.close();
					out = null;
				}
				String unzipLocation = getExternalFilesDir(null).getPath() + "/"; 
		        Decompress d = new Decompress(outFile.getPath(), unzipLocation); 
		        d.doUnzip(outFile.getPath(), unzipLocation);
		        
		        SharedPreferences.Editor editor = getSharedPreferences("assets", MODE_PRIVATE).edit();
		        editor.putBoolean("save", true);
		        editor.commit();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void a) {
			mProgressHUD.dismiss();
			fragmentReplace(0);
		}
	}
	
	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}	
}
