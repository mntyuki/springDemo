package com.springDemo.entity;



import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "booksinfo")

public class Book {
	
	
	//本のID
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id; 
	
	
	//タイトル
	@Column(name="title")
	private String title;
	
	
	//値段
	@Column(name="price")
	private int price;
	
	
	//発売日
	@Column(name="publish_date")
	private LocalDate publishDate;
	
	
	//著者名
	@Column(name="author")
	private String author;
	
	
	/*論理削除*/
	@Column(nullable = false)
	private boolean deleted = false;
	
}
