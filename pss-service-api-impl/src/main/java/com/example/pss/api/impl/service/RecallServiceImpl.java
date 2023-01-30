package com.example.pss.api.impl.service;

import com.example.pss.api.domain.SendRequest;
import com.example.pss.api.domain.SendResponse;
import com.example.pss.api.impl.domain.SendTaskModel;
import com.example.pss.api.service.RecallService;
import com.example.pss.common.vo.BasicResultVO;
import com.example.pss.support.pipeline.ProcessContext;
import com.example.pss.support.pipeline.ProcessController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 撤回接口
 *  
 */
@Service
public class RecallServiceImpl implements RecallService {

    @Autowired
    private ProcessController processController;

    @Override
    public SendResponse recall(SendRequest sendRequest) {
        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(sendRequest.getMessageTemplateId())
                .build();
        ProcessContext context = ProcessContext.builder()
                .code(sendRequest.getCode())
                .processModel(sendTaskModel)
                .needBreak(false)
                .response(BasicResultVO.success()).build();
        ProcessContext process = processController.process(context);
        return new SendResponse(process.getResponse().getStatus(), process.getResponse().getMsg());
    }
}
