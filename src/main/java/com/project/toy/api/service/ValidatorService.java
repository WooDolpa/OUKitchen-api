package com.project.toy.api.service;

import com.project.toy.api.constant.ApiConstants;
import com.project.toy.api.dto.ApiResponseDto;
import com.project.toy.api.exception.ManagedExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/06
 */
@Service
@Slf4j
public class ValidatorService {

    @Autowired
    private ErrorMessageService errorMessageService;

    public Optional<ResponseEntity> validateParameter(final BindingResult errors){

        if(errors.hasErrors()){

            ApiResponseDto apiResponseDto = new ApiResponseDto();

            errors.getFieldErrors().stream().forEach(p -> {

                if(ApiConstants.FIELD_ID.equals(p.getField())){
                    setErrorFormat(apiResponseDto, ManagedExceptionCode.InvalidId.toInt());
                    return;
                }

                if(ApiConstants.FIELD_PASSWORD.equals(p.getField())){
                    setErrorFormat(apiResponseDto, ManagedExceptionCode.InvalidPassword.toInt());
                    return;
                }

            });

            return Optional.of(new ResponseEntity(apiResponseDto, HttpStatus.BAD_REQUEST));
        }

        return Optional.empty();
    }

    /**
     * 에러정보 넣기
     *
     * @param errorCode
     * @return
     */
    private void setErrorFormat(final ApiResponseDto apiResponseDto, int errorCode){

        String msg = errorMessageService.findErrorMsg(errorCode);

        apiResponseDto.setCode(errorCode);
        apiResponseDto.setMsg(msg);

    }
}
