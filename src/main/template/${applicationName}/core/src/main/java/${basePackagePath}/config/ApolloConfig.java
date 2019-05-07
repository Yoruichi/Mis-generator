package ${basePackage}.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Yoruichi
 * @Date: 2018/12/20 3:35 PM
 */
@Configuration
@EnableApolloConfig
public class ApolloConfig {

    @com.ctrip.framework.apollo.spring.annotation.ApolloConfig
    private Config config;

}
