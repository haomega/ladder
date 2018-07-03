package com.valid.demo.resp;

import org.springframework.http.ResponseEntity;

public class BaseRespEntity {

    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_FAILED = "failed";

    private String status;
    private String message;

    public BaseRespEntity set(String status, String message) {
        this.clean();
        this.status = status;
        this.message = message;
        return this;
    }

    public BaseRespEntity setStatus(String status) {
        this.clean();
        this.status = status;
        return this;
    }
    public BaseRespEntity setMessage(String message) {
        this.clean();
        this.message = message;
        return this;
    }



    public BaseRespEntity setStatusSuccess(String msg) {
        this.setMessage(msg);
        return this;
    }

    public void clean() {
        this.status=null;
        this.message = null;
    }
}
