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
	 * 희망도서 리스트 출력
	 * @author 조애슬
	 * @since 2020-11-12
	 * @return List
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
	 * 희망도서 글 등록 등록되면 양수리턴
	 * @return int
	 * @author 조애슬
	 * @since 2020-11-12
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
	 * @return hopeno가 일치하는 hopeVO 글 하나를 반환
	 * @author 조애슬
	 * @since 2020-11-12
	 */
	@Override
	public HopeVO hopeDetailView(int hopeNo) {
		
		return dao.hopeDetailView(hopeNo);
	}
	
	/**
	 * 희망도서 삭제
	 * @return 삭제에 성공하면 1반환
	 * @param 삭제를 시도하는 멤버의 아이디와 삭제하고자 하는 글 번호
	 * @since 2020-11-12
	 * @author 조애슬
	 */
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
