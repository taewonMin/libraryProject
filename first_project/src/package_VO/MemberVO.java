package package_VO;

public class MemberVO {
   private String mem_id;			//회원ID(PK)
   private String mem_name;			//회원명
   private String mem_pw;			//비밀번호
   private String mem_bir;			//생년월일
   private String mem_email;		//이메일
   private String mem_tel;			//전화번호
   private int rent_count;			//대출횟수 카운트
   private boolean isActivate = true;	//활성화 여부 - 삭제시 참조테이블 체크
   
   
   //get
   public String getMem_id() {
	   return mem_id;
   }
   public String getMem_name() {
      return mem_name;
   }
   public String getMem_pw() {
      return mem_pw;
   }
   public String getMem_bir() {
      return mem_bir;
   }
   public String getMem_email() {
      return mem_email;
   }
   public String getMem_tel() {
      return mem_tel;
   }
   public int getRent_count() {
      return rent_count;
   }
   public boolean isActivate() {
	  return isActivate;
   }
   
   //set
   public void setMem_id(String mem_id) {
      this.mem_id = mem_id;
   }
   public void setMem_name(String mem_name) {
      this.mem_name = mem_name;
   }
   public void setMem_pw(String mem_pw) {
      this.mem_pw = mem_pw;
   }
   public void setMem_bir(String mem_bir) {
      this.mem_bir = mem_bir;
   }
   public void setMem_email(String mem_email) {
      this.mem_email = mem_email;
   }
   public void setMem_tel(String mem_tel) {
      this.mem_tel = mem_tel;
   }
   public void setRent_count(int rent_count) {
	   this.rent_count = rent_count;
   }
   public void setActivate(boolean isActivate) {
	   this.isActivate = isActivate;
   }
}