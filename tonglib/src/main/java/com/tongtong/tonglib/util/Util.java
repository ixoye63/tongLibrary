package com.tongtong.tonglib.util;


import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Build;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by KOHeeJin on 2016-06-21.
 */
public class Util {
    private static Context mContext = null;
    private static TelephonyManager mTelephonyManager = null;
    private static PackageManager mPackageManager = null;
    private static Typeface typeface = null;

    public Util(Context context)
    {
        mContext = context;
        mTelephonyManager = (TelephonyManager) mContext.getSystemService(mContext.TELEPHONY_SERVICE);
        mPackageManager = mContext.getPackageManager();
    }


    public static void setContext(Context context)
    {
        if (mContext == null)
        {
            mContext = context;
            mTelephonyManager = (TelephonyManager) mContext.getSystemService(mContext.TELEPHONY_SERVICE);
            mPackageManager = mContext.getPackageManager();
        }
    }


    //----------------------------------------------
    // Device 정보
    //----------------------------------------------
    public static boolean isCallingState()
    {
        return (mTelephonyManager.getCallState() == TelephonyManager.CALL_STATE_IDLE ? false : true);
    }

    public static String getDeviceId()
    {
        return nullProcess(mTelephonyManager.getDeviceId(), "---");
    }

    public static String getSubscriberId()
    {
        return nullProcess(mTelephonyManager.getSubscriberId(), "---");
    }

    public static String getLine1Number()
    {
        return nullProcess(mTelephonyManager.getLine1Number(), "---");
    }

    public static String getNetworkOperator()
    {
        return nullProcess(mTelephonyManager.getNetworkOperator(), "---");
    }

    public static String getNetworkOperatorName()
    {
        return nullProcess(mTelephonyManager.getNetworkOperatorName(), "---");
    }

    public static String getSimSerialNumber()
    {
        return nullProcess(mTelephonyManager.getSimSerialNumber(), "---");
    }

    public static String getAndroidId()
    {
        return nullProcess(Settings.Secure.getString(mContext.getContentResolver(),	Settings.Secure.ANDROID_ID), "---");
    }

    public static String getDeviceSoftwareVersion()
    {
        return nullProcess(mTelephonyManager.getDeviceSoftwareVersion(), "---");
    }

    public static String getDeviceManufacturer()
    {
        return Build.MANUFACTURER;
    }

    public static String getDeviceModel()
    {
        return Build.MODEL;
    }

    public static String getDeviceVersion()
    {
        return Build.VERSION.RELEASE;
    }

    public static String getDeviceSdkVersion()
    {
        return Build.VERSION.SDK;
    }

    public static String GetDevicesUUID()
    {
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + getDeviceId();
        tmSerial = "" + getSimSerialNumber();
        androidId = "" + getAndroidId();

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();

        return nullProcess(deviceId, "---");
    }


    public static boolean isGingerbread() {
        // Can use static final constants like HONEYCOMB, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean isHoneycomb() {
        // Can use static final constants like HONEYCOMB, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean isJellyban() {
        // Can use static final constants like HONEYCOMB, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= 16;
    }

    //----------------------------------------------
    // 기타
    //----------------------------------------------
    public static String sprintf(String format, Object... args) {
        String msg = (args == null) ? format : String.format(Locale.KOREAN, format, args);
        return String.format(Locale.KOREAN, "%s", msg);
    }

    public static String nullProcess(String checkString, String defaultString)
    {
        if (checkString != null)
            return checkString;

        return defaultString;
    }


    //----------------------------------------------
    // 위치관련 : 주소, 거리등
    //----------------------------------------------
    public static float distance(double latitude1, double longitude1, double latitude2, double longitude2)
    {
        float distance=0;

        Location locationA = new Location("point A");

        locationA.setLatitude(latitude1);
        locationA.setLongitude(longitude1);

        Location locationB = new Location("point B");

        locationB.setLatitude(latitude2);
        locationB.setLongitude(longitude2);

        distance = locationA.distanceTo(locationB);

        return(distance);
    }

    public static float distance(Location location1, Location location2)
    {
        float distance=0;

        distance = location1.distanceTo(location2);

        return(distance);
    }

