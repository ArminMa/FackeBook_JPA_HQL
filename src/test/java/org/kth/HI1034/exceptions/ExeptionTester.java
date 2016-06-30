
    package org.kth.HI1034.exceptions;

    public class ExeptionTester extends Exception {


        private String msg;

        public ExeptionTester() {
            super();
        }

        public ExeptionTester(String msg) {
            this.msg = System.currentTimeMillis()
                    + ": " + msg;
        }

        public String getMsg() {
            return msg;
        }


    }


