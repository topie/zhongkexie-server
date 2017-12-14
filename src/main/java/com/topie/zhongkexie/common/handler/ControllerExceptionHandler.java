package com.topie.zhongkexie.common.handler;

import com.topie.zhongkexie.common.exception.BusinessException;
import com.topie.zhongkexie.common.utils.ResponseUtil;
import com.topie.zhongkexie.common.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/6 说明：
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleAllException(Exception e) {
        String error = e.getMessage();
        if (e instanceof BusinessException) {
            error = e.getMessage();
            logger.error(error);
        } else if (e instanceof BindException) {
            error = ((BindException) e).getAllErrors().get(0).getDefaultMessage();
            logger.error(error);
        } else {
            e.printStackTrace();
        }
        return ResponseUtil.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, error);
    }
}
