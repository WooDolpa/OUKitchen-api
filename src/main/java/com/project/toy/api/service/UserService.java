package com.project.toy.api.service;

import com.project.toy.api.dto.UserDto;
import com.project.toy.api.repository.UserRepository;
import com.project.toy.common.enums.DataStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

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

}
