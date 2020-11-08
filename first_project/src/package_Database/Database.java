package package_Database;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import package_VO.*;

public class Database {
	private AdminVO admin = new AdminVO();	// 관리자계정	
	private List<MemberVO> 		memberList		= new ArrayList<MemberVO>();	//회원 목록
	private List<BookLGUVO>     bookLGUList     = new ArrayList<BookLGUVO>();	//도서분류 목록
	private List<BookVO>        bookList      	= new ArrayList<BookVO>();		//도서 목록
	private List<NoticeVO> 		noticeList 		= new ArrayList<NoticeVO>();	//공지글 목록
	private List<BlackListVO>	blackList 		= new ArrayList<BlackListVO>();	//블랙리스트 목록
	private List<HopeVO> 		hopeList 		= new ArrayList<HopeVO>();		//희망도서 목록
	private List<RentalVO> 		rentalList 		= new ArrayList<RentalVO>();	//대여도서 목록
	private List<ReserveVO> 	reserveList 	= new ArrayList<ReserveVO>();	//예약도서 목록
	
	private static Database database = new Database();
	
	public static Database getDatabase() {
		return database;
	}
	
	/**
	 * 현재 날짜 가져오기 메서드
	 * @return 현재 날짜를 문자열로 반환
	 * @author 민태원
	 * @since 2020.11.07
	 */
	public String getDate(){
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(new Date());
        return df.format(cal.getTime());
	}
	
	/**
	 * n일 후의 날짜 구하는 메서드
	 * @param dayInfo 더할 날짜 문자열, 더할 날의 수를 Map으로 반환
	 * @return 반납일자를 문자열로 반환
	 * @author 민태원
	 * @since 2020.11.07
	 */
	public String getEndDate(Map<String, Object> dayInfo){
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = fm.parse((String)dayInfo.get("day"));
		} catch (ParseException e) {
			System.out.println("날짜변환 실패..");
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, (int)dayInfo.get("addDay"));
		return fm.format(cal.getTime());
	}
	
	/**
	 * 아이디 중복체크 메서드
	 * @param mem_id 
	 * @return boolean
	 * @author 조애슬
	 * @since 2020-11-06
	 */
	public int idUniqCheck(String mem_id){
		int count = 0;//중복이면 증가
		for(MemberVO member : memberList){
			if(member.getMem_id().equals(mem_id)){
				count++;
				break;//하나중복이면 바로빠져나옴
			}
		}
		
		return count;
	}
	
	/**
	 * 로그인 정보 확인 
	 * @return 회원정보 있을 시 memberVO반환
	 * @author 조애슬
	 * @since 2020-11-04
	 */
	public MemberVO loginMatch(Map<String, String> params){
		String mem_id = params.get("mem_id");
		String mem_pw = params.get("mem_pw");
		MemberVO logInMember = null;
		
		for(MemberVO member : memberList){
			if(member.getMem_id().equals(mem_id) 
					&& member.getMem_pw().equals(mem_pw)
					&& member.isActivate()==true){	
				logInMember = member;				
				break;
			}else if(admin.getadmin_id().equals(mem_id)
					&& admin.getadmin_pw().equals(mem_pw)
					&& admin.isActivate()==true){
				logInMember = member;
				break;
			}
		}
		return logInMember;
	}
	
	/**
	 * 관리자 정보 확인
	 * @param params
	 * @return 관리자일시 true
	 */
	public boolean adminMatch(Map<String, String> params) {
		String admin_id = params.get("mem_id");
		String admin_pw = params.get("mem_pw");
	
		if(admin.getadmin_id().equals(admin_id)
				&&admin.getadmin_pw().equals(admin_pw)
				&&admin.isActivate()==true){
			return true;
		}
		return false;
	}
	
	
//회원 CRUD
	/**
    * 회원가입을 위한 메서드
    * @param params 멤버 한명의 모든 정보
    * @return 추가에 성공하였으면 true 그렇지 않으면 false
    * @author 조애슬
    * @since 2020.11.04
    */
   public boolean createMember(MemberVO params){
      if(memberList.add(params)){//멤버리스트(db)에 가입한 회원의 정보 넣고 성공시 true
    	  for (MemberVO member : memberList) {
    	  }//확인
    	  return true;
      	}
      return false;
   }
   
   /**
    * 회원정보 조회 메서드
    * @param mem_id 조회할 회원의 아이디
    * @return 조회할 회원의 모든 정보 반환
    * @author 민태원
    * @since 2020.11.05
    */
   	public MemberVO readMember(String mem_id){
   		MemberVO mv = null;
   		for(MemberVO temp : memberList){
   			if(temp.getMem_id().equals(mem_id)){
   				mv = temp;
   			}
   		}
   		return mv;
   	}
   	
   	/**
     * 회원정보 갱신 메서드
     * @param myInfo 수정할 내 정보를 Map으로 받아 업데이트
     * @return 갱신 성공하면 1, 아니면 0
     * @author 민태원
     * @since 2020.11.06
     */
    public int updateMember(Map<String, String> myInfo){
    	MemberVO mv = readMember(myInfo.get("mem_id"));
    	if(mv!=null){
    		if(myInfo.get("mem_pw")!=null){
    			mv.setMem_pw(myInfo.get("mem_pw"));
    			return 1;
    		}
    		if(myInfo.get("mem_email")!=null){
    			mv.setMem_email(myInfo.get("mem_email"));
    			return 1;
    		}
    		if(myInfo.get("mem_tel")!=null){
    			mv.setMem_tel(myInfo.get("mem_tel"));
    			return 1;
    		}
    	}
      	return 0;
	}
   
   /**
    * 회원정보 삭제 메서드
    * @param mem_id 탈퇴할 멤버의 아이디
    * @return 비활성화 성공하면 1, 아니면 0
    * @author 민태원
    * @since 2020.11.06
    */
   	public int deleteMember(String mem_id){
   		MemberVO mv = readMember(mem_id);
   		if(mv==null)
   			return 0;
   		mv.setActivate(false);
   		return 1;
   	}
   	
