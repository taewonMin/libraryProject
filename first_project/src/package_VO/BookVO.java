package package_VO;

public class BookVO {
   private String book_id;            //도서코드 (PK)
   private String book_name;         //이름
   private String book_theme;         //장르
   private String book_author;         //작가
   private String book_summary;      //줄거리
   private String book_publisher;      //출판사
   private String book_LGU;         //도서분류코드 (FK)
   private boolean isActivate = true;

   
   
   public boolean isActivate() {
	return isActivate;
	}
	public void setActivate(boolean isActivate) {
		this.isActivate = isActivate;
	}
	public String getBook_id() {
      return book_id;
   }
   public void setBook_id(String book_id) {
      this.book_id = book_id;
   }
   public String getBook_name() {
      return book_name;
   }
   public void setBook_name(String book_name) {
      this.book_name = book_name;
   }
   public String getBook_theme() {
      return book_theme;
   }
   public void setBook_theme(String book_theme) {
      this.book_theme = book_theme;
   }
   public String getBook_author() {
      return book_author;
   }
   public void setBook_author(String book_author) {
      this.book_author = book_author;
   }
   public String getBook_summary() {
      return book_summary;
   }
   public void setBook_summary(String book_summary) {
      this.book_summary = book_summary;
   }
   public String getBook_publisher() {
      return book_publisher;
   }
   public void setBook_publisher(String book_publisher) {
      this.book_publisher = book_publisher;
   }
   public String getBook_LGU() {
      return book_LGU;
   }
   public void setBook_LGU(String book_LGU) {
      this.book_LGU = book_LGU;
   }

   
}