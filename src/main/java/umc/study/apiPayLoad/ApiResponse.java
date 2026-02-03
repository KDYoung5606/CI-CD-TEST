package umc.study.apiPayLoad;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import umc.study.apiPayLoad.code.BaseCode;
import umc.study.apiPayLoad.code.status.SuccessStatus;

@Getter
@Builder
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse <T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T result;

    public static <T> ApiResponse<T> onSuccess(T data){
        return new ApiResponse<>(true, SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), data);
    }

    public static <T> ApiResponse<T> of(BaseCode code, T data){
        return new ApiResponse<>(true, code.getReason().getCode(), code.getReasonHttpStatus().getMessage(), data);
    }


    public static <T> ApiResponse<T> onFailure(String code, String message, T data){
        return new ApiResponse<>(false, code, message, data);
    }
}
