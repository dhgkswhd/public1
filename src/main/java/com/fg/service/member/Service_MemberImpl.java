package com.fg.service.member;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fg.dao.FeedDAO;
import com.fg.dao.MemberDAO;
import com.fg.vo.MemberVO;

@Service("service_member")
public class Service_MemberImpl implements Service_Member {
	MemberDAO member_dao;
	
	public Service_MemberImpl( MemberDAO member_dao ) {
		this.member_dao = member_dao;
	}
	
	// 회원가입
	@Override
	public void join( MemberVO memberVO ) {
		//memberdao.insertMemberData( memberVO );
	}

	// 회원가입시 아이디 중복체크
	@Override
	public Map<String, String> joinCheck( String paramId, MemberDAO memberdao ) {
		
		// 초기값 설정
		String msg	 	  = "사용할 수 없는 아이디 입니다.";
		String textColor  = "red";
		
       	boolean res = false;
       	
       	// 아이디 유효성 검사 후 중복체크
       	if ( paramId != "" && !(paramId.indexOf(" ") >= 0) &&
       			!(paramId.length() < 4 || paramId.length() > 12) ) {

       		// 아이디 중복체크
       		res = memberdao.checkId( paramId );
       		
       		// 결과 (res) 가  true면
       		if ( res ) {
           		msg 	   = "사용 가능한 아이디 입니다.";
           		textColor  = "blue";
           	}
       	}
       	
       	Map<String, String> map = new HashMap<String, String>();
       	map.put("msg", msg);
       	map.put("textColor", textColor);
       	
       	return map;
	}
	


}
