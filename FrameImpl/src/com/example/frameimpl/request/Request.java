package com.example.frameimpl.request;

import java.io.IOException;
import java.util.List;

import com.example.frameimpl.bean.ResponseResult;

/**
 * 
 * @author wqYuan
 *
 */
public class Request {
    private IRequestFormat requestFormat;

    private static Request request;

    /**
     * 上锁对象
     */
    private static final Object INSTANCE_LOCK = new Object();

    public static Request getRequest() throws IOException {
        synchronized (INSTANCE_LOCK) {
            if (request == null) {
                request = new Request();
            }
            return request;
        }
    }

    public Request() throws IOException {
    	requestFormat = new RequestFormatIsJson();
    }
    
    public ResponseResult < List<String> > queryKeyword(
            String keyword) throws IOException{
    	return requestFormat.queryKeyword("","");
    }
    
}
