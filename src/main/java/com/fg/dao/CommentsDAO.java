package com.fg.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.fg.vo.CommentsVO;

public class CommentsDAO {
	SqlSession sqlSession;

	public void setSqlSession( SqlSession sqlSession ) {
		this.sqlSession = sqlSession;
	}
	
	// 댓글 & 답글 등록
	public void text_regi( CommentsVO vo ) {
		sqlSession.insert( "comments.text_regi", vo );
	}
	
	// 피드에 달린 댓글 가져오기
	public List<CommentsVO> getComments( int feed_idx ) {
		
		List<CommentsVO> list = sqlSession.selectList( "comments.getComments", feed_idx );
				
		return list;
	}
	// 모달창에서 댓글 + 답글 전부 가져오기
	public List<CommentsVO> getCommentsAll( int feed_idx ) {
		
		List<CommentsVO> list = sqlSession.selectList( "comments.getCommentsAll", feed_idx );
		
		return list;
	}
	
	
	
}
