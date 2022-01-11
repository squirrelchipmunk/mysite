package com.javaex.dao;

import com.javaex.vo.UserVo;

public class TestDao {

	public static void main(String[] args) {
		UserVo vo = new UserVo(0, "id10","1234","황일영","male");
		UserDao dao = new UserDao();
		
		int count = dao.insert(vo);
		System.out.println(count);
	}

}
