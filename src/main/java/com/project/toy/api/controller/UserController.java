package com.project.toy.api.controller;

import com.project.toy.api.constant.ApiConstants;
import com.project.toy.api.dto.ApiResponseDto;
import com.project.toy.api.dto.UserDto;
import com.project.toy.api.exception.ManagedException;
import com.project.toy.api.exception.ManagedExceptionCode;
import com.project.toy.api.service.UserService;
import com.project.toy.api.service.ValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/05
 */
@RestController
@RequestMapping(path = "api/v1/user")
@Slf4j
public class UserController {

    @Autowired
    private ValidatorService validatorService;
    @Autowired
    private UserService userService;

    /**
     * 회원가입
     *
     * @param request
     * @param dto
     * @param errors
     * @return
     */
    @PostMapping(path = "/add")
    public ResponseEntity userAdd(HttpServletRequest request,
                                   @RequestBody @Valid UserDto.RegDto dto,
                                   BindingResult errors){

        Optional<ResponseEntity> responseEntityOptional = validatorService.validateParameter(errors);

        if(responseEntityOptional.isPresent()){
            return responseEntityOptional.get();
        }

        userService.insertUser(dto);        // 사용자 등록

        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setCode(ApiConstants.RES_CODE_SUCCESS);
        apiResponseDto.setMsg(ApiConstants.RES_MSG_SUCCESS);

        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    /**
     * 아이디 중복체크
     *
     * @param request
     * @param dto
     * @param errors
     * @return
     */
    @PostMapping(path = "/login/check")
    public ResponseEntity loginCheck(HttpServletRequest request,
                                     @RequestBody @Valid UserDto.DefaultReqDto dto,
                                     BindingResult errors){

        Optional<ResponseEntity> responseEntityOptional = validatorService.validateParameter(errors);

        if(responseEntityOptional.isPresent()){
            return responseEntityOptional.get();
        }

        userService.userIdCheck(dto);

        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setCode(ApiConstants.RES_CODE_SUCCESS);
        apiResponseDto.setMsg(ApiConstants.RES_MSG_SUCCESS);

        return new ResponseEntity(apiResponseDto, HttpStatus.OK);
    }

    /**
     * 로그인
     *
     * @param request
     * @param dto
     * @param errors
     * @return
     */
    @PostMapping(path = "/login")
    public ResponseEntity login(HttpServletRequest request,
                                @RequestBody @Valid UserDto.LoginReqDto dto,
                                BindingResult errors){

        Optional<ResponseEntity> responseEntityOptional = validatorService.validateParameter(errors);

        if(responseEntityOptional.isPresent()){
            return responseEntityOptional.get();
        }

        userService.login(dto);

        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setCode(0);
        apiResponseDto.setMsg(ApiConstants.RES_MSG_SUCCESS);

        return new ResponseEntity(apiResponseDto, HttpStatus.OK);
    }

    /**
     * 사용자 정보 조회
     *
     * @param request
     * @param userId
     * @return
     */
    @GetMapping(path = "/info")
    public ResponseEntity userInfo (HttpServletRequest request,
                                    @RequestParam(value = "userId", defaultValue = "") final String userId){

        if("".equals(userId)){
            throw new ManagedException(ManagedExceptionCode.InvalidId);
        }

        Optional<UserDto.ResDto> dto = userService.findUser(userId);

        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setCode(ApiConstants.RES_CODE_SUCCESS);
        apiResponseDto.setMsg(ApiConstants.RES_MSG_SUCCESS);
        apiResponseDto.setData(dto.get());

        return new ResponseEntity(apiResponseDto, HttpStatus.OK);
    }

    /**
     * 사용자 업데이트
     *
     * @param request
     * @param dto
     * @param errors
     * @return
     */
    @PostMapping(path = "/update")
    public ResponseEntity userUpdate (HttpServletRequest request,
                                      @RequestBody @Valid UserDto.UpdateDto dto,
                                      BindingResult errors){

        Optional<ResponseEntity> responseEntityOptional = validatorService.validateParameter(errors);

        if(responseEntityOptional.isPresent()){
            return responseEntityOptional.get();
        }

        userService.updateUser(dto);


        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setCode(ApiConstants.RES_CODE_SUCCESS);
        apiResponseDto.setMsg(ApiConstants.RES_MSG_SUCCESS);

        return new ResponseEntity(apiResponseDto, HttpStatus.OK);
    }

}
