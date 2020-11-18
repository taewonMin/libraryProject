package rental;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.ConnectionUtil;

public class IRentalDaoImpl implements IRentalDao{
	private ConnectionUtil dbUtil = new ConnectionUtil();
	private static IRentalDao dao;
	
	private IRentalDaoImpl() {
		
	}

	public static IRentalDao getInstance() {
		if(dao == null){
			dao = new IRentalDaoImpl();
		}
		return dao;
	}
	
	@Override
	public RentalVO createRental(Map<String, Object> map) {
		RentalVO rv = new RentalVO();
		rv.setMem_id((String)map.get("mem_id"));
		rv.setBook_id((int)map.get("book_id"));
		rv.setRental_start((String)map.get("start_date"));
		rv.setRental_end((String)map.get("end_date"));
		
		String sql = "INSERT INTO RENTALVO"
					+ " VALUES(rent_seq.nextval,'"
					+ rv.getRental_start() + "','"
					+ rv.getRental_end() + "','"
					+ rv.getBook_id() + "','"
					+ rv.getMem_id() + "')";
		dbUtil.updateDB(sql);
		
		return rv;
	}

	@Override
	public RentalVO readRentalVO(int book_id) {
		RentalVO rv = null;
		ResultSet rs = null;
		
		String sql = "SELECT *"
				+ " FROM RENTALVO"
				+ " WHERE BOOK_ID ='"+book_id+"'";
		rs = dbUtil.readDB(sql);
		try {
			if(rs.next()){
				rv = new RentalVO();
				rv.setRental_id(rs.getInt("rental_id"));
				rv.setRental_start(rs.getString("rental_start"));
				rv.setRental_end(rs.getString("rental_end"));
				rv.setBook_id(rs.getInt("book_id"));
				rv.setMem_id(rs.getString("mem_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속 실패");
		} 
		dbUtil.destroy(rs);
		return rv;
	}

	@Override
	public List<RentalVO> readRentalList(String mem_id) {
		List<RentalVO> rvList = new ArrayList<>();
		ResultSet rs = null;
		
		String sql = "SELECT *"
				+ " FROM RENTALVO"
				+ " WHERE MEM_ID ='"+mem_id+"'";
		rs = dbUtil.readDB(sql);
		
		try {
			while(rs.next()){
				RentalVO rv = new RentalVO();
				rv.setRental_id(rs.getInt("rental_id"));
				rv.setRental_start(rs.getString("rental_start"));
				rv.setRental_end(rs.getString("rental_end"));
				rv.setBook_id(rs.getInt("book_id"));
				rv.setMem_id(rs.getString("mem_id"));
				rvList.add(rv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속 실패");
		} 
		dbUtil.destroy(rs);
		return rvList;
	}

	@Override
	public int deleteRental(int book_id) {
		String sql = "DELETE RENTALVO"
				   + " WHERE BOOK_ID ="+book_id;
		return dbUtil.updateDB(sql);
	}
}
