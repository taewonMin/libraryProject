package package_Main;

import java.util.*;
import package_VO.*;
import package_Database.Database;
import java.text.SimpleDateFormat;

public class IServiceImpl implements IService{
	Database db = Database.getDatabase();
	
	/**
	 * 아이디중복체크. 중복값이 있으면 증가된 count가 리턴된다
	 * @param mem_id
	 * @return int
	 * @author 조애슬
	 * @since 2020-11-06 
	 */
	@Override
	public int idUniqCheck(String mem_id) {
		
		return db.idUniqCheck(mem_id);
	}
	
//회원 CRUD
	/**
	 * 회원가입 입력값을 MemberVO 객체로 묶은걸 DB로 보내는 메서드
	 * @param params
	 * @return params
	 * @author 조애슬
	 * @since 2020-11-04
	 */
	public boolean createMember(MemberVO params) {
		
		return db.createMember(params);
	}
	
	@Override
	public MemberVO readMember(String mem_id) {
		return db.readMember(mem_id);
	}
	
	@Override
	public int updateMember(Map<String, String> myInfo) {
		return db.updateMember(myInfo);
	}
	
	@Override
	public int deleteMember(String mem_id) {
		return db.deleteMember(mem_id);
	}

	
	/**
	 * 로그인 입력받은거 MemberVO와 비교
   	* @param Map<> in, Map<> pw
   	* @return MemberVO //MemberVO nowmember를 null에서 로그인한 회원으로 바꿔주기위해
   	* @since 2020-11-04
   	*/
	@Override
	public MemberVO loginMatch(Map<String, String> params) {
		
		return db.loginMatch(params);
	}
	
	/**
    * 로그인 입력받은거 AdminVO와 비교
    * @param params
    * @return boolean
    */
	@Override
	public boolean adminMatch(Map<String, String> params) {
		// TODO Auto-generated method stub
		return db.adminMatch(params);
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
	public List<HopeVO> hopeList() {
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
/////////////////////////////마이페이지//////////////////////////
	
	@Override
	public List<Map<String, String>> readRentalList(String mem_id) {
		List<RentalVO> rvList = db.readRental(mem_id);
		List<Map<String,String>> result = new ArrayList<>();
		int rental_no = 0;
		for(RentalVO rv : rvList){
			BookVO bv = db.readBook(rv.getBook_id());
			if(bv != null){
				Map<String, String> map = new HashMap<>();
				map.put("rental_no", String.valueOf(++rental_no));
				map.put("book_id", bv.getBook_id());
				map.put("book_name", bv.getBook_name());
				map.put("book_author", bv.getBook_author());
				map.put("book_publisher", bv.getBook_publisher());
				map.put("start_date", rv.getRental_start());
				map.put("end_date", rv.getRental_end());
				result.add(map);
			}
		}
		return result;
	}

	@Override
	public int returnBook(Map<String,String> map) {
		RentalVO rv = db.readRentalVO(map);
		return db.deleteRental(rv);
	}

	@Override
	public List<Map<String, String>> readReserveList(String mem_id) {
		List<ReserveVO> rsvList = db.readReserve(mem_id);
		List<Map<String,String>> result = new ArrayList<>();
		int rsv_no = 0;
		for(ReserveVO rsv : rsvList){
			BookVO bv = db.readBook(rsv.getBook_id());
			if(bv != null){
				Map<String, String> map = new HashMap<>();
				map.put("rsv_no", String.valueOf(++rsv_no));
				map.put("book_id", bv.getBook_id());
				map.put("book_name", bv.getBook_name());
				map.put("book_author", bv.getBook_author());
				map.put("book_publisher", bv.getBook_publisher());
				map.put("end_date", db.getReturnDate(rsv.getBook_id()));
				result.add(map);
			}
		}
		return result;
	}
	
	@Override
	public int cancelReserve(Map<String, String> map) {
		List<ReserveVO> rsvlist = db.readReserve(map.get("mem_id"));
		for(ReserveVO temp : rsvlist){
			if(temp.getBook_id().equals(map.get("book_id"))){
				return db.deleteReserve(temp);
			}
		}
		return 0;
	}
	

	@Override
	public void noiceReadMethod(int num) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void noiceAddMethod(NoticeVO nv) {
	   
	}

	@Override
	public boolean noiceDeltleMethod(int num) {
		return db.deleteNotice(num);
	}

	@Override
	public boolean hopeBookAddMethod(int num) {
		// TODO Auto-generated method stub
		return false;
		
	}

	@Override
	public void hopeBookeDeltleMethod(int num) {
	}

	@Override
	public void bookAddMethod(BookVO bv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<NoticeVO> noticList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookVO> booklList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberVO> memList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BlackListVO> blackList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void blackAddMethod(int num) {
				
	}

	@Override
	public void blackDeltleMethod(BlackListVO bv) {
		// TODO Auto-generated method stub
		
	}


}
