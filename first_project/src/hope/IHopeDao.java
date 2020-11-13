package hope;

import java.util.List;
import java.util.Map;

public interface IHopeDao {

	/**
	 * 희망도서 리스트 출력
	 * @author 조애슬
	 * @since 2020-11-12
	 * @return List
	 */
	List<HopeVO> hopeList();

	/**
	 * 희망도서 글 등록 등록되면 양수리턴
	 * @return int
	 * @author 조애슬
	 * @since 2020-11-12
	 */
	int hopeListAdd(Map<String, String> params);

	/**
	 * 희망도서 상세보기
	 * @return hopeno가 일치하는 hopeVO 글 하나를 반환
	 * @author 조애슬
	 * @since 2020-11-12
	 */
	HopeVO hopeDetailView(int hopeNo);

	/**
	 * 희망도서 삭제
	 * @return 삭제에 성공하면 1반환
	 * @param 삭제를 시도하는 멤버의 아이디와 삭제하고자 하는 글 번호
	 * @since 2020-11-12
	 * @author 조애슬
	 */
	int hopeRemoveView(String mem_id, int hopeNo);

	/**
	 * 희망도서 삭제 메서드
	 * @param hope_id 희망도서 아이디
	 * @return 삭제에 성공하면 1, 아니면 0
	 * @author 민태원
	 * @since 2020.11.13
	 */
	int deleteHope(int hope_id);

	/**
	 * 희망글 추천메서드
	 * @param hope_id 희망도서 아이디
	 * @return 추천에 성공하면 1반환
	 * @author 조애슬
	 * @since 2020.11.13  
	*/
	int hopeThumb(int hope_id);
	
}