    public static String getAddressFromJson(String addressJson)
    {
        String addressString = null;

        if (addressJson == null)
            return "";

        JSONArray jsonarray;
        try
        {
            JSONObject jsonobject = new JSONObject(addressJson);

            jsonarray = jsonobject.getJSONArray("results");
            JSONObject jsonobject1 = jsonarray.getJSONObject(0);

            // String addr = jsonobject1.toString();

            String addr = jsonobject1.getString("formatted_address");


            StringBuffer sb = new StringBuffer("");
            String[] splitStr = addr.split(" ");
            int startPos = 0;

            if("대한민국".equalsIgnoreCase(splitStr[0]))
                startPos = 1;

            int i;
            for (i = startPos; i < splitStr.length; i++)
            {
                sb.append(splitStr[i]).append(" ");
            }

            addressString = sb.toString();
        }
        catch(JSONException jsonexception)
        {
            return null;
        }

        return addressString;
    }


    //----------------------------------------------
    // 시스템 관련 정보
    //----------------------------------------------
    // 버전은 Double 형태로 관리
    //
    // 1.1001 -> 1.10.01
    // 1.100.001 -> 1.100.001
    // 형식 ㅣ: mainVersion.MinorVersion.VersionSeq
    public static double getAppVersion()
    {
        double version = -1.0;

        try {
            String versionName = mPackageManager.getPackageInfo(mContext.getPackageName(), 0).versionName;
            version = Double.valueOf(versionName);
        }
        catch (NameNotFoundException e) {
            LogUtil.e("error=%s", e.getMessage());
        }

        return version;
    }

    public static int getAppVersionCode()
    {
        int versionCode = 0;

        try {
            versionCode = mPackageManager.getPackageInfo(mContext.getPackageName(), PackageManager.GET_META_DATA).versionCode;
        }
        catch (NameNotFoundException e) {
            LogUtil.e("error=%s", e.getMessage());
        }

        return versionCode;
    }

    public static boolean getAppIsDebuggable()
    {
        boolean isDebuggable = false;

        // LogUtil.d("mContext.getApplicationInfo().flags : %s", Integer.toBinaryString(mContext.getApplicationInfo().flags));
        // LogUtil.d("ApplicationInfo.FLAG_DEBUGGABLE : %s", Integer.toBinaryString(ApplicationInfo.FLAG_DEBUGGABLE));

        // debuggable가 false일 경우 0
        isDebuggable = (mContext.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) == 0 ? false : true;

        return isDebuggable;
    }


    public static String getPackageName()
    {
        return mContext.getPackageName();
    }

    public static boolean isProcessRunning(Context context, String packageName) {
        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = manager.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo procInfo : list) {
            if (procInfo.processName.equals(packageName))
                return true;
        }

        return false;
    }

    public static boolean isServiceRunning(Context context, String packageName) {
        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> list = manager.getRunningServices(1000);

        for (RunningServiceInfo procInfo : list) {
            if (procInfo.service.getClassName().equals(packageName))
                return true;
        }

        return false;
    }

    public static int getScreenDpi()
    {
        Resources resources = mContext.getResources();

        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return (int)(displayMetrics.widthPixels / displayMetrics.density);
    }

    public static int getScreenWidth()
    {
        Resources resources = mContext.getResources();

        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight()
    {
        Resources resources = mContext.getResources();

        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    public static int dpToPx(int dp)
    {
        return Math.round((float)dp * mContext.getResources().getDisplayMetrics().density);
    }

    public static void adaptDefaultFont(int style, View[] pView)
    {
        Typeface typeface = getDefaultFont();
        int i = pView.length;

        for (int j=0; j <= i; j++)
        {
            View v = pView[j];

            if(v instanceof TextView || v instanceof Button /*etc.*/)
                ((TextView)v).setTypeface(typeface, style);
        }
    }

    public static void adaptDefaultFont(View[] pView)
    {
        adaptDefaultFont(Typeface.NORMAL, pView);
    }

    public static Typeface getDefaultFont()
    {
        AssetManager assetManager = mContext.getAssets();

        if (typeface == null)
        {
            try
            {
                String fontname = String.format("fonts/%s.ttf", "NanumGothicB") + ".mp3";
                typeface = Typeface.createFromAsset(assetManager, fontname);
            }
            catch (Exception exception)
            {
                LogUtil.d("error=%s", exception.getMessage());
            }
        }

        return typeface;
    }

    public static void hideKeyboard(FragmentActivity pFragmentActivity)
    {
        if (pFragmentActivity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) pFragmentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(pFragmentActivity.getCurrentFocus().getWindowToken(), 0);
        }
    }

}
