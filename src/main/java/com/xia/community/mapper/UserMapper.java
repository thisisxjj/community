package com.xia.community.mapper;

import com.xia.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;


public interface UserMapper {
    @Insert("insert into user " +
            "(account_id,name,token,bio,gmt_create,gmt_modified,avatar_url) " +
            "values(#{accountId},#{name},#{token},#{bio},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    int insert(User user);

    @Select("select * from user where token = #{token}")
    User selectByToken(String token);

    @Select("select * from user where id = #{id}")
    User selectById(Integer id);

    @Select("<script>" +
            "select " +
            "* " +
            "from user " +
            "where id in " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    List<User> selectByIdSet(@Param("ids") Set<Integer> userIdSet);
}
