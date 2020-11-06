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
    * 회원정보 조회 메서드
    * @param mem_id 조회할 회원의 아이디
    * @return 조회할 회원의 모든 정보 반환
    * @author 민태원
    * @since 2020.11.05
    */
	MemberVO readMember(String mem_id); //매개변수  : 나의 아이디 String, 반화타입 : 나의 정보 MemberVO
	
	/**
    * 회원정보 갱신 메서드
    * @param myInfo 수정할 내 정보를 Map으로 받아 업데이트
    * @return 갱신 성공하면 1, 아니면 0
    * @author 민태원
    * @since 2020.11.04
    */
   	int updateMember(Map<String, String> myInfo);
   	
   	/**
     * 회원정보 삭제 메서드
     * @param mem_id 탈퇴할 멤버의 모든 정보
     * @return 비활성화 성공하면 1, 아니면 0
     * @author 민태원
     * @since 2020.11.06
     */
   	int deleteMember(String mem_id);
   	
   	/**
     * 로그인 입력받은거 MemberVO와 비교
     * @param Map<> in, Map<> pw
     * @return MemberVO //MemberVO nowmember를 null에서 로그인한 회원으로 바꿔주기위해
     * @since 2020-11-04
     */
     MemberVO loginMatch(Map<String, String> params); // 반환타입 MemberVO, String
	
     /**
      * 로그인 입력받은거 AdminVO와 비교
      * @param params
      * @return boolean
      */
     boolean adminMatch(Map<String,String> params);

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
	List<HopeVO> hopeList(); //반환타입
	
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
	 * 회원의 대출도서리스트 출력 메서드
	 * @param mem_id 조회할 회원의 아이디
	 * @author 민태원
	 * @since 2020.11.04
	 * @return 대출도서목록 반환
	 */
	List<Map<String, String>> readRentalList(String mem_id);
	
	/**
	 * 도서반납 메서드 			
	 * @param map 회원의 아이디와 반납할 도서의 아이디
	 * @return 반납에 성공하면 1, 아니면 0
	 * @author 민태원
	 * @since 2020.11.06
	 */
	int returnBook(Map<String,String> map);
	
	/**
	 * 나의 예약도서 리스트 출력 메서드
	 * @param mem_id 조회할 회원의 아이디
	 * @return 예약도서 목록 반환
	 * @author 민태원
	 * @since 2020.11.04
	 */
	List<Map<String, String>> readReserveList(String mem_id);
	
	/**
	 * 예약 취소 메서드 			
	 * @param map 회원의 아이디와 예약취소할 도서의 아이디
	 * @return 예약취소에 성공하면 1, 아니면 0
	 * @author 민태원
	 * @since 2020.11.06
	 */
	int cancelReserve(Map<String, String> map);
	
	
////////////////////관리자//////////////////////////////
	/**
	 * 공지리스트 출력 메소드
	 * @param nv
	 * 출력할 공지정보
	 * @author 김태규
	 */
	List<NoticeVO> noticList();
	/**
	 * 선택된 공지 출력 메소드
	 * @param nv
	 * 출력할 공지정보
	 * @author 김태규
	 */
	void noiceReadMethod(int num);

	/**
	 * 공지 추가 메서드
	 * @param nv
	 * 추가할 공지정보
	 * @author 김태규
	 */
	void noiceAddMethod(NoticeVO nv);

	/**
	 * 공지 삭제 메소드 공지
	 * @param nv
	 * 삭제할 공지정보 
	 * @author 김태규
	 * @return 
	 */
	boolean noiceDeltleMethod(int num);

	/**
	 * 도서 승인 메소드
	 * @param nv
	 * 승인할 희망도서  정보
	 * @author 김태규
	 * @return 
	 */
	boolean hopeBookAddMethod(int num);

	/**
	 * 부결 도서 메소드
	 * @param nv
	 * 리스트에 삭제할 희망도서 정보 
	 * @author 김태규
	 */
	void hopeBookeDeltleMethod(int num);

	/**
	 * 전체 도서리스트 출력 메소드
	 * * @param nv
	 * 출력할 도서 정보
	 * @author 김태규
	 */
	List<BookVO> booklList();

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
	List<MemberVO> memList();

	/**
	 * 블랙리스트 등록 메소드
	 * @param nv
	 * 등록할 블랙리스트정보 
	 * return BlackListVO
	 * @author 김태규
	 */
	void blackAddMethod(int num);

	/**
	 * 블랙리스트 리스트 출력 메소드
	 * 블랙리스트의 전체의 정보  
	 * @author 김태규
	 */
	List<BlackListVO> blackList();


	/**
	 * 블랙리스트 리스트 삭제 메소드
	 * 삭제할 블랙리스트정보 
	 * @author 김태규
	 */
	void blackDeltleMethod(BlackListVO bv);
}