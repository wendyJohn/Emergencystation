package com.sanleng.emergencystation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.fragment.FragmentFactory;
import com.sanleng.emergencystation.utils.CommonUtil;

public class PagersAdapter extends FragmentPagerAdapter {

    private String[] tabArr;

    public PagersAdapter(FragmentManager fm, String custom) {
        super(fm);
        if (custom.equals("1")) {
            tabArr = CommonUtil.getStringArray(R.array.tab_namea);
        }
        if (custom.equals("2")) {
            tabArr = CommonUtil.getStringArray(R.array.tab_nameb);
        }
        if (custom.equals("3")) {
            tabArr = CommonUtil.getStringArray(R.array.tab_namec);
        }
        if (custom.equals("4")) {
            tabArr = CommonUtil.getStringArray(R.array.tab_named);
        }

    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.create(position);
    }

    @Override
    public int getCount() {
        return tabArr.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabArr[position];
    }
}