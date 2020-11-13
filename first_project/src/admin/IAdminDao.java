package admin;

import java.util.Map;

public interface IAdminDao {
	
	/**
	 * 관리자 로그인
	 * @param params
	 * @return 관리자 vo 를 반환
	 * @author 조애슬
	 * @since 2020-11-13
	 */
	AdminVO adminMatch(Map<String, String> params);

	/**
	 * 관리자 정보 조회 메서드
	 * @param mem_id 조회할 관리자의 아이디
	 * @return 관리자의 모든 정보 반환
	 * @author 민태원
	 * @since 2020.11.13
	 */
	AdminVO readAdmin(String mem_id);
}
