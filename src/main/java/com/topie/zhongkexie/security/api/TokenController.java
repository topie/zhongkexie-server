package com.topie.zhongkexie.security.api;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.topie.zhongkexie.common.tools.cache.RedisCache;
import com.topie.zhongkexie.common.utils.HttpResponseUtil;
import com.topie.zhongkexie.common.utils.RequestUtil;
import com.topie.zhongkexie.common.utils.date.DateUtil;
import com.topie.zhongkexie.database.core.model.SysLog;
import com.topie.zhongkexie.security.constants.SecurityConstant;
import com.topie.zhongkexie.security.security.AuthenticationRequest;
import com.topie.zhongkexie.security.security.HttpAuthenticationDetails;
import com.topie.zhongkexie.security.service.UserService;
import com.topie.zhongkexie.security.utils.TokenUtils;
import com.topie.zhongkexie.system.service.LogService;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Value("${security.token.header}")
    private String tokenHeader;
    @Value("${security.login.vcode}")
    private String vcode;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;

    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public ResponseEntity<?> authenticationRequest(HttpServletRequest request,
            @RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
    	if(vcode==null||vcode.equals("1")){
	        if (StringUtils.isEmpty(authenticationRequest.getVcode()) || StringUtils
	                .isEmpty(authenticationRequest.getVkey())) {
	        	insertSysLog("登录失败","["+authenticationRequest.getUsername()+"]:请输入验证码！",authenticationRequest.getUsername(),RequestUtil.getIpAddress(request));
	            return ResponseEntity.ok(HttpResponseUtil.error("请输入验证码"));
	        }
	        if (StringUtils.isNotEmpty((String) redisCache.get(authenticationRequest.getVkey()))) {
	            if (!((String) redisCache.get(authenticationRequest.getVkey())).equals(authenticationRequest.getVcode())) {
	            	insertSysLog("登录失败","["+authenticationRequest.getUsername()+"]:验证码不正确！",authenticationRequest.getUsername(),RequestUtil.getIpAddress(request));
	                return ResponseEntity.ok(HttpResponseUtil.error("验证码不正确"));
	            }
	        } else {
	        	insertSysLog("登录失败","["+authenticationRequest.getUsername()+"]:验证码已过期！",authenticationRequest.getUsername(),RequestUtil.getIpAddress(request));
	            return ResponseEntity.ok(HttpResponseUtil.error("验证码已过期"));
	        }
    	}
    	redisCache.del(authenticationRequest.getVkey());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword());
        usernamePasswordAuthenticationToken.setDetails(new HttpAuthenticationDetails(request));

        Authentication authentication = null;
        try {
            authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            if (authentication == null) {
            	insertSysLog("登录失败","["+authenticationRequest.getUsername()+"]:未检测到验证信息！",authenticationRequest.getUsername(),RequestUtil.getIpAddress(request));
                return ResponseEntity.ok(HttpResponseUtil.error("未检测到验证信息"));
            }
        } catch (InternalAuthenticationServiceException failed) {
            logger.error("An internal error occurred while trying to authenticate the user.", failed);
            insertSysLog("登录失败","["+authenticationRequest.getUsername()+"]:"+failed.getMessage()+"！",authenticationRequest.getUsername(),RequestUtil.getIpAddress(request));
            return ResponseEntity.ok(HttpResponseUtil.error(failed.getMessage()));
        } catch (AuthenticationException failed) {
        	insertSysLog("登录失败","["+authenticationRequest.getUsername()+"]:"+failed.getMessage()+"！",authenticationRequest.getUsername(),RequestUtil.getIpAddress(request));
            return ResponseEntity.ok(HttpResponseUtil.error(failed.getMessage()));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) redisCache
                .get(SecurityConstant.USER_CACHE_PREFIX + authenticationRequest.getUsername());
        if (userDetails == null) {
            userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            redisCache.set(SecurityConstant.USER_CACHE_PREFIX + authenticationRequest.getUsername(), userDetails);
        }
        String token = this.tokenUtils.generateToken(userDetails);
        userService.updateLastLoginInfoByUserName(authenticationRequest.getUsername(), new Date(),
                request.getRemoteAddr());
        insertSysLog("登录成功","["+authenticationRequest.getUsername()+"]:登录成功！",authenticationRequest.getUsername(),RequestUtil.getIpAddress(request));
        return ResponseEntity.ok(HttpResponseUtil.success(token));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(HttpServletRequest request, AuthenticationRequest authenticationRequest)
            throws AuthenticationException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword());
        usernamePasswordAuthenticationToken.setDetails(new HttpAuthenticationDetails(request));
        Authentication authentication = null;
        try {
            authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            if (authentication == null) {
            	insertSysLog("登录失败","["+authenticationRequest.getUsername()+"]:未检测到验证信息！",authenticationRequest.getUsername(),RequestUtil.getIpAddress(request));
                return ResponseEntity.ok(HttpResponseUtil.error("未检测到验证信息"));
            }
        } catch (InternalAuthenticationServiceException failed) {
            logger.error("An internal error occurred while trying to authenticate the user.", failed);
            insertSysLog("登录失败","["+authenticationRequest.getUsername()+"]:"+failed.getMessage()+"！",authenticationRequest.getUsername(),RequestUtil.getIpAddress(request));
            return ResponseEntity.ok(HttpResponseUtil.error(failed.getMessage()));
        } catch (AuthenticationException failed) {
        	insertSysLog("登录失败","["+authenticationRequest.getUsername()+"]:"+failed.getMessage()+"！",authenticationRequest.getUsername(),RequestUtil.getIpAddress(request));
            return ResponseEntity.ok(HttpResponseUtil.error(failed.getMessage()));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) redisCache
                .get(SecurityConstant.USER_CACHE_PREFIX + authenticationRequest.getUsername());
        if (userDetails == null) {
            userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            redisCache.set(SecurityConstant.USER_CACHE_PREFIX + authenticationRequest.getUsername(), userDetails);
        }
        String token = this.tokenUtils.generateToken(userDetails);
        userService.updateLastLoginInfoByUserName(authenticationRequest.getUsername(), new Date(),
                request.getRemoteAddr());
        insertSysLog("登录成功","["+authenticationRequest.getUsername()+"]:登录成功！",authenticationRequest.getUsername(),RequestUtil.getIpAddress(request));
        return ResponseEntity.ok(HttpResponseUtil.success(token));
    }
    
    private void insertSysLog(String title,String content,String user,String ip) {
   	 SysLog log = new SysLog();  
   	 //获取登录管理员 
        log.setCuser(user);//设置管理员id  
        log.setCdate(DateUtil.DateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
        log.setContent(content);//操作内容 
        log.setTitle(title);//操作  
        log.setCtype(LogService.DENGLU);
        log.setIp(ip);
        logService.save(log);//添加日志  
		
	}

}
