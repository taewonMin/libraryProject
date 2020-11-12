package member;

import java.util.Map;

public interface IMemberDao {
	int idUniqCheck(String mem_id);

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
	
	MemberVO loginMatch(Map<String, String> params);

	int adminMatch(Map<String, String> params);


	


}
