package reserve;

import java.util.List;
import java.util.Map;

public class IReserveServiceImpl implements IReserveService{
	private static IReserveService service;
	private IReserveDao dao;
	
	private IReserveServiceImpl() {
		dao = IReserveDaoImpl.getInstance();
	}

	public static IReserveService getInstance() {
		if(service == null){
			service = new IReserveServiceImpl();
		}
		return service;
	}

	@Override
	public int createReserveVO(Map<String, Object> rsvMap) {
		return dao.createReserve(rsvMap);
	}

	@Override
	public ReserveVO readReserveVO(int book_id) {
		return dao.readReserveVO(book_id);
	}

	@Override
	public int deleteReserve(Map<String, Object> newRsv) {
		return dao.deleteReserve(newRsv);
	}

	@Override
	public List<ReserveVO> readReserveList(String mem_id) {
		return dao.readReserveList(mem_id);
	}
}
