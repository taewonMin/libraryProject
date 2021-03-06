package reserve;

public class ReserveVO {
	private int rsv_no;			//예약번호(PK) 	number - 시퀀스
	private int book_id;		//도서ID(FK)
	private String mem_id;		//회원ID(FK)
	
	//get
	public int getRsv_no() {
		return rsv_no;
	}
	public int getBook_id() {
		return book_id;
	}
	public String getMem_id() {
		return mem_id;
	}
	
	//set
	public void setRsv_no(int rsv_no) {
		this.rsv_no = rsv_no;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
}
