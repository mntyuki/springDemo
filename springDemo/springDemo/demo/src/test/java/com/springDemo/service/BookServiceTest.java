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

import com.springDemo.dto.BookDto;
import com.springDemo.dto.EditBookDto;
import com.springDemo.entity.Book;
import com.springDemo.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

	@Mock
	BookRepository repository;

	@InjectMocks
	BookService bookService;

	@Test
	void DBから本一覧を表示する() {
		
		List<Book> books = List.of(new Book(), new Book());

		when(repository.findByDeletedFalse())
				.thenReturn(books);

		List<Book> result = bookService.findAll();
		
		assertEquals(2, result.size());
		
		 verify(repository).findByDeletedFalse();
	}

	@Test
	void 入力された本をDBに保存する() {
		
		BookDto dto = new BookDto();
		dto.setTitle("book");
		dto.setPrice(1000);
		
		bookService.insert(dto);
		verify(repository).save(any(Book.class));
	}

	@Test
	void 指定された本の編集ページを表示する() {
		
		Book book = new Book();
		book.setId(1L);
		book.setTitle("book");
		book.setPrice(1000);
		
		when(repository.findById(1L))
			.thenReturn(Optional.of(book));
		
		 EditBookDto result = bookService.getOneBook(1L);
		
		assertEquals("book", result.getTitle());
		assertEquals(1000, result.getPrice());
	}

	@Test
	void 本を編集して更新する() {
		
		EditBookDto dto = new EditBookDto();
		
		dto.setId(1L);
		dto.setTitle("title");
		dto.setPrice(1000);
		
		bookService.update(dto);
		
		
		verify(repository).save(any(Book.class));
	}

	
	@Test
	void 本を削除する() {
		Book book = new Book();
		
		book.setId(1L);
		book.setDeleted(false);
		
		when(repository.findById(1L))
			.thenReturn(Optional.of(book));
		
		bookService.delete(1L);
		
		assertTrue(book.isDeleted());
		
		verify(repository).save(any(Book.class));
	}

}
