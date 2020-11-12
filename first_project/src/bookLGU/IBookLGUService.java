package bookLGU;

import java.util.List;

import book.BookVO;

public interface IBookLGUService {
	
	/**
	 * 
	 * @return
	 * @author 송지은
	 */
	List<BookLGUVO> lguList();

	/**
	 * 
	 * @param book_lgu
	 * @return
	 * @author 송지은
	 */
	List<BookVO> themeList(int book_lgu);
}
