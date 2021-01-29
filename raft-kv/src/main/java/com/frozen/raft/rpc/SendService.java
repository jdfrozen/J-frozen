package com.frozen.raft.rpc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.frozen.raft.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SendService {
    @Autowired
    HttpUtils httpUtils;

	/**
	 * 发送消息
	 * @param request
	 * @return
	 */
	public <T> Response sendMsg(Request request){
        String json = httpUtils.sendMsg(request.url, JSON.toJSONString(request));
        return JSON.parseObject(json,new TypeReference<Response<T>>() {});
    }

}
