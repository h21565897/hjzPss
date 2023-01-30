package com.example.pss.api.service;

import com.example.pss.api.domain.SendRequest;
import com.example.pss.api.domain.SendResponse;

/**
 * 撤回接口
 *
 *  
 */
public interface RecallService {


    /**
     * 根据模板ID撤回消息
     *
     * @param sendRequest
     * @return
     */
    SendResponse recall(SendRequest sendRequest);
}
