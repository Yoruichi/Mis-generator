package ${basePackage}.config;

import com.jcabi.aspects.aj.MethodLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Author: Yoruichi
 * @Date: 2018/10/15 2:58 PM
 */
@Component
@Aspect
public class MethodLoggerAj {
    private MethodLogger methodLogger = new MethodLogger();

    @Around("@annotation(com.jcabi.aspects.Loggable)")
    public Object wrapClass(ProceedingJoinPoint point) throws Throwable {
        return methodLogger.wrapClass(point);
    }

    @Around("@annotation(com.jcabi.aspects.Loggable)")
    public Object wrapMethod(ProceedingJoinPoint point) throws Throwable {
        return methodLogger.wrapMethod(point);
    }

}
