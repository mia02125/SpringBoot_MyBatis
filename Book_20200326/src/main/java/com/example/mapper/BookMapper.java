package com.example.mapper;

import java.util.List;



import com.example.model.Book;


public interface BookMapper {
	
	List<Book> getBookList() throws Exception;
	

}
