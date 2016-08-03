package com.tongtong.tonglib.json;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.tongtong.tonglib.util.LogUtil;


public class JsonClassMapper
{
	private final String HEAD_VERSION = "version";
	private final String HEAD_VERSION_V = "v.1.0";
	private final String HEAD_QUERYSTATE = "querystate";
	private final String HEAD_MESSAGE = "msg";
	
	private boolean mIsVaild = false;
	private boolean mIsSuccess = false;
	private String mMessage = "";
	private String mJson = "";
	
	private static JSONObject mJsonObject;
	
	public JsonClassMapper(String json)
	{
		JSONObject jsonObject;
		
		LogUtil.d("JsonClassMapper : %s", json);
		
		mJson = json;
		
		try
        {
			mJsonObject = new JSONObject(json);
			
			if (mJsonObject.getString(HEAD_VERSION).equals(HEAD_VERSION_V))
			{
				mIsVaild = true;
			}
				
			if (mJsonObject.getString(HEAD_QUERYSTATE).equals("success"))
				mIsSuccess = true;
			
			mMessage = mJsonObject.getString(HEAD_MESSAGE);
        }
		catch(JSONException jsonexception)
        {
			LogUtil.d("%s", jsonexception.getMessage());
        }
        catch(Exception exception)
        {
        	LogUtil.d("%s", exception.getMessage());
        }
	}

	public void setJson(String json)
	{
		mJson = json;
		mIsVaild = false;
		mIsSuccess = false;
		
		try
        {
			mJsonObject = new JSONObject(json);
			
			if (mJsonObject.getString(HEAD_VERSION).equals(HEAD_VERSION_V))
				mIsVaild = true;
				
			if (mJsonObject.getString(HEAD_QUERYSTATE).equals("success"))
				mIsSuccess = true;
			
			mMessage = mJsonObject.getString(HEAD_MESSAGE);
        }
		catch(JSONException jsonexception)
        {
			LogUtil.d("%", jsonexception.getMessage());
        }
        catch(Exception exception)
        {
        	LogUtil.d("%", exception.getMessage());
        }
				
	}

	public JSONArray getJson() {
		JSONArray jsonArrayDataList = null;

		try
		{
			jsonArrayDataList = mJsonObject.getJSONArray("datalist");
		}
		catch(JSONException jsonexception)
		{
			LogUtil.d("%", jsonexception.getMessage());
		}
		catch(Exception exception)
		{
			LogUtil.d("%", exception.getMessage());
		}

		return jsonArrayDataList;
	}
	
	public String getReturnMessage()
	{
		if (mIsVaild)
			return mMessage;
		else
			return "Internet Access Fail";
	}
	
	public boolean isValid()
	{
        return mIsVaild;
	}
	
	public boolean isSuccess()
	{
        return mIsSuccess;
	}

	

	private <T> T getInfo(Class<T> classOfT)
	{
		Class<T> _class = null;
		
		Gson gson = new Gson();
        JSONArray jsonArrayDataList = null;
        
        JSONArray jsonArray = null;
        JSONObject jsonObject = null;
        
        try
        {
        	jsonArrayDataList = mJsonObject.getJSONArray("datalist");
        	jsonObject = jsonArrayDataList.getJSONObject(0);
        	
        	_class = gson.fromJson(jsonObject.toString(), (Type) classOfT);
        }
		catch(JSONException jsonexception)
        {
			LogUtil.d("--> jsonexception : %s", jsonexception.getMessage());
        }
        catch(Exception exception)
        {
			LogUtil.d("--> jsonexception : %s", exception.getMessage());
        }
		
		return (T) _class;
	}
	
	
	private ArrayList<Object> getInfoList(Class<Object> classOfT)
	{
		Object object = null;
		ArrayList<Object> arList = new ArrayList<Object>();
		
		Gson gson = new Gson();
        JSONArray jsonArrayDataList = null;
        
        JSONArray jsonArray = null;
        JSONObject jsonObject = null;
        
        try
        {
        	jsonArrayDataList = mJsonObject.getJSONArray("datalist");
			
        	int j = jsonArrayDataList.length();
        	
        	for (int i=0; i<j; i++)
        	{       		
        		jsonObject = jsonArrayDataList.getJSONObject(i);
        		
        		object = gson.fromJson(jsonObject.toString(), (Type) classOfT);
        		arList.add(object);
        	}
        }
		catch(JSONException jsonexception)
        {
			LogUtil.d("--> jsonexception : %s", jsonexception.getMessage());
        }
        catch(Exception exception)
        {
			LogUtil.d("--> jsonexception : %s", exception.getMessage());
        }

		return arList;
        
	}
}
