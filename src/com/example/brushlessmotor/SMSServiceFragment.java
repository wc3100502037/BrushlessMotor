package com.example.brushlessmotor;

import java.util.List;
import java.util.zip.Inflater;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SMSServiceFragment extends Fragment{
    private String TAG="SMSServiceFragment";
    private EditText phoneNum,textContent;
    private Button sendSMBtn;
    private String phoneNumString=null,textConString=null;
    private List<String> divideContent;
    private SmsManager smsManger;
    private View SMSFragmentView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreateView(inflater, container, savedInstanceState);
		Log.i(TAG, "----onCreateView()----");
	    SMSFragmentView=inflater.inflate(R.layout.sms_tab,container,false);
	    phoneNum=(EditText) SMSFragmentView.findViewById(R.id.phoneNum);
	    textContent=(EditText) SMSFragmentView.findViewById(R.id.textCon);
        sendSMBtn=(Button) SMSFragmentView.findViewById(R.id.send);
        smsManger=SmsManager.getDefault();
 
        sendSMBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				 phoneNumString=phoneNum.getText().toString();
			     textConString=textContent.getText().toString();
			  
		         divideContent=smsManger.divideMessage(textConString);
			     for(String text:divideContent)
			     {smsManger.sendTextMessage(phoneNumString, null,text,null,null);}	
			}
		});
		    return SMSFragmentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, "----onActivityView()----");
	}

}
