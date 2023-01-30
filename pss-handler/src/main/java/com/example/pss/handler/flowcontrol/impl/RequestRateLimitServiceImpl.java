package com.example.pss.handler.flowcontrol.impl;

import com.google.common.util.concurrent.RateLimiter;
import com.example.pss.common.domain.TaskInfo;
import com.example.pss.handler.enums.RateLimitStrategy;
import com.example.pss.handler.flowcontrol.FlowControlParam;
import com.example.pss.handler.flowcontrol.FlowControlService;
import com.example.pss.handler.flowcontrol.annotations.LocalRateLimit;

/**
 * Created by TOM
 * On 2022/7/21 17:05
 * @author TOM
 */
@LocalRateLimit(rateLimitStrategy = RateLimitStrategy.REQUEST_RATE_LIMIT)
public class RequestRateLimitServiceImpl implements FlowControlService {

  /**
   * 根据渠道进行流量控制
   *
   * @param taskInfo
   * @param flowControlParam
   */
  @Override
  public Double flowControl(TaskInfo taskInfo, FlowControlParam flowControlParam) {
    RateLimiter rateLimiter = flowControlParam.getRateLimiter();
    return rateLimiter.acquire(1);
  }
}
