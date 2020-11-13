package admin;

import java.util.Map;

public interface IAdminService {

	/** 로그인 입력받은거 AdminVO와 비교
    * @param params
    * @return boolean
    * @author 조애슬
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
