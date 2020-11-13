package blackList;

import java.util.List;

public interface IBlackListService {

	/**
	 * 블랙리스트에 추가될 아이디 체크
	 * @param 멤버의 아이디
	 * @return
	 * @author 김태규
	 */
	int createBlackList(BlackListVO bv);

	/**
	 * 블랙리스트 출력
	 * @return
	 * @author 김태규
	 */
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
	 * 블랙리스트에 삭제
	 * @param 멤버 번호
	 * @return
	 * @author 김태규
	 */
	int blackDeltle(int black_no);

	/**
	 * 블랙리스트에 추가될 아이디 체크
	 * @param 멤버의 아이디
	 * @return
	 * @author 김태규
	 */
	int idcheack(String id);

}
