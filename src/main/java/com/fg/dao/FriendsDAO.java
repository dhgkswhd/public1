package com.fg.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.fg.vo.FriendsApplyVO;
import com.fg.vo.FriendsVO;

public class FriendsDAO {
	SqlSession sqlSession;

	// 주입될 때 사용될 setter
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	/** --- 둘이 친구 맺어줌 --- */
	public void friendConnected( FriendsApplyVO vo ) {
		// 내 아이디, 친구아이디를 바꿔서 테이블에 2개 인서트한다.
		sqlSession.insert( "friends.friendConnected_sender", vo );
		sqlSession.insert( "friends.friendConnected_receiver", vo );
		
	}

	/** --- 둘이 이미 친구인지 아닌지 검사 --- */
	public boolean checkFriend( Map<String,String> map ) {
		boolean res = true; // 결과값을 반환할 boolean 변수
		
		// 친구면 1을 반환, 친구가 아니면 0을 반환
		int temp = sqlSession.selectOne( "friends.checkFriend", map );
		
		// 친구면 res 를 false로 바꿈
		if ( temp == 1 ) {
			res = false;
		}
		System.out.println("친구이면 1, 아니면 0 : " + temp);
		
		return res;
	}

	/** --- 내 친구 수 가져오기 --- */
	public int getFriendsCount( String id ) {

		int count = sqlSession.selectOne( "friends.getFriendsCount", id );
		
		return count;
	}

}
