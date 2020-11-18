package book;

import java.util.List;
import java.util.Map;

public interface IBookDao {

	/**
	 * 도서 추가 메서드
	 * @param bv 추가할 도서의 모든 정보
	 * @return 성공하면 1, 아니면 0
	 * @author 민태원
	 * @since 2020.11.13
	 */
	int createBook(BookVO bv);
	
	/**
     * 도서정보 조회 메서드
     * @param book_id 조회할 도서의 아이디
     * @return 조회할 도서의 모든 정보 반환
     * @author 민태원
     * @since 2020.11.06
     */
	BookVO readBook(int book_id);
	
	/**
	 * 도서리스트 출력
	 * @return
	 * @author 김태규
	 */
	List<BookVO> bookList();
	
	/**
	 * 도서명으로 이름검색
	 * @author 송지은
	 * @param book_name 검색한 도서명
	 * @return book_name받아서 동일한 문자가 들어간 도서 리스트에 담아서 온다.
	 */
	List<BookVO> bookSearch(String book_name);

	/**
	 * 도서 갱신 메서드
	 * @param bv 갱신할 도서의 모든 정보
	 * @return 갱신완료되면 1, 아니면 0
	 * @author 민태원
	 * @since 2020.11.12
	 */
	int updateBook(BookVO bv);

	/**
	 * 도서 삭제 메서드
	 * @param book_no 삭제할 도서의 아이디
	 * @return 삭제되면 1, 아니면 0
	 * @author 김태규
	 * @since 2020.11.13
	 */
	int deleteBook(int book_no);
	
	/**
	 * 엑셀파일로 읽어낸 북리스트를 데이터베이스에 넣는 메서드
	 * @param list
	 * @author 조애슬
	 */
	int bookExcelAdd(List<Map<Integer,String>> list);
	
	/**
	 * 도서목록 db에서 가져오는 메서드
	 * @return list (도서전체목록)
	 */
	List<BookVO> bookExcelout();

}
