package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
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
	
	public int insert(UserVo vo) {
		int count=0;
		try {
			getConnection();

			String query ="";
			query += " insert into users ";
			query += " values(seq_users_no.nextval, ?, ?, ?, ?) " ;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getId() );    
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getGender());
			
			count = pstmt.executeUpdate();  
			System.out.println(count + " 건이 등록되었습니다.[UserDao]");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		close();
		return count;
	}

	public UserVo getUser(String id, String password) {
		UserVo vo = null;
		try {
			getConnection();

			String query ="";
			query += " select no,  ";
			query += " 		  name ";
			query += " from users ";
			query += " where id = ? and " ;
			query += " 		 password = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id );    
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();  
			while(rs.next()) {
				vo = new UserVo();
				vo.setNo(rs.getInt(1));
				vo.setName(rs.getString(2));
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		close();
		
		return vo; 
	}
	
	public UserVo getUserData(int no) {
		UserVo vo = null;
		try {
			getConnection();

			String query ="";
			query += " select id, ";
			query += " 		  password, ";
			query += " 		  name, ";
			query += " 		  gender ";
			query += " from users ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no );    
			
			rs = pstmt.executeQuery();  
			while(rs.next()) {
				vo = new UserVo(0, rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		close();
		
		return vo; 
	}
	
	public void modify(UserVo vo) {
		try {
			getConnection();

			String query ="";
			query += " update users ";
			query += " set password = ?, " ;
			query += "	   name = ?, " ;
			query += "	   gender = ? " ;
			query += " where id = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getPassword() );    
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getGender());
			pstmt.setString(4, vo.getId());
			
			int count = pstmt.executeUpdate();  
			System.out.println(count + " 건이 수정되었습니다.[UserDao]");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		close();
	}
	
	
}
