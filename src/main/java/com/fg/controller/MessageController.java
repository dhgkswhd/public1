package com.fg.controller;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fg.dao.MessageDAO;
import com.fg.dao.ProfileDAO;
import com.fg.vo.MemberVO;
import com.fg.vo.MessageVO;
import com.fg.vo.ProfileVO;
import com.fg.dao.FeedDAO;
import com.fg.dao.FriendsDAO;
import com.fg.vo.FriendsVO;



@Controller
@SessionAttributes({"checkcount", "friendsList"})
public class MessageController {
	
	ProfileDAO profile_dao;
	MessageDAO message_dao;
	FriendsDAO friends_dao;
	FeedDAO feed_dao;
	
	// 중간경로
	final static String PATH_MESSAGE = "message/";
	
	public MessageController() {} // 기본생성자
	
	public MessageController( MessageDAO message_dao, FriendsDAO friends_dao, FeedDAO feed_dao, ProfileDAO profile_dao) {
		this.message_dao = message_dao;
		this.friends_dao = friends_dao;
		this.feed_dao = feed_dao;
		this.profile_dao = profile_dao;
	}

	// 채팅창 열었을 때 & 메세지 전송하고 페이지 redirect 할 때
	@RequestMapping("/message/main")
	public ModelAndView chatting_Action( HttpSession session, HttpServletRequest request, 
										 String my_id, String friend_id ) {
		
		System.out.println("내 아이디 : " + my_id);
		System.out.println("친구아이디 : " + friend_id);
		
		// 모델앤뷰 선언
		ModelAndView mv = new ModelAndView();
		
		// 친구아이디 세션에 저장
		session.setAttribute( "friend_id", friend_id ); 
		
		// 맵에 내 아이디, 친구 아이디 저장
		Map<String, String> inputData = new HashMap<String, String>();
		inputData.put("my_id", my_id);
		inputData.put("friend_id", friend_id);
		
		// 안읽은 메세지 읽은걸로 처리하기 ---> 채팅창 들어오는 컨트롤러 이므로
		message_dao.updatecheck( inputData ); // checkcount: 1(안읽음) -> 0(읽음)
		
		// 룸네임 설정
		String room_name = friend_id + my_id;
		
		if ( my_id.compareTo( friend_id ) >= 0 ) {
			room_name = my_id + friend_id;
		}
		
		// 룸네임으로 이전 메세지 내용들 가져오기
		List<MessageVO> msg = message_dao.myMsgList( room_name );
		
		mv.addObject("msg", msg); 			   			// 이전 대화내용
		mv.addObject("checkcount", 0); 				   	// 메세지 읽었다는 세션 설정
		
		mv.setViewName( PATH_MESSAGE + "message_now" );   	// 뷰 이름
		
		return mv;
	}
	
	
	// 메세지 보내기 클릭시
	@RequestMapping("/message/send")
	public String send_message( Model model, HttpServletRequest request, HttpSession session,
								String my_id, String msg_text ) {
		
		// 친구 아이디
		String friend_id = (String)session.getAttribute("friend_id");
		
		MessageVO vo = new MessageVO();
		vo.setMy_id( my_id );							// 발신자 아이디
		vo.setFriend_id( friend_id );					// 수신자 아이디
		vo.setMsg_text( msg_text );						// 메세지 내용
		vo.setCheckcount( 1 );	 						// 처음에 1로 저장 (읽으면 0으로바뀜)
		
		// 메세지 방 이름 (room_name) 설정
		if ( my_id.compareTo( friend_id ) >= 0 ) {
			vo.setRoom_name( my_id + friend_id );
		}
		else {
			vo.setRoom_name( friend_id + my_id );
		}
		
		System.out.println("발신자 (나) 아이디 : " + my_id);
		System.out.println("수신자 아이디 : " + friend_id);
		System.out.println("메세지 내용 : " + msg_text);
		//System.out.println("내 프로필 사진 : " + vo.getProfile_image());
		System.out.println("룸네임 : " + vo.getRoom_name());
		System.out.println("체크카운트 : " + vo.getCheckcount());
		
		// 메세지 한줄 db에 삽입
		message_dao.insertMsg( vo );
		
		// 메세지 체크 session, 처음에 메세지 삽입시 체크 1로 넣음
		model.addAttribute( "checkcount", 1 );
		
		return "redirect:/message/main?my_id=" + my_id + "&friend_id=" + friend_id;
	}
	
	// 메세지 버튼 눌렀을 때 나오는 메세지 목록
	@RequestMapping("/message/msglist")
	String message_list( String my_id, String profile_image, Model model ) {
		
		System.out.println("내아이디 : " + my_id);
		System.out.println("내 프로필사진 : " + profile_image);
		
		// 나랑 대화한 사람과의 마지막 메세지 가져오기
		List<MessageVO> lastMsgInfo = message_dao.last_message( my_id );
		
		 // 안읽은 쪽지 카운트
		List<Integer> countList = new ArrayList<Integer>();
		
		Map<String, String> inputData = new HashMap<String, String>();
		
		inputData.put("my_id", my_id);
		
		for ( int i = 0 ; i < lastMsgInfo.size() ; i++ ) {
			inputData.put( "friend_id", lastMsgInfo.get(i).getMy_id());
			countList.add( message_dao.list_checkcount( inputData ) );
		}
  		
  	
 		model.addAttribute("lastmsgInfo", lastMsgInfo);		// 마지막 메세지 정보
		model.addAttribute("countlist", countList);
		
		return PATH_MESSAGE + "message_list";
	}
}
