package com.xia.community.advice;

import com.xia.community.exception.QuestionException;
import com.xia.community.exception.UserException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author thisXjj
 * @date 2020/4/7 6:00 下午
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model) {
        if (e instanceof QuestionException || e instanceof UserException) {
            model.addAttribute("message", e.getMessage());
        } else if (e instanceof MethodArgumentTypeMismatchException){
            model.addAttribute("message", "您查询的参数格式不正确");
        }
        return new ModelAndView("error");
    }
}
