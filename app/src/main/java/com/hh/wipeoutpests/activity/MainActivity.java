package com.hh.wipeoutpests.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.hh.wipeoutpests.R;
import com.hh.wipeoutpests.activity.main.BaseActivity;
import com.hh.wipeoutpests.activity.main.BaseFragment;
import com.hh.wipeoutpests.activity.main.MainTabFactory;

public class MainActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener ,View.OnTouchListener{
    MainTabFactory factory;
    BaseFragment currentTab;
    CheckBox mOneFragment;
    CheckBox mTwoFragment;
    CheckBox mThreeFragment;
    CheckBox mFourFragment;
    int checkedId;
    long exitTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }
    private void initView(){
        mOneFragment = (CheckBox) findViewById(R.id.mOneFragment);
        mTwoFragment = (CheckBox) findViewById(R.id.mTwoFragment);
        mThreeFragment = (CheckBox) findViewById(R.id.mThreeFragment);
        mFourFragment = (CheckBox) findViewById(R.id.mFourFragment);
        factory = new MainTabFactory(this,R.id.mLayout);
        currentTab = factory.getInstanceByIndex(R.id.mOneFragment, currentTab);

        mOneFragment.setOnCheckedChangeListener(this);
        mTwoFragment.setOnCheckedChangeListener(this);
        mThreeFragment.setOnCheckedChangeListener(this);
        mFourFragment.setOnCheckedChangeListener(this);
        mOneFragment.setOnTouchListener(this);
        mTwoFragment.setOnTouchListener(this);
        mThreeFragment.setOnTouchListener(this);
        mFourFragment.setOnTouchListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked){
            switch (compoundButton.getId()){
                case R.id.mOneFragment:
                    mOneFragment.setChecked(true);
                    mTwoFragment.setChecked(false);
                    mThreeFragment.setChecked(false);
                    mFourFragment.setChecked(false);
//                    mTitle.setText("我家");
                    break;
                case R.id.mTwoFragment:
                    mOneFragment.setChecked(false);
                    mTwoFragment.setChecked(true);
                    mThreeFragment.setChecked(false);
                    mFourFragment.setChecked(false);
                    break;
                case R.id.mThreeFragment:
                    mOneFragment.setChecked(false);
                    mTwoFragment.setChecked(false);
                    mThreeFragment.setChecked(true);
                    mFourFragment.setChecked(false);
                    break;
                case R.id.mFourFragment:
                    mOneFragment.setChecked(false);
                    mTwoFragment.setChecked(false);
                    mThreeFragment.setChecked(false);
                    mFourFragment.setChecked(true);
                    break;
            }
            this.checkedId = compoundButton.getId();
            currentTab = factory.getInstanceByIndex(checkedId, currentTab);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(checkedId == view.getId()){
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if(System.currentTimeMillis() - exitTime > 2000){
                showToast("再按一次退出程序！");
                exitTime = System.currentTimeMillis();
                return true;
            }else{
                finish();
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
