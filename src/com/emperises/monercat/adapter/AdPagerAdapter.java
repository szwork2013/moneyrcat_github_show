package com.emperises.monercat.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class AdPagerAdapter extends PagerAdapter
{
    
    private static String TAG = "ViewPagerAdapter";
    private List<View> mViews;
    
    public AdPagerAdapter(List<View> mViews)
    {
        this.mViews = mViews;
    }
    @Override
    public int getCount()
    {
       // Log.v(TAG, "getCount" + mViews.size());
        return Integer.MAX_VALUE;
    }
    
    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        
       // Log.v(TAG, "isViewFromObject" + (view == object));
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        Log.v(TAG, "instantiateItem" + position);
    
        position = position % mViews.size();
        try {
        	container.addView(mViews.get(position), 0);
		} catch (Exception e) {
		}
        return mViews.get(position);
        
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        Log.v(TAG, "destroyItem" + position);
        position = position % mViews.size();
        container.removeView(mViews.get(position));        
    }  

}
