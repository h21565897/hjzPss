package com.example.pss.cron.handler;

import com.dtp.core.thread.DtpExecutor;
import com.example.pss.cron.config.CronAsyncThreadPoolConfig;
import com.example.pss.cron.service.TaskHandler;
import com.example.pss.support.utils.ThreadPoolUtils;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 后台提交的定时任务处理类
 *
 */
@Service
@Slf4j
public class CronTaskHandler {

    @Autowired
    private TaskHandler taskHandler;

    @Autowired
    private ThreadPoolUtils threadPoolUtils;
    private DtpExecutor dtpExecutor = CronAsyncThreadPoolConfig.getXxlCronExecutor();

    /**
     * 处理后台的 pss 定时任务消息
     */
    @XxlJob("pssJob")
    public void execute() {
        log.info("CronTaskHandler#execute messageTemplateId:{} cron exec!", XxlJobHelper.getJobParam());
        threadPoolUtils.register(dtpExecutor);

        Long messageTemplateId = Long.valueOf(XxlJobHelper.getJobParam());
        dtpExecutor.execute(() -> taskHandler.handle(messageTemplateId));

    }

}
