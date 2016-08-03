package com.tongtong.tonglib.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.tongtong.tonglib.util.Util;

/**
 * Created by KOHeeJin on 2016-08-02.
 */
public class SharedDataManager {

    private volatile static SharedDataManager instance = null;

    private static Context mContext = null;
    private static SharedPreferences readPreference = null;
    private static SharedPreferences writePreference = null;
    private static SharedPreferences.Editor editor = null;
    private static String sharedDataName;


    private SharedDataManager(Context context) {
        if (mContext == null)
        {
            mContext = context;

            Util.setContext(context);
            sharedDataName = Util.getPackageName();

            writePreference = context.getSharedPreferences(sharedDataName, Context.MODE_PRIVATE);
            readPreference = context.getSharedPreferences(sharedDataName, Context.MODE_PRIVATE);
            editor = writePreference.edit();
        }
    }

    /*
    public static synchronized SharedDataManager getInstance(Context context){
        if(null == instance){
            //Always pass in the Application Context
            // ApplicationContext을 참조로 걸게 되면 Activity또는 Service의 생명주기에 영향을 받지 않고 안전하게 참조할수 있다.
            // ApplicationContext는 singletone자제이기때문에 다른 정적 참조를 하여도 메모리누수가 되지 않는다
            instance = new SharedDataManager(context.getApplicationContext());
        }

        return instance;
    }
    */

    // DCL(duble0checking locking ) 사용
    public static SharedDataManager getInstance(Context context){
        if(null == instance){
            synchronized(SharedDataManager.class) {
                 if(null == instance){
                    instance = new SharedDataManager(context.getApplicationContext());
                 }
            }
        }

        return instance;
    }

    public void set(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void set(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void set(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void set(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public void set(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public String get(String key, String defValue) {
        try {
            return readPreference.getString(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    public boolean get(String key, boolean defValue) {
        try {
            return readPreference.getBoolean(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    public int get(String key, int defValue) {
        try {
            return readPreference.getInt(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    public float get(String key, float defValue) {
        try {
            return readPreference.getFloat(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    public long get(String key, long defValue) {
        try {
            return readPreference.getLong(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }


}
