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
	
	
	/**
	 * 희망도서출력메서드
	 * @author 조애슬
	 */
	@Override
	public List<HopeVO> hopeList() {
		return dao.hopeList();
	}
	
	/**
	 * @author 김태규
	 */
//	@Override
//	public List<HopeVO> hopeList() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	/**
	 * 희망도서 등록
	 * @author 조애슬
	 */
	@Override
	public int hopeListAdd(Map<String,String> params) {
		return dao.hopeListAdd(params);
	}
	
	/**
	 * @author 김태규
	 */
//	@Override
//	public boolean hopeListAdd(HopeVO hv) {
//		// TODO Auto-generated method stub
//		return false;
//	}
	
	/**
	 * 희망도서 상세보기 
	 * @author 조애슬
	 */
	@Override
	public HopeVO hopeDetailView(int hopeNo) {
		
		return dao.hopeDetailView(hopeNo);
	}
	
	/**
	 * 희망도서 삭제하기
	 */
	@Override
	public int hopeRemoveView(String mem_id,int hopeNo) {
		
		return dao.hopeRemoveView(mem_id,hopeNo);
	}
}
