package com.example.pss.api.service;

import com.example.pss.api.domain.BatchSendRequest;
import com.example.pss.api.domain.SendRequest;
import com.example.pss.api.domain.SendResponse;

/**
 * 发送接口
 *
 *
 */
public interface SendService {


    /**
     * 单文案发送接口
     * @param sendRequest
     * @return
     */
    SendResponse send(SendRequest sendRequest);


    /**
     * 多文案发送接口
     * @param batchSendRequest
     * @return
     */
    SendResponse batchSend(BatchSendRequest batchSendRequest);

}
