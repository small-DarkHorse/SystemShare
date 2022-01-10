package com.phone.share.utils;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.phone.share.model.ShareModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取手机所有应用信息
 *
 * @author WangZhenKai
 * @since 2021/1/4
 */
public class ShareFileUtil {
    private static File apkFile = null;

    public static ShareModel getAloneApp(Activity mContext, String packageName) {
        List<PackageInfo> packageInfos = mContext.getApplicationContext().getPackageManager().getInstalledPackages(0);
        ShareModel shareModel = new ShareModel();
        for (int i = 0; i < packageInfos.size(); i++) {
            if (packageName.equals(packageInfos.get(i).packageName)) {
                apkFile = new File(packageInfos.get(i).applicationInfo.sourceDir);
                String apkSize = apkFile.length() / 1024 / 1024 + "M";
                shareModel.setFile(apkFile);
                shareModel.setFilePath(packageInfos.get(i).applicationInfo.sourceDir);
                shareModel.setAppName(getApplicationName(packageInfos.get(i).packageName, mContext.getPackageManager()));
                shareModel.setPackageName(packageInfos.get(i).packageName);
                shareModel.setIcon(getApplicationLogo(packageInfos.get(i).packageName, mContext.getPackageManager()));
                shareModel.setApkSize(apkSize);
                shareModel.setApkFirstInstallTime(TimeUtil.stampToTime(packageInfos.get(i).firstInstallTime));
                return shareModel;
            }
        }
        return new ShareModel();
    }

    public static List<ShareModel> getMultipleApp(Activity mContext) {
        List<PackageInfo> packageInfos = mContext.getApplicationContext().getPackageManager().getInstalledPackages(0);
        List<ShareModel> shareModelList = new ArrayList<>();
        for (int i = 0; i < packageInfos.size(); i++) {
            apkFile = new File(packageInfos.get(i).applicationInfo.sourceDir);
            String apkSize = apkFile.length() / 1024 / 1024 + "M";
            ShareModel shareModel = new ShareModel();
            shareModel.setFile(apkFile);
            shareModel.setFilePath(packageInfos.get(i).applicationInfo.sourceDir);
            shareModel.setAppName(getApplicationName(packageInfos.get(i).packageName, mContext.getPackageManager()));
            shareModel.setPackageName(packageInfos.get(i).packageName);
            shareModel.setIcon(getApplicationLogo(packageInfos.get(i).packageName, mContext.getPackageManager()));
            shareModel.setApkSize(apkSize);
            shareModel.setApkFirstInstallTime(TimeUtil.stampToTime(packageInfos.get(i).firstInstallTime));
            shareModelList.add(shareModel);
        }
        return shareModelList;
    }

    private static String getApplicationName(String packageName, PackageManager packageManager) {
        String applicationName = "";
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            return "packageInfo is Error";
        }
        return applicationName;
    }

    private static Drawable getApplicationLogo(String packageName, PackageManager packageManager) {
        Drawable applicationLogo = null;
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            applicationLogo = packageManager.getApplicationIcon(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
        return applicationLogo;
    }
     public static boolean isEmptyStr(String strTarget) {
        return (("null".equals(strTarget)) || (null == strTarget) || (0 == strTarget.length()) || "".equals(strTarget));
    }
}
