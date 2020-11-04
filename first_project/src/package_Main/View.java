package package_Main;

import java.util.*;

import package_VO.*;

public class View {
	private MemberVO nowMember = null;
	
	Scanner sc = new Scanner(System.in);
	
	/**
	 * 시작 메서드
	 */
	void startMethod() {
		//로그인,회원가입
		while(true) {
			System.out.println("1. 로그인");
			System.out.println("2. 회원가입");
			System.out.println("3. 종료");
			
			int input = 0;
			try {
				input = sc.nextInt();
			}catch(Exception e) {
				System.out.println("숫자만 입력해주세요.");
				continue;
			}
			
			switch(input) {
			case 1:
				loginSessionView();
				break;
			case 2:
				joinMemberView();
				break;
			case 3:
				endProgram();
				break;
			default:
				System.out.println("다시 입력해주세요.");
			}
		}
	}
	
	/**
    * 시작배너
    */
   private void showBanner(String str) { 
      System.out.println();
      System.out.println("=======================================================");
      System.out.println("\t\t\t JJUGURI BOOK");
      System.out.println("───────────────────────────────────────────────────────");
      System.out.println("\t\t\t" + str + "\t\t\t" );
      System.out.println("───────────────────────────────────────────────────────");
      System.out.println();
   }

   /**
    * 종료메서드
    */
   private void endProgram(){
      showBanner("\n\t프로그램을 종료합니다. 이용해 주셔서 감사합니다.\n");
      System.exit(0);
   }
	
   /**
    * 로그인 메서드 
    * @author 조애슬
    */
   private void loginSessionView(){ 
      System.out.println("아이디를 입력하세요");
      String mem_id = sc.next();
      System.out.println("패스워드를 입력하세요");
      String mem_pw = sc.next();
      showBanner("로그인 성공");
      homeView();
  }
	
	/**
    * 회원가입 메서드(ex1) ->MemberVO
    * @author 조애슬
    */
   private void joinMemberView(){
      showBanner("회원가입");
      
      System.out.println("아이디를 입력하세요");
//      String mem_id = inputId();
      System.out.println("패스워드를 입력하세요");
      String mem_pw = sc.next();
      System.out.println("이름을 입력하세요");
      String mem_name = sc.next();
      System.out.println("생년월일을 입력하세요");
      String mem_bir = sc.next();
      System.out.println("핸드폰번호를 입력하세요");
      String mem_tel = sc.next();
      System.out.println("이메일을 입력하세요");
      String mem_email = sc.next();
      
      //DataBase
      // boolean createMember(MemberVO mv);
      
      showBanner("회원가입 성공");
  }

	
	/**
	 * 메인화면 - 검색, 게시판, 마이페이지, 반납
	 */
	private void homeView() {
		while(true) {
			System.out.println("[1]도서검색");
		    System.out.println("[2]게시판");
		    System.out.println("[3]마이페이지");
		    System.out.println("[4]반납");
		    System.out.println("<로그아웃(Q/q)>");
		    
		    int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("다시 입력해주세요");
				continue;
			}
			
			switch(input) {
			case 1:
				bookSearchView();	//도서검색
				break;
			case 2:
				boardView();	//게시판
				break;
			case 3:
				myPageView();	//마이페이지
//					if(!nowMember.isActivate())	// 탈퇴했으면
//						return;
				break;
			case 4:
				rentalListView();	//반납 - 대출목록
				break;
			case 0:
				return;
			default:
				System.out.println("다시 입력해주세요");
			}
		}
	}
