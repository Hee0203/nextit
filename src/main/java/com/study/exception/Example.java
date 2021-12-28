package com.study.exception;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.study.free.vo.FreeBoardVO;

public class Example {
	
	public static void main(String[] args) {
		// 1. 예외 블랙홀 
		try {
			// 예외발생코드
			String a = null;
			a.substring(0);			
		} catch (Exception e) {
			
		}
		
		// 에러 관전 
		try {
			// 예외발생코드
			String a = null;
			a.substring(0);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 적절한 처리
		try {
			// 예외발생코드
			String a = null;
			a.substring(0);			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("a == null / a에 객체 전달하기");
			String a = "적절히 생성된 객체";
			a.substring(0);
		}
		
		// 에러변환
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//con = DriverManager.getConnection(("연결");
			pstmt = con.prepareStatement(" SELECT BO_NO, BO_TITLE FROM MEMBER");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				rs.getInt("BO_NO");
				rs.getString("BO_TITLE");
			}
			
			
		} catch (SQLException e) {
			// e.printStackTrace(); 하고 뒷 내용이 실행되는 것이 아니고 프로그램 자체가 멈췄으면 한다
			// 에러를 던져서 프로그램을 중지 시키도록 한다
			// SQLException에서 던질 에러로 DaoException을 만들었다
			
			throw new DaoException("DAO단 에러", e);
		}
		
		// 에러 변환 2
//		try {
//			// DB조회
//			// 입력한 비밀번호랑 DB에 있는 비밀번호가 다를 경우
//			if(free.getBoPass.equals(vo.getBoPass())){
//				// 코드상 에러가 아니고 개발자 생각에 문제가 있는 경우 -> service단
//				// 에러를 임시로 발생시킬 수 있다
//				// 그 때 던지는exception 으로 BizException을 만들었다
//				// throw new BizException();
//				throw new BizPasswordNotMatchedException();
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		
		
	}
	
}
