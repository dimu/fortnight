package dimu.res.config;

/**
 * custom exception
 * @author dimu
 *
 */
public class CustomException extends Exception{

    private static final long serialVersionUID = 1544032775640969569L;

    public int errorCode;
    
    public CustomException() {
        super();
    }
    
    public CustomException(CustomExceptionEnum ex, Throwable cause) {
        super(ex.getErrorMessage(), cause);
        this.errorCode = ex.getErrorCode();
    }
    
    public CustomException(int errorCode, String message,  Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    
    
}
