package bookLGU;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.ConnectionUtil;

import book.BookVO;

public class IBookLGUDaoImpl implements IBookLGUDao{
	private ConnectionUtil dbUtil = new ConnectionUtil();
	private static IBookLGUDao dao;
	
	private IBookLGUDaoImpl() {
		
	}

	public static IBookLGUDao getInstance() {
		if(dao == null){
			dao = new IBookLGUDaoImpl();
		}
		return dao;
	}
	
	@Override
	public List<BookLGUVO> lguList() {
		ResultSet rs = null;
		List<BookLGUVO> list = new ArrayList<>();
		
		String sql = "SELECT * FROM BOOKLGU_VO";
		rs = dbUtil.readDB(sql);
		try {
			while (rs.next()) {
				BookLGUVO vo = new BookLGUVO();
				vo.setBook_LGU(rs.getInt("book_LGU"));
				vo.setBook_theme(rs.getString("book_theme"));				
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		dbUtil.destroy(rs);
		return list;
	}

	@Override
	public List<BookVO> themeList(int book_lgu) {
		ResultSet rs = null;
		List<BookVO> list = new ArrayList<>();
		
		String sql = "SELECT *"
				+ "FROM BOOKVO A, BOOKLGU_VO B "
				+ "WHERE A.BOOK_LGU = B.BOOK_LGU AND A.BOOK_LGU ='"+book_lgu+"'";
		rs = dbUtil.readDB(sql);
		
		try {
			while (rs.next()) {
				BookVO vo = new BookVO();
				vo.setBook_id(rs.getInt("book_id"));
				vo.setBook_name(rs.getString("book_name"));
				vo.setBook_author(rs.getString("book_author"));
				vo.setBook_publisher(rs.getString("book_publisher"));
				vo.setBook_LGU(rs.getInt("book_LGU"));
				vo.setBook_summary(rs.getString("book_summary"));
				vo.setBook_state(rs.getString("book_state"));
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		dbUtil.destroy(rs);
		return list;
	}

	@Override
	public BookLGUVO readBookLGU(int booklgu_id) {
		BookLGUVO blv = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT *"
						+ " FROM BOOKLGU_VO"
						+ " WHERE BOOK_LGU =" + booklgu_id;
			rs = dbUtil.readDB(sql);
			if(rs.next()){
				blv = new BookLGUVO();
				blv.setBook_LGU(rs.getInt("book_lgu"));
				blv.setBook_theme(rs.getString("book_theme"));
				blv.setIsActivate(rs.getString("isactivate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속 실패");
		}
		dbUtil.destroy(rs);
		return blv;
	}
}
