package com.jdy.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * Created by Dale on 2017/8/30.
 * 项目名：Supa
 * 描述：所有Activity的基类
 * 统一属性、统一接口、统一方法
 */
public abstract class BaseActivity extends AppCompatActivity implements Action,OnClickListener {
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInit();
        if (getRootLayoutID() != 0) {
            setContentView(getRootLayoutID());
            initData(savedInstanceState);
            initView();
            setListener();
        }
    }

    protected abstract void beforeInit();

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void showToast(String desc) {
        if (mToast == null) {
            mToast = Toast.makeText(this.getApplicationContext(), desc, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(desc);
        }
        mToast.show();
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

    @Override
    public void onClick(View v) {

    }
}
