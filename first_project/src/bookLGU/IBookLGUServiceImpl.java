package bookLGU;

import java.util.List;

import book.BookVO;

public class IBookLGUServiceImpl implements IBookLGUService{
	private static IBookLGUService service;
	private IBookLGUDao dao;
	
	private IBookLGUServiceImpl() {
		dao = IBookLGUDaoImpl.getInstance();
	}

	public static IBookLGUService getInstance() {
		if(service == null){
			service = new IBookLGUServiceImpl();
		}
		return service;
	}
	
	@Override
	public List<BookLGUVO> lguList() {
		
		return dao.lguList();
	}

	@Override
	public List<BookVO> themeList(int book_lgu) {
		
		return dao.themeList(book_lgu);
	}

	@Override
	public BookLGUVO readBookLGU(int booklgu_id) {
		return dao.readBookLGU(booklgu_id);
	}
}
