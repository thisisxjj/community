package com.xia.community.mapper;

import com.xia.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;


public interface UserMapper {
    @Insert("insert into user " +
            "(account_id,name,token,bio,gmt_create,gmt_modified) " +
            "values(#{accountId},#{name},#{token},#{bio},#{gmtCreate},#{gmtModified})")
    int insert(User user);

    @Select("select * from user where token = #{token}")
    User selectByToken(String token);
}
