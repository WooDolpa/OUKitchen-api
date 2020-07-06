package com.project.toy.api.aspect;

import com.project.toy.api.constant.ApiConstants;
import com.project.toy.api.exception.ManagedException;
import com.project.toy.api.exception.ManagedExceptionCode;
import com.project.toy.common.enums.YesNoType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/05
 */

@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Around("execution(public * com.project.toy.api.controller.*.*(..))")
    public Object aroundAdviceMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("ControllerAspect|aroundAdviceMethod|Start");

        Object[] parameterValues;
        Object result = null;
        StringBuilder parameterValueStr = new StringBuilder();

        String authYn = YesNoType.No.toStr();

        try {
            // 파라미터 값 추출
            parameterValues = joinPoint.getArgs();
            // 파라미터 정보
            if (parameterValues != null && parameterValues.length > 0){
                Arrays.stream(parameterValues).forEach(p -> parameterValueStr.append(p).append(","));
            }
            // 요청
            log.info("REQ|{}|{}|{}", joinPoint.getTarget().getClass().getCanonicalName(), joinPoint.getSignature().getName(), parameterValueStr.toString());
            // HttpServletRequest 객체를 가져와 Header 값을 조회
            for(int i=0; i < parameterValues.length; i++){
                if(parameterValues[i] instanceof HttpServletRequest){
                    authYn = ((HttpServletRequest)parameterValues[i]).getHeader(ApiConstants.OUKITCHEN_ACCOUNT_AUTH);
                    break;
                }
            }

            // 결과
            log.info("------------------ authYn:{} ------------------", authYn);

            // 인증 결과
            if(YesNoType.No.toStr().equals(authYn)) throw new ManagedException(ManagedExceptionCode.AuthError);

            result = joinPoint.proceed();

        }catch (Throwable throwable){
            throw throwable;
        }finally {
            log.info("ControllerAspect|aroundAdviceMethod|End");
            return result;
        }

    }
}
