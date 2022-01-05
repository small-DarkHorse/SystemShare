package com.phone.share.model;

import android.graphics.drawable.Drawable;

import java.io.File;

/**
 * share
 *
 * @author WangZhenkai
 * @since 2022/1/4
 */
public class ShareModel {
    private String appName;
    private String packageName;
    private String className;
    private String apkSize;
    private String apkFirstInstallTime;
    private Drawable icon;
    private File file;
    private String filePath;

    public ShareModel() {
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getApkSize() {
        return apkSize;
    }

    public void setApkSize(String apkSize) {
        this.apkSize = apkSize;
    }

    public String getApkFirstInstallTime() {
        return apkFirstInstallTime;
    }

    public void setApkFirstInstallTime(String apkFirstInstallTime) {
        this.apkFirstInstallTime = apkFirstInstallTime;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
