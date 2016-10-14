package com.march.reaper.ipresenter.impl;

import android.view.View;

import com.march.bean.WholeAlbumItem;
import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.SimpleRvAdapter;
import com.march.quickrvlibs.inter.OnItemClickListener;
import com.march.quickrvlibs.module.LoadMoreModule;
import com.march.reaper.R;
import com.march.reaper.common.API;
import com.march.reaper.imodel.WholeAlbumResponse;
import com.march.reaper.ipresenter.BaseNetFragmentPresenter;
import com.march.reaper.base.mvp.BaseView;
import com.march.reaper.base.activity.BaseReaperActivity;
import com.march.reaper.iview.activity.AlbumDetailActivity;
import com.march.reaper.utils.QueryUtils;

import java.util.List;

/**
 * Created by march on 16/7/13.
 * 发现界面,mPresenter
 */
public class SearchPresenterImpl extends BaseNetFragmentPresenter<SearchPresenterImpl.SearchView, WholeAlbumItem> {

    public SearchPresenterImpl(SearchView mView, BaseReaperActivity mContext) {
        super(mView, mContext);
    }

    public interface SearchView extends BaseView {

    }

    @Override
    protected void queryDbDatas() {

    }

    @Override
    public void justQuery() {
        if (checkCanQuery())
            queryNetDatas();
    }

    @Override
    public void queryNetDatas() {
        QueryUtils.get().query(API.GET_LUCKY + "?limit=10", WholeAlbumResponse.class, new QueryUtils.OnQueryOverListener<WholeAlbumResponse>() {
            @Override
            public void queryOver(WholeAlbumResponse rst) {
                List<WholeAlbumItem> data = rst.getData();
                handleDatasAfterQueryReady(data);
            }

            @Override
            public void error(Exception e) {

            }
        });
    }

    @Override
    protected void createRvAdapter() {
        mAdapter = new SimpleRvAdapter<WholeAlbumItem>(mContext, datas, R.layout.albumquery_item_album) {
            @Override
            public void bindData4View(RvViewHolder holder, WholeAlbumItem data, int pos) {
                holder.setImg(mContext, R.id.albumquery_item_iv, data.getAlbum_cover());
                holder.setVisibility(R.id.albumquery_item_tv, View.VISIBLE).setText(R.id.albumquery_item_tv, data.getAlbum_desc());
            }
        };

        mAdapter.addLoadMoreModule(mPreLoadNum, new LoadMoreModule.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener<RvViewHolder>() {
            @Override
            public void onItemClick(int pos, RvViewHolder holder) {
                AlbumDetailActivity.loadActivity4DetailShow(mContext, datas.get(pos));
            }
        });

        mRgv.setAdapter(mAdapter);
    }

    @Override
    public void switchMode() {

    }
}