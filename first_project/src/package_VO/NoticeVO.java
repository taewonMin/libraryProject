package package_VO;

public class NoticeVO {
	
	private int notice_no;				//게시글 번호
	private String notice_title;		//게시글 제목
	private String notice_content;		// 게시글 내용
	private String admin_id;			//관리자 ID(FK)
	
	//get
	public int getNotice_no() {
		return notice_no;
	}
	public String getNotice_title() {
		return notice_title;
	}
	public String getNotice_content() {
		return notice_content;
	}
	//set
	public void setNotice_no(int notice_no) {
		this.notice_no = notice_no;
	}
	public void setNotice_title(String notice_title) {
		this.notice_title = notice_title;
	}
	public void setNotice_content(String notice_content) {
		this.notice_content = notice_content;
	}

}
