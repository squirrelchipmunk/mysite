package com.javaex.dao;

import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

public class TestDao {

	public static void main(String[] args) {
		//UserDao test
		
		UserVo uVo = new UserVo(0, "id10","1234","황일영","male");
		UserDao uDao = new UserDao();

//		int count = uDao.insert(uVo);
//		System.out.println(count);
		

		
		
		//BoardDao test
		
		BoardDao bDao = new BoardDao();
		BoardVo bVo = new BoardVo();		
		
		//글쓰기
//		bVo.setTitle("제목");
//		bVo.setContent("내용");
//		bVo.setUserNo(1);
//		bDao.write(bVo);
		
		//글수정
//		bVo.setNo(1);
//		bVo.setTitle("제목입니다");
//		bVo.setContent("내용입니다");
//		bDao.modify(bVo);
		
		//글삭제
//		bDao.delete(1);
	}

}
