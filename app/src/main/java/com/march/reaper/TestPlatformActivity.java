package com.march.reaper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.march.lib.core.common.Logger;
import com.march.lib.core.common.Toaster;
import com.march.lib.core.mvp.presenter.BasePresenter;
import com.march.lib.platform.Platform;
import com.march.lib.platform.exception.PlatformException;
import com.march.lib.platform.helper.Util;
import com.march.lib.platform.impl.QQPlatform;
import com.march.lib.platform.impl.WbPlatform;
import com.march.lib.platform.impl.WxPlatform;
import com.march.lib.platform.listener.OnQQLoginListener;
import com.march.lib.platform.listener.OnQQShareListener;
import com.march.lib.platform.listener.OnWbLoginListener;
import com.march.lib.platform.listener.OnWbShareListener;
import com.march.lib.platform.listener.OnWxLoginListener;
import com.march.lib.platform.listener.OnWxShareListener;
import com.march.lib.platform.tencent.QQUserInfo;
import com.march.lib.platform.weibo.WbUserInfo;
import com.march.lib.platform.wx.WxUserInfo;
import com.march.reaper.base.activity.BaseReaperActivity;
import com.march.reaper.helper.ImageHelper;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

public class TestPlatformActivity extends BaseReaperActivity {

    @Bind(R.id.wx_ly)
    View wxLy;
    @Bind(R.id.qq_ly)
    View qqLy;
    @Bind(R.id.wb_ly)
    View wbLy;

    @Bind(R.id.qq_to_zone)
    Switch qq_toZone;
    @Bind(R.id.wx_to_zone)
    Switch wx_toZone;

    @Bind(R.id.info_tv)
    TextView mInfoTv;


    private WxPlatform mWxPlatform;
    private WbPlatform mWbPlatform;
    private QQPlatform mQqPlatform;

    private String localImagePath;
    private String netImagePath;
    private String netVideoPath;
    private String netMusicPath;
    private String localVideoPath;
    private String localGifPath;
    private String testWebUrl;
    private Bitmap testBit;

    @Override
    public void onInitDatas() {
        super.onInitDatas();
        mWxPlatform = Platform.getInst().wx();
        mWbPlatform = Platform.getInst().wb();
        mQqPlatform = Platform.getInst().qq();

        localImagePath = new File(Environment.getExternalStorageDirectory(), "1.jpg").getAbsolutePath();
        localVideoPath = new File(Environment.getExternalStorageDirectory(), "4.mp4").getAbsolutePath();
        localGifPath = new File(Environment.getExternalStorageDirectory(), "3.gif").getAbsolutePath();
        testBit = res2Bitmap();
        netVideoPath = "http://7xtjec.com1.z0.glb.clouddn.com/export.mp4";
        netImagePath = "http://7xtjec.com1.z0.glb.clouddn.com/token.png";
        netMusicPath = "http://7xtjec.com1.z0.glb.clouddn.com/test_music.mp3";
        testWebUrl = "http://chendongmarch.github.io/";


        mWxPlatform.setWxShareListener(new OnWxShareListener() {
            @Override
            public void onSuccess() {
                Toaster.get().show(mContext, "分享成功");
            }

            @Override
            public void onFailure(PlatformException e) {
                Toaster.get().show(mContext, "分享失败");
            }

            @Override
            public void onCancel() {
                Toaster.get().show(mContext, "分享取消");
            }
        });

        mQqPlatform.setOnQQShareListener(new OnQQShareListener() {
            @Override
            public void onSuccess() {
                Toaster.get().show(mContext, "分享成功");
            }

            @Override
            public void onFailure(PlatformException e) {
                Toaster.get().show(mContext, "分享失败 " + e.getQQError().errorDetail);
            }

            @Override
            public void onCancel() {
                Toaster.get().show(mContext, "分享取消");
            }

        });
        mWbPlatform.setWbShareListener(new OnWbShareListener() {
            @Override
            public void onSuccess() {
                Toaster.get().show(mContext, "分享成功");
            }

            @Override
            public void onFailure(PlatformException e) {
                Toaster.get().show(mContext, "分享失败");
                e.getWbError().printStackTrace();
            }

            @Override
            public void onCancel() {
                Toaster.get().show(mContext, "分享取消");
            }
        });
    }

