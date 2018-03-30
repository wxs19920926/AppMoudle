package com.example.mylibrary.weight.hovermenu.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.mylibrary.R;


public class DropdownListItemView extends android.support.v7.widget.AppCompatTextView {
    public DropdownListItemView(Context context) {
        this(context,null);
    }

    public DropdownListItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public DropdownListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bind(CharSequence text, boolean checked){
        setText(text);
        if (checked){
            Drawable icon = getResources().getDrawable(R.drawable.ic_ok);
            setCompoundDrawablesWithIntrinsicBounds(null,null,icon,null);
        }else{
            setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        }
    }


}
