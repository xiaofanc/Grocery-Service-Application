package edu.gatech.streamingwars.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class Response extends HashMap {
    private String message;
    private String requestId;
    Logger log = LoggerFactory.getLogger(getClass());

    public Response(ResponseEntity entity, String msg){
        message = msg;
        setResponseEntity(entity);
    }

    public Response(ResponseEntity entity) {
        setResponseEntity(entity);
    }

    private void setResponseEntity(ResponseEntity entity){
        this.requestId = LogInterceptor.requestId;
        this.put("code", entity.getStatusCodeValue());
        this.put("message", entity.getStatusCode());
        this.put("data", entity.getBody());
        // TODO: fix Exception no requestId
        if(entity.getStatusCodeValue() != 404){
            this.put("requestId", requestId);
        }


        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        log.info(entity.getStatusCode() + " - "  + stes[3].getClassName() + " - " + stes[3].getMethodName() + " - " + stes[3].getFileName());
    }
}
