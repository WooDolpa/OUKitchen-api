package com.project.toy.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/05
 */
public class UserDto {

    /**
     * 사용자 정보 기본 요청
     *
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DefaultReqDto implements Serializable {

        private static final long serialVersionUID = 6018103877969062833L;
        @NotEmpty
        private String userId;
    }

    /**
     * 로그인 정보
     *
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class LoginReqDto implements Serializable {

        private static final long serialVersionUID = -8699263325984010854L;
        @NotEmpty
        private String userId;                          // 아이디
        @NotEmpty
        private String userPassword;                    // 패스워드
    }

    /**
     * 회원가입 정보
     *
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RegDto implements Serializable {

        private static final long serialVersionUID = -2794366792295686685L;
        @NotEmpty
        private String userId;                          // 아이디
        @NotEmpty
        private String userPassword;                    // 패스워드
    }

    /**
     * API 응답값
     *
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ResDto implements Serializable {

        private static final long serialVersionUID = -5895007915944151155L;

        private String userNo;                          // 사용자 번호
        private String userId;                          // 아이디
        private String userStatus;                      // 상태
        private String userStatusNm;                    // 상태명
        private String regDatetime;                     // 등록일
        private String updDatetime;                     // 수정일
    }
}
