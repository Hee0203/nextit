package com.study.free.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardVO;
import com.study.servlet.Handler;


public class FreeDelete implements  Handler{
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		FreeBoardVO freeBoard = new FreeBoardVO();
		BeanUtils.populate(freeBoard, req.getParameterMap());
		req.setAttribute("freeBoard", freeBoard);
		
		IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
		
		try{
			freeBoardService.removeBoard(freeBoard);
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(true, "글 삭제 성공", "삭제되었습니다.", "/free/freeList.wow", "목록");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizNotFoundException enf){
			// 글 번호가 없을 때
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "글 찾기 실패", "해당 글이 없습니다.", "/free/freeList.wow", "목록");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";			
		} catch(BizPasswordNotMatchedException epm){
			// 글 등록 당시 비밀번호와 입력한 비밀번호가 다를 때
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "비밀번호 틀림 / 수정 실패", "비밀번호가 틀립니다.", "/free/freeList.wow", "목록");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizNotEffectedException ene){
			// 이유 모르게 수정 실패
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "실패", "걍 실패했습니다.", "/free/freeList.wow", "목록");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";	
		}

	}

}
