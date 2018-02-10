package com.hh.wipeoutpests.activity.main;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.hh.wipeoutpests.R;
import com.hh.wipeoutpests.fragment.FourFragment;
import com.hh.wipeoutpests.fragment.OneFragment;
import com.hh.wipeoutpests.fragment.ThreeFragment;
import com.hh.wipeoutpests.fragment.TwoFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huhu on 2018/2/10.
 */

public class MainTabFactory {
    private int resourseId;
    public FragmentManager fragmentManager;
    public FragmentTransaction transaction;
    public Map<Integer, BaseFragment> map = new HashMap<Integer, BaseFragment>();

    public MainTabFactory(FragmentActivity activity, int resourseId){
        this.resourseId = resourseId;
        fragmentManager = activity.getSupportFragmentManager();
    }

    public BaseFragment getInstanceByIndex(int index,BaseFragment old){
        transaction = fragmentManager.beginTransaction();
        BaseFragment fragment = map.get(index);
        if(fragment == null){
            switch (index){
                case R.id.mOneFragment:
                    fragment = new OneFragment();
                    break;
                case R.id.mTwoFragment:
                    fragment = new TwoFragment();
                    break;
                case R.id.mThreeFragment:
                    fragment = new ThreeFragment();
                    break;
                case R.id.mFourFragment:
                    fragment = new FourFragment();
                    break;
            }
            transaction.add(resourseId, fragment);
            map.put(index, fragment);
        }
        if (old != null) {
            transaction.hide(old);
        }
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
        return fragment;
    }
}
