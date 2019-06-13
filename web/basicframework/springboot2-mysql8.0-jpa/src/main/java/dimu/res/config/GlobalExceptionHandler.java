package dimu.res.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * add global exception handler
 * @author dwx
 *
 */
@ControllerAdvice("dimu.ssm")
public class GlobalExceptionHandler {
    
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * custom exception handler
     * @param ex custom exception
     * @return 
     */
    @ExceptionHandler({CustomException.class})
    public ResponseEntity<CustomResponseBody<Object>> handleCustomException(CustomException ex) {
        LOG.error("exception:{}, handling exception: {}", ex.getStackTrace(), ex.getClass().getName());
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        
        CustomResponseBody<Object> res = new CustomResponseBody<>();
        res.setCode(ex.getErrorCode());
        res.setMessage(ex.getMessage());
        
        return new ResponseEntity<>(res, headers, HttpStatus.OK);
    }
}
