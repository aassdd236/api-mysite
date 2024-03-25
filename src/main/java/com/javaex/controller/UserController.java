package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaex.service.UserService;
import com.javaex.util.JwtUtil;
import com.javaex.vo.UserVo;

import jakarta.servlet.http.HttpServletResponse;

@RestController //@ResponseBody를 안 적어도 됨
public class UserController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/api/users/login")
	public UserVo login(@RequestBody UserVo userVo, HttpServletResponse response) {
		System.out.println("UserController.login()");
		
		UserVo authUser = userService.exeLogin(userVo);
		
		if(authUser != null) {
			//토큰 발급 > 헤더에 실어보냄
			JwtUtil.createTokenAndSetHeader(response, ""+authUser.getNo());
		}

		return authUser;
	}
}
