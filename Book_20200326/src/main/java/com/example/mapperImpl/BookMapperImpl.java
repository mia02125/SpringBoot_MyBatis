package com.example.mapperImpl;

import java.util.List;

import javax.inject.Inject;


import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
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
}