//도서 CRUD
   	/**
     * 도서추가를 위한 메서드
     * @param params 도서의 모든 정보
     * @return 추가에 성공하였으면 true 그렇지 않으면 false
     * @author 민태원
     * @since 2020.11.06
     */
    public boolean createBook(BookVO params){
       if(bookList.add(params)){
          return true;
       }
       return false;
    }
   	
   	/**
     * 도서정보 조회 메서드
     * @param book_id 조회할 도서의 아이디
     * @return 조회할 도서의 모든 정보 반환
     * @author 민태원
     * @since 2020.11.06
     */
	public BookVO readBook(String book_id){
		BookVO bv = null;
		for(BookVO temp : bookList){
			if(temp.getBook_id().equals(book_id)){
				bv = temp;
			}
		}
		return bv;
	}
	
	/**
    * 도서정보 삭제 메서드
    * @param book_id 삭제할 도서의 아이디
    * @return 비활성화 성공하면 1, 아니면 0
    * @author 민태원
    * @since 2020.11.06
    */
   	public int deleteBook(String book_id){
   		BookVO bv = readBook(book_id);
   		if(bv==null)
   			return 0;
   		bv.setActivate(false);
   		return 1;
   	}
   	
///////////////////////책 분류 CRUD///////////////////////
   	/**
	 * 도서명 검색 메서드
	 * @author 송지은
	 * @param bo_name 도서명
	 * @return bo_name book_name List로 변환
	 */
	public List<BookVO> bookNameListVer2(String bo_name) {
		List<BookVO> result = new ArrayList<>();
		for (BookVO dbBook : bookList) {
			if (dbBook.getBook_name().replace(" ", "").contains(bo_name)) {
				result.add(dbBook);
			}

		}
		return result;
	}
	
	/**
	 * 도서 조회 메서드
	 * @author 송지은
	 * @param bo_id 도서 아이디
	 * @return book_LGU List로 반환
	 */
	public List<BookVO> bookNameListVer1(int bo_id) {
		List<BookVO> result = new ArrayList<>();
		for(BookVO bookDB : bookList) {
			if(bookDB.getBook_LGU().equals(String.valueOf(bo_id))) {
				result.add(bookDB);
			}	
		}
		return result;
	}

	/**
	 * 장르카테고리 메서드
	 * @author 송지은
	 * @return 전체 bookLGUVO List형 반환
	 */
	public List<BookLGUVO> bookLGUList() {
		List<BookLGUVO> result = new ArrayList<>();
		for(BookLGUVO bookDB : bookLGUList) {
			result.add(bookDB);
		}
		return result;
	}
	
	/**
	 * 도서 상세설명 메서드
	 * @author 송지은
	 * @param bookID 도서ID
	 * @return BookVO List로 반환
	 */
	public List<BookVO> BookListID(String bookID){
		List<BookVO> bo = new ArrayList<>();
		for(BookVO dbSlr: bookList) {
			if(dbSlr.getBook_id().equals(bookID)) {
				bo.add(dbSlr);
			}
		}
		return bo;
	}

///공지 CRUD
   	/**
   	 * 공지 추가 메서드
   	 * @param nv 저장할 공지의 모든 정보
   	 * @return 추가에 성공하였으면 true 그렇지 않으면 false
   	 * @author 민태원
   	 * @since 2020.11.05
   	 */
   	public boolean createNotice(NoticeVO nv){
        if(noticeList.add(nv)){
           return true;
        }
        return false;
     }
   	
   	/**
   	 * 공지 조회 메서드 
   	 * @param nv 조회할 공지의 모든 정보
   	 * @author 조애슬
   	 * @since 2020.11.06
   	 */
   	public void readNotice(NoticeVO nv){
   		System.out.print("["+nv.getNotice_no()+"]");
   		System.out.println("\t작성일 : "+nv.getNotice_date());
   		System.out.println("제목 : " + nv.getNotice_title());
   		System.out.println("-------------------------------------------------------");
   	}
   	
   	/**
   	 * 공지 상세조회 메서드 
   	 * @param nv 조회할 공지의 모든 정보
   	 * @author 조애슬
   	 * @since 2020.11.06
   	 */
   	public void readDetailNotice(NoticeVO nv){
   		this.readNotice(nv);
   		System.out.println("내용 : " + nv.getNotice_content()); //추가
   		System.out.println("-------------------------------------------------------");
   	}
   	
   	/**
   	 * 공지 삭제 메서드
   	 * @param nv 삭제할 공지의 모든 정보
   	 * @author 민태원
   	 * @since 2020.11.05
   	 */
   	public void deleteNotice(NoticeVO nv){
 	   if (noticeList.remove(nv)) {
       }
	}
   	
