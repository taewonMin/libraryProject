package package_VO;

public class BookLGUVO {
   
   private String book_LGU;      //도서분류코드 (PK)
   private String book_theme;      //도서장르
   private boolean isActivate = true;
   
   
  

//set
   public void setBook_LGU(String book_LGU) {
      this.book_LGU = book_LGU;
   }
   public void setBook_theme(String book_theme) {
      this.book_theme = book_theme;
   }
   public boolean isActivate() {
		return isActivate;
	}
   
   
   //get
   public String getBook_LGU() {
      return book_LGU;
   }
   public String getBook_theme() {
      return book_theme;
   }
   public void setActivate(boolean isActivate) {
		this.isActivate = isActivate;
	}
   
   
}