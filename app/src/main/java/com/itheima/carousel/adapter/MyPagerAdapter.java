package com.itheima.carousel.adapter;
/*
 *  @项目名：  carousel 
 *  @包名：    com.itheima.carousel.adapter
 *  @文件名:   MyPagerAdapter
 *  @创建者:   Administrator
 *  @创建时间: 十月
 *  @描述：    TODO
 */

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    List<ImageView> mList;

    public MyPagerAdapter(List<ImageView> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView view = mList.get(position);

        container.addView(view);

        return view;
    }
}
