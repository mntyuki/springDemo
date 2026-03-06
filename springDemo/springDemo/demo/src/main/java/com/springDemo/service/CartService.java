package com.springDemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springDemo.entity.CartItem;
import com.springDemo.entity.User;
import com.springDemo.repository.BookRepository;
import com.springDemo.repository.CartItemRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartService {

	@Autowired
	private final CartItemRepository cartItemRepository;
	private final BookRepository bookRepository;

	/*書籍をカートへ追加
	 * 
	 * @param user ログイン中のユーザー
	 * @oaram bookId 書籍ID
	 * 
	 * */

	public void addCart(User user, Long bookId) {

		/*カートに値を設定*/
		Optional<CartItem> existing = cartItemRepository.findByUserAndBookId(user, bookId);

		if (existing.isPresent()) { //カートに同じ商品があるとき
			CartItem item = existing.get();

			if (item.isDeleted()) { //論理削除済みのとき
				item.setDeleted(false);

			} //カートに同じ商品があるとき
			item.setQuantity(item.getQuantity() + 1);
			cartItemRepository.save(item);

		} else { //カートに同じ商品がないとき
			CartItem newItem = new CartItem();
			newItem.setUser(user);
			newItem.setBook(bookRepository.findById(bookId).orElseThrow());
			newItem.setQuantity(1);
			cartItemRepository.save(newItem);

		}

	}
	/*カート一覧を表示*/

	public List<CartItem> getCartItems(User user) {
		return cartItemRepository.findByUserAndDeletedFalse(user);
	}

	/*カート内金額の算出*/

	public int totalCalc(User user) {

		/*
		 * stream 引数のuserのcartitemを一つずつ渡す
		 * 
		 * */
		return getCartItems(user).stream()

				//stream()で渡されたcartitemがitem->
				.mapToInt(item -> item.getBook().getPrice() * item.getQuantity())
				.sum();
	}

	/*カートから削除*/

	public void deleteCartItem(User user, Long bookId) {

		CartItem item = cartItemRepository.findByUserAndBookId(user, bookId)
				.orElseThrow(() -> new RuntimeException("カートに商品が見つかりません"));

		if (item.getQuantity() > 1) {
			item.setQuantity(item.getQuantity() - 1);
		} else {
			item.setDeleted(true);
		}
		cartItemRepository.save(item);
	}
}
