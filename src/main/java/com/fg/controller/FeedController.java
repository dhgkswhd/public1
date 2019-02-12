package com.fg.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fg.dao.CommentsDAO;
import com.fg.dao.FeedDAO;
import com.fg.dao.FriendsApplyDAO;
import com.fg.dao.FriendsDAO;
import com.fg.dao.MessageDAO;
import com.fg.dao.ProfileDAO;
import com.fg.vo.CommentsVO;
import com.fg.vo.FeedVO;
import com.fg.vo.FriendsApplyVO;
import com.fg.vo.FriendsVO;
import com.fg.vo.MemberVO;
import com.fg.vo.ProfileVO;

@Controller
@SessionAttributes({"applyCount", "friendsList", "checkcount"})
public class FeedController {
	FeedDAO feed_dao;
	FriendsDAO friends_dao;
	FriendsApplyDAO friendsApply_dao;
	CommentsDAO comments_dao;
	ProfileDAO profile_dao;
	MessageDAO message_dao;
	
	// 페이지에 따라 중간 경로들을 다르게 지정
	final static String PATH_MAIN	 = "main/";
	final static String PATH_FEED  	 = "feed/";
	final static String PATH_MEMBER  = "member/";
	
	public FeedController() {} // 기본생성자
	
	public FeedController( FeedDAO feed_dao, FriendsDAO friends_dao, 
			FriendsApplyDAO friendsApply_dao, CommentsDAO comments_dao,
			ProfileDAO profile_dao, MessageDAO message_dao ) {
		this.feed_dao = feed_dao;
		this.friends_dao = friends_dao;
		this.friendsApply_dao = friendsApply_dao;
		this.comments_dao = comments_dao;
		this.profile_dao = profile_dao;
		this.message_dao = message_dao;
	}
	
	// 피드 뷰(피드 메인) 페이지로 이동 (이쪽이 할일 제일 많음)
	@RequestMapping("/feed/mainview")
	public String feed_view ( Model model, HttpSession session ) {
		MemberVO vo = (MemberVO)session.getAttribute("memberVO");
		
		String my_id = vo.getMember_id(); // 세션에 저장되어있는 내 아이디를 구해온다.
		
		// 친구목록 가져오기
		List<ProfileVO> friendsList = profile_dao.getFriendsList( my_id );
		
		// 친구신청 알림 수 count 가져오기
		int applyCount = friendsApply_dao.getApplyCount( my_id );
		
		// 내 친구들 프로필사진 + 게시물 가져오기 (피드에서 친구아이디 옆에 사진 띄워줄려고 사진 가져옴)
		List<FeedVO> friendsFeedList = feed_dao.getFriendsFeedList( my_id );
		
		// 채팅방 쪽지 알림 수 가져오기
		int checkcount = message_dao.checkcount( my_id );
		
		// 피드 각 게시물에 달린 댓글 가져오기
		// 모든 게시물의 댓글을 담을 List
		for ( int i = 0; i < friendsFeedList.size(); i++ ) {
			  // 위에서 가져온 친구들 게시물에서 번호만 추출
			  int feed_idx = friendsFeedList.get(i).getFeed_idx();
			  System.out.println("각 게시물번호 : " + feed_idx);
			  
			  // 해당 게시물의 번호를 파라미터로 전달해서 댓글들을 구해온 뒤 멤버변수에 set
			  List<CommentsVO> list = comments_dao.getComments( feed_idx );
			  friendsFeedList.get(i).setCom_list( list );
			  
			  // System.out.println( "댓글번호 : " + list.get(i).getComments_idx() );
			  
		}
		
		
		model.addAttribute("friendsFeedList", friendsFeedList);   // 내 친구들 프로필사진 + 게시물 + 게시물에 달린 댓글
		
		// 세션에 저장할 값 3개
		model.addAttribute("friendsList", friendsList);	 	  	  // 로그인한 회원의 친구 목록
		model.addAttribute("applyCount", applyCount);		  	  // 로그인한 회원의 친구신청 알림 수
		model.addAttribute("checkcount", checkcount);			  // 안읽은 메세지 수 카운트
		
		return PATH_FEED + "feed_main";
	}
	
	
	// 친구 검색 자동완성
	@RequestMapping( value = "/feed/search", method = RequestMethod.POST, produces = "application/json; charset=utf-8" )
	@ResponseBody
	public List<ProfileVO> feed_search( @RequestParam("my_id") String my_id, 
									    @RequestParam("user_id") String user_id ) {
		List<ProfileVO> list = new ArrayList<ProfileVO>();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("my_id", my_id);
		map.put("user_id", user_id);
		
		// onkeyup 될 때마다 수행될 쿼리문. 검색결과를  list 에 담는다.
		if ( user_id != "" ) {
			// map 넘겨줌 (내 아이디 제외하고 검색할 것)
			 list = (ArrayList)profile_dao.searchUser( map );
		}
		
		return list;
	}
	
