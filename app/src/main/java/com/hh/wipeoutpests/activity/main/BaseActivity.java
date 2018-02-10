package com.hh.wipeoutpests.activity.main;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.hh.wipeoutpests.dialog.LoadingDialog;

/**
 * Created by huhu on 2018/2/10.
 */

public class BaseActivity extends AppCompatActivity {

    private Dialog mWeiboDialog;
    private Toast toast = null;

    protected void showToast(String msg) {
        // Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        if (toast != null) {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showLoading() {
        showLoading("数据加载中...");
    }

    protected void showLoading(int times) {
        for (int i = 0; i < times; i++) {
            showLoading();
        }

    }

    protected void showLoading(String... msg) {
        for (String s : msg) {
            showLoading(msg);
        }

    }

    /**
     * 显示等待框 @Title: showLoading @Description: TODO @param msg 提示信息 @return
     * void @throws
     */
    protected void showLoading(String msg) {
        if (mWeiboDialog == null){
            mWeiboDialog = LoadingDialog.createLoadingDialog(this, msg);
        }
        mWeiboDialog.show();
    }

    /**
     * 关闭等待框 @Title: dismissLoading @Description: TODO @param @return
     * void @throws
     */
    public void dismissLoading() {
        if (mWeiboDialog != null)
            LoadingDialog.closeDialog(mWeiboDialog);
    }
}
