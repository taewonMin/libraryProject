package hope;

public class HopeVO {
	private int hope_id;			//도서코드(PK)	number - 시퀀스
	private String hope_name;		//책이름		VARCHAR2(100)
	private String hope_author;     //작가		VARCHAR2(20)
    private String hope_publisher;  //출판사		VARCHAR2(50)
	private String hope_content;	//희망이유	VARCHAR2(200)
	private String mem_id;			//회원ID(FK)	
	
	
	//get
	public int getHope_id() {
		return hope_id;
	}
	public String getHope_name() {
		return hope_name;
	}
	public String getHope_author() {
		return hope_author;
	}
	public String getHope_publisher() {
		return hope_publisher;
	}
	public String getHope_content() {
		return hope_content;
	}
	public String getMem_id() {
		return mem_id;
	}

	
	//set
	public void setHope_id(int hope_id) {
		this.hope_id = hope_id;
	}
	public void setHope_name(String hope_name) {
		this.hope_name = hope_name;
	}
	public void setHope_author(String hope_author) {
		this.hope_author = hope_author;
	}
	public void setHope_publisher(String hope_publisher) {
		this.hope_publisher = hope_publisher;
	}
	public void setHope_content(String hope_content) {
		this.hope_content = hope_content;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

}
