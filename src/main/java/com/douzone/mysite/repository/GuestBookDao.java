package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {
	public GuestBookVo get(){
			GuestBookVo vo = new GuestBookVo();
			Connection conn =null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
		try {
			 conn = getConnection();
			 String sql = "select no,name,password,message,reg_date from guestbook order by reg_date desc limit 1";
			 pstmt=conn.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			 while(rs.next()) {
				 long no = rs.getLong(1);
				 String name = rs.getString(2);
				 String password = rs.getString(3);
				 String	message = rs.getString(4);
				 String regDate = rs.getString(5);
				 
				 
				 vo.setNo(no);
				 vo.setName(name);
				 vo.setPassword(password);
				 vo.setMessage(message);
				 vo.setRegDate(regDate);
				
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
	
	return vo;
	}
	
	
	public boolean select(GuestBookVo vo){
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long no = -1;
	try {
		 conn = getConnection();
		 String sql = "select no from guestbook where no="+vo.getNo()+" and password="+vo.getPassword() ;
		 pstmt=conn.prepareStatement(sql);
		 rs=pstmt.executeQuery();
		 while(rs.next()) {
			 no = rs.getLong(1);
			 vo.setNo(no);
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
	
	if(no == -1) {
		return false;
	}
	
	
		return true;
	
		
	}
	
	
	public Long getId() {
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long no = 0;
	try {
		 conn = getConnection();
		 String sql = "select last_insert_id()";
		 pstmt=conn.prepareStatement(sql);
		 rs=pstmt.executeQuery();
		 while(rs.next()) {
			no = rs.getLong(1);

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

	return no;
	}
	public boolean insert(GuestBookVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			 conn = getConnection();
			 String sql = "insert into guestbook values(null,?,?,?,now())";
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setString(1, vo.getName());
			 pstmt.setString(2, vo.getPassword());
			 pstmt.setString(3, vo.getMessage());
			 int count = pstmt.executeUpdate();
			 
			 //방금 들어간 row에 primary key 받아오는 방법
			 // " select last_insert_id()";
			 
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
	
		public List<GuestBookVo> getList(){
			List<GuestBookVo> list = new ArrayList<GuestBookVo>();
				Connection conn =null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
			try {
				 conn = getConnection();
				 String sql = "select no,name,password,message,reg_date from guestbook order by no desc";
				 pstmt=conn.prepareStatement(sql);
				 rs=pstmt.executeQuery();
				 while(rs.next()) {
					 long no = rs.getLong(1);
					 String name = rs.getString(2);
					 String password = rs.getString(3);
					 String	message = rs.getString(4);
					 String regDate = rs.getString(5);
					 
					 GuestBookVo vo = new GuestBookVo();
					 vo.setNo(no);
					 vo.setName(name);
					 vo.setPassword(password);
					 vo.setMessage(message);
					 vo.setRegDate(regDate);
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
		public List<GuestBookVo> getList(int page){
			List<GuestBookVo> list = new ArrayList<GuestBookVo>();
				Connection conn =null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
			try {
				 conn = getConnection();
				 String sql = "select no,name,password,message,reg_date from guestbook order by reg_date desc limit ?,5";
				 pstmt=conn.prepareStatement(sql);
				 pstmt.setInt(1,(page-1)*5);
				 rs=pstmt.executeQuery();
				 while(rs.next()) {
					 long no = rs.getLong(1);
					 String name = rs.getString(2);
					 String password = rs.getString(3);
					 String	message = rs.getString(4);
					 String regDate = rs.getString(5);
					 
					 GuestBookVo vo = new GuestBookVo();
					 vo.setNo(no);
					 vo.setName(name);
					 vo.setPassword(password);
					 vo.setMessage(message);
					 vo.setRegDate(regDate);
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
	public  boolean delete(GuestBookVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			 conn = getConnection();
			 String sql = "delete from guestbook where no=? and password=?";
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setLong(1, vo.getNo());
			 pstmt.setString(2, vo.getPassword());
			 int count = pstmt.executeUpdate();
			 result = count ==1;
			
		}catch (SQLException e) {
			System.out.println("error:" +e);
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();
				if(conn != null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return result;
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
}
