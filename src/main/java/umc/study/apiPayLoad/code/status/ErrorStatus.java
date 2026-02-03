package umc.study.apiPayLoad.code.status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.study.apiPayLoad.ApiResponse;
import umc.study.apiPayLoad.code.BaseErrorCode;
import umc.study.apiPayLoad.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버에러"),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청"),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요힘"),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청"),

    _MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER400", "사용자 없음"),
    _NICKNAME_NOT_EXIT(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임 입력 필수"),

    _ARTICLE_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMMON4001", "게시글이 없습니다."),
    _TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4000", "이거는 테스트"),
    _FOOD_CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "FOOD4000", "음식 카테고리를 찾을 수 없습니다.")



    ;


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    @Override
    public ErrorReasonDTO getErrorReason() {
        return ErrorReasonDTO.builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }

    @Override
    public ErrorReasonDTO getErrorReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .httpStatus(httpStatus)
                .build();
    }
}