//////////////////////////////////////////검색///////////////////////////////////////////////	
	/**
	 * 도서검색
	 */
   void bookSearchView() {
      
      showBanner("도서검색");
      
      while (true) {
    	 System.out.println("[1]도서명 검색");
    	 System.out.println("[2]장르 검색");
    	 System.out.println("<뒤로(Q/q)>");
    	 
    	 int input = 0;
         try {
        	input = sc.nextInt();
         } catch (Exception e) {
        	 System.out.println("다시 입력해주세요");
        	 continue;
         }
         
         switch (input) {
         case 1:
        	 bookNameSearchView();
        	 break;
         case 2:
        	 genreSearchView();
        	 break;
         case 0:
        	 return;
         default:
        	 System.out.println("다시 입력해주세요");
         }
      }
   }
   
   /**
    *  도서 이름으로 검색
    */
   void bookNameSearchView() {
      String input = "";
      showBanner("도서명 검색");
      System.out.println("<뒤로(Q/q)>");
      System.out.println("도서명: ");

      while (!input.toLowerCase().equals("q")) {
         try {
        	 input = sc.next();
        	 //도서명 검색
         } catch (Exception e) {
        	 System.out.println("잘못된 입력입니다.");
             return;
         }
      }
      
   }
   
   /**
    * 장르별 검색
    */
   void genreSearchView() {
      String input = "";
      showBanner("장르별 검색");
      System.out.println("<뒤로(Q/q)>"); 
      System.out.println("장르명: ");
      
      while (!input.toLowerCase().equals("q")) {
         try {
        	 input = sc.next();
             switch (Integer.parseInt(input)) {
             
             }
         } catch (Exception e) {
        	 System.out.println("잘못된 입력입니다.");
             return;
         }
      }
   }
   
/////////////////////////////게시판//////////////////////////////////   
   /**
    * 게시판메서드
    * @author 조애슬
    */
   private void boardView(){
      while(true){
         System.out.println("=============================");
         System.out.println("게시판");
         System.out.println("=============================");
         System.out.println("사용하실 메뉴를 선택해주세요");
         System.out.println("1.공지사항");
         System.out.println("2.희망도서목록");
         System.out.println("3.종료");
         System.out.println("<뒤로(Q/q)>");
         System.out.println("=============================");
         
         int input = 0;
         try{
            input = sc.nextInt();
         }catch(Exception e){
            System.out.println("숫자를 입력해주세요.");
            continue;
         }
         switch(input){
         case 1 :
        	 noticeView();
        	 break;
         case 2 :
        	 hopeView();
        	 break;
         case 3 :
        	 endProgram();
        	 break;
         case 0 :
        	 return;
         default : 
        	 System.out.println("다시 입력해주세요");
         }
      }
   }

   /**
    * 공지사항 메서드
    * @author 조애슬
    */
   private void noticeView(){
      showBanner("공지사항");
      System.out.println("No"+"\t제목"+"\t내용");
      System.out.println("1" + "\t휴관안내"+"\t2020.10.5-2020.10.31 도서관 휴관합니다.");
      System.out.println("2" + "\t행사안내"+"\t2020.11.3 문화축제 소식을 알립니다.");
      System.out.println("3" + "\t유의사항"+"\t도서관에서는 항상 조용히 걸어주세요");
      
      int input = 0;
      while(true){
         System.out.println("=============================");
         System.out.println("<뒤로(Q/q)>");
         System.out.println("=============================");
         try{
            input = sc.nextInt();
         }catch(Exception e){
            System.out.println("숫자를 입력해주세요.");
            continue;
         }
         
         switch(input){
            case 0 :
               return;
         default : 
        	 System.out.println("다시 입력해주세요");
        	 break;
         }
      }
   }

   /**
    * 희망도서목록 메서드
    * @author 조애슬
    */
   private void hopeView(){
      
      showBanner("희망도서");
      System.out.println("No"+"\t제목"+"\t\t저자"+"\t출판사");
      System.out.println("1" + "\t비행운"+"\t\t김애란"+"\t나무출판사");
      System.out.println("2" + "\t아몬드"+"\t\t박떙땡"+"\t작은창출판사");
      System.out.println("3" + "\t우울할떈 뇌과학"+"\t엘릭스코드"+"\t한강출판사");
      
      while(true){
         System.out.println("=============================");
         System.out.println("글등록:1 \t 뒤로 : <Q/q>");
         System.out.println("=============================");
         
         int input = 0;
         try{
            input = sc.nextInt();
         }catch(Exception e){
            System.out.println("숫자를 입력해주세요.");
            continue;
         }
         
         switch(input){
            case 0 :
               return;
         case 1 :
        	 hopeRegiView();
        	 break;
         default : 
        	 System.out.println("다시 입력해주세요");
         }
      }
   }

   /**
    * 희망도서등록메서드
    * @author 조애슬
    */
   private void hopeRegiView(){
      showBanner("희망도서 등록");
      System.out.println("도서제목을 입력해주세요");
      String hope_title = sc.next();
      System.out.println("저자를 입력해주세요");
      String hope_author = sc.next();
      System.out.println("출판사를 입력해주세요");
      String hope_publisher = sc.next();
      showBanner("희망도서등록 성공");
      System.out.println("메인메뉴로 돌아갑니다.");
//      mainView();
   }

