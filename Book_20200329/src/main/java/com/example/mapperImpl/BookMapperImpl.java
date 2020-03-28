package com.example.mapperImpl;

import java.util.List;

import javax.inject.Inject;


import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.mapper.BookMapper;
import com.example.model.Book;


@Repository 
public class BookMapperImpl implements BookMapper{

	@Inject
	private SqlSession session;
	
	@Override
	public List<Book> getBookList() throws Exception {	
		session.getMapper(BookMapper.class);
		return session.selectList("com.example.mapper.BookMapper.getBookList");
	}	
	@Override
	public Book getBook(Book book) { 
		return session.selectOne("com.example.mapper.BookMapper.getBookList", book);
	}
	@Override
	public void insertBook(Book book) throws Exception {
		session.insert("com.example.mapper.BookMapper.insertBook", book);
	}
	@Override
	public void updateBook(Book book) throws Exception {
		session.update("com.example.mapper.BookMapper.updateBook", book);	
	}
	@Override
	public void deleteBook() throws Exception {
		session.delete("com.example.mapper.BookMapper.deleteBook");
	}
	@Override
	public void deleteBookOne(Book book) throws Exception {
		session.delete("com.example.mapper.BookMapper.deleteBookOne", book);
	}
	
}
