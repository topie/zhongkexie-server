package com.topie.zhongkexie.security.security;

import com.topie.zhongkexie.common.utils.HttpResponseUtil;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        HttpResponseUtil.error(httpServletResponse, HttpServletResponse.SC_UNAUTHORIZED, "未授权访问");
    }

}
