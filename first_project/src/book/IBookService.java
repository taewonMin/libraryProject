package book;

import java.util.List;

public interface IBookService {

	/**
	 * 도서 추가 메서드
	 * @param bv 추가할 도서의 모든 정보
	 * @return 성공하면 1, 아니면 0
	 * @author 민태원
	 * @since 2020.11.13
	 */
	int createBook(BookVO bv);
	
	/**
	 * 도서 검색 메서드
	 * @param book_id 도서의 아이디
	 * @return 검색된 도서의 모든 정보를 반환
	 * @author 민태원
	 * @since 2020.11.07
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
	 * @return book_name받아서 동일한 문자가 들어간 도서 리스트를 담아오는 메서드
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
	 * 도서목록 엑셀파일로 불러들여 한번에 추가하기 //츄가
	 * @param bPath
	 * @author 조애슬
	 */
	void bookExcelAdd(String bPath);
	
	/**
	 * 도서목록 엑셀파일로 내보내는 메서드 //츄가
	 * @author 조애슬
	 * @param fname 
	 */
	void bookExcelout(String fname);

	/**
	 * 도서목록 pdf로 내보내는 메서드
	 * @param fname 파일 이름
	 * @author 민태원
	 * @since 2020.11.17
	 */
	void bookPdfOut(String fname);
}
