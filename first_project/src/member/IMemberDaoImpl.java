package member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.ConnectionUtil;

public class IMemberDaoImpl implements IMemberDao{
	private ConnectionUtil dbUtil = new ConnectionUtil();
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
		ResultSet rs = null;
		MemberVO mv = new MemberVO();
		
		String sql = "SELECT *"//memvo 받아갈거니까 전체
				+ " FROM MEMBERVO"
				+ " WHERE MEM_ID = '"+params.get("mem_id")+"'"
				+ " AND MEM_PW = '"+params.get("mem_pw")+"'";
		rs = dbUtil.readDB(sql);
		
		try {
			while(rs.next()){
				mv.setMem_id(rs.getString("mem_id"));
				mv.setMem_pw(rs.getString("mem_pw"));
				mv.setMem_name(rs.getString("mem_name"));
				mv.setMem_bir(rs.getString("mem_bir"));
				mv.setMem_email(rs.getString("mem_email"));
				mv.setMem_tel(rs.getString("mem_tel"));
				mv.setRent_count(rs.getInt("rent_count"));
				mv.setIsActivate(rs.getString("isactivate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속실패");
		}
		dbUtil.destroy(rs);
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
		ResultSet rs = null;
		
		String sql = "SELECT COUNT(MEM_ID) FROM MEMBERVO WHERE MEM_ID = '"  +mem_id +"'";
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

	@Override
	public int createMember(MemberVO params) {
		String mem_id = params.getMem_id();
		String mem_pw = params.getMem_pw();
		String mem_name = params.getMem_name();
		String mem_bir = params.getMem_bir();
		String mem_tel = params.getMem_tel();
		String mem_email = params.getMem_email();
		int rent_count = 0;
		String isActivate = "T";
		
		String sql = "INSERT INTO MEMBERVO ( mem_id,mem_name,mem_pw,mem_bir,mem_tel, mem_email,rent_count,isActivate)"
				+ " VALUES ('"
				+mem_id+"','"+ mem_name+"','"+ mem_pw +"','"+mem_bir +"','"+mem_tel +"','"+mem_email+"','"
				+rent_count+"','"+ isActivate+"') ";
		return dbUtil.updateDB(sql);
	}

	@Override
	public MemberVO readMember(String mem_id) {
		MemberVO mv = null;
		ResultSet rs = null;
		
		String sql = "SELECT *"
				+ " FROM MEMBERVO"
				+ " WHERE MEM_ID ='"+mem_id+"'";
		rs = dbUtil.readDB(sql);
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속 실패");
		} 
		dbUtil.destroy(rs);
		return mv;
	}
	
	/**
	 * 회원리스트 출력
	 * @return 
	 * @author 김태규
	 */
	@Override
	public List<MemberVO> memberList() {
		ResultSet rs = null;
		List<MemberVO> memberList = new ArrayList<>();// null로 하면 안됨

		String sql = "SELECT *" + " FROM MemberVO";
		rs = dbUtil.readDB(sql);
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속실패");
		}
		dbUtil.destroy(rs);
		return memberList;
	}
	
	@Override
	public int updateMember(Map<String, String> myInfo) {
		int result = 0;
		if(myInfo.get("mem_pw")!=null || myInfo.get("mem_tel")!=null || myInfo.get("mem_email")!=null 
				|| myInfo.get("rent_cnt")!=null || myInfo.get("isactivate")!=null){
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
			else if(myInfo.get("rent_cnt")!=null){
				sql += " RENT_COUNT="+Integer.parseInt(myInfo.get("rent_cnt"));
			}
			else if(myInfo.get("isactivate")!=null){
				sql += " isActivate='"+myInfo.get("isactivate")+"'";
			}
			sql += " WHERE MEM_ID='"+myInfo.get("mem_id")+"'";
			result = dbUtil.updateDB(sql);
		}
		return result;
	}
	
	@Override
	public int deleteMember(String mem_id) {
		String sql = "DELETE MEMBERVO"
				   + " WHERE MEM_ID ='"+mem_id+"'";
		return dbUtil.updateDB(sql);
	}
	

}
