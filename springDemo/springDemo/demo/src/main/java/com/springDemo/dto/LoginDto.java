package com.springDemo.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginDto {

	/*ユーザー名*/
	@NotBlank(message = "ユーザー名を入力してください")
	private String username;
	
	/*パスワード*/
	@NotBlank(message = "パスワードを入力してください")
	private String password;
}
