package com.phone.share.utils;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.IntDef;

import com.phone.share.R;
import com.phone.share.ui.BlueToothShareDialog;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;


/**
 * @author WangZhenKai
 * @ClassName : ShareBuilder
 * @Date 2021/1/4
 * @Description : 分享属性构造器
 */
public class SystemShare {

    public static SystemShare share;
    /**
     * 分享文字
     */
    public static final int SHARE_TEXT = 1;
    /**
     * 分享文件
     */
    public static final int SHARE_FILE = 2;

    /**
     * 分享多个文件
     */
    public static final int SHARE_MULTIPLE_FILES = 3;
    /**
     * 分享apk文件
     */
    public static final int SHARE_APK_FILE = 4;

    private Activity context;
    private int isUseSelfCanceledOnTouchOutside = -1;
    private BlueToothShareDialog blueToothShareDialog;

    @IntDef({SHARE_TEXT, SHARE_FILE, SHARE_MULTIPLE_FILES, SHARE_APK_FILE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShareType {
    }

    public static SystemShare getInstance() {
        if (share == null) {
            synchronized (SystemShare.class) {
                if (share == null) {
                    share = new SystemShare();
                }
            }
        }
        return share;
    }

    /**
     * 分享的文字内容
     */
    private String text;

    /**
     * 分享选择标题
     */
    private String chooserTitle;

    /**
     * 分享类型
     */
    private int shareType;

    /**
     * 分享的文件集合
     */
    private List<Uri> shareFiles;

    /**
     * 分享应用的文件路径
     */
    private Uri shareApkFile;
    /**
     * 是否取消外部点击关闭dialog
     */
    private boolean canceledOnTouchOutside;

    public String getText() {
        return text;
    }

    public SystemShare setText(String text) {
        this.text = text;
        return this;
    }

    public String getChooserTitle() {
        return chooserTitle;
    }

    public SystemShare setChooserTitle(String chooserTitle) {
        this.chooserTitle = chooserTitle;
        return this;
    }

    public int getShareType() {
        return shareType;
    }

    public SystemShare setShareType(@ShareType int shareType) {
        this.shareType = shareType;
        return this;
    }

    public List<Uri> getShareFiles() {
        return shareFiles;
    }

    public SystemShare setShareFiles(List<Uri> shareFiles) {
        this.shareFiles = shareFiles;
        return this;
    }

    public Uri getShareApkFile() {
        return shareApkFile;
    }

    public SystemShare setShareApkFile(Uri shareApkFile) {
        this.shareApkFile = shareApkFile;
        return this;
    }

    public Activity getContext() {
        return context;
    }

    public SystemShare setContext(Activity context) {
        this.context = context;
        return this;
    }

    public boolean isCanceledOnTouchOutside() {
        return canceledOnTouchOutside;
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        isUseSelfCanceledOnTouchOutside = 10;
        this.canceledOnTouchOutside = canceledOnTouchOutside;
    }

    public Share build() {
        return new Share(this);
    }

    //不需要回调
    public SystemShare showBlueTooth() {
        if (CommonUtil.validateSupportBlueTooth(context)) {
            CommonUtil.validateDeviceOpenBlueTooth(context, () -> {
                if (isUseSelfCanceledOnTouchOutside == 10) {
                    blueToothShareDialog = new BlueToothShareDialog(context, apkFile -> {
                        build().share(context);
                        blueToothShareDialog.dismiss();
                    }, isCanceledOnTouchOutside());
                } else {
                    blueToothShareDialog = new BlueToothShareDialog(context, apkFile -> {
                        build().share(context);
                        blueToothShareDialog.dismiss();
                    });
                }
                blueToothShareDialog.show();
            });
        }
        return this;
    }

    //需要在Activity中回调蓝牙状态，处理自己的业务逻辑
    public SystemShare showBlueToothForResult(int requestCode) {
        if (CommonUtil.validateSupportBlueTooth(context)) {
            CommonUtil.validateDeviceOpenBlueTooth(context, () -> {
                if (isUseSelfCanceledOnTouchOutside == 10) {
                    blueToothShareDialog = new BlueToothShareDialog(context, apkFile -> {
                        build().shareForResult(context, requestCode);
                        blueToothShareDialog.dismiss();
                    }, isCanceledOnTouchOutside());
                } else {
                    blueToothShareDialog = new BlueToothShareDialog(context, apkFile -> {
                        build().shareForResult(context, requestCode);
                        blueToothShareDialog.dismiss();
                    });
                }
                blueToothShareDialog.show();
            });
        }
        return this;
    }

    //不需要回调,让用户自己操作设备
    public SystemShare showSetBlueTooth() {
        if (CommonUtil.validateSupportBlueTooth(context)) {
            CommonUtil.validateDeviceSetBlueTooth(context, () -> {
                if (isUseSelfCanceledOnTouchOutside == 10) {
                    blueToothShareDialog = new BlueToothShareDialog(context, apkFile -> {
                        build().share(context);
                        blueToothShareDialog.dismiss();
                    }, isCanceledOnTouchOutside());
                } else {
                    blueToothShareDialog = new BlueToothShareDialog(context, apkFile -> {
                        build().share(context);
                        blueToothShareDialog.dismiss();
                    });
                }
                blueToothShareDialog.show();
            });
        }
        return this;
    }

    //需要在Activity中回调蓝牙状态，处理自己的业务逻辑,让用户自己操作设备
    public SystemShare showSetBlueToothForResult(int requestCode) {
        if (CommonUtil.validateSupportBlueTooth(context)) {
            CommonUtil.validateDeviceSetBlueTooth(context, () -> {
                if (isUseSelfCanceledOnTouchOutside == 10) {
                    blueToothShareDialog = new BlueToothShareDialog(context, apkFile -> {
                        build().shareForResult(context, requestCode);
                        blueToothShareDialog.dismiss();
                    }, isCanceledOnTouchOutside());
                } else {
                    blueToothShareDialog = new BlueToothShareDialog(context, apkFile -> {
                        build().shareForResult(context, requestCode);
                        blueToothShareDialog.dismiss();
                    });
                }
                blueToothShareDialog.show();
            });
        }
        return this;
    }

    public SystemShare showDefaultBlueTooth() {
        blueToothShareDialog = new BlueToothShareDialog(context, apkFile -> {
            build().share(context);
            blueToothShareDialog.dismiss();
        });
        blueToothShareDialog.show();
        return this;
    }
}
