package hope;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IHopeDaoImpl implements IHopeDao{
	
	private static IHopeDao dao;
	
	private IHopeDaoImpl() {
		
	}

	public static IHopeDao getInstance() {
		if(dao == null){
			dao = new IHopeDaoImpl();
		}
		return dao;
	}
	
	/**
	 * 희망도서 리스트 출력
	 * @author 조애슬
	 * @since 2020-11-12
	 * @return List
	 */
	@Override
	public List<HopeVO> hopeList() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		List<HopeVO> hopeList = new ArrayList<>();//null로 하면 안됨
		
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
					+ " FROM HOPEVO";
			//4. 결과
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				
				HopeVO hv = new HopeVO();/////////
								
				hv.setHope_id(rs.getInt("hope_id"));
				hv.setHope_name(rs.getString("hope_name"));
				hv.setHope_author(rs.getString("hope_author"));
				hv.setHope_content(rs.getString("hope_content"));
				hv.setHope_publisher(rs.getString("hope_publisher"));
				hv.setMem_id(rs.getString("mem_id"));
			
				
				hopeList.add(hv);
				
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
		
		return hopeList;
	}

	/**
	 * 희망도서 글 등록 등록되면 양수리턴
	 * @return int
	 * @author 조애슬
	 * @since 2020-11-12
	 */
	@Override
	public int hopeListAdd(Map<String, String> params) {
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
	
		String hope_memId = params.get("hope_memId");
		String hope_name = params.get("hope_name");
		String hope_author = params.get("hope_author");
		String hope_publisher = params.get("hope_publisher");
		String hope_content = params.get("hope_content");
		

		
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
			String sql = "INSERT INTO HOPEVO (HOPE_ID,HOPE_NAME,HOPE_AUTHOR,HOPE_PUBLISHER,HOPE_CONTENT,MEM_ID)"
					+ " VALUES (ho_seq.nextval,'"
					+hope_name+"','"+ hope_author+"','"+ hope_publisher +"','"+hope_content +"','"+ hope_memId +"') ";
			
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

	/**
	 * 희망도서 상세보기
	 * @return hopeno가 일치하는 hopeVO 글 하나를 반환
	 * @author 조애슬
	 * @since 2020-11-12
	 */
	@Override
	public HopeVO hopeDetailView(int hopeNo) {
		HopeVO hv = new HopeVO();
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
			String sql = "SELECT * FROM HOPEVO WHERE HOPE_ID = '"  +hopeNo +"'";
			
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				hv.setHope_id(rs.getInt("hope_id"));
				hv.setMem_id(rs.getString("mem_id"));
				hv.setHope_name(rs.getString("hope_name"));
				hv.setHope_author(rs.getString("hope_author"));
				hv.setHope_publisher(rs.getString("hope_publisher"));
				hv.setHope_content(rs.getString("hope_content"));
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
		

		return hv;
	}

	/**
	 * 희망도서 삭제
	 * @return 삭제에 성공하면 1반환
	 * @param 삭제를 시도하는 멤버의 아이디와 삭제하고자 하는 글 번호
	 * @since 2020-11-12
	 * @author 조애슬
	 */
	@Override
	public int hopeRemoveView(String mem_id, int hopeNo) {
		
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
	
		String deleteId = mem_id;
		int deleteNo = hopeNo;
		
		
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
			String sql = "DELETE FROM HOPEVO "
					+ " WHERE MEM_ID = '"
					+deleteId+"' and "+ "hope_id = '"+ deleteNo +"'";
			
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
	
}
