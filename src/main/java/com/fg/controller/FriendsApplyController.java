package com.fg.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fg.dao.FriendsApplyDAO;
import com.fg.dao.FriendsDAO;
import com.fg.vo.FriendsApplyVO;
import com.fg.vo.MemberVO;

@Controller
public class FriendsApplyController {
	FriendsApplyDAO friendsApply_dao;
	FriendsDAO friends_dao;
	
	// 페이지에 따라 중간 경로들을 다르게 지정
	final static String PATH_MAIN 	 = "main/";
	final static String PATH_FEED  	 = "feed/";
	final static String PATH_MEMBER  = "member/";
	
	public FriendsApplyController() {}
	
	public FriendsApplyController( FriendsApplyDAO friendsApply_dao, FriendsDAO friends_dao ) {
		this.friendsApply_dao = friendsApply_dao;
		this.friends_dao = friends_dao;
	}
	
	// 친구 요청, 친구요청취소 (AJAX)
	@RequestMapping( value = "/friendapply/apply", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String apply ( @RequestParam("my_id") String my_id, 
					   	  @RequestParam("user_id") String user_id, 
					   	  @RequestParam("status") String status ) {
		
		String str = "";
		
		// 내 아이디, 친구요청할 아이디를 vo에 담는다.
		FriendsApplyVO vo = new FriendsApplyVO();
		vo.setSender_id( my_id );
		vo.setReceiver_id( user_id );
		
		// status = 친구요청인지 친구요청취소인지에 따라 수행될 함수가 다르다.
		// '친구요청' 버튼 클릭 시 db 테이블 삽입 + 버튼을 '친구요청취소' 로 바꾼다 
		if ( status.equals("친구요청") && my_id != null && user_id != null ) {
			friendsApply_dao.apply( vo );
			str = "친구요청취소";
		}
		// '친구요청취소' 버튼 클릭 시 db 테이블 삭제 + 버튼을 '친구요청' 으로 바꾼다
		else if ( status.equals("친구요청취소") && my_id != null && user_id != null ){
			friendsApply_dao.applyCancel( vo );
			str = "친구요청";
		}	
		
		else {
			str = "오류발생...";
		}
		
		return str;
	}
	
	// '알림' 버튼 눌렀을 때 나에게 친구요청한 회원 리스트 가져오기
	@RequestMapping("/friendapply/applylist")
	public ModelAndView friendsApplyList_view ( HttpSession session ) {
		MemberVO vo = (MemberVO)session.getAttribute("memberVO");
		
		String my_id = vo.getMember_id(); // 세션에 저장되어있는 내 아이디를 구해온다.
		
		List<FriendsApplyVO> applyList = new ArrayList<FriendsApplyVO>();
		
		// 친구요청 받은사람 목록에 내 아이디가 있으면 다 가져옴
		applyList = friendsApply_dao.getFriendsApplyList( my_id );

		ModelAndView mv = new ModelAndView();
		
		System.out.println("몇명이 친구요청했을까 :" + applyList);
		
		mv.addObject("applyList", applyList);
		mv.setViewName( PATH_MEMBER + "member_friends_apply" );
		
		return mv;
	}
	
	// 친구 거절
	@RequestMapping("/friendapply/refuse")
	public String friendRefuse( @RequestParam("receiver_id") String receiver_id, 
			 					@RequestParam("sender_id") String sender_id ) {
		
		// 파라미터에 담겨온 아이디 2개를 vo에 담아서 사용
		FriendsApplyVO vo = new FriendsApplyVO();
		vo.setReceiver_id( receiver_id );
		vo.setSender_id( sender_id );
		
		// 거절누르면 테이블에서 날림
		friendsApply_dao.friendRefuse( vo );
	
		// 피드 메인페이지로 리다이렉트
		return "redirect:/feed/mainview";
	}
	
	// 친구 수락
	@RequestMapping("/friendapply/accept")
	public String friendAccept ( @RequestParam("receiver_id") String receiver_id, 
								 @RequestParam("sender_id") String sender_id ) {
			
		// 파라미터에 담겨온 아이디 2개를 vo에 담아서 사용
		FriendsApplyVO vo = new FriendsApplyVO();
		vo.setReceiver_id( receiver_id );
		vo.setSender_id( sender_id );
		
		// 친구수락 시 FRIENDS_APPLY 테이블에 있는 해당 row 날려버림
		friendsApply_dao.friendAccept( vo );
		
		// 친구가 됐으니 FRIENDS 테이블에 각 회원당 데이터 1개씩 넣어줌 (총2개, 추후 추가기능구현할때 필요해서 2개넣음)
		friends_dao.friendConnected( vo );
			
		// 피드 메인페이지로 리다이렉트
		return "redirect:/feed/mainview";
	}
}
