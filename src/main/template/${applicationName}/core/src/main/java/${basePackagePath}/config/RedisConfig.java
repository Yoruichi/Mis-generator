package ${basePackage}.config;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @Author: Yoruichi
 * @Date: 2018/11/27 2:29 PM
 */
@Slf4j
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(
            @Value("<#noparse>${redis.host:localhost}</#noparse>") String host,
            @Value("<#noparse>${redis.port:6379}</#noparse>") int port,
            @Value("<#noparse>${redis.database:0}</#noparse>") int db,
            @Value("<#noparse>${redis.password:}</#noparse>") String password
    ) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        configuration.setDatabase(db);
        if (!Strings.isNullOrEmpty(password)) {
            configuration.setPassword(password);
        }

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(configuration);
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        log.debug("Create redis template with url {}:{}/{} pwd {}",
                ((JedisConnectionFactory) template.getConnectionFactory()).getHostName(),
                ((JedisConnectionFactory) template.getConnectionFactory()).getPort(),
                ((JedisConnectionFactory) template.getConnectionFactory()).getDatabase(),
                ((JedisConnectionFactory) template.getConnectionFactory()).getPassword());
        return template;
    }

}
