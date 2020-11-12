package rental;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IRentalDaoImpl implements IRentalDao{
	private static IRentalDao dao;
	
	private IRentalDaoImpl() {
		
	}

	public static IRentalDao getInstance() {
		if(dao == null){
			dao = new IRentalDaoImpl();
		}
		return dao;
	}
	
	@Override
	public RentalVO createRental(Map<String, Object> map) {
		RentalVO rv = new RentalVO();
		int result = 0;
		rv.setMem_id((String)map.get("mem_id"));
		rv.setBook_id((int)map.get("book_id"));
		rv.setRental_start((String)map.get("start_date"));
		rv.setRental_end((String)map.get("end_date"));
		
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "jju";
			String password = "java";
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			String sql = "INSERT INTO RENTALVO"
						+ " VALUES(rent_seq.nextval,'"
						+ rv.getRental_start() + "','"
						+ rv.getRental_end() + "','"
						+ rv.getBook_id() + "','"
						+ rv.getMem_id() + "')";
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
		if(result == 0){
			rv = null;
		}    
		return rv;
	}

	@Override
	public RentalVO readRentalVO(int book_id) {
		RentalVO rv = null;
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
						+ " FROM RENTALVO"
						+ " WHERE BOOK_ID ='"+book_id+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				rv = new RentalVO();
				rv.setRental_id(rs.getInt("rental_id"));
				rv.setRental_start(rs.getString("rental_start"));
				rv.setRental_end(rs.getString("rental_end"));
				rv.setBook_id(rs.getInt("book_id"));
				rv.setMem_id(rs.getString("mem_id"));
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
		return rv;
	}

	@Override
	public List<RentalVO> readRentalList(String mem_id) {
		List<RentalVO> rvList = new ArrayList<>();
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
						+ " FROM RENTALVO"
					   + " WHERE MEM_ID ='"+mem_id+"'";
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				RentalVO rv = new RentalVO();
				rv.setRental_id(rs.getInt("rental_id"));
				rv.setRental_start(rs.getString("rental_start"));
				rv.setRental_end(rs.getString("rental_end"));
				rv.setBook_id(rs.getInt("book_id"));
				rv.setMem_id(rs.getString("mem_id"));
				rvList.add(rv);
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
		return rvList;
	}

	@Override
	public int deleteRental(int book_id) {
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
			String sql = "DELETE RENTALVO"
					   + " WHERE BOOK_ID ="+book_id;
			
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
