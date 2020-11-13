package blackList;

public class BlackListVO {
	private int black_id;		//블랙리스트ID(PK)		NUMBER - 시퀀스
    private String mem_id;		//회원ID(FK)			VARCHAR2(30)
    private String black_day;	//블랙리스트 등록일		DATE
    private String black_end;	//블랙리스트 종료일		DATE
    private String admin_id;	//관리자 아이디(FK)
    
    
	
	//get
    public int getBlack_id() {
		return black_id;
	}
    public String getMem_id() {
    	return mem_id;
    }
    public String getBlack_day() {
    	return black_day;
    }
    public String getBlack_end() {
    	return black_end;
    }
    public String getAdmin_id() {
		return admin_id;
	}
 
    //set
   	public void setBlack_id(int black_id) {
   		this.black_id = black_id;
	}
   	public void setMem_id(String mem_id) {
   		this.mem_id = mem_id;
    }
   	public void setBlack_day(String black_day) {
        this.black_day = black_day;
   	}
   	public void setBlack_end(String black_end) {
        this.black_end = black_end;
    }
   	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
}