package notice;

import java.util.List;

public class INoticeServiceImpl implements INoticeService{
	private static INoticeService service;
	private INoticeDao dao;
	
	private INoticeServiceImpl() {
		dao = INoticeDaoImpl.getInstance();
	}

	public static INoticeService getInstance() {
		if(service == null){
			service = new INoticeServiceImpl();
		}
		return service;
	}
	
	@Override
	public int createNotice(NoticeVO nv) {
		return dao.createNotice(nv);
	}
	
	/**
	 * 공지사항 출력
	 * @author 조애슬
	 */
	@Override
	public List<NoticeVO> noticeList() {
		return dao.noticeList();
	}
	
	/**
	 * 공지사항 상세보기
	 * @author 조애슬
	 */
	@Override
	public NoticeVO openNoDetail(int input) {
		return dao.openNoDetail(input);
	}

	@Override
	public int deleteNotice(int notice_no) {
		return dao.deleteNotice(notice_no);
	}

	
}
