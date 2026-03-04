package com.springDemo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.springDemo.entity.User;
import com.springDemo.repository.UserRepository;
import com.springDemo.service.CartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CartController {

	@Autowired
	private final CartService cartService;
	private final UserRepository userRepository;

	/*
	 * カート詳細表示
	 * 
	 * @param principal ログイン中のユーザー
	 * @param model 画面へ送るカートの商品一覧
	 * 
	 * @return カート一覧へ
	 * */
	
	@GetMapping("/cart")
	public String viewCart(Model model, Principal principal) {

	    User user = userRepository
	            .findByUsername(principal.getName())
	            .orElseThrow(()-> new RuntimeException("ユーザーが存在しません"));

	    model.addAttribute("cartItems",
	            cartService.getCartItems(user));

	    model.addAttribute("total",
	            cartService.totalCalc(user));

	    return "cart";
	}
	
	

	/*
	 * カート内の書籍を削除
	 * */
	
	@PostMapping("/cart/delete/{bookId}")
	public String deleteCartItem(@PathVariable Long bookId, Principal principal) {
		
		User user = userRepository.findByUsername(principal.getName())
				.orElseThrow();
		
		cartService.deleteCartItem(user, bookId);
		
		return "redirect:cart";
	}
	
}
