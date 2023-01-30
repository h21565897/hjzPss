package com.example.pss.handler.handler.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.example.pss.common.domain.TaskInfo;
import com.example.pss.common.dto.model.AlipayMiniProgramContentModel;
import com.example.pss.common.enums.ChannelType;
import com.example.pss.handler.alipay.AlipayMiniProgramAccountService;
import com.example.pss.handler.domain.alipay.AlipayMiniProgramParam;
import com.example.pss.handler.handler.BaseHandler;
import com.example.pss.handler.handler.Handler;
import com.example.pss.support.domain.MessageTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jwq
 * 支付宝小程序发送订阅消息
 */
@Component
@Slf4j
public class AlipayMiniProgramAccountHandler extends BaseHandler implements Handler {

    @Autowired
    private AlipayMiniProgramAccountService alipayMiniProgramAccountService;

    public AlipayMiniProgramAccountHandler() {
        channelCode = ChannelType.ALIPAY_MINI_PROGRAM.getCode();
    }

    @Override
    public boolean handler(TaskInfo taskInfo) {
        AlipayMiniProgramParam miniProgramParam = buildMiniProgramParam(taskInfo);
        try {
            alipayMiniProgramAccountService.send(miniProgramParam);
        }catch (Exception e) {
            log.error("AlipayMiniProgramAccountHandler#handler fail:{},params:{}",
                    Throwables.getStackTraceAsString(e), JSON.toJSONString(taskInfo));
            return false;
        }
        return true;
    }

    /**
     * 通过taskInfo构建小程序订阅消息
     *
     * @param taskInfo 任务信息
     * @return AlipayMiniProgramParam
     */
    private AlipayMiniProgramParam buildMiniProgramParam(TaskInfo taskInfo){
        AlipayMiniProgramParam param = AlipayMiniProgramParam.builder()
                .toUserId(taskInfo.getReceiver())
                .messageTemplateId(taskInfo.getMessageTemplateId())
                .sendAccount(taskInfo.getSendAccount())
                .build();

        AlipayMiniProgramContentModel contentModel = (AlipayMiniProgramContentModel) taskInfo.getContentModel();
        param.setData(contentModel.getMap());
        return param;
    }

    @Override
    public void recall(MessageTemplate messageTemplate) {

    }
}
