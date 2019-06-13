package dimu.res.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * 全局返回数据再次封装为自定义的返回对象
 * @author dwx
 *
 */
@RestControllerAdvice("dimu.ssm")
public class GlobalResponsePayloadAdvice implements ResponseBodyAdvice<Object>{
    
    Logger log = LoggerFactory.getLogger(GlobalResponsePayloadAdvice.class);

    /**
     * 不处理GlobalExceptionHandler下面的数据返回
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getContainingClass() != GlobalExceptionHandler.class;
    }

    /**
     * 将返回的对象
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        
        CustomResponseBody<Object> responseBody = new CustomResponseBody<>(HttpStatus.OK.value());
        
        if (body == null) {
            log.info("return type is void");
        } else if (body instanceof List) {
            responseBody.setData(body);
        } else if (body instanceof Page){
            responseBody.setData(body);
        } 
        
        return responseBody;
    }

}
