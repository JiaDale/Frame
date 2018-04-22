package com.jdy.base.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

public class ResourceUtil {
    // R文件的对象
    private static Resources resources;
    private static String pkgName;


    public static void init(Context context) {
        pkgName = context.getPackageName();
        resources = context.getResources();
    }

    public static int checkExistID(int id){
        return checkExistID(id, "Couldn't find the ID that for the View!");
    }

    public static int checkExistID(int id, String message){
        if (id == View.NO_ID){
            throw new NullPointerException(message);
        }
        return id;
    }

    public static String getString(int resId) {
        return checkResourceNotNull(resources).getString(resId);
    }

    public static int getColor(int resId) {
        return checkResourceNotNull(resources).getColor(resId);
    }

    public static float getDimen(int resId) {
        return checkResourceNotNull(resources).getDimension(resId);
    }

    private static Resources checkResourceNotNull(Resources resources) {
        if (resources == null)
            throw new NullPointerException("You should init the util before!");
        return resources;
    }
}
