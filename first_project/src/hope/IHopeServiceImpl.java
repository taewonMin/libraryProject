package hope;

import java.util.List;
import java.util.Map;

public class IHopeServiceImpl implements IHopeService{
	private static IHopeService service;
	private IHopeDao dao;
	
	private IHopeServiceImpl() {
		dao = IHopeDaoImpl.getInstance();
	}

	public static IHopeService getInstance() {
		if(service == null){
			service = new IHopeServiceImpl();
		}
		return service;
	}
	
	
	@Override
	public List<HopeVO> hopeList() {
		return dao.hopeList();
	}
	
	@Override
	public int hopeListAdd(Map<String,String> params) {
		return dao.hopeListAdd(params);
	}
	
	@Override
	public HopeVO hopeDetailView(int hopeNo) {
		return dao.hopeDetailView(hopeNo);
	}
	
	@Override
	public int hopeRemoveView(String mem_id,int hopeNo) {
		return dao.hopeRemoveView(mem_id,hopeNo);
	}

	@Override
	public int deleteHope(int hope_id) {
		return dao.deleteHope(hope_id);
	}

	@Override
	public int hopeThumb(int hope_id) {
		return dao.hopeThumb(hope_id);
	}
}