	// 게시물 작성
	@RequestMapping("/feed/regi")
	public String feed_regi( MultipartHttpServletRequest request ) {
		
		MultipartFile mf = request.getFile("myImg");
		String path = request.getRealPath("/resources/feedimages/");
		
		System.out.println( "파일저장 경로 : " + path );
		System.out.println(" 등록인 : " + request.getParameter("register"));
		System.out.println(" 내용 : " + request.getParameter("feedtext"));
		
		String fileName =  mf.getOriginalFilename();
		
		File uploadFile = new File( path + fileName );
		
		try {
			mf.transferTo( uploadFile );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		FeedVO vo = new FeedVO();
		vo.setUser_id( request.getParameter("register") );	 // 등록인
		vo.setFeed_text( request.getParameter("feedtext") ); // 내용
		vo.setFeed_image( fileName );						 // 파일명
		
		// 피드 내용 DB에 insert
		feed_dao.insertFeed( vo );
		
		return "redirect:/feed/mainview";
	}
	
	// 게시물 삭제
	@RequestMapping("/feed/delete")
	public String deleteContent ( @RequestParam("feed_idx") int feed_idx ) {
		
		System.out.println("게시물번호 : " + feed_idx);
		feed_dao.deleteContent( feed_idx );
		
		return "redirect:/feed/mainview";
	}
	
	// 게시물 수정
	@RequestMapping("/feed/update")
	public String updateContent ( @RequestParam("feed_idx") int feed_idx,
								  @RequestParam("text") String text ) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("feed_idx", feed_idx);
		map.put("text", text);
		
		feed_dao.updateContent( map );
		
		return "redirect:/feed/showcontent?feed_idx=" + feed_idx + "&register_id=";
	}
	
	
	// 마이페이지 뷰
	@RequestMapping("/feed/mypageview") 
	public ModelAndView myPage( HttpSession session ) {
		ModelAndView mv = new ModelAndView();
		
		 // 세션에 저장되어있는 로그인한 id
		MemberVO vo = (MemberVO)session.getAttribute("memberVO");
		String id = vo.getMember_id();
		
		// 내 id로 내 피드 가져오기
		List<FeedVO> myFeedList = feed_dao.getFeed( id );
			
		// 내 id로 내 프로필 가져오기
		ProfileVO myProfile = feed_dao.getProfile( id );
		
		// 내id로  친구 수 가져오기
		int friendsCount = friends_dao.getFriendsCount( id );
		
		// 내id로  게시물 수 가져오기
		int feedCount = feed_dao.getFeedCount( id );
		
		mv.addObject("myFeedList", myFeedList);
		mv.addObject("myProfile", myProfile);
		mv.addObject("friendsCount", friendsCount);
		mv.addObject("feedCount", feedCount);
		
		mv.setViewName( PATH_MEMBER + "member_my_feed" );
		
		return mv;
	}
		
	
	
	// 상대방 피드 보기
	@RequestMapping("/feed/friend")
	public ModelAndView friendPage ( HttpSession session, @RequestParam("friend_id") String friend_id ) {
		int applyStatus; // 친구요청 상태 변수 (-1:이미친구 / 0:친구요청가능 / 1:수락대기중)
		
		MemberVO vo = (MemberVO)session.getAttribute("memberVO");
		
		String my_id = vo.getMember_id(); // 세션에 저장되어있는 내 아이디를 구해온다.
		
		ModelAndView mv = new ModelAndView();
		
		// 상대방 id로 상대방 게시물 가져오기
		List<FeedVO> friendFeed = feed_dao.getFeed( friend_id );
			
		// 상대방 id로 상대방 프로필 가져오기
		ProfileVO friendProfile = feed_dao.getProfile( friend_id );
		
		// 상대방 id로  친구 수 가져오기
		int friendsCount = friends_dao.getFriendsCount( friend_id );
		
		// 상대방 id로  게시물 수 가져오기
		int feedCount = feed_dao.getFeedCount( friend_id );
		
		// 내 아이디, 친구 아이디를 map 에 담음
		Map<String, String> map = new HashMap<String, String>();
		map.put("my_id", my_id);
		map.put("friend_id", friend_id);
		
		// 아래에 친구요청 상태 가져오기 전에, 둘이 이미 친구인지부터 검사한다.
		boolean res = friends_dao.checkFriend( map );
		
		// 친구요청이 가능한 상태면
		if ( res ) {
			// 친구요청 상태 가져오기
			// 결과가 1이면(테이블에 친구요청 row가 존재하면) 친구요청 수락대기중이기 때문에 친구요청 불가(수락대기중), 0이면 친구요청 가능
			applyStatus = friendsApply_dao.getApplyStatus( map );
		} 
		//  이미 친구이면 applyStatus를 -1로 넣어줌
		else {
			applyStatus = -1;
		}
		
		mv.addObject("friendFeed", friendFeed);
		mv.addObject("friendProfile", friendProfile);
		mv.addObject("applyStatus", applyStatus);
		mv.addObject("friendsCount", friendsCount);
		mv.addObject("feedCount", feedCount);
		
		mv.setViewName( PATH_MEMBER + "member_friend_feed" );
		
		return mv;
		
	}
	
	// 게시글 클릭시 모달창 띄우는 화면으로 가기 전 controller
	@RequestMapping("/feed/showcontent")
	public ModelAndView showContent( @RequestParam("feed_idx") Integer feed_idx,
									 @RequestParam("register_id") String register_id ) {
		
		System.out.println("게시글번호 : " + feed_idx);
		System.out.println("게시글 작성자id : " + register_id);
		
		ModelAndView mv = new ModelAndView();
		
		// 클릭한 게시물 번호가 null이 아니면
		if ( feed_idx != null && register_id !=null ) {

			// 친구 프로필사진 + 해당 게시글 내용 가져오기
			FeedVO vo = feed_dao.getContent( feed_idx );
			
			// 해당 게시물에 달린 댓글 + 답글 가져오기
			List<CommentsVO> commentsListAll = comments_dao.getCommentsAll( feed_idx );
			
			mv.addObject("vo", vo);		// 글쓴이 프로필
			mv.addObject("commentsListAll", commentsListAll);	// 게시글에 달린 댓글들
		}
		
		mv.setViewName( PATH_MEMBER + "member_showcontent" );
		
		return mv;
	}
}