////////////////////////////////////////////마이페이지//////////////////////////////////////////////////
	/**
	 * 마이페이지
	 * @author 민태원
	 * @since 2020.11.04
	 */
	private void myPageView() {
		while(true) {
			System.out.println("1. 정보수정");
			System.out.println("2. 대출도서목록");
			System.out.println("3. 예약도서목록");
			System.out.println("<뒤로(Q/q)>");
			
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못된 입력입니다.");
				continue;
			}
			
			switch(input) {
			case 1:
				updateInfoView();	//정보수정
//					if(!nowMember.isActivate())	//탈퇴했으면
//						return;
				break;
			case 2:
				rentalListView();	//대출목록
				break;
			case 3:
				rsvLsitView();	//예약목록
				break;
			case 0:
				return;		//뒤로가기
			default : 
	        	 System.out.println("다시 입력해주세요");
			}
		}
	}
	
	
	/**
	 * 내 정보 수정 페이지
	 * @author 민태원
	 * @since 2020.11.04
	 */
	private void updateInfoView() {
		while(true) {
			//내 정보 출력 메서드
			//void printProfile(MemberVO mv);
			System.out.println("바꿀 정보 선택");
			System.out.println("비밀번호(1) 이메일(2) 전화번호(3) 탈퇴하기(9) 뒤로가기(0)");
			
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못된 입력입니다.");
				continue;
			}
			

			switch(input) {
			case 1:
				//비밀번호
				//String updatePW();
				break;
			case 2:
				//이메일
				//String updateEmail();
				break;
			case 3:
				//전화번호
				//String updatePhone();
				break;
			case 9:
				//탈퇴 메서드
				//void withdrawal(MemberVO mv);
				return;
			case 0:
				return;		//뒤로가기
			default : 
	        	 System.out.println("다시 입력해주세요");
	        	 continue;
			}
			
			//회원정보 갱신
			//database
			//void updateMember(MemberVO mv);
			
		}
	}
	
	
	
	/**
	 * 대출목록
	 * @author 민태원
	 * @since 2020.11.04
	 */
	private void rentalListView() {
		while(true) {
			//대출도서리스트 출력 메서드
			//void printRentalList(String mem_id);
			System.out.println("<뒤로(0)>");
			System.out.println("반납할 도서를 선택해 주세요.");
			
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못된 입력입니다.");
				continue;
			}
			
			//입력한 번호가 유효한 번호인지 체크
			//도서번호입력받아 반납 메서드
			//void returnBook(String book_id);
			switch(input) {
			case 0:
				return;		//뒤로가기
			default: 
	        	 System.out.println("다시 입력해주세요");
			}
		}
	}
	
	
	/**
	 * 예약목록
	 * @author 민태원
	 * @since 2020.11.04
	 */
	private void rsvLsitView() {
		while(true) {
			//예약 도서 목록 출력
			//void printBookList(String mem_id);
			System.out.println("<뒤로(0)>");
			
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못된 입력입니다.");
				continue;
			}
			
			//입력한 번호가 유효한 번호인지 체크
			//도서번호입력받아 예약취소 메서드
			//void rsvCancel(String book_id);
			switch(input) {
			case 0:
				return;		//뒤로가기
			default : 
				System.out.println("다시 입력해주세요");
			}
		}
	}
	
	
	

