package package_Main;

import java.util.*;

import package_VO.*;
import package_Database.Database;

public class IServiceImpl implements IService{
	Database db = Database.getDatabase();
	
	@Override
	public int idUniqCheck(String mem_id) {
		return 0;
	}

	@Override
	public boolean createMember(MemberVO mv) {
		if(db.createMember(mv)){
			return true;
		}
		return false;
	}

	@Override
	public MemberVO loginMatch(Map<Integer, String> id, Map<Integer, String> pw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NoticeVO> noticeList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoticeVO openNoDetail(int input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NoticeVO> hopeList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hopeListAdd(HopeVO hv) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<BookVO> bookList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookLGUVO> bookLGUList() {
		// TODO Auto-generated method stub
		return null;
	}
/////////////////////////////마이페이지////////////////////////
	@Override
	public MemberVO printProfile(String mem_id) {
		return db.readMember(mem_id);
	}
	
	@Override
	public void withdrawal(MemberVO mv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<RentalVO> printRentalList(String mem_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void returnBook(String book_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ReserveVO> printBookList(String mem_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void noiceReadMethod(NoticeVO nv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String noiceAddMethod(NoticeVO nv) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void noiceDeltleMethod(NoticeVO nv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hopeBookAddMethod(HopeVO hv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hopeBookeDeltleMethod(HopeVO hv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bookListMethod(BookVO bv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bookAddMethod(BookVO bv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void memberListMethod(MemberVO mv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void blackAddMethod(BlackListVO bv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void blackListReadView(BlackListVO bv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void blackDeltleMethod(BlackListVO bv) {
		// TODO Auto-generated method stub
		
	}

}
