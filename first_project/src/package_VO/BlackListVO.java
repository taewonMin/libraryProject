package package_VO;

public class BlackListVO {
	private String black_id;	//블랙리스트ID(PK)
    private String mem_id;		//회원ID(FK)
    private String black_day;	//블랙리스트 등록일
    private String black_end;	//블랙리스트 종료일
    
    //get
    public String getBlack_id() {
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
 
    //set
   	public void setBlack_id(String black_id) {
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
}