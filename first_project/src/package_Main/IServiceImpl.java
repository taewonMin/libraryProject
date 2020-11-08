package package_Main;

import java.util.*;

import package_VO.*;
import package_Database.Database;

import java.text.DateFormat;
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

	
	/**
	 * @author 조애슬
	 */
	@Override
//	public List<NoticeVO> noticeList() {
	public boolean noticeList() {
		return db.noticeList();
	}
	
	/**
	 * @author 김태규
	 */
//	@Override
//	public List<NoticeVO> noticeList() {
//		
//		return null;
//	}

	@Override
	public boolean openNoDetail(int input) {
		return db.openNoDetail(input);
	}

	/**
	 * @author 조애슬
	 */
	@Override
	public boolean hopeList() {
		return db.hopeList();
	}
	
	/**
	 * @author 김태규
	 */
//	@Override
//	public List<HopeVO> hopeList() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	/**
	 * @author 조애슬
	 */
	@Override
	public boolean hopeListAdd(Map<String,String> params) {
		return db.hopeListAdd(params);
	}
	
	/**
	 * @author 김태규
	 */
//	@Override
//	public boolean hopeListAdd(HopeVO hv) {
//		// TODO Auto-generated method stub
//		return false;
//	}
	
	/**
	 * 희망도서 상세보기 
	 * @author 조애슬
	 */
	@Override
	public boolean hopeDetailView(int hopeNo) {
		
		return db.hopeDetailView(hopeNo);
	}
	
	/**
	 * 희망도서 삭제하기
	 */
	@Override
	public boolean hopeRemoveView(String mem_id,int hopeNo) {
		
		return db.hopeRemoveView(mem_id,hopeNo);
	}

	@Override
	public BookVO readBook(String book_id){
		return db.readBook(book_id); 
	}
	
////////////////////////검색///////////////////	
	@Override
	public List<BookVO> bookNameListVer2(String bo_name) {
		return db.bookNameListVer2(bo_name);
		
	}
	
	@Override
	public List<BookVO> bookNameListVer1(int bo_id) {

		return db.bookNameListVer1(bo_id);
	}
	@Override
	public List<BookVO> booklList() {
		
		return null;
	}
	@Override
	public List<BookLGUVO> bookLGUList() {

		return db.bookLGUList();
	}
	@Override
	public List<BookVO> booklList(String bookID) {
		return db.BookListID(bookID);
	}

	/**
	 * 장르 선택 메서드
	 * @author 송지은
	 */
	@Override
	public int scanCID() {
		Scanner scn = new Scanner(System.in);
		System.out.println("───────────────────────────────────────────────────────");
		System.out.println("선택할 장르의 번호를 입력해주세요.");
		while (true) {
			try {
				String input = scn.next();
				if (RegEx.checkScanCID(input)) {
					return Integer.parseInt(input);
				}else {
					System.out.println("숫자 1개만 입력 가능합니다.");
					continue;
				}
			} catch (InputMismatchException e) {
				System.out.println("숫자 1개만 입력 가능합니다.");
			}
		}
	}
	
	/**
	 * 도서 아이디 선택 메서드
	 * @author 송지은
	 */
	@Override
	public String inputBook() {
		Scanner scn = new Scanner(System.in);
		System.out.println("───────────────────────────────────────────────────────");
		System.out.println("선택하실 도서 번호를 입력해주세요.");
		while (true) {
			try {
				String bo_id = scn.next();
				return bo_id;	
			}catch (InputMismatchException e) {
				System.out.println("숫자만 입력 가능합니다.");
			}
		
		}
		
	}
	/**
	 * 도서 아이디 선택 메서드2
	 * @author 송지은
	 */
	@Override
	public String inputBook2() {
		Scanner scn = new Scanner(System.in);
		System.out.println("───────────────────────────────────────────────────────");
		System.out.println("선택하실 도서 번호를 입력해주세요.");
		while (true) {
			try {
				String bo_name = scn.next();
				return bo_name;	
			}catch (InputMismatchException e) {
				System.out.println("숫자만 입력 가능합니다.");
			}
		
		}
		
	}
	
//대여 및 예약	
	@Override
	public RentalVO createRentalVO(Map<String, String> map) {
		RentalVO rv = new RentalVO();
		rv.setMem_id(map.get("mem_id"));
		rv.setBook_id(map.get("book_id"));
		rv.setRental_start(db.getDate());
		
		//반납일 Map
		Map<String, Object> dateInfo = new HashMap<>();
		dateInfo.put("day", rv.getRental_start());
		dateInfo.put("addDay", 14);
		
		rv.setRental_end(db.getEndDate(dateInfo));
		db.createRental(rv);
		return rv;
	}
	
	@Override
	public String createReserveVO(Map<String, String> map) {
		ReserveVO rsv = new ReserveVO();
		rsv.setMem_id(map.get("mem_id"));
		rsv.setBook_id(map.get("book_id"));
		db.createReserve(rsv);
		
		//해당 도서를 대여한 사람의 반납일자 받아오기
		RentalVO rv = db.readRentalVO(map.get("book_id"));
		return rv.getRental_end();
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
	public int returnBook(String book_id) {
		RentalVO rv = db.readRentalVO(book_id);
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
	public boolean noticList(int num) {
		return db.noticList(num);
	}

	@Override
	public boolean noiceAddMethod(Map<String, String> params) {
	   return noiceAddMethod(params);
	}

	@Override
	public boolean deleteNotice(int num) {
		return db.deleteNotice(num);
	}

	@Override
	public boolean hopeBookAddMethod(int num) {
		return db.hopeBookAddMethod(num);
		
	}

	@Override
	public boolean hopeBookeDeltleMethod(int num) {
		return db.hopeBookeDeltleMethod(num);
	}

	@Override
	public void bookAddMethod(BookVO bv) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean bookListMethod() {
		return db.bookListMethod();
	}

	@Override
	public boolean noticList() {
		return db.noticeList();
		
	}
	@Override
	public boolean memList() {
		return db.memList();
	}

	@Override
	public boolean blackListList() {
		return db.blackListList();
	}

	@Override

	public boolean createBlackList(String id) {
		return db.createBlackList(id);
	}

	@Override
	public boolean blackDeltleMethod(String id, int num) {
		return db.blackDeltleMethod(id,num);
	}

	@Override
	public boolean bookAddMethod() {
		return db.bookListMethod();
	}
}
