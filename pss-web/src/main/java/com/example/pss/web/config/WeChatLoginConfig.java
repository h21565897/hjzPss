package com.example.pss.web.config;

import com.example.pss.common.dto.account.WeChatOfficialAccount;
import com.example.pss.support.utils.WxServiceUtils;
import lombok.Data;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import java.util.Map;


/**
 * 使用微信服务号作为登录的媒介
 * (测试环境 && 开启了配置才使用)
 *
 *  
 */
@Profile("test")
@Configuration("weChatLoginConfig")
@ConditionalOnProperty(name = "pss.login.official.account.enable", havingValue = "true")
@Data
public class WeChatLoginConfig {

    @Value("${pss.login.official.account.appId}")
    private String appId;
    @Value("${pss.login.official.account.secret}")
    private String secret;
    @Value("${pss.login.official.account.token}")
    private String token;

    @Autowired
    private WxServiceUtils wxServiceUtils;

    /**
     * 微信服务号 登录 相关对象
     */
    private WxMpService officialAccountLoginService;
    private WxMpDefaultConfigImpl config;
    private WxMpMessageRouter wxMpMessageRouter;

    @Autowired
    private Map<String, WxMpMessageHandler> WxMpMessageHandlers;


    @PostConstruct
    private void init() {
        WeChatOfficialAccount account = WeChatOfficialAccount.builder().appId(appId).secret(secret).token(token).build();
        officialAccountLoginService = wxServiceUtils.initOfficialAccountService(account);
        initConfig();
        initRouter();
    }

    /**
     * 初始化路由器
     * 扫码、关注、取消关注
     */
    private void initRouter() {
        wxMpMessageRouter = new WxMpMessageRouter(officialAccountLoginService);
        wxMpMessageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE).handler(WxMpMessageHandlers.get(OfficialAccountParamConstant.SUBSCRIBE_HANDLER)).end();
        wxMpMessageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SCAN).handler(WxMpMessageHandlers.get(OfficialAccountParamConstant.SCAN_HANDLER)).end();
        wxMpMessageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.UNSUBSCRIBE).handler(WxMpMessageHandlers.get(OfficialAccountParamConstant.UNSUBSCRIBE_HANDLER)).end();
    }

    /**
     * 初始化配置信息
     */
    private void initConfig() {
        config = new WxMpDefaultConfigImpl();
        config.setAppId(appId);
        config.setToken(token);
        config.setSecret(secret);
    }

}
