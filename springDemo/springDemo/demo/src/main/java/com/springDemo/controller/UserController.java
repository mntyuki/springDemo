package com.springDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.springDemo.dto.UserDto;
import com.springDemo.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	/*登録画面の表示*/
	@GetMapping("/userregister")
	public String showRegister(Model model) {
		
		model.addAttribute("userDto", new UserDto());
		return "/userregister";
	}
	
	
	/*
	 * 新規ユーザー登録
	 * 
	 * @result エラーを保持
	 * */
	@PostMapping("/register")
	public String register(@Validated @ModelAttribute UserDto userDto, BindingResult result, Model model) {

		//画面入力でエラーを保持する場合
		if (result.hasErrors()) {
			return "/userregister";
		}
		
		//throwされた例外判定（DBでユーザーの重複チェック）
		try {
			userService.register(userDto);
		} catch (IllegalArgumentException e) {
			
			//フィ―ルド名、エラーコード、表示メッセージ（エラーメッセージの投入）
		result.rejectValue("username","error.username", e.getMessage());  //例外発生時の処理
			return "/userregister";
		}
		
		//登録情報を画面に表示
		model.addAttribute("username", userDto.getUsername());
		model.addAttribute("password", userDto.getPassword());
		return "/registercomplete";
	}
}
