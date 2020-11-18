package notice;

import java.sql.*;
import java.util.*;

import common.ConnectionUtil;

public class INoticeDaoImpl implements INoticeDao{
	private ConnectionUtil dbUtil = new ConnectionUtil();
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
		String sql = "INSERT INTO NOTICEVO"
					+ " VALUES (not_seq.nextval,'"
					+ nv.getNotice_title() + "','"
					+ nv.getNotice_content() +"','"
					+ nv.getNotice_date() +"','"
					+ nv.getAdmin_id() + "')";
		return dbUtil.updateDB(sql);
	}
	
	@Override
	public List<NoticeVO> noticeList() {
		ResultSet rs = null;
		List<NoticeVO> noticeList = new ArrayList<>();//null로 하면 안됨
		
		String sql = "SELECT *"
				+ " FROM NOTICEVO";
		rs = dbUtil.readDB(sql);
		
		try {
			while(rs.next()){
				NoticeVO nv = new NoticeVO();/////////
								
				nv.setNotice_no(rs.getInt("notice_no"));
				nv.setNotice_title(rs.getString("notice_title"));
				nv.setNotice_content(rs.getNString("notice_content"));
				nv.setNotice_date(rs.getNString("notice_date"));
				nv.setAdmin_id(rs.getString("admin_id"));
				
				noticeList.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속실패");
		}
		dbUtil.destroy(rs);
		return noticeList;
	}

	@Override
	public NoticeVO openNoDetail(int input) {
		ResultSet rs = null;
		NoticeVO nv = null;
		
		String sql = "SELECT * FROM NOTICEVO WHERE NOTICE_NO = '"  +input +"'";
		rs = dbUtil.readDB(sql);
		
		try {
			if(rs.next()){
				nv = new NoticeVO();
				nv.setAdmin_id(rs.getString("admin_id"));
				nv.setNotice_no(rs.getInt("notice_no"));
				nv.setNotice_title(rs.getString("notice_title"));
				nv.setNotice_content(rs.getString("notice_content"));
				nv.setNotice_date(rs.getString("notice_date"));
			}
		} catch (SQLException e) {
			System.out.println("접속실패..??");
			e.printStackTrace();
		}
		dbUtil.destroy(rs);
		return nv;
	}

	@Override
	public int deleteNotice(int notice_no) {
		String sql = "DELETE NOTICEVO"
				   + " WHERE NOTICE_NO ="+notice_no;
		return dbUtil.updateDB(sql);
	}

}
