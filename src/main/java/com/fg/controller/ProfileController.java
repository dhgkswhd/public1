package com.fg.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fg.dao.ProfileDAO;
import com.fg.vo.FeedVO;
import com.fg.vo.MemberVO;
import com.fg.vo.ProfileVO;

@Controller
public class ProfileController {
	ProfileDAO profile_dao;
	
	// 페이지에 따라 중간 경로들을 다르게 지정
	final static String PATH_MAIN	 = "main/";
	final static String PATH_FEED  	 = "feed/";
	final static String PATH_MEMBER	 = "member/";
	
	public ProfileController() {}
	
	public ProfileController(ProfileDAO profile_dao) {
		this.profile_dao = profile_dao;
	}

	// 프로필 내용 수정
	@RequestMapping("/profile/textupdate")
	public String profile_update_text ( @RequestParam("my_id") String my_id,
										@RequestParam("text") String text ) {
		
		// 내 아이디 + 수정할 내용 map에 담음
		Map<String, String> map = new HashMap<String, String>();
		map.put("my_id", my_id);
		map.put("text", text);
		
		// 프로필 내용 수정
		profile_dao.updateProfileText( map );
		
		return "redirect:/feed/mypageview";
	}
	
	
	// 프로필사진 수정
	@RequestMapping("/profile/photoupdate")
	public String profile_output( MultipartHttpServletRequest request ) {
		MultipartFile mf = request.getFile("profile_update");
		String path = request.getRealPath("/resources/profileimages/");	// 파일이 저장될 경로
		
		String fileName =  mf.getOriginalFilename();	  // 변경될 파일명
		String my_id 	= request.getParameter("my_id");  // 회원 아이디
		
		System.out.println( "파일이 저장될 경로 : " + path );
		System.out.println( "변경될 파일명 : " + fileName );
		System.out.println(" 수정할 회원 아이디 : " + my_id);
		
		File uploadFile = new File( path + fileName );
		
		try {
			mf.transferTo( uploadFile );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// vo 에 담아서 보냄
		ProfileVO vo = new ProfileVO();
		vo.setMember_id( my_id );
		vo.setProfile_image( fileName );
		
		// 프로필 사진 업데이트
		profile_dao.updateProfileImg( vo );
		
		return "redirect:/feed/mypageview";
	}
	
}
