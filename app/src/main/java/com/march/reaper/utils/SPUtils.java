package com.march.reaper.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.march.bean.AlbumDetail;
import com.march.bean.RecommendAlbumItem;
import com.march.bean.WholeAlbumItem;
import com.march.reaper.ReaperApplication;

/**
 * Created by march on 16/7/2.
 * sp
 */
public class SPUtils {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private final String SP_NAME = "Reaper";

    private SPUtils() {
        sp = ReaperApplication.get().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    private static SPUtils inst;

    public static SPUtils get() {
        if (inst == null) {
            synchronized (SPUtils.class) {
                if (inst == null) {
                    inst = new SPUtils();
                }
            }
        }
        return inst;
    }

    private String mTempKey;

    private void generateTypeKey(Class cls) {
        new Operate4Type() {
            @Override
            protected void isAlbumDetail() {
                mTempKey = AlbumDetail.class.getSimpleName();
            }

            @Override
            protected void isRecommendAlbumItem() {
                mTempKey = RecommendAlbumItem.class.getSimpleName();
            }

            @Override
            protected void isWholeAlbumItem() {
                mTempKey = WholeAlbumItem.class.getSimpleName();
            }
        }.operate(cls);
    }

    public String getTimeStamp(Class cls) {
        generateTypeKey(cls);
        return sp.getString(mTempKey, "000000000000");
    }

    public void putTimeStamp(Class cls, String currentTimeStamp) {
        generateTypeKey(cls);
        editor.putString(mTempKey, currentTimeStamp).apply();
    }

}