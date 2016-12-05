/*
 This App serves as the main App of the whole system,which include the wireless communication by
 small message and socket.
 One can slip the screen to switch the activity.The main function focus on sending control instructor
 of motor and receiving relevant information.
 Using the module of Fragment,SMS,Socket.
*/
package com.example.brushlessmotor;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
public class MainActivity extends FragmentActivity {
  
	private ArrayList<Fragment> mfragmentList=new ArrayList<Fragment>();
	private FragmentAdapter mfragmentadapter;
	private ViewPager mViewPager;
	private TextView sms_Tab,socket_Tab,dispInfo_Tab;
	private SMSServiceFragment mSMSFragment;
	private SocketServiceFragment mSocketFragment;
	private DispInfoFragment mDispInfoFragment;
	private int currentIndex;
	private int screenWidth;
	private ImageView tab_line;
	
	private String TAG="MainActivity";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "----onCreate----");
        findConponentById();
        init();
        initTabLineWidth();
    
    }

    private void findConponentById()
    {   Log.i(TAG, "----findConponenbyID()----");
    	dispInfo_Tab=(TextView) findViewById(R.id.display_tab);
    	socket_Tab=(TextView) findViewById(R.id.socket_tab);
    	sms_Tab=(TextView) findViewById(R.id.sms_tab);
    	mViewPager=(ViewPager) findViewById(R.id.viewpager);   	
    	tab_line=(ImageView) findViewById(R.id.tab_line);
    }
    
    private void init()
    {Log.i(TAG, "----init()----");
     final String TAG_init_ViewPagerListener="--ViewPagerListener--";
     mSMSFragment=new SMSServiceFragment();
     mSocketFragment=new SocketServiceFragment();
     mDispInfoFragment=new DispInfoFragment();
     mfragmentList.add(mDispInfoFragment);
     mfragmentList.add(mSMSFragment);
     mfragmentList.add(mSocketFragment);
     mfragmentadapter=new FragmentAdapter(this.getSupportFragmentManager(),mfragmentList);
     mViewPager.setAdapter(mfragmentadapter);
     mViewPager.setCurrentItem(0);
     mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO 自动生成的方法存根
			Log.i(TAG+TAG_init_ViewPagerListener, "----onPageSelected----");	
			switch(position)
			{
			case 0:
				resetTabTextColor();
				dispInfo_Tab.setTextColor(Color.RED);
			    break;
			case 1:
				resetTabTextColor();
				sms_Tab.setTextColor(Color.RED);
				break;
			case 2:
				resetTabTextColor();
				socket_Tab.setTextColor(Color.RED);
				break;    
			}
			
			}
			
			@Override
			public void onPageScrolled(int position, float offset, int offsetPixels) {
				// TODO 自动生成的方法存根
		    Log.i(TAG+TAG_init_ViewPagerListener, "----onPageScrolled----");	
		    LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams) tab_line.getLayoutParams();
		    Log.i("---offset---",offset+"");
		 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		    if (currentIndex == 0 && position == 0)// 0->1
			{
				lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
						* (screenWidth / 3));

			} else if (currentIndex == 1 && position == 0) // 1->0
			{
				lp.leftMargin = (int) (-(1 - offset)
						* (screenWidth * 1.0 / 3) + currentIndex
						* (screenWidth / 3));

			} else if (currentIndex == 1 && position == 1) // 1->2
			{
				lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
						* (screenWidth / 3));
			} else if (currentIndex == 2 && position == 1) // 2->1
			{
				lp.leftMargin = (int) (-(1 - offset)
						* (screenWidth * 1.0 / 3) + currentIndex
						* (screenWidth / 3));
			}
		    
		    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////		    
		    tab_line.setLayoutParams(lp);
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO 自动生成的方法存根
		    Log.i(TAG+TAG_init_ViewPagerListener, "----onPageScrollStateChanged----");
			}
		}); 	
    }
    private void initTabLineWidth()
    {   Log.i(TAG, "----initTabLineWidth()----");
    	DisplayMetrics dp=new DisplayMetrics();
    	getWindow().getWindowManager().getDefaultDisplay().getMetrics(dp);
    	screenWidth=dp.widthPixels;
    	LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams) tab_line.getLayoutParams();
    	lp.weight=screenWidth/3;
    	tab_line.setLayoutParams(lp); 	
    }
    private void resetTabTextColor()
    {
    	sms_Tab.setTextColor(Color.BLACK);
    	socket_Tab.setTextColor(Color.BLACK);
    	dispInfo_Tab.setTextColor(Color.BLACK); 	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        menu.add(0,Menu.FIRST,1,"设置默认号码").setIcon(R.drawable.ic_launcher);
        menu.add(0,Menu.FIRST+1,1,"设置默认IP").setIcon(R.drawable.ic_launcher);
        menu.add(0,Menu.FIRST+2,1,"查看历史记录").setIcon(R.drawable.ic_launcher);
    	return true;
    }
   

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO 自动生成的方法存根
		switch(item.getItemId())
		{
		case Menu.FIRST:
			Menu_setPhoneNum();
			break;
		case Menu.FIRST+1:
			break;
		case Menu.FIRST+2:
			break;
		
		}
		return false;
	}
	/*菜单选项，设置号码*/
	public void Menu_setPhoneNum()
	{
		Intent intent=new Intent(MainActivity.this,customPhoneNum.class);
		startActivity(intent);		
	}
	/**/
	public void Menu_setIPAddress()
	{
		
	}
	/**/
	public void Menu_viewPreConstructor()
	{
		
	}
    
}
