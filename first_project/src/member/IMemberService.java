package member;

import java.util.List;
import java.util.Map;


public interface IMemberService {

	/**
	 * 아이디중복체크
	 *@param mem_id 멤버 한명의 아이디
	 *@return int 일치하는 사람이 있으면 true를 없으면 false를 반환
	 *@author 조애슬
	 */
	int idUniqCheck(String mem_id); //반환 타입 int (count로 세려고)

	//SELECT count(mem_id)//boolean으로 하는것보다 count트로 0이면 중복아님 1이상이면 중복임으로 하기
	//FROM  MEMBER
	//WHERE MEM_ID = 'a001'
		
	/**
	 * 회원가입 입력값을 MemberVO 객체로 묶은걸 DB로 보내는 메서드
	 * @param params
	 * @return int
	 * @author 조애슬
	 * @since 2020-11-04
	 */
	int createMember(MemberVO params);
	
	/**
    * 회원정보 조회 메서드
    * @param mem_id 조회할 회원의 아이디
    * @return 조회할 회원의 모든 정보 반환
    * @author 민태원
    * @since 2020.11.12
    */
	MemberVO readMember(String mem_id);
	
	/**
	 * 회원 리스트 출력
	 * 
	 * @author 김태규
	 */
	List<MemberVO> memberList();
	
	/**
    * 회원정보 갱신 메서드
    * @param myInfo 수정할 내 정보를 Map으로 받아 업데이트
    * @return 갱신 성공하면 1, 아니면 0
    * @author 민태원
    * @since 2020.11.12
    */
	int updateMember(Map<String, String> myInfo);
	
	/**
     * 회원정보 삭제 메서드
     * @param mem_id 탈퇴할 멤버의 아이디
     * @return 비활성화 성공하면 1, 아니면 0
     * @author 민태원
     * @since 2020.11.12
     */
	int deleteMember(String mem_id);
	
	/**
	 * 로그인 입력받은거 MemberVO와 비교
	 * @param Map<> in, Map<> pw
	 * @return MemberVO //MemberVO nowmember를 null에서 로그인한 회원으로 바꿔주기위해
	 * @since 2020-11-04
	 * @author 조애슬
	 */
	MemberVO loginMatch(Map<String, String> params); // 반환타입 MemberVO, String
	
	/**
	 * 회원리스트 엑셀파일로 내보내는 메서드
	 * @param mname
	 * @author 조애슬
	 */
	void memberExcelout(String mname);

	/**
	 * 회원리스트 pdf파일로 내보내는 메서드
	 * @param mname 회원의 아이디
	 * @author 민태원
	 * @since 2020.11.17
	 */
	void memberPdfOut(String fname);
}
