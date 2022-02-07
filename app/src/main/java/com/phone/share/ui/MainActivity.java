package com.phone.share.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.phone.share.R;
import com.phone.share.model.ShareModel;
import com.phone.share.utils.BluetoothManager;
import com.phone.share.utils.CommonUtil;
import com.phone.share.utils.ShareFileUtil;
import com.phone.share.utils.SystemShare;
import com.phone.share.utils.UriUtil;

import static com.phone.share.utils.BluetoothManager.turnOnBlueTooth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView btnSystemShare;
    private TextView btnActionBarShare;
    private TextView btnCustomShare;
    private TextView btnBlueToothShare;
    private int REQUEST_ENABLE_BT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //强制打开Bluetooth
//        if ((BluetoothManager.isBlueToothSupported())
//                && (!BluetoothManager.isBlueToothEnabled())) {
//            turnOnBlueTooth();
//        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initView();
    }

    private void initView() {
        btnSystemShare = findViewById(R.id.btnSystemShare);
        btnActionBarShare = findViewById(R.id.btnActionBarShare);
        btnCustomShare = findViewById(R.id.btnCustomShare);
        btnBlueToothShare = findViewById(R.id.btnBlueToothShare);
        btnSystemShare.setOnClickListener(this);
        btnActionBarShare.setOnClickListener(this);
        btnCustomShare.setOnClickListener(this);
        btnBlueToothShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBlueToothShare:
                startShare();
                break;
            case R.id.btnSystemShare:
                startActivity(SystemShareActivity.createIntent(this));
                break;
            case R.id.btnActionBarShare:
                //startActivity(MenuShareProviderActivity.createIntent(this));
                break;
            case R.id.btnCustomShare:
                startActivity(CustomShareActivity.createIntent(this));
                break;
        }
    }

    private void validateDeviceOpenBlueTooth() {
        if (BluetoothAdapter.getDefaultAdapter() == null) {
            Log.d("当前蓝牙是否开启==>", "当前设备不支持蓝牙");
            Toast.makeText(this, "当前设备不支持蓝牙！", Toast.LENGTH_SHORT).show();
            return;
        } else {
            //已开启蓝牙
            if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                Log.d("当前蓝牙是否开启==>", "当前蓝牙已开启");
                startShare();
            }
            //未开启蓝牙，去开启
            else {
                Toast.makeText(this, "您的设备没有开启蓝牙，请去开启！", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("提醒")
                        .setMessage("您还未开启蓝牙，请先开启")
                        .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                BluetoothManager.toBlueToothSetting(MainActivity.this);
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.create().show();
            }
        }
    }

    //分享app
    private void startShare() {
        ShareModel model = ShareFileUtil.getAloneApp(this, this.getPackageName());
        Uri fileUri = UriUtil.getUriFromFile(this, model.getFilePath());
        SystemShare.getInstance()
                .setContext(this)
                .setChooserTitle("应用分享")
                .setShareType(SystemShare.SHARE_APK_FILE)
                .setShareApkFile(fileUri)
                .showBlueToothForResult(000).build();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {//成功开启蓝牙
            startShare();
            Log.d("Share=OpenBlueTooth==>", "成功开启蓝牙");
        }
        if (resultCode == RESULT_CANCELED) {//未开启或用户拒绝开启蓝牙
            Log.d("Share=BlueToothFail==>", "开启蓝牙失败或用户拒绝");
        }
    }
}
