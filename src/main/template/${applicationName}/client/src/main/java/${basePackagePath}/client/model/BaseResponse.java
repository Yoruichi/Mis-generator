package ${basePackage}.client.model;

import ${basePackage}.client.model.ErrorCode;
import lombok.Data;
import org.apache.http.HttpStatus;

import java.io.Serializable;

@Data
public class BaseResponse<T extends Serializable> implements Serializable {
    private static final int SUCCESS_CODE = HttpStatus.SC_OK;
    private static final String SUCCESS_MSG = "success";

    Integer code;
    String msg;
    T data;

    private CoinFoundResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T extends Serializable> CoinFoundResponse<T> failedByInvalidParameter(String parameterName) {
        return failed(ErrorCode.INVALID_PARAMETER, String.format(ErrorCode.INVALID_PARAMETER_MSG, parameterName));
    }

    public static <T extends Serializable> CoinFoundResponse<T> failedByPermission() {
        return failed(ErrorCode.ACCESS_DENIED, ErrorCode.ACCESS_DENIED_MSG);
    }

    public static <T extends Serializable> CoinFoundResponse<T> failed() {
        return failed(ErrorCode.SYSTEM_ERROR, ErrorCode.SYSTEM_ERROR_MSG);
    }

    public static <T extends Serializable> CoinFoundResponse<T> failed(Integer code, String msg) {
        return new CoinFoundResponse<>(code, msg, null);
    }

    public static <T extends Serializable> CoinFoundResponse<T> success(T data) {
        return new CoinFoundResponse<>(HttpStatus.SC_OK, SUCCESS_MSG, data);
    }

    public static <T extends Serializable> CoinFoundResponse<T> success() {
        return new CoinFoundResponse<>(HttpStatus.SC_OK, SUCCESS_MSG, null);
    }
}