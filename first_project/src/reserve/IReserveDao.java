package reserve;

import java.util.List;
import java.util.Map;

public interface IReserveDao {

	/**
   	 * 내 예약목록에 추가하기
   	 * @param rsv 예약목록에 추가할 예약정보
   	 * @return 예약 완료면  1, 아니면 0
   	 * @author 민태원
   	 * @since 2020.11.05
   	 */
	int createReserve(Map<String, Object> rsvMap);

	/**
	 * 나의 예약도서 리스트 출력 메서드
	 * @param mem_id 조회할 회원의 아이디
	 * @return 예약도서 목록 반환
	 * @author 민태원
	 * @since 2020.11.12
	 */
	List<ReserveVO> readReserveList(String mem_id);
	
	/**
   	 * 예약테이블의 튜플 "하나만" 검색
   	 * @param book_id 조회할 도서의 아이디
   	 * @return 조회된 예약 튜플 반환
   	 * @author 민태원
   	 * @since 2020.11.06
   	 */
	ReserveVO readReserveVO(int book_id);

	/**
   	 * 예약목록에서 삭제하기
   	 * @param newRsv 회원아이디, 도서아이디
   	 * @return 삭제에 성공하면 1, 아니면 0
   	 * @author 민태원
   	 * @since 2020.11.05
   	 */
	int deleteReserve(Map<String, Object> newRsv);


}
