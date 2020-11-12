package rental;

public class RentalVO {
	private int rental_id;				// (PK)대여ID number - 시퀀스
	private String rental_start;		// 대여 시작일		date
	private String rental_end;			// 대여 종료일		date
	private String mem_id;				// (FK)회원ID	
	private int book_id;				// (FK)도서ID
	
	//get
	public int getRental_id() {
		return rental_id;
	}
	public String getRental_start() {
		return rental_start;
	}
	public String getRental_end() {
		return rental_end;
	}
	public String getMem_id() {
		return mem_id;
	}
	public int getBook_id() {
		return book_id;
	}
	
	//set
	public void setRental_id(int rental_id) {
		this.rental_id = rental_id;
	}
	public void setRental_start(String rental_start) {
		this.rental_start = rental_start;
	}
	public void setRental_end(String rental_end) {
		this.rental_end = rental_end;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
}
