package com.fg.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.fg.vo.FriendsVO;
import com.fg.vo.MemberVO;
import com.fg.vo.ProfileVO;

public class ProfileDAO {
	SqlSession sqlSession;

	// 주입될 때 사용될 setter
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	/** --- 로그인 할 때, 친구목록 가져오기 --- */
	public List<ProfileVO> getFriendsList( String my_id ) {
		
		List<ProfileVO> list = sqlSession.selectList( "profile.getFriendsList", my_id );
		
		return list;
	}
	
	/**--- 검색어 자동완성 ---*/
	public List<ProfileVO> searchUser ( Map<String, String> map ) {
		
		List<ProfileVO> list = sqlSession.selectList( "feed.searchUser", map );
		
		return list;
	}
	
	/** --- 프로필 가져오기 --- */
	public ProfileVO getProfileData(String member_id) {
		
		ProfileVO vo = (ProfileVO)sqlSession.selectOne("profile.getProfileData", member_id);
		
		return vo;
	}
	
	/** --- 프로필 내용 수정 --- */
	public void updateProfileText( Map<String, String> map ) {
		sqlSession.update( "profile.update_text", map );
	}

	/** --- 프로필 사진 업데이트 --- */
	public void updateProfileImg( ProfileVO vo ) {
		sqlSession.update( "profile.update_img", vo );
	}
	
	
}
