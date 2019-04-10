package com.example.new_project.base;

public class send_base {

    /**
     * code : 200
     * msg : 发布成功
     * msgs : null
     * data : null
     */

    private int code;
    private String msg;
    private Object msgs;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getMsgs() {
        return msgs;
    }

    public void setMsgs(Object msgs) {
        this.msgs = msgs;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
