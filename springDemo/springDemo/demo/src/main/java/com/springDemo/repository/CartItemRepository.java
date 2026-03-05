package com.springDemo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springDemo.entity.CartItem;
import com.springDemo.entity.User;


public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
	/*カート内の表示に使用*/
	List<CartItem> findByUserAndDeletedFalse(User user);  //ユーザー情報からカート内の書籍を検索
	
	/*書籍をカート追加、削除に使用*/
	Optional<CartItem> findByUserAndBookId(User user, Long bookId);  //userとbookIdが一致するCartItemを返す。
}
