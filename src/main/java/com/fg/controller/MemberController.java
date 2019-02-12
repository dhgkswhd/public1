package com.fg.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.fg.dao.FeedDAO;
import com.fg.dao.FriendsApplyDAO;
import com.fg.dao.FriendsDAO;
import com.fg.dao.MemberDAO;
import com.fg.dao.ProfileDAO;
import com.fg.service.member.Service_Member;
import com.fg.vo.FeedVO;
import com.fg.vo.FriendsVO;
import com.fg.vo.MemberVO;
import com.fg.vo.ProfileVO;

@Controller
@SessionAttributes({"memberVO", "profileVO"}) // 세션 설정
public class MemberController {
	
	// MemberDAO, FriendsDAO, FriendsApplyDAO
	MemberDAO member_dao;
	FriendsDAO friends_dao;
	FriendsApplyDAO friendsApply_dao;
	
	// 트랜잭션 매니저 
	PlatformTransactionManager transactionManager;
	
	// 페이지에 따라 중간 경로들을 다르게 지정
	final static String PATH_MAIN    = "main/";
	final static String PATH_FEED    = "feed/";
	final static String PATH_MEMBER  = "member/";
	
	public MemberController() {}
	
	public MemberController( MemberDAO member_dao, FriendsDAO friends_dao, FriendsApplyDAO friendsApply_dao, PlatformTransactionManager transactionManager ) {
		this.member_dao 		= member_dao;
		this.friends_dao		= friends_dao;
		this.friendsApply_dao 	= friendsApply_dao;
		this.transactionManager = transactionManager;
	}
	
	// 회원가입 페이지로 이동
	@RequestMapping("/member/joinview")
	public String joinView () {
		return PATH_MEMBER + "member_join";
	}
	
	// 비밀번호 찾기 페이지로 이동
	@RequestMapping("/member/findpwview")
	public String findPwView () {
		return PATH_MEMBER + "member_find_pw";
	}
	
	// 비밀번호 찾기 결과 (AJAX)
	@RequestMapping("/member/findpwresult")
	@ResponseBody
	public Map<String, String> findpw_result( @RequestParam("id") String id,
											  @RequestParam("phonenumber") String pn ) {
		
		String resStr = "no";
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("pn", pn);
		
		// res => 찾아온 비밀번호
		String pw = member_dao.find_pw( map );
		
		// 비밀번호가 null 이 아니면
		if ( pw != null ) {
			resStr = "yes";
		}
		
		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put("resStr", resStr);
		resMap.put("pw", pw);
		
		// 결과를 JSONArray로 반환
		return resMap;
	}
	
	// 회원가입
	@RequestMapping( value = "/member/join", method = RequestMethod.POST )
	public String join ( MemberVO memberVO ) {
		
		TransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction( definition );
			
		// 트랜잭션 처리
		try {
			// MEMBER 테이블에 회원정보 인서트
			member_dao.insertMemberData( memberVO );
			
			// PROFILE 테이블에 프로필 초기값 세팅
			member_dao.initMemberProfile( memberVO );
			
			transactionManager.commit( status );
			
		} catch (Exception e){
			e.printStackTrace();
			transactionManager.rollback( status );
		}
		
		return "redirect:/main";
	}
	
	// 회원가입 아이디 중복체크 (AJAX)
	@RequestMapping( value = "/member/checkid", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, String> checkId ( @RequestParam("id") String paramId ) {
		
		System.out.println("아이디체크들어옴");
		
		// 초기값 설정
		String msg	 	  = "사용할 수 없는 아이디 입니다.";
		String textColor  = "red";
		
       	boolean res;
       	
       	// 아이디 유효성 검사 후 중복체크
       	if ( paramId != "" && !(paramId.indexOf(" ") >= 0) &&
       			!(paramId.length() < 4 || paramId.length() > 12) ) {

       		// 아이디 중복체크
       		res = member_dao.checkId( paramId );
       		
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
	
	
	// 로그인 (AJAX)
	@RequestMapping( value = "/member/login", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String login ( Model model, @RequestParam("member_id") String id, @RequestParam("member_pwd") String pwd ) {
		
		String msg = "no"; // ajax 콜백함수로 보여줄 메세지
		
		// 회원의 모든정보 가져오기
		Map<String, String> inputData = new HashMap<String, String>();
		inputData.put("id", id);
		inputData.put("pwd", pwd);
		
		// 로그인한 회원의 회원정보 가져오기
		MemberVO memberVO = member_dao.getMemberData( inputData );
		
		// 로그인한 회원의 프로필정보 가져오기
		ProfileVO profileVO = member_dao.getMemberProfile( inputData );
		
		// 정상적으로 계정이 있으면 (회원정보가져오기 + 프로필정보가져오기) 나머지 할일 수행
		if ( memberVO != null && profileVO != null ) {
			msg = "yes"; // msg를 'yes' 로 변경
			
			model.addAttribute("memberVO", memberVO);		// 로그인한 회원 정보 (세션저장)
			model.addAttribute("profileVO", profileVO);		// 로그인한 회원 프로필정보 (세션저장)
		}
		
		return msg;
	}
	
	
	// 로그아웃
	@RequestMapping("/member/logout")
	public String logout ( SessionStatus sessionStatus ) {
		
		sessionStatus.setComplete();
		
		return "redirect:/main"; 
	}
	
}









