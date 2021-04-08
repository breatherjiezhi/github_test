package com.dhc.rad.common.web;

import com.dhc.rad.common.config.Global;

import java.util.List;
import java.util.Map;

public class Result {

    private String messageStatus;

    private String message;

    private List<String> messageList;

    private Map<String,String> messageMap ;

    private Object data;


    public Result(String message) {
        super();
        this.message = message;
        this.messageStatus = Global.YES;
    }

    public Result() {
        super();
        this.messageStatus = Global.YES;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }

    public Map<String,String> getMessageMap() {
        return messageMap;
    }

    public void setMessageMap(Map<String,String> messageMap) {
        this.messageMap = messageMap;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
