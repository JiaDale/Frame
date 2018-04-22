package com.jdy.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;

interface Action {

    int getRootLayoutID();

    void initData(Bundle savedInstanceState);

    void initView();

    void setListener();

    void showToast(String desc);

    void showView(View view);

    void hiddenView(View view);

    <T extends View> T getView(@IdRes int id);
}
