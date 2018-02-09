package com.example.mylibrary.refresh;

import android.arch.lifecycle.LifecycleActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.mylibrary.R;
import com.example.mylibrary.adapter.BaseRecyclerAdapter;
import com.example.mylibrary.adapter.SmartViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import static android.R.layout.simple_list_item_2;

/**
 * Created by wxs on 2018/2/8.
 */

public class RefershActivity extends LifecycleActivity {
    SmartRefreshLayout refreshLayout;
    RecyclerView recyclerview;
    Toolbar toolbar;
    private BaseRecyclerAdapter<Void> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refersh_basic);
        findViewById();
        initView();
    }

    public void findViewById() {
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    public void initView() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerview.setAdapter(mAdapter = new BaseRecyclerAdapter(initData(),simple_list_item_2) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, Object model, int position) {
                holder.text(android.R.id.text1, String.format(Locale.CHINA, "第%02d条数据", position));
                holder.text(android.R.id.text2, String.format(Locale.CHINA, "这是测试的第%02d条数据", position));
                holder.textColorId(android.R.id.text2, R.color.sandybrown);
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.refresh(initData());
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(false);
                    }
                }, 2000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mAdapter.getItemCount() > 60) {
                            Toast.makeText(getApplication(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                            refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                        } else {
                            mAdapter.loadMore(initData());
                            refreshLayout.finishLoadMore();
                        }
                    }
                }, 2000);
            }
        });
    }


    private Collection<Void> initData() {
        return Arrays.asList(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
    }
}
