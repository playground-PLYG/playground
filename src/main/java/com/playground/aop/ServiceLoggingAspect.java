package com.playground.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ServiceLoggingAspect {
  static final String END_CHAR = "============================================================";

  @Pointcut("execution(* com.playground.api.*.service.*.*(..))")
  private void cut() {}

  /**
   * Before: 대상 “메서드”가 실행되기 전에 Advice를 실행합니다.
   *
   * @param joinPoint
   */
  @Before("cut()")
  public void logBefore(JoinPoint joinPoint) {
    log.debug("==================== Service - Before ===================");
    log.debug("  Method          : {}", joinPoint.getSignature().toString());

    Object[] args = joinPoint.getArgs();

    if (args.length == 0) {
      log.debug("  Parameter Type  : no parameter");
      log.debug("  Parameter Value : no parameter");
    } else {
      for (int i = 0; i < args.length; i++) {
        Object arg = args[i];

        if (arg == null) {
          log.debug("  Parameter Type[{}]  : null");
          log.debug("  Parameter Value[{}] : null");
        } else {
          log.debug("  Parameter Type[{}]  : {}", i, arg.getClass().getName());
          log.debug("  Parameter Value[{}] : {}", i, arg);
        }
      }
    }

    log.debug(END_CHAR);
  }

  /**
   * AfterReturning: 대상 “메서드”가 정상적으로 실행되고 반환된 후에 Advice를 실행합니다.
   *
   * @param joinPoint
   * @param result
   */
  @AfterReturning(pointcut = "cut()", returning = "result")
  public void logAfterReturning(JoinPoint joinPoint, Object result) {
    log.debug("==================== Service - After ====================");
    log.debug("  Method          : {}", joinPoint.getSignature().toString());

    Object[] args = joinPoint.getArgs();

    if (args.length == 0) {
      log.debug("  Parameter Type  : no parameter");
      log.debug("  Parameter Value : no parameter");
    } else {
      for (int i = 0; i < args.length; i++) {
        Object arg = args[i];

        if (arg == null) {
          log.debug("  Parameter Type[{}]  : null");
          log.debug("  Parameter Value[{}] : null");
        } else {
          log.debug("  Parameter Type[{}]  : {}", i, arg.getClass().getName());
          log.debug("  Parameter Value[{}] : {}", i, arg);
        }
      }
    }

    if (result == null) {
      log.debug("  Return Type     : no result");
      log.debug("  Return Value    : no result");
    } else {
      log.debug("  Return Type     : {}", result.getClass().getName());
      log.debug("  Return Value    : {}", result);
    }

    log.debug(END_CHAR);
  }

  /**
   * AfterThrowing: 대상 “메서드에서 예외가 발생”했을 때 Advice를 실행합니다.
   *
   * @param joinPoint
   * @param e
   */
  @AfterThrowing(pointcut = "cut()", throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    log.debug(">>> AfterThrowing: {} exception: {}", joinPoint.getSignature().getName(), e.getMessage());
  }

  @Around("cut()")
  public Object aroundLog(ProceedingJoinPoint joinPoint) throws Throwable {
    final StopWatch watch = new StopWatch();
    watch.start();
    Object result = joinPoint.proceed();
    watch.stop();
    log.debug("===================== Service - End =====================");
    log.debug("  Eexecution Time : {} ms", watch.getTotalTimeMillis());
    log.debug(END_CHAR);
    return result;
  }
}
