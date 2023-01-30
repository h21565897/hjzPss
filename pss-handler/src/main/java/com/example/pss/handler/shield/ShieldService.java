package com.example.pss.handler.shield;

import com.example.pss.common.domain.TaskInfo;

/**
 * 屏蔽服务
 *
 *  
 */
public interface ShieldService {


    /**
     * 屏蔽消息
     * @param taskInfo
     */
    void shield(TaskInfo taskInfo);
}
