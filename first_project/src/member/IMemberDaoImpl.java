package member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IMemberDaoImpl implements IMemberDao{
	
	private static IMemberDao dao;
	
	private IMemberDaoImpl() {
		
	}

	public static IMemberDao getInstance() {
		if(dao == null){
			dao = new IMemberDaoImpl();
		}
		return dao;
	}
	
	/**
	 * 로그인 받은 ID와 PW를 DB와 비교
	 * @author 조애슬
	 */
	@Override
	public MemberVO loginMatch(Map<String, String> params) {
		
		String mem_id = params.get("mem_id");
		String mem_pw = params.get("mem_pw");
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		MemberVO mv = new MemberVO();
		
		String id =null;
		String pw = null;
		String name = null;
		String bir = null;
		String email = null;
		String tel = null;
		int rent_count = 0;
		String isActivate = null;
		
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
			String sql = "SELECT *"//memvo 받아갈거니까 전체
					+ " FROM MEMBERVO"
					+ " WHERE MEM_ID = '"+mem_id+"'"
					+ " AND MEM_PW = '"+mem_pw+"'";
			//4. 결과
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				id = rs.getString("mem_id");
				pw = rs.getString("mem_pw");
				name = rs.getString("mem_name");
				bir = rs.getString("mem_bir");
				email = rs.getString("mem_email");
				tel = rs.getString("mem_tel");
				rent_count = rs.getInt("rent_count");
				isActivate = rs.getString("isactivate");
				
				mv.setMem_id(id);
				mv.setMem_pw(pw);
				mv.setMem_name(name);
				mv.setMem_bir(bir);
				mv.setMem_email(email);
				mv.setMem_tel(tel);
				mv.setRent_count(rent_count);
				mv.setIsActivate(isActivate);
				
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
		
		if(mv==null){
			return null;
		}
		return mv;
	}
	
	/**
	 * 아이디중복체크 . 중복이면 1을 반환 중복이아니면 0
	 * @return int
	 * @author 조애슬
	 **/
	@Override
	public int idUniqCheck(String mem_id) {
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
			String sql = "SELECT COUNT(MEM_ID) FROM MEMBERVO WHERE MEM_ID = '"  +mem_id +"'";
			
			rs = stmt.executeQuery(sql);
			while(rs.next()){
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

	@Override
	public int createMember(MemberVO params) {
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
	
		String mem_id = params.getMem_id();
		String mem_pw = params.getMem_pw();
		String mem_name = params.getMem_name();
		String mem_bir = params.getMem_bir();
		String mem_tel = params.getMem_tel();
		String mem_email = params.getMem_email();
		int rent_count = 0;
		String isActivate = "T";
		
		//접속
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			
		//연결?
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "jju";
			String password = "java";
			conn = DriverManager.getConnection(url, user, password);
			
		//질의
			stmt = conn.createStatement();
			String sql = "INSERT INTO MEMBERVO ( mem_id,mem_name,mem_pw,mem_bir,mem_tel, mem_email,rent_count,isActivate)"
					+ " VALUES ('"
					+mem_id+"','"+ mem_name+"','"+ mem_pw +"','"+mem_bir +"','"+mem_tel +"','"+mem_email+"','"
					+rent_count+"','"+ isActivate+"') ";
			
			//결과값반환
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
	public MemberVO readMember(String mem_id) {
		MemberVO mv = null;
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
						+ " FROM MEMBERVO"
						+ " WHERE MEM_ID ='"+mem_id+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				mv = new MemberVO();
				mv.setMem_id(rs.getString("mem_id"));
				mv.setMem_pw(rs.getString("mem_pw"));
				mv.setMem_name(rs.getString("mem_name"));
				mv.setMem_bir(rs.getString("mem_bir"));
				mv.setMem_email(rs.getString("mem_email"));
				mv.setMem_tel(rs.getString("mem_tel"));
				mv.setIsActivate(rs.getString("isActivate"));
				mv.setRent_count(rs.getInt("rent_count"));
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
		return mv;
	}
	
	/**
	 * 회원리스트 출력
	 * @return 
	 * @author 김태규
	 */
	@Override
	public List<MemberVO> memberList() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		List<MemberVO> memberList = new ArrayList<>();// null로 하면 안됨

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
			String sql = "SELECT *" + " FROM MemberVO";
			// 4. 결과
			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				MemberVO mv = new MemberVO();// ///////

				mv.setMem_id(rs.getString("mem_id"));
				mv.setMem_name(rs.getString("mem_name"));
				mv.setMem_pw(rs.getNString("mem_pw"));
				mv.setMem_bir(rs.getNString("mem_bir"));
				mv.setMem_email(rs.getString("mem_email"));
				mv.setMem_tel(rs.getString("mem_tel"));
				mv.setRent_count(rs.getInt("rent_count"));
				mv.setIsActivate(rs.getString("isActivate"));

				memberList.add(mv);

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
		return memberList;
	}
	
	@Override
	public int updateMember(Map<String, String> myInfo) {
		int result = 0;
		if(myInfo.get("mem_pw")!=null || myInfo.get("mem_tel")!=null || myInfo.get("mem_email")!=null || myInfo.get("isactivate")!=null){
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
				String sql = "UPDATE MEMBERVO"
						+ " SET";
				if(myInfo.get("mem_pw")!=null){
					sql += " MEM_PW='"+myInfo.get("mem_pw")+"'";
				}
				else if(myInfo.get("mem_tel")!=null){
					sql += " MEM_TEL='"+myInfo.get("mem_tel")+"'";
				}
				else if(myInfo.get("mem_email")!=null){
					sql += " MEM_EMAIL='"+myInfo.get("mem_email")+"'";
				}
				else if(myInfo.get("isactivate")!=null){
					sql += " isActivate='"+myInfo.get("isactivate")+"'";
				}
				sql += " WHERE MEM_ID='"+myInfo.get("mem_id")+"'";
				
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
		}
		return result;
	}
	
	@Override
	public int deleteMember(String mem_id) {
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
			String sql = "DELETE MEMBERVO"
					   + " WHERE MEM_ID ='"+mem_id+"'";
			
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