/////////////////////////////////////////////관리자 페이지//////////////////////////////////////////////////
	/**
	 * 관리자 로그인화면
	 */
	public void startAdminView() {
		while (true) {
			System.out.println("\t관\t리\t자");
			System.out.println("======================");
			System.out.println("[1]\t 게시판 관리");
			System.out.println("[2]\t 책 등록");
			System.out.println("[3]\t 회원리스트");
			System.out.println("[0]\t 로그아웃");
			System.out.println("======================");
			System.out.print("번호를 입력하시오 : ");
			
			int num = 0;
			try {
				num = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못입력하였습니다");
				continue;
			}
			
			switch (num) {
			case 0: {
				startMethod();// 로그인화면
				break;
			}
			case 1: {
//					adminMethod();// 게시판 관리 메소드
				return;
			}
			case 2: {
//					bookMethod();// 책 등록 메소드
				return;
			}
			case 3: {
//					memberMethod();// 회원 리스트 메소드
				return;
			}
			default:
				System.out.println("잘못입력하였습니다");
			}
		}
	}

	/**
	 * 게시판 관리창 출력
	 */
	public void adminMethod() {
		while (true) {
			System.out.println("[1]\t 공지관리입니까?");
			System.out.println("[2]\t 희망도서관리입니까?");
			System.out.println("[0]\t 관리자 화면");
			System.out.print("번호를 입력하시오 : ");
			
			int num=0;
			try {
				num = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못입력하였습니다");
				continue;
			}
			
			switch (num) {
			case 0: {
				startAdminView();// 관리자 화면
				break;
			}
			case 1: {
				noiceMethod();// 공지관리
				return;
			}
			case 2: {
				hopeBookMethod();// 희망도서관리
				return;
			}
			default:
				System.out.println("잘못입력하였습니다");
				return;
			}
		}
	}

	/**
	 * 공지관리창 출력
	 */
	public void noiceMethod() {
		while (true) {
//			noiceListMethod();// 게시판 리스트 출력
			System.out.println("[1]\t 공지를 추가하시겠습니까?");// 0번은 고정설정
			System.out.println("[2]\t 공지를 삭제하시겠습니까?");// 임의로 1번으로 지정 나중에 리스트 번호에 맞게 출력
			System.out.println("[0]\t 관리자 화면");
			System.out.print("번호를 입력하시오 : ");
			
			int num=0;
			try {
				num = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못입력하였습니다");
				continue;
			}
			
			switch (num) {
			case 0: {
				startAdminView();// 관리자 화면
				break;
			}
			case 1: {
//				noiceAddMethod();// 공지 추가
				return;
			}
			case 2: {
//				noiceDeltleMethod();// 공지 삭제
				return;
			}
			default:
				System.out.println("잘못입력하였습니다");
				return;
			}
		}
	}

	/**
	 * 희망도서 관리창 출력
	 */
	public void hopeBookMethod() {
		while (true) {
//			noiceListMethod();// 희망도서 리스트 출력
			System.out.println("[1]\t 승인 하시겠습니까?");// 0번은 고정설정
			System.out.println("[2]\t 부결 하시겠습니까?");// 임의로 1번으로 지정 나중에 리스트 번호에 맞게 출력
			System.out.println("[0]\t 관리자 화면");
			System.out.print("번호를 입력하시오 : ");
			
			int num=0;
			try {
				num = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못입력하였습니다");
				continue;
			}
			
			switch (num) {
			case 0: {
				startAdminView();// 관리자 화면
				break;
			}
			case 1: {
//				hopeBookAddMethod();// 희망도서 추가
				return;
			}
			case 2: {
//				hopeBookeDeltleMethod();// 희망도서 삭제
				return;
			}
			default:
				System.out.println("잘못입력하였습니다");
				return;
			}
		}
	}
	
	
	/**
	 * 도서관리 출력
	 */
	public void bookView() { // 서창
	  while (true) {
	     System.out.println("[1]\t 도서리스트 확인");// 0번은 고정설정
	     System.out.println("[2]\t 등록");// 임의로 1번으로 지정 나중에 리스트 번호에 맞게 출력
	     System.out.println("[Q,q]\t 관리자 화면");
	     System.out.print("번호를 입력하시오 : ");

	     int input=0;
		 try {
			 input = sc.nextInt();
    	 } catch (Exception e) {
			 System.out.println("잘못입력하였습니다");
			 continue;
		 }
	     
	     switch (input) {
	     case 1:
	        bookListMethod();// 도서리스트 출력
	        return;
	     case 2:
	        bookMethod();// 도서 등록
	        return;
	     case 0:
	    	 startAdminView();// 관리자 로그인 화면
	    	 break;
	     default:
	        System.out.println("잘못입력하였습니다");
	        return;
	     }
	  }
	}

	/**
	 * 책리스트 출력  
	 */
	public void bookListMethod() { 
		System.out.println("리스트 출력");
    }
	/**
	 * 책 등록
	 */
	public void bookMethod() { 
		System.out.println("책이 등록되었습니다");
    }

	
