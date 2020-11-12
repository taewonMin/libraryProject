package blackList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import member.MemberVO;

public class IBlackListDaoImpl implements IBlackListDao {
	private List<BlackListVO> blackList; // 블랙리스트
	private List<MemberVO> memberList ; // 회원 목록
	
	private static IBlackListDao dao;

	private IBlackListDaoImpl() {

	}

	public static IBlackListDao getInstance() {
		if (dao == null) {
			dao = new IBlackListDaoImpl();
		}
		return dao;
	}
	public List<BlackListVO> blackList() {
		List<BlackListVO> bs = new ArrayList<>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String id = null;
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
			String sql = "SELECT * " + " FROM BlackList";
			// 4. 결과
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				BlackListVO bv = new BlackListVO();

				bv.setBlack_id(rs.getInt("BLACk_ID"));
				bv.setMem_id("MEM_ID");
				bv.setBlack_day("BLACK DAY");
				bv.setBlack_end("BLACK_END");
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
		blackList = bs;  // 블랙리스트
		return bs;
	}
	
	public List<MemberVO> memberList() {
		List<MemberVO> ms = new ArrayList<>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String id = null;
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
			String sql = "SELECT * " + " FROM Member";
			// 4. 결과
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				MemberVO mv = new MemberVO();
				
				mv.setMem_id("MEM_ID");
				ms.add(mv);

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
		memberList = ms; // 회원 목록
		return ms;
	}
	
	/**
	 * 블랙리스트 삭제
	 * 
	 * @param id
	 *            삭제할 블랙의 아이디
	 * @author 김태규
	 * @since 2020-11-12
	 */
	@Override
	public boolean blackDeltleMethod(String id) {
		for (BlackListVO hv : blackList) {
			if (hv.getMem_id().equals(id)) {
				readMember(id).setIsActivate("T");
				blackList.remove(hv);
				return true;
			}
		}
		return false;
	}
	/**
	 * 멤버리스트의 아이디 체크
	 * 
	 * @param id
	 *            체크할 블랙리스트의 아이디
	 * @author 김태규
	 * @since 2020-11-12
	 */
	private MemberVO readMember(String id) {
		MemberVO mv = null;
		for (MemberVO temp : memberList) {
			if (temp.getMem_id().equals(id)) {
				mv = temp;
			}
		}
		return mv;
	}

}
