package rental;

import java.util.List;
import java.util.Map;

public interface IRentalDao {

	/**
   	 * 나의 대여목록에 추가하기
   	 * @param map 저장할 대여 정보(mem_id, book_id)
   	 * @return 추가에 성공하였으면 대여 객체 반환
   	 * @author 민태원
   	 * @since 2020.11.07
   	 */
	RentalVO createRental(Map<String, Object> map);

	/**
   	 * 대여테이블의 튜플 "하나만" 검색
   	 * @param book_id 조회할 도서의 아이디
   	 * @return 조회된 대여 튜플 반환
   	 * @author 민태원
   	 * @since 2020.11.06
   	 */
	RentalVO readRentalVO(int book_id);

	/**
	 * 회원의 대출도서리스트 출력 메서드
	 * @param mem_id 조회할 회원의 아이디
	 * @author 민태원
	 * @since 2020.11.12
	 * @return 대출도서목록 반환
	 */
	List<RentalVO> readRentalList(String mem_id);

	/**
   	 * 대여목록에서 삭제하기
   	 * 반납(삭제)된 도서를 예약한 사람이 있으면 그 사람에게 바로 대여
   	 * @param book_id 반납(삭제)할 도서의 아이디
   	 * @return 반납(삭제)에 성공하면 1, 아니면 0
   	 * @author 민태원
   	 * @since 2020.11.12
   	 */
	int deleteRental(int book_id);
}
