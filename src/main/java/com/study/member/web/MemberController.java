package com.study.member.web;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.code.service.ICommCodeService;
import com.study.code.vo.CodeVO;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizDuplicateKeyException;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.member.service.IMemberService;
import com.study.member.vo.MemberSearchVO;
import com.study.member.vo.MemberVO;

@Controller
public class MemberController {
	
	@Inject
	IMemberService memberService;
	
	@Autowired
	ICommCodeService codeService;
	
	@ModelAttribute("jobList")
	public List<CodeVO> jobList(){
		return codeService.getCodeListByParent("JB00");
	}
	
	@ModelAttribute("hobbyList")
	public List<CodeVO> hobbyList(){
		return codeService.getCodeListByParent("HB00");
	}
	
	@RequestMapping("/member/memberList.wow")
	public String memberList(Model model, @ModelAttribute("searchVO") MemberSearchVO searchVO) {
		List<MemberVO> memberList = memberService.getMemberList(searchVO);
		model.addAttribute("memberList", memberList);
		return "member/memberList";
	}
	
	@RequestMapping("/member/memberView.wow")
	public String memberView(Model model, @RequestParam(required=true)String memId) {
		try{
			MemberVO member = memberService.getMember(memId);
			model.addAttribute("member", member);
		}catch(BizNotFoundException enf){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "회원 찾기 실패", "해당 회원이 없습니다.", "/member/memberList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		}
		return "member/memberView";
	}
	
	@RequestMapping("/member/memberEdit.wow")
	public String memberEdit(Model model, @RequestParam(required=true)String memId) {
		try{
			MemberVO member = memberService.getMember(memId);
			model.addAttribute("member", member);		
		}catch(BizNotFoundException enf){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "회원 찾기 실패", "해당 회원이 없습니다.", "/member/memberList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		}
		return "member/memberEdit";
	}
	@PostMapping("/member/memberModify.wow")
	public String memberModify(Model model, MemberVO member) {
		try{
			memberService.modifyMember(member);
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(true, "회원 수정 성공", "수정되었습니다.", "/member/memberList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizNotFoundException enf){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "회원 찾기 실패", "해당 회원이 없습니다.", "/member/memberList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizNotEffectedException ene){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "회원 수정 실패", "수정에 실패하였습니다.", "/member/memberList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";		
		}
	}
	
	@RequestMapping("/member/memberForm.wow")
	public String memberForm() {
		return "member/memberForm";
	}
	
	@PostMapping("/member/memberRegist.wow")
	public String memberRegist(Model model, MemberVO member) {
		try{
			memberService.registMember(member);
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(true, "회원 등록 성공", "등록되었습니다.", "/member/memberList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizNotEffectedException ene){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "회원 등록 실패", "등록에 실패하였습니다.", "/member/memberList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";	
		} catch(BizDuplicateKeyException edk){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "아이디가 존재함", "아이디가 이미 존재합니다.", "/member/memberList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";	
		}
	}
	@RequestMapping("/member/memberDelete.wow")
	public String memberDelete(Model model, MemberVO member) {
		try{
			memberService.removeMember(member);
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(true, "회원 삭제 성공", "삭제되었습니다.", "/member/memberList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizNotFoundException enf){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "회원 찾기 실패", "해당 회원이 없습니다.", "/member/memberList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizNotEffectedException ene){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "회원 삭제 실패", "삭제에 실패하였습니다.", "/member/memberList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";		
		}
	}
	
}