//대여 CRUD
   	/**
   	 * 나의 대여목록에 추가하기
   	 * @param rv 저장할 대여 정보
   	 * @return 추가에 성공하였으면 true, 아니면 false
   	 * @author 민태원
   	 * @since 2020.11.07
   	 */
   	public boolean createRental(RentalVO rv){
        if(rentalList.add(rv)){
           return true;
        }
        return false;
   	}
   	
   	/**
   	 * 회원의 대여목록 조회하기
   	 * @param mem_id 조회할 회원의 id
   	 * @return 검색된 회원의 대여 정보를 List로 반환
   	 * @author 민태원
   	 * @since 2020.11.06
   	 */
   	public List<RentalVO> readRental(String mem_id){
   		List<RentalVO> list = new ArrayList<>();
   		for(RentalVO temp : rentalList){
   			if(temp.getMem_id().equals(mem_id)){
   				list.add(temp);
   			}
   		}
   		return list;
	}
   	
   	/**
   	 * 대여테이블의 튜플 "하나만" 검색
   	 * @param book_id 조회할 도서의 아이디
   	 * @return 조회된 대여 튜플 반환
   	 * @author 민태원
   	 * @since 2020.11.06
   	 */
   	public RentalVO readRentalVO(String book_id){
   		for(RentalVO rv : rentalList){
   			if(rv.getBook_id().equals(book_id)){
   				return rv;
   			}
   		}
   		return null;
   	}
   	
   	/**
   	 * 대여목록에서 삭제하기
   	 * 삭제된 도서를 예약한 사람이 있으면 그 사람에게 바로 대여
   	 * @param nv 삭제할 대여목록의 대여 정보
   	 * @return 반납에 성공하면 1, 아니면 0
   	 * @author 민태원
   	 * @since 2020.11.06
   	 */
   	public int deleteRental(RentalVO rv){
   		BookVO bv = readBook(rv.getBook_id());
 	   if (rentalList.remove(rv)) {
 		   //삭제된 도서를 예약한 사람이 있는지 확인 
 		   ReserveVO rsv = checkReserve(bv.getBook_id());
 		   if(rsv != null){
 			   //있으면 가장 날짜가 빠른 사람과 도서를 대여테이블에 추가
 			   RentalVO newRsv = new RentalVO();
 			   //반납일 Map
 			   Map<String, Object> dayInfo = new HashMap<>();
 			   dayInfo.put("day", rv.getRental_end());
 			   dayInfo.put("addDay", 14);
 			   
 			   newRsv.setRental_start(rv.getRental_end());
 			   newRsv.setRental_end(getEndDate(dayInfo));
 			   newRsv.setMem_id(rsv.getMem_id());
 			   newRsv.setBook_id(rsv.getBook_id());
 			   createRental(newRsv);
 			   
 			   //대출된 도서는 예약테이블에서 삭제
 	 		   Map<String, String> map = new HashMap<>();
 	 		   map.put("mem_id", rsv.getMem_id());
 	 		   map.put("book_id", rsv.getBook_id());
 	 		   deleteReserve(readReserveVO(map));
 		   }else {
 			  bv.setBook_state(true);	//대여상태 가능으로 변경
 		   }
 		   return 1;
       }
 	   return 0;
	}

//예약 CRUD
   	/**
   	 * 내 예약목록에 추가하기
   	 * @param rv 예약목록에 추가할 예약정보
   	 * @return 추가에 성공하였으면 true 그렇지 않으면 false
   	 * @author 민태원
   	 * @since 2020.11.05
   	 */
   	public boolean createReserve(ReserveVO rsv){
        if(reserveList.add(rsv)){
           return true;
        }
        return false;
     }
   	
   	/**
   	 * 회원의 예약 목록 조회하기
   	 * @param mem_id 회원 아이디를 입력받아 회원의 예약목록 조회
   	 * @return 회원의 예약 목록 반환
   	 * @author 민태원
   	 * @since 2020.11.06
   	 */
   	public List<ReserveVO> readReserve(String mem_id){
   		List<ReserveVO> list = new ArrayList<>();
   		for(ReserveVO temp : reserveList){
   			if(temp.getMem_id().equals(mem_id)){
   				list.add(temp);
   			}
   		}
   		return list;
	}
   	
   	/**
   	 * 예약테이블의 튜플 "하나만" 검색
   	 * @param memData (회원아이디, 도서아이디)를 저장하고있는 Map
   	 * @return 조회된 예약 튜플 반환
   	 * @author 민태원
   	 * @since 2020.11.06
   	 */
   	public ReserveVO readReserveVO(Map<String,String> memData){
   		for(ReserveVO rsv : reserveList){
   			if(rsv.getMem_id().equals(memData.get("mem_id")) && 
   					rsv.getBook_id().equals(memData.get("book_id"))){
   				return rsv;
   			}
   		}
   		return null;
   	}
   	
   	/**
   	 * 예약목록에서 삭제하기
   	 * @param rv 삭제할 대여목록의 대여 정보
   	 * @return 삭제에 성공하면 1, 아니면 0
   	 * @author 민태원
   	 * @since 2020.11.05
   	 */
   	public int deleteReserve(ReserveVO rv){
 	   if (reserveList.remove(rv)) {
 		   return 1;
       }
 	   return 0;
	}
   	
   	/**
   	 * 대여테이블에서 도서의 아이디를 조회하여 해당 도서의 반납일자를 반환
   	 * @param book_id 조회할 도서의 아이디
   	 * @return 조회된 도서의 반납일자 반환
   	 * @author 민태원
   	 * @since 2020.11.06
   	 */
   	public String getReturnDate(String book_id){
   		for(RentalVO rv : rentalList){
   			if(rv.getBook_id().equals(book_id)){
   				return rv.getRental_end();
   			}
   		}
   		return null;
   	}
   	
   	/**
   	 * 예약테이블에서 도서의 아이디를 조회하여 예약한 사람이 있는지 체크
   	 * @param book_id 조회할 도서의 아이디
   	 * @return 예약정보 반환
   	 * @author 민태원
   	 * @since 2020.11.06
   	 */
   	public ReserveVO checkReserve(String book_id){
   		for(ReserveVO rsv : reserveList){
   			if(rsv.getBook_id().equals(book_id)){
   				return rsv;
   			}
   		}
   		return null;
   	}
   	
/////////////////////////게시판//////////////////////////////	
   	/**
	 * 공지사항 게시판을 출력한다.
	 * @author 조애슬
	 * @param	
	 * @return	NoticeList를 반환
	 */
	public boolean noticeList(){
		for (int i = 0; i < noticeList.size(); i++) {
			readNotice(noticeList.get(i));
		}
		return true;
	}
	
	/**
	 * 공지사항 상세글을 표시한다.
	 * @param input
	 * @return
	 * @author 조애슬
	 * @since 2020-11-06
	 */
	public boolean openNoDetail(int input){
		if(input<=noticeList.size()){//입력한 글번호가 공지사항 범위내이면
			readDetailNotice(noticeList.get(input-1));
			return true;
		}else{
			System.out.println("올바른 글번호를 입력해 주세요");
			return false;
		}
	}
	
	///hope Read//
	/**
	 * 희망도서목록을 출력한다
	 * @author 송지은
	 * @return 
	 * @since 2020-11-05
	 */
	public boolean readHope(HopeVO hv){ 
		if(hopeList.contains(hv)){      
			System.out.print("["+hv.getHope_id()+"] ");
			System.out.println("작성자 : "+hv.getMem_id());
			System.out.println("희망하는 책 : "+hv.getHope_name());
			System.out.println("작가 : "+hv.getHope_author());
			System.out.println("출판사 : "+hv.getHope_publisher());
			System.out.println("희망하는 이유 : "+ hv.getHope_content());
	   		System.out.println("-------------------------------------------------------");
        }
		return true;
	}
	
	/**
	 * 희망도서목록 상세를 출력한다
	 * @author 송지은
	 * @return 
	 * @since 2020-11-05
	 */
	public boolean readDetailHope(HopeVO hv){ 
		if(hopeList.contains(hv)){
			System.out.println("");
			this.readHope(hv);
		}
		return true;
	}
	
	/**
	 * 희망도서 상세보기 메서드
	 * @param hopeNo
	 * @return
	 */
	public boolean hopeDetailView(int hopeNo){
		if(0< hopeNo && hopeNo<=hopeList.size()){
			readDetailHope(hopeList.get(hopeNo-1));
			return true;	
		}else{
			System.out.println("올바른 글번호를 입력해주세요");
			return false;
		}
	}
	
	/**
	 * 희망도서목록 전체를 출력한다
	 * @return
	 * @author 조애슬
	 * @since 2020-11-06
	 */
	public boolean hopeList(){
		for (int i = 0; i < hopeList.size(); i++) {
			readHope(hopeList.get(i));
		}
		return true;
	}
	
	/**
	 * 희망도서를 db에 등록하고 정상등록되면  true를 리턴
	 * @author 조애슬
	 * @since 2020-11-05
	 */
	public boolean hopeListAdd(Map<String, String> params){
		
		int hope_id = hopeList.size()+1;
		String hope_memId = params.get("hope_memId");
		String hope_name = params.get("hope_name");
		String hope_author = params.get("hope_author");
		String hope_publisher = params.get("hope_publisher");
		String hope_content = params.get("hope_content");
				
		HopeVO hv = new HopeVO();
		hv.setMem_id(hope_memId);
		hv.setHope_name(hope_name);
		hv.setHope_author(hope_author);
		hv.setHope_publisher(hope_publisher);
		hv.setHope_content(hope_content);
		hv.setHope_id(hope_id+"");
		
		hopeList.add(hv);
		/*if(hopeList.add(hv)){
			System.out.println("희망도서가 정상적으로 등록되었습니다.");
			return true;
		}*/
		return true;
	}
	
	/**
	 * 희망도서 삭제하기 메서드
	 * @param mem_id
	 * @param hopeNo
	 * @return 본인의 글이라 삭제에 성공하면 true, 본인이 아니면 false
	 */
	public boolean hopeRemoveView(String mem_id, int hopeNo){
		if(hopeNo > hopeList.size())
			return false;
		for(HopeVO hv : hopeList){
			for(MemberVO mv : memberList){
				if(hopeList.get(hopeNo-1).getMem_id().equals(mem_id)){
					hopeList.remove(hopeNo-1);
					return true;
				}
			}//memberlist
		}//hopelist
		return false;
	}

///////////////////////////////////////관리자////////////////////////////////
	/**
	 * 공지 전체조회 메서드
	 * 
	 * @param num
	 * @author 김태규
	 * @since 2020.11.05
	 */
	public boolean noiceReadMethod(int num) {
		for (int i = 0; i < noticeList.size(); i++) {
			allNoticList(noticeList.get(i));
		}
		return true;
	}

	public boolean allNoticList(NoticeVO nv) {
		if (noticeList.contains(nv)) {
			System.out.println("번호\t[" + nv.getNotice_no() + "]");
			System.out.println("제목\t[" + nv.getNotice_title() + "]");
			System.out.println("내용\t[" + nv.getNotice_content() + "]");
			System.out.println("작성일자\t[" + nv.getNotice_date() + "]");
			System.out.println("관리자\t[" + nv.getAdmin_id() + "]");
			System.out.println("==============================");
		}
		return true;
	}

	/**
	 * 공지 상세보기 메서드
	 * 
	 * @param num
	 * @author 김태규
	 * @since 2020.11.05
	 */
	public boolean noticList(int num) {
		if (num <= noticeList.size()) {
			allNoticList(noticeList.get(num - 1));
			return true;
		} else {
			System.out.println("올바른 글번호를 입력해주세요");
			return false;
		}
	}

	/**
	 * 공지 추가 메서드
	 * 
	 * @param params
	 * @author 김태규
	 * @since 2020.11.05
	 */

	public boolean noiceAddMethod(Map<String, String> params) {

		Date time = new Date(); // 날짜를 구한다.
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-mm-dd");// 날짜 형식
		String timeset = fm.format(time);

		int notice_no = (noticeList.size() + 1);
		String notice_title = params.get("notice_title");
		String notice_content = params.get("notice_content");
		String notice_date = params.get("notice_date");

		NoticeVO nv = new NoticeVO();
		nv.setNotice_no(notice_no);
		nv.setNotice_title(notice_title);
		nv.setNotice_content(notice_content);
		nv.setNotice_date(timeset);
		nv.setAdmin_id("admin");

		noticeList.add(nv);

		return true;
	}

	/**
	 * 공지 삭제 메서드
	 * 
	 * @param num
	 * @author 김태규
	 * @since 2020.11.05
	 */
	public boolean deleteNotice(int num) {
		NoticeVO nv = null;
		for (NoticeVO nv2 : noticeList) {
			if (noticeList.get(num - 1).getNotice_no() == num) {
				noticeList.remove(num - 1);
				return true;
			}
		}
		return false;
	}

