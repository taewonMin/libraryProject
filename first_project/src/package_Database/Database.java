package package_Database;

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
   	 * @author 민태원
   	 * @since 2020.11.05
   	 */
   	public void readNotice(NoticeVO nv){
   		System.out.println(nv.getNotice_no());
   		System.out.println(nv.getNotice_title());
   		System.out.println(nv.getNotice_date());
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
   	 * @return 추가에 성공하였으면 true 그렇지 않으면 false
   	 * @author 민태원
   	 * @since 2020.11.05
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
   	 * @param memData (회원아이디, 도서아이디)를 저장하고있는 Map
   	 * @return 조회된 대여 튜플 반환
   	 * @author 민태원
   	 * @since 2020.11.06
   	 */
   	public RentalVO readRentalVO(Map<String,String> memData){
   		for(RentalVO rv : rentalList){
   			if(rv.getMem_id().equals(memData.get("mem_id")) && 
   					rv.getBook_id().equals(memData.get("book_id"))){
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
 		   bv.setBook_state(true);	//대여상태 가능으로 변경
 		   //삭제된 도서를 예약한 사람이 있는지 확인 
 		   ReserveVO rsv = checkReserve(bv.getBook_id());
 		   if(rsv != null){
 			   //있으면 가장 날짜가 빠른 사람과 도서를 대여테이블에 추가
 			   RentalVO newRsv = new RentalVO();
 			   newRsv.setRental_start(rv.getRental_end());
 			   newRsv.setRental_end("+14일");	// 날짜 더하기 메서드 수정
 			   newRsv.setMem_id(rsv.getMem_id());
 			   newRsv.setBook_id(rsv.getBook_id());
 			   newRsv.setRental_id(0);	///렌탈 아이디 수정
 			   createRental(newRsv);
 			   
 			   //대출된 도서는 예약테이블에서 삭제
 	 		   Map<String, String> map = new HashMap<>();
 	 		   map.put("mem_id", rv.getMem_id());
 	 		   map.put("book_id", rv.getBook_id());
 	 		   deleteReserve(readReserveVO(map));
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
   	public boolean createReserve(ReserveVO rv){
        if(reserveList.add(rv)){
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
	//public List<NoticeVO> noticeList(){return noticeList;}
	public boolean noticeList(){
		
		return true;
	}
	
	/**
	 * 희망도서목록을 출력한다
	 * @author 조애슬
	 * @return 
	 * @since 2020-11-05
	 */
	public boolean hopeList(){
		return true;
	}
	
	/**
	 * 희망도서를 db에 등록하고 정상등록되면  true를 리턴
	 * @author 조애슬
	 * @since 2020-11-05
	 */
	public boolean hopeListAdd(HopeVO hv){
		if(hopeList.add(hv)){
			System.out.println("희망도서가 정상적으로 등록되었습니다.");
			return true;
		}
		return false;
	}

///////////////////////////////////////관리자////////////////////////////////
	/**
	 * 공지 삭제 메서드
	 * 
	 * @param nv
	 *            삭제할 공지의 모든 정보
	 * @author 김태규
	 * @since 2020.11.05
	 */
	public boolean deleteNotice(int num) {
		NoticeVO nv = null;
		for (NoticeVO nv2 : noticeList) {
			if (nv2.getNotice_no() == num) {
				noticeList.remove(num);
			}
		}
		return false;
	}
	
	/**
	 * 희망도서를 db에 삭제하고 정상삭제되면 true를 리턴
	 * 
	 * @author 김태규
	 * @since 2020-11-05
	 */
	public void hopeBookeDeltleMethod(int num) {
		NoticeVO nv = null;
		for (NoticeVO nv2 :noticeList) {
			if (nv2.getNotice_no()==num) {
				noticeList.remove(num);
			}
		}		
	}
///////////////////////블랙리스트 CRUD////////////////////////	
	/**
	 * 블랙리스트 삽입 메서드
	 */
	public boolean createBlackList(BlackListVO params) {
		if (blackList.add(params)) {// 정보 넣고 성공시 true
			return true;
		}
		return false;
	}

	/**
	 * 블랙리스트 조회 메서드
	 */
	public void readBlack(BlackListVO bv) {
		if (blackList.contains(bv)) {
			System.out.println(bv.getMem_id());
			System.out.println(bv.getBlack_day());
			System.out.println(bv.getBlack_end());
		}
	}

	/**
	 * 블랙리스트 갱신 메서드
	 */
	void updateBlackList(BlackListVO bv) {
		if (blackList.contains(bv.getMem_id())) {
			(blackList.get(blackList.indexOf(bv.getMem_id()))).setMem_id(bv
					.getMem_id());
			(blackList.get(blackList.indexOf(bv.getMem_id()))).setBlack_day(bv
					.getBlack_day());
			(blackList.get(blackList.indexOf(bv.getMem_id()))).setBlack_end(bv
					.getBlack_end());
		}
	}

	/**
	 * 블랙리스트 삭제 메서드
	 * 
	 */
	void blackDeltleMethod(BlackListVO bv) {
		/*
		 * if (memberList.contains(bv.getMem_id())) { (
		 * memberList.get(memberList
		 * .indexOf(bv.getMem_id()))).setActivate(false); }이렇게 하면 블랙리스트의 멤버아이디의
		 * 인덱스값이 해당 멤버의 인덱스값이랑 다를것같아서..
		 */

		for (int i = 0; i < memberList.size(); i++) {
			if (memberList.get(i).getMem_id() == bv.getMem_id()) {
				memberList.get(i).setActivate(false);
				blackList.remove(bv);
			}
		}
	}
	

	
////////////////////데이터베이스 초기화///////////////////////
	
	// 관리자 초기화
	{
		admin.setadmin_id("admin");
		admin.setadmin_pw("a1234");
	}
		
	// 회원정보 목록 초기화
   {
      MemberVO member1 = new MemberVO();
      member1.setMem_id("abcd01");
      member1.setMem_name("오지란");
      member1.setMem_pw("1234");
      member1.setMem_bir("1980-01-12");
      member1.setMem_email("abcd01@naver.com");
      member1.setMem_tel("010-1234-5678");
      member1.setRent_count(10);
      memberList.add(member1);
      
      MemberVO member2 = new MemberVO();
      member2.setMem_id("qwer09");
      member2.setMem_name("설마음");
      member2.setMem_pw("8546");
      member2.setMem_bir("1999-12-07");
      member2.setMem_email("qwer90@naver.com");
      member2.setMem_tel("010-5555-4678");
      member2.setRent_count(50);
      memberList.add(member2);
      
      MemberVO member3 = new MemberVO();
      member3.setMem_id("nex1032");
      member3.setMem_name("김미소");
      member3.setMem_pw("5462");
      member3.setMem_bir("1987-05-01");
      member3.setMem_email("nex1032@gamil.com");
      member3.setMem_tel("010-4468-1957");
      member3.setRent_count(77);
      memberList.add(member3);
      
      MemberVO member4 = new MemberVO();
      member4.setMem_id("wow7777");
      member4.setMem_name("이영옥");
      member4.setMem_pw("57536");
      member4.setMem_bir("2020-01-05");
      member4.setMem_email("wow7777@gmail.com");
      member4.setMem_tel("010-9999-8542");
      member4.setRent_count(2);
      memberList.add(member4);
      
      MemberVO member5 = new MemberVO();
      member5.setMem_id("number1");
      member5.setMem_name("이영준");
      member5.setMem_pw("1111");
      member5.setMem_bir("1850-03-21");
      member5.setMem_email("number1@naver.com");
      member5.setMem_tel("010-7895-1325");
      member5.setRent_count(1300);
      memberList.add(member5);
      
      MemberVO member6 = new MemberVO();
      member6.setMem_id("yung22");
      member6.setMem_name("이성연");
      member6.setMem_pw("4658sdq");
      member6.setMem_bir("1982-09-09");
      member6.setMem_email("yung22@naver.com");
      member6.setMem_tel("010-0545-6489");
      member6.setRent_count(135);
      memberList.add(member6);
      
      MemberVO member7 = new MemberVO();
      member7.setMem_id("sum09054");
      member7.setMem_name("박유식");
      member7.setMem_pw("qad656");
      member7.setMem_bir("1988-08-23");
      member7.setMem_email("sum09054");
      member7.setMem_tel("010-4562-7893");
      member7.setRent_count(465);
      memberList.add(member7);
      
      MemberVO member8 = new MemberVO();
      member8.setMem_id("han6666");
      member8.setMem_name("고귀남");
      member8.setMem_pw("jhan421");
      member8.setMem_bir("1988-04-30");
      member8.setMem_email("han6666@naver.com");
      member8.setMem_tel("010-9635-1247");
      member8.setRent_count(400);
      memberList.add(member8);
      
      MemberVO member9 = new MemberVO();
      member9.setMem_id("jia76");
      member9.setMem_name("김지아");
      member9.setMem_pw("13d5w3");
      member9.setMem_bir("1976-02-28");
      member9.setMem_email("joa76@naver.com");
      member9.setMem_tel("010-4432-3391");
      member9.setRent_count(190);
      memberList.add(member9);
      
      MemberVO member10 = new MemberVO();
      member10.setMem_id("sheep133");
      member10.setMem_name("양철");
      member10.setMem_pw("sheep99");
      member10.setMem_bir("1999-12-26");
      member10.setMem_email("sheep@naver.com");
      member10.setMem_tel("010-3333-9987");
      member10.setRent_count(80);
      memberList.add(member10);
   }
	
	//도서분류 목록 초기화
   {
	   BookLGUVO bl1 = new BookLGUVO();
	   bl1.setBook_LGU("NO");
	   bl1.setBook_theme("소설");
	   bookLGUList.add(bl1);
	   
	   BookLGUVO bl2 = new BookLGUVO();
	   bl2.setBook_LGU("PO");
	   bl2.setBook_theme("시");
	   bookLGUList.add(bl2);
	   
	   BookLGUVO bl3 = new BookLGUVO();
	   bl3.setBook_LGU("ES");
	   bl3.setBook_theme("에세이");
	   bookLGUList.add(bl3);
	   
	   BookLGUVO bl4 = new BookLGUVO();
	   bl4.setBook_LGU("EC");
	   bl4.setBook_theme("경제");
	   bookLGUList.add(bl4);
	   
	   BookLGUVO bl5 = new BookLGUVO();
	   bl5.setBook_LGU("IM");
	   bl5.setBook_theme("자기계발");
	   bookLGUList.add(bl5);
	   
	   BookLGUVO bl6 = new BookLGUVO();
	   bl6.setBook_LGU("HE");
	   bl6.setBook_theme("역사");
	   bookLGUList.add(bl6);
	   
	   BookLGUVO bl7 = new BookLGUVO();
	   bl7.setBook_LGU("PL");
	   bl7.setBook_theme("정치");
	   bookLGUList.add(bl7);
	   
	   BookLGUVO bl8 = new BookLGUVO();
	   bl8.setBook_LGU("AR");
	   bl8.setBook_theme("예술");
	   bookLGUList.add(bl8);
	   
	   BookLGUVO bl9 = new BookLGUVO();
	   bl9.setBook_LGU("SI");
	   bl9.setBook_theme("과학");
	   bookLGUList.add(bl9);
	   
	   BookLGUVO bl10 = new BookLGUVO();
	   bl10.setBook_LGU("IT");
	   bl10.setBook_theme("컴퓨터");
	   bookLGUList.add(bl10);
   }
   
   // 도서정보 초기화
   {
	   BookVO bv1 = new BookVO();
	   bv1.setBook_id("1");
	   bv1.setBook_name("달러구트 꿈 백화점");
	   bv1.setBook_author("이미예");
	   bv1.setBook_LGU("NO");
	   bv1.setBook_publisher("팩토리나인");
	   bv1.setBook_summary("주문하신 꿈은 매진입니다.");
	   bookList.add(bv1);
	   
	   BookVO bv2 = new BookVO();
	   bv2.setBook_id("2");
	   bv2.setBook_name("나의 하루는 4시 30분에 시작된다");
	   bv2.setBook_author("김유진");
	   bv2.setBook_LGU("IM");
	   bv2.setBook_publisher("토네이도");
	   bv2.setBook_summary("하루를 두 배로 사는 단 하나의 습관");
	   bookList.add(bv2);
	   
	   BookVO bv3 = new BookVO();
	   bv3.setBook_id("3");
	   bv3.setBook_name("어떻게 말해줘야 할까");
	   bv3.setBook_author("오은영");
	   bv3.setBook_LGU("ES");
	   bv3.setBook_publisher("김영사");
	   bv3.setBook_summary("버럭하지 않고 분명하게 알려주는 방법");
	   bookList.add(bv3);
	   
	   BookVO bv4 = new BookVO();
	   bv4.setBook_id("4");
	   bv4.setBook_name("트렌드 코리아 2021");
	   bv4.setBook_author("김난도");
	   bv4.setBook_LGU("EC");
	   bv4.setBook_publisher("미래의 창");
	   bv4.setBook_summary("설명이 필요없는 '트렌드서'의 대명사");
	   bookList.add(bv4);
	   
	   BookVO bv5 = new BookVO();
	   bv5.setBook_id("5");
	   bv5.setBook_name("아우슈비츠의 치과의사");
	   bv5.setBook_author("벤저민 제이콥스");
	   bv5.setBook_LGU("HE");
	   bv5.setBook_publisher("서해문집");
	   bv5.setBook_summary("나는 유대인이고, 141129번 수용자였으며, 수용소 내 치과의사였다.");
	   bookList.add(bv5);
	   
	   BookVO bv6 = new BookVO();
	   bv6.setBook_id("6");
	   bv6.setBook_name("마음챙김의 시");
	   bv6.setBook_author("류시화");
	   bv6.setBook_LGU("PO");
	   bv6.setBook_publisher("수오서재");
	   bv6.setBook_summary("사회적 거리두기와 삶에 대한 성찰이 어느 때보다 필요한 지금, 손대신 시를 건내는 것은 어떤가");
	   bookList.add(bv6);
	   
	   BookVO bv7 = new BookVO();
	   bv7.setBook_id("7");
	   bv7.setBook_name("트루 리버럴리즘");
	   bv7.setBook_author("디드러 낸슨 매클로스키");
	   bv7.setBook_LGU("PL");
	   bv7.setBook_publisher("7분의 언덕");
	   bv7.setBook_summary("자유주의가 더 정의롭고 더 번영하는 세상을 만드는 이유");
	   bookList.add(bv7);
	   
	   BookVO bv8 = new BookVO();
	   bv8.setBook_id("8");
	   bv8.setBook_name("나의 작은 정원");
	   bv8.setBook_author("차유정");
	   bv8.setBook_LGU("AR");
	   bv8.setBook_publisher("동양북스");
	   bv8.setBook_summary("12색 물감으로 완성하는 수채화 컬러링 북");
	   bookList.add(bv8);
	   
	   BookVO bv9 = new BookVO();
	   bv9.setBook_id("9");
	   bv9.setBook_name("내게 다가온 수학의 시간들");
	   bv9.setBook_author("장우석");
	   bv9.setBook_LGU("SI");
	   bv9.setBook_publisher("한권의 책");
	   bv9.setBook_summary("아름답고 고통스러웠던 어느 수학 교사의 자전적 이야기");
	   bookList.add(bv9);
	   
	   BookVO bv10 = new BookVO();
	   bv10.setBook_id("10");
	   bv10.setBook_name("너무 외로운 사람들을 위한 책");
	   bv10.setBook_author("오시마 노부요리");
	   bv10.setBook_LGU("IM");
	   bv10.setBook_publisher("메이트북스");
	   bv10.setBook_summary("함께 있어도 외로운 당신을 위한 심리처방전");
	   bookList.add(bv10);
   }

	//게시글 초기화
	{
		NoticeVO notice = new NoticeVO();
		notice.setNotice_no(1);
		notice.setNotice_title("제목");
		notice.setNotice_content("내용");
		notice.setNotice_date("2020-11-01");
		noticeList.add(notice);
		
		NoticeVO notice1 = new NoticeVO();
		notice.setNotice_no(2);
		notice.setNotice_title("제목1");
		notice.setNotice_content("내용1");
		notice.setNotice_date("2020-11-01");
		noticeList.add(notice1);
		
		NoticeVO notice2 = new NoticeVO();
		notice.setNotice_no(3);
		notice.setNotice_title("제목2");
		notice.setNotice_content("내용2");
		notice.setNotice_date("2020-11-01");
		noticeList.add(notice2);
		
		NoticeVO notice3 = new NoticeVO();
		notice.setNotice_no(4);
		notice.setNotice_title("제목3");
		notice.setNotice_content("내용3");
		notice.setNotice_date("2020-11-01");
		noticeList.add(notice3);
		
		NoticeVO notice4 = new NoticeVO();
		notice.setNotice_no(5);
		notice.setNotice_title("제목4");
		notice.setNotice_content("내용4");
		notice.setNotice_date("2020-11-01");
		noticeList.add(notice4);
		
		NoticeVO notice5 = new NoticeVO();
		notice.setNotice_no(6);
		notice.setNotice_title("제목5");
		notice.setNotice_content("내용5");
		notice.setNotice_date("2020-11-01");
		noticeList.add(notice5);
		
		NoticeVO notice6 = new NoticeVO();
		notice.setNotice_no(7);
		notice.setNotice_title("제목6");
		notice.setNotice_content("내용6");
		notice.setNotice_date("2020-11-01");
		noticeList.add(notice6);
		
		NoticeVO notice7 = new NoticeVO();
		notice.setNotice_no(8);
		notice.setNotice_title("제목7");
		notice.setNotice_content("내용7");
		notice.setNotice_date("2020-11-01");
		noticeList.add(notice7);
		
		NoticeVO notice8 = new NoticeVO();
		notice.setNotice_no(9);
		notice.setNotice_title("제목8");
		notice.setNotice_content("내용8");
		notice.setNotice_date("2020-11-01");
		noticeList.add(notice8);
		
		NoticeVO notice9 = new NoticeVO();
		notice.setNotice_no(10);
		notice.setNotice_title("제목9");
		notice.setNotice_content("내용9");
		notice.setNotice_date("2020-11-01");
		noticeList.add(notice9);
	}
	//대여정보 초기화
	{
		RentalVO re1 = new RentalVO();
		re1.setRental_id(1);
		re1.setRental_start("2002-11-11");
		re1.setRental_end("2002-12-11");
		re1.setMem_id("abcd01");
		re1.setBook_id("3");
		rentalList.add(re1);
		
		RentalVO re2 = new RentalVO();
		re2.setRental_id(2);
		re2.setRental_start("2002-05-21");
		re2.setRental_end("2002-06-11");
		re2.setMem_id("abcd01");
		re2.setBook_id("1");
		rentalList.add(re2);
		
		RentalVO re3 = new RentalVO();
		re3.setRental_id(3);
		re3.setRental_start("2002-11-11");
		re3.setRental_end("2002-12-11");
		re3.setMem_id("abcd01");
		re3.setBook_id("2");
		rentalList.add(re3);
		
	///예약목록 확인용
//		RentalVO re5 = new RentalVO();
//		re5.setRental_id(4);
//		re5.setRental_start("2002-11-11");
//		re5.setRental_end("2002-12-31");
//		re5.setMem_id("nex1032");
//		re5.setBook_id("5");
//		rentalList.add(re5);
//		
//		
//		RentalVO re7 = new RentalVO();
//		re7.setRental_id(6);
//		re7.setRental_start("2002-11-11");
//		re7.setRental_end("2002-11-11");
//		re7.setMem_id("nex1032");
//		re7.setBook_id("7");
//		rentalList.add(re7);
		
	}
	
	//예약정보 초기화
	{
//		ReserveVO re1 = new ReserveVO();
//		re1.setRsv_no(1);
//		re1.setMem_id("abcd01");
//		re1.setBook_id("5");
//		reserveList.add(re1);
//		
//		ReserveVO re2 = new ReserveVO();
//		re2.setRsv_no(2);
//		re2.setMem_id("abcd01");
//		re2.setBook_id("7");
//		reserveList.add(re2);
		
		ReserveVO re5 = new ReserveVO();
		re5.setRsv_no(5);
		re5.setMem_id("nex1032");
		re5.setBook_id("3");
		reserveList.add(re5);
	}
}
//nex1032
//5462