package com.study.login.web;

import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.common.util.CookieUtils;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.login.service.ILoginService;
import com.study.login.service.LoginServiceImpl;
import com.study.login.vo.UserVO;
import com.study.servlet.Handler;

public class Login implements Handler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// url 창에 login/login.wow를 입력한 것이랑 
		// 로그인 화면에서 버튼 눌러서 login/login.wow로 이동한 것이랑 다르다
		// == get 방식인지 post 방식인지 다르다  
		String method = req.getMethod();
		if(method.equals("GET")){ // login.wow를 브라우저에 직접 입력
			return "login/login";
		} else if(method.equals("POST")) {
			// 사용자가 id, pass입력해서 로그인 버튼을 누름
			// loginCheck.jsp 내용이 들어감
			String id = req.getParameter("userId");
			String pw = req.getParameter("userPass");
			String save_id = req.getParameter("rememberMe");
			if (save_id == null) {
				CookieUtils cookieUtils = new CookieUtils(req);
				if (cookieUtils.exists("SAVE_ID")) {
					Cookie cookie = CookieUtils.createCookie("SAVE_ID", id, 0, "/");
					resp.addCookie(cookie);
				}
				save_id = "";
			}

			if ((id == null || id.isEmpty()) || (pw == null || pw.isEmpty())) {
				// 직접 redirect보다는 DispatcherServlet한테 redirect 하는 것을 알려주는 것이 좋다
				return "redirect:"+req.getContextPath()+"/login/login.wow?msg="+ URLEncoder.encode("입력되지 않았습니다.", "utf-8");
			} else {
				// DB에 있는 memberTable을 조회해서 로그인 가능여부를 판단
				ILoginService loginService = new LoginServiceImpl();
				UserVO user = loginService.getUser(id);
				req.setAttribute("user", user);
				

				if (user == null) {
					return "redirect:"+req.getContextPath()+"/login/login.wow?msg="+ URLEncoder.encode("아이디 또는 비밀번호 확인", "utf-8");
				} else { //id맞았을때 
					if (user.getUserPass().equals(pw)) {//다 맞는경우
						if (save_id.equals("Y")) {
							resp.addCookie(CookieUtils.createCookie("SAVE_ID", id, 3600 * 24 * 7, "/"));
						}
						HttpSession session = req.getSession();
						session.setAttribute("USER_INFO", user);
						return "redirect:"+req.getContextPath(); // redirect:/study3
					} else {//  비번만 틀린경우
						return "redirect:"+req.getContextPath()+"/login/login.wow?msg="+ URLEncoder.encode("아이디 또는 비밀번호 확인", "utf-8");					
					}

				}
			}
			
		} else { // Not 'GET', Not 'POST'
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "허용되지 않은 메소드입니다."); // 405
			return "";
		}
		
	}
}
