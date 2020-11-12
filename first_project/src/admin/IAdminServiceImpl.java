package admin;

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
}
