package com.springDemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springDemo.dto.BookDto;
import com.springDemo.dto.EditBookDto;
import com.springDemo.entity.Book;
import com.springDemo.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class BookService {
	
	/*BookRepositoryをインスタンス化*/
	
	@Autowired
	BookRepository repository;
	
	
	/*
	 * DBから本の一覧を取得
	 * 
	 * @return DB内の本一覧
	 * */
	
	public List<Book> findAll() {
		return repository.findAll();
	}
	
	
	/*
	 * 入力された本をDBに保存する
	 */
	
	public void insert(BookDto bookDto) {
		
		/*DBに保存するデータを保持するインスタンス
		 * 
		 * データを保持するインスタンスはメソッド内で実体化する*/
		
		Book book = new Book();
		
		book.setTitle(bookDto.getTitle());
		book.setPrice(bookDto.getPrice());
		book.setPublishDate(bookDto.getPublishDate());
		book.setAuthor(bookDto.getAuthor());
		
		repository.save(book);
	}
	
	
	/*
	 * 受け取ったIDからデータを取得して編集画面を返却する
	 * */
	
	public EditBookDto getOneBook(Long id) {
		
		/*指定したidの書籍データを取得*/
		Book book = repository.findById(id).orElseThrow();
		
		/*書籍データを詰めた編集用画面を画面に表示*/
		EditBookDto editbookdto = new EditBookDto();
		
		editbookdto.setId(book.getId());
		editbookdto.setTitle(book.getTitle());
		editbookdto.setPrice(book.getPrice());
		editbookdto.setPublishDate(book.getPublishDate());
		editbookdto.setAuthor(book.getAuthor());
		
		return editbookdto;
		
	}
	
	
	/*
	 * 書籍内容を更新する
	 * */
	
	public void update(EditBookDto bookDto) {
		
		/*DBに登録する値を保持するインスタンスの作成*/
		Book book = new Book();
		
		/*画面から受け取った値をインスタンスに設定*/
		book.setId(bookDto.getId());
		book.setTitle(bookDto.getTitle());
		book.setPrice(bookDto.getPrice());
		book.setPublishDate(bookDto.getPublishDate());
		book.setAuthor(bookDto.getAuthor());
		
		/*変更箇所を保存*/
		repository.save(book);
	}
	
	
	/*
	 * 書籍の削除
	 * 
	 * */
	public void delete(Long id) {
		
		Book book = repository.findById(id)
				.orElseThrow(()-> new RuntimeException("書籍が見つかりません"));
		
		book.setDeleted(true);
		
		repository.save(book);
	
	}
	
}
