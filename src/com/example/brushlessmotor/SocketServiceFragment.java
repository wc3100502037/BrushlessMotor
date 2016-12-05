package com.example.brushlessmotor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SocketServiceFragment extends Fragment {
    private String TAG="SocketServiceFragment";
    
    private EditText ipAdress,portNet;
    private Button connectToServerBtn,cutoffServerBtn,sendDataToClientBtn;
    private EditText sendedText;
    private TextView receiveText;
    private Socket socket=null;
    private Thread connectingSocketThread,recvDataFromSocketThread;
    private BufferedReader mBufferedReader;
    private InputStream myInputStream;
    private PrintStream mPrintStream;
    private Handler mRecvDataHandler;//handle data receiving from message
    private static boolean recvThreadRunningFlag;//control rec thread running or stopping
    private String rcvThreadText;
    private String rcvThreadInt;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreateView(inflater, container, savedInstanceState);
		Log.i(TAG, "----onCreateView()----");
	    View view=inflater.inflate(R.layout.socket_tab,container,false);
	    
	    ipAdress=(EditText) view.findViewById(R.id.ipAdress);
        portNet=(EditText) view.findViewById(R.id.portNet);
        connectToServerBtn=(Button) view.findViewById(R.id.connect);
        cutoffServerBtn=(Button) view.findViewById(R.id.cutoff);
        sendedText=(EditText) view.findViewById(R.id.sendedText);
        receiveText=(TextView) view.findViewById(R.id.receivedText);
        sendDataToClientBtn=(Button) view.findViewById(R.id.sendBtn); 
        recvThreadRunningFlag=true;
        connectToServerBtn.setEnabled(true);
        cutoffServerBtn.setEnabled(false);
	    
        connectToServerBtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
			 String ipadressString=ipAdress.getText().toString();
			 String portNetString=portNet.getText().toString();
			 recvThreadRunningFlag=true;
			// Toast.makeText(SocketServiceFragment.class, "before try"+" "+ipadressString,Toast.LENGTH_SHORT).show();	
			 if((connectingSocketThread.isAlive()==true)||(recvDataFromSocketThread.isAlive()==true))
			 {   
				 
				 recvThreadRunningFlag=false;
			 }
			 connectingSocketThread.start();
			 recvDataFromSocketThread.start(); 
		     Log.i(TAG, "----after try----");	
		    // Toast.makeText(SocketServiceFragment.this, "after try"+" "+ipadressString,Toast.LENGTH_SHORT).show();
			 connectToServerBtn.setEnabled(false);	
			}
		});

      
        sendDataToClientBtn.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View arg0) {
    			// TODO 自动生成的方法存根
    		Log.i(TAG, "----push sendbtn----");	
    		String msg=sendedText.getText().toString();
    		mPrintStream.print(msg);		
    		mPrintStream.flush();
    			
    			
    		}
    	});
         
           
            cutoffServerBtn.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View arg0) {
    				// TODO 自动生成的方法存根
    				try {myInputStream.close();
    				     mPrintStream.close();
    					socket.close();
    					if(connectingSocketThread.isAlive()==true)
    					connectingSocketThread.interrupt();
    					if(recvDataFromSocketThread.isAlive()==true)
    					 recvThreadRunningFlag=false;
    					 connectToServerBtn.setEnabled(true);
    				 	
    				} catch (IOException e) {
    					// TODO 自动生成的 catch 块
    					e.printStackTrace();
    				}
    			}
    		});
 /*         Handling segment,handling the received data using the Handle-Message mechanism                  */           
            mRecvDataHandler=new Handler(){
            public void handleMessage(Message msg)
            {   
            	String LocalTAG=TAG+"_mHandler";
            	byte[] recvData=new byte[20];
            	Log.i(LocalTAG, "----mHandler----");
             	switch(msg.what)
             	{ 
             	   case 1:recvData=msg.getData().getByteArray("content");
             	   Log.i(LocalTAG, "----"+rcvThreadInt+""+"----");
             	   break;
             	   default: break;
             	}
             	displayOriginalReceivedData(recvData,LocalTAG);//display the received handle message to logcat
                receiveText.setText(convertToDisplayedData(recvData,3));	//display received data
            }
          };
          
