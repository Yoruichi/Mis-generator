package ${basePackage}.config;

import brave.CurrentSpanCustomizer;
import brave.SpanCustomizer;
import brave.Tracing;
import brave.context.slf4j.MDCScopeDecorator;
import brave.http.HttpTracing;
import brave.propagation.B3Propagation;
import brave.propagation.ExtraFieldPropagation;
import brave.propagation.ThreadLocalCurrentTraceContext;
import brave.servlet.TracingFilter;
import brave.spring.webmvc.SpanCustomizingAsyncHandlerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

import javax.servlet.Filter;

/**
 * @Author: Yoruichi
 * @Date: 2018/11/14 6:27 PM
 */
@Slf4j
@Configuration
@Import(SpanCustomizingAsyncHandlerInterceptor.class)
public class TracingConfig implements WebMvcConfigurer {

    @Value("<#noparse>${zipkin.address:}</#noparse>")
    private String zipkinAddress;

    /**
     * Configuration for how to send spans to Zipkin
     */
    @Bean
    Sender sender() {
        if (null == zipkinAddress || zipkinAddress.length() == 0) {
            return null;
        }
        return OkHttpSender.create(zipkinAddress);
    }

    /**
     * Configuration for how to buffer spans into messages for Zipkin
     */
    @Bean
    AsyncReporter<Span> spanReporter() {
        if (null == zipkinAddress || zipkinAddress.length() == 0) {
            return null;
        }
        return AsyncReporter.create(sender());
    }

    @Bean
    public Tracing tracing() {
        AsyncReporter<Span> spanReporter = spanReporter();
        return Tracing.newBuilder()
                .localServiceName("${applicationName}-Service")
                .propagationFactory(BaggagePropagation.newFactoryBuilder(B3Propagation.FACTORY).build())
                .currentTraceContext(ThreadLocalCurrentTraceContext.newBuilder().addScopeDecorator(MDCScopeDecorator.get()).build())
                .spanReporter(spanReporter == null ? span -> log.info("{}", span) : spanReporter)
                .build();
    }

    @Bean
    SpanCustomizer spanCustomizer(Tracing tracing) {
        return CurrentSpanCustomizer.create(tracing);
    }

    /**
     * decides how to name and tag spans. By default they are named the same as the http method.
     */
    @Bean
    HttpTracing httpTracing(Tracing tracing) {
        return HttpTracing.create(tracing);
    }

    /**
     * Creates server spans for http requests
     */
    @Bean
    Filter tracingFilter(HttpTracing httpTracing) {
        return TracingFilter.create(httpTracing);
    }

    @Autowired
    SpanCustomizingAsyncHandlerInterceptor webMvcTracingCustomizer;

    /**
     * Decorates server spans with application-defined web tags
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webMvcTracingCustomizer);
    }

}
