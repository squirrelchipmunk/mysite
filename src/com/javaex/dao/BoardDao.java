package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs= null;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	private void getConnection(){
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
		}catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	private void close() {
		try {               
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	public List<BoardVo> getPostList() {
		List<BoardVo> postList = new ArrayList<>();
		
		try {
			getConnection();

			String query ="";
			query += " select b.no pno, ";
			query += " 	 	  title, ";
			query += " 		  content, ";
			query += " 		  hit, ";
			query += " 		  to_char(reg_date, 'yy-mm-dd hh24:mi') reg_date, ";
			query += " 		  u.no uno,";
			query += " 		  name ";
			query += " from users u, board  b";
			query += " where u.no = b.user_no ";
			query += " order by reg_date desc ";
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();  
			
			while(rs.next()) { //			  
				BoardVo postVo = new BoardVo( rs.getInt("pno"), rs.getString("title"), rs.getString("content"),
						rs.getInt("hit"), rs.getString("reg_date"), rs.getInt("uno"), rs.getString("name"));
				postList.add(postVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		close();
			
		return postList;
	}
	
	// 게시글(vo) 가져오기
	// isRead  --> 게시글 보기 vo
	// !isRead --> 수정폼 기본값 vo
	public BoardVo getPost(int no, boolean isRead) {
		BoardVo postVo = null;
		try {
			getConnection();

			String query ="";
			
			// 게시글 보기 조회수 +1
			if(isRead) {
				query += " update board ";
				query += " set hit = hit+1 ";
				query += " where no = ? ";

				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, no);
				pstmt.executeUpdate();
			}
			
			query ="";
			query += " select b.no bno, ";
			query += " 		  title, ";
			query += " 		  content, ";
			query += " 		  hit, ";
			query += " 		  to_char(reg_date, 'yy-mm-dd hh24:mi') reg_date, ";
			query += " 		  u.no uno,";
			query += " 		  name ";
			query += " from users u, board  b";
			query += " where u.no = b.user_no and ";
			query += " 		 b.no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();  
			
			while(rs.next()) { //
				int bno = rs.getInt("bno");
				String title =  rs.getString("title");
				String content = rs.getString("content");
				if(isRead) // 게시글 보기 html 공백, 개행 처리
					content = content.replace(" ", "&nbsp;").replace("\n", "<br>");
				int hit = rs.getInt("hit");
				String regDate =  rs.getString("reg_date");
				int uno = rs.getInt("uno");
				String writer = rs.getString("name");
				
				postVo = new BoardVo( bno,title, content, hit, regDate, uno, writer);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		close();
		
		return postVo;
	}

	
	public void write(BoardVo postVo) {

		try {
			getConnection();

			String query ="";
			query += " insert into board ";
			query += " values(seq_board_no.nextval, ?, ?, 0,  sysdate, ?) " ;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, postVo.getTitle());    
			pstmt.setString(2, postVo.getContent());
			pstmt.setInt(3, postVo.getUserNo());
			
			int count = pstmt.executeUpdate();  
			System.out.println(count + " 건이 등록되었습니다.[board]");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		close();
	}

	public void delete(int no) {
		try {
			getConnection();

			String query ="";
			query += " delete from board ";
			query += " where no = ? " ;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			int count = pstmt.executeUpdate();  
			System.out.println(count + " 건이 삭제되었습니다.[board]");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		close();
		
	}

	public void modify(BoardVo vo) {
		try {
			getConnection();

			String query ="";
			query += " update board ";
			query += " set title = ?, ";
			query += " 	   content = ? ";
			query += " where no = ? " ;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNo());
			
			int count = pstmt.executeUpdate();  
			System.out.println(count + " 건이 삭제되었습니다.[board]");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		close();
	}
}
