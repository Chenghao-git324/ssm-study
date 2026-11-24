package com.itheima.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    /** 1 = 成功，0 = 失败 */
    private Integer code;

    /** 失败时的提示信息 */
    private String msg;

    /** 真正的业务数据 */
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(1, null, data);
    }

    public static <T> Result<T> success() {
        return new Result<>(1, null, null);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(0, msg, null);
    }
}
