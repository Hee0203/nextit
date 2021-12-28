package com.study.common.util;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtils {
	private Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
	
	public CookieUtils(HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
		
		/*
		 * if(cookies != null) { for(int i = 0; i < cookies.length; i++) {
		 * cookieMap.put(cookies[i].getName(), cookies[i]); } }
		 */
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		
	} // CookieUtils 만들 때 request에 있는 쿠키들을 cookieMap에 저장
	// Map<cookieName(String), Cookie>
	
	public boolean exists(String cookieName) {
		return cookieMap.get(cookieName)!= null;
	}
	
	public Cookie getCookie(String cookieName) {
		return cookieMap.get(cookieName);
	}
	
	public String getValue(String cookieName) {
		return cookieMap.get(cookieName).getValue();
	}

	public static Cookie createCookie(String name, String value) {
		return createCookie(name, value, -1, "","");
	}
	
	public static Cookie createCookie(String name, String value, int maxAge) {
//		Cookie cookie = new Cookie(name, value);
//		cookie.setMaxAge(maxAge);
		return createCookie(name, value, maxAge, "", "");
	}
	
	public static Cookie createCookie(String name, String value, String path) {
		return createCookie(name, value, -1, path, "");
	}
	
	public static Cookie createCookie(String name, String value, int maxAge, String path) {
		return createCookie(name, value, maxAge, path,"");
	}	
	
	public static Cookie createCookie(String name, String value, int MaxAge, String path, String domain) {
		Cookie cookie = new Cookie(name, value);
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setMaxAge(MaxAge);
		return cookie;
	}	
	
}
