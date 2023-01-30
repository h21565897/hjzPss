package com.example.pss.web.controller;

import cn.hutool.core.util.StrUtil;
import com.example.pss.common.enums.RespStatusEnum;
import com.example.pss.common.vo.BasicResultVO;
import com.example.pss.web.service.DataService;
import com.example.pss.web.vo.DataParam;
import com.example.pss.web.vo.amis.EchartsVo;
import com.example.pss.web.vo.amis.SmsTimeLineVo;
import com.example.pss.web.vo.amis.UserTimeLineVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 获取数据接口（全链路追踪)
 *
 *  
 */
@Slf4j
@RestController
@RequestMapping("/trace")
@Api("获取数据接口（全链路追踪)")
public class DataController {
    @Autowired
    private DataService dataService;

    @PostMapping("/user")
    @ApiOperation("/获取【当天】用户接收消息的全链路数据")
    public BasicResultVO getUserData(@RequestBody DataParam dataParam) {
        UserTimeLineVo traceUserInfo = dataService.getTraceUserInfo(dataParam.getReceiver());

        return BasicResultVO.success(traceUserInfo);
    }

    @PostMapping("/messageTemplate")
    @ApiOperation("/获取消息模板全链路数据")
    public BasicResultVO getMessageTemplateData(@RequestBody DataParam dataParam) {
        EchartsVo echartsVo = EchartsVo.builder().build();
        if (StrUtil.isNotBlank(dataParam.getBusinessId())) {
            echartsVo = dataService.getTraceMessageTemplateInfo(dataParam.getBusinessId());
        }
        return new BasicResultVO<>(RespStatusEnum.SUCCESS, echartsVo);
    }

    @PostMapping("/sms")
    @ApiOperation("/获取短信下发数据")
    public BasicResultVO getSmsData(@RequestBody DataParam dataParam) {
        if (Objects.isNull(dataParam) || Objects.isNull(dataParam.getDateTime()) || StrUtil.isBlank(dataParam.getReceiver())) {
            return new BasicResultVO<>(RespStatusEnum.SUCCESS, SmsTimeLineVo.builder().items(new ArrayList<>()).build());
        }

        SmsTimeLineVo smsTimeLineVo = dataService.getTraceSmsInfo(dataParam);

        return new BasicResultVO<>(RespStatusEnum.SUCCESS, smsTimeLineVo);
    }

}
