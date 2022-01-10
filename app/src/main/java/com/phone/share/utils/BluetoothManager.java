
package com.phone.share.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.provider.Settings;

/**
 * 蓝牙管理类,包含三种蓝牙打开方式
 *
 * @author WangZhenKai
 * @since 2022/1/10
 */
public class BluetoothManager {
    //打开 Bluetooth 的请求码，与 onActivityResult 中返回的 requestCode 匹配
    private static final int REQUEST_CODE_BLUETOOTH_ON = 1313;
    //Bluetooth 设备可见时间，单位：秒。
    private static final int BLUETOOTH_DISCOVERABLE_DURATION = 200;

    /**
     * 当前 Android 设备是否支持 Bluetooth
     *
     * @return true：支持 Bluetooth false：不支持 Bluetooth
     */
    public static boolean isBlueToothSupported() {

        return BluetoothAdapter.getDefaultAdapter() != null ? true : false;

    }

    /**
     * 当前 Android 设备的 bluetooth 是否已经开启
     *
     * @return true：Bluetooth 已经开启 false：Bluetooth 未开启
     */
    public static boolean isBlueToothEnabled() {

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            return bluetoothAdapter.isEnabled();
        }
        return false;
    }

    /**
     * 强制开启当前 Android 设备的 Bluetooth
     *
     * @return true：强制打开 Bluetooth 成功 false：强制打开 Bluetooth 失败
     */
    public static boolean turnOnBlueTooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            return bluetoothAdapter.enable();
        }

        return false;
    }

    /**
     * 强制关闭当前 Android 设备的 Bluetooth
     *
     * @return true：强制关闭 Bluetooth 成功 false：强制关闭 Bluetooth 失败
     */
    public static boolean turnOffBlueTooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            return bluetoothAdapter.disable();
        }
        return false;
    }

    /**
     * 弹窗提示用户打开Bluetooth 设置,用户同意跳转到蓝牙列表页面
     *
     * @param act activity
     */
    public static void doWindowTurnOnBlueTooth(Activity act) {
        // 请求打开 Bluetooth
        Intent requestBluetoothOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        // 设置 Bluetooth 设备可以被其它 Bluetooth 设备扫描到
        requestBluetoothOn.setAction(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        // 设置 Bluetooth 设备可见时间
        requestBluetoothOn.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, BLUETOOTH_DISCOVERABLE_DURATION);
        // 请求开启 Bluetooth
        act.startActivityForResult(requestBluetoothOn, REQUEST_CODE_BLUETOOTH_ON);
    }

    /**
     * 跳转到系统 Bluetooth 设置
     *
     * @param act activity
     */
    public static void toBlueToothSetting(Activity act) {
        act.startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
    }
}
