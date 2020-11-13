package blackList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class IBlackListDaoImpl implements IBlackListDao {
	private static IBlackListDao dao;

	private IBlackListDaoImpl() {

	}

	public static IBlackListDao getInstance() {
		if (dao == null) {
			dao = new IBlackListDaoImpl();
		}
		return dao;
	}
	
	@Override
	public int createBlackList(BlackListVO bv) {
		int result = 0;
		Connection conn = null;
		Statement stmt = null;

		// 접속
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "jju";
			String password = "java";
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			String sql = "INSERT INTO BLACKLISTVO"
					+ " VALUES (bl_seq.nextval,'" 
					+ bv.getMem_id() + "','"
					+ bv.getBlack_day() + "','" 
					+ bv.getBlack_end() + "','"
					+ bv.getAdmin_id() + "')";

			result = stmt.executeUpdate(sql);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 접속 실패");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속실패");
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
	
	public List<BlackListVO> blackList() {
		List<BlackListVO> bs = new ArrayList<>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. 접속
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "jju";
			String password = "java";
			conn = DriverManager.getConnection(url, user, password);

			// 3. 질의
			stmt = conn.createStatement();
			String sql = "SELECT * " + " FROM BLACKLISTVO";
			// 4. 결과
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				BlackListVO bv = new BlackListVO();

				bv.setBlack_id(rs.getInt("BLACK_ID"));
				bv.setMem_id(rs.getString("MEM_ID"));
				bv.setBlack_day(rs.getString("BLACK_DAY"));
				bv.setBlack_end(rs.getString("BLACK_END"));
				bs.add(bv);

			}
		}
		// 5. 반환
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속실패");
		} catch (ClassNotFoundException e) {
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bs;
	}
	
	@Override
	public BlackListVO readBlackList(int black_no) {
		BlackListVO blv = null;
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
						+ " FROM BLACKLISTVO"
						+ " WHERE BLACK_ID ='"+black_no+"'";
			
			rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				blv = new BlackListVO();
				blv.setBlack_id(rs.getInt("black_id"));
				blv.setMem_id(rs.getString("mem_id"));
				blv.setBlack_day(rs.getString("black_day"));
				blv.setBlack_end(rs.getString("black_end"));
				blv.setAdmin_id(rs.getString("admin_id"));
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
		return blv;
	}
	
	@Override
	public int blackDeltle(int black_no) {
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
			String sql = "DELETE BLACKLISTVO" + " WHERE BLACK_ID ="
					+ black_no;

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

	@Override
	public int idcheack(String id) {
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
			String sql = "SELECT COUNT(MEM_ID) FROM BLACKLISTVO WHERE MEM_ID = '"  +id +"'";
			
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				result = rs.getInt("COUNT(mem_id)");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("접속실패??");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("접속실패..??");
			e.printStackTrace();
		}finally{
			try{
				if(rs!=null){
					rs.close();
				}if(stmt!=null){
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

}
