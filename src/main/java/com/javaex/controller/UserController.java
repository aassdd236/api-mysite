package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaex.service.UserService;
import com.javaex.util.JsonResult;
import com.javaex.util.JwtUtil;
import com.javaex.vo.UserVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController //@ResponseBody를 안 적어도 됨
public class UserController {
	
	@Autowired
	private UserService userService;

	//로그인
	@PostMapping("/api/users/login")
	public JsonResult login(@RequestBody UserVo userVo, HttpServletResponse response) {
		System.out.println("UserController.login()");
		
		UserVo authUser = userService.exeLogin(userVo);
		
		if(authUser != null) {
			//토큰 발급 > 헤더에 실어보냄
			JwtUtil.createTokenAndSetHeader(response, ""+authUser.getNo());
			return JsonResult.success(authUser);
		}else {
			return JsonResult.fail("로그인 실패");
		}
	}
	
	//회원정보 수정폼
	@GetMapping("/api/users/modify")
	public JsonResult modifyform(HttpServletRequest request) {
		System.out.println("UserController.modifyform()");
		
		/*
		//토큰
		String token = JwtUtil.getTokenByHeader(request);
		System.out.println("token: "+token);
		
		//검증
		boolean check = JwtUtil.checkToken(token);
		System.out.println(check);
		
		//이상없음
		if(check == true) {
			System.out.println("정상");
			int no =Integer.parseInt(JwtUtil.getSubjectFromToken(token));
			System.out.println(no);
		} > 세 가지 일을 줄여서 만든 메소드 하나 사용
		*/
		
		//no값 추출
		int no = JwtUtil.getNoFromHeader(request);
		if(no != -1) {
			//정상일 때
			UserVo userVo = userService.exeModifyForm(no);
			return JsonResult.success(userVo);
		}else {
			//토큰이 없거나(로그인 x) 변조된 경우
			return JsonResult.fail("실패");
		}

	}
	
	//회원정보 수정
	@PutMapping("/api/users/modify")
	public JsonResult modify(@RequestBody UserVo userVo, HttpServletRequest request) {
		System.out.println("UserController.modify()");
		
		int no = JwtUtil.getNoFromHeader(request);
		if(no != -1) {
			//db에서 수정
			int count = userService.exeModify(userVo);
			
			return JsonResult.success(userVo.getName());
		}else {
			return JsonResult.fail("토큰 오류");
		}

	}
}
