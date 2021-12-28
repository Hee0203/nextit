package com.study.free.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.free.dao.IFreeBoardDao;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

@Service
public class FreeBoardServiceImpl implements IFreeBoardService {

	@Inject
	IFreeBoardDao freeBoardDao;

	@Override
	public List<FreeBoardVO> getBoardList(FreeBoardSearchVO searchVO) {
		int totalRowCount = freeBoardDao.getTotalRowCount(searchVO);
		searchVO.setTotalRowCount(totalRowCount);
		searchVO.pageSetting();
		
		return freeBoardDao.getBoardList(searchVO);
	}

	@Override
	public FreeBoardVO getBoard(int boNo) throws BizNotFoundException {
		FreeBoardVO freeBoard = freeBoardDao.getBoard(boNo);
		// freeBoard가 null ( 글 번호 사용자가 url에 이상하게 입력한 경우
		if(freeBoard == null) {
			throw new BizNotFoundException();
		}
		return freeBoard;
	}

	@Override
	public void increaseHit(int boNo) throws BizNotEffectedException {
		int cnt = freeBoardDao.increaseHit(boNo); // update된 행 수가 return 됨
		
		if(cnt == 0) { // 업데이트가 제대로 안됐을 때 (일어날 수 없는 상황이긴 함 )
			throw new BizNotEffectedException();
		}
	}

	@Override
	public void modifyBoard(FreeBoardVO freeBoard)
			throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {
		FreeBoardVO vo = freeBoardDao.getBoard(freeBoard.getBoNo());
		// vo는 DB에 있는 데이터
		if(vo == null) throw new BizNotFoundException();
		
		if(freeBoard.getBoPass().equals(vo.getBoPass())) {
			// 사용자가 입력한 비밀번호랑 해당 글이 현재 DB에 있는 비밀번호와 같다.
			// ==> 글 등록 당시의 비밀번호를 잘 입력했다 == 글쓴이 본인이다 == 수정 가능
			int cnt = freeBoardDao.updateBoard(freeBoard);
			if(cnt == 0) throw new BizNotEffectedException();
		} else {
			// 비밀번호가 다른 경우
			throw new BizPasswordNotMatchedException();
		}
	}

	@Override
	public void removeBoard(FreeBoardVO freeBoard)
			throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {
		FreeBoardVO vo = freeBoardDao.getBoard(freeBoard.getBoNo());
		// vo는 DB에 있는 데이터
		if(vo == null) throw new BizNotFoundException();
		
		if(freeBoard.getBoPass().equals(vo.getBoPass())) {
			// 사용자가 입력한 비밀번호랑 해당 글이 현재 DB에 있는 비밀번호와 같다.
			// ==> 글 등록 당시의 비밀번호를 잘 입력했다 == 글쓴이 본인이다 == 수정 가능
			int cnt = freeBoardDao.deleteBoard(freeBoard);
			if(cnt == 0) throw new BizNotEffectedException();
		} else {
			// 비밀번호가 다른 경우
			throw new BizPasswordNotMatchedException();
		}
	}

	@Override
	public void registBoard(FreeBoardVO freeBoard) throws BizNotEffectedException {
		int cnt = freeBoardDao.insertBoard(freeBoard);
		if(cnt == 0) throw new BizNotEffectedException();
	}

}
