package com.itheima.exception;

import com.itheima.domain.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 业务异常：消息可以直接给用户看 */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusiness(BusinessException e) {
        System.out.println("业务异常：" + e.getMessage());
        return Result.error(e.getMessage());
    }

    /** 参数类型错误：比如 /books/abc */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<Void> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        return Result.error("参数格式错误：" + e.getName());
    }

    /** 兜底：所有没被上面接住的异常 */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        e.printStackTrace();          // 堆栈打到控制台，给开发者看
        return Result.error("系统繁忙，请稍后再试");   // 给用户看
    }
}
