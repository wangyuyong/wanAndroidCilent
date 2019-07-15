package com.wyy.wanandroidcilent.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


//滑动界面的viewpager的适配器
public class GuideAdapter extends FragmentPagerAdapter {

    private List<Fragment> guideFragments;

    public GuideAdapter(FragmentManager fm, List<Fragment> guideFragments) {
        super(fm);
        this.guideFragments = guideFragments;
    }

    @Override
    public Fragment getItem(int i) {
        return guideFragments.get(i);
    }

    @Override
    public int getCount() {
        return guideFragments.size();
    }

}
