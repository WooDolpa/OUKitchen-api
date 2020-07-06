package com.project.toy.api.service;

import com.project.toy.api.constant.ApiConstants;
import com.project.toy.api.repository.ErrorMessageRepository;
import com.project.toy.common.enums.DataStatus;
import com.project.toy.common.model.ErrorMsgModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/06
 */
@Service
@Slf4j
public class ErrorMessageService {

    @Autowired
    private ErrorMessageRepository errorMessageRepository;

    /**
     * 에러코드를 통한 에러메시지 조회
     *
     * @param errorCode
     * @return
     */
    @Cacheable(value = ApiConstants.CACHE_ERROR_MSG, key = "{#errorCode}", unless = "#result== null")
    public String findErrorMsg (int errorCode){

        Map<String, Object> map = new HashMap<>();

        map.put("errorCode", errorCode);
        map.put("dataStatus", DataStatus.ACTIVE.toInt());

        Optional<ErrorMsgModel> errorMsgModelOptional = Optional.ofNullable(errorMessageRepository.findErrorMessage(map));

        if(errorMsgModelOptional.isPresent()){
            ErrorMsgModel errorMsgModel = errorMsgModelOptional.get();
            return errorMsgModel.getErrorMsg();
        }

        return ApiConstants.UNKNOWN_ERROR_MSG;
    }
}
