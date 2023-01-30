package com.example.pss.handler.receiver.eventbus;

import com.google.common.eventbus.Subscribe;
import com.example.pss.common.domain.TaskInfo;
import com.example.pss.handler.receiver.service.ConsumeService;
import com.example.pss.support.constans.MessageQueuePipeline;
import com.example.pss.support.domain.MessageTemplate;
import com.example.pss.support.mq.eventbus.EventBusListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  
 */
@Component
@ConditionalOnProperty(name = "pss.mq.pipeline", havingValue = MessageQueuePipeline.EVENT_BUS)
public class EventBusReceiver implements EventBusListener {

    @Autowired
    private ConsumeService consumeService;

    @Override
    @Subscribe
    public void consume(List<TaskInfo> lists) {
        consumeService.consume2Send(lists);

    }

    @Override
    @Subscribe
    public void recall(MessageTemplate messageTemplate) {
        consumeService.consume2recall(messageTemplate);
    }
}
