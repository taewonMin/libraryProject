package notice;

import java.sql.*;
import java.util.*;



public class INoticeDaoImpl implements INoticeDao{
	
	private static INoticeDao dao;
	
	private INoticeDaoImpl() {
		
	}

	public static INoticeDao getInstance() {
		if(dao == null){
			dao = new INoticeDaoImpl();
		}
		return dao;
	}
	
	
	
	@Override
	public int createNotice(NoticeVO nv) {
		int result = 0;
		Connection conn = null;
		Statement stmt = null;
	
		//접속
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "jju";
			String password = "java";
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			String sql = "INSERT INTO NOTICEVO"
						+ " VALUES (not_seq.nextval,'"
						+ nv.getNotice_title() + "','"+ nv.getNotice_content() +"','"+ nv.getNotice_date() +"','"+ nv.getAdmin_id() + "')";
			
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
	
	
	
	
	/**
	 * 공지출력메서드
	 * @return 출력에성공하면 List값반환
	 * @author 조애슬
	 * 
	 */
	@Override
	public List<NoticeVO> noticeList() {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		List<NoticeVO> noticeList = new ArrayList<>();//null로 하면 안됨
		
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
					String sql = "SELECT *"
					+ " FROM NOTICEVO";
			//4. 결과
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				
				NoticeVO nv = new NoticeVO();/////////
								
				nv.setNotice_no(rs.getInt("notice_no"));
				nv.setNotice_title(rs.getString("notice_title"));
				nv.setNotice_content(rs.getNString("notice_content"));
				nv.setNotice_date(rs.getNString("notice_date"));
				nv.setAdmin_id(rs.getString("admin_id"));
				
				noticeList.add(nv);
				
				}
				
			//}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로드실패");
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
		return noticeList;
	}

	/**
	 * 공지사항 상세보기 메서드
	 * @return int
	 * @author 조애슬
	 */
	@Override
	public NoticeVO openNoDetail(int input) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		NoticeVO nv = new NoticeVO();
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "jju";
			String password = "java";
			
			conn = DriverManager.getConnection(url, user, password);
			
			stmt = conn.createStatement();
			String sql = "SELECT * FROM NOTICEVO WHERE NOTICE_NO = '"  +input +"'";
			
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				nv.setAdmin_id(rs.getString("admin_id"));
				nv.setNotice_no(rs.getInt("notice_no"));
				nv.setNotice_title(rs.getString("notice_title"));
				nv.setNotice_content(rs.getString("notice_content"));
				nv.setNotice_date(rs.getString("notice_date"));
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
		return nv;
	}

	@Override
	public int deleteNotice(int notice_no) {
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
			String sql = "DELETE NOTICEVO"
					   + " WHERE NOTICE_NO ="+notice_no;
			
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
