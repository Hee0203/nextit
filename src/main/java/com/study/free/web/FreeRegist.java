package com.study.free.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotEffectedException;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardVO;
import com.study.servlet.Handler;

public class FreeRegist implements Handler {
	
	

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		FreeBoardVO freeBoard = new FreeBoardVO();
		BeanUtils.populate(freeBoard, req.getParameterMap());
		req.setAttribute("freeBoard", freeBoard);
		
		IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
		try{
			freeBoardService.registBoard(freeBoard);
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(true, "글 등록 성공", "등록 되었습니다.", "/free/freeList.wow", "목록");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizNotEffectedException ene){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "실패", "걍 실패했습니다.", "/free/freeList.wow", "목록");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";	
		}
		
	}

}
