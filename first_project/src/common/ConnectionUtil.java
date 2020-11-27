package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionUtil {
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private static Properties prop = null;
	
	static {
		prop = new Properties();
		
		File file = new File("res/db.properties");
		
		try {
			FileInputStream fis = new FileInputStream(file);
			
			prop.load(fis);
			
			Class.forName(prop.getProperty("driver"));
			
		} catch (FileNotFoundException e) {
			System.out.println("파일을 찾을 수 없습니다.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 접속 실패..");
			e.printStackTrace();
		}
	}
	/**
	 * DB연결
	 * @author 민태원
	 * @since 2020.11.17
	 */
	private void init(){
		try {
			conn = DriverManager.getConnection(
					prop.getProperty("url"),
					prop.getProperty("user"),
					prop.getProperty("pass"));
			stmt = conn.createStatement();
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
