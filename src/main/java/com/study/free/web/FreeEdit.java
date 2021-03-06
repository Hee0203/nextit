package com.study.free.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.code.service.CommCodeServiceImpl;
import com.study.code.service.ICommCodeService;
import com.study.code.vo.CodeVO;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotFoundException;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardVO;
import com.study.servlet.Handler;

public class FreeEdit implements Handler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int boNo=Integer.parseInt(req.getParameter("boNo"));
		IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
		try{
			FreeBoardVO freeBoard = freeBoardService.getBoard(boNo);		
			req.setAttribute("freeBoard", freeBoard);
		}catch(BizNotFoundException enf){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "글 찾기 실패", "해당 글이 없습니다.", "/free/freeList.wow", "목록");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";		
		}
		
		ICommCodeService codeService = new CommCodeServiceImpl();
		List<CodeVO> cateList = codeService.getCodeListByParent("BC00");
		req.setAttribute("cateList", cateList);
		
		return "/free/freeEdit";
	}
}
