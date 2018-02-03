package com.example.mylibrary.database;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.mylibrary.workflow.model.WorkFlowDoResult;
import com.example.mylibrary.workflow.model.WorkFlowInitResult;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wxs on 2018/1/4.
 */
public class WsClient {
    Context context;
    String action = "";
    SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    // 超时时间
    public int timeout = 60000;
    //命名空间
    public static final String NAMESPACE = "http://www.ibm.com/maximo";
//    String url = "http://61.49.29.220:7003/meaweb/services/WFSERVICE";
//    String url = "http://10.1.29.155:7002/meaweb/services/WFSERVICE";

    public WsClient(Context context) {
        this.context = context;
    }

    /**
     * 启动流程
     * @param paras 参数设置
     * @param url 访问地址
     * @return
     */
    public Observable<WorkFlowInitResult> initWorkFlow(final Map<String,String> paras, final String url) {
        Observable<WorkFlowInitResult> observable = Observable.create(new ObservableOnSubscribe<SoapObject>() {
            @Override
            public void subscribe(ObservableEmitter<SoapObject> e) throws Exception {
                // 封装请求参数
                SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapEnvelope.implicitTypes = true;
                soapEnvelope.dotNet = true;
                SoapObject soapReq = new SoapObject(NAMESPACE, "wfservicestartWF");
                soapReq.addProperty("processname", paras.get("processname"));//工单：UDFJHWO，采购申请（含零星和集中采购风电场部分审批）：UDPR，集中汇总采购计划流程（分公司发起）：UDPRHZ
                soapReq.addProperty("mbo", paras.get("mbo"));//工单WORKORDER,采购申请pr
                soapReq.addProperty("keyValue", paras.get("keyValue"));//对应的表ID的值，如工单需要传送workorderid的值，采购申请prnum的值
                soapReq.addProperty("key", paras.get("key"));//对应的表ID，如工单：wonum，采购申请，prnum

//                soapEnvelope.bodyOut = soapReq;
                soapEnvelope.setOutputSoapObject(soapReq);

                HttpTransportSE transport = new HttpTransportSE(url, timeout);
                transport.call(action, soapEnvelope);
                String a = soapEnvelope.getResponse().toString();
                Object result = soapEnvelope.bodyIn;
                e.onNext((SoapObject) result);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<SoapObject, WorkFlowInitResult>() {
                    @Override
                    public WorkFlowInitResult apply(SoapObject o) throws Exception {
                        SoapPrimitive retObj = (SoapPrimitive) o.getProperty("return");
                        String str = retObj.toString();
                        WorkFlowInitResult workFlowInitResult =  JSON.parseObject(str, new TypeReference<WorkFlowInitResult>() {
                        });
                        return workFlowInitResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    /**
     * 审批流程
     * @param paras 参数
     * @param url 访问地址
     * @return
     */
    public Observable<WorkFlowDoResult> WorkFlowDo(final Map<String,String> paras, final String url) {
        Observable<WorkFlowDoResult> observable = Observable.create(new ObservableOnSubscribe<SoapObject>() {
            @Override
            public void subscribe(ObservableEmitter<SoapObject> e) throws Exception {
                // 封装请求参数
                SoapObject soapReq = new SoapObject(NAMESPACE, "wfservicewfGoOn");
                soapReq.addProperty("processname", paras.get("processname"));//工单：UDFJHWO，采购申请（含零星和集中采购风电场部分审批）：UDPR，集中汇总采购计划流程（分公司发起）：UDPRHZ
                soapReq.addProperty("mboName", paras.get("mboName"));//工单WORKORDER,采购申请pr
                soapReq.addProperty("keyValue", paras.get("keyValue"));//对应的表ID的值，如工单需要传送wonum的值，采购申请prnum的值
                soapReq.addProperty("key", paras.get("key"));//对应的表ID，如工单：wonum，采购申请，prnum
                soapReq.addProperty("zx", paras.get("zx"));//审批的结果，1为审批通过，0为审批不通过
                soapReq.addProperty("loginid", paras.get("loginid"));
                if (!paras.get("desc").equals("")) {
                    soapReq.addProperty("desc", paras.get("desc"));//审批意见
                }

                soapEnvelope.bodyOut = soapReq;
                soapEnvelope.setOutputSoapObject(soapReq);

                HttpTransportSE transport = new HttpTransportSE(url, timeout);
                transport.call(action, soapEnvelope);

                Object result = soapEnvelope.bodyIn;
                e.onNext((SoapObject) result);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<SoapObject, WorkFlowDoResult>() {
                    @Override
                    public WorkFlowDoResult apply(SoapObject o) throws Exception {
                        SoapPrimitive retObj = (SoapPrimitive) o.getProperty("return");
                        String str = retObj.toString();
                        WorkFlowDoResult workFlowInitResult =  JSON.parseObject(str, new TypeReference<WorkFlowDoResult>() {
                        });
                        return workFlowInitResult;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
        return observable;
    }
}
