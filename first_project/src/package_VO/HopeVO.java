package package_VO;

public class HopeVO {
	private String hope_id;            //도서코드 (PK)
	private String hope_name;         //이름
	private String mem_id;				//회원ID(FK)
	
	
	public String getHope_id() {
		return hope_id;
	}
	public void setHope_id(String hope_id) {
		this.hope_id = hope_id;
	}
	public String getHope_name() {
		return hope_name;
	}
	public void setHope_name(String hope_name) {
		this.hope_name = hope_name;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
}
