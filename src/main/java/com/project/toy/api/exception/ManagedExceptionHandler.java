package com.project.toy.api.exception;

import com.project.toy.api.constant.ApiConstants;
import com.project.toy.api.dto.ApiResponseDto;
import com.project.toy.api.service.ErrorMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/06
 */
@ControllerAdvice(annotations = {RestController.class})
@Slf4j
public class ManagedExceptionHandler {

    @Autowired
    private ErrorMessageService errorMessageService;

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity managedExceptionHandler (Exception e){

        ManagedException me;
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;     //400

        log.info("Error Type|{}", e.getClass());
        if(e instanceof ManagedException){
            me = (ManagedException) e;
        }else {
            me = new ManagedException(ManagedExceptionCode.ServerError);
        }

        // 에러메시지 조회
        String msg = errorMessageService.findErrorMsg(me.getExceptionCode().toInt());

        ApiResponseDto apiResponseDto = new ApiResponseDto();

        apiResponseDto.setCode(me.getExceptionCode().toInt());
        apiResponseDto.setMsg(msg);

        return new ResponseEntity<>(apiResponseDto, httpStatus);
    }

}
