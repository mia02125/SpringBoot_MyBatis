package com.example.book;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;




@Controller
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "index";
	}
	@RequestMapping(value = "/inputRequest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insert(HttpServletRequest request) {
		//JSON형태로 데이터를 보내려면 HashMap형태로 리턴해야함
		Map<String, Object> map = new HashMap<String, Object>();
		String bookName = request.getParameter("name");
		String bookPublisher = request.getParameter("publisher");
		String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		map.put("name", bookName);
		map.put("publisher", bookPublisher);
		map.put("updateDate", currentDate);
		return map;
	}	
}
