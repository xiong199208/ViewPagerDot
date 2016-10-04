package com.itheima.carousel;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.itheima.carousel.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    int [] mIcons = {
            R.drawable.guide_1,
            R.drawable.guide_2,
            R.drawable.guide_3
    };

    @Bind(R.id.viewpager)
    ViewPager mViewpager;
    @Bind(R.id.ll_dot)
    LinearLayout mLlDot;
    @Bind(R.id.iv_red)
    ImageView mIvRed;
    private List<ImageView> mList;
    private int mPitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        initListener();
    }

    private void initData() {
        //初始化小圆点
        initDot();

        //初始化ViewPager的view集合
        initPager();

        MyPagerAdapter adapter = new MyPagerAdapter(mList);
        mViewpager.setAdapter(adapter);

    }

    /**
     * 处理监听事件
     */
    private void initListener() {
        //ViewPager的滑动监听
        mViewpager.addOnPageChangeListener(this);
    }

    private List<ImageView> initPager() {

        mList = new ArrayList<>();

        for (int i = 0; i < mIcons.length; i++) {

            ImageView iv = new ImageView(this);
            iv.setImageResource(mIcons[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            mList.add(iv);

        }
        return mList;
    }

    private void initDot() {

        //屏幕适配，将dp值转换成Pixel值
        int size = getResources().getDimensionPixelSize(R.dimen.dot_size);
        //根据轮播图的数量添加小黑点
        for (int i = 0; i < mIcons.length; i++) {

            ImageView image = new ImageView(this);
            image.setImageResource(R.drawable.dot_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size,size);

            if (i!=0) {
                //小黑点的间距
                params.leftMargin = size;
            }
            image.setLayoutParams(params);
            mLlDot.addView(image);

            //当添加到第二个小黑点时，计算两个小黑点间的距离，为小红点的移动做准备
            if (mLlDot.getChildCount()==2) {
                mLlDot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        //通过取两个小黑点左边距来
                        mPitch = mLlDot.getChildAt(1).getLeft()-mLlDot.getChildAt(0).getLeft();
                        mLlDot.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });

            }

        }
    }

    /**
     *通过轮播图移动的距离，来测算小红点移动的距离，并进行移动
     *
     * @param position 当前是第几个条目
     * @param positionOffset 移动的距离的比值
     * @param positionOffsetPixels 实际移动的像素点
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        int pixel = Math.round(mPitch*positionOffset)+position*mPitch;

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mIvRed.getLayoutParams();
        //通过改变小红点左边距，来移动小红点
        params.leftMargin = pixel;

        mIvRed.setLayoutParams(params);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
