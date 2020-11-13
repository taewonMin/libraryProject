package bookLGU;

import java.util.List;

import book.BookVO;

public interface IBookLGUService {
	
	/**
	 * 도서 장르 리스트를 가지고 오는 메서드
	 * @author 송지은
	 * @return
	 */
	List<BookLGUVO> lguList();

	/**
	 * 도서 장르 선택시, 해당되는 도서 리스트를 가져오는 메서드
	 * @author 송지은
	 * @param book_lgu 도서 장르 번호
	 * @return
	 */
	List<BookVO> themeList(int book_lgu);
	
	/**
	 * 도서 장르 조회 메서드
	 * @param booklgu_id 조회할 분류코드
	 * @return 조회된 도서 장르 객체 반환
	 * @author 민태원
	 * @since 2020.11.13
	 */
	BookLGUVO readBookLGU(int booklgu_id);
}
