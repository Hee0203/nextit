package com.study.mypage.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotFoundException;
import com.study.login.vo.UserVO;
import com.study.member.service.IMemberService;
import com.study.member.service.MemberServiceImpl;
import com.study.member.vo.MemberVO;
import com.study.servlet.Handler;

public class Info implements Handler{
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// If login, user can see user's info
		
		// 로그인 여부 확인 USER_INFO에 담긴 userId로 memberTable 조회
		HttpSession session = req.getSession();
		UserVO user = (UserVO)session.getAttribute("USER_INFO");

		
		// 로그인 된 상태기 때문에 user로 member테이블 조회 후 개인정보화면으로 이동
		IMemberService memberService = new MemberServiceImpl();
		try {
			MemberVO member = memberService.getMember(user.getUserId());
			req.setAttribute("member", member);
			return "mypage/info";
			
		} catch(BizNotFoundException e) {
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "회원 없음", "회원이 존재하지 않습니다.","/","메인");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		}
		
		
		
	}
}
