package com.project.toy.api.repository;

import com.project.toy.common.model.UserModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    @Insert("<script>                                                                                                   "
            +"insert /* UserRepository.insertUser */ into user                                                          "
            +"(user_id, user_password, user_status, reg_datetime)values                                                 "
            +"(#{userId}, #{userPassword}, #{userStatus}, now())                                                        "
            +"</script>                                                                                                 "
    )
    int insertUser (Map<String, Object> map);

    /**
     * 사용자 조회
     *
     * @param map
     * @return
     */
    @Select("<script>                                                                                                   "
            +"select /* UserRepository.findUser */                                                                      "
            +"*                                                                                                         "
            +"from user                                                                                                 "
            +"<where>                                                                                                   "
            +"1=1                                                                                                       "
            +"<if test=\"userId != null\">                                                                              "
            +" and user_id = #{userId}                                                                                  "
            +"</if>                                                                                                     "
            +"<if test=\"dataStatus != null \">                                                                         "
            +" and data_status = #{dataStatus}                                                                          "
            +"</if>                                                                                                     "
            +"</where>                                                                                                  "
            +"</script>                                                                                                 "
    )
    UserModel findUser (Map<String, Object> map);

    /**
     * 사용자 수정
     *
     * @param map
     * @return
     */
    @Update("<script>                                                                                                   "
            +"update user /* UserRepository.updateUser */                                                               "
            +"<trim prefix=\"set\" suffixOverrides=\",\">                                                               "
            +"<if test=\"userId != null \">             user_id = #{userId},                    </if>                   "
            +"<if test=\"userPassword != null \">       user_password = #{userPassword},        </if>                   "
            +"<if test=\"userStatus != null \">         user_status = #{userStatus},            </if>                   "
            +"upd_datetime = now()                                                                                      "
            +"</trim>                                                                                                   "
            +"<where>                                                                                                   "
            +"<if test=\"userNo != null\">                                                                              "
            +"user_no = #{userNo}                                                                                       "
            +"</if>                                                                                                     "
            +"</where>                                                                                                  "
            +"</script>                                                                                                 "
    )
    int updateUser (Map<String, Object> map);
}
