package admin;

import java.util.Map;

public class IAdminServiceImpl implements IAdminService{
	private static IAdminService service;
	private IAdminDao dao;
	
	private IAdminServiceImpl() {
		dao = IAdminDaoImpl.getInstance();
	}

	public static IAdminService getInstance() {
		if(service == null){
			service = new IAdminServiceImpl();
		}
		return service;
	}
	
	@Override
	public AdminVO adminMatch(Map<String, String> params) {
		return dao.adminMatch(params);
	}

	@Override
	public AdminVO readAdmin(String mem_id) {
		return dao.readAdmin(mem_id);
	}
}
