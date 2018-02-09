package com.example.mylibrary.indexMain.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleActivity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.example.mylibrary.R;
import com.example.mylibrary.indexMain.fragment.Fragment1;
import com.example.mylibrary.indexMain.fragment.Fragment2;
import com.example.mylibrary.indexMain.fragment.Fragment3;
import com.example.mylibrary.util.StatusBarUtil;

/**
 * Created by wxs on 2018/2/8.
 */

public class IndexMainActivity extends LifecycleActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private enum TabFragment {
        practice(R.id.navigation_practice,Fragment1.class),
        styles(R.id.navigation_style,Fragment2.class),
        using(R.id.navigation_using,Fragment3.class)
        ;

        private Fragment fragment;
        private final int menuId;
        private final Class<? extends Fragment> clazz;

        TabFragment(@IdRes int menuId, Class<? extends Fragment> clazz) {
            this.menuId = menuId;
            this.clazz = clazz;
        }

        @NonNull
        public Fragment fragment() {
            if (fragment == null) {
                try {
                    fragment = clazz.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    fragment = new Fragment();
                }
            }
            return fragment;
        }

        public static TabFragment from(int itemId) {
            for (TabFragment fragment : values()) {
                if (fragment.menuId == itemId) {
                    return fragment;
                }
            }
            return styles;
        }

        public static void onDestroy() {
            for (TabFragment fragment : values()) {
                fragment.fragment = null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_main);

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
//        navigation.setSelectedItemId(R.id.navigation_style);

        ViewPager viewPager = (ViewPager) findViewById(R.id.content);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return TabFragment.values().length;
            }
            @Override
            public Fragment getItem(int position) {
                return TabFragment.values()[position].fragment();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                navigation.setSelectedItemId(TabFragment.values()[position].menuId);
            }
        });

        //状态栏透明和间距处理
//        StatusBarUtil.immersive(this, 0xff000000, 0.1f);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TabFragment.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        ((ViewPager) findViewById(R.id.content)).setCurrentItem(TabFragment.from(item.getItemId()).ordinal());
//        getSupportFragmentManager()
//                .beginTransaction()
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .replace(R.id.content,TabFragment.from(item.getItemId()).fragment())
//                .commit();
        return true;
    }
}
