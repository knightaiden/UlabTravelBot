package net.ulab.travelbot.mapper;

import net.ulab.travelbot.model.AuthInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangzhe on 3/7/19.
 */
@Mapper
@Repository
public interface AuthMapper {

    @Select("SELECT * FROM auth_config WHERE id=#{id}")
    AuthInfo selectById(long id);

    @Update("UPDATE auth_config SET token=#{token}, last_update=datetime('now', 'localtime')  WHERE id=#{id}")
    int updateTokenById(@Param("token") String token, @Param("id") long id);
}
