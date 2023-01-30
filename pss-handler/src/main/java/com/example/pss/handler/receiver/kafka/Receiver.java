package com.example.pss.handler.receiver.kafka;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.example.pss.common.domain.TaskInfo;
import com.example.pss.handler.receiver.service.ConsumeService;
import com.example.pss.handler.utils.GroupIdMappingUtils;
import com.example.pss.support.constans.MessageQueuePipeline;
import com.example.pss.support.domain.MessageTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 *
 * 消费MQ的消息
 */
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@ConditionalOnProperty(name = "pss.mq.pipeline", havingValue = MessageQueuePipeline.KAFKA)
public class Receiver {
    @Autowired
    private ConsumeService consumeService;
    /**
     * 发送消息
     *
     * @param consumerRecord
     * @param topicGroupId
     */
    @KafkaListener(topics = "#{'${pss.business.topic.name}'}", containerFactory = "filterContainerFactory")
    public void consumer(ConsumerRecord<?, String> consumerRecord, @Header(KafkaHeaders.GROUP_ID) String topicGroupId) {
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMessage.isPresent()) {

            List<TaskInfo> taskInfoLists = JSON.parseArray(kafkaMessage.get(), TaskInfo.class);
            String messageGroupId = GroupIdMappingUtils.getGroupIdByTaskInfo(CollUtil.getFirst(taskInfoLists.iterator()));
            /**
             * 每个消费者组 只消费 他们自身关心的消息
             */
            if (topicGroupId.equals(messageGroupId)) {
                consumeService.consume2Send(taskInfoLists);
            }
        }
    }

    /**
     * 撤回消息
     * @param consumerRecord
     */
    @KafkaListener(topics = "#{'${pss.business.recall.topic.name}'}",groupId = "#{'${pss.business.recall.group.name}'}",containerFactory = "filterContainerFactory")
    public void recall(ConsumerRecord<?,String> consumerRecord){
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if(kafkaMessage.isPresent()){
            MessageTemplate messageTemplate = JSON.parseObject(kafkaMessage.get(), MessageTemplate.class);
            consumeService.consume2recall(messageTemplate);
        }
    }
}