//////////////////////희망도서 ///////////////////////////
	/**
	 * 희망도서 승인
	 * 
	 * @param num
	 * @author 김태규
	 * @since 2020-11-05
	 */
	public boolean hopeBookAddMethod(int num) {
		BookVO bv = new BookVO();
		String num2 = num + "";
		for (HopeVO hv : hopeList) {
			if (hopeList.get(num - 1).getHope_id().equals(num2)) {
				bv.setBook_id(hv.getHope_id());
				bv.setBook_name(hv.getHope_name());
				bv.setBook_author(hv.getHope_author());
				bv.setBook_summary("줄거리");
				bv.setBook_publisher(hv.getHope_publisher());
				bv.setBook_LGU("분류");
				bv.setActivate(true);
				bookList.add(bv);
				return true;
			}
		}
		return false;
	}

	/**
	 * 희망도서 부결를 db에 삭제하고 정상삭제되면 true를 리턴
	 * 
	 * @param num
	 * @author 김태규
	 * @since 2020-11-05
	 */
	public boolean hopeBookeDeltleMethod(int num) {
		String num2 = num + "";
		for (HopeVO hv : hopeList) {
			if (hopeList.get(num - 1).getHope_id().equals(num2)) {
				hopeList.remove(num - 1);
				return true;
			}
		}
		return false;
	}

//////////////////////도서관리 CRUD///////////////////////////
	/**
	 * 전체도서 리스트
	 * 
	 * @author 김태규
	 * @since 2020-11-05
	 */
	public boolean bookListMethod() {
		for (int i = 0; i < bookList.size(); i++) {
			bookList(bookList.get(i));
		}
		return true;
	}

	/**
	 * 전체도서 출력 리스트
	 * 
	 * @param BookVO
	 * @author 김태규
	 * @since 2020-11-05
	 */
	public boolean bookList(BookVO bv) {
		if (bookList.contains(bv)) {
			System.out.println("도서코드\t[" + bv.getBook_id() + "]");
			System.out.println("제목\t[" + bv.getBook_name() + "]");
			System.out.println("작가\t[" + bv.getBook_author() + "]");
			System.out.println("줄거리\t[" + bv.getBook_summary() + "]");
			System.out.println("출판사\t[" + bv.getBook_publisher() + "]");
			String lgu = "";
			for(BookLGUVO blgu : bookLGUList) {
				if(blgu.getBook_LGU().equals(bv.getBook_LGU()))
					lgu = blgu.getBook_theme();
					break;
			}
			System.out.println("도서분류\t[" + lgu + "]");
			if (bv.isBook_state() == true) {
				System.out.println("대출가능");
			} else {
				System.out.println("대여중");
			}
			System.out.println("==============================");
		}
		return true;
	}

	/**
	 * 도서 추가
	 * 
	 * @author 김태규
	 * @since 2020-11-05
	 */
	public boolean bookAdd(Map<String, String> params) {

		int book_id = bookList.size() + 1;
		String book_name = params.get("hope_name");
		String book_author = params.get("book_author");
		String book_summary = params.get("book_summary");
		String book_publisher = params.get("book_publisher");
		String book_LGU = params.get("book_LGU");
		boolean book_state = true;
		boolean isActivate = true;

		BookVO bv = new BookVO();
		bv.setBook_id(book_id + "");
		bv.setBook_name(book_name);
		bv.setBook_author(book_author);
		bv.setBook_summary(book_summary);
		bv.setBook_publisher(book_publisher);
		bv.setBook_LGU(book_LGU);
		bv.setBook_state(book_state);
		bv.setActivate(isActivate);

		bookList.add(bv);
		return true;
	}

// /////////////////////회원리스트 CRUD////////////////////////
	
	/**
	 * 회원전체리스트 출력 리스트
	 * 
	 *
	 * @author 김태규
	 * @since 2020-11-05
	 */
	
	public boolean memList() {
		for (int i = 0; i < memberList.size(); i++) {
			readMemList(memberList.get(i));
		}
		return true;
	}

	/**
	 * 회원전체 출력 리스트
	 * 
	 * @param mv
	 * @author 김태규
	 * @since 2020-11-05
	 */
	
	public boolean readMemList(MemberVO mv) {
		if (memberList.contains(mv)) {
			System.out.println("회원ID\t[" + mv.getMem_id() + "]");
			System.out.println("이름\t[" + mv.getMem_name() + "]");
			System.out.println("비밀번호\t[" + mv.getMem_pw() + "]");
			System.out.println("생년월일\t[" + mv.getMem_bir() + "]");
			System.out.println("이메일\t[" + mv.getMem_email() + "]");
			System.out.println("전화번호\t[" + mv.getMem_tel() + "]");
			System.out.println("대출횟수\t[" + mv.getRent_count() + "]");
			System.out.println("==============================");
		}
		return true;
	}

///////////////////////블랙리스트 CRUD////////////////////////


	/**
	 * 블랙리스트 출력 리스트
	 * 
	 * @param BookVO
	 * @author 김태규
	 * @since 2020-11-05
	 */
	public boolean blackListList() {
		for (int i = 0; i < blackList.size(); i++) {
			readvBlackList(blackList.get(i));
		}
		return true;
	}

	public boolean readvBlackList(BlackListVO bv) {
		if (blackList.contains(bv)) {
			System.out.println("[" + bv.getBlack_id() + "]");
			System.out.println("회원ID\t[" + bv.getMem_id() + "]");
			System.out.println("등록일\t[" + bv.getBlack_day() + "]");
			System.out.println("종료일\t[" + bv.getBlack_end() + "]");
			System.out.println("==============================");
		}
		return true;
	}
	/**
	 * 블랙리스트 추가 리스트
	 * 
	 * @param id //추가할 회원의 아이디
	 * @author 김태규
	 * @since 2020-11-05
	 */
	public boolean createBlackList(String id) {
		boolean check = false;
		for(BlackListVO blv : blackList) {
			if(blv.getMem_id().equals(id)) {
				check = true;
				break;
			}
		}
		if(check) {
			// 등록일을 현재 날짜로 입력 종료일은 +7일 더한 날짜를 입력
			int black_id = blackList.size() + 1;
			
			BlackListVO bv = new BlackListVO();
			bv.setBlack_id(black_id);
			bv.setMem_id(id);
			bv.setBlack_day(getDate());
			
			// 종료일 Map
			Map<String, Object> dayInfo = new HashMap<>();
			dayInfo.put("day", bv.getBlack_day());
			dayInfo.put("addDay", 7);
			
			bv.setBlack_end(getEndDate(dayInfo));
			
			readMember(id).setActivate(false);
			
			blackList.add(bv);
		}
		return check;
	}
