package com.march.reaper.iview.activity;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.march.reaper.R;
import com.march.reaper.base.activity.BaseReaperActivity;
import com.march.reaper.base.mvp.life.PresenterLoader;
import com.march.reaper.helper.Logger;

import butterknife.Bind;
import butterknife.OnClick;

public class GuardActivity extends BaseReaperActivity {

    @Bind(R.id.et_guard)
    EditText mGuardEt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guard;
    }

    @Override
    protected void onInitViews(Bundle save) {
        super.onInitViews(save);
        Logger.e("guard start");
    }

    @Override
    protected void onInitEvents() {
        super.onInitEvents();
        mGuardEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("91109123"))
                    startActivity(AppStartActivity.class);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.btn_check)
    public void clickBtn(View view) {
        if (getText(mGuardEt).equals("91109123"))
            startActivity(AppStartActivity.class);
    }

    @Override
    protected String[] getPermission2Check() {
        return new String[0];
    }

    @Override
    protected boolean isInitTitle() {
        return false;
    }

    @Override
    protected PresenterLoader createPresenterLoader() {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }
}