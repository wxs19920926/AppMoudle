package com.example.mylibrary.workflow.model;

/**
 * Created by wxs on 2018/1/4.
 */

public class WorkFlowDoResult {
    private String errorMsg;
    private String PRID;
    private String errorNo;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getPRID() {
        return PRID;
    }

    public void setPRID(String PRID) {
        this.PRID = PRID;
    }

    public String getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(String errorNo) {
        this.errorNo = errorNo;
    }
}
