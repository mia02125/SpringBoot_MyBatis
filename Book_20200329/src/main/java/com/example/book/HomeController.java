package com.example.book;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.mapper.BookMapper;
import com.example.model.Book;

@Controller
public class HomeController {

	@Autowired
	private BookMapper bookMapper;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) throws Exception {
		List<Book> bookList = bookMapper.getBookList();
		model.addAttribute("bookList", bookList);
		return "index";
	}
	/* 
	 * 오류 내용 : 심각: org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to be returned by selectOne(), but found: 5] with root cause
	 * => 값이 많아지면 오류가 생김  
	 */
	
	@RequestMapping(value = "/detail/{Id}", method = RequestMethod.GET)
	public String detail(@PathVariable int Id ,Model model) throws Exception {
		Book book = new Book();
		book.setId(Id);
		Book books = bookMapper.getBook(book);
		model.addAttribute("bookDetail", books);
		return "detail";
	}
	@RequestMapping(value = "/update/{Id}", method = RequestMethod.GET)
	public String update(@PathVariable int Id) throws Exception { 
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
