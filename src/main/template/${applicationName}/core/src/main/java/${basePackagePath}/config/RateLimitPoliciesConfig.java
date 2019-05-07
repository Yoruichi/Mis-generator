package ${basePackage}.config;

import com.yoruichi.ratelimiter.annotation.RateLimiterPolicy;
import com.yoruichi.ratelimiter.bean.RateLimiterPolicyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Yoruichi
 * @Date: 2019/4/24 7:18 PM
 */
@Component
public class RateLimitPoliciesConfig {
    @Bean(name = "NormalPolicy")
    public RateLimiterPolicyBean getNormalPolicyBean() {
        return new RateLimiterPolicyBean(
                "NormalPolicy",
                RateLimiterPolicy.Type.IP,
                1, 1,
                1, TimeUnit.HOURS,
                RateLimiterPolicy.RefreshType.LAST_ALLOWED_REQUEST,
                1);
    }
}
