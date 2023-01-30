package com.example.pss.handler.deduplication.service;


import com.example.pss.handler.deduplication.DeduplicationParam;

/**
 * @author huskey
 * @date 2022/1/18
 */
public interface DeduplicationService {

    /**
     * 去重
     * @param param
     */
    void deduplication(DeduplicationParam param);
}
