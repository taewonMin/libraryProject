package rental;

import java.util.List;
import java.util.Map;

public interface IRentalService {

	/**
	 * 대여시 대여테이블에 저장할 객체 생성 메서드
	 * @param map 회원의아이디, 도서의 아이디
	 * @return 객체 생성에 성공하면 객체 반환
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
	 * @since 2020.11.04
	 * @return 대출도서목록 반환
	 */
	List<RentalVO> readRentalList(String mem_id);

	/**
	 * 도서반납(삭제) 메서드 			
	 * @param map 반납(삭제)할 도서의 아이디
	 * @return 반납에 성공하면 1, 아니면 0
	 * @author 민태원
	 * @since 2020.11.06
	 */
	int deleteRental(int book_id);
	
	/**
	 * 대여목록 엑셀로 내보내는 메서드
	 * @param params
	 * @author 조애슬
	 * @since 2020.11.16
	 */
	void rentalExcelout(Map<String, String> params);

	/**
	 * 대여목록 pdf로 내보내는 메서드
	 * @author 김태규
	 * @since 2020.11.17
	 */
	void createRentalPdf(Map<String, String> params);
}
