package com.hh.wipeoutpests.activity.main;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.hh.wipeoutpests.dialog.LoadingDialog;

/**
 * Created by huhu on 2018/2/10.
 */

public class BaseFragment  extends Fragment {

    private Dialog mWeiboDialog;

    /**
     * 显示Toast消息
     *
     * @param @param msg
     * @return void
     * @throws
     * @Title: showToast
     * @Description: TODO
     */
    protected void showToast(String msg) {
        if (msg==null){
            msg="错误的操作";
        }
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLoading() {
        showLoading("数据加载中");
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
            mWeiboDialog = LoadingDialog.createLoadingDialog(getActivity(), msg);
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
