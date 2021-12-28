package com.study.login.service;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import com.study.login.vo.UserVO;
import com.study.member.dao.IMemberDao;
import com.study.member.vo.MemberVO;

@Service
public class LoginServiceImpl implements ILoginService{

	@Inject
	IMemberDao memberDao;
	
	@Override
	public UserVO getUser(String id) {
		MemberVO member = memberDao.getMember(id);
		if(member == null) {
			return null;
		} else {
			UserVO user = new UserVO();
			user.setUserId(member.getMemId());
			user.setUserName(member.getMemName());
			user.setUserPass(member.getMemPass());
			user.setUserRole("member");
			return user;
		}
			
	}
	
}
