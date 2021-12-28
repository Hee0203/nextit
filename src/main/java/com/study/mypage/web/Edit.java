package com.study.mypage.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.code.service.CommCodeServiceImpl;
import com.study.code.service.ICommCodeService;
import com.study.code.vo.CodeVO;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotFoundException;
import com.study.login.vo.UserVO;
import com.study.member.service.IMemberService;
import com.study.member.service.MemberServiceImpl;
import com.study.member.vo.MemberVO;
import com.study.servlet.Handler;

public class Edit implements Handler{
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();
		UserVO user = (UserVO)session.getAttribute("USER_INFO");

		
		IMemberService memberService = new MemberServiceImpl();
		try {
			MemberVO member = memberService.getMember(user.getUserId());
			req.setAttribute("member", member);
			ICommCodeService codeService = new CommCodeServiceImpl();
			List<CodeVO> jobList=  codeService.getCodeListByParent("JB00");
			List<CodeVO> hobbyList=  codeService.getCodeListByParent("HB00");
			req.setAttribute("jobList", jobList);
			req.setAttribute("hobbyList", hobbyList);
			return "mypage/edit";
			
		} catch(BizNotFoundException e) {
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "회원 없음", "회원이 존재하지 않습니다.","/","메인");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		}
		
		
		
	}
}
