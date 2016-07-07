package com.march.reaper;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.march.quickrvlibs.RvQuick;
import com.march.reaper.common.DaoHelper;

/**
 * Created by march on 16/6/6.
 * application
 */
public class ReaperApplication extends Application {

    private static ReaperApplication mInst;

    @Override
    public void onCreate() {
        super.onCreate();
        mInst = this;
        initRvQuick();
        DaoHelper.get().setupDatabase(this);
    }

    public static ReaperApplication get() {
        return mInst;
    }


    //    初始化QuickAdapter
    private void initRvQuick() {
        RvQuick.init(new RvQuick.QuickLoad() {
            @Override
            public void load(Context context, String url, ImageView view) {
                Glide.with(context).load(url).centerCrop().crossFade().into(view);
            }

            @Override
            public void loadSizeImg(Context context, String url, ImageView view, int w, int h, int placeHolder) {
                Glide.with(context).load(url).centerCrop().crossFade().into(view);
            }
        });
    }


}