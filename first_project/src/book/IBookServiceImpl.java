package book;

import java.util.List;

public class IBookServiceImpl implements IBookService{
	private static IBookService service;
	private IBookDao dao;
	
	private IBookServiceImpl() {
		dao = IBookDaoImpl.getInstance();
	}

	public static IBookService getInstance() {
		if(service == null){
			service = new IBookServiceImpl();
		}
		return service;
	}

	@Override
	public BookVO readBook(int book_id) {
		return dao.readBook(book_id);
	}

	@Override
	public List<BookVO> bookSearch(String book_name) {
		return dao.bookSearch(book_name);
	}
	
	@Override
	public int updateBook(BookVO bv) {
		return dao.updateBook(bv);
	}
}
