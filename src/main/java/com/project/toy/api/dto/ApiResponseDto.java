package com.project.toy.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/05
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiResponseDto<T> implements Serializable {

    private static final long serialVersionUID = 5755736816380424742L;

    private int code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;


}
