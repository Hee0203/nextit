package com.study.free.web;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.study.code.service.CommCodeServiceImpl;
import com.study.code.service.ICommCodeService;
import com.study.code.vo.CodeVO;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

@Controller
public class FreeController {
	
	// 데이터 저장이 아닌 단순 기능이면 객체를 1개만 만드는 것이 좋다.
	// Singleton pattern 말고 spring이 객체 1개 만들면 spring이 만든 객체만 계속 사용 (bean)
	
	@Inject
	IFreeBoardService freeBoardService;
	
	@Autowired // inject랑 같다
	ICommCodeService codeService;
	
	// edit, form에서는 categoryList를 model에 담아서 사용
	// 그때 model에 담지 않고 무조건 model categoryList를 담아도 문제 없다
	
	@ModelAttribute("cateList")
	public List<CodeVO> cateList(){
		return codeService.getCodeListByParent("BC00");
	}
		
	@RequestMapping("/free/freeList.wow")
	public String freeList(Model model, @ModelAttribute("searchVO") FreeBoardSearchVO searchVO) {		
		// 파라미터들이 searchVO의 필드면 알아서 setting + model.addAttribute("searchVO")
		List<FreeBoardVO> freeBoardList = freeBoardService.getBoardList(searchVO);
		model.addAttribute("freeBoardList", freeBoardList);	
		//cateList();

		return "free/freeList";
		
	}
	
	@RequestMapping("/free/freeView.wow")
	public String freeView(Model model, @RequestParam(required = true)int boNo) {
		try{
			FreeBoardVO freeBoard = freeBoardService.getBoard(boNo);		
			model.addAttribute("freeBoard", freeBoard);
			freeBoardService.increaseHit(boNo);
		}catch(BizNotFoundException enf){
			// If error , move to message.jsp
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "글 찾기 실패", "해당 글이 없습니다.", "/free/freeList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";			
		} catch(BizNotEffectedException ene){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "조회수 증가 실패", "조회수 증가에 실패했습니다.", "/free/freeList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";			
		}
		
		return "free/freeView";
	}
	
	@RequestMapping(value="/free/freeEdit.wow", params={"boNo"})
	public String freeEdit(Model model, int boNo) {
		
		try{
			FreeBoardVO freeBoard = freeBoardService.getBoard(boNo);		
			model.addAttribute("freeBoard", freeBoard);
		}catch(BizNotFoundException enf){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "글 찾기 실패", "해당 글이 없습니다.", "/free/freeList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";		
		}
		//cateList();
		return "/free/freeEdit";
	}
	
	//@RequestMapping(value="/free/freeModify.wow", method = RequestMethod.POST)
	@PostMapping("/free/freeModify.wow")
	public String freeModify(Model model, FreeBoardVO freeBoard) {		
		try{
			freeBoardService.modifyBoard(freeBoard);
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(true, "글 수정 성공", "수정하였습니다.", "/free/freeList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizNotFoundException enf){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "글 찾기 실패", "해당 글이 없습니다.", "/free/freeList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizPasswordNotMatchedException epm){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "비밀번호 틀림 / 수정 실패", "비밀번호가 틀립니다.", "/free/freeList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizNotEffectedException ene){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "수정 실패", "수정 실패했습니다.", "/free/freeList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		}
	}
	
	@RequestMapping("/free/freeForm.wow")
	public String freeForm() {
		//cateList();
		return "free/freeForm";
	}
	
	// Return type is String, . . . etc
	
//	@RequestMapping("/free/freeForm.wow")
//	public ModelAndView freeForm2(Model model) {
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("free/freeForm");
//		mav.addObject("data","data");
//		return null;
//	}
	
	@PostMapping("/free/freeRegist.wow")
	public String freeRegist(Model model, FreeBoardVO freeBoard) {
		try{
			freeBoardService.registBoard(freeBoard);
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(true, "글 등록 성공", "등록 되었습니다.", "/free/freeList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizNotEffectedException ene){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "실패", "등록 실패했습니다.", "/free/freeList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";	
		}
	}
	
	@PostMapping("/free/freeDelete.wow")
	public String freeDelete(Model model, FreeBoardVO freeBoard) {
		try{
			freeBoardService.removeBoard(freeBoard);
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(true, "글 삭제 성공", "삭제되었습니다.", "/free/freeList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizNotFoundException enf){
			// 글 번호가 없을 때
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "글 찾기 실패", "해당 글이 없습니다.", "/free/freeList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";			
		} catch(BizPasswordNotMatchedException epm){
			// 글 등록 당시 비밀번호와 입력한 비밀번호가 다를 때
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "비밀번호 틀림 / 수정 실패", "비밀번호가 틀립니다.", "/free/freeList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizNotEffectedException ene){
			// 이유 모르게 수정 실패
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "실패", "삭제 실패했습니다.", "/free/freeList.wow", "목록");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";	
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
