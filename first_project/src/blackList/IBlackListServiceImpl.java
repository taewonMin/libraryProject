package blackList;

import java.util.List;

import member.MemberVO;

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
	public List<BlackListVO> blackListList() {
		return dao.blackListList();
		
	}

	@Override
	public boolean createBlackList(String id) {
		return createBlackList(id);
	}

	@Override
	public boolean blackDeltleMethod(String id) {
		return dao.blackDeltleMethod(id);
	}
}
