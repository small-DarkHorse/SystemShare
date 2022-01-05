/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2014-2019. All rights reserved.
 *
 */

package com.phone.share.ui;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.phone.share.R;
import com.phone.share.model.ShareModel;
import com.phone.share.utils.ActDialog;
import com.phone.share.utils.AppFileUtil;
import com.phone.share.utils.PxUtils;
import com.phone.share.utils.TimeUtil;

import java.io.File;
import java.util.Date;

/**
 * 蓝牙分享弹窗
 *
 * @author WangZhenKai
 * @since 2020/12/31
 */
public class BlueToothShareDialog extends ActDialog {
    private ShareEvent mShareEvent;
    private final Activity mContext;
    private ImageView ivAppIcon;
    private TextView tvAppName;
    private TextView tvAppSize;
    private TextView tvAppInstallTime;
    private TextView brnShare;
    private ShareModel shareModel;

    public BlueToothShareDialog(Activity context, ShareEvent shareEvent) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        this.mShareEvent = shareEvent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bluetooth_share);
        // 设置对话框在屏幕的位置
        Window windowE2cBnd = getWindow();
        if (windowE2cBnd == null) {
            return;
        }
        WindowManager windowManager = windowE2cBnd.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(outMetrics);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = outMetrics.widthPixels;
        lp.height = PxUtils.dp2px(mContext, 250);
        lp.dimAmount = 0.1f;
        lp.gravity = Gravity.BOTTOM;
        windowE2cBnd.setAttributes(lp);
        windowE2cBnd.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setCanceledOnTouchOutside(true);

        ivAppIcon = findViewById(R.id.ivAppIcon);
        tvAppName = findViewById(R.id.tvAppName);
        tvAppSize = findViewById(R.id.tvAppSize);
        tvAppInstallTime = findViewById(R.id.tvAppInstallTime);
        brnShare = findViewById(R.id.brnShare);
        initEvent();
        new ShareThread().start();
    }

    private void initEvent() {
        brnShare.setOnClickListener(view -> {
            if (mShareEvent != null) {
                if (shareModel != null) {
                    mShareEvent.onShare(shareModel.getFile());
                } else {
                    Toast.makeText(mContext, "File Maybe no exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private BlueToothShareDialog setAttr(Drawable icon, String appName, String appSize, String time) {
        if (!AppFileUtil.isEmptyStr(appName)) {
            tvAppName.setText(appName);
        } else {
            tvAppName.setText("iSupply");
        }
        if (!AppFileUtil.isEmptyStr(appSize)) {
            tvAppSize.setText(appSize);
        } else {
            tvAppSize.setText("0M");
        }
        if (!AppFileUtil.isEmptyStr(time)) {
            tvAppInstallTime.setText(time);
        } else {
            Date date = new Date();
            String timed = date.getYear() + "年" + date.getMonth() + "月" + date.getDay() + "日 " + date.getHours() + ":00";
            tvAppInstallTime.setText(timed);
        }
        ivAppIcon.setImageDrawable(icon);
        return this;
    }


    class ShareThread extends Thread {
        @Override
        public void run() {
            super.run();
            getAppInfo();
        }
    }

    //获取当前应用信息并分享
    private void getAppInfo() {
        shareModel = AppFileUtil.getAloneApp(mContext, mContext.getApplicationContext().getPackageName());
        if (shareModel != null) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setAttr(shareModel.getIcon(), shareModel.getAppName(), shareModel.getApkSize(), shareModel.getApkFirstInstallTime());
                }
            });
        }
    }

    public interface ShareEvent {
        void onShare(File apkFile);
    }
}
