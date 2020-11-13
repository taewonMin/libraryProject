package book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class IBookDaoImpl implements IBookDao{
	
	private static IBookDao dao;
	
	private IBookDaoImpl() {
		
	}

	public static IBookDao getInstance() {
		if(dao == null){
			dao = new IBookDaoImpl();
		}
		return dao;
	}

	@Override
	public int createBook(BookVO bv) {
		int result = 0;
		Connection conn = null;
		Statement stmt = null;
	
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "jju";
			String password = "java";
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			String sql = "INSERT INTO BOOKVO"
						+ " VALUES (BOOK_SEQ.NEXTVAL,'"
						+ bv.getBook_author() +"','"
						+ bv.getBook_name() +"','"
						+ bv.getBook_summary() +"','"
						+ bv.getBook_publisher() +"','"
						+ bv.getBook_state() +"','"
						+ bv.getIsActivate() +"',"
						+ bv.getBook_LGU() +")";
			
			result = stmt.executeUpdate(sql);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 접속 실패");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속실패");
		}finally{
			try{
				if(stmt!=null){
					stmt.close();
				}if(conn!=null){
					conn.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@Override
	public BookVO readBook(int book_id){
		BookVO bv = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "jju";
			String password = "java";
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			String sql = "SELECT *"
						+ " FROM BOOKVO"
						+ " WHERE BOOK_ID ="+book_id;
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				bv = new BookVO();
				bv.setBook_id(rs.getInt("book_id"));
				bv.setBook_author(rs.getString("book_author"));
				bv.setBook_name(rs.getString("book_name"));
				bv.setBook_summary(rs.getString("book_summary"));
				bv.setBook_publisher(rs.getString("book_publisher"));
				bv.setBook_state(rs.getString("book_state"));
				bv.setIsActivate(rs.getString("isActivate"));
				bv.setBook_LGU(rs.getInt("book_lgu"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로드 실패");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속 실패");
		} finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bv;
	}
	
	@Override
	public List<BookVO> bookList() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		List<BookVO> bookList = new ArrayList<>();// null로 하면 안됨

		// 여기서 database가야함 여기서 쿼리준비
		try {
			// 디비한테 가야
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. 접속
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "jju";
			String password = "java";

			conn = DriverManager.getConnection(url, user, password);

			// 3. 질의
			stmt = conn.createStatement();
			String sql = "SELECT *" + " FROM BookVO";
			// 4. 결과
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				BookVO bv = new BookVO();// ///////

				bv.setBook_id(rs.getInt("book_id"));
				bv.setBook_name(rs.getString("book_name"));
				bv.setBook_author(rs.getNString("book_author"));
				bv.setBook_summary(rs.getNString("book_summary"));
				bv.setBook_publisher(rs.getString("book_publisher"));
				bv.setBook_LGU(rs.getInt("book_LGU"));
				bv.setBook_state(rs.getString("book_state"));
				bv.setIsActivate(rs.getString("isActivate"));

				bookList.add(bv);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로드실패");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속실패");
		}
		// 5. 반환

		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bookList;
	}
	
	
	@Override
	public List<BookVO> bookSearch(String book_name) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<BookVO> list = new ArrayList<>();
		
		try {
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. DB접속
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "jju";
			String password = "java";
			conn = DriverManager.getConnection(url, user, password);

			// 3. 질의
			stmt = conn.createStatement();
			String sql ="SELECT COUNT(BOOK_NAME),BOOK_ID,BOOK_AUTHOR,BOOK_NAME,BOOK_SUMMARY,"
					+" BOOK_PUBLISHER, BOOK_STATE, ISACTIVATE, BOOK_LGU"
					+" FROM BOOKVO "
					+" WHERE BOOK_NAME LIKE '%"+book_name+"%' "
					+" GROUP BY BOOK_ID,BOOK_AUTHOR,BOOK_NAME,BOOK_SUMMARY, "
					+" BOOK_PUBLISHER, BOOK_STATE, ISACTIVATE, BOOK_LGU";

			// 4. 결과
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				BookVO vo = new BookVO();
				vo.setBook_id(rs.getInt("book_id"));
				vo.setBook_name(rs.getString("book_name"));	
				vo.setBook_author(rs.getString("book_author"));
				vo.setBook_summary(rs.getString("book_summary"));
				vo.setBook_publisher(rs.getString("book_publisher"));
				vo.setBook_state(rs.getString("book_state"));
				vo.setIsActivate(rs.getString("isactivate"));
				list.add(vo);
			}
			

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("실패");
		}

		// 5. 반환
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ee) {
				ee.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public int updateBook(BookVO bv) {
		int result = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "jju";
			String password = "java";
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			String sql = "UPDATE BOOKVO"
						+ " SET"
						+ " BOOK_ID="+bv.getBook_id()+","
						+ " BOOK_AUTHOR='"+bv.getBook_author()+"',"
						+ " BOOK_NAME='"+bv.getBook_name()+"',"
						+ " BOOK_SUMMARY='"+bv.getBook_summary()+"',"
						+ " BOOK_PUBLISHER='"+bv.getBook_publisher()+"',"
						+ " BOOK_STATE='"+bv.getBook_state()+"',"
						+ " ISACTIVATE='"+bv.getIsActivate()+"',"
						+ " BOOK_LGU='"+bv.getBook_LGU()+"'"
						+ " WHERE BOOK_ID="+bv.getBook_id();
			
			result = stmt.executeUpdate(sql);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로드 실패");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속 실패");
		} finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public int deleteBook(int book_id) {
		int result = 0;
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "jju";
			String password = "java";
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			String sql = "DELETE BOOKVO" 
					   + " WHERE BOOK_ID =" + book_id;

			result = stmt.executeUpdate(sql);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로드 실패");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속 실패");
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
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

}
