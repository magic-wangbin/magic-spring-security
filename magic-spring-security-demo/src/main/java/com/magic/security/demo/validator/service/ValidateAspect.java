/**
 *
 */
package com.magic.security.demo.validator.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author zhailiang
 *
 */
@Aspect
@Component
public class ValidateAspect {

    @Around("execution(* com.magic.security.demo.web.UserController.*(..))")
    public Object handleValidateResult(ProceedingJoinPoint pjp) throws Throwable {

        System.out.println("进入切片");

        Object[] args = pjp.getArgs();
        if (args.length == 0) {
            return pjp.proceed();
        }

        for (Object object : args) {
            // 校验实体
            if (object instanceof BeanPropertyBindingResult) {
                BeanPropertyBindingResult errors = (BeanPropertyBindingResult) object;
                if (errors.hasErrors()) {
                    throw new ValidateException(errors.getAllErrors());
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
            System.out.println("有校验结果");
            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
            // 获得方法的参数名称
            for(ConstraintViolation<Object> constraintViolation : validResult) {
                PathImpl pathImpl = (PathImpl) constraintViolation.getPropertyPath();
                // 获得校验的参数路径信息
                int paramIndex = pathImpl.getLeafNode().getParameterIndex();
                // 获得校验的参数位置
                String paramName = parameterNames[paramIndex];
                // 获得校验的参数名称
                System.out.println(paramName);
                //校验信息
                System.out.println(constraintViolation.getMessage());
            }
            //返回第一条
            return validResult.iterator().next().getMessage();
        }



        Object result = pjp.proceed();

        return result;
    }

    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final ExecutableValidator validator = factory.getValidator().forExecutables();

    private <T> Set<ConstraintViolation<T>> validMethodParams(T obj, Method method, Object[] params) {
        return validator.validateParameters(obj, method, params);
    }
}
