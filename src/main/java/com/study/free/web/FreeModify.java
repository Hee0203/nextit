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

public class FreeModify implements Handler {
	
	

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		FreeBoardVO freeBoard = new FreeBoardVO();
		BeanUtils.populate(freeBoard, req.getParameterMap());
		req.setAttribute("freeBoard", freeBoard);
		
		IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
		try{
			freeBoardService.modifyBoard(freeBoard);
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(true, "글 수정 성공", "수정하였습니다.", "/free/freeList.wow", "목록");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizNotFoundException enf){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "글 찾기 실패", "해당 글이 없습니다.", "/free/freeList.wow", "목록");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizPasswordNotMatchedException epm){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "비밀번호 틀림 / 수정 실패", "비밀번호가 틀립니다.", "/free/freeList.wow", "목록");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		} catch(BizNotEffectedException ene){
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.messageSetting(false, "조회수 증가 실패", "조회수 증가에 실패했습니다.", "/free/freeList.wow", "목록");
			req.setAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		}
		
	}	
}