// 회원리스트
	/**
	 * 회원관리창 출력
	 */
	public void memberMethod() { 
		while (true) {
			System.out.println("[1]\t 회원리스트 확인");// 0번은 고정설정
			System.out.println("[2]\t 블랙리스트 확인");// 임의로 1번으로 지정 나중에 리스트 번호에 맞게 출력
			System.out.println("[0]\t 관리자 화면");
			System.out.print("번호를 입력하시오 : ");
			
			int input=0;
			try {
				 input = sc.nextInt();
	    	} catch (Exception e) {
				 System.out.println("잘못입력하였습니다");
				 continue;
			}
			 
			switch (input) {
			case 0:
				startAdminView();// 관리자 화면
				break;
			case 1:
				memberListMethod();// 회원리스트 출력
				return;
			case 2:
				blackListMethod();// 블랙리스트관리
				return;
			default:
				System.out.println("잘못입력하였습니다");
				return;
			}
		}

	}

	/**
	 * 회원리스트 출력
	 * @author pc19
	 */
	public void memberListMethod() { 
		// 리스트출력
		System.out.println("[1]\t 회원관리창 ");
		System.out.println("[2]\t 블랙리스트 등록");
		System.out.println("[0]\t 관리자 화면");
		System.out.print("번호를 입력하시오 : ");
		int input=0;
		try {
			 input = sc.nextInt();
    	} catch (Exception e) {
			 System.out.println("잘못입력하였습니다");
			 return;
		}
		
		switch (input) {
		case 0: {
			startAdminView();// 관리자 화면
			break;
		}
		case 1: {
			memberMethod();// 회원관리창
			break;
		}
		case 2: {
//			blackAddMethod();// 블랙리스트 등록
			return;
		}
		default:
			System.out.println("잘못입력하였습니다");
			return;
		}
	}

	/**
	 * 블랙리스트 출력
	 */
	public void blackListMethod() {
		while (true) {

			// 블랙리스트 출력
			System.out.print("삭제할 블랙리스트의 번호를 입력하시오( 0번은 관리자화면 ) : ");
			int num = sc.nextInt();
			if (num == 0) {
				startAdminView();
				break;
			} else
//				blackDeltleMethod(num);// 블랙리스트(삭제)
			return;
		}
	}
}