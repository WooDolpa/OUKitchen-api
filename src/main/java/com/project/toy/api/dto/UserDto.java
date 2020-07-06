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
     * 로그인 정보
     *
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class LoginReqDto implements Serializable {

        private static final long serialVersionUID = -8699263325984010854L;
        @NotEmpty
        private String id;              // 아이디
        @NotEmpty
        private String password;        // 패스워드
    }

}