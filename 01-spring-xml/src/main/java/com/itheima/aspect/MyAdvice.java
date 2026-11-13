package com.itheima.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class MyAdvice {

    @Pointcut("execution(* com.itheima.service.*.save(..))")

    public void pt() {
    }

    @Around("pt()")
    // ↑ 环绕通知：把目标方法整个包起来，前后都能插代码
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();
        // ↑ 纳秒，比 currentTimeMillis 精度高

        Object result = pjp.proceed();
        // ↑ 执行目标方法（save）

        long end = System.nanoTime();
        System.out.println("save() 耗时：" + (end - start) / 1_000_000.0 + " ms");
        // ↑ 纳秒转毫秒

        return result;
        // ↑ 目标方法的返回值，必须原样返回，否则调用方拿到的是 null
    }

    @Pointcut("execution(* com.itheima.service.BookService.findName(*))")
// ↑ * 返回值任意；findName(*) 表示「一个任意类型的参数」
    public void pt2() {
    }

    @Before("pt2()")
    public void beforeFindName(JoinPoint jp) {
        // ↑ JoinPoint 是「连接点」对象，里面装着当前被调用的方法信息
        System.out.println("方法名：" + jp.getSignature().getName());
        System.out.println("参数：" + Arrays.toString(jp.getArgs()));
        System.out.println("目标对象：" + jp.getTarget().getClass().getName());
    }

    @AfterReturning(pointcut = "pt2()", returning = "ret")
// ↑ returning = "ret"：把目标方法的返回值绑定到 ret 参数上
//   参数名必须和 returning 的值一模一样
    public void afterReturningFindName(Object ret) {
        System.out.println("返回值：" + ret);
    }


    @Pointcut("execution(* com.itheima.service.BookService.login(..))")
// ↑ (..) 表示「任意个数、任意类型的参数」
    public void pt3() {
    }

    @Around("pt3()")
    public Object aroundLogin(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        // ↑ 拿到参数数组：[username, password]

        String password = (String) args[1];
        password = password.trim();
        // ↑ 兼容处理：去掉首尾空格（百度网盘老数据里常见）

        args[1] = password;
        // ↑ 把改好的密码放回参数数组

        return pjp.proceed(args);
        // ↑ 用「修改后的参数」执行目标方法
        //   如果写 pjp.proceed()（不带参数），目标方法拿到的还是原始参数
    }

}
