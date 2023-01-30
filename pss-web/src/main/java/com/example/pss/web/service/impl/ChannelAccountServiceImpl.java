package com.example.pss.web.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.example.pss.common.constant.pssConstant;
import com.example.pss.common.constant.CommonConstant;
import com.example.pss.support.dao.ChannelAccountDao;
import com.example.pss.support.domain.ChannelAccount;
import com.example.pss.support.utils.WxServiceUtils;
import com.example.pss.web.service.ChannelAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 *
 */
@Service
public class ChannelAccountServiceImpl implements ChannelAccountService {

    @Autowired
    private ChannelAccountDao channelAccountDao;
    @Autowired
    private WxServiceUtils wxServiceUtils;

    @Override
    public ChannelAccount save(ChannelAccount channelAccount) {
        if (Objects.isNull(channelAccount.getId())) {
            channelAccount.setCreated(Math.toIntExact(DateUtil.currentSeconds()));
            channelAccount.setIsDeleted(CommonConstant.FALSE);
        }
        channelAccount.setCreator(StrUtil.isBlank(channelAccount.getCreator()) ? pssConstant.DEFAULT_CREATOR : channelAccount.getCreator());
        channelAccount.setUpdated(Math.toIntExact(DateUtil.currentSeconds()));
        ChannelAccount result = channelAccountDao.save(channelAccount);
        wxServiceUtils.fresh();
        return result;
    }

    @Override
    public List<ChannelAccount> queryByChannelType(Integer channelType, String creator) {
        return channelAccountDao.findAllByIsDeletedEqualsAndCreatorEqualsAndSendChannelEquals(CommonConstant.FALSE, creator, channelType);
    }

    @Override
    public List<ChannelAccount> list(String creator) {
        return channelAccountDao.findAllByCreatorEquals(creator);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        channelAccountDao.deleteAllById(ids);
    }
}