//	/**
//	 * 블랙리스트 갱신 메서드
//	 */
//	void updateBlackList(BlackListVO bv) {
//		if (blackList.contains(bv.getMem_id())) {
//			(blackList.get(blackList.indexOf(bv.getMem_id()))).setMem_id(bv
//					.getMem_id());
//			(blackList.get(blackList.indexOf(bv.getMem_id()))).setBlack_day(bv
//					.getBlack_day());
//			(blackList.get(blackList.indexOf(bv.getMem_id()))).setBlack_end(bv
//					.getBlack_end());
//		}
//	}

	/**
	 * 블랙리스트삭제 리스트
	 * 
	 * @param mem_id, Num 삭제할 회원 ID,리스트에 나오는 번호
	 * @author 김태규
	 * @since 2020-11-05
	 */
	
	public boolean blackDeltleMethod(String mem_id) {
		for (BlackListVO hv : blackList) {
			if (hv.getMem_id().equals(mem_id)) {
				readMember(mem_id).setActivate(true);
				blackList.remove(hv);
				return true;
			}
		} // hopelist
		return false;
	}
	

	
////////////////////데이터베이스 초기화///////////////////////
	
	// 관리자 초기화
   {
      admin.setadmin_id("admin");
      admin.setadmin_pw("admin123");
   }
      
   // 회원정보 목록 초기화
   {
      MemberVO member1 = new MemberVO();
      member1.setMem_id("abcd01");
      member1.setMem_name("오지란");
      member1.setMem_pw("abcd0123");
      member1.setMem_bir("1980-01-12");
      member1.setMem_email("abcd01@naver.com");
      member1.setMem_tel("010-1234-5678");
      member1.setRent_count(10);
      memberList.add(member1);
      
      MemberVO member2 = new MemberVO();
      member2.setMem_id("qwer09");
      member2.setMem_name("설마음");
      member2.setMem_pw("qwer0912");
      member2.setMem_bir("1999-12-07");
      member2.setMem_email("qwer90@naver.com");
      member2.setMem_tel("010-5555-4678");
      member2.setRent_count(50);
      memberList.add(member2);
      
      MemberVO member3 = new MemberVO();
      member3.setMem_id("nex1032");
      member3.setMem_name("김미소");
      member3.setMem_pw("miso10321");
      member3.setMem_bir("1987-05-01");
      member3.setMem_email("nex1032@gamil.com");
      member3.setMem_tel("010-4468-1957");
      member3.setRent_count(77);
      memberList.add(member3);
      
      MemberVO member4 = new MemberVO();
      member4.setMem_id("wow7777");
      member4.setMem_name("이영옥");
      member4.setMem_pw("duddhr777");
      member4.setMem_bir("2020-01-05");
      member4.setMem_email("wow7777@gmail.com");
      member4.setMem_tel("010-9999-8542");
      member4.setRent_count(2);
      memberList.add(member4);
      
      MemberVO member5 = new MemberVO();
      member5.setMem_id("number1");
      member5.setMem_name("이영준");
      member5.setMem_pw("dudwns123");
      member5.setMem_bir("1850-03-21");
      member5.setMem_email("number1@naver.com");
      member5.setMem_tel("010-7895-1325");
      member5.setRent_count(1300);
      memberList.add(member5);
      
      MemberVO member6 = new MemberVO();
      member6.setMem_id("yung22");
      member6.setMem_name("이성연");
      member6.setMem_pw("tjddus123");
      member6.setMem_bir("1982-09-09");
      member6.setMem_email("yung22@naver.com");
      member6.setMem_tel("010-0545-6489");
      member6.setRent_count(135);
      memberList.add(member6);
      
      MemberVO member7 = new MemberVO();
      member7.setMem_id("sum09054");
      member7.setMem_name("박유식");
      member7.setMem_pw("mun09054");
      member7.setMem_bir("1988-08-23");
      member7.setMem_email("sum09054");
      member7.setMem_tel("010-4562-7893");
      member7.setRent_count(465);
      member7.setActivate(false);
      memberList.add(member7);
      
      MemberVO member8 = new MemberVO();
      member8.setMem_id("han6666");
      member8.setMem_name("고귀남");
      member8.setMem_pw("jhan1421");
      member8.setMem_bir("1988-04-30");
      member8.setMem_email("han6666@naver.com");
      member8.setMem_tel("010-9635-1247");
      member8.setRent_count(400);
      member8.setActivate(false);
      memberList.add(member8);
      
      MemberVO member9 = new MemberVO();
      member9.setMem_id("jia76");
      member9.setMem_name("김지아");
      member9.setMem_pw("13d5w312");
      member9.setMem_bir("1976-02-28");
      member9.setMem_email("joa76@naver.com");
      member9.setMem_tel("010-4432-3391");
      member9.setRent_count(190);
      member9.setActivate(false);
      memberList.add(member9);
      
      MemberVO member10 = new MemberVO();
      member10.setMem_id("sheep133");
      member10.setMem_name("양철");
      member10.setMem_pw("sheep9999");
      member10.setMem_bir("1999-12-26");
      member10.setMem_email("sheep@naver.com");
      member10.setMem_tel("010-3333-9987");
      member10.setRent_count(80);
      memberList.add(member10);
   }
	
	//도서분류 목록 초기화
   {
	   BookLGUVO bl1 = new BookLGUVO();
	   bl1.setBook_LGU("1");
	   bl1.setBook_theme("소설");
	   bookLGUList.add(bl1);
	   
	   BookLGUVO bl2 = new BookLGUVO();
	   bl2.setBook_LGU("2");
	   bl2.setBook_theme("시");
	   bookLGUList.add(bl2);
	   
	   BookLGUVO bl3 = new BookLGUVO();
	   bl3.setBook_LGU("3");
	   bl3.setBook_theme("에세이");
	   bookLGUList.add(bl3);
	   
	   BookLGUVO bl4 = new BookLGUVO();
	   bl4.setBook_LGU("4");
	   bl4.setBook_theme("경제");
	   bookLGUList.add(bl4);
	   
	   BookLGUVO bl5 = new BookLGUVO();
	   bl5.setBook_LGU("5");
	   bl5.setBook_theme("자기계발");
	   bookLGUList.add(bl5);
	   
	   BookLGUVO bl6 = new BookLGUVO();
	   bl6.setBook_LGU("6");
	   bl6.setBook_theme("역사");
	   bookLGUList.add(bl6);
	   
	   BookLGUVO bl7 = new BookLGUVO();
	   bl7.setBook_LGU("7");
	   bl7.setBook_theme("정치");
	   bookLGUList.add(bl7);
	   
	   BookLGUVO bl8 = new BookLGUVO();
	   bl8.setBook_LGU("8");
	   bl8.setBook_theme("예술");
	   bookLGUList.add(bl8);
	   
	   BookLGUVO bl9 = new BookLGUVO();
	   bl9.setBook_LGU("9");
	   bl9.setBook_theme("과학");
	   bookLGUList.add(bl9);
	   
	   BookLGUVO bl10 = new BookLGUVO();
	   bl10.setBook_LGU("10");
	   bl10.setBook_theme("컴퓨터");
	   bookLGUList.add(bl10);
   }
   
   // 도서정보 초기화
   {
	   BookVO bv1 = new BookVO();
	   bv1.setBook_id("1");
	   bv1.setBook_name("달러구트 꿈 백화점");
	   bv1.setBook_author("이미예");
	   bv1.setBook_LGU("1");
	   bv1.setBook_publisher("팩토리나인");
	   bv1.setBook_summary("주문하신 꿈은 매진입니다.");
	   bv1.setBook_state(false);
	   bookList.add(bv1);
	   
	   BookVO bv2 = new BookVO();
	   bv2.setBook_id("2");
	   bv2.setBook_name("나의 하루는 4시 30분에 시작된다");
	   bv2.setBook_author("김유진");
	   bv2.setBook_LGU("2");
	   bv2.setBook_publisher("토네이도");
	   bv2.setBook_summary("하루를 두 배로 사는 단 하나의 습관");
	   bv2.setBook_state(false);
	   bookList.add(bv2);
	   
	   BookVO bv3 = new BookVO();
	   bv3.setBook_id("3");
	   bv3.setBook_name("어떻게 말해줘야 할까");
	   bv3.setBook_author("오은영");
	   bv3.setBook_LGU("3");
	   bv3.setBook_publisher("김영사");
	   bv3.setBook_summary("버럭하지 않고 분명하게 알려주는 방법");
	   bv3.setBook_state(false);
	   bookList.add(bv3);
	   
	   BookVO bv4 = new BookVO();
	   bv4.setBook_id("4");
	   bv4.setBook_name("트렌드 코리아 2021");
	   bv4.setBook_author("김난도");
	   bv4.setBook_LGU("4");
	   bv4.setBook_publisher("미래의 창");
	   bv4.setBook_summary("설명이 필요없는 '트렌드서'의 대명사");
	   bookList.add(bv4);
	   
	   BookVO bv5 = new BookVO();
	   bv5.setBook_id("5");
	   bv5.setBook_name("아우슈비츠의 치과의사");
	   bv5.setBook_author("벤저민 제이콥스");
	   bv5.setBook_LGU("5");
	   bv5.setBook_publisher("서해문집");
	   bv5.setBook_summary("나는 유대인이고, 141129번 수용자였으며, 수용소 내 치과의사였다.");
	   bookList.add(bv5);
	   
	   BookVO bv6 = new BookVO();
	   bv6.setBook_id("6");
	   bv6.setBook_name("마음챙김의 시");
	   bv6.setBook_author("류시화");
	   bv6.setBook_LGU("6");
	   bv6.setBook_publisher("수오서재");
	   bv6.setBook_summary("사회적 거리두기와 삶에 대한 성찰이 어느 때보다 필요한 지금, 손대신 시를 건내는 것은 어떤가");
	   bookList.add(bv6);
	   
	   BookVO bv7 = new BookVO();
	   bv7.setBook_id("7");
	   bv7.setBook_name("트루 리버럴리즘");
	   bv7.setBook_author("디드러 낸슨 매클로스키");
	   bv7.setBook_LGU("7");
	   bv7.setBook_publisher("7분의 언덕");
	   bv7.setBook_summary("자유주의가 더 정의롭고 더 번영하는 세상을 만드는 이유");
	   bookList.add(bv7);
	   
	   BookVO bv8 = new BookVO();
	   bv8.setBook_id("8");
	   bv8.setBook_name("나의 작은 정원");
	   bv8.setBook_author("차유정");
	   bv8.setBook_LGU("8");
	   bv8.setBook_publisher("동양북스");
	   bv8.setBook_summary("12색 물감으로 완성하는 수채화 컬러링 북");
	   bookList.add(bv8);
	   
	   BookVO bv9 = new BookVO();
	   bv9.setBook_id("9");
	   bv9.setBook_name("내게 다가온 수학의 시간들");
	   bv9.setBook_author("장우석");
	   bv9.setBook_LGU("9");
	   bv9.setBook_publisher("한권의 책");
	   bv9.setBook_summary("아름답고 고통스러웠던 어느 수학 교사의 자전적 이야기");
	   bookList.add(bv9);
	   
	   BookVO bv10 = new BookVO();
	   bv10.setBook_id("10");
	   bv10.setBook_name("너무 외로운 사람들을 위한 책");
	   bv10.setBook_author("오시마 노부요리");
	   bv10.setBook_LGU("10");
	   bv10.setBook_publisher("메이트북스");
	   bv10.setBook_summary("함께 있어도 외로운 당신을 위한 심리처방전");
	   bookList.add(bv10);
   }

	//게시글 초기화
   {
		NoticeVO notice = new NoticeVO();
		notice.setNotice_no(1);
		notice.setNotice_title("도서관휴관합니다");
		notice.setNotice_content("내맘입니다");
		notice.setNotice_date("2020-11-01");
		noticeList.add(notice);
		
		NoticeVO notice1 = new NoticeVO();
		notice1.setNotice_no(2);
		notice1.setNotice_title("도서관 상영회 관련");
		notice1.setNotice_content("영화제목:피아니스트");
		notice1.setNotice_date("2020-11-02");
		noticeList.add(notice1);
		
		NoticeVO notice2 = new NoticeVO();
		notice2.setNotice_no(3);
		notice2.setNotice_title("도서관 이용시 유의사항");
		notice2.setNotice_content("항상 조심히 걸어다니기");
		notice2.setNotice_date("2020-11-01");
		noticeList.add(notice2);
		
		NoticeVO notice3 = new NoticeVO();
		notice3.setNotice_no(4);
		notice3.setNotice_title("도서관 휴관안내");
		notice3.setNotice_content("내일부터 휴관합니다.");
		notice3.setNotice_date("2020-11-13");
		noticeList.add(notice3);
		
		NoticeVO notice4 = new NoticeVO();
		notice4.setNotice_no(5);
		notice4.setNotice_title("코로나 바이러스 관련");
		notice4.setNotice_content("소독완료했습니다.");
		notice4.setNotice_date("2020-11-21");
		noticeList.add(notice4);
		
		NoticeVO notice5 = new NoticeVO();
		notice5.setNotice_no(6);
		notice5.setNotice_title("11월 희망도서 업데이트 목록");
		notice5.setNotice_content("첨부파일 확인 바랍니다.");
		notice5.setNotice_date("2020-11-01");
		noticeList.add(notice5);
		
		NoticeVO notice6 = new NoticeVO();
		notice6.setNotice_no(7);
		notice6.setNotice_title("이달의 독서왕 발표");
		notice6.setNotice_content("첨부파일 확인 바랍니다.");
		notice6.setNotice_date("2020-11-01");
		noticeList.add(notice6);
		
		NoticeVO notice7 = new NoticeVO();
		notice7.setNotice_no(8);
		notice7.setNotice_title("분실물 안내");
		notice7.setNotice_content("1층 사무실에서 찾아가세요");
		notice7.setNotice_date("2020-11-01");
		noticeList.add(notice7);
		
		NoticeVO notice8 = new NoticeVO();
		notice8.setNotice_no(9);
		notice8.setNotice_title("코로나로 인해 2주간 미디어실 운영을 중지합니다.");
		notice8.setNotice_content("죄송합니다.");
		notice8.setNotice_date("2020-11-01");
		noticeList.add(notice8);
		
		NoticeVO notice9 = new NoticeVO();
		notice9.setNotice_no(10);
		notice9.setNotice_title("희망도서 신청안내");
		notice9.setNotice_content("희망도서 게시판을 이용해주세요");
		notice9.setNotice_date("2020-11-01");
		noticeList.add(notice9);
	}
 //희망도서 초기화
  	{
  		HopeVO hope1 = new HopeVO();
  		hope1.setHope_id("1");
  		hope1.setMem_id("yung22");
  		hope1.setHope_name("불안");
  		hope1.setHope_author("알랭드보통");
  		hope1.setHope_publisher("열림출판사");
  		hope1.setHope_content("이책이 보고싶었어요");
  		hopeList.add(hope1);
  		
  		HopeVO hope2 = new HopeVO();
  		hope2.setHope_id("2");
  		hope2.setMem_id("sum09054");
  		hope2.setHope_name("개미");
  		hope2.setHope_author("베르나르베르베르");
  		hope2.setHope_publisher("연필");
  		hope2.setHope_content("이 작가를 좋아해서");
  		hopeList.add(hope2);
  		
  		HopeVO hope3 = new HopeVO();
  		hope3.setHope_id("3");
  		hope3.setMem_id("abcd01");
  		hope3.setHope_name("모순");
  		hope3.setHope_author("양귀자");
  		hope3.setHope_publisher("펭귄출판사");
  		hope3.setHope_content("유명한 책이라 보고싶어요");
  		hopeList.add(hope3);
  	}
 	
	//대여정보 초기화
	{
		RentalVO re1 = new RentalVO();
		re1.setRental_start("2002-11-11");
		re1.setRental_end("2002-12-11");
		re1.setMem_id("abcd01");
		re1.setBook_id("3");
		rentalList.add(re1);
		
		RentalVO re2 = new RentalVO();
		re2.setRental_start("2002-05-21");
		re2.setRental_end("2002-06-11");
		re2.setMem_id("abcd01");
		re2.setBook_id("1");
		rentalList.add(re2);
		
		RentalVO re3 = new RentalVO();
		re3.setRental_start("2002-11-11");
		re3.setRental_end("2002-12-11");
		re3.setMem_id("abcd01");
		re3.setBook_id("2");
		rentalList.add(re3);
		
	}
	
	//예약정보 초기화
	{
		ReserveVO re1 = new ReserveVO();
		re1.setMem_id("nex1032");
		re1.setBook_id("1");
		reserveList.add(re1);
		
		ReserveVO re2 = new ReserveVO();
		re2.setMem_id("nex1032");
		re2.setBook_id("2");
		reserveList.add(re2);
		
		ReserveVO re5 = new ReserveVO();
		re5.setMem_id("nex1032");
		re5.setBook_id("3");
		reserveList.add(re5);
	}
	//블랙리스트
	{
		BlackListVO bv1 = new BlackListVO();
		bv1.setBlack_id(1);
		bv1.setMem_id("jia76");
		bv1.setBlack_day("2020-10-20");
		bv1.setBlack_end("2020-10-27");
		blackList.add(bv1);
		
		BlackListVO bv2 = new BlackListVO();
		bv2.setBlack_id(2);
		bv2.setMem_id("han6666");
		bv2.setBlack_day("2020-10-20");
		bv2.setBlack_end("2020-10-27");
		blackList.add(bv2);
		
		BlackListVO bv3 = new BlackListVO();
		bv3.setBlack_id(3);
		bv3.setMem_id("sum09054");
		bv3.setBlack_day("2020-10-20");
		bv3.setBlack_end("2020-10-27");
		blackList.add(bv3);
		
	}
}
//nex1032
//5462