package com.springDemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springDemo.entity.Book;

/*DB接続用のインターフェース
 * 
 * @pram Book DB内のBookデータ
 * @pram Integer BookのID
 *  */

public interface BookRepository extends JpaRepository<Book, Long> {
	
	List<Book> findByDeletedFalse();
	List<Book> findByTitleContaining(String keyword);
}
