package com.springDemo.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.springDemo.entity.User;

import lombok.RequiredArgsConstructor;

/*
 * springsecurity用のユーザー詳細クラス
 * userentityをspringsecurityが扱える形式へ変換する
 * */
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

	/*コンストラクタ*/
	private final User user;

	/*権限情報の返却
	 * 
	 * userからrole情報を取得してリストに追加して
	 * リストを返却
	 * 
	 * */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(
				new SimpleGrantedAuthority("ROLE_" + user.getRole()));
	}

	/*ハッシュ化されたパスワードを返却*/
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	/*ログインID(username)を取得*/
	@Override
	public String getUsername() {
		return user.getUsername();
	}

	/** 以下はアカウント状態（今は全て有効） */

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * 追加：ログイン中ユーザーのID取得用（便利）
	 */
	public Long getUserId() {
		return user.getId();
	}
}
