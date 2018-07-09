package com.topie.zhongkexie.security.security;

import javax.servlet.http.HttpServletRequest;

import com.topie.zhongkexie.common.utils.RequestUtil;

public class HttpAuthenticationDetails {
	private String ip;
    public HttpAuthenticationDetails() {

    }

    public HttpAuthenticationDetails(HttpServletRequest req) {
       ip = RequestUtil.getIpAddress(req);
    }

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
    
}
