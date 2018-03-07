package com.example.android.appmoudle;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mylibrary.indexMain.activity.IndexMainActivity;
import com.example.mylibrary.refresh.RefershActivity;
import com.example.mylibrary.util.DateTimePickerDialog;
import com.example.mylibrary.workflow.model.WorkFlowDoResult;
import com.example.mylibrary.workflow.model.WorkFlowInitResult;
import com.example.mylibrary.workflow.viewModel.WorkFlowViewModel;

import java.util.ArrayList;


//import butterknife.Bind;
//import butterknife.ButterKnife;

/**
 * Created by wxs on 2018/1/4.
 */
public class WorkFlowActivity extends LifecycleActivity {
    private WorkFlowViewModel workFlowViewModel;
    String url;
    ArrayList<String> array1 = new ArrayList<String>();

    //启动流程
//    @Bind(R.id.button1)
    Button button1;
    //流程审批
//    @Bind(R.id.button2)
    Button button2;

    Button button3;

    Button button4;

    Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workflow);
//        ButterKnife.bind(this);
        findViewById();
        initView();
    }

    public void findViewById() {
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
    }

    public void initView() {
        array1 = new ArrayList<String>();
        array1.add("运行");
        array1.add("备用");
        array1.add("检修");
        array1.add("停用");
        workFlowViewModel = ViewModelProviders.of(this).get(WorkFlowViewModel.class);
        button1.setOnClickListener(button1OnClickListener);
        button2.setOnClickListener(button2OnClickListener);
        button3.setOnClickListener(button3OnClickListener);
        button4.setOnClickListener(button4OnClickListener);
        button5.setOnClickListener(button5OnClickListener);
    }

    private View.OnClickListener button5OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DateTimePickerDialog dialog = new DateTimePickerDialog(WorkFlowActivity.this, array1, "测试");
            dialog.setOnSelectTimeSetListener(new DateTimePickerDialog.OnSelectSetListener<String>() {
                @Override
                public void OnSelectSet(String select) {
//                    ASSETSTATUS.setText(select);
                }
            });
        }
    };

    private View.OnClickListener button4OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WorkFlowActivity.this, RefershActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener button3OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WorkFlowActivity.this, IndexMainActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener button1OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            workFlowViewModel.getWorkFlowLiveData(WorkFlowActivity.this,"","","","",url).observe(WorkFlowActivity.this, new Observer<WorkFlowInitResult>() {
                @Override
                public void onChanged(WorkFlowInitResult workFlowInitResult) {
                    Toast.makeText(WorkFlowActivity.this, workFlowInitResult.getMsg() + "==" + workFlowInitResult.getPrid(), Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    View.OnClickListener button2OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            workFlowViewModel.getWorkFlowDoLiveData(WorkFlowActivity.this,"","","","","","",url).observe(WorkFlowActivity.this, new Observer<WorkFlowDoResult>() {
                @Override
                public void onChanged(WorkFlowDoResult workFlowDoResult) {
                    Toast.makeText(WorkFlowActivity.this, workFlowDoResult.getErrorMsg() + "==" + workFlowDoResult.getErrorNo() + "==" + workFlowDoResult.getPRID(), Toast.LENGTH_LONG);
                }
            });
        }
    };
}

