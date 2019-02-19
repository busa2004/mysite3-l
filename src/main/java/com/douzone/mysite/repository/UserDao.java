package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.UserDaoException;
import com.douzone.mysite.vo.UserVo;
@Repository
public class UserDao {
	
	public int insert(UserVo vo) throws UserDaoException {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = 
				" insert" + 
				"   into user" + 
				" values ( null, ?, ?, ?, ?, now() )";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new UserDaoException("사용정보 저장 실패");
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new UserDaoException("회원정보 저장 실패");
			}
		}

		return count;
	}
	
	
	public UserVo get(String email,String password) {
		UserVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			String sql = 
				" select no,name" + 
				"   from user" + 
				" where email=? and password=? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1,email);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();
			if(rs.next()) {
				long no = rs.getLong(1);
				String name = rs.getString(2);
				result = new UserVo();
				result.setNo(no);
				result.setName(name);
				
			}
			
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public UserVo get(String email) {
		UserVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			String sql = 
				" select no,name" + 
				"   from user" + 
				" where email=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1,email);

			rs = pstmt.executeQuery();
			if(rs.next()) {
				long no = rs.getLong(1);
				String name = rs.getString(2);
				result = new UserVo();
				result.setNo(no);
				result.setName(name);
				
			}
			
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public UserVo get(long no) {
		UserVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			String sql = 
				" select email,gender" + 
				"   from user" + 
				" where no=? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1,no);
			

			rs = pstmt.executeQuery();
			if(rs.next()) {
				String email = rs.getString(1);
				String gender  = rs.getString(2);
				result = new UserVo();
				result.setEmail(email);
				result.setGender(gender);
				
			}
			
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			//1. 드라이버 로딩
			Class.forName( "com.mysql.jdbc.Driver" );
			
			//2. 연결하기
			String url="jdbc:mysql://localhost/webdb?characterEncoding=utf8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch( ClassNotFoundException e ) {
			System.out.println( "드러이버 로딩 실패:" + e );
		} 
		
		return conn;
	}


	public void update(long no, String name, String password,String gender) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			String sql = 
				" update user" + 
				"  set name=?, password=?,gender=?" + 
				" where no=? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1,name);
			pstmt.setString(2,password);
			pstmt.setString(3, gender);
			pstmt.setLong(4,no);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		
		
	}	
}