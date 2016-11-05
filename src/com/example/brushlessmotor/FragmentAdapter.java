package com.example.brushlessmotor;

import java.util.ArrayList;
import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class FragmentAdapter extends FragmentPagerAdapter {
    private String TAG="FragmentAdapter";
    private List<Fragment> mFragmentList=new ArrayList<Fragment>();
	public FragmentAdapter(FragmentManager fm,List<Fragment> fragmentlist) {
		super(fm);
		
		this.mFragmentList=fragmentlist;
	}

	@Override
	public Fragment getItem(int position) {
		// TODO 自动生成的方法存根
		Log.i(TAG, "----Fragment_getItem()----");
		return mFragmentList.get(position);
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		Log.i(TAG, "----Fragment_getCount()----");
		return mFragmentList.size();
	}

}
