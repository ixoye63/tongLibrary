package com.tongtong.tonglib.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class TabView extends LinearLayout
{
	private static int n = 0;
	private static final int kPadding = 8;
	
	private Context context;

	private View _backgroundView;
	private View _contentView;
	private ImageView _dividerView;
	private ImageView _imageView;
	private TextView _titleLabel;
	
	public TabView(Context context) {
		super(context);
		
		this.context = context;
		
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
		this.setOrientation(LinearLayout.HORIZONTAL);	
		this.setBackgroundColor(Color.TRANSPARENT);

		FrameLayout frameLayout = new FrameLayout(context);
		if (frameLayout != null) {
			frameLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
					LayoutParams.MATCH_PARENT));
					
			_backgroundView = new FrameLayout(context);
			((FrameLayout) _backgroundView).setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			frameLayout.addView(_backgroundView);
			
			_contentView = new LinearLayout(context); 
			((LinearLayout) _contentView).setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			((LinearLayout) _contentView).setOrientation(LinearLayout.VERTICAL);	
			((LinearLayout) _contentView).setGravity(Gravity.CENTER);	
			//((LinearLayout) _contentView).setPadding(kPadding, kPadding, kPadding, kPadding);
			
			if (_contentView != null) {
				_imageView = new ImageView(context);
				_imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				((LinearLayout) _contentView).addView(_imageView);
				
				_titleLabel = new TextView(context);
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				lp.topMargin = 4;
				_titleLabel.setLayoutParams(lp);				
				_titleLabel.setGravity(Gravity.CENTER);
				((LinearLayout) _contentView).addView(_titleLabel);
				
				frameLayout.addView(_contentView);			
			}
			
			
			_dividerView = new ImageView(context);
			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(1, (int)convertDpToPixel(47, context));
			lp.gravity = Gravity.CENTER_VERTICAL  | Gravity.LEFT;
			_dividerView.setLayoutParams(lp);
			_dividerView.setBackgroundColor(Color.GRAY);
			_dividerView.setVisibility(View.GONE);
			frameLayout.addView(_dividerView);
		
			
			this.addView(frameLayout);
		}
		
	}
	
	public TabView(Context context, int drawable, String title) {
		super(context);
		
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
		
		FrameLayout layout = new FrameLayout(context);
		if (layout != null) {
			layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));				
			
			if (n % 2 == 0)
				layout.setBackgroundColor(Color.RED);
			else 
				layout.setBackgroundColor(Color.BLUE);
			
			n++;
			
			this.addView(layout);
		}		
		
		//-- --//
		Resources r = context.getResources();
	}

	
	public static float convertDpToPixel(float dp, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float px = dp * (metrics.densityDpi / 160f);
	    return px;
	}
	
	
	public void showDivider() {
		_dividerView.setVisibility(View.VISIBLE);
	}
	
	
	public void setBackgroundImage(int drawable) {
		Resources r = getResources();
		
		_backgroundView.setBackgroundDrawable(r.getDrawable(drawable));
	}
	
	public void setIconImage(int drawable) {
		Resources r = getResources();
		_imageView.setImageDrawable(r.getDrawable(drawable));		
	}

	public void setTitle(int resId) {
		Resources r = getResources();
		_titleLabel.setText(r.getString(resId));
	}
	
	
	public void setTitleColor(int resId) {		
		XmlResourceParser parser = getResources().getXml(resId);
		ColorStateList colors;
		try {
			colors = ColorStateList.createFromXml(getResources(), parser);
			_titleLabel.setTextColor(colors);			
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setTitleFont(Typeface typeface, float size) {
		_titleLabel.setTypeface(typeface);
		_titleLabel.setTextSize(size);
	}

	public String getTitle()
	{
		return(_titleLabel.getText().toString());
	}

}
