package package_VO;

public class ReserveVO {
	private String rsv_no;		//예약번호(PK)
	private String book_id;		//도서ID(FK)
	private String mem_id;		//회원ID(FK)
	
	//get
	public String getRsv_no() {
		return rsv_no;
	}
	public String getBook_id() {
		return book_id;
	}
	public String getMem_id() {
		return mem_id;
	}
	
	//set
	public void setRsv_no(String rsv_no) {
		this.rsv_no = rsv_no;
	}
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
}
