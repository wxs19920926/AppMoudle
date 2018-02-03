package com.example.mylibrary.workflow.viewModel;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.DialogInterface;


import com.example.mylibrary.database.WsClient;
import com.example.mylibrary.workflow.util.WorkFlowDialog;
import com.example.mylibrary.workflow.model.WorkFlowDoResult;
import com.example.mylibrary.workflow.model.WorkFlowInitResult;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wxs on 2018/1/4.
 */
public class WorkFlowViewModel extends ViewModel {
    private MediatorLiveData<WorkFlowInitResult> WorkFlowLiveData;
    private MediatorLiveData<WorkFlowDoResult> workFlowDoLiveData;

    public MediatorLiveData<WorkFlowInitResult> getWorkFlowLiveData(Context context, String processname, String mbo,
                                                                    String keyValue, String key, String url) {
        WorkFlowLiveData = new MediatorLiveData();
        //请求参数
        HashMap requestMap = new HashMap();
        requestMap.put("processname", processname);
        requestMap.put("mbo", mbo);
        requestMap.put("keyValue", keyValue);
        requestMap.put("key", key);

        new WsClient(context).initWorkFlow(requestMap, url).subscribe(new Observer<WorkFlowInitResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WorkFlowInitResult workFlowInitResult) {
                WorkFlowLiveData.postValue(workFlowInitResult);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        return WorkFlowLiveData;
    }

    public MediatorLiveData<WorkFlowDoResult> getWorkFlowDoLiveData(final Context context, final  String processname, final String mboName, final String keyValue,
                                                                    final String key, final String loginid, String personName, final String url) {
        workFlowDoLiveData = new MediatorLiveData();
        WorkFlowDialog.Builder builder = new WorkFlowDialog.Builder(context);
        builder.setNormalDialog_title("流程审批");
        builder.setCurrent_person_value(personName);
        builder.setPositiveButton(new WorkFlowDialog.OnClickListener1() {
            @Override
            public void onClick(DialogInterface dialog, String note_value, String description) {
                doWorkFlow(context, "1",processname,mboName,keyValue,key,loginid,description, url);
            }
        });
        builder.setNegativeButton(new WorkFlowDialog.OnClickListener1() {
            @Override
            public void onClick(DialogInterface dialog, String note_value, String description) {
                doWorkFlow(context, "0",processname,mboName,keyValue,key,loginid,description, url);
            }
        });
        builder.create().show();

        return workFlowDoLiveData;
    }

    public void doWorkFlow(Context context, String zx, String processname, String mboName, String keyValue,
            String key, String loginid, String desc, String url) {
        //请求参数
        HashMap requestMap = new HashMap();
        requestMap.put("processname", processname);
        requestMap.put("mboName", mboName);
        requestMap.put("keyValue", keyValue);
        requestMap.put("key", key);
        requestMap.put("zx", zx);
        requestMap.put("loginid", loginid);
        requestMap.put("desc", desc);
        new WsClient(context).WorkFlowDo(requestMap, url).subscribe(new Observer<WorkFlowDoResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WorkFlowDoResult workFlowDoResult) {
                workFlowDoLiveData.postValue(workFlowDoResult);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
