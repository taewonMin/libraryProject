package admin;

public class AdminVO {
	private String admin_id;			//관리자 ID(PK)	VARCHAR2(30)
	private String admin_pw;			//관리자 pw		VARCHAR2(30)
	private String isActivate = "T";	//활성화 여부 - 삭제시 참조테이블 체크	CHAR(1)


	//get
	public String getadmin_id(){
		return admin_id;
	}
	public String getIsActivate() {
		return isActivate;
	}
	public String getadmin_pw(){
		return admin_pw;
	}
	//set
	public void setadmin_id(String admin_id){
		this.admin_id = admin_id;
	}
	public void setadmin_pw(String admin_pw){
		this.admin_pw = admin_pw;
	}
	public void setIsActivate(String isActivate) {
		this.isActivate = isActivate;
	}
}
