package ${basePackage};

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author
 */
@SpringBootApplication
@DubboComponentScan("${basePackage}.rpc")
@ComponentScan({ "com.yoruichi", "${basePackage}" })
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
