package com.example.brushlessmotor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SocketServiceFragment extends Fragment {
    private String TAG="SocketServiceFragment";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreateView(inflater, container, savedInstanceState);
		Log.i(TAG, "----onCreateView()----");
	    View view=inflater.inflate(R.layout.socket_tab,container,false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, "----onActivityView()----");
	}

}
