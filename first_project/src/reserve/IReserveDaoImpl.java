package reserve;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.ConnectionUtil;

public class IReserveDaoImpl implements IReserveDao{
	private ConnectionUtil dbUtil = new ConnectionUtil();
	private static IReserveDao dao;
	
	private IReserveDaoImpl() {
		
	}

	public static IReserveDao getInstance() {
		if(dao == null){
			dao = new IReserveDaoImpl();
		}
		return dao;
	}

	@Override
	public int createReserve(Map<String, Object> rsvMap) {
		String sql = "INSERT INTO RESERVEVO"
					+ " VALUES(rsv_seq.nextval,'"
					+ (int)rsvMap.get("book_id") + "','"
					+ (String)rsvMap.get("mem_id") + "')";
		return dbUtil.updateDB(sql);
	}
	
	@Override
	public List<ReserveVO> readReserveList(String mem_id) {
		List<ReserveVO> rsvList = new ArrayList<>();
		ResultSet rs = null;
		
		String sql = "SELECT *"
				+ " FROM RESERVEVO"
				+ " WHERE MEM_ID ='"+mem_id+"'";
		rs = dbUtil.readDB(sql);
		
		try {
			while(rs.next()){
				ReserveVO rsv = new ReserveVO();
				rsv.setRsv_no(rs.getInt("rsv_no"));
				rsv.setBook_id(rs.getInt("book_id"));
				rsv.setMem_id(rs.getString("mem_id"));
				rsvList.add(rsv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속 실패");
		} 
		dbUtil.destroy(rs);
		return rsvList;
	}

	@Override
	public ReserveVO readReserveVO(int book_id) {
		ReserveVO rsv = null;
		ResultSet rs = null;
		
		String sql = "SELECT *"
				+ " FROM RESERVEVO"
				+ " WHERE BOOK_ID ='"+book_id+"'";
		rs = dbUtil.readDB(sql);
		
		try {
			if(rs.next()){
				rsv = new ReserveVO();
				rsv.setRsv_no(rs.getInt("rsv_no"));
				rsv.setBook_id(rs.getInt("book_id"));
				rsv.setMem_id(rs.getString("mem_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속 실패");
		}
		dbUtil.destroy(rs);
		return rsv;
	}

	@Override
	public int deleteReserve(Map<String, Object> newRsv) {
		String sql = "DELETE RESERVEVO"
				   + " WHERE MEM_ID ='"+(String)newRsv.get("mem_id")+"'"
			   		 + " AND BOOK_ID ="+(int)newRsv.get("book_id");
		return dbUtil.updateDB(sql);
	}

}
