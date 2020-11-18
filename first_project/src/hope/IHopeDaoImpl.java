package hope;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.ConnectionUtil;

public class IHopeDaoImpl implements IHopeDao{
	private ConnectionUtil dbUtil = new ConnectionUtil();
	private static IHopeDao dao;
	
	private IHopeDaoImpl() {
		
	}

	public static IHopeDao getInstance() {
		if(dao == null){
			dao = new IHopeDaoImpl();
		}
		return dao;
	}
	
	@Override
	public List<HopeVO> hopeList() {
		ResultSet rs = null;
		List<HopeVO> hopeList = new ArrayList<>();//null로 하면 안됨
		
		String sql = "SELECT * FROM HOPEVO";
		rs = dbUtil.readDB(sql);
		try {
			while (rs.next()) {
				HopeVO hv = new HopeVO();// ///////

				hv.setHope_id(rs.getInt("hope_id"));
				hv.setHope_name(rs.getString("hope_name"));
				hv.setHope_author(rs.getString("hope_author"));
				hv.setHope_content(rs.getString("hope_content"));
				hv.setHope_publisher(rs.getString("hope_publisher"));
				hv.setMem_id(rs.getString("mem_id"));
				hv.setThumb(rs.getInt("thumb"));

				hopeList.add(hv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속실패");
		}
		dbUtil.destroy(rs);
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
		String hope_memId = params.get("hope_memId");
		String hope_name = params.get("hope_name");
		String hope_author = params.get("hope_author");
		String hope_publisher = params.get("hope_publisher");
		String hope_content = params.get("hope_content");
		
		String sql = "INSERT INTO HOPEVO (HOPE_ID,HOPE_NAME,HOPE_AUTHOR,HOPE_PUBLISHER,HOPE_CONTENT,MEM_ID)"
				+ " VALUES (ho_seq.nextval,'"
				+hope_name+"','"+ hope_author+"','"+ hope_publisher +"','"+hope_content +"','"+ hope_memId +"') ";
		return dbUtil.updateDB(sql);
	}

	/**
	 * 희망도서 상세보기
	 * @return hopeno가 일치하는 hopeVO 글 하나를 반환
	 * @author 조애슬
	 * @since 2020-11-12
	 */
	@Override
	public HopeVO hopeDetailView(int hopeNo) {
		HopeVO hv = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM HOPEVO WHERE HOPE_ID = '"  +hopeNo +"'";
		rs = dbUtil.readDB(sql);
		
		try {
			while(rs.next()){
				hv = new HopeVO();
				hv.setHope_id(rs.getInt("hope_id"));
				hv.setMem_id(rs.getString("mem_id"));
				hv.setHope_name(rs.getString("hope_name"));
				hv.setHope_author(rs.getString("hope_author"));
				hv.setHope_publisher(rs.getString("hope_publisher"));
				hv.setHope_content(rs.getString("hope_content"));
			}
		} catch (SQLException e) {
			System.out.println("접속실패..??");
			e.printStackTrace();
		}
		dbUtil.destroy(rs);
		return hv;
	}

	@Override
	public int hopeRemoveView(String mem_id, int hopeNo) {
		String sql = "DELETE FROM HOPEVO "
				+ " WHERE MEM_ID = '"
				+mem_id+"' and "+ "hope_id = '"+ hopeNo +"'";
		return dbUtil.updateDB(sql);
	}

	@Override
	public int deleteHope(int hope_id) {
		String sql = "DELETE HOPEVO"
				   + " WHERE HOPE_ID ="+hope_id;
		return dbUtil.updateDB(sql);
	}

	@Override
	public int hopeThumb(int hope_id) {
		String sql = "UPDATE hopevo " +
						" SET " +
						" thumb = thumb + 1 " +
						" WHERE hope_id = '" +hope_id + "'";
		return dbUtil.updateDB(sql);
	}
}
