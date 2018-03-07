package com.example.mylibrary.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.mylibrary.R;
import com.example.mylibrary.callBack.PopCallBack;

import java.util.ArrayList;


/**
 * Created by wxs on 2016/8/25.
 */
public class DateTimePickerDialog<E> extends Dialog {
    static OnSelectSetListener OnSelectSetListener;

    public DateTimePickerDialog(Context context) {
        super(context);
    }

    public DateTimePickerDialog(Context context, int theme) {
        super(context, theme);
    }

    public DateTimePickerDialog(Context context, String[] str, String normalDialog_title) {
        super(context);
        Builder builder = new Builder(context);
        builder.setNormalDialog_title(normalDialog_title);
        builder.setStr_old(str);
        builder.create().show();
    }

    public DateTimePickerDialog(Context context, ArrayList<E> str, String normalDialog_title) {
        super(context);
        Builder builder = new Builder(context);
        builder.setNormalDialog_title(normalDialog_title);
        builder.setStr(str);
        builder.create().show();
    }

    public void setOnSelectTimeSetListener(OnSelectSetListener<String> OnSelectSetListener) {
        DateTimePickerDialog.OnSelectSetListener= OnSelectSetListener;
    }


    public interface  OnSelectSetListener <E>{
        /**
         * This method will be invoked when a button in the dialog is clicked.
         *
         */
        /* TODO: Change to use BUTTON_POSITIVE after API council */
        void OnSelectSet(E select);
    }

    public static  class Builder <E> {
        private Context context;
        /**
         * 标题
         */
        private String normalDialog_title;

        LinearLayout table_list;
        /**
         * 取消
         */
        private ImageView cancle_top;

        ArrayList<E> str;

        String [] str_old;

        private View contentView;

        PopCallBack popCallBack;

        public String[] getStr_old() {
            return str_old;
        }

        public void setStr_old(String[] str_old) {
            this.str_old = str_old;
        }

        public Builder(Context context) {
            this.context = context;
        }

        public String getNormalDialog_title() {
            return normalDialog_title;
        }

        public void setNormalDialog_title(String normalDialog_title) {
            this.normalDialog_title = normalDialog_title;
        }

        public ArrayList<E> getStr() {
            return str;
        }

        public void setStr(ArrayList<E> str) {
            this.str = str;
        }

        public PopCallBack getPopCallBack() {
            return popCallBack;
        }

        public void setPopCallBack(PopCallBack popCallBack) {
            this.popCallBack = popCallBack;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.normalDialog_title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.normalDialog_title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public DateTimePickerDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final DateTimePickerDialog dialog = new DateTimePickerDialog(context, R.style.Dialog);
            final View layout = inflater.inflate(R.layout.activity_status, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            // set the content message
            if (normalDialog_title != null) {
                ((TextView) layout.findViewById(R.id.normalDialog_title)).setText(normalDialog_title);
            }

            table_list = ((LinearLayout) layout.findViewById(R.id.table_list));
            for(final Object s : str) {
                TextView t = new TextView(context);
                if(s instanceof String) {
                    t.setText(s.toString());
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 80);
                t.setLayoutParams(params);
                t.setGravity(Gravity.CENTER);
                t.setTextColor(context.getResources().getColor(R.color.viewfinder_mask));
                t.setTextSize(20);
                if(s.equals(str.get(str.size()-1))) {
                    t.setBackground(context.getResources().getDrawable(R.drawable.top_status_item_bottom));
                }else {
                    t.setBackground(context.getResources().getDrawable(R.drawable.item_status_select));
                }
                t.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnSelectSetListener.OnSelectSet(s);
                        dialog.dismiss();
                    }
                });
                View view = new View(context);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
                view.setLayoutParams(params1);
                view.setBackgroundColor(context.getResources().getColor(R.color.gray_small_three));
                table_list.addView(t);
                if(!s.equals(str.get(str.size()-1))) {
                    table_list.addView(view);
                }

            }

            if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(contentView, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
            }
            dialog.setContentView(layout);

            cancle_top = ((ImageView) layout.findViewById(R.id.cancle_top));
            cancle_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            return dialog;
        }
    }
}
