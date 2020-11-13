package member;

import java.util.List;
import java.util.Map;

public interface IMemberDao {
	/**
	 * 아이디중복체크
	 *@param mem_id 멤버 한명의 아이디
	 *@return int 일치하는 사람이 있으면 true를 없으면 false를 반환
	 *@author 조애슬
	 */
	int idUniqCheck(String mem_id);
	
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
	 * 로그인 받은 ID와 PW를 DB와 비교
	 * @author 조애슬
	 * @param mem_id와 mem_pw 를 묶은 map
	 * @return 로그인 한 회원의 memberVO 전체
	 * @since 2020-11-13
	 */
	MemberVO loginMatch(Map<String, String> params);

}
