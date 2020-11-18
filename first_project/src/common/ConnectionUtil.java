package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionUtil {
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	/**
	 * DB연결
	 * @author 민태원
	 * @since 2020.11.17
	 */
	private void init(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "jju";
			String password = "java";
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로딩실패");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속실패");
		}
	}
	
	/**
	 * DB연결해제
	 */
	public void destroy(ResultSet rs){
		try {
			if (rs != null) {
				rs.close();
			}
			if(this.rs!=null){
				this.rs.close();
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
	
	/**
	 * init()
	 * Read
	 * destroy() 호출 필요
	 */
	public ResultSet readDB(String sql){
		init();
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * init();
	 * Create, Update, Delete
	 * destroy();
	 */
	public int updateDB(String sql){
		init();
		int result = 0;
		try {
			result = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroy(rs);
		return result;
	}
}
