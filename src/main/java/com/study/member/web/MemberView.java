package com.study.member.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.code.service.CommCodeServiceImpl;
import com.study.code.service.ICommCodeService;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotFoundException;
import com.study.member.service.IMemberService;
import com.study.member.service.MemberServiceImpl;
import com.study.member.vo.MemberVO;
import com.study.servlet.Handler;


public class MemberView implements Handler {

	
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String memId=req.getParameter("memId");
		
		IMemberService memberService = new MemberServiceImpl();
		try{
			MemberVO member = memberService.getMember(memId);
			req.setAttribute("member", member);
		}catch(BizNotFoundException enf){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "회원 찾기 실패", "해당 회원이 없습니다.", "/member/memberList.wow", "목록");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		}
		
		return "member/memberView";
	}
}
