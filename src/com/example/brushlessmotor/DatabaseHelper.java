package com.example.brushlessmotor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private String TAG="DataBaseHelper";
	private Context mcontext;
	private String TableItem;
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO �Զ����ɵĹ��캯�����
		Log.i(TAG, "----Constructor DatabaseHelper----");
		mcontext=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO �Զ����ɵķ������
		
	}

}
