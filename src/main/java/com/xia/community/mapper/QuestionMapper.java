package com.xia.community.mapper;

import com.xia.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author thisXjj
 * @date 2020/3/31 9:11 下午
 */
@Mapper
public interface QuestionMapper {
    /**
     * 插入问题
     * @param question
     * @return
     */
    @Insert("insert into question " +
            "(title,description,gmt_create,gmt_modified,creator,tag) " +
            "values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    int create(Question question);

    /**
     * 列出问题列表
     * @param offset
     * @param pageSize
     * @return
     */
    @Select("select * from question limit #{offset},#{pageSize}")
    List<Question> list(Integer offset, Integer pageSize);

    /**
     * 查询所有问题列表
     * @return
     */
    @Select("select count(*) from question")
    Integer count();

    /**
     * 通过userID查询用户的提问数
     * @param userId
     * @return
     */
    @Select("select count(*) from question where creator = #{userId}")
    Integer countByUser(Integer userId);

    /**
     * 通过userID列出问题列表
     * @param userId
     * @param offset
     * @param pageSize
     * @return
     */
    @Select("select * from question where creator = #{userId} limit #{offset},#{pageSize}")
    List<Question> listByUser(Integer userId, Integer offset, Integer pageSize);
}
