package com.march.reaper.mvp.contact;

import com.march.reaper.mvp.presenter.BasePresenter;
import com.march.reaper.mvp.ui.BaseView;

/**
 * com.march.reaper.mvp.contact
 * Created by chendong on 16/7/19.
 * desc :
 */
public interface LoginContact {

    interface LoginView extends BaseView {
    }

    interface LoginPresenter extends BasePresenter {
        void checkToMyServer(String phone,String pwd);
    }
}