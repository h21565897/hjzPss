package com.example.pss.handler.domain.push;


import com.example.pss.common.domain.TaskInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * push的参数
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PushParam {

    /**
     * 调用 接口时需要的token
     */
    private String token;

    /**
     * 调用接口时需要的appId
     */
    private String appId;

    /**
     * 消息模板的信息
     */
    private TaskInfo taskInfo;

}
