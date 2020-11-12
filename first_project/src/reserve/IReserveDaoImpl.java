package reserve;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IReserveDaoImpl implements IReserveDao{
	
	private static IReserveDao dao;
	
	private IReserveDaoImpl() {
		
	}

	public static IReserveDao getInstance() {
		if(dao == null){
			dao = new IReserveDaoImpl();
		}
		return dao;
	}

	@Override
	public int createReserve(Map<String, Object> rsvMap) {
		int result = 0;
		ReserveVO rsv = new ReserveVO();
		rsv.setMem_id((String)rsvMap.get("mem_id"));
		rsv.setBook_id((int)rsvMap.get("book_id"));
		
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "jju";
			String password = "java";
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			String sql = "INSERT INTO RESERVEVO"
						+ " VALUES(rsv_seq.nextval,'"
						+ rsv.getBook_id() + "','"
						+ rsv.getMem_id() + "')";
			result = stmt.executeUpdate(sql);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로드 실패");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속 실패");
		} finally{
			try {
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
	public List<ReserveVO> readReserveList(String mem_id) {
		List<ReserveVO> rsvList = new ArrayList<>();
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
						+ " FROM RESERVEVO"
					   + " WHERE MEM_ID ='"+mem_id+"'";
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				ReserveVO rsv = new ReserveVO();
				rsv.setRsv_no(rs.getInt("rsv_no"));
				rsv.setBook_id(rs.getInt("book_id"));
				rsv.setMem_id(rs.getString("mem_id"));
				rsvList.add(rsv);
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
		return rsvList;
	}

	@Override
	public ReserveVO readReserveVO(int book_id) {
		ReserveVO rsv = null;
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
						+ " FROM RESERVEVO"
						+ " WHERE BOOK_ID ='"+book_id+"'";
			
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				rsv = new ReserveVO();
				rsv.setRsv_no(rs.getInt("rsv_no"));
				rsv.setBook_id(rs.getInt("book_id"));
				rsv.setMem_id(rs.getString("mem_id"));
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
		return rsv;
	}

	@Override
	public int deleteReserve(Map<String, Object> newRsv) {
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
			String sql = "DELETE RESERVEVO"
					   + " WHERE MEM_ID ='"+(String)newRsv.get("mem_id")+"'"
				   		 + " AND BOOK_ID ="+(int)newRsv.get("book_id");
			
			result = stmt.executeUpdate(sql);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로드 실패");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속 실패");
		} finally{
			try {
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

}
