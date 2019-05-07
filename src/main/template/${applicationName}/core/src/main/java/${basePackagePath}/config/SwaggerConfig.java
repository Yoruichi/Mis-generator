package ${basePackage}.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: Yoruichi
 * @Date: 2018/12/19 3:44 PM
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("${basePackage}.controller"))
                .paths(PathSelectors.any()).build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Application REST APIs")
                .version("1.0")
                .description("${applicationDescription}")
                .build();
    }
}
