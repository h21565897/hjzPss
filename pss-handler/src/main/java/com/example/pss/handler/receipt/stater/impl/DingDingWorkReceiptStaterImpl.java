package com.example.pss.handler.receipt.stater.impl;

import com.example.pss.common.constant.CommonConstant;
import com.example.pss.common.enums.ChannelType;
import com.example.pss.handler.handler.impl.DingDingWorkNoticeHandler;
import com.example.pss.handler.receipt.stater.ReceiptMessageStater;
import com.example.pss.support.dao.ChannelAccountDao;
import com.example.pss.support.domain.ChannelAccount;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 拉取 钉钉工作消息的回执 内容 【未完成】
 *
 *
 */
public class DingDingWorkReceiptStaterImpl implements ReceiptMessageStater {

    @Autowired
    private DingDingWorkNoticeHandler workNoticeHandler;

    @Autowired
    private ChannelAccountDao channelAccountDao;

    @Override
    public void start() {
        List<ChannelAccount> accountList = channelAccountDao.findAllByIsDeletedEqualsAndSendChannelEquals(CommonConstant.FALSE, ChannelType.DING_DING_WORK_NOTICE.getCode());
        for (ChannelAccount channelAccount : accountList) {
            workNoticeHandler.pull(channelAccount.getId());
        }
    }
}
