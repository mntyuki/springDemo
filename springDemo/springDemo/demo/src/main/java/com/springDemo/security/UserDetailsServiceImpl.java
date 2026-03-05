package com.springDemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springDemo.entity.User;
import com.springDemo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/*認証時にユーザー情報を取得するクラス
 * 
 * usernameをキーにしてDB検索を行う
 * */

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	/*コンストラクタ*/
	@Autowired
	private final UserRepository userRepository;

	/*ユーザー名からユーザー情報を取得
	 * 
	 * @param username ログイン画面で入力されたユーザー名
	 * @return UserDetailsオブジェクトを返す
	 * @throws UsernameNotFoundException ユーザーが存在しない場合
	 * */

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		//DBからユーザーを検索
		User user = userRepository.findByUsername(username)
		.orElseThrow(()-> new UsernameNotFoundException("ユーザーが存在しません"));
		
	
		return new UserDetailsImpl(user);
	}

}
