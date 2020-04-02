package com.example.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.dao.BookDAO;
import com.example.model.Book;

@Controller
public class HomeController {

/*
	1) select 만으로 ajax / post / get 방식으로 모두 해보고 
		나만의 주석 달기 (파라미터 전달에 따른 원리 확인) 
	2) 자세하게 주석 달기 
*/
	@Autowired
	private BookDAO bookDAO;

	@Autowired
	private HttpSession session;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) throws Exception {
		
		List<Book> bookList = bookDAO.getBookList();
		session.setAttribute("bookList", bookList);
		model.addAttribute("bookList", bookList);
		
		return "index";
	}
	/* 
	 * 오류 내용 : 심각: org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to be returned by selectOne(), but found: 5] with root cause
	 * => 값이 많아지면 오류가 생김  
	 */
	
	@RequestMapping(value = "/detail/{bookId}", method = RequestMethod.GET)
	public String detail(@PathVariable(value = "bookId") int bookId ,Model model) throws Exception {
		Book book = new Book();
		book.setBookId(bookId);
		Book books = bookDAO.getBook(book);
		model.addAttribute("bookDetail", books);
		return "detail";
	}
	@RequestMapping(value = "/update/{bookId}", method = RequestMethod.GET)
	public String update() throws Exception {
		// @PathVariable : get방식만 가능 (why : ) 
		return "update";
	}
	
//	
//	@RequestMapping(value = "/inputRequest", method = RequestMethod.POST)
//	@ResponseBody
//	public List<Map<String, Object>> insert(HttpServletRequest request, RedirectAttributes rttr) {
//		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//		Map<String, Object> map = new HashMap<String, Object>();
//		String bookName = request.getParameter("name");
//		String bookPublisher = request.getParameter("publisher");
//		String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//		map.put("name", bookName);
//		map.put("publisher", bookPublisher);
//		map.put("updateDate", currentDate);
//		list.add(map);
//		return list;
//	}
}
