package blackList;

import java.util.List;

public interface IBlackListDao {

	/**
	 * 블랙리스트 추가
	 * @param id 추가할 블랙의 아이디
	 * @author 김태규
	 * @since 2020-11-12
	 */
	int createBlackList(BlackListVO bv);
	
	/**
	 * 블랙리스트 출력
	 * @author 김태규
	 **/
	List<BlackListVO> blackList();

	/**
	 * 블랙리스트 조회
	 * @param black_no 조회할 아이디
	 * @return 조회된 블랙멤버의 모든 정보
	 * @author 민태원
	 * @since 2020.11.13
	 */
	BlackListVO readBlackList(int black_no);
	
	/**
	 * 블랙리스트 삭제
	 * @param black_no 삭제할 블랙의 아이디
	 * @author 김태규
	 * @since 2020-11-12
	 */
	int blackDeltle(int black_no);

	/**
	 * 블랙리스트 추가될아이디체크 . 중복이면 1을 반환 중복이아니면 0
	 * @return int
	 * @author 김태규
	 **/
	int idcheack(String id);


}
