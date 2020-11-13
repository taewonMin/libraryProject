package admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class IAdminDaoImpl implements IAdminDao{
	
	private static IAdminDao dao;
	
	private IAdminDaoImpl() {
		
	}

	public static IAdminDao getInstance() {
		if(dao == null){
			dao = new IAdminDaoImpl();
		}
		return dao;
	}
	
	
	/**
	 * 관리자로그인
	 * @param 관리자아이디와 비밀번호
	 * @return adimnvo 
	 * @author 조애슬
	 * @since 2020-11-13
	 */
	@Override
	public AdminVO adminMatch(Map<String, String> params) {
		
		String mem_id = params.get("mem_id");
		String mem_pw = params.get("mem_pw");
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		AdminVO av = null;
		
		//여기서 database가야함 여기서 쿼리준비
		try {
			//디비한테 가야
			//1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//2. 접속
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "jju";
			String password = "java";
			
			conn = DriverManager.getConnection(url, user, password);
			//3. 질의
			stmt = conn.createStatement();
			String sql = "SELECT ADMIN_ID, ADMIN_PW "//memvo 받아갈거니까 전체
					+ " FROM ADMINVO"
					+ " WHERE ADMIN_ID = '"+mem_id+"'"
					+ " AND ADMIN_PW = '"+mem_pw+"'";
			//4. 결과
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				av = new AdminVO();
				av.setadmin_id(rs.getString("ADMIN_ID"));
				av.setadmin_pw(rs.getString("ADMIN_PW"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로딩실패");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속실패");
		}
		//5. 반환
		
		finally{
			try{
				if(rs!=null){
					rs.close();
				}
				if(stmt!=null){
					stmt.close();
				}
				if(conn!=null){
					conn.close();
				}	
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		if(av==null){
			return null;
		}
		return av;
	}

	@Override
	public AdminVO readAdmin(String mem_id) {
		AdminVO av = null;
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
						+ " FROM ADMINVO"
						+ " WHERE ADMIN_ID ='"+mem_id+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				av = new AdminVO();
				av.setadmin_id(rs.getString("admin_id"));
				av.setadmin_pw(rs.getString("admin_pw"));
				av.setIsActivate(rs.getString("isactivate"));
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
		return av;
	}
}
