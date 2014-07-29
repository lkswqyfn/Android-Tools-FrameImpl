package com.example.frameimpl.request;

import java.io.IOException;
import java.util.List;

import com.example.frameimpl.bean.ResponseResult;
/**
 * 请求接口
 * @author wqYuan
 *
 */
public interface IRequestFormat {
    /**
     * 关键字查询
     * 
     * @param downLoadingProgress
     * @throws IOException
     */
    public ResponseResult < List<String> > queryKeyword(
            String keyword,String pSize) throws IOException;
}
