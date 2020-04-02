package com.example.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.dao.BookDAO;
import com.example.model.Book;



@Controller
public class BookController {

	@Autowired
	private BookDAO bookDAO;
	
	@RequestMapping(value = "/input", method = RequestMethod.POST)
	@ResponseBody
	public String insertBook(@RequestBody Book book) throws Exception { 
		// ajax에서 Post방식으로 Book이라는 객체의 Body에 데이터를 받아온다
		String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		book.setBookUpdateDate(currentDate);
		bookDAO.insertBook(book);
		return "redirect:/";
		/*
		 *  redirect : 
		 *  	1. CRUD에 적합
		 *  	2. 똑같은 요청정보가 여러분 수행되지않고 URL1이 아닌 URL2로 요청보냄
		 *  	3. 소량의 데이터 전달(get방식만 가능) 
		 *  forward : 
		 *  	1. 리스트, 검색에 적합
		 *  	2. 요청정보가 그대로 살아있기 때문에 똑같은 글이 여러번 등록 될 수 있다
		 *  	3. 대량의 데이터 전달  
		 */
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateBookName(@RequestBody Book book) throws Exception { 
		// @RequestBody가 없다면 Book객체의 데이터를 받지못함
		bookDAO.updateBook(book);
		return "redirect:/";
		/*
		 	redirect한 후 새로운 URL을 요청했고, controller에는
		 	RequestMapping value값인 error를 지정해주지않아서 404오류 
		 */
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
