package com.fg.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fg.dao.CommentsDAO;
import com.fg.vo.CommentsVO;

@Controller
public class CommentsController {
	CommentsDAO comments_dao;
	
	final static String VIEW_PATH = "/WEB-INF/views/"; // 접두 경로
	
	// 페이지에 따라 중간 경로들을 다르게 지정
	final static String PATH_MAIN = "main/";
	final static String PATH_FEED = "feed/";
	final static String PATH_MEMBER = "member/";
	
	public CommentsController() {}
	
	public CommentsController( CommentsDAO comments_dao ) {
		this.comments_dao = comments_dao;
	}
	
	// 댓글 작성 
	@RequestMapping("/comment/regi")
	public String comment_regi( @RequestParam("feed_idx") Integer feed_idx,
							    @RequestParam("my_id") String my_id, 
							    @RequestParam("text") String text,
							    @RequestParam("where_url") String where_url,
								@RequestParam("comments_refNum") Integer comments_refNum ) {
		
		// vo 에담음
		CommentsVO vo = new CommentsVO();
		vo.setFeed_idx( feed_idx );			  // 게시글 번호
		vo.setUser_id( my_id );				  // 내 아이디
		vo.setComments_text( text ); 		  	 // 댓글 내용
		
		// 댓글이면 (참조할 부모댓글이 없으면)
		if ( comments_refNum == 0 ) {
			System.out.println("댓글입니다.");
			vo.setComments_refNum( 0 ); 		 	 // 부모댓글 번호 (댓글일 경우 0을 넣음)
			vo.setComments_div( 0 ); 			  	 // 댓글은 0
		}
		// 답글이면 (부모댓글 참조)
		else {
			System.out.println("답글입니다.");
			vo.setComments_refNum( comments_refNum ); // 부모댓글 번호 (답글이기때문에 부모댓글번호 넣음)
			vo.setComments_div( 1 ); 			  	  // 답글은 1
		}
	
		// 쿼리로 ㄱ
		comments_dao.text_regi( vo );
		
		// 메인창에서 댓글을 썼는지, 모달창에서 썼는지에 따라 이동할 페이지가 다름
		if ( where_url.equals("main") ) { 		// 메인창에서 댓글 썼을 때
			return "redirect:/feed/mainview";
		}
		
		// 모달창에서 댓글 썼을 때
		return "redirect:/feed/showcontent?feed_idx=" + feed_idx + "&register_id=";
	}
}






