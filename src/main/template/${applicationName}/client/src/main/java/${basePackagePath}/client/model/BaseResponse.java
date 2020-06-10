package ${basePackage}.client.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.io.Serializable;
import static ${basePackage}.client.model.ErrorCode.*;
@Data
public class BaseResponse<T extends Serializable> implements Serializable {
    Integer code;
    String msg;
    T data;

    private BaseResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    @JsonIgnore
    public boolean isSuccess() {
        return ErrorCode.SUCCESS_CODE == this.code.intValue();
    }

    public static <T extends Serializable> BaseResponse<T> failedByInvalidParameter(String parameterName) {
        return failed(INVALID_PARAMETER, String.format(INVALID_PARAMETER_MSG, parameterName));
    }

    public static <T extends Serializable> BaseResponse<T> failedByPermission() {
        return failed(ACCESS_DENIED, ACCESS_DENIED_MSG);
    }

    public static <T extends Serializable> BaseResponse<T> failed() {
        return failed(SYSTEM_ERROR, SYSTEM_ERROR_MSG);
    }

    public static <T extends Serializable> BaseResponse<T> failed(Integer code, String msg) {
        return new BaseResponse<>(code, msg, null);
    }

    public static <T extends Serializable> BaseResponse<T> success(T data) {
        return new BaseResponse<>(SUCCESS_CODE, SUCCESS_MSG, data);
    }

    public static <T extends Serializable> BaseResponse<T> success() {
        return new BaseResponse<>(SUCCESS_CODE, SUCCESS_MSG, null);
    }
}