package blackList;

import java.util.List;

public class IBlackListServiceImpl implements IBlackListService{
	private static IBlackListService service;
	private IBlackListDao dao;
	
	private IBlackListServiceImpl() {
		dao = IBlackListDaoImpl.getInstance();
	}

	public static IBlackListService getInstance() {
		if(service == null){
			service = new IBlackListServiceImpl();
		}
		return service;
	}
	
	@Override
	public int createBlackList(BlackListVO bv) {
		return dao.createBlackList(bv);
	}

	@Override
	public List<BlackListVO> blackList() {
		return dao.blackList();
	}
	
	@Override
	public BlackListVO readBlackList(int black_no) {
		return dao.readBlackList(black_no);
	}
	
	@Override
	public int blackDeltle(int black_no) {
		return dao.blackDeltle(black_no);
	}

	@Override
	public int idcheack(String id) {
		return dao.idcheack(id);
	}
}
