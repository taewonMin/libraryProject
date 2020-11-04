package package_Main;

import java.util.*;

import package_VO.*;


public interface IService {
	
	/**
	 * 회원가입을 위한 메서드
	 * @param mv 멤버 한명의 모든 정보
	 * @return 추가에 성공하였으면 true 그렇지 않으면 false
	 * @author 민태원
	 * @since 2020.11.04
	 */
	boolean createMember(MemberVO mv);
	
	/**
	 * 회원정보 갱신 메서드
	 * @param mv 갱신할 멤버의 모든 정보
	 * @author 민태원
	 * @since 2020.11.04
	 */
	void updateMember(MemberVO mv);
	
	/**
	 * 회원정보 삭제 메서드
	 * @param mv 탈퇴할 멤버의 모든 정보
	 * @author 민태원
	 * @since 2020.11.04
	 */
	void deleteMember(MemberVO mv);
	
///////////////////마이페이지///////////////////////////
	/**
	 * 내 정보 출력 메서드(아이디,이름,생년월일,이메일,전화번호,대출횟수)
	 * @param mv 멤버 한명의 모든 정보
	 * @author 민태원
	 * @since 2020.11.04
	 */
	void printProfile(MemberVO mv);
	
	/**
	 * 비밀번호 변경 메서드 
	 * 출력 : "새로운 비밀번호를 입력하세요"
	 * @return 형식에 맞는 비밀번호면 비밀번호가 저장된 문자열 반환
	 * @author 민태원
	 * @since 2020.11.04
	 */
	String updatePW();
	
	/**
	 * 이메일 변경 메서드
	 * 출력 : "새로운 이메일을 입력하세요"
	 * @return 형식에 맞는 이메일이면 이메일이 저장된 문자열 반환
	 * @author 민태원
	 * @since 2020.11.04
	 */
	String updateEmail();
	
	/**
	 * 전화번호 변경 메서드
	 * 출력 : "새로운 전화번호를 입력하세요"
	 * @return 형식에 맞는 전화번호이면 전화번호가 저장된 문자열 반환
	 * @author 민태원
	 * @since 2020.11.04
	 */
	String updatePhone();
	
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
	 */
	void printRentalList(String mem_id);
	
	/**
	 * 도서반납 메서드 			
	 * 출력 : "반납하시겠습니까? (y/n)" => y면 반납 / n면 return
	 * @param book_id 반납할 도서의 아이디
	 * @author 민태원
	 * @since 2020.11.04
	 */
	void returnBook(String book_id);
	
	
	/**
	 * 예약도서 리스트 출력 메서드
	 * @param mem_id 조회할 회원의 아이디
	 * @author 민태원
	 * @since 2020.11.04
	 */
	void printBookList(String mem_id);
	
	
	/**
	 * 예약 취소 메서드 
	 * 출력: "취소하시겠습니까? (y/n)" => y면 예약취소 / n면 return
	 * @param book_id 예약된 도서의 아이디
	 * @author 민태원
	 * @since 2020.11.04
	 */
	void rsvCancel(String book_id);
	
	
	
	
	
	
	
	
	
// 공지목록
	/**
	 * 공지목록 리스트출력
	 */
	void noiceListMethod();
	/*
	SELECT *
	FROM   MEMBER
	WHERE  MEM_ID = 'a001'
	AND    MEM_PW = 'asdfasdf';
	
	매개변수가 vo거의 일치해 => VO
	매개변수로 2개~4개 => Map
	MemberVO logIn(String mem_id, String mem_pw);
	
	Map<String,String> params = new HashMap<>();
	params.put("mem_id","a001");
	params.put("mem_pw","asdfasdf");
	MemberVO logIn(Map<String,String> params);
	
	*/

	/**
	 * 공지 등록
	 */
	void noiceAddMethod();

	/**
	 * 공지 삭제
	 */
	void noiceDeltleMethod();

// 희망도서
	/**
	 * 희망도서 등록
	 */
	void hopeBookAddMethod();

	/**
	 * 희망도서 삭제
	 */
	void hopeBookeDeltleMethod();

// 책등록
	/**
	 * 책등록
	 */
	void bookMethod();
}
