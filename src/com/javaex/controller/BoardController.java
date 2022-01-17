package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
//		System.out.println(action);
		
		//게시글 보기
		if("read".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no")); // 게시글 번호
			new BoardDao().incHit(no);
			BoardVo postVo = new BoardDao().getPost(no);
			
			request.setAttribute("post", postVo);
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		}
		
		//게시글 삭제
		else if("delete".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			new BoardDao().delete(no);
			
			WebUtil.redirect(request, response, "/mysite/board");
		}
		
		//게시글 쓰기 화면
		else if("writeForm".equals(action)) {
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			if(authUser != null) {
				WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			}
			else { // 잘못된 접근 처리
				WebUtil.redirect(request, response, "/mysite/main");
			}
				
		}
		
		//게시글 쓰기
		else if("write".equals(action)) {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			HttpSession session = request.getSession();
			UserVo user = (UserVo)session.getAttribute("authUser");
			int userNo = user.getNo();
			
			BoardVo postVo = new BoardVo();
			postVo.setUserNo(userNo);
			postVo.setTitle(title);
			postVo.setContent(content);
			
			new BoardDao().write(postVo);
			
			WebUtil.redirect(request, response, "/mysite/board");
		}
		
		//게시글 수정 화면
		else if("modifyForm".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			BoardVo postVo = new BoardDao().getPost(no);
			
			request.setAttribute("post", postVo);
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		}
		
		//게시글 수정
		else if("modify".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			BoardVo postVo = new BoardVo();
			postVo.setNo(no);
			postVo.setTitle(title);
			postVo.setContent(content);
			
			new BoardDao().modify(postVo);
			
			WebUtil.redirect(request, response, "/mysite/board");
		}
		
		//게시글 리스트
		else {
			List<BoardVo> postList = new BoardDao().getPostList();
			
			request.setAttribute("pList", postList);
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}
