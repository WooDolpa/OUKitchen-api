package com.project.toy.api.service;

import com.project.toy.api.constant.ApiConstants;
import com.project.toy.api.dto.UserDto;
import com.project.toy.api.exception.ManagedException;
import com.project.toy.api.exception.ManagedExceptionCode;
import com.project.toy.api.repository.UserRepository;
import com.project.toy.common.enums.UserStatus;
import com.project.toy.common.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/10
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 회원가입
     *
     * @param dto
     */
    @Transactional
    public void insertUser(final UserDto.RegDto dto){

        try {

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String password = bCryptPasswordEncoder.encode(dto.getUserPassword());

            Map<String, Object> map = new HashMap<>();

            map.put("userId", dto.getUserId());
            map.put("userPassword", password);
            map.put("userStatus", UserStatus.ACTIVE.toInt());

            userRepository.insertUser(map);

        }catch (Exception e){
            throw e;
        }

    }

    /**
     * 로그인
     *
     * @param dto
     * @return
     */
    public Optional<UserDto.ResDto> login (final UserDto.LoginReqDto dto){

        UserDto.ResDto resDto = new UserDto.ResDto();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", dto.getUserId());

        Optional<UserModel> userModelOptional = Optional.ofNullable(userRepository.findUser(map));

        if(userModelOptional.isPresent()){

            UserModel userModel = userModelOptional.get();
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            // 비밀번호 일치여부
            if(!bCryptPasswordEncoder.matches(dto.getUserPassword(), userModel.getUserPassword())){
                throw new ManagedException(ManagedExceptionCode.MisMatchPassword);
            }

            // 사용자 상태값 체크
            if(UserStatus.INACTIVE.toInt() == userModel.getUserStatus()){
                throw new ManagedException(ManagedExceptionCode.InActiveUser);
            }

            resDto.setUserId(userModel.getUserId());
            return Optional.ofNullable(resDto);

        }else{
            // 존재하지 않은 사용자
            throw new ManagedException(ManagedExceptionCode.NoExistUser);
        }
    }

    /**
     * 아이디 중복체크
     *
     * @param dto
     */
    public void userIdCheck (final UserDto.DefaultReqDto dto){

        Map<String, Object> map = new HashMap<>();
        map.put("userId", dto.getUserId());

        Optional<UserModel> userModelOptional = Optional.ofNullable(userRepository.findUser(map));

        if(userModelOptional.isPresent()){
            throw new ManagedException(ManagedExceptionCode.AlreadyUserId);
        }
    }

    /**
     * 사용자 조회
     *
     * @param userId
     * @return
     */
    public Optional<UserDto.ResDto> findUser(final String userId){

        UserDto.ResDto result = new UserDto.ResDto();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("userStatus", UserStatus.ACTIVE.toInt());

        Optional<UserModel> userModelOptional = Optional.ofNullable(userRepository.findUser(map));

        if(userModelOptional.isPresent()){

            UserModel userModel = userModelOptional.get();
            result.setUserNo(Integer.toString(userModel.getUserNo()));
            result.setUserId(userModel.getUserId());
            result.setUserStatus(Integer.toString(userModel.getUserStatus()));

            if(UserStatus.ACTIVE.toInt() == userModel.getUserStatus()){
                result.setUserStatusNm(ApiConstants.USER_STATUS_ACTIVE_NAME);   // 활성
            }else if(UserStatus.INACTIVE.toInt() == userModel.getUserStatus()){
                result.setUserStatusNm(ApiConstants.USER_STATUS_INACTIVE_NAME); // 비활성
            }

            result.setRegDatetime(userModel.getRegDatetime());
            result.setUpdDatetime(userModel.getUpdDatetime());

        }else{
            throw new ManagedException(ManagedExceptionCode.ServerError);
        }

        return Optional.ofNullable(result);
    }
}
