package cn.byssted.bbs.bbsrd.common;

/**
 * 通用响应结果类
 */
public class Result<T> {
    
    private Integer code;
    private String message;
    private T data;
    
    // 私有构造函数
    private Result() {}
    
    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    // 成功响应
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }
    
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }
    
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }
    
    // 失败响应
    public static <T> Result<T> error() {
        return new Result<>(500, "操作失败", null);
    }
    
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }
    
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }
    
    // 参数错误
    public static <T> Result<T> badRequest(String message) {
        return new Result<>(400, message, null);
    }
    
    // 未授权
    public static <T> Result<T> unauthorized(String message) {
        return new Result<>(401, message, null);
    }
    
    // 禁止访问
    public static <T> Result<T> forbidden(String message) {
        return new Result<>(403, message, null);
    }
    
    // 资源未找到
    public static <T> Result<T> notFound(String message) {
        return new Result<>(404, message, null);
    }
    
    // Getter 和 Setter 方法
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
