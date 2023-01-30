package com.example.pss.handler.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 限流枚举
 *
 *
 */
@Getter
@ToString
@AllArgsConstructor
public enum RateLimitStrategy {


    REQUEST_RATE_LIMIT(10, "根据真实请求数限流"),
    SEND_USER_NUM_RATE_LIMIT(20, "根据发送用户数限流"),
    ;

    private final Integer code;
    private final String description;


}
