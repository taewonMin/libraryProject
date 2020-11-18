package admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import common.ConnectionUtil;

public class IAdminDaoImpl implements IAdminDao{
	private ConnectionUtil dbUtil = new ConnectionUtil();
	private static IAdminDao dao;
	
	private IAdminDaoImpl() {
		
	}

	public static IAdminDao getInstance() {
		if(dao == null){
			dao = new IAdminDaoImpl();
		}
		return dao;
	}
	
	@Override
	public AdminVO adminMatch(Map<String, String> params) {
		String mem_id = params.get("mem_id");
		String mem_pw = params.get("mem_pw");

		ResultSet rs = null;
		AdminVO av = null;

		String sql = "SELECT ADMIN_ID, ADMIN_PW "// memvo 받아갈거니까 전체
				+ " FROM ADMINVO" + " WHERE ADMIN_ID = '" + mem_id
				+ "'"
				+ " AND ADMIN_PW = '" + mem_pw + "'";
		rs = dbUtil.readDB(sql);
		try {
			if (rs.next()) {
				av = new AdminVO();
				av.setadmin_id(rs.getString("ADMIN_ID"));
				av.setadmin_pw(rs.getString("ADMIN_PW"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbUtil.destroy(rs);
		return av;
	}

	@Override
	public AdminVO readAdmin(String mem_id) {
		AdminVO av = null;
		ResultSet rs = null;
		
		String sql = "SELECT *"
				+ " FROM ADMINVO"
				+ " WHERE ADMIN_ID ='"+mem_id+"'";
		rs = dbUtil.readDB(sql);
		try {
			if(rs.next()){
				av = new AdminVO();
				av.setadmin_id(rs.getString("admin_id"));
				av.setadmin_pw(rs.getString("admin_pw"));
				av.setIsActivate(rs.getString("isactivate"));
				av.setAdmin_email(rs.getString("admin_email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속 실패");
		}
		dbUtil.destroy(rs);
		return av;
	}
}
