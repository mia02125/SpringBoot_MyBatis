package com.example.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.model.Book;


@Repository 
public class BookDAO {

	@Inject
	private SqlSession session;
	
	/*
	 * 오류내용 :  Mapped Statements collection does not contain value for com.example.dao.BookDAO.getBookList] with root cause
	 * => mapper.xml의 namespace 이름이 동일한지 확인
	 */
	public List<Book> getBookList()  {	
		return session.selectList("com.example.dao.BookDAO.getBookList");
	}	
	
	public Book getBook(Book book) { 
		return session.selectOne("com.example.dao.BookDAO.getBook", book);
	}
	
	public void insertBook(Book book) throws Exception {
		session.insert("com.example.dao.BookDAO.insertBook", book);
	}
	
	public void updateBook(Book book) throws Exception {
		session.update("com.example.dao.BookDAO.updateBook", book);	
	}
	
	public void deleteBook() throws Exception {
		session.delete("com.example.dao.BookDAO.deleteBook");
	}
	
	public void deleteBookOne(Book book) throws Exception {
		session.delete("com.example.dao.BookDAO.deleteBookOne", book);
	}
	
}
