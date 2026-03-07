package com.henan.heritage.common;

/**
 * 统一返回结果类
 * 用于规范API接口返回格式
 */
public class Result<T> {
    /**
     * 状态码
     * 200: 成功
     * 400: 请求错误
     * 401: 未授权
     * 500: 服务器内部错误
     */
    private int code;
    
    /**
     * 提示信息
     */
    private String message;
    
    /**
     * 返回数据
     */
    private T data;
    
    /**
     * 总记录数（用于分页）
     */
    private Integer total;
    
    /**
     * 构造方法
     * @param code 状态码
     * @param message 提示信息
     * @param data 返回数据
     */
    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    /**
     * 构造方法（带总记录数）
     * @param code 状态码
     * @param message 提示信息
     * @param data 返回数据
     * @param total 总记录数
     */
    private Result(int code, String message, T data, Integer total) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.total = total;
    }
    
    /**
     * 成功返回
     * @param data 返回数据
     * @param <T> 数据类型
     * @return Result对象
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }
    
    /**
     * 成功返回（带总记录数）
     * @param data 返回数据
     * @param total 总记录数
     * @param <T> 数据类型
     * @return Result对象
     */
    public static <T> Result<T> success(T data, Integer total) {
        return new Result<>(200, "success", data, total);
    }
    
    /**
     * 成功返回（无数据）
     * @param <T> 数据类型
     * @return Result对象
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "success", null);
    }
    
    /**
     * 失败返回
     * @param code 状态码
     * @param message 错误信息
     * @param <T> 数据类型
     * @return Result对象
     */
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }
    
    /**
     * 请求错误返回
     * @param message 错误信息
     * @param <T> 数据类型
     * @return Result对象
     */
    public static <T> Result<T> badRequest(String message) {
        return new Result<>(400, message, null);
    }
    
    /**
     * 未授权返回
     * @param message 错误信息
     * @param <T> 数据类型
     * @return Result对象
     */
    public static <T> Result<T> unauthorized(String message) {
        return new Result<>(401, message, null);
    }
    
    /**
     * 服务器内部错误返回
     * @param message 错误信息
     * @param <T> 数据类型
     * @return Result对象
     */
    public static <T> Result<T> serverError(String message) {
        return new Result<>(500, message, null);
    }
    
    // getter方法
    public int getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public T getData() {
        return data;
    }
    
    public Integer getTotal() {
        return total;
    }
}
