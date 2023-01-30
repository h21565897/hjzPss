package com.example.pss.handler.discard;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.pss.common.constant.CommonConstant;
import com.example.pss.common.domain.AnchorInfo;
import com.example.pss.common.domain.TaskInfo;
import com.example.pss.common.enums.AnchorState;
import com.example.pss.support.service.ConfigService;
import com.example.pss.support.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 丢弃模板消息
 *  .
 */
@Service
public class DiscardMessageService {
    private static final String DISCARD_MESSAGE_KEY = "discardMsgIds";

    @Autowired
    private ConfigService config;

    @Autowired
    private LogUtils logUtils;
    

    /**
     * 丢弃消息，配置在apollo
     * @param taskInfo
     * @return
     */
    public boolean isDiscard(TaskInfo taskInfo) {
        // 配置示例:	["1","2"]
        JSONArray array = JSON.parseArray(config.getProperty(DISCARD_MESSAGE_KEY, CommonConstant.EMPTY_VALUE_JSON_ARRAY));

        if (array.contains(String.valueOf(taskInfo.getMessageTemplateId()))) {
            logUtils.print(AnchorInfo.builder().businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver()).state(AnchorState.DISCARD.getCode()).build());
            return true;
        }
        return false;
    }

}
