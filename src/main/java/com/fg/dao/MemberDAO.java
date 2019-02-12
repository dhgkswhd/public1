package com.fg.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.fg.vo.MemberVO;
import com.fg.vo.ProfileVO;

public class MemberDAO {
	SqlSession sqlSession;

	// 주입될 때 사용될 setter
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	/** --- 회원가입 시 아이디 중복체크  --- */
	public boolean checkId ( String paramId ) {
		boolean result = false;
		
		String id = sqlSession.selectOne("member.checkId", paramId.toLowerCase());
		
		// 해당 아이디가 존재하면 true 로 반환
		if ( id == null )
			result = true;
		
		return result;
	}
	
	/**--- 회원가입 - MEMBER 테이블에 회원정보 인서트 ---*/
	public void insertMemberData ( MemberVO vo ) {
		// MEMBER 테이블에 회원정보 인서트
		sqlSession.insert( "member.insertMemberData", vo );
	}
	
	/**--- 회원가입 - PROFILE 테이블에 프로필 초기값 세팅 ---*/
	public void initMemberProfile ( MemberVO vo ) {
		// PROFILE 테이블에 프로필 초기값 세팅
		sqlSession.insert( "profile.initProfile", vo );
	}
	
	/**--- 로그인한 회원의 회원정보 획득 ---*/
	public MemberVO getMemberData( Map<String, String> inputData ) {
		
		MemberVO vo = (MemberVO)sqlSession.selectOne("member.getMemberData", inputData);
		
		return vo;
	}
	
	/**--- 로그인한 회원의 프로필정보 획득 ---*/
	public ProfileVO getMemberProfile( Map<String, String> inputData ) {
		
		ProfileVO profileVO = (ProfileVO)sqlSession.selectOne("member.getMemberProfile", inputData);
		
		return profileVO;
	}

	/** --- 비밀번호 찾기 --- */
	public String find_pw( Map<String, String> map ) {
		
		String res = sqlSession.selectOne( "member.find_pw", map );
		
		return res;
	}
	
	
}
