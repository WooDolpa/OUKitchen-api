package com.project.toy.api.service;

import com.project.toy.api.constant.ApiConstants;
import com.project.toy.api.repository.ApiAccountRepository;
import com.project.toy.common.enums.DataStatus;
import com.project.toy.common.model.ApiAccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class ApiAccountService {

    @Autowired
    private ApiAccountRepository apiAccountRepository;

    /**
     * API 인증키 유효성 검사
     *
     * @param clientId
     * @param secretKey
     * @return
     */
    @Cacheable(value = ApiConstants.CACHE_CLIENT_ACCOUNT, key = "{}", unless = "#result == null")
    public Optional<ApiAccountModel> findApiAccount (final String clientId, final String secretKey){

        Map<String, Object> map = new HashMap<>();

        map.put("clientId", clientId);
        map.put("dataStatus", DataStatus.ACTIVE.toInt());

        Optional<ApiAccountModel> apiAccountModelOptional = Optional.ofNullable(apiAccountRepository.findApiAccount(map));

        if(apiAccountModelOptional.isPresent()){

            ApiAccountModel apiAccountModel = apiAccountModelOptional.get();

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            if(!bCryptPasswordEncoder.matches(secretKey, apiAccountModel.getSecretKey())){
                return Optional.empty();
            }

            return apiAccountModelOptional;

        }else{
            return Optional.empty();
        }
    }

}
