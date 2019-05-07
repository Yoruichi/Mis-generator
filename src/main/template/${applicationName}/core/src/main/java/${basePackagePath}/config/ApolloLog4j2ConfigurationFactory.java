package ${basePackage}.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.enums.PropertyChangeType;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.properties.PropertiesConfigurationBuilder;

import java.net.URI;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

/**
 * @author liyang  Date: 18-11-30 Time: 上午11:25
 */
@Plugin(name = "ApolloLog4j2ConfigurationFactory", category = ConfigurationFactory.CATEGORY)
@Order(50)
@Slf4j
public class ApolloLog4j2ConfigurationFactory extends ConfigurationFactory {
    private static final String LOG4J2_NAMESPACE = "BitMarket-BE.log4j2";

    @Override
    protected String[] getSupportedTypes() {
        return new String[]{"*"};
    }

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource configurationSource) {
        return getConfiguration(loggerContext, configurationSource.toString(), null);
    }

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, String name, URI configLocation) {
        Config config = ConfigService.getConfig(LOG4J2_NAMESPACE);
        Set<String> propertyNames = config.getPropertyNames();

        Properties properties = new Properties();
        for (String propertyName : propertyNames) {
            String propertyValue = config.getProperty(propertyName, null);
            properties.setProperty(propertyName, propertyValue);
        }

        // 添加log4j2配置的监听器
        config.addChangeListener(new Log4j2ConfigChangeListener(properties));

        // 构造log4j2的Configuration
        return new PropertiesConfigurationBuilder()
                .setRootProperties(copyProperties(properties))
                .setLoggerContext(loggerContext)
                .build();
    }

    private Properties copyProperties(Properties properties) {
        Properties newProperties = new Properties();

        Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String propertyName = enumeration.nextElement();
            newProperties.put(propertyName, properties.getProperty(propertyName));
        }
        return newProperties;
    }

    private class Log4j2ConfigChangeListener implements ConfigChangeListener {

        private Properties configProperties;

        public Log4j2ConfigChangeListener(Properties configProperties) {
            this.configProperties = configProperties;
        }

        @Override
        public void onChange(ConfigChangeEvent changeEvent) {
            ConfigChange configChange;

            for (String changedKey : changeEvent.changedKeys()) {
                configChange = changeEvent.getChange(changedKey);
                String newValue = configChange.getNewValue();
                PropertyChangeType type = configChange.getChangeType();
                switch (type) {
                    case DELETED:
                        log.info("config delete key [{}]", changedKey);
                        configProperties.remove(changedKey);
                        break;
                    case ADDED:
                        log.info("config add key [{}],value [{}]", changedKey, newValue);
                        configProperties.put(changedKey, newValue);
                        break;
                    case MODIFIED:
                        log.info("config change key [{}], from [{}] to [{}]", changedKey, configChange.getOldValue(), newValue);
                        configProperties.put(changedKey, newValue);
                        break;
                    default:
                }
            }

            // 构造新配置并应用到LoggerContext

            ConfigurationFactory configurationFactory = new ApolloLog4j2ConfigurationFactory();
            ConfigurationFactory.setConfigurationFactory(configurationFactory);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            ctx.start(configurationFactory.getConfiguration(ctx, ConfigurationSource.NULL_SOURCE));
        }
    }
}