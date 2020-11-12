package bookLGU;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import book.BookVO;

public class IBookLGUDaoImpl implements IBookLGUDao{
	
	private static IBookLGUDao dao;
	
	private IBookLGUDaoImpl() {
		
	}

	public static IBookLGUDao getInstance() {
		if(dao == null){
			dao = new IBookLGUDaoImpl();
		}
		return dao;
	}
	
	@Override
	public List<BookLGUVO> lguList() {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<BookLGUVO> list = new ArrayList<>();
		
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
			String sql = "SELECT * FROM BOOKLGU_VO";

			// 4. 결과
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				BookLGUVO vo = new BookLGUVO();
				vo.setBook_LGU(rs.getInt("book_LGU"));
				vo.setBook_theme(rs.getString("book_theme"));				
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
	public List<BookVO> themeList(int book_lgu) {
		
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
			String sql = "SELECT *"
					+ "FROM BOOKVO A, BOOKLGU_VO B "
					+ "WHERE A.BOOK_LGU = B.BOOK_LGU AND A.BOOK_LGU ='"+book_lgu+"'";

			// 4. 결과
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				BookVO vo = new BookVO();
				vo.setBook_id(rs.getInt("book_id"));
				vo.setBook_name(rs.getString("book_name"));
				vo.setBook_author(rs.getString("book_author"));
				vo.setBook_publisher(rs.getString("book_publisher"));
				vo.setBook_LGU(rs.getInt("book_LGU"));
				vo.setBook_summary(rs.getString("book_summary"));
				vo.setBook_state(rs.getString("book_state"));
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
	
}
