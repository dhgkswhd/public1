package com.fg.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.fg.vo.FeedVO;
import com.fg.vo.MemberVO;
import com.fg.vo.ProfileVO;

public class FeedDAO {
	SqlSession sqlSession;

	public void setSqlSession( SqlSession sqlSession ) {
		this.sqlSession = sqlSession;
	}

	/**--- 피드에 작성한 글 업로드 ---*/
	public void insertFeed( FeedVO vo ) {
		sqlSession.insert( "feed.insertFeed", vo );
	}
	
	/**--- 게시물 가져오기 ---*/
	public List<FeedVO> getFeed( String id ) {
		List<FeedVO> list = null;
		
		list = sqlSession.selectList( "feed.getFeed", id );
		
		return list;
	}
	
	/** --- 내 프로필 가져오기 or 친구 프로필 가져오기 --- */
	public ProfileVO getProfile( String id ) {
		
		ProfileVO vo = (ProfileVO)sqlSession.selectOne( "profile.getProfile", id );
		
		return vo;
	}
	
	/**--- 게시물 상세보기 ---*/
	public FeedVO getFeedDetails( int feed_idx ) {
		FeedVO vo = null;
		
		vo = sqlSession.selectOne("feed.getFeedDetails",feed_idx);
				
		return vo;
	}
	
	/** --- 피드메인 페이지로 이동할 때, 내 친구들 프로필사진 + 게시물 가져오기 --- */
	public List<FeedVO> getFriendsFeedList( String id ) {
		
		List<FeedVO> friendsFeedList = sqlSession.selectList( "feed.getFriendsFeedList", id );
		
		return friendsFeedList;
	}
	
	/** --- 친구 프로필사진 + 해당 게시글 내용 가져오기 --- */
	public FeedVO getContent( Integer feed_idx ) {
		
		FeedVO vo = sqlSession.selectOne( "feed.getContent", feed_idx );
		
		return vo;
	}

	/** --- 게시물 수 가져오기 --- */
	public int getFeedCount( String id ) {
		
		int count = sqlSession.selectOne( "feed.getFeedCount" , id ); 
		
		return count;
	}

	/** --- 게시물 삭제 --- */
	public void deleteContent( int feed_idx ) {
		sqlSession.delete( "feed.deleteContent", feed_idx );
	}

	/** --- 게시물 수정 --- */
	public void updateContent( Map<String, Object> map ) {
		sqlSession.update( "feed.updateContent", map );
	}
}
