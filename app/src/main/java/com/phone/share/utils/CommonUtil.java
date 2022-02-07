package com.phone.share.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.widget.Toast;

import com.phone.share.dialog.TipDialog;
import com.phone.share.inter.BlueToothShareCallBack;

import java.lang.ref.WeakReference;

/**
 * CommonUtil
 *
 * @author Wangzhenkai
 * @since 2022/1/10
 */
public class CommonUtil {
    /**
     * 启动蓝牙及检查蓝牙状态
     *
     * @param context            上下文
     * @param toothShareCallBack 回调执行
     */
    public static void validateDeviceOpenBlueTooth(Activity context, BlueToothShareCallBack toothShareCallBack) {
        // 已开启蓝牙
        if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            if (toothShareCallBack != null) {
                toothShareCallBack.startShareApp();
            }
        } else { // 未开启蓝牙，去开启并执行附近蓝牙搜索
            new TipDialog(context, () -> BluetoothManager.doWindowTurnOnBlueTooth(context),
                    () -> Toast.makeText(context, "您拒绝开启蓝牙，无法进行蓝牙分享！", Toast.LENGTH_LONG).show())
                    .setTitle("蓝牙状态提醒")
                    .setContent("您还未开启蓝牙，请先开启").show();
        }
    }

    /**
     * 启动蓝牙及检查蓝牙状态
     *
     * @param context            上下文
     * @param toothShareCallBack 回调执行
     */
    public static void validateDeviceSetBlueTooth(Activity context, BlueToothShareCallBack toothShareCallBack) {
        // 已开启蓝牙
        if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            if (toothShareCallBack != null) {
                toothShareCallBack.startShareApp();
            }
        } else { // 未开启蓝牙，去蓝牙设置界面让用户开启蓝牙
            new TipDialog(context, () -> BluetoothManager.toBlueToothSetting(context),
                    () -> Toast.makeText(context, "您拒绝开启蓝牙，无法进行蓝牙分享！", Toast.LENGTH_LONG).show())
                    .setTitle("蓝牙状态提醒")
                    .setContent("您还未开启蓝牙，请先开启").show();
        }
    }

    /**
     * 检查设备是否支持蓝牙
     *
     * @param context 上下文
     * @return 设备是否支持蓝牙
     */
    public static boolean validateSupportBlueTooth(Context context) {
        if (BluetoothAdapter.getDefaultAdapter() == null) {
            Toast.makeText(context, "当前设备不支持蓝牙！", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static <T extends Activity> boolean isActivityFinished(T checkContext) {
        if (null == checkContext) {
            return true;
        }
        return isActivityFinished(new WeakReference<Activity>(checkContext));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static <T extends Activity> boolean isActivityFinished(WeakReference<T> checkActivity) {
        if (null == checkActivity || null == checkActivity.get()) {
            return true;
        }
        Activity tempCheckActivity = checkActivity.get();
        if (tempCheckActivity.isDestroyed() || tempCheckActivity.isFinishing()) {
            return true;
        }
        return false;
    }
}
