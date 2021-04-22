package com.sanleng.emergencystation.fragment;

import android.support.v4.app.Fragment;

/**
 * Fragment工厂类
 */
public class FragmentFactory {
    /**
     * 根据position生产不同的fragment
     *
     * @param position
     * @return
     */
    public static Fragment create(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new SearchFragment("一");
                break;
            case 1:
                fragment = new SearchFragment("二");
                break;
            case 2:
                fragment = new SearchFragment("三");
                break;
            case 3:
                fragment = new SearchFragment("四");
                break;
            case 4:
                fragment = new SearchFragment("五");
                break;
            case 5:
                fragment = new SearchFragment("六");
                break;
            case 6:
                fragment = new SearchFragment("七");
                break;
            case 7:
                fragment = new SearchFragment("八");
                break;
            case 8:
                fragment = new SearchFragment("九");
                break;
            case 9:
                fragment = new SearchFragment("十");
                break;
        }
        return fragment;
    }
}