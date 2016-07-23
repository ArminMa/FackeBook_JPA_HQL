
    package org.kth.HI1034.exceptions;

    public class SiteOptionNotFoundException extends RuntimeException {


        private static final long serialVersionUID = -9166026439762847476L;
        private String msg;

        public SiteOptionNotFoundException() {
            super();
        }

        public SiteOptionNotFoundException(String msg) {
            this.msg = System.currentTimeMillis()
                    + ": " + msg;
        }

        public String getMsg() {
            return msg;
        }


    }


