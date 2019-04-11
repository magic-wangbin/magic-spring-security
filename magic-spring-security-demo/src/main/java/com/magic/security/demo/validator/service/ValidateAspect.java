/**
 *
 */
package com.magic.security.demo.validator.service;

import com.magic.security.demo.exception.ValidateException;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * @author wang.bin
 *
 */
@Aspect
@Component
public class ValidateAspect {

    @Autowired
    private LocalValidatorFactoryBean localValidatorFactoryBean;

    @Autowired
    @Qualifier("localVariableTableParameterNameDiscoverer")
    private ParameterNameDiscoverer parameterNameDiscoverer;

    @Around("execution(* com.magic.security.demo.web.UserController.*(..))")
    public Object handleValidateResult(ProceedingJoinPoint pjp) throws Throwable {

        Object[] args = pjp.getArgs();
        if (args.length == 0) {
            return pjp.proceed();
        }

        for (Object object : args) {
            // 校验实体
            if (object instanceof BeanPropertyBindingResult) {
                BeanPropertyBindingResult errors = (BeanPropertyBindingResult) object;
                List<ObjectError> errorList = errors.getAllErrors();
                if (CollectionUtils.isNotEmpty(errorList)) {
                    throw new ValidateException(errorList.get(0).getDefaultMessage());
                }

            }
        }

        // 普通参数的校验
        //  获得切入目标对象
        Object target = pjp.getThis();
        //获取方法
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        // 执行校验，获得校验结果
        Set<ConstraintViolation<Object>> validResult = validMethodParams(target, method, args);
        //
        if (!validResult.isEmpty()) {
            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
            // 获得方法的参数名称
            for (ConstraintViolation<Object> constraintViolation : validResult) {
                //校验信息
                throw new ValidateException(constraintViolation.getMessage());
            }


        }


        Object result = pjp.proceed();

        return result;
    }

    private <T> Set<ConstraintViolation<T>> validMethodParams(T obj, Method method, Object[] params) {
        ExecutableValidator validator = localValidatorFactoryBean.getValidator().forExecutables();
        return validator.validateParameters(obj, method, params);
    }
}
