package notice;

public class NoticeVO {
	private int notice_no;				//게시글 번호(PK) number 시퀀스
	private String notice_title;		//게시글 제목		varchar2(50)
	private String notice_content;		//게시글 내용		varchar2(200)
	private String notice_date; 		//작성일자		date
	private String admin_id;			//관리자 ID(FK)	varchar2(30)
	
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
	public String getNotice_date() {
		return notice_date;
	}
	public String getAdmin_id() {
		return admin_id;
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
	public void setNotice_date(String notice_date) {
		this.notice_date = notice_date;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
}
