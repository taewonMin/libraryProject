package book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.ConnectionUtil;

public class IBookDaoImpl implements IBookDao{
	private ConnectionUtil dbUtil = new ConnectionUtil();
	private static IBookDao dao;
	
	private IBookDaoImpl() {
		
	}

	public static IBookDao getInstance() {
		if(dao == null){
			dao = new IBookDaoImpl();
		}
		return dao;
	}

	@Override
	public int createBook(BookVO bv) {
		String sql = "INSERT INTO BOOKVO"
					+ " VALUES (BOOK_SEQ.NEXTVAL,'"
					+ bv.getBook_author() +"','"
					+ bv.getBook_name() +"','"
					+ bv.getBook_summary() +"','"
					+ bv.getBook_publisher() +"','"
					+ bv.getBook_state() +"','"
					+ bv.getIsActivate() +"',"
					+ bv.getBook_LGU() +")";
		return dbUtil.updateDB(sql);
	}
	
	@Override
	public BookVO readBook(int book_id){
		BookVO bv = null;
		String sql = "SELECT *"
					+ " FROM BOOKVO"
					+ " WHERE BOOK_ID ="+book_id;
		ResultSet rs = dbUtil.readDB(sql);
		try {
			if(rs.next()){
				bv = new BookVO();
				bv.setBook_id(rs.getInt("book_id"));
				bv.setBook_author(rs.getString("book_author"));
				bv.setBook_name(rs.getString("book_name"));
				bv.setBook_summary(rs.getString("book_summary"));
				bv.setBook_publisher(rs.getString("book_publisher"));
				bv.setBook_state(rs.getString("book_state"));
				bv.setIsActivate(rs.getString("isActivate"));
				bv.setBook_LGU(rs.getInt("book_lgu"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbUtil.destroy(rs);
		return bv;
	}
	
	@Override
	public List<BookVO> bookList() {
		ResultSet rs = null;
		List<BookVO> bookList = new ArrayList<>();// null로 하면 안됨

		// 여기서 database가야함 여기서 쿼리준비
		String sql = "SELECT *" + " FROM BookVO";
		rs = dbUtil.readDB(sql);
		try {
			while (rs.next()) {
				BookVO bv = new BookVO();// ///////

				bv.setBook_id(rs.getInt("book_id"));
				bv.setBook_name(rs.getString("book_name"));
				bv.setBook_author(rs.getNString("book_author"));
				bv.setBook_summary(rs.getNString("book_summary"));
				bv.setBook_publisher(rs.getString("book_publisher"));
				bv.setBook_LGU(rs.getInt("book_LGU"));
				bv.setBook_state(rs.getString("book_state"));
				bv.setIsActivate(rs.getString("isActivate"));

				bookList.add(bv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbUtil.destroy(rs);
		return bookList;
	}
	
	
	@Override
	public List<BookVO> bookSearch(String book_name) {
		ResultSet rs = null;
		List<BookVO> list = new ArrayList<>();
		
		String sql ="SELECT COUNT(BOOK_NAME),BOOK_ID,BOOK_AUTHOR,BOOK_NAME,BOOK_SUMMARY,"
				+" BOOK_PUBLISHER, BOOK_STATE, ISACTIVATE, BOOK_LGU"
				+" FROM BOOKVO "
				+" WHERE BOOK_NAME LIKE '%"+book_name+"%' "
				+" GROUP BY BOOK_ID,BOOK_AUTHOR,BOOK_NAME,BOOK_SUMMARY, "
				+" BOOK_PUBLISHER, BOOK_STATE, ISACTIVATE, BOOK_LGU";
		
		rs = dbUtil.readDB(sql);
		try {
			while (rs.next()) {
				BookVO vo = new BookVO();
				vo.setBook_id(rs.getInt("book_id"));
				vo.setBook_name(rs.getString("book_name"));	
				vo.setBook_author(rs.getString("book_author"));
				vo.setBook_summary(rs.getString("book_summary"));
				vo.setBook_publisher(rs.getString("book_publisher"));
				vo.setBook_state(rs.getString("book_state"));
				vo.setIsActivate(rs.getString("isactivate"));
				list.add(vo);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		dbUtil.destroy(rs);
		return list;
	}

	@Override
	public int updateBook(BookVO bv) {
		String sql = "UPDATE BOOKVO"
					+ " SET"
					+ " BOOK_ID="+bv.getBook_id()+","
					+ " BOOK_AUTHOR='"+bv.getBook_author()+"',"
					+ " BOOK_NAME='"+bv.getBook_name()+"',"
					+ " BOOK_SUMMARY='"+bv.getBook_summary()+"',"
					+ " BOOK_PUBLISHER='"+bv.getBook_publisher()+"',"
					+ " BOOK_STATE='"+bv.getBook_state()+"',"
					+ " ISACTIVATE='"+bv.getIsActivate()+"',"
					+ " BOOK_LGU='"+bv.getBook_LGU()+"'"
					+ " WHERE BOOK_ID="+bv.getBook_id();
		return dbUtil.updateDB(sql);
	}

	@Override
	public int deleteBook(int book_id) {
		String sql = "DELETE BOOKVO" 
				   + " WHERE BOOK_ID =" + book_id;
		return dbUtil.updateDB(sql);
	}
	
	@Override
	public int bookExcelAdd(List<Map<Integer,String>> list) {
		int result = 0;
		List<BookVO> booklist = new ArrayList<>();
		BookVO bv = null;
		for (int i = 0; i < list.size(); i++) {
			bv = new BookVO();
			//bv.setBook_LGU(Integer.parseInt(list.get(i).get(0)));
			bv.setBook_LGU(1);
			bv.setBook_name(list.get(i).get(1));
			bv.setBook_author(list.get(i).get(2));
			bv.setBook_publisher(list.get(i).get(3));
			bv.setBook_summary(list.get(i).get(4));
			booklist.add(bv);
		}
		
		for(int i = 0; i<booklist.size(); i++){
			String sql = "INSERT INTO BOOKVO (book_id, book_lgu, book_name,book_author,book_publisher,book_summary,BOOK_STATE,ISACTIVATE)"
						+ " VALUES (BOOK_SEQ.NEXTVAL,'"
						+ booklist.get(i).getBook_LGU() +"','"
						+ booklist.get(i).getBook_name() +"','"
						+ booklist.get(i).getBook_author() +"','"
						+ booklist.get(i).getBook_publisher() +"','"
						+ booklist.get(i).getBook_summary() +"','T','T')";
			result = dbUtil.updateDB(sql);
		}
		return result;
	}
	
	@Override
	public List<BookVO> bookExcelout() {
		List<BookVO> list = new ArrayList<>();
		BookVO bv = null;
		ResultSet rs = null;
		
		String sql = "select * from bookvo";
		rs = dbUtil.readDB(sql);
		try {
			while(rs.next()){
				bv = new BookVO();
				bv.setBook_id(rs.getInt("book_id"));
				bv.setBook_author(rs.getString("book_author"));
				bv.setBook_name(rs.getString("book_name"));
				bv.setBook_summary(rs.getString("book_summary"));
				bv.setBook_publisher(rs.getString("book_publisher"));
				bv.setBook_LGU(rs.getInt("book_LGU"));
				list.add(bv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbUtil.destroy(rs);
		return list;
	}
}
