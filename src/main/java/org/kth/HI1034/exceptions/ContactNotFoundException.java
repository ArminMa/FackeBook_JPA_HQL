
    package org.kth.HI1034.exceptions;

    public class ContactNotFoundException extends RuntimeException {


        private String msg;

        public ContactNotFoundException() {
            super();
        }

        public ContactNotFoundException(String msg) {
            this.msg = System.currentTimeMillis()
                    + ": " + msg;
        }

        public String getMsg() {
            return msg;
        }


    }


