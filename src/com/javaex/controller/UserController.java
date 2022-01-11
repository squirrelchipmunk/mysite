package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println(action);

		if ("joinForm".equals(action)) {
			WebUtil.forward(request, response, "WEB-INF/views/user/joinForm.jsp");
		}
		else if ("join".equals(action)) {
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			UserVo uVo = new UserVo(0, id, password, name, gender);
			new UserDao().insert(uVo);

			WebUtil.forward(request, response, "WEB-INF/views/user/joinOk.jsp");
		} else if ("loginForm".equals(action)) {
			WebUtil.forward(request, response, "WEB-INF/views/user/loginForm.jsp");

		} else {

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
