package com.fg.service.member;

import java.util.Map;

import org.springframework.ui.Model;

import com.fg.dao.MemberDAO;
import com.fg.vo.MemberVO;

public interface Service_Member {
	void join( MemberVO memberVO );
	
	Map<String, String> joinCheck( String paramId, MemberDAO member_dao );
}
