package com.phone.share.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WangZhenKai
 * @Date 2021/1/4
 * @Description :分享
 */
public class Share {
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

    private Uri apkFileUri;

    private Intent shareIntent = null;

    public Share(SystemShare builder) {
        text = builder.getText();
        chooserTitle = builder.getChooserTitle();
        shareType = builder.getShareType();
        shareFiles = builder.getShareFiles();
        apkFileUri = builder.getShareApkFile();
        shareIntent = initIntent();
    }

    /**
     * 初始化Intent意图
     *
     * @return
     */
    private Intent initIntent() {
        Intent shareIntent = null;
        switch (shareType) {
            case SystemShare.SHARE_TEXT:
                shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, text);
                shareIntent.setType("text/plain");
                break;
            case SystemShare.SHARE_FILE:
                if (shareFiles != null && shareFiles.size() > 0) {
                    shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, shareFiles.get(0));
                    String extension = MimeTypeMap.getFileExtensionFromUrl(shareFiles.get(0).getPath());
                    String mimeType = "".equals(extension) ? null : MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                    shareIntent.setType(mimeType == null ? "*/*" : mimeType);
                }
                break;
            case SystemShare.SHARE_MULTIPLE_FILES:
                if (shareFiles != null && shareFiles.size() > 1) {
                    shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                    shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, (ArrayList) shareFiles);
                    String mimeType = null;
                    String[] mimeTypeSplit = null;
                    for (Uri file : shareFiles) {
                        String extension = MimeTypeMap.getFileExtensionFromUrl(file.getPath());
                        String fileMimeType = "".equals(extension) ? null : MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                        if (mimeType == null) {
                            mimeType = fileMimeType;
                            mimeTypeSplit = fileMimeType.split("/");
                        } else {
                            String[] typeSplit = fileMimeType.split("/");
                            if (typeSplit[0].equals(mimeTypeSplit[0]) && typeSplit[1].equals(mimeTypeSplit[1])) {
                                //相同类型且后缀相同则不改变mimType
                            } else if (typeSplit[0].equals(mimeTypeSplit[0]) && !typeSplit[1].equals(mimeTypeSplit[1])) {
                                //相同类型不同后缀，则保留类型
                                mimeType = typeSplit[0] + "/*";
                            } else if (!typeSplit[0].equals(mimeTypeSplit[0]) && !typeSplit[1].equals(mimeTypeSplit[1])) {
                                mimeType = "*/*";
                                break;
                            }
                        }
                    }
                    shareIntent.setType(mimeType);
                }
                break;
            case SystemShare.SHARE_APK_FILE:
                shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, apkFileUri);
                shareIntent.setPackage("com.android.bluetooth");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                builder.detectFileUriExposure();
                shareIntent.setType("*/*");
                break;
        }
        return shareIntent;
    }

    /**
     * 获取ShareIntent
     *
     * @return
     */
    public Intent getShareIntent() {
        return shareIntent;
    }

    /**
     * 调用分享页面
     *
     * @param activity
     */
    public void share(Activity activity) {
        if (shareType == SystemShare.SHARE_APK_FILE) {
            Intent chooser = Intent.createChooser(shareIntent, chooserTitle);
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(chooser);
        } else {
            activity.startActivity(Intent.createChooser(shareIntent, chooserTitle));
        }
    }

    /**
     * 调用分享页面
     *
     * @param activity
     * @param requestCode
     */
    public void shareForResult(Activity activity, int requestCode) {
        if (shareType == SystemShare.SHARE_APK_FILE) {
            Intent chooser = Intent.createChooser(shareIntent, chooserTitle);
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivityForResult(chooser, requestCode);
        } else {
            activity.startActivityForResult(Intent.createChooser(shareIntent, chooserTitle), requestCode);
        }
    }
}
