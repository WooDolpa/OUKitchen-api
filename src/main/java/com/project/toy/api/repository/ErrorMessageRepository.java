package com.project.toy.api.repository;

import com.project.toy.common.model.ErrorMsgModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/06
 */
@Mapper
public interface ErrorMessageRepository {

    @Select("<script>                                                                               "
            +"select /* ErrorMessageRepository.findErrorMessage */                                  "
            +"*                                                                                     "
            +"from error_msg                                                                        "
            +"<where>                                                                               "
            +"<if test=\"errorCode != null\">                                                       "
            +"error_code = #{errorCode}                                                             "
            +"</if>                                                                                 "
            +"<if test=\"dataStatus != null \">                                                     "
            +"and data_status =#{dataStatus}                                                        "
            +"</if>                                                                                 "
            +"</where>                                                                              "
            +"</script>                                                                             "
    )
    ErrorMsgModel findErrorMessage (Map<String, Object> map);

}
