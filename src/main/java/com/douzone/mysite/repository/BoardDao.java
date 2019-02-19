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
import com.douzone.mysite.vo.GuestBookVo;
@Repository
public class BoardDao {
	public boolean insert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			 conn = getConnection();
			 String sql = "insert into board values(null,?,?,now(),?,?,?,?,?,?,'n')";
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setString(1, vo.getTitle());
			 pstmt.setString(2, vo.getContents());
			 pstmt.setInt(3, vo.getHit());
			 pstmt.setInt(4, vo.getgNo());
			 pstmt.setInt(5, vo.getoNo());
			 pstmt.setInt(6, vo.getDepth());
			 pstmt.setLong(7, vo.getUserNo());
			 pstmt.setString(8, vo.getFileName());
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
	
	
	public List<BoardVo> getList(int start,int end){
		List<BoardVo> list = new ArrayList<BoardVo>();
			Connection conn =null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
		try {
			 conn = getConnection();
			 String sql = "select a.no, a.title,a.contents,a.write_date,a.hit,a.g_no,a.o_no,a.depth,b.name,a.user_no,a.remove_check from board a, user b where a.user_no = b.no order by g_no desc, o_no asc limit ?,?";
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setInt(1, start);
			 pstmt.setInt(2, end);
			 rs=pstmt.executeQuery();
			 while(rs.next()) {
				 long no = rs.getLong(1);
				 String title = rs.getString(2);
				 String	contents = rs.getString(3);
				 String writeDate = rs.getString(4);
				 int hit = rs.getInt(5);
				 int gNo = rs.getInt(6);
				 int oNo = rs.getInt(7);
				 int depth = rs.getInt(8);
				 String userName = rs.getString(9);
				 long userNo = rs.getLong(10);
				 String removeCheck = rs.getString(11);
				 
				 BoardVo vo = new BoardVo();
				 vo.setNo(no);
				 vo.setTitle(title);
				 vo.setContents(contents);
				 vo.setWriteDate(writeDate);
				 vo.setHit(hit);
				 vo.setgNo(gNo);
				 vo.setoNo(oNo);
				 vo.setDepth(depth);
				 vo.setUserName(userName);
				 vo.setUserNo(userNo);
				 vo.setRemoveCheck(removeCheck);
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
	
	
	public List<BoardVo> getList(){
		List<BoardVo> list = new ArrayList<BoardVo>();
			Connection conn =null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
		try {
			 conn = getConnection();
			 String sql = "select a.no, a.title,a.contents,a.write_date,a.hit,a.g_no,a.o_no,a.depth,b.name,a.user_no from board a, user b where a.user_no = b.no order by g_no desc, o_no asc";
			 pstmt=conn.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			 while(rs.next()) {
				 long no = rs.getLong(1);
				 String title = rs.getString(2);
				 String	contents = rs.getString(3);
				 String writeDate = rs.getString(4);
				 int hit = rs.getInt(5);
				 int gNo = rs.getInt(6);
				 int oNo = rs.getInt(7);
				 int depth = rs.getInt(8);
				 String userName = rs.getString(9);
				 long userNo = rs.getLong(10);
				 
				 BoardVo vo = new BoardVo();
				 vo.setNo(no);
				 vo.setTitle(title);
				 vo.setContents(contents);
				 vo.setWriteDate(writeDate);
				 vo.setHit(hit);
				 vo.setgNo(gNo);
				 vo.setoNo(oNo);
				 vo.setDepth(depth);
				 vo.setUserName(userName);
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
	public List<BoardVo> getList(int start,int end,String search){
		List<BoardVo> list = new ArrayList<BoardVo>();
			Connection conn =null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
		try {
			 conn = getConnection();
			 String sql = "select DISTINCT a.no, a.title,a.contents,a.write_date,a.hit,a.g_no,a.o_no,a.depth,b.name,a.user_no,a.remove_check from board a, user b where a.remove_check='n' and a.user_no = b.no and (a.title like ? or b.name like ?) order by g_no desc, o_no asc limit ?,?";
			
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setString(1, "%"+search+"%");
			 pstmt.setString(2, "%"+search+"%");
			 pstmt.setInt(3, start);
			 pstmt.setInt(4, end);
			 rs=pstmt.executeQuery();
			 while(rs.next()) {
				 long no = rs.getLong(1);
				 String title = rs.getString(2);
				 String	contents = rs.getString(3);
				 String writeDate = rs.getString(4);
				 int hit = rs.getInt(5);
				 int gNo = rs.getInt(6);
				 int oNo = rs.getInt(7);
				 int depth = rs.getInt(8);
				 String userName = rs.getString(9);
				 long userNo = rs.getLong(10);
				 String removeCheck = rs.getString(11);
				 
				 BoardVo vo = new BoardVo();
				 vo.setNo(no);
				 vo.setTitle(title);
				 vo.setContents(contents);
				 vo.setWriteDate(writeDate);
				 vo.setHit(hit);
				 vo.setgNo(gNo);
				 vo.setoNo(oNo);
				 vo.setDepth(depth);
				 vo.setUserName(userName);
				 vo.setUserNo(userNo);
				 vo.setRemoveCheck(removeCheck);
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
	public List<BoardVo> getList(String search){
		List<BoardVo> list = new ArrayList<BoardVo>();
			Connection conn =null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
		try {
			 conn = getConnection();
			 String sql = "select DISTINCT a.no, a.title,a.contents,a.write_date,a.hit,a.g_no,a.o_no,a.depth,b.name,a.user_no from board a, user b where a.remove_check='n' and a.user_no = b.no and (a.title like ? or b.name like ?) order by g_no desc, o_no asc";
			
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setString(1, "%"+search+"%");
			 pstmt.setString(2, "%"+search+"%");

			 rs=pstmt.executeQuery();
			 while(rs.next()) {
				 long no = rs.getLong(1);
				 String title = rs.getString(2);
				 String	contents = rs.getString(3);
				 String writeDate = rs.getString(4);
				 int hit = rs.getInt(5);
				 int gNo = rs.getInt(6);
				 int oNo = rs.getInt(7);
				 int depth = rs.getInt(8);
				 String userName = rs.getString(9);
				 long userNo = rs.getLong(10);
				 
				 BoardVo vo = new BoardVo();
				 vo.setNo(no);
				 vo.setTitle(title);
				 vo.setContents(contents);
				 vo.setWriteDate(writeDate);
				 vo.setHit(hit);
				 vo.setgNo(gNo);
				 vo.setoNo(oNo);
				 vo.setDepth(depth);
				 vo.setUserName(userName);
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
	
	public  BoardVo getVo(long no){
		BoardVo vo = new BoardVo();
			Connection conn =null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
		try {
			 conn = getConnection();
			 String sql = "select  a.no,a.title,a.contents,a.write_date,a.hit,a.g_no,a.o_no,a.depth,b.name,a.user_no,a.file_name from board a, user b where a.user_no = b.no and a.no = ? order by g_no desc, o_no asc ";
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setLong(1, no);
			 rs=pstmt.executeQuery();
			 while(rs.next()) {
				  no = rs.getLong(1);
				 String title = rs.getString(2);
				 String	contents = rs.getString(3);
				 String writeDate = rs.getString(4);
				 int hit = rs.getInt(5);
				 int gNo = rs.getInt(6);
				 int oNo = rs.getInt(7);
				 int depth = rs.getInt(8);
				 String userName = rs.getString(9);
				 long userNo = rs.getLong(10);
				 String fileName = rs.getString(11);
				 vo.setNo(no);
				 vo.setTitle(title);
				 vo.setContents(contents);
				 vo.setWriteDate(writeDate);
				 vo.setHit(hit);
				 vo.setgNo(gNo);
				 vo.setoNo(oNo);
				 vo.setDepth(depth);
				 vo.setUserName(userName);
				 vo.setUserNo(userNo);
				 vo.setFileName(fileName);
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
	public  int getMaxGno(){
			int maxGno =0 ;
			Connection conn =null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
		try {
			 conn = getConnection();
			 String sql = "select max(g_no) from board";
			 pstmt=conn.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			 while(rs.next()) {
				 maxGno = rs.getInt(1);
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
	
	return maxGno;
}
	public  int getMaxono(int no){
		int maxGno =0 ;
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	try {
		 conn = getConnection();
		 String sql = "select max(o_no) from board where g_no = ?";
		 pstmt=conn.prepareStatement(sql);
		 pstmt.setInt(1, no);
		 rs=pstmt.executeQuery();
		 while(rs.next()) {
			 maxGno = rs.getInt(1);
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

return maxGno;
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


	public void update(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			String sql = 
				" update board" + 
				"  set title=?, contents=?, write_date=now()" + 
				" where no=? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1,vo.getTitle());
			pstmt.setString(2,vo.getContents());
			pstmt.setLong(3,vo.getNo());
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

	public void updateCheck(long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			String sql = 
				" update board" + 
				"  set remove_check=?" + 
				" where no=? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1,"y");
			pstmt.setLong(2,no);
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
	
	public void remove(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			 conn = getConnection();
			 String sql = "delete from board where no=?";
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


	public void update(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			String sql = 
				" update board" + 
				"  set hit=hit+1" + 
				" where no=? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1,no);
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
