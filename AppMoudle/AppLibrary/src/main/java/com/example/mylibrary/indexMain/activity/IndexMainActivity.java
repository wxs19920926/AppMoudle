package com.example.mylibrary.indexMain.activity;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mylibrary.R;
import com.example.mylibrary.indexMain.fragment.Fragment1;
import com.example.mylibrary.indexMain.fragment.Fragment2;
import com.example.mylibrary.indexMain.fragment.Fragment3;

import java.lang.reflect.Method;

/**
 * Created by wxs on 2018/2/8.
 */

public class IndexMainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, LifecycleRegistryOwner {

    private SearchView mSearchView;
    private SearchView.SearchAutoComplete mSearchAutoComplete;

    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

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

//        //设置menu
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.inflateMenu(R.menu.optionsmenu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearchAutoComplete.isShown()) {
                    try {
                        mSearchAutoComplete.setText("");
                        Method method = mSearchView.getClass().getDeclaredMethod("onCloseClicked");
                        method.setAccessible(true);
                        method.invoke(mSearchView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    finish();
                }
            }
        });

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        MenuItem searchItem = menu.findItem(R.id.ab_search);

        //通过MenuItem得到SearchView
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        AppCompatImageView search=(AppCompatImageView)mSearchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        search.setImageResource(R.drawable.ic_search);
        AppCompatImageView close=(AppCompatImageView)mSearchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        close.setImageResource(R.drawable.ic_close);
        mSearchAutoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(R.id.search_src_text);
        mSearchView.setQueryHint("搜索本地歌曲by code");

        //设置输入框提示文字样式
        mSearchAutoComplete.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
        mSearchAutoComplete.setTextColor(getResources().getColor(android.R.color.background_light));
        mSearchAutoComplete.setTextSize(14);
        //设置触发查询的最少字符数（默认2个字符才会触发查询）
        mSearchAutoComplete.setThreshold(1);

        //设置搜索框有字时显示叉叉，无字时隐藏叉叉
        mSearchView.onActionViewExpanded();
        mSearchView.setIconified(true);

        //修改搜索框控件间的间隔（这样只是为了更加接近网易云音乐的搜索框）
        LinearLayout search_edit_frame = (LinearLayout) mSearchView.findViewById(R.id.search_edit_frame);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) search_edit_frame.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        search_edit_frame.setLayoutParams(params);

        //监听SearchView的内容
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

//                Cursor cursor = TextUtils.isEmpty(s) ? null : queryData(s);
//
////                if (mSearchView.getSuggestionsAdapter() == null) {
////                    mSearchView.setSuggestionsAdapter(new SimpleCursorAdapter(SearchViewActivity2.this, R.layout.item_layout, cursor, new String[]{"name"}, new int[]{R.id.text1}));
////                } else {
////                    mSearchView.getSuggestionsAdapter().changeCursor(cursor);
////                }
//                setAdapter(cursor);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    // 让菜单同时显示图标和文字
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
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
