package com.tongtong.tonglib.io.volley;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class ParamRequest extends StringRequest 
{	
	private Map<String,String> mParams = new HashMap<String,String>();

	public ParamRequest(int method, String url, Listener<String> listener, ErrorListener errorListener) {
		super(method, url, listener, errorListener);
	}

	public ParamRequest(String url, Listener<String> listener, ErrorListener errorListener) {
		this(Method.GET, url, listener, errorListener);
	}
	
	@Override
    public Map<String, String> getParams() 
    {
        return this.mParams;
    }
	
	public void addParam(String name, String value)
	{
		mParams.put(name, value);
	}
}
