package com.example.pss.handler.receiver.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.example.pss.common.domain.AnchorInfo;
import com.example.pss.common.domain.LogParam;
import com.example.pss.common.domain.TaskInfo;
import com.example.pss.common.enums.AnchorState;
import com.example.pss.handler.handler.HandlerHolder;
import com.example.pss.handler.pending.Task;
import com.example.pss.handler.pending.TaskPendingHolder;
import com.example.pss.handler.receiver.service.ConsumeService;
import com.example.pss.handler.utils.GroupIdMappingUtils;
import com.example.pss.support.domain.MessageTemplate;
import com.example.pss.support.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  
 */
@Service
public class ConsumeServiceImpl implements ConsumeService {
    private static final String LOG_BIZ_TYPE = "Receiver#consumer";
    private static final String LOG_BIZ_RECALL_TYPE = "Receiver#recall";
    @Autowired
    private ApplicationContext context;

    @Autowired
    private TaskPendingHolder taskPendingHolder;

    @Autowired
    private LogUtils logUtils;

    @Autowired
    private HandlerHolder handlerHolder;

    @Override
    public void consume2Send(List<TaskInfo> taskInfoLists) {
        String topicGroupId = GroupIdMappingUtils.getGroupIdByTaskInfo(CollUtil.getFirst(taskInfoLists.iterator()));
        for (TaskInfo taskInfo : taskInfoLists) {
            logUtils.print(LogParam.builder().bizType(LOG_BIZ_TYPE).object(taskInfo).build(), AnchorInfo.builder().ids(taskInfo.getReceiver()).businessId(taskInfo.getBusinessId()).state(AnchorState.RECEIVE.getCode()).build());
            Task task = context.getBean(Task.class).setTaskInfo(taskInfo);
            taskPendingHolder.route(topicGroupId).execute(task);
        }
    }

    @Override
    public void consume2recall(MessageTemplate messageTemplate) {
        logUtils.print(LogParam.builder().bizType(LOG_BIZ_RECALL_TYPE).object(messageTemplate).build());
        handlerHolder.route(messageTemplate.getSendChannel()).recall(messageTemplate);
    }
}
