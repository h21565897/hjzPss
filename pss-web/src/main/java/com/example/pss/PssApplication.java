

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 *
 */
@SpringBootApplication
public class PssApplication {
    public static void main(String[] args) {

        /**
         * 如果你需要启动Apollo动态配置
         * 1、启动apollo
         * 2、将application.properties配置文件的 pss.apollo.enabled 改为true
         * 3、下方的property替换真实的ip和port
         */
        System.setProperty("apollo.config-service", "http://pss.apollo.config:5001");
        SpringApplication.run(pssApplication.class, args);
    }
}
