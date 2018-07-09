package com.topie.zhongkexie.security.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.topie.zhongkexie.common.utils.HttpResponseUtil;
import com.topie.zhongkexie.common.utils.RequestUtil;
import com.topie.zhongkexie.common.utils.date.DateUtil;
import com.topie.zhongkexie.database.core.model.SysLog;
import com.topie.zhongkexie.security.utils.SecurityUtil;
import com.topie.zhongkexie.system.service.LogService;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/9 说明：
 */
public class HttpAuthenticationFailureHandler
        implements AuthenticationFailureHandler {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private LogService logService;

    public HttpAuthenticationFailureHandler() {
    }

    /**
     * Performs the redirect or forward to the {@code defaultFailureUrl} if set, otherwise returns a
     * 401 error code.
     * <p/>
     * If redirecting or forwarding, {@code saveException} will be called to cache the exception for
     * use in the target view.
     */
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        try {
            writeJson(response, exception);
        } finally {
            logger
                    .info("登录系统失败;原因:" + exception.getMessage() + ";日志类型:{};用户:{};登录IP:{};", "登录", "-",
                            RequestUtil.getIpAddress(request));
            
            insertSysLog("登录系统失败","[用户:"+request.getParameter("j_username")+"]登录系统失败;原因:" + exception.getMessage(),RequestUtil.getIpAddress(request));
        }
    }
    private void insertSysLog(String title,String content,String ip) {
   	 SysLog log = new SysLog();  
   	 //获取登录管理员 
   	 SecurityUser user = SecurityUtil.getCurrentSecurityUser(); 
   	 if(user==null){
   		 return;
   	 }
        log.setCuser(user.getUsername()+"["+user.getId()+"]");//设置管理员id  
        log.setCdate(DateUtil.DateToString(new Date(),"YYYY-MM-dd HH:mm:ss"));//操作时间  
        log.setContent(content);//操作内容 
        log.setTitle(title);//操作  
        log.setCtype(LogService.DENGLU);
        log.setIp(ip);
        logService.save(log);//添加日志  
		
	}

    private void writeJson(HttpServletResponse response, AuthenticationException exception)
            throws IOException {
        HttpResponseUtil.writeJson(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception.getMessage());
    }

}
