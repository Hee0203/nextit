package com.study.member.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import com.study.exception.BizDuplicateKeyException;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.member.dao.IMemberDao;	
import com.study.member.service.IMemberService;
import com.study.member.vo.MemberSearchVO;
import com.study.member.vo.MemberVO;

@Service
public class MemberServiceImpl implements IMemberService{
	
	@Inject
	IMemberDao memberDao;
	
	@Override
	public List<MemberVO> getMemberList(MemberSearchVO searchVO) {
		int totalRowCount = memberDao.getTotalRowCount(searchVO);
		searchVO.setTotalRowCount(totalRowCount);
		searchVO.pageSetting();
		
		// Dao에서 받은 list데이터 그대로 return
		return memberDao.getMemberList(searchVO);			
	}

	@Override
	public MemberVO getMember(String memId) throws BizNotFoundException {

		MemberVO member = memberDao.getMember(memId);
		if(member == null) {
			throw new BizNotFoundException();
		}
		return member;
	}

	@Override
	public void modifyMember(MemberVO member) throws BizNotEffectedException, BizNotFoundException {
		MemberVO vo = memberDao.getMember(member.getMemId());
		if(vo == null) {
			throw new BizNotFoundException();
		} else {
			int cnt = memberDao.updateMember(member);
			if(cnt == 0) throw new BizNotEffectedException();
		}
	}

	@Override
	public void removeMember(MemberVO member) throws BizNotEffectedException, BizNotFoundException {
		MemberVO vo = memberDao.getMember(member.getMemId());
		if(vo == null) {
			throw new BizNotFoundException();
		} else {
			int cnt = memberDao.deleteMember(member);
			if(cnt == 0) throw new BizNotEffectedException();
		}
	}

	@Override
	public void registMember(MemberVO member) throws BizNotEffectedException, BizDuplicateKeyException {
		MemberVO vo = memberDao.getMember(member.getMemId());
		if(vo != null) {
			throw new BizDuplicateKeyException();
		} else {
			int cnt = memberDao.insertMember(member);
			if(cnt == 0) throw new BizNotEffectedException();
		}
	}
	
}
