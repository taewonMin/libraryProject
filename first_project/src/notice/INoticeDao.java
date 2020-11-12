package notice;

import java.util.List;

public interface INoticeDao {
	
	/**
	 * 공지추가 메서드
	 * @param 추가할 공지의 모든 정보
	 * @return 추가되면 1, 아니면 0
	 * @author 민태원
	 * @since 2020.11.12
	 */
	int createNotice(NoticeVO nv);
	
	List<NoticeVO> noticeList();

	NoticeVO openNoDetail(int input);

	/**
	 * 공지 삭제 메서드
	 * @param notice_no 삭제할 공지 번호
	 * @return 삭제되면 1, 아니면 0
	 * @author 민태원
	 * @since 2020.11.12
	 */
	int deleteNotice(int notice_no);

}
