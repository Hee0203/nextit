package com.study.mypage.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.login.vo.UserVO;
import com.study.member.service.IMemberService;
import com.study.member.service.MemberServiceImpl;
import com.study.member.vo.MemberVO;
import com.study.servlet.Handler;

public class Modify implements Handler{
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();
		UserVO user = (UserVO)session.getAttribute("USER_INFO");

		
		MemberVO member = new MemberVO();
		BeanUtils.populate(member, req.getParameterMap());
		req.setAttribute("member", member);
		
		IMemberService memberService = new MemberServiceImpl();
		
		try {
			memberService.modifyMember(member);
			user.setUserName(member.getMemName());
			user.setUserPass(member.getMemPass());
			session.setAttribute("USER_INFO", user);
			return "redirect:"+req.getContextPath();
		} catch(BizNotFoundException e) {
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "내 정보 없음", "내 정보가 존재하지 않습니다.","/","홈");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizNotEffectedException e) {
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "수정 실패", "내 정보 수정에 실패했습니다.","/","홈");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		}

		
	}
}
