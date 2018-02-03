package com.example.mylibrary.workflow.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mylibrary.R;
import com.example.mylibrary.util.DisplayUtil;


/**
 * Created by wxs on 2016/8/25.
 */
public class WorkFlowDialog extends Dialog {

    public WorkFlowDialog(Context context) {
        super(context);
    }

    public WorkFlowDialog(Context context, int theme) {
        super(context, theme);
    }

    public interface OnClickListener1 {
        /* TODO: Change to use BUTTON_POSITIVE after API council */
        public void onClick(DialogInterface dialog, String note_value, String description);
    }

    public static class Builder {
        private Context context;
        /**
         * 标题
         */
        private String normalDialog_title;
        /**
         * 当前人员
         */
        private String current_person_value;
        /**
         * 备注
         */
        private String note_value;
        /**
         * 取消
         */
        private ImageView cancle_top;
        private String positiveButtonText;
        private String negativeButtonText;
        private String cancelButtonText;

        private OnClickListener1 positiveButtonClickListener;
        private OnClickListener1 negativeButtonClickListener;
        private OnClickListener1 cancelButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public void setNormalDialog_title(String normalDialog_title) {
            this.normalDialog_title = normalDialog_title;
        }

        public void setCurrent_person_value(String current_person_value) {
            this.current_person_value = current_person_value;
        }

        public void setNote_value(String note_value) {
            this.note_value = note_value;
        }

        public Builder setPositiveButton(OnClickListener1 listener) {
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(OnClickListener1 listener) {
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setCancelButton(
                                    OnClickListener1 listener) {
            this.cancelButtonClickListener = listener;
            return this;
        }

        public LinearLayout createButton(String value, LinearLayout layout) {
            LinearLayout newLinear = new LinearLayout(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayUtil.dp2px(context, 160), LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, DisplayUtil.dp2px(context, 10), 0, DisplayUtil.dp2px(context, 5));
            if(value.equals("已确认")) {
                newLinear.setBackground(context.getResources().getDrawable(R.drawable.workflow_border_save));
            }else if(value.equals("确认")) {
                newLinear.setBackground(context.getResources().getDrawable(R.drawable.workflow_border_save));
            }else if(value.equals("驳回")) {
                newLinear.setBackground(context.getResources().getDrawable(R.drawable.workflow_border_delete));
            }else if(value.equals("同意")) {
                newLinear.setBackground(context.getResources().getDrawable(R.drawable.workflow_border_save));
            }else if(value.equals("不同意")) {
                newLinear.setBackground(context.getResources().getDrawable(R.drawable.workflow_border_delete));
            }else {
                newLinear.setBackground(context.getResources().getDrawable(R.drawable.workflow_border_save));
            }
            newLinear.setGravity(Gravity.CENTER);
            params.gravity = Gravity.CENTER;
            newLinear.setLayoutParams(params);
//            newLinear.setPadding(DisplayUtil.dp2px(context, 8), DisplayUtil.dp2px(context, 8), DisplayUtil.dp2px(context, 8), DisplayUtil.dp2px(context, 8));

            TextView textView = new TextView(context);
            textView.setText(value);
            if(value.equals("已确认")) {
                textView.setTextColor(context.getResources().getColor(R.color.write));
            }else if(value.equals("确认")) {
                textView.setTextColor(context.getResources().getColor(R.color.write));
            }else if(value.equals("驳回")) {
                textView.setTextColor(context.getResources().getColor(R.color.cornflowerblue));
            }else if(value.equals("同意")) {
                textView.setTextColor(context.getResources().getColor(R.color.write));
            }else if(value.equals("不同意")) {
                textView.setTextColor(context.getResources().getColor(R.color.cornflowerblue));
            }else {
                textView.setTextColor(context.getResources().getColor(R.color.write));
            }
            textView.setTextSize(15);
            textView.setPadding(DisplayUtil.dp2px(context, 6), DisplayUtil.dp2px(context, 6), DisplayUtil.dp2px(context, 6), DisplayUtil.dp2px(context, 6));
//            textView.setGravity(Gravity.CENTER);
            newLinear.addView(textView);

            layout.addView(newLinear);

            return newLinear;
        }

        public WorkFlowDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final WorkFlowDialog dialog = new WorkFlowDialog(context, R.style.myDialogTheme);
            final View layout = inflater.inflate(R.layout.dialog_workflow_layout, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            // set the dialog title
            ((TextView) layout.findViewById(R.id.normalDialog_title)).setText(normalDialog_title);
            // set the confirm button
            LinearLayout linearLayout = ((LinearLayout) layout.findViewById(R.id.sd));

            /**
             * 确认按钮
             */
            createButton("确认", linearLayout).setOnClickListener(new View.OnClickListener() {
                     public void onClick(View v) {
                         positiveButtonClickListener.onClick(dialog, "01", ((TextView) layout.findViewById(R.id.note_value)).getText().toString());
                }
            });

            /**
             * 驳回按钮
             */
            createButton("驳回", linearLayout).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    negativeButtonClickListener.onClick(dialog, "02", ((TextView) layout.findViewById(R.id.note_value)).getText().toString());
                }
            });

            /**
             * 取消按钮
             */
            if (cancelButtonClickListener != null) {
                ((LinearLayout) layout.findViewById(R.id.cancelButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
            }

            if (normalDialog_title != null) {
                ((TextView) layout.findViewById(R.id.normalDialog_title)).setText(normalDialog_title);
            } if (current_person_value != null) {
                ((TextView) layout.findViewById(R.id.current_person_value)).setText(current_person_value);
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
