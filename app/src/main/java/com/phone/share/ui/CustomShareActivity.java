package com.phone.share.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.phone.share.R;
import com.phone.share.model.ShareModel;
import com.phone.share.utils.SystemShare;
import com.phone.share.utils.UriUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 自定义分享页面
 *
 * @author WangZhenKai
 * @date 2018/11/23 15:15
 */
public class CustomShareActivity extends AppCompatActivity implements ShareDialog.OnItemClickListener, View.OnClickListener {
    private Button btnShareText;
    private Button btnShareImage;
    private Button btnShareMultiple;
    private Button btnShareFile;
    private Intent shareIntent;
    private Dialog dialog;
    public static final int REQUEST_SELECT_FILE = 100;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, CustomShareActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("自定义分享");
        setContentView(R.layout.activity_custom_share);
        initView();

    }

    private void initView() {
        btnShareText = findViewById(R.id.btnShareText);
        btnShareImage = findViewById(R.id.btnShareImage);
        btnShareMultiple = findViewById(R.id.btnShareMultiple);
        btnShareFile = findViewById(R.id.btnShareFile);
        btnShareFile.setOnClickListener(this);
        btnShareText.setOnClickListener(this);
        btnShareMultiple.setOnClickListener(this);
        btnShareImage.setOnClickListener(this);
    }

    @Override
    public void onItemClick(ShareModel shareBean) {
        shareIntent.setComponent(new ComponentName(shareBean.getPackageName(), shareBean.getClassName()));
        startActivity(shareIntent);
        dialog.dismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnShareText: {
                shareIntent = new SystemShare().setText("This is Share Content!").setChooserTitle("分享")
                        .setShareType(SystemShare.SHARE_TEXT).build().getShareIntent();
                showDialog();
            }
            break;
            case R.id.btnShareImage: {
                Uri fileUri = UriUtil.getUriFromFile(this, "/sdcard/DCIM/Camera/IMG_20181126_012932.jpg");
                shareIntent = new SystemShare().setChooserTitle("分享").setShareType(SystemShare.SHARE_FILE).setShareFiles(Arrays.asList(fileUri)).build().getShareIntent();
                showDialog();
            }
            break;
            case R.id.btnShareMultiple: {
                Uri fileUri = UriUtil.getUriFromFile(this, "/sdcard/DCIM/Camera/IMG_20181126_012933.jpg");
                Uri fileUri2 = UriUtil.getUriFromFile(this, "/sdcard/DCIM/Camera/IMG_20181126_012934.jpg");
                ArrayList<Uri> uris = new ArrayList<>();
                uris.add(fileUri);
                uris.add(fileUri2);
                shareIntent = new SystemShare().setChooserTitle("分享").setShareType(SystemShare.SHARE_MULTIPLE_FILES).setShareFiles(uris).build().getShareIntent();
                showDialog();
            }
            break;
            case R.id.btnShareFile:
                startActivityForResult(FileSelectActivity.createIntent(this, Environment.getExternalStorageDirectory().getAbsolutePath()), REQUEST_SELECT_FILE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SELECT_FILE:
                    Uri fileUri = UriUtil.getUriFromFile(this, data.getStringExtra(FileSelectActivity.EXTRA_GET_FILE));
                    shareIntent = new SystemShare().setChooserTitle("分享").setShareType(SystemShare.SHARE_FILE).setShareFiles(Arrays.asList(fileUri)).build().getShareIntent();
                    showDialog();
                    break;
            }
        }
    }

    private void showDialog() {
        dialog = new ShareDialog(this, this);
        dialog.show();
    }
}
