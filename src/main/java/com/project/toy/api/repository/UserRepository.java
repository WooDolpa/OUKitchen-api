package com.project.toy.api.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/10
 */
@Mapper
public interface UserRepository {

    /**
     * 사용자 등록
     *
     * @param map
     * @return
     */
    @Insert("<script>                                                           "
            +"insert /* UserRepository.insertUser */ into user                  "
            +"(user_id, user_password, data_status, reg_datetime)values         "
            +"(#{userId}, #{userPassword}, #{dataStatus}, now())                "
            +"</script>                                                         "
    )
    int insertUser (Map<String, Object> map);

}
