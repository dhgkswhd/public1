package com.fg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	// 첫 페이지 (메인)
	@RequestMapping("/main")
	public String main() {
		
		System.out.println("main 시작");
		
		return "main/main";
	}
}
