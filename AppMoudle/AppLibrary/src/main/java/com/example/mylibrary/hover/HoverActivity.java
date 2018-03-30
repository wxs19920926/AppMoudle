package com.example.mylibrary.hover;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.mylibrary.R;
import com.example.mylibrary.weight.hovermenu.bean.DropdownItemObject;
import com.example.mylibrary.weight.hovermenu.view.DropdownButton;
import com.example.mylibrary.weight.hovermenu.view.DropdownListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxs on 2018/3/30.
 */

public class HoverActivity extends AppCompatActivity implements DropdownListView.Container {

    DropdownButton chooseType;
    DropdownButton chooseLanguage;
    RecyclerView mRecyclerView;
    View mask;
    DropdownListView dropdownType;
    DropdownListView dropdownLanguage;

    private DropdownListView currentDropdownList;
    Animation dropdown_in, dropdown_out, dropdown_mask_out;

    private List<DropdownItemObject> chooseTypeData = new ArrayList<>();//选择类型
    private List<DropdownItemObject> chooseLanguageData = new ArrayList<>();//选择语言

    private List<String> mData;
//    private DemoAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hover);

        chooseType = (DropdownButton) findViewById(R.id.chooseType);
        chooseLanguage = (DropdownButton) findViewById(R.id.chooseLanguage);
//        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mask = findViewById(R.id.mask);
        dropdownType = (DropdownListView) findViewById(R.id.dropdownType);
        dropdownLanguage = (DropdownListView) findViewById(R.id.dropdownLanguage);
        dropdownLanguage = (DropdownListView) findViewById(R.id.dropdownLanguage);

        setTitle("淡漠de人生");

        dropdown_in = AnimationUtils.loadAnimation(this, R.anim.dropdown_in);
        dropdown_out = AnimationUtils.loadAnimation(this,R.anim.dropdown_out);
        dropdown_mask_out = AnimationUtils.loadAnimation(this,R.anim.dropdown_mask_out);
        init();
        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });

        mData = new ArrayList<>();
        for(int i=0; i<30;i++){
            mData.add("标题"+i);
        }
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
//        mAdapter = new DemoAdapter(this, R.layout.item_demo, mData);
//        initAdapter();
    }

    @Override
    public void show(DropdownListView view) {
        if (currentDropdownList != null) {
            currentDropdownList.clearAnimation();
            currentDropdownList.startAnimation(dropdown_out);
            currentDropdownList.setVisibility(View.GONE);
            currentDropdownList.button.setChecked(false);
        }
        currentDropdownList = view;
        mask.clearAnimation();
        mask.setVisibility(View.VISIBLE);
        currentDropdownList.clearAnimation();
        currentDropdownList.startAnimation(dropdown_in);
        currentDropdownList.setVisibility(View.VISIBLE);
        currentDropdownList.button.setChecked(true);
    }

    @Override
    public void hide() {
        if (currentDropdownList != null) {
            currentDropdownList.clearAnimation();
            currentDropdownList.startAnimation(dropdown_out);
            currentDropdownList.button.setChecked(false);
            mask.clearAnimation();
            mask.startAnimation(dropdown_mask_out);
        }
        currentDropdownList = null;
    }

    @Override
    public void onSelectionChanged(DropdownListView view) {
        if (view == dropdownType) {

        }
    }

    void reset() {
        chooseType.setChecked(false);
        chooseLanguage.setChecked(false);

        dropdownType.setVisibility(View.GONE);
        dropdownLanguage.setVisibility(View.GONE);
        mask.setVisibility(View.GONE);

        dropdownType.clearAnimation();
        dropdownLanguage.clearAnimation();
        mask.clearAnimation();
    }

    void init() {
        reset();
        chooseTypeData.add(new DropdownItemObject("全部分类", 0, "全部分类"));
        chooseTypeData.add(new DropdownItemObject("分类1", 1, "分类1"));
        chooseTypeData.add(new DropdownItemObject("分类2", 2, "分类2"));
        chooseTypeData.add(new DropdownItemObject("分类3", 3, "分类3"));
        chooseTypeData.add(new DropdownItemObject("分类4", 4, "分类4"));
        dropdownType.bind(chooseTypeData, chooseType, this, 0);

        chooseLanguageData.add(new DropdownItemObject("全部语言", 0, "全部语言"));
        chooseLanguageData.add(new DropdownItemObject("语言1", 1, "语言1"));
        chooseLanguageData.add(new DropdownItemObject("语言2", 2, "语言2"));
        chooseLanguageData.add(new DropdownItemObject("语言3", 3, "语言3"));
        chooseLanguageData.add(new DropdownItemObject("语言4", 4, "语言4"));
        dropdownLanguage.bind(chooseLanguageData, chooseLanguage, this, 0);

        dropdown_mask_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (currentDropdownList == null) {
                    reset();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 设置RecyclerView属性
     */
//    private void initAdapter() {
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mAdapter.openLoadAnimation();
//        mRecyclerView.setAdapter(mAdapter);//设置adapter
//        //设置item点击事件
//        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//
//            }
//        });
//    }
//
//    class DemoAdapter extends BaseQuickAdapter<String> {
//        int mLayoutId;
//        public DemoAdapter(Context context, int LayoutId, List<String> str) {
//            super(context, LayoutId, str);
//            mLayoutId = LayoutId;
//        }
//
//        @Override
//        public void convert(BaseViewHolder helper, String str) {
//            helper.setText(R.id.tvTitle,str);
//        }
//    }


}
