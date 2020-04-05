package com.xia.community.mapper;

import com.xia.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Set;


public interface UserMapper {
    @Insert("insert into user " +
            "(account_id,name,token,bio,gmt_create,gmt_modified,avatar_url) " +
            "values(#{accountId},#{name},#{token},#{bio},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    Integer insert(User user);

    @Select("select * from user where token = #{token}")
    User selectByToken(String token);

    @Select("select * from user where id = #{id}")
    User selectById(Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User selectByAccountId(Integer id);

    @Select("<script>" +
            "select " +
            "* " +
            "from user " +
            "<where> " +
            "<if test='ids.size()>0'> " +
            "id in " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</if> " +
            "</where>" +
            "</script>")
    List<User> selectByIdSet(@Param("ids") Set<Integer> userIdSet);

    @Update("update user set name=#{name}, token=#{token}, gmt_Modified=#{gmtModified}, bio=#{bio}, avatar_url=#{avatarUrl} where id=#{id}")
    Integer update(User dbUser);
}
