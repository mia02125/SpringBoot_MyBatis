package com.example.mapper;

import java.util.List;


import com.example.model.Book;


public interface BookMapper {
	
	List<Book> getBookList() throws Exception;

	void insertBook(Book book) throws Exception;
	
	void deleteBook() throws Exception;

	void updateBook(Book book) throws Exception;

	void deleteBookOne(Book book) throws Exception;

	Book getBook(Book book);

	

	
	



	


}
