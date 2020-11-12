package bookLGU;

public class BookLGUVO {
   
   private int book_LGU;      		//도서분류코드 (PK)		NUMBER(2)
   private String book_theme;      		//도서장르	VARCHAR2(20)
   private String isActivate = "T";	//활성화 여부 - 삭제시 참조테이블 체크	CHAR(1)
   
   //get
   public int getBook_LGU() {
	   return book_LGU;
   }
   public String getBook_theme() {
	   return book_theme;
   }
   public String getIsActivate() {
	   return isActivate;
   }
   
   //set
   public void setBook_theme(String book_theme) {
      this.book_theme = book_theme;
   }
   public void setBook_LGU(int book_LGU) {
	   this.book_LGU = book_LGU;
   }
   public void setIsActivate(String isActivate) {
	   this.isActivate = isActivate;
   }
}