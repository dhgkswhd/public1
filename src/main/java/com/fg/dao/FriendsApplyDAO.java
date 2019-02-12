package com.fg.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.fg.vo.FriendsApplyVO;
import com.fg.vo.FriendsVO;

public class FriendsApplyDAO {
	SqlSession sqlSession;

	// 주입될 때 사용될 setter
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	/** --- 친구신청 알림 수 (몇명이 친구신청 했는지) count --- */
	public int getApplyCount ( String my_id ) {
		int applyCount = sqlSession.selectOne( "friendsapply.getApplyCount", my_id);
		System.out.println("어플라이카운트 : " + applyCount);
		
		return applyCount;
	}
	
	
	/** --- 친구요청 --- **/
	public void apply ( FriendsApplyVO vo ) {
		sqlSession.insert( "friendsapply.friendsApply", vo );
	}
	
	
	/** --- 친구요청취소 --- **/
	public void applyCancel( FriendsApplyVO vo ) {
		sqlSession.delete( "friendsapply.friendsApplyCancel", vo );
	}
	
	
	/** --- 친구요청 상태 가져오기 --- **/
	public int getApplyStatus( Map<String, String> map ) {
		// 결과가 1이면 친구요청 수락대기중, 0이면 친구요청 가능
		int applyStatus = sqlSession.selectOne( "friendsapply.applyStatus", map );
		
		return applyStatus;
	}
	
	
	/** --- 나에게 친구요청한 인간들 리스트 가져오기 --- */
	public List<FriendsApplyVO> getFriendsApplyList ( String my_id ) {
		
		List<FriendsApplyVO> list = sqlSession.selectList( "friendsapply.getFriendsApplyList", my_id );
		
		return list;
	}

	/** --- 친구 수락시 데이터 삭제 --- */
	public void friendAccept( FriendsApplyVO vo ) {
		sqlSession.delete( "friendsapply.deleteData", vo );
	}
	
	/** --- 친구 거절시 데이터 삭제 (위에꺼랑 쿼리문이 똑같음 나중에 추가기능구현하면 바꿔줌 ex:친구추가 두번 못걸게 하기) --- */
	public void friendRefuse( FriendsApplyVO vo ) {
		sqlSession.delete( "friendsapply.deleteData", vo );
		
	}
	
}
