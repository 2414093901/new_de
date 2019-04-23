package com.example.new_project.Base;

import java.util.List;

public class msgbase {

    /**
     * code : 200
     * msg : null
     * msgs : null
     * data : {"list":[{"mid":13,"uphone":"444444","mtitle":null,"mmsg":null,"mimg":null,"mtime":"1553841296","mtype":"2","iid":"96067444444","userBase":{"uphone":"444444","upassword":"444444","uimg":"/mybatis/picture/1553764539091.jpg","uname":"张三疯"},"imgBases":[{"iid":"96067444444","iimg":"/mybatis/picture/1553841296035.jpg"},{"iid":"96067444444","iimg":"/mybatis/picture/1553841296051.jpg"}]},{"mid":12,"uphone":"444444","mtitle":null,"mmsg":null,"mimg":null,"mtime":"1553841238","mtype":"2","iid":"38519444444","userBase":{"uphone":"444444","upassword":"444444","uimg":"/mybatis/picture/1553764539091.jpg","uname":"张三疯"},"imgBases":[]},{"mid":11,"uphone":"444444","mtitle":"标题7","mmsg":"内容7","mimg":"/mybatis/picture/1553835559675.jpg","mtime":"1553835559","mtype":"1","iid":null,"userBase":{"uphone":"444444","upassword":"444444","uimg":"/mybatis/picture/1553764539091.jpg","uname":"张三疯"},"imgBases":null},{"mid":10,"uphone":"444444","mtitle":"标题6","mmsg":"内容6","mimg":"/mybatis/picture/1553835530743.jpg","mtime":"1553835530","mtype":"1","iid":null,"userBase":{"uphone":"444444","upassword":"444444","uimg":"/mybatis/picture/1553764539091.jpg","uname":"张三疯"},"imgBases":null},{"mid":9,"uphone":"444444","mtitle":"标题5","mmsg":"内容5","mimg":"/mybatis/picture/1553835525704.jpg","mtime":"1553835525","mtype":"1","iid":null,"userBase":{"uphone":"444444","upassword":"444444","uimg":"/mybatis/picture/1553764539091.jpg","uname":"张三疯"},"imgBases":null},{"mid":8,"uphone":"444444","mtitle":"标题4","mmsg":"内容4","mimg":"/mybatis/picture/1553835521626.jpg","mtime":"1553835521","mtype":"1","iid":null,"userBase":{"uphone":"444444","upassword":"444444","uimg":"/mybatis/picture/1553764539091.jpg","uname":"张三疯"},"imgBases":null},{"mid":7,"uphone":"444444","mtitle":"标题3","mmsg":"内容3","mimg":"/mybatis/picture/1553835518062.jpg","mtime":"1553835518","mtype":"1","iid":null,"userBase":{"uphone":"444444","upassword":"444444","uimg":"/mybatis/picture/1553764539091.jpg","uname":"张三疯"},"imgBases":null},{"mid":6,"uphone":"444444","mtitle":"标题2","mmsg":"内容2","mimg":"/mybatis/picture/1553835512019.jpg","mtime":"1553835512","mtype":"1","iid":null,"userBase":{"uphone":"444444","upassword":"444444","uimg":"/mybatis/picture/1553764539091.jpg","uname":"张三疯"},"imgBases":null},{"mid":5,"uphone":"444444","mtitle":null,"mmsg":null,"mimg":null,"mtime":"1553833202","mtype":"2","iid":"2820444444","userBase":{"uphone":"444444","upassword":"444444","uimg":"/mybatis/picture/1553764539091.jpg","uname":"张三疯"},"imgBases":[{"iid":"2820444444","iimg":"/mybatis/picture/1553833202782.jpg"},{"iid":"2820444444","iimg":"/mybatis/picture/1553833202793.jpg"},{"iid":"2820444444","iimg":"/mybatis/picture/1553833202802.jpg"}]},{"mid":4,"uphone":"444444","mtitle":null,"mmsg":null,"mimg":null,"mtime":"1553832783","mtype":"2","iid":"83222444444","userBase":{"uphone":"444444","upassword":"444444","uimg":"/mybatis/picture/1553764539091.jpg","uname":"张三疯"},"imgBases":[{"iid":"83222444444","iimg":"/mybatis/picture/1553832783184.jpg"},{"iid":"83222444444","iimg":"/mybatis/picture/1553832783195.jpg"},{"iid":"83222444444","iimg":"/mybatis/picture/1553832783204.jpg"}]}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * mid : 13
             * uphone : 444444
             * mtitle : null
             * mmsg : null
             * mimg : null
             * mtime : 1553841296
             * mtype : 2
             * iid : 96067444444
             * userBase : {"uphone":"444444","upassword":"444444","uimg":"/mybatis/picture/1553764539091.jpg","uname":"张三疯"}
             * imgBases : [{"iid":"96067444444","iimg":"/mybatis/picture/1553841296035.jpg"},{"iid":"96067444444","iimg":"/mybatis/picture/1553841296051.jpg"}]
             */

            private int mid;
            private String uphone;
            private Object mtitle;
            private Object mmsg;
            private Object mimg;
            private String mtime;
            private String mtype;
            private String iid;
            private UserBaseBean userBase;
            private List<ImgBasesBean> imgBases;

            public int getMid() {
                return mid;
            }

            public void setMid(int mid) {
                this.mid = mid;
            }

            public String getUphone() {
                return uphone;
            }

            public void setUphone(String uphone) {
                this.uphone = uphone;
            }

            public Object getMtitle() {
                return mtitle;
            }

            public void setMtitle(Object mtitle) {
                this.mtitle = mtitle;
            }

            public Object getMmsg() {
                return mmsg;
            }

            public void setMmsg(Object mmsg) {
                this.mmsg = mmsg;
            }

            public Object getMimg() {
                return mimg;
            }

            public void setMimg(Object mimg) {
                this.mimg = mimg;
            }

            public String getMtime() {
                return mtime;
            }

            public void setMtime(String mtime) {
                this.mtime = mtime;
            }

            public String getMtype() {
                return mtype;
            }

            public void setMtype(String mtype) {
                this.mtype = mtype;
            }

            public String getIid() {
                return iid;
            }

            public void setIid(String iid) {
                this.iid = iid;
            }

            public UserBaseBean getUserBase() {
                return userBase;
            }

            public void setUserBase(UserBaseBean userBase) {
                this.userBase = userBase;
            }

            public List<ImgBasesBean> getImgBases() {
                return imgBases;
            }

            public void setImgBases(List<ImgBasesBean> imgBases) {
                this.imgBases = imgBases;
            }

            public static class UserBaseBean {
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

            public static class ImgBasesBean {
                /**
                 * iid : 96067444444
                 * iimg : /mybatis/picture/1553841296035.jpg
                 */

                private String iid;
                private String iimg;

                public String getIid() {
                    return iid;
                }

                public void setIid(String iid) {
                    this.iid = iid;
                }

                public String getIimg() {
                    return iimg;
                }

                public void setIimg(String iimg) {
                    this.iimg = iimg;
                }
            }
        }
    }
}
