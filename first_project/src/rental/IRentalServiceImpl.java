package rental;

import java.util.List;
import java.util.Map;

public class IRentalServiceImpl implements IRentalService{
	private static IRentalService service;
	private IRentalDao dao;
	
	private IRentalServiceImpl() {
		dao = IRentalDaoImpl.getInstance();
	}

	public static IRentalService getInstance() {
		if(service == null){
			service = new IRentalServiceImpl();
		}
		return service;
	}

	@Override
	public RentalVO createRental(Map<String, Object> map) {
		return dao.createRental(map);
	}

	@Override
	public RentalVO readRentalVO(int book_id) {
		return dao.readRentalVO(book_id);
	}

	@Override
	public List<RentalVO> readRentalList(String mem_id) {
		return dao.readRentalList(mem_id);
	}

	@Override
	public int deleteRental(int book_id) {
		return dao.deleteRental(book_id);
	}
	
	
}
