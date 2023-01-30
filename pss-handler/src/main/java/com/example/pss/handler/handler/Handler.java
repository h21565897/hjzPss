package com.example.pss.handler.handler;

import com.example.pss.common.domain.TaskInfo;
import com.example.pss.support.domain.MessageTemplate;

/**
 *
 * 消息处理器
 */
public interface Handler {

    /**
     * 处理器
     *
     * @param taskInfo
     */
    void doHandler(TaskInfo taskInfo);

    /**
     * 撤回消息
     *
     * @param messageTemplate
     * @return
     */
    void recall(MessageTemplate messageTemplate);

}
