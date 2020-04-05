package com.xia.community.service.impl;

import com.xia.community.dto.Pagination;
import com.xia.community.dto.QuestionDTO;
import com.xia.community.mapper.QuestionMapper;
import com.xia.community.mapper.UserMapper;
import com.xia.community.model.Question;
import com.xia.community.model.User;
import com.xia.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author thisXjj
 * @date 2020/4/1 11:03 上午
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Pagination list(Integer pageNum, Integer pageSize) {
        pageNum = pageNum <= 1 ? 1 : pageNum;
        Integer totalCount = questionMapper.count();
        Integer totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        totalPage = totalPage < 1 ? 1 : totalPage;
        pageNum = pageNum > totalPage ? totalPage : pageNum;
        // 起始索引
        Integer offset = (pageNum - 1) * pageSize;
        List<Question> questionList = questionMapper.list(offset, pageSize);
        // 遍历找出所有问题的创建者的id集合
        Set<Integer> userIdSet = questionList.stream().map(Question::getCreator).collect(Collectors.toSet());
        // 通过id集合，找出所有的user对象
        List<User> userList = userMapper.selectByIdSet(userIdSet);
        // 映射成map<id,user>的集合
        Map<Integer, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, user -> user));
        List<QuestionDTO> questionDTOList = questionList.stream().map(question -> {
            // 如果从question list中查询的question id等于userMap中的key，也就是user id
            User user = userMap.get(question.getCreator());
            // 如果user 不等于空，则说明这个question的创建者就是这个user
            // 所以可以新建一个questionDTO对象,把question对象映射成questionDTO
//            if (user != null) {
            QuestionDTO questionDTO = QuestionDTO
                    .builder()
                    .user(user)
                    .build();
            BeanUtils.copyProperties(question, questionDTO);
            return questionDTO;
//            }
            // 如果user等于空，则说明question没有创建者，直接返回空
//            return null;
        }).collect(Collectors.toList());
        Pagination pagination = Pagination.builder().questionList(questionDTOList).build();
        pagination.setPagination(pageNum, pageSize, totalCount, totalPage);
//        List<QuestionDTO> questionDTOList = new ArrayList<>();
        // 在for循环里面遍历查询数据库，性能低下
//        for (Question question : questionList) {
//            User user = userMapper.selectById(question.getCreator());
//            QuestionDTO questionDTO = QuestionDTO.builder().build();
//            BeanUtils.copyProperties(question, questionDTO);
//            questionDTO.setUser(user);
//            questionDTOList.add(questionDTO);
//        }
        return pagination;
    }

    @Override
    public Pagination list(User user, Integer pageNum, Integer pageSize) {
        pageNum = pageNum <= 1 ? 1 : pageNum;
        Integer totalCount = questionMapper.countByUser(user.getId());
        Integer totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        totalPage = totalPage < 1 ? 1 : totalPage;
        pageNum = pageNum > totalPage ? totalPage : pageNum;
        // 起始索引
        Integer offset = (pageNum - 1) * pageSize;
        List<Question> questionList = questionMapper.listByUser(user.getId(), offset, pageSize);
        // 将Question集合转换成QuestionDTO集合
        List<QuestionDTO> questionDTOList = questionList.stream().map(question -> {
            QuestionDTO questionDTO = QuestionDTO.builder()
                    .user(user)
                    .build();
            BeanUtils.copyProperties(question, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        Pagination pagination = Pagination.builder().questionList(questionDTOList).build();
        pagination.setPagination(pageNum, pageSize, totalCount, totalPage);
        return pagination;
    }

    @Override
    public QuestionDTO selectOne(Integer id) {
        // 通过问题id查询问题
        Question question = questionMapper.selectById(id);
        if (question == null) {
            throw new RuntimeException("该id无法查询到问题");
        }
        // 通过问题创建者id查询出这个问题的创建者
        User user = userMapper.selectById(question.getCreator());
        QuestionDTO questionDTO = QuestionDTO.builder().user(user).build();
        BeanUtils.copyProperties(question, questionDTO);
        return questionDTO;
    }



    @Override
    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            // 创建问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            Integer row = questionMapper.create(question);
            if (row < 0) {
                throw new RuntimeException("创建问题异常, 问题创建者：" + question.getCreator());
            }
        } else {
            // 更新问题
            question.setGmtModified(System.currentTimeMillis());
            Integer row = questionMapper.update(question);
            if (row < 0) {
                throw new RuntimeException("更新问题异常, 问题创建者：" + question.getCreator());
            }
        }
    }
}
