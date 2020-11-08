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
 	
// 	   List<NoticeVO> noticeList(); // 반환타입 List<NoticeVO> 
 	   boolean noticeList(); // 반환타입 List<NoticeVO> 
 	
 	/**
 	* 공지사항의 글번호를 가지고 해당하는 글 상세보기\
 	* @param input
 	* @return NoticeVO //글 하나니까
 	* @author 조애슬
 	* @since 2020-11-05
 	*/
 	boolean openNoDetail(int input); // 매개변수 PK, 반환타입 NoticeVO 글 전체가 아니라 글 하나니까 List가 아니다
	
 	/**
	* 희망도서리스트출력
	* @return List
	* @author 조애슬
	* @since 2020-11-05
	*/
	boolean hopeList(); //반환타입
	
	/**
	* 희망도서등록 , 등록성공하면 true
	* @param HopeVO 
	*@return boolean 
	* @author 조애슬
	* @since 2020-11-05
	*/
	boolean hopeListAdd(Map<String, String> params); //매개변수 HopeVO
	
	/**
	 * 희망도서 상세보기
	 * @author 조애슬
	 * @since 2020-11-07
	 */
	boolean hopeDetailView(int hopeNo);
	
	/**
	 * 희망도서 삭제하기
	 * @author 조애슬
	 * @since 2020-11-07
	 */
	boolean hopeRemoveView(String mem_id,int hopeNo);

////////////////////////검색/////////////////////////////
	/**
	 * 도서명 검색 메서드
	 * @param bo_name 도서 이름
	 * @return 전체 도서명 List형으로 반환
	 */
	List<BookVO> bookNameListVer2(String bo_name);

	/**
	 * 도서 조회 메서드
	 * @author 송지은
	 * @param bo_id 도서 번호
	 * @return 전체 BookVO List형 반환
	 */
	List<BookVO> bookNameListVer1(int bo_id);
	
	/**
	 * 도서 분류 코드 조회 메서드
	 * @author 송지은
	 * @return
	 */
	List<BookLGUVO> bookLGUList();
	
	/**
	 * 장르 선택  입출력 메서드
	 * @author 송지은
	 * @return
	 */
	int scanCID();
	
	/**
	 * 도서 아이디 선택 메서드(int)
	 * @author 송지은
	 * @return bo_id반환
	 */
	
	String inputBook();
	/**
	 * 도서 아이디 선택 메서드(String)
	 * @author 송지은
	 * @return bo_name 반환
	 */
	int inputBook2();
	
	/**
	 * 도서 선택 메서드
	 * @author 송지은
	 * @return
	 */
	
	List<BookVO> booklList(); 
	
	/**
	 * 도서 상세 조회 메서드
	 * @author 송지은
	 * @param bookID
	 * @return
	 */
	List<BookVO> booklList(String bookID);

	/**
	 * 도서 검색 메서드
	 * @param book_id 도서의 아이디
	 * @return 검색된 도서의 모든 정보를 반환
	 * @author 민태원
	 * @since 2020.11.07
	 */
	BookVO readBook(String book_id);
//대여 및 예약	
	/**
	 * 대여시 대여테이블에 저장할 객체 생성 메서드
	 * @param map 회원의아이디, 도서의 아이디
	 * @return 객체 생성에 성공하면 객체 반환
	 * @author 민태원
	 * @since 2020.11.07
	 */
	RentalVO createRentalVO(Map<String, String> map);
	
	/**
	 * 예약시 예약테이블에 저장할 객체를 생성하고 예약가능 일자 반환
	 * @param map 회원의 아이디, 도서의 아이디
	 * @return 예약할 도서를 빌려간 사람의 반납일자 반환
	 * @author 민태원
	 * @since 2020.11.07
	 */
	String createReserveVO(Map<String, String> map);
	
	
	/**
	 * 도서의 아이디를 이용하여 대여목록에 있는지 체크
	 * @param book_id 검색할 도서의 아이디
	 * @return 검색된 도서의 모든 정보 반환
	 * @author 민태원
	 * @since 2020.11.09
	 */
	int checkRentalVO(Map<String,String> map);
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
	 * @param map 반납할 도서의 아이디
	 * @return 반납에 성공하면 1, 아니면 0
	 * @author 민태원
	 * @since 2020.11.06
	 */
	int returnBook(String book_id);
	
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
	boolean noticList();
	
	/**
	 * 선택된 공지 출력 메소드
	 * @param nv
	 * 출력할 공지정보
	 * @author 김태규
	 */
	boolean noticList(int num);

	/**
	 * 공지 추가 메서드
	 * @param nv
	 * 추가할 공지정보
	 * @author 김태규
	 * @return 
	 */
	boolean noiceAddMethod(Map<String, String> params);
	

	/**
	 * 공지 삭제 메소드 공지
	 * @param nv
	 * 삭제할 공지정보 
	 * @author 김태규
	 * @return 
	 */
	boolean deleteNotice(int num);

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
	 * @return 
	 */
	boolean hopeBookeDeltleMethod(int num);

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
	boolean memList();

	/**
	 * 블랙리스트 등록 메소드
	 * @param nv
	 * 등록할 블랙리스트정보 
	 * return BlackListVO
	 * @author 김태규
	 */
	boolean createBlackList(String id);

	/**
	 * 블랙리스트 리스트 출력 메소드
	 *  
	 * @author 김태규
	 */
	boolean blackListList();


	/**
	 * 블랙리스트 리스트 삭제 메소드
	 * 삭제할 블랙리스트정보 
	 * @author 김태규
	 */
	boolean blackDeltleMethod(String id);

	/**
	 * 도서 리스트 출력 메소드
	 * 
	 * @author 김태규
	 */
	boolean bookListMethod();

	/**
	 * 도서 리스트 추가 메소드
	 * 
	 * @author 김태규
	 */
	boolean bookAddMethod();
	
	/**
	 * 도서  추가 메소드
	 * 
	 * @author 김태규
	 */
	boolean bookAdd(Map<String, String> params);
}