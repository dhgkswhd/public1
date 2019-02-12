package com.fg.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fg.dao.FriendsDAO;
import com.fg.vo.FriendsApplyVO;
import com.fg.vo.FriendsVO;
import com.fg.vo.MemberVO;

@Controller
public class FriendsController {
	FriendsDAO friends_dao;
	
	final static String VIEW_PATH = "/WEB-INF/views/"; // 접두 경로
	
	// 페이지에 따라 중간 경로들을 다르게 지정
	final static String PATH_MAIN = "main/";
	final static String PATH_FEED = "feed/";
	final static String PATH_MEMBER = "member/";
	
	public FriendsController() {}
	
	public FriendsController( FriendsDAO friends_dao ) {
		this.friends_dao = friends_dao;
	}
	
}
