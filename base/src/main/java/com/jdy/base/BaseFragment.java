package com.jdy.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Dale on 2017/9/25.
 * 项目名：Supa
 * 描述：Fragment 的基类
 */
public abstract class BaseFragment extends Fragment implements Action, OnClickListener {
    protected View view;
    private Toast mToast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getRootLayoutID() != 0) {
            view = inflater.inflate(getRootLayoutID(), container, false);
            return view;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(savedInstanceState);
        initView();
        setListener();
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 注意： R.id.toolbar_title 这个id必须自定义后 才可调用该方法
     */
    protected void setTitle(String title) {

    }

    protected void setTitle(@IdRes int id, String title) {
        ((TextView) getView(id)).setText(title);
    }

    public final <T extends View> T getView(@IdRes int id) {
        if (id == View.NO_ID) {
            return null;
        }
        return view.findViewById(id);
    }

    @Override
    public void showView(View view) {
        if (view.getVisibility() == View.INVISIBLE)
            view.setVisibility(View.VISIBLE);
    }

    @Override
    public void hiddenView(View view) {
        if (view.getVisibility() == View.VISIBLE)
            view.setVisibility(View.INVISIBLE);
    }
}
