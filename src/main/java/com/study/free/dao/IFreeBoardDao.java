package com.study.free.dao;

import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

@Mapper
public interface IFreeBoardDao {
	
public List<FreeBoardVO> getBoardList(FreeBoardSearchVO searchVO) ;
public int getTotalRowCount(FreeBoardSearchVO searchVO);	

  public FreeBoardVO getBoard( int boNo);  
  public int increaseHit(int boNo); 
  public int updateBoard(FreeBoardVO board);
  public int deleteBoard(FreeBoardVO board);
  public int insertBoard(FreeBoardVO board);
  
  public List<FreeBoardVO> getBoardMap(Map<String,Object>map);
  public List<FreeBoardVO> getBoardAnotation(@Param(value="boNo") int boNo,
		  @Param(value="boTitle") String boTitle);

  
}
