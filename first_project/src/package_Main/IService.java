package package_Main;

import java.util.*;

import package_VO.*;


public interface IService {
	
	
/////////////////////////로그인&회원가입///////////////////////////////
		   
	/**
	* 아이디중복체크
	*@param mem_id 멤버 한명의 아이디
	*@return boolean 일치하는 사람이 있으면 true를 없으면 false를 반환
	*@author 조애슬
	*/
	int idUniqCheck(String mem_id); //반환 타입 int (count로 세려고)

	//SELECT count(mem_id)//boolean으로 하는것보다 count트로 0이면 중복아님 1이상이면 중복임으로 하기
	//FROM  MEMBER
	//WHERE MEM_ID = 'a001'
		
	
	/**
	* 회원가입 받은거 db에 저장. 성공하면 true리턴
	* @param mv
	* @return boolean
	* @author 조애슬
	*/
	boolean createMember(MemberVO mv);
	
	/**
	* 로그인 입력받은거 db와 비교
	* @param Map<> in, Map<> pw
	* @return MemberVO //MemberVO nowmember를 null에서 로그인한 회원으로 바꿔주기위해
	* @since 2020-11-04
	*/
	MemberVO loginMatch(Map<Integer, String> id, Map<Integer , String> pw); // 반환타입 MemberVO, String
	


/////////////////////////////////공지사항///////////////////////////////////////   
	/**
	* 공지사항 보기
	* @author 조애슬
	* @return List<NoticeVO>
	* @since 2020-11-05
	*/
	List<NoticeVO> noticeList(); // 반환타입 List<NoticeVO> 
	
	
	/**
	* 공지사항의 글번호를 가지고 해당하는 글 상세보기\
	* @param input
	* @return NoticeVO //글 하나니까
	* @author 조애슬
	* @since 2020-11-05
	*/
	NoticeVO openNoDetail(int input); // 매개변수 PK, 반환타입 NoticeVO 글 전체가 아니라 글 하나니까 List가 아니다
	
	/**
	* 희망도서리스트출력
	* @return List
	* @author 조애슬
	* @since 2020-11-05
	*/
	List<NoticeVO> hopeList(); //반환타입
	
	/**
	* 희망도서등록 , 등록성공하면 true
	* @param HopeVO 
	*@return boolean 
	* @author 조애슬
	* @since 2020-11-05
	*/
	boolean hopeListAdd(HopeVO hv); //매개변수 HopeVO

////////////////////////검색/////////////////////////////
  /**
    * 도서명 검색 후 출력
    * 및 도서 상세 메서드
    * @author 송지은
    * @param 도서명(String)
    * @return BookVO List형 반환
    * 검색 조건: 도서명을 받고 일치하는 도서목록을 보여준다
    */
	List<BookVO> bookList(); // 매개변수
	
  /**
    * 장르 검색 메서드
    * @author 송지은
    * @param 장르명
    * @return  BookLGUVO List형 반환
    * 검색 조건: 장르 목록 리스트를 보고 해당 숫자 입력시 이동 후 리스트 출력(BookList)
    */
	List<BookLGUVO> bookLGUList();

	
	
	
///////////////////마이페이지///////////////////////////
	/**
	 * 내 정보 출력 메서드(아이디,이름,생년월일,이메일,전화번호,대출횟수)
	 * @param mem_id 나의 아이디
	 * @author 민태원
	 * @since 2020.11.04
	 * @return 회원정보 반환
	 */
	MemberVO printProfile(String mem_id); //매개변수  : 나의 아이디 String, 반화타입 : 나의 정보 MemberVO
	
	/**
	 * 회원 탈퇴 메서드 -> 회원정보 삭제 메서드 호출
	 * 출력: "정말 탈퇴하시겠습니까? (Y/N)" => y면 회원정보 삭제 / n이면 return
	 * @param mv 탈퇴할 멤버의 모든 정보 
	 * @author 민태원
	 * @since 2020.11.04
	 */
	void withdrawal(MemberVO mv);
	
	/**
	 * 회원의 대출도서리스트 출력 메서드
	 * @param mem_id 조회할 회원의 아이디
	 * @author 민태원
	 * @since 2020.11.04
	 * @return 대출도서목록 반환
	 */
	List<RentalVO> printRentalList(String mem_id);
	
	/**
	 * 도서반납 메서드 			
	 * 출력 : "반납하시겠습니까? (y/n)" => y면 반납 / n면 return
	 * @param book_id 반납할 도서의 아이디
	 * @author 민태원
	 * @since 2020.11.04
	 */
	void returnBook(String book_id);
	
	
	/**
	 * 나의 예약도서 리스트 출력 메서드
	 * @param mem_id 조회할 회원의 아이디
	 * @author 민태원
	 * @since 2020.11.04
	 * @return 예약도서 목록 반환
	 */
	List<ReserveVO> printBookList(String mem_id); //반환타입 
	
	
	
	
	
////////////////////관리자//////////////////////////////
	/**
	 * 읽을 공지 출력 메소드
	 * @param nv
	 * 출력할 공지정보
	 * @author 김태규
	 */
	void noiceReadMethod(NoticeVO nv);

	/**
	 * 공지 추가 메서드
	 * @param nv
	 * 추가할 공지정보
	 * @author 김태규
	 */
	String noiceAddMethod(NoticeVO nv);

	/**
	 * 공지 삭제 메소드 공지
	 * @param nv
	 * 삭제할 공지정보 
	 * @author 김태규
	 */
	void noiceDeltleMethod(NoticeVO nv);

	/**
	 * 도서 승인 메소드
	 * @param nv
	 * 승인할 희망도서  정보
	 * @author 김태규
	 */
	void hopeBookAddMethod(HopeVO hv);

	/**
	 * 부결 도서 메소드
	 * @param nv
	 * 리스트에 삭제할 희망도서 정보 
	 * @author 김태규
	 */
	void hopeBookeDeltleMethod(HopeVO hv);

	/**
	 * 전체 도서리스트 출력 메소드
	 * * @param nv
	 * 출력할 도서 정보
	 * @author 김태규
	 */
	void bookListMethod(BookVO bv);

	/**
	 * 도서 등록 메소드
	 * @param nv
	 * 추가할 공지정보
	 * @author 김태규
	 */
	void bookAddMethod(BookVO bv);

	/**
	 * 회원리스트 출력 메소드
	 * @param nv
	 * 출력할 회원 정보 
	 * @author 김태규
	 */
	void memberListMethod(MemberVO mv);

	/**
	 * 블랙리스트 등록 메소드
	 * @param nv
	 * 등록할 블랙리스트정보 
	 * return BlackListVO
	 * @author 김태규
	 */
	void blackAddMethod(BlackListVO bv);

	/**
	 * 블랙리스트 리스트 출력 메소드
	 * 블랙리스트의 전체의 정보  
	 * @author 김태규
	 */
	void blackListReadView(BlackListVO bv);

	/**
	 * 블랙리스트 리스트 삭제 메소드
	 * 삭제할 블랙리스트정보 
	 * @author 김태규
	 */
	void blackDeltleMethod(BlackListVO bv);
}