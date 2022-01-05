package com.phone.share.utils;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

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
    private BlueToothShareDialog blueToothShareDialog;

    @IntDef({SHARE_TEXT, SHARE_FILE, SHARE_MULTIPLE_FILES})
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

    public Share build() {
        Log.d("Share=build==》","Running build");
        return new Share(this);
    }

    public SystemShare showBlueTooth() {
        Log.d("Share=showBlueTooth==》","Running showBlueTooth");
        blueToothShareDialog = new BlueToothShareDialog(context, new BlueToothShareDialog.ShareEvent() {
            @Override
            public void onShare(File apkFile) {
                 build().share(context);
            }
        });
        blueToothShareDialog.show();
        return this;
    }
}
