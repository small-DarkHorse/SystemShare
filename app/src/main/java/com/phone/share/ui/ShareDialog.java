package com.phone.share.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.share.R;
import com.phone.share.inter.OnItemClickListener;
import com.phone.share.model.ShareModel;
import com.phone.share.utils.PxUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : ShareDialog
 * @Author : WangZhenKai
 * @Date : 2021/1/4
 * @Description : 自定义分享对话框
 */
public class ShareDialog extends Dialog implements AdapterView.OnItemClickListener {
    private TextView tvTitle;
    private GridView gridView;

    private List<ShareModel> mShareApps;
    private OnItemClickListener mItemClickListener;

    protected ShareDialog(Context context) {
        super(context, R.style.MyDialog);

    }

    public ShareDialog(Context context, OnItemClickListener mItemClickListener) {
        super(context, R.style.MyDialog);
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share);
        initView();
    }

    @Override
    public void onItemClick(AdapterView<?> view, View view1, int i, long l) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(mShareApps.get(i));
        }
    }

    private void initView() {
        tvTitle = findViewById(R.id.tvTitle);
        gridView = findViewById(R.id.gridView);
        GridAdapter mAdapter = new GridAdapter();
        mShareApps = getCanableShareApp(getContext(), "image/*", null);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(this);
        mAdapter.notifyDataSetChanged();

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        params.width = width;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        window.setWindowAnimations(R.style.BottomDialog_Animation);
    }

    public List<ShareModel> getCanableShareApp(Context context, String mimeType, String[] shareFilter) {
        List<ShareModel> shareModels = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(mimeType);
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (int i = 0; i < resolveInfos.size(); i++) {
            ResolveInfo resolveInfo = resolveInfos.get(i);
            PackageManager pm = context.getPackageManager();
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            ShareModel shareModel = new ShareModel();
            shareModel.setAppName(activityInfo.loadLabel(pm).toString());
            shareModel.setPackageName(activityInfo.packageName);
            shareModel.setClassName(activityInfo.name);
            shareModel.setIcon(activityInfo.loadIcon(pm));

            if (shareFilter != null && shareFilter.length > 0) {
                for (int j = 0; j < shareFilter.length; j++) {
                    if (shareModel.getPackageName().equals(shareFilter[j])) {
                        shareModels.add(shareModel);
                    }
                }
            } else {
                shareModels.add(shareModel);
            }
        }
        return shareModels;
    }

    private class GridAdapter extends BaseAdapter {

        private int iconWidth = 0;

        public GridAdapter() {
            WindowManager wm = (WindowManager) getContext()
                    .getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            int height = wm.getDefaultDisplay().getHeight();
            iconWidth = (width - PxUtils.dp2px(getContext(), 10) * 2 - PxUtils.dp2px(getContext(), 5) * 6) / 5;
        }

        @Override
        public int getCount() {
            return mShareApps == null ? 0 : mShareApps.size();
        }

        @Override
        public Object getItem(int i) {
            return mShareApps.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup group) {
            ViewHolder holder = null;
            if (view != null) {
                holder = (ViewHolder) view.getTag();
            } else {
                view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_dialog_app, null, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }
            holder.ivIcon.setImageDrawable(mShareApps.get(i).getIcon());
            holder.ivIcon.setScaleType(ImageView.ScaleType.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(iconWidth, iconWidth);
            holder.ivIcon.setLayoutParams(params);
            holder.tvAppName.setText(mShareApps.get(i).getAppName());
            return view;
        }

        class ViewHolder {
            private ImageView ivIcon;
            private TextView tvAppName;

            public ViewHolder(View view) {
                ivIcon = view.findViewById(R.id.ivIcon);
                tvAppName = view.findViewById(R.id.tvAppName);
            }

        }
    }
}
