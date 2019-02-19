package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;
@Repository
public class CommentDao {

	public List<CommentVo> getList(long no){
		List<CommentVo> list = new ArrayList<CommentVo>();
			Connection conn =null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
		try {
			 conn = getConnection();
			 String sql = "select a.no,b.name,a.contents,a.regDate,a.user_no from comment a,user b where b.no=a.user_no and board_no =?;";
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setLong(1,no);
			 rs=pstmt.executeQuery();
			 while(rs.next()) {
				 long no2 = rs.getLong(1);
				 String name = rs.getString(2);
				 String	contents = rs.getString(3);
				 String regDate = rs.getString(4);
				 long userNo= rs.getLong(5);
				 
				 
				 CommentVo vo = new CommentVo();
				 vo.setNo(no2);
				 vo.setName(name);
				 vo.setContents(contents);
				 vo.setRegDate(regDate);
				 vo.setUserNo(userNo);
				 list.add(vo);
			 }
			 
		} catch (SQLException e) {
			System.out.println("error:"+ e);
		}finally {
		
				try {
					if(rs != null) {
						rs.close();
					}
					if(pstmt!=null) {
						pstmt.close();
					}
					if(conn!=null) {
						conn.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	
	return list;
}
	
	private  Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
		Class.forName("com.mysql.jdbc.Driver");
		//2. 연결하기(Connection 객체 얻어보기)
		String url = "jdbc:mysql://localhost:3306/webdb";
		conn = DriverManager.getConnection(url,"webdb","webdb");
		} catch (ClassNotFoundException e) { 
			System.out.println("드라이버 로딩 실패");
		}
		
		return conn;
	}

	public boolean insert(CommentVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			 conn = getConnection();
			 String sql = "insert into comment values(null,?,now(),?,?)";
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setString(1, vo.getContents());
			 pstmt.setLong(2, vo.getBoardNo());
			 pstmt.setLong(3, vo.getUserNo());
			 int count = pstmt.executeUpdate();
			 result = count ==1;
		} catch (SQLException e) {
			System.out.println("error:"+ e);
		}finally {
			
				try {
					
					if(pstmt!=null) {
						pstmt.close();
					}
					if(conn!=null) {
					conn.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
		return result;
	}

	public void delete(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			 conn = getConnection();
			 String sql = "delete from comment where no=?";
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setLong(1, no);
			 pstmt.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println("error:" +e);
		} finally {
			try {
				if(pstmt!=null)
					pstmt.close();
				if(conn != null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	public void deleteAll(long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			 conn = getConnection();
			 String sql = "delete from comment where board_no=?";
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setLong(1, no);
			 pstmt.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println("error:" +e);
		} finally {
			try {
				if(pstmt!=null)
					pstmt.close();
				if(conn != null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}

}
