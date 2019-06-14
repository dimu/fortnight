package dimu.res.config;

/**
 * custom exception enum
 * 
 * @author dimu
 *
 */
public enum CustomExceptionEnum {

    NoException(200, "处理成功");

    private int errorCode;

    private String errorMessage;

    /**
     * private constructor, can't initialize
     * @param errorCode
     * @param errorMessage
     */
    private CustomExceptionEnum(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
