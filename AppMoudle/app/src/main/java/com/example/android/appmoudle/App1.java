package com.example.android.appmoudle;

import android.app.Application;
import android.content.Context;

import com.example.mylibrary.Application.App;
import com.example.mylibrary.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;

/**
 *
 * Created by SCWANG on 2017/6/11.
 */

public class App1 extends App {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
