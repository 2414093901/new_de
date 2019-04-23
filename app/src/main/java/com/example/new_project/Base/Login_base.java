package com.example.new_project.Base;

public class Login_base {

    /**
     * code : 200
     * msg : null
     * msgs : null
     * data : {"uphone":"444444","upassword":"444444","uimg":"/mybatis/picture/1553764539091.jpg","uname":"张三疯"}
     */

    private int code;
    private Object msg;
    private Object msgs;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public Object getMsgs() {
        return msgs;
    }

    public void setMsgs(Object msgs) {
        this.msgs = msgs;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uphone : 444444
         * upassword : 444444
         * uimg : /mybatis/picture/1553764539091.jpg
         * uname : 张三疯
         */

        private String uphone;
        private String upassword;
        private String uimg;
        private String uname;

        public String getUphone() {
            return uphone;
        }

        public void setUphone(String uphone) {
            this.uphone = uphone;
        }

        public String getUpassword() {
            return upassword;
        }

        public void setUpassword(String upassword) {
            this.upassword = upassword;
        }

        public String getUimg() {
            return uimg;
        }

        public void setUimg(String uimg) {
            this.uimg = uimg;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }
    }


}
