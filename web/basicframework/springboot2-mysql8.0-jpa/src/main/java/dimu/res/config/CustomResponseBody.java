package dimu.res.config;

/**
 * 自定义返回对象
 * @author dwx
 *
 * @param <T>
 */
public class CustomResponseBody<T> {
    
    //返回业务消息
    public String message;
    
    //返回业务代码
    public int code;
    
    //返回的业务有效数据
    public T data;
    
    public CustomResponseBody() {
        
    }
    
    public CustomResponseBody(int code) {
        this.code = code;
    }
    
    public CustomResponseBody(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
}
