package com.example.pss.common.constant;


/**
 * pss常量信息
 *
 *
 */
public class pssConstant {

    /**
     * 跨域地址端口
     */
    public static final String ORIGIN_VALUE = "http://localhost:3000";

    /**
     * businessId默认的长度
     * 生成的逻辑：com.example.pss.support.utils.TaskInfoUtils#generateBusinessId(java.lang.Long, java.lang.Integer)
     */
    public final static Integer BUSINESS_ID_LENGTH = 16;

    /**
     * 接口限制 最多的人数
     */
    public static final Integer BATCH_RECEIVER_SIZE = 100;

    /**
     * 消息发送给全部人的标识
     * (企业微信 应用消息)
     * (钉钉自定义机器人)
     * (钉钉工作消息)
     */
    public static final String SEND_ALL = "@all";


    /**
     * 默认的常量，如果新建模板/账号时，没传入则用该常量
     */
    public static final String DEFAULT_CREATOR = "example";
    public static final String DEFAULT_UPDATOR = "example";
    public static final String DEFAULT_TEAM = "example公众号";
    public static final String DEFAULT_AUDITOR = "example";




}
