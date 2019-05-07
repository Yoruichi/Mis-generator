package ${basePackage}.client.model;

/**
 * @Author: Yoruichi
 * @Date: 2018/11/27 6:46 PM
 */
public class ErrorCode {
    public static final int INVALID_PARAMETER = 400;
    public static final String INVALID_PARAMETER_MSG = "Invalid parameter %s for given.";
    public static final int ACCESS_DENIED = 403;
    public static final String ACCESS_DENIED_MSG = "Access denied.";
    public static final int MISSING = 404;
    public static final String MISSING_MSG = "Missing something";
    public static final int SYSTEM_ERROR = 500;
    public static final String SYSTEM_ERROR_MSG = "System error, we're working on it.";

}
