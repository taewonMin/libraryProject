package package_VO;

public class AdminVO {
	private String admin_id;			//관리자 ID(PK)
	private String admin_pw;			//관리자 pw
	private boolean isActivate = true;	//활성화 여부 - 삭제시 참조테이블 체크


	//get
	public String getadmin_id(){
		return admin_id;
	}
	public String getadmin_pw(){
		return admin_pw;
	}
	public boolean isActivate() {
		return isActivate;
	}
	//set
	public void setadmin_id(String admin_id){
		this.admin_id = admin_id;
	}
	public void setadmin_pw(String admin_pw){
		this.admin_pw = admin_pw;
	}
	public void setActivate(boolean isActivate) {
		this.isActivate = isActivate;
	}
}
