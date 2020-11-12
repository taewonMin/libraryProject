package member;

import java.util.Map;

public class IMemberServiceImpl implements IMemberService{
	private static IMemberService service;
	private IMemberDao dao;
	
	private IMemberServiceImpl() {
		dao = IMemberDaoImpl.getInstance();
	}

	public static IMemberService getInstance() {
		if(service == null){//아직 IMEMBERSERVICE가 안만들어졌으면
			service = new IMemberServiceImpl(); //서비스임플객체 새로만들자 (처음이자 마지막)
		}
		return service;
	}
	
	/**
	 * 로그인 입력받은거 MemberVO와 비교
	 * @param Map<> in, Map<> pw
	 * @return MemberVO //MemberVO nowmember를 null에서 로그인한 회원으로 바꿔주기위해
	 * @since 2020-11-04
	 */
	@Override
	public MemberVO loginMatch(Map<String, String> params) {
		
		return dao.loginMatch(params);
	}
	
	/**
	 * 아이디중복체크. 중복값이 있으면 증가된 count가 리턴된다
	 * @param mem_id
	 * @return int
	 * @author 조애슬
	 * @since 2020-11-06 
	 */
	@Override
	public int idUniqCheck(String mem_id) {
		
		return dao.idUniqCheck(mem_id);
	}
	
//회원 CRUD
	@Override
	public int createMember(MemberVO params) {
		return dao.createMember(params);
	}
	
	@Override
	public MemberVO readMember(String mem_id) {
		return dao.readMember(mem_id);
	}
	
	@Override
	public int updateMember(Map<String, String> myInfo) {
		return dao.updateMember(myInfo);
	}
	
	@Override
	public int deleteMember(String mem_id) {
		return dao.deleteMember(mem_id);
	}
	
	/**
    * 로그인 입력받은거 AdminVO와 비교
    * @param params
    * @return boolean
    */
	@Override
	public int adminMatch(Map<String, String> params) {
		return dao.adminMatch(params);
	}


}
