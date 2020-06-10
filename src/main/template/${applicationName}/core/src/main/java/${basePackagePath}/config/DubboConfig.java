package ${basePackage}.config;

import org.apache.dubbo.config.*;
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
        @Value("<#noparse>${dubbo.config.address:localhost:2181}</#noparse>")
        private String dubboConfigAddress;
        @Value("<#noparse>${dubbo.metadata.address:localhost:2181}</#noparse>")
        private String dubboMetadataReportAddress;
        @Value("<#noparse>${dubbo.registry.address:localhost:2181}</#noparse>")
        private String dubboRegistryAddress;
        @Value("<#noparse>${dubbo.registry.protocol:zookeeper}</#noparse>")
        private String dubboRegistryProtocol;
        @Value("<#noparse>${dubbo.register.file:dubbo/registry.properties}</#noparse>")
        private String dubboRegistryFile;
        @Value("<#noparse>${dubbo.protocol.name:dubbo}</#noparse>")
        private String dubboProtocolName;
        @Value("<#noparse>${dubbo.protocol.port:28083}</#noparse>")
        private int dubboPrptocolPort;
        @Value("<#noparse>${dubbo.protocol.threads:500}</#noparse>")
        private int dubboProtocolThreads;
        @Value("<#noparse>${dubbo.protocol.dispatcher:message}</#noparse>")
        private String dubboProtocolDispatcher;
        @Value("<#noparse>${dubbo.application.name:Hela}</#noparse>")
        private String dubboApplicationName;
        @Value("<#noparse>${dubbo.application.version:1.0.0}</#noparse>")
        private String dubboApplicationVersion;
        @Value("<#noparse>${dubbo.appliaction.owner:wgw}</#noparse>")
        private String dubboApplicationOwner;
        @Value("<#noparse>${dubbo.provider.filter:tracing}</#noparse>")
        private String dubboProviderFilter;
        @Value("<#noparse>${dubbo.provider.timeout:6000}</#noparse>")
        private int dubboProviderTimeout;
        @Value("<#noparse>${dubbo.provider.version:1.0.0}</#noparse>")
        private String dubboProviderVersion;
        @Value("<#noparse>${dubbo.provider.accesslog:true}</#noparse>")
        private String dubboProviderAccessLog;
        @Value("<#noparse>${dubbo.consumer.filter:tracing}</#noparse>")
        private String dubboConsumerFilter;
        @Value("<#noparse>${dubbo.consumer.timeout:3000}</#noparse>")
        private int dubboConsumerTimeout;
        @Value("<#noparse>${dubbo.consumer.retries:0}</#noparse>")
        private int dubboConsumerRetries;

        private final String DUBBO_CONFIG_PROTOCOL = "zookeeper";

        @Bean
        public ApplicationConfig applicationConfig() {
            ApplicationConfig applicationConfig = new ApplicationConfig();
            applicationConfig.setName(dubboApplicationName);
            applicationConfig.setVersion(dubboApplicationVersion);
            applicationConfig.setOwner(dubboApplicationOwner);
            return applicationConfig;
        }

        //    @Bean
        //    public MonitorConfig monitorConfig() {
        //        MonitorConfig monitorConfig=new MonitorConfig();
        //        monitorConfig.setProtocol("registry");
        //        return monitorConfig;
        //    }

        @Bean
        public ConfigCenterConfig configCenterConfig() {
            ConfigCenterConfig configCenter = new ConfigCenterConfig();
            configCenter.setProtocol(DUBBO_CONFIG_PROTOCOL);
            configCenter.setAddress(dubboConfigAddress);
            return configCenter;
        }

        @Bean
        public MetadataReportConfig metadataReportConfig() {
            MetadataReportConfig metadataReportConfig = new MetadataReportConfig();
            metadataReportConfig.setAddress(String.format("zookeeper://%s",dubboMetadataReportAddress));
            return metadataReportConfig;
        }

        @Bean
        public RegistryConfig registryConfig() {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setAddress(dubboRegistryAddress);
            registryConfig.setFile(dubboRegistryFile);
            registryConfig.setProtocol(DUBBO_CONFIG_PROTOCOL);
            return registryConfig;
        }

        @Bean
        public ProtocolConfig protocolConfig() {
            ProtocolConfig protocolConfig = new ProtocolConfig();
            protocolConfig.setName(dubboProtocolName);
            protocolConfig.setPort(dubboPrptocolPort);
            protocolConfig.setThreads(dubboProtocolThreads);
            protocolConfig.setDispatcher(dubboProtocolDispatcher);
            return protocolConfig;
        }

        @Bean
        public ProviderConfig providerConfig() {
            ProviderConfig providerConfig = new ProviderConfig();
            providerConfig.setFilter(dubboProviderFilter);
            providerConfig.setTimeout(dubboProviderTimeout);
            providerConfig.setVersion(dubboProviderVersion);
            return providerConfig;
        }

        @Bean
        public ConsumerConfig consumerConfig() {
            ConsumerConfig consumerConfig = new ConsumerConfig();
            consumerConfig.setFilter(dubboConsumerFilter);
            consumerConfig.setRetries(dubboConsumerRetries);
            consumerConfig.setTimeout(dubboConsumerTimeout);
            return consumerConfig;
        }
    }
