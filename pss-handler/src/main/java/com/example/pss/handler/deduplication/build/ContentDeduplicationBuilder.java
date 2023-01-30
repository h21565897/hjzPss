package com.example.pss.handler.deduplication.build;

import com.example.pss.common.domain.TaskInfo;
import com.example.pss.common.enums.AnchorState;
import com.example.pss.common.enums.DeduplicationType;
import com.example.pss.handler.deduplication.DeduplicationParam;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * @author huskey
 * @date 2022/1/18
 */
@Service
public class ContentDeduplicationBuilder extends AbstractDeduplicationBuilder implements Builder {

    public ContentDeduplicationBuilder() {
        deduplicationType = DeduplicationType.CONTENT.getCode();
    }

    @Override
    public DeduplicationParam build(String deduplication, TaskInfo taskInfo) {
        DeduplicationParam deduplicationParam = getParamsFromConfig(deduplicationType, deduplication, taskInfo);
        if (Objects.isNull(deduplicationParam)) {
           return null;
        }
        deduplicationParam.setAnchorState(AnchorState.CONTENT_DEDUPLICATION);
        return deduplicationParam;

    }
}
