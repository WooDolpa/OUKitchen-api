package com.project.toy.api.service;

import com.project.toy.api.dto.UserDto;
import com.project.toy.api.exception.ManagedException;
import com.project.toy.api.exception.ManagedExceptionCode;
import com.project.toy.api.repository.UserRepository;
import com.project.toy.common.enums.DataStatus;
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
            map.put("dataStatus", DataStatus.ACTIVE.toInt());

            userRepository.insertUser(map);

        }catch (Exception e){
            throw e;
        }

    }

    public Optional<UserDto.ResDto> loginCheck (final UserDto.LoginReqDto dto){

        UserDto.ResDto resDto = new UserDto.ResDto();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", dto.getUserId());

        Optional<UserModel> userModelOptional = Optional.ofNullable(userRepository.findUser(map));

        if(userModelOptional.isPresent()){

            UserModel userModel = userModelOptional.get();
            log.info("UserModel : {}", userModel);

            // 비밀번호 일치 여부
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            if(!bCryptPasswordEncoder.matches(dto.getUserPassword(), userModel.getUserPassword())){
                throw new ManagedException(ManagedExceptionCode.MisMatchPassword);
            }

            if(DataStatus.INACTIVE.toInt() == userModel.getDataStatus()){
                throw new ManagedException(ManagedExceptionCode.InActiveUser);
            }

            resDto.setUserId(userModel.getUserId());
            return Optional.ofNullable(resDto);

        }else{
            throw new ManagedException(ManagedExceptionCode.NoExistUser);
        }
    }

}
