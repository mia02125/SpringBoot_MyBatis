package com.example.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.dao.BookDAO;
import com.example.model.Book;



@Controller
public class BookController {

	@Autowired
	private BookDAO bookDAO;
	
	@RequestMapping(value = "/input", method = RequestMethod.POST)
	public String insertBook(HttpServletRequest request) throws Exception { 
		String bookName = request.getParameter("name");
		String bookPublisher = request.getParameter("publisher");
		String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		Book book = new Book();
		book.setBookName(bookName);
		book.setBookPublisher(bookPublisher);
		book.setBookUpdateDate(currentDate);
		bookDAO.insertBook(book);
		return "redirect:/";
	}
	@RequestMapping(value = "/update/{bookId}")
//	public String updateBookName(HttpServletRequest request, @PathVariable(value = "bookId") int bookId) throws Exception { 
	public String updateBookName(HttpServletRequest request, Book book) throws Exception { 
		String updateName = request.getParameter("updateName");
		String updatePublisher = request.getParameter("updatePublisher");
		// 문제점 : 수정된 데이터값이 결과값으로 나오지않음
		book.setBookName(updateName);
		book.setBookPublisher(updatePublisher);
		bookDAO.updateBook(book);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteBook() throws Exception {
		bookDAO.deleteBook();
		return "redirect:/";
	}
	
	@RequestMapping(value = "/delete/{bookId}", method = RequestMethod.POST)
	public String deleteBookOne(@PathVariable(value = "bookId") int bookId, Book book) throws Exception {
		book.setBookId(bookId);
		bookDAO.deleteBookOne(book);
		return "redirect:/";
	}
	
}