/******************************************************************************************************/            
        connectingSocketThread=new Thread(new Runnable() {	
    		String ThreadTAG=TAG+" --connectingSocketThread";
        	@Override
    		public void run() {
    			// TODO 自动生成的方法存根
    			//Socket socket=null;
    			Log.i(ThreadTAG, "----connectingSocketThread run----");	
    			String ipadressString=ipAdress.getText().toString();
    			String portNetString=portNet.getText().toString();
    		    try {
    			     Log.i(ThreadTAG, "using try  "+ipadressString+"//"+portNetString);
    			     //socket=new Socket(ipadressString,Integer.parseInt(portNetString));
    			     socket=new Socket(ipadressString,Integer.parseInt(portNetString));
    			     Log.i(ThreadTAG, "----after new socket----");
    			     mPrintStream=new PrintStream(socket.getOutputStream());
    			     Log.i(ThreadTAG, "----after new print++678writer----");
    			     if(socket.getInputStream()!=null){		
    			         //mBufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
    			         myInputStream=socket.getInputStream();
    			                                      }
    			} catch (UnknownHostException e)
    			{
    			  // TODO 自动生成的 catch 块
    			  e.printStackTrace();
    		    } catch (IOException e) 
    		    {
    			  // TODO 自动生成的 catch 块
    			  e.printStackTrace();
    		    }
    		
    		}
    	});
    
 /**********************************************************************************************/
 /*                                           receive Thread                                   */        
            recvDataFromSocketThread=new Thread(new Runnable() {
            	String ThreadTAG=TAG+" --recvDataFromSocketThread";
    			byte[] receivedData=new byte[20];
    			Bundle bundle=new Bundle();
    			//String mess=null;
    			int readMsg;
    			//InputStreamReader inputReader=null;			
    			@Override
    			public void run() 
    	{
    				// TODO 自动生成的方法存根
    			Log.i(ThreadTAG, "----recvDataFromSocketThread run----");
    		    while(recvThreadRunningFlag)
    			{ Message msg=mRecvDataHandler.obtainMessage();	
    			  msg.what=1;		
    			  Log.i(ThreadTAG, "----receiveThread_while()----");
    			  try {
    				if(myInputStream!=null){
    				Log.i(ThreadTAG, "----BufferReader.readLine()----");
    				//inputReader=new InputStreamReader(myInputStream);		
    				if((readMsg=myInputStream.read(receivedData))!=0){
    					//readMsg = myInputStream.read(receivedData);	
    					Log.i("---the length of receivedData is",String.valueOf(readMsg));
    					Log.i(ThreadTAG, "----"+convertToString(receivedData)+"----");
    					//Log.i(ThreadTAG, "byte[1]="+"----"+receivedData[1]+"----");
    					displayOriginalReceivedData(receivedData,ThreadTAG);
    					bundle.putByteArray("content",receivedData);
    					msg.setData(bundle);
    					mRecvDataHandler.sendMessage(msg);	
    				} }}catch (IOException e) {
    				// TODO 自动生成的 catch 块
    				e.printStackTrace();
    			                            }
    			}	
    	}
    		
            });      
     
		return view;
	}
/**********************************************************************************************/
	
	private byte[] convertToString(byte[] data)
	{String LocalTAG=TAG+" --Function_convertToString";
	 int length=data.length;
	 for(int i=0;i<length;i++)
	 {   
		 data[i]=(byte) (data[i]-48);
		 Log.i(LocalTAG,"data["+i+"]"+data[i]+"");
	 }
	  return data;
	}
/**********************************************************************************************/
/*                         To display the original received data                             */
	private void displayOriginalReceivedData(byte[] data,String TAGofUsedFunction) 
	{   String LocalTAG=TAG+TAGofUsedFunction+"_displayOriginalReceivedData";
		int dataLength=data.length;
		for(;dataLength>0;dataLength--)
		{
			Log.i(LocalTAG,data[20-dataLength]+"");
		}
	}
/**************************************************************************************************/
	/*                                  handled to display data                                       */
	/*                                  #pramater:receiving data(byte[])                                        */
    private String convertToDisplayedData(byte[] recvData,int disLength)
    {
    	int dataLength=recvData.length;
    	int indexOfRecvdata=0;
    	String finalDisplayedData=null;
    	if(disLength>dataLength) disLength=dataLength;
    	for(;indexOfRecvdata<disLength;indexOfRecvdata++)
    	{
    		finalDisplayedData=finalDisplayedData+recvData[indexOfRecvdata];
    	}
    	return finalDisplayedData;
    }
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, "----onActivityView()----");
	}

}
