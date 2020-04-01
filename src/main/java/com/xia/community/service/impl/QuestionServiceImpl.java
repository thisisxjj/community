package com.xia.community.service.impl;

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
    public List<QuestionDTO> list() {
        List<Question> questionList = questionMapper.list();
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
            if (user != null) {
                QuestionDTO questionDTO = QuestionDTO
                        .builder()
                        .user(user)
                        .build();
                BeanUtils.copyProperties(question, questionDTO);
                return questionDTO;
            }
            // 如果user等于空，则说明question没有创建者，直接返回空
            return null;
        }).collect(Collectors.toList());
//        List<QuestionDTO> questionDTOList = new ArrayList<>();
        // 在for循环里面遍历查询数据库，性能低下
//        for (Question question : questionList) {
//            User user = userMapper.selectById(question.getCreator());
//            QuestionDTO questionDTO = QuestionDTO.builder().build();
//            BeanUtils.copyProperties(question, questionDTO);
//            questionDTO.setUser(user);
//            questionDTOList.add(questionDTO);
//        }
        return questionDTOList;
    }
}
