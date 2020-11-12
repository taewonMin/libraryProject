package admin;

public class IAdminDaoImpl implements IAdminDao{
	
	private static IAdminDao dao;
	
	private IAdminDaoImpl() {
		
	}

	public static IAdminDao getInstance() {
		if(dao == null){
			dao = new IAdminDaoImpl();
		}
		return dao;
	}
	
}
