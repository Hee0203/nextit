package com.study.member.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.study.code.service.CommCodeServiceImpl;
import com.study.code.service.ICommCodeService;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.member.service.IMemberService;
import com.study.member.service.MemberServiceImpl;
import com.study.member.vo.MemberVO;
import com.study.servlet.Handler;

public class MemberModify implements Handler {

	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		MemberVO member = new MemberVO();
		BeanUtils.populate(member, req.getParameterMap());
		req.setAttribute("member", member);
		
		IMemberService memberService = new MemberServiceImpl();
		
		try{
			memberService.modifyMember(member);
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(true, "회원 수정 성공", "수정되었습니다.", "/member/memberList.wow", "목록");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizNotFoundException enf){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "회원 찾기 실패", "해당 회원이 없습니다.", "/member/memberList.wow", "목록");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizNotEffectedException ene){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "회원 수정 실패", "수정에 실패하였습니다.", "/member/memberList.wow", "목록");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";		
		}
	}
}