    @Override
    public void onInitViews(View view, Bundle saveData) {
        super.onInitViews(view, saveData);
        mWbPlatform.handleWeiboResponse(mActivity, saveData);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mWbPlatform.onNewIntent(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_platform;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected String[] getPermission2Check() {
        return new String[0];
    }

    @Override
    protected boolean isInitTitle() {
        return false;
    }

    @OnClick({R.id.wx, R.id.wb, R.id.qq})
    public void clickTop(View view) {
        switch (view.getId()) {
            case R.id.wx:
                wbLy.setVisibility(View.GONE);
                qqLy.setVisibility(View.GONE);
                wxLy.setVisibility(View.VISIBLE);
                break;
            case R.id.wb:
                wbLy.setVisibility(View.VISIBLE);
                qqLy.setVisibility(View.GONE);
                wxLy.setVisibility(View.GONE);
                break;
            case R.id.qq:
                wbLy.setVisibility(View.GONE);
                qqLy.setVisibility(View.VISIBLE);
                wxLy.setVisibility(View.GONE);
                break;
        }
    }

    private Bitmap res2Bitmap() {
        return BitmapFactory.decodeResource(getResources(), R.drawable.bg_appstart);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.e(requestCode);
        mQqPlatform.onActivityResult(requestCode, resultCode, data);
        mWbPlatform.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private int wxShareType() {
        return wx_toZone.isChecked() ? WxPlatform.ZONE : WxPlatform.CHAT;
    }

    private int qqShareType() {
        return qq_toZone.isChecked() ? QQPlatform.ZONE : QQPlatform.CHAT;
    }


    @OnClick({R.id.wx_login,
            R.id.wx_share_text,
            R.id.wx_share_image_bit,
            R.id.wx_share_image_local,
            R.id.wx_share_image_net,
            R.id.wx_share_gif_local,
            R.id.wx_share_video_local,
            R.id.wx_share_video_net,
            R.id.wx_share_music,
            R.id.wx_share_web})
    public void clickWx(View view) {
        switch (view.getId()) {
            case R.id.wx_login:
                mWxPlatform.login(mContext, new OnWxLoginListener() {
                    @Override
                    public void onSucceed(WxUserInfo info) {
                        Logger.e(info);
                    }

                    @Override
                    public void onException(PlatformException e) {
                        Logger.e(e.getMessage());
                    }
                });
                break;
            case R.id.wx_share_text:
                mWxPlatform.shareText("测试", wxShareType());
                break;
            case R.id.wx_share_image_bit:
                mWxPlatform.shareImage(testBit, wxShareType());
                break;
            case R.id.wx_share_image_local:
                mWxPlatform.shareImage(localImagePath, wxShareType());
                break;
            case R.id.wx_share_image_net:
                ImageHelper.downloadPic(mContext, netImagePath, new ImageHelper.OnDownloadOverHandler() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        mWxPlatform.shareImage(bitmap, wxShareType());
                    }
                });
                break;
            case R.id.wx_share_gif_local:
                mWxPlatform.shareGif(localGifPath, wxShareType());
                break;
            case R.id.wx_share_video_net:
                mWxPlatform.shareVideo(netVideoPath, "video title", "video desc", res2Bitmap(), wxShareType());
                break;
            case R.id.wx_share_video_local:
                Toaster.get().show(mContext, "无法分享本地视频，会导致点击之后打不开，音乐网页也是如此");
//                mWxPlatform.shareVideo(localVideoPath, "video title", "video desc", res2Bitmap(), WxPlatform.CHAT);
                break;
            case R.id.wx_share_music:
                mWxPlatform.shareMusic(netMusicPath, "music title", "music desc", res2Bitmap(), wxShareType());
                break;
            case R.id.wx_share_web:
                mWxPlatform.shareWebPage(testWebUrl, "web title", "web desc", res2Bitmap(), wxShareType());
                break;
        }
    }


    @OnClick({R.id.qq_login,
            R.id.qq_share_image,
            R.id.qq_share_image_text,
            R.id.qzone_share_image_text,
            R.id.qq_share_app,
            R.id.qq_share_audio
    })
    public void clickQQ(View view) {
        switch (view.getId()) {
            case R.id.qq_login:
                mQqPlatform.login(mActivity, new OnQQLoginListener() {
                    @Override
                    public void onException(PlatformException e) {
                        Logger.e(e.getQQError().errorMessage + "  " + e.getQQError().errorDetail);
                    }

                    @Override
                    public void onSucceed(QQUserInfo userInfo) {
                        Logger.e(userInfo);
                        mInfoTv.setText(userInfo.toString());
                    }
                });
                break;
            case R.id.qq_share_image:
                mQqPlatform.shareLocalImage(mActivity, localImagePath, qqShareType());
                break;
            case R.id.qq_share_image_text:
                mQqPlatform.shareImageText(mActivity, "title", "summary", testWebUrl, localImagePath, qqShareType());
                break;
            case R.id.qq_share_app:
                mQqPlatform.shareApp(mActivity, "title", "summary", testWebUrl, localImagePath, qqShareType());
                break;
            case R.id.qq_share_audio:
                mQqPlatform.shareAudio(mActivity, "title", "summary", testWebUrl, localImagePath, netMusicPath, qqShareType());
                break;
            case R.id.qzone_share_image_text:
                mQqPlatform.shareImageTextQzone(mActivity, "title", "summary", testWebUrl, localImagePath);
                break;
        }
    }

    @OnClick({R.id.wb_login,
            R.id.wb_share_text,
            R.id.wb_share_web,
            R.id.wb_share_music,
            R.id.wb_share_video,
            R.id.wb_share_voice,
            R.id.wb_share_image_local,
            R.id.wb_share_image_net,
            R.id.wb_share_file,
            R.id.wb_auth
    })
    public void clickWeibo(View view) {
        switch (view.getId()) {
            case R.id.wb_auth:
                mWbPlatform.justAuth(mActivity, new Runnable() {
                    @Override
                    public void run() {
                        Logger.e("授权成功");
                    }
                });
                break;
            case R.id.wb_login:
                mWbPlatform.login(mActivity, new OnWbLoginListener() {
                    @Override
                    public void onSucceed(WbUserInfo info) {
                        mInfoTv.setText(info.toString());
                        Logger.e(info.toString());
                    }

                    @Override
                    public void onCancel() {
                        Toaster.get().show(mContext, "取消");

                    }

                    @Override
                    public void onException(PlatformException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case R.id.wb_share_text:
                mWbPlatform.shareText(mActivity, "test");
                break;
            case R.id.wb_share_web:
                mWbPlatform.shareWebpage(mActivity, "textContent", "title", "desc", res2Bitmap(), testWebUrl, "defaultText");
                break;
            case R.id.wb_share_music:
                mWbPlatform.shareWebpage(mActivity, "textContent", "title", "desc", res2Bitmap(), netMusicPath, "defaultText");
                break;
            case R.id.wb_share_video:
                mWbPlatform.shareWebpage(mActivity, "textContent", "title", "desc", res2Bitmap(), netVideoPath, "defaultText");
                break;
            case R.id.wb_share_voice:
                mWbPlatform.shareWebpage(mActivity, "textContent", "title", "desc", res2Bitmap(), netMusicPath, "defaultText");
                break;
            case R.id.wb_share_image_local:
                mWbPlatform.shareImage(mActivity, "text", res2Bitmap());
                break;
            case R.id.wb_share_image_net:
                // 需要申请高级权限
                mWbPlatform.shareNetImage(mActivity, "text", netImagePath);
                break;
            case R.id.wb_share_file:
                File file = new File(localGifPath);
                mWbPlatform.shareFile(mActivity, "text", Util.getOutputStreamFromFile(file));
                break;
        }
    }
}
