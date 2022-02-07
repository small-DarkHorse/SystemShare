package com.phone.share.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import java.lang.ref.WeakReference;

/**
 *
 * @author WangZhenKai
 * @since 2019-8-15
 */
public class ActDialog extends Dialog {
    private WeakReference<Activity> mWeakReference;

    public ActDialog(Context context) {
        super(context);
        mWeakReference = new WeakReference<Activity>((Activity) context);
        setOwnerActivity((Activity) context);
    }

    public ActDialog(Context context, int theme) {
        super(context, theme);
        mWeakReference = new WeakReference<Activity>((Activity) context);
        setOwnerActivity((Activity) context);
    }

    protected ActDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mWeakReference = new WeakReference<Activity>((Activity) context);
        setOwnerActivity((Activity) context);
    }

    @Override
    public void show() {
        if (UriUtil.isActivityFinished(getOwnerActivity()) || UriUtil.isActivityFinished(
                mWeakReference.get())) {
            return;
        }
        super.show();
    }
}
