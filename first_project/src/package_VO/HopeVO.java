package package_VO;

public class HopeVO {
	private String hope_id;			//도서코드 (PK) 
	private String hope_name;		//이름, 출판사
	private String hope_content;	//희망이유		
	private String mem_id;			//회원ID(FK)
	
	
	//get
	public String getHope_id() {
		return hope_id;
	}
	public String getHope_name() {
		return hope_name;
	}
	public String getHope_content() {
		return hope_content;
	}
	public String getMem_id() {
		return mem_id;
	}
	
	//set
	public void setHope_id(String hope_id) {
		this.hope_id = hope_id;
	}
	public void setHope_name(String hope_name) {
		this.hope_name = hope_name;
	}
	public void setHope_content(String hope_content) {
		this.hope_content = hope_content;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
}
