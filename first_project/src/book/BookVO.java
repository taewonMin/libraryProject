package book;

public class BookVO {
   private int book_id;				//도서코드 (PK)	NUMBER - 시퀀스
   private String book_name;		//이름		VARCHAR2(100)
   private String book_author;		//작가		VARCHAR2(20)
   private String book_summary;		//줄거리		VARCHAR2(500)
   private String book_publisher;	//출판사		VARCHAR2(50)
   private int book_LGU;			//도서분류코드 (FK)		NUMBER(2)
   private String book_state = "T";	//대여상태 (true면 대여가능 / false면 대여불가=대여중)		CHAR(1)
   private String isActivate = "T";	//활성화 여부 - 삭제시 참조테이블 체크	CHAR(1)

   
//get
   public int getBook_id() {
	  return book_id;
   }
   public String getBook_name() {
      return book_name;
   }
   public String getBook_author() {
      return book_author;
   }
   public String getBook_summary() {
      return book_summary;
   }
   public String getBook_publisher() {
      return book_publisher;
   }
   public int getBook_LGU() {
	  return book_LGU;
   }
   public String getBook_state() {
	  return book_state;
   }
   public String getIsActivate() {
	  return isActivate;
   }

   //set
   public void setBook_id(int book_id) {
	  this.book_id = book_id;
   }
   public void setBook_name(String book_name) {
      this.book_name = book_name;
   }
   public void setBook_author(String book_author) {
      this.book_author = book_author;
   }
   public void setBook_summary(String book_summary) {
      this.book_summary = book_summary;
   }
   public void setBook_publisher(String book_publisher) {
      this.book_publisher = book_publisher;
   }
   public void setBook_LGU(int book_LGU) {
	  this.book_LGU = book_LGU;
   }
   public void setBook_state(String book_state) {
	   this.book_state = book_state;
   }
   public void setIsActivate(String isActivate) {
	   this.isActivate = isActivate;
   }
}