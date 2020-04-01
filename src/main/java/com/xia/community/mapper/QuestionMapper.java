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
    @Insert("insert into question " +
            "(title,description,gmt_create,gmt_modified,creator,tag) " +
            "values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    int create(Question question);

    @Select("select * from question limit #{offset},#{pageSize}")
    List<Question> list(Integer offset, Integer pageSize);

    @Select("select count(*) from question")
    Integer count();
}
