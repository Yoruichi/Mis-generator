package ${basePackage}.config;

import com.alibaba.dubbo.config.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Yoruichi
 * @Date: 2018/12/14 6:49 PM
 */
@Slf4j
@Configuration
public class DubboConfig {

    @Value("<#noparse>${application.dubbo.zk.addr}</#noparse>")
    private String dubboZookeeperAddress;

    @Value("<#noparse>${application.dubbo.application.name}</#noparse>")
    private String applicationName;

    @Value("<#noparse>${application.dubbo.logger.name:slf4j}</#noparse>")
    private String loggerName;

    @Value("<#noparse>${application.dubbo.interface.version}</#noparse>")
    private String serviceVersion;

    @Value("<#noparse>${application.dubbo.provider.port}</#noparse>")
    private Integer servicePort;

    @Value("<#noparse>${application.dubbo.register.file}</#noparse>")
    private String registryFile;

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(applicationName);
        applicationConfig.setLogger(loggerName);
        applicationConfig.setVersion(serviceVersion);
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(dubboZookeeperAddress);
        registryConfig.setFile(registryFile);
        registryConfig.setProtocol("zookeeper");
        return registryConfig;
    }

    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(servicePort);
        return protocolConfig;
    }

    @Bean
    public ProviderConfig providerConfig() {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setFilter("tracing");
        return providerConfig;
    }

    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setFilter("tracing");
        return consumerConfig;
    }

}
