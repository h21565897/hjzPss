package com.example.pss.handler.deduplication;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.pss.common.domain.TaskInfo;
import com.example.pss.common.enums.AnchorState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @date 2021/12/11
 * 去重服务所需要的参数
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeduplicationParam {
    /**
     * TaskIno信息
     */
    private TaskInfo taskInfo;

    /**
     * 去重时间
     * 单位：秒
     */
    @JSONField(name = "time")
    private Long deduplicationTime;

    /**
     * 需达到的次数去重
     */
    @JSONField(name = "num")
    private Integer countNum;

    /**
     * 标识属于哪种去重(数据埋点)
     */
    private AnchorState anchorState;
}
