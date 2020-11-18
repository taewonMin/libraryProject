package blackList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.ConnectionUtil;

public class IBlackListDaoImpl implements IBlackListDao {
	private ConnectionUtil dbUtil = new ConnectionUtil();
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
		String sql = "INSERT INTO BLACKLISTVO"
				+ " VALUES (bl_seq.nextval,'" 
				+ bv.getMem_id() + "','"
				+ bv.getBlack_day() + "','" 
				+ bv.getBlack_end() + "','"
				+ bv.getAdmin_id() + "')";
		return dbUtil.updateDB(sql);
	}
	
	public List<BlackListVO> blackList() {
		List<BlackListVO> bs = new ArrayList<>();
		ResultSet rs = null;
		
		String sql = "SELECT * " + " FROM BLACKLISTVO";
		rs = dbUtil.readDB(sql);
		
		try {
			while (rs.next()) {
				BlackListVO bv = new BlackListVO();

				bv.setBlack_id(rs.getInt("BLACK_ID"));
				bv.setMem_id(rs.getString("MEM_ID"));
				bv.setBlack_day(rs.getString("BLACK_DAY"));
				bv.setBlack_end(rs.getString("BLACK_END"));
				bs.add(bv);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속실패");
		}
		dbUtil.destroy(rs);
		return bs;
	}
	
	@Override
	public BlackListVO readBlackList(int black_no) {
		BlackListVO blv = null;
		ResultSet rs = null;
		
		String sql = "SELECT *"
				+ " FROM BLACKLISTVO"
				+ " WHERE BLACK_ID ='"+black_no+"'";
		rs = dbUtil.readDB(sql);
		
		try {
			if(rs.next()){
				blv = new BlackListVO();
				blv.setBlack_id(rs.getInt("black_id"));
				blv.setMem_id(rs.getString("mem_id"));
				blv.setBlack_day(rs.getString("black_day"));
				blv.setBlack_end(rs.getString("black_end"));
				blv.setAdmin_id(rs.getString("admin_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속 실패");
		}
		dbUtil.destroy(rs);
		return blv;
	}
	
	@Override
	public int blackDeltle(int black_no) {
		String sql = "DELETE BLACKLISTVO" + " WHERE BLACK_ID ="
				+ black_no;
		return dbUtil.updateDB(sql);
	}

	@Override
	public int idcheack(String id) {
		int result = 0;
		ResultSet rs = null;
		
		String sql = "SELECT COUNT(MEM_ID) FROM BLACKLISTVO WHERE MEM_ID = '"  +id +"'";
		rs = dbUtil.readDB(sql);
		try {
			if(rs.next()){
				result = rs.getInt("COUNT(mem_id)");
			}
		} catch (SQLException e) {
			System.out.println("접속실패..??");
			e.printStackTrace();
		}
		dbUtil.destroy(rs);
		return result;
	}

}
