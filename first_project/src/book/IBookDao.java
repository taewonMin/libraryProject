package book;

import java.util.List;

public interface IBookDao {

	/**
     * 도서정보 조회 메서드
     * @param book_id 조회할 도서의 아이디
     * @return 조회할 도서의 모든 정보 반환
     * @author 민태원
     * @since 2020.11.06
     */
	BookVO readBook(int book_id);
	
	/**
	 * 
	 * @param book_name
	 * @return
	 * @author 송지은
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
}
