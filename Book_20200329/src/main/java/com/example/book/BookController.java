package com.example.book;

import java.time.LocalDateTime;
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
public class BookController {

	@Autowired
	private BookMapper bookMapper;
	
	@RequestMapping(value = "/input", method = RequestMethod.POST)
	@ResponseBody
	public void insertBook(HttpServletRequest request) throws Exception { 
		String bookName = request.getParameter("name");
		String bookPublisher = request.getParameter("publisher");
		String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		Book book = new Book();
		book.setName(bookName);
		book.setPublisher(bookPublisher);
		book.setUpdateDate(currentDate);
		bookMapper.insertBook(book);
	}
	@RequestMapping(value = "/update/{Id}", method = RequestMethod.POST)
	@ResponseBody
	public String updateBookName(HttpServletRequest request, @PathVariable(value = "Id") int Id) throws Exception { 
		String updateName = request.getParameter("updateName");
		String updatePublisher = request.getParameter("updatePublisher");
		Book book = new Book();
		book.setName(updateName);
		book.setPublisher(updatePublisher);
		bookMapper.updateBook(book);
		return "index";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteBook() throws Exception {
		bookMapper.deleteBook();
		return "redirect:/";
	}
	
	@RequestMapping(value = "/delete/{Id}", method = RequestMethod.POST)
	public String deleteBookOne(@PathVariable int Id) throws Exception {
		Book book = new Book();
		book.setId(Id);
		bookMapper.deleteBookOne(book);
		return "redirect:/";
	}
	
}
