package medo.payment.aspect;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import medo.common.core.json.JSONMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ControllerLogAspect {

    /** Log point */
    private static final String LOG_POINT = "execution (* medo.payment.web.*.*(..)) ";

    @Around(LOG_POINT)
    public Object appLogAround(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        // get method name
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();

        // get method parameters
        Object[] args = joinPoint.getArgs();

        // log args
        log.info(methodName + " args:" + Arrays.asList(args));

        // execute method
        Object obj = joinPoint.proceed(args);
        // print log
        long diffTime = System.currentTimeMillis() - startTime;
        log.info(
                methodName
                        + " args:"
                        + Arrays.asList(args)
                        + " result:"
                        + JSONMapper.toJSON(obj)
                        + " execute time："
                        + diffTime
                        + " ms");
        return obj;
    }
}
