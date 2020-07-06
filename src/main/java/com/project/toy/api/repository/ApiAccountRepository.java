package com.project.toy.api.repository;

import com.project.toy.common.model.ApiAccountModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/06
 */
@Mapper
public interface ApiAccountRepository {

    @Select("<script>                                                   "
            +"select /* ApiAccountRepository.findApiAccount */          "
            +"*                                                         "
            +"from api_account                                          "
            +"<where>                                                   "
            +"<if test=\"clientId != null and clientId != '' \">        "
            +"client_id = #{clientId}                                   "
            +"</if>                                                     "
            +"<if test=\"dataStatus != null \">                         "
            +"and data_status = #{dataStatus}                           "
            +"</if>                                                     "
            +"</where>                                                  "
            +"</script>                                                 "
    )
    ApiAccountModel findApiAccount(Map<String, Object> map);

}
