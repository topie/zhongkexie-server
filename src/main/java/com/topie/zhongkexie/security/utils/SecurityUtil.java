package com.topie.zhongkexie.security.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.topie.zhongkexie.security.security.SecurityUser;

/**
 * Created by cgj on 2015/10/26.
 */
public class SecurityUtil {

	public static String getCurrentUserName() {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userName = null;
		if (principal instanceof SecurityUser)
			userName = ((SecurityUser) principal).getUsername();
		return userName;
	}

	public static Integer getCurrentUserId() {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Integer userId = null;
		if (principal instanceof SecurityUser)
			userId = ((SecurityUser) principal).getId();
		return userId;
	}

	public static SecurityUser getCurrentSecurityUser() {
		try{
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof SecurityUser)
			return (SecurityUser) principal;
		}catch(Exception e){e.printStackTrace();}
		return null;
	}

	public static String encodeString(String character) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(character);
	}

	public static boolean matchString(String character, String encodedCharacter) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(character, encodedCharacter);
	}

	public static String getCurrentIP() {
//		Authentication auth = SecurityContextHolder.getContext()
//				.getAuthentication();
		/*HttpAuthenticationDetails wauth = (HttpAuthenticationDetails) auth
				.getDetails();
		String ip = wauth.getIp();*/
		try{
		return getCurrentSecurityUser().getIp();
		}catch(Exception e){}
		return "-";
	}

	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
