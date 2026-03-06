package com.springDemo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springDemo.entity.Book;
import com.springDemo.entity.CartItem;
import com.springDemo.entity.User;
import com.springDemo.repository.BookRepository;
import com.springDemo.repository.CartItemRepository;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

	@Mock
	CartItemRepository cartItemRepository;

	@Mock
	BookRepository bookRepository;

	@InjectMocks
	CartService cartService;

	@Test
	void カートに商品がない場合新規登録される() {

		User user = new User();
		Book book = new Book();
		book.setId(1L);

		when(cartItemRepository.findByUserAndBookId(user, 1L))
				.thenReturn(Optional.empty());

		when(bookRepository.findById(1L))
				.thenReturn(Optional.of(book));

		cartService.addCart(user, 1L);

		verify(cartItemRepository).save(any(CartItem.class));
	}
	
	@Test
	void 論理削除されている場合falseに変更されてカートに登録される() {
		
		User user = new User();
		CartItem item = new CartItem();
		item.setQuantity(1);
		item.setDeleted(true);
		
		when(cartItemRepository.findByUserAndBookId(user, 1L))
				.thenReturn(Optional.of(item));
		
		cartService.addCart(user, 1L);
		
		assertEquals(1, item.getQuantity());
		assertFalse(item.isDeleted());
		
		verify(cartItemRepository).save(item);
	}

	@Test
	void カートに商品がある場合は数量が増える() {

		User user = new User();
		CartItem item = new CartItem();
		item.setQuantity(1);
		item.setDeleted(false);

		when(cartItemRepository.findByUserAndBookId(user, 1L))
				.thenReturn(Optional.of(item));

		cartService.addCart(user, 1L);

		assertEquals(2, item.getQuantity());
		verify(cartItemRepository).save(item);
	}

	@Test
	void カートにある商品を一覧表示する() {
		User user = new User();

		CartItem item = new CartItem();

		when(cartItemRepository.findByUserAndDeletedFalse(user))
				.thenReturn(List.of(item));

		List<CartItem> result = cartService.getCartItems(user);

		assertEquals(1, result.size());

	}

	@Test
	void カートの合計金額を算出() {
		User user = new User();
		Book book = new Book();
		book.setPrice(1000);

		CartItem item = new CartItem();
		item.setQuantity(2);
		item.setBook(book);

		when(cartItemRepository.findByUserAndDeletedFalse(user))
				.thenReturn(List.of(item));

		int total = cartService.totalCalc(user);

		assertEquals(2000, total);
	}

	@Test
	void カートから商品を削除() {

		User user = new User();
		CartItem item = new CartItem();

		item.setQuantity(2);

		when(cartItemRepository.findByUserAndBookId(user, 1L))
				.thenReturn(Optional.of(item));

		cartService.deleteCartItem(user, 1L);

		assertEquals(1, item.getQuantity());
		verify(cartItemRepository).save(item);
	}

	@Test
	void カート商品の論理削除() {

		User user = new User();
		CartItem item = new CartItem();
		
		item.setQuantity(1);
		item.setDeleted(false);
		
		when(cartItemRepository.findByUserAndBookId(user, 1L))
			.thenReturn(Optional.of(item));
		
		cartService.deleteCartItem(user, 1L);
		
		assertTrue(item.isDeleted());
		verify(cartItemRepository).save(item);
	}

}
