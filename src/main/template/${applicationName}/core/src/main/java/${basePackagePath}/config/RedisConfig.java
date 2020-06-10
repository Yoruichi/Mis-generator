package ${basePackage}.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.base.Strings;
import com.superatomfin.hela.client.model.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author: Yoruichi
 * @Date: 2018/11/27 2:29 PM
 */
@Slf4j
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, BaseResponse> redisTemplate(
            @Value("<#noparse>${spring.redis.host:localhost}</#noparse>") String host,
            @Value("<#noparse>${spring.redis.port:6379}</#noparse>") int port,
            @Value("<#noparse>${spring.redis.database:0}</#noparse>") int db,
            @Value("<#noparse>${spring.redis.password:}</#noparse>") String password,
            @Value("<#noparse>${spring.redis.pool.max-active:64}</#noparse>") int maxActive,
            @Value("<#noparse>${spring.redis.pool.max-wait:-1}</#noparse>") int maxWait,
            @Value("<#noparse>${spring.redis.pool.max-idle:64}</#noparse>") int maxIdle,
            @Value("<#noparse>${spring.redis.pool.min-idle:32}</#noparse>") int minIdle
    ) {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxWaitMillis(maxWait);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(60000L);
        poolConfig.setTimeBetweenEvictionRunsMillis(30000L);
        poolConfig.setNumTestsPerEvictionRun(-1);

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        configuration.setDatabase(db);
        if (!Strings.isNullOrEmpty(password)) {
            configuration.setPassword(password);
        }

        ObjectMapper om = new ObjectMapper();
        //bugFix Jackson2反序列化数据处理LocalDateTime类型时出错
        om.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());
        Jackson2JsonRedisSerializer<BaseResponse> valueSerializer = new Jackson2JsonRedisSerializer<>(BaseResponse.class);
        valueSerializer.setObjectMapper(om);

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(configuration,
                LettucePoolingClientConfiguration.builder().poolConfig(poolConfig).build());
        lettuceConnectionFactory.afterPropertiesSet();
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(lettuceConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(valueSerializer);
        template.afterPropertiesSet();
        log.info("Create redis template with url {}:{}/{} pwd {}",
                ((LettuceConnectionFactory) template.getConnectionFactory()).getHostName(),
                ((LettuceConnectionFactory) template.getConnectionFactory()).getPort(),
                ((LettuceConnectionFactory) template.getConnectionFactory()).getDatabase(),
                ((LettuceConnectionFactory) template.getConnectionFactory()).getPassword());
        return template;
    }

}
