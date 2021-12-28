package com.study.free.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;
import com.study.servlet.Handler;

public class FreeView implements Handler {

	
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int boNo=Integer.parseInt(req.getParameter("boNo"));

		IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
		try{
			FreeBoardVO freeBoard = freeBoardService.getBoard(boNo);		
			req.setAttribute("freeBoard", freeBoard);
			freeBoardService.increaseHit(boNo);
		}catch(BizNotFoundException enf){
			// If error , move to message.jsp
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "글 찾기 실패", "해당 글이 없습니다.", "/free/freeList.wow", "목록");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";			
		} catch(BizNotEffectedException ene){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "조회수 증가 실패", "조회수 증가에 실패했습니다.", "/free/freeList.wow", "목록");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";			
		}
		
		return "free/freeView";
	}
}
