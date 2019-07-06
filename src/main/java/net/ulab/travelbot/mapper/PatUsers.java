package net.ulab.travelbot.mapper;

import net.ulab.travelbot.model.PatUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangzhe on 6/7/19.
 */
@Mapper
@Repository
public interface PatUsers {

    @Select("SELECT * FROM pat_users WHERE frontend_key=#{frontend_key}")
    PatUser getPatUserByFID(@Param("frontend_key") String frontendKey);

    @Update("UPDATE pat_users SET pat_user_key=#{pat_user_key}  WHERE frontend_key=#{frontend_key}")
    int updatePatUserKeyByFID(@Param("pat_user_key") String PatUserKey, @Param("frontend_key") String frontendKey);

    @Insert("Insert into pat_users VALUES (#{frontend_key}, #{pat_user_key})")
    int insertPatUserInfo(PatUser patUser);

    @Delete("Delete from pat_users where frontend_key=#{frontend_key}")
    int deletePatUserById(@Param("frontend_key") String frontendKey);

}
