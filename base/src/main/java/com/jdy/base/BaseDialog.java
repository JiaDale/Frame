package com.jdy.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


public abstract class BaseDialog extends Dialog implements Action, OnClickListener{
    protected Context mContext;

    protected BaseDialog(Context context) {
        this(context, R.style.Base_Dialog);
    }

    protected BaseDialog(Context context, int styleId) {
        super(context, styleId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getRootLayoutID() != 0) {
            setContentView(LayoutInflater.from(mContext).inflate(getRootLayoutID(), null));
            initData(savedInstanceState);
            initView();
            setListener();
        }
    }

    /**
     * 注意： R.id.toolbar_title 这个id必须自定义后 才可调用该方法
     */
    protected void setTitle(String title) {

    }

    protected void setTitle(@IdRes int id, String title) {
        ((TextView) getView(id)).setText(title);
    }

    @Override
    public final <T extends View> T getView(@IdRes int id) {
        if (id == View.NO_ID) {
            return null;
        }
        return findViewById(id);
    }

    protected void show(View view) {
        if (view.getVisibility() == View.INVISIBLE)
            view.setVisibility(View.VISIBLE);
    }

    protected void hidden(View view) {
        if (view.getVisibility() == View.VISIBLE)
            view.setVisibility(View.INVISIBLE);
    }

    protected void setOnClickListener(@IdRes int id) {
        getView(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDialog.this.onClick(BaseDialog.this, v.getId());
            }
        });
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
