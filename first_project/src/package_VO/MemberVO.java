package package_VO;

public class MemberVO {
   
   private String mem_id;
   private String mem_name;
   private String mem_pw;
   private String mem_bir;
   private String mem_email;
   private String mem_tel;
   private int rent_count;
   private boolean isActivate = true;
   
   public boolean isActivate() {
	return isActivate;
	}
	public void setActivate(boolean isActivate) {
		this.isActivate = isActivate;
	}
	public String getMem_id() {
      return mem_id;
   }
   public void setMem_id(String mem_id) {
      this.mem_id = mem_id;
   }
   public String getMem_name() {
      return mem_name;
   }
   public void setMem_name(String mem_name) {
      this.mem_name = mem_name;
   }
   public String getMem_bir() {
      return mem_bir;
   }
   public void setMem_bir(String mem_bir) {
      this.mem_bir = mem_bir;
   }
   public String getMem_email() {
      return mem_email;
   }
   public void setMem_email(String mem_email) {
      this.mem_email = mem_email;
   }
   public String getMem_tel() {
      return mem_tel;
   }
   public void setMem_tel(String mem_tel) {
      this.mem_tel = mem_tel;
   }
   public String getMem_pw() {
      return mem_pw;
   }
   public void setMem_pw(String mem_pw) {
      this.mem_pw = mem_pw;
   }
   public int getRent_count() {
      return rent_count;
   }
   public void setRent_count(int rent_count) {
      this.rent_count = rent_count;
   }
   
}