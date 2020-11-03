package package_Main;

import java.util.*;
import package_VO.*;

public class View {
	private MemberVO nowMember = null;
	
	void startMethod() {
		//로그인,회원가입
		
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("1. 로그인");
			System.out.println("2. 회원가입");
			try {
				String n = sc.next();
				switch(n) {
				case "1":
					loginSessionView();
					break;
				case "2":
					joinMemberView();
					break;
				default:
					System.out.println("다시 입력해주세요.");
				}
			}catch(Exception e) {
				System.out.println("숫자만 입력해주세요.");
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
      Scanner in = new Scanner(System.in);
      System.out.println("아이디를 입력하세요");
      String mem_id = in.next();
      System.out.println("패스워드를 입력하세요");
      String mem_pw = in.next();
      showBanner("로그인 성공");
      homeView();
  }

	
	/**
    * 회원가입 메서드(ex1) ->MemberVO
    * @author 조애슬
    * @param 스캐너로 MemberVO정보 입력
    * @return boolean
    */
   private void joinMemberView(){
      showBanner("회원가입");
      
      Scanner in = new Scanner(System.in);
      System.out.println("아이디를 입력하세요");
      String mem_id = in.next();
      System.out.println("패스워드를 입력하세요");
      String mem_pw = in.next();
      System.out.println("이름을 입력하세요");
      String mem_name = in.next();
      System.out.println("생년월일을 입력하세요");
      String mem_bir = in.next();
      System.out.println("핸드폰번호를 입력하세요");
      String mem_tel = in.next();
      System.out.println("이메일을 입력하세요");
      String mem_email = in.next();
      showBanner("회원가입 성공");
//      startMethod();
  }

	
	/**
	 * 메인화면 - 검색, 게시판, 마이페이지, 반납
	 */
	private void homeView() {
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.println("[1]도서검색");
		    System.out.println("[2]게시판");
		    System.out.println("[3]마이페이지");
		    System.out.println("[4]반납");
		    System.out.println("<로그아웃(Q/q)>");

			try {
				String input = sc.next().toLowerCase();
				switch(input) {
				case "1":
					bookSearch();	//도서검색
					break;
				case "2":
					boardView();	//게시판
					break;
				case "3":
					myPage();	//마이페이지
					if(!nowMember.isActivate())	// 탈퇴했으면
						return;
					break;
				case "4":
					rentalList();	//반납 - 대출목록
					break;
				case "q":
					return;
				default:
					System.out.println("다시 입력해주세요");
				}
			} catch (Exception e) {
				System.out.println("다시 입력해주세요");
			}
		}
	}
	
	/**
	 * 도서검색
	 */
   void bookSearch() {
      Scanner scn = new Scanner(System.in);
      
      showBanner("도서검색");
      
      while (true) {
    	 System.out.println("[1]도서명 검색");
    	 System.out.println("[2]장르 검색");
    	 System.out.println("<뒤로(Q/q)>");
         try {
        	String input = scn.next().toLowerCase();
            switch (input) {
            case "1":
               bookNameSearch();
               break;
            case "2":
               genreSearch();
               break;
            case "q":
                return;
           default:
        	   System.out.println("다시 입력해주세요");
            }
         } catch (Exception e) {
        	 System.out.println("다시 입력해주세요");
         }
      }
   }
   
   /**
    *  도서 이름으로 검색
    */
   void bookNameSearch() {
      Scanner scn = new Scanner(System.in);
      String input = "";
      showBanner("도서명 검색");
      System.out.println("<뒤로(Q/q)>");
      System.out.println("도서명: ");

      while (!input.toLowerCase().equals("q")) {
         try {
        	 input = scn.next();
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
   void genreSearch() {
      Scanner scn = new Scanner(System.in);
      String input = "";
      showBanner("장르별 검색");
      System.out.println("<뒤로(Q/q)>");   
      System.out.println("장르명: ");
      
      while (!input.toLowerCase().equals("q")) {
         try {
        	 input = scn.next();
             switch (Integer.parseInt(input)) {
             
             }
         } catch (Exception e) {
        	 System.out.println("잘못된 입력입니다.");
             return;
         }
      }
   }
   
   
   /**
    * 게시판메서드
    * @author 조애슬
    */
   private void boardView(){
      Scanner in = new Scanner(System.in);
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
         try{
            String input = in.next().toLowerCase();
            switch(input){
            case "1" :
               noticeView();
               break;
            case "2" :
               hopeView();
               break;
            case "3" :
               endProgram();
               break;
            case "q" :
                return;
            default : 
               System.out.println("다시 입력해주세요");
            }
         }catch(Exception e){
            System.out.println("숫자를 입력해주세요.");
         }
      }
   }

   /**
    * 공지사항 메서드
    * @author 조애슬
    */
   private void noticeView(){
      Scanner in = new Scanner(System.in);
      
      showBanner("공지사항");
      System.out.println("No"+"\t제목"+"\t내용");
      System.out.println("1" + "\t휴관안내"+"\t2020.10.5-2020.10.31 도서관 휴관합니다.");
      System.out.println("2" + "\t행사안내"+"\t2020.11.3 문화축제 소식을 알립니다.");
      System.out.println("3" + "\t유의사항"+"\t도서관에서는 항상 조용히 걸어주세요");
      
      while(true){
         System.out.println("=============================");
         System.out.println("<뒤로(Q/q)>");
         System.out.println("=============================");
         try{
            String input = in.next().toLowerCase();
            switch(input){
            case "q" :
               return;
            default : 
               System.out.println("다시 입력해주세요");
               break;
            }
         }catch(Exception e){
            System.out.println("숫자를 입력해주세요.");
            continue;
         }
      }
   }

   /**
    * 희망도서목록 메서드
    * @author 조애슬
    */
   private void hopeView(){
      Scanner in = new Scanner(System.in);
      
      showBanner("희망도서");
      System.out.println("No"+"\t제목"+"\t\t저자"+"\t출판사");
      System.out.println("1" + "\t비행운"+"\t\t김애란"+"\t나무출판사");
      System.out.println("2" + "\t아몬드"+"\t\t박떙땡"+"\t작은창출판사");
      System.out.println("3" + "\t우울할떈 뇌과학"+"\t엘릭스코드"+"\t한강출판사");
      
      while(true){
         System.out.println("=============================");
         System.out.println("글등록:1 \t 뒤로 : <Q/q>");
         System.out.println("=============================");
         try{
            String input = in.next().toLowerCase();
            switch(input){
            case "q" :
               return;
            case "1" :
               hopeRegiView();
               break;
            default : 
               System.out.println("다시 입력해주세요");
            }
         }catch(Exception e){
            System.out.println("숫자를 입력해주세요.");
         }
      }
   }

   /**
    * 희망도서등록메서드
    * @author 조애슬
    */
   private void hopeRegiView(){
      showBanner("희망도서 등록");
      Scanner input = new Scanner(System.in);
      System.out.println("도서제목을 입력해주세요");
      String hope_title = input.next();
      System.out.println("저자를 입력해주세요");
      String hope_author = input.next();
      System.out.println("출판사를 입력해주세요");
      String hope_publisher = input.next();
      showBanner("희망도서등록 성공");
      System.out.println("메인메뉴로 돌아갑니다.");
//      mainView();
   }

	
	/**
	 * 마이페이지
	 */
	private void myPage() {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("1. 정보수정");
			System.out.println("2. 대출목록");
			System.out.println("3. 예약목록");
			System.out.println("<뒤로(Q/q)>");
			
			try {
				String input = sc.next().toLowerCase();
				switch(input) {
				case "1":
					updateInfo();	//정보수정
					if(!nowMember.isActivate())	//탈퇴했으면
						return;
					break;
				case "2":
					rentalList();	//대출목록
					break;
				case "3":
					rsvLsit();	//예약목록
					break;
				case "q":
					return;		//뒤로가기
				}
			} catch (Exception e) {
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	
	/**
	 * 회원정보 수정 메서드
	 */
	private void updateInfo() {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("1~7. 회원정보들");
			System.out.println("0. 탈퇴");
			System.out.println("<뒤로(Q/q)>");
			
			
			try {
				String input = sc.next().toLowerCase();
				switch(input) {
				case "0":
					withdrawal();	//탈퇴
					return;
				case "q":
					return;		//뒤로가기
				}
			} catch (Exception e) {
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	/**
	 * 회원 탈퇴 메서드
	 */
	private void withdrawal() {
//		nowMember.setActivate(false);
		System.out.println("탈퇴되었습니다.");
	}
	
	
	/**
	 * 대출목록
	 */
	private void rentalList() {
		//대출도서리스트 출력
		
		
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("대출책순번, 정보");
			System.out.println("<뒤로(Q/q)>");
			try {
				String input = sc.next().toLowerCase();
				switch(input) {
				case "0":
					returnBook();	//반납
					break;
				case "q":
					return;		//뒤로가기
				}
			} catch (Exception e) {
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	
	/**
	 * 반납 메서드
	 */
	private void returnBook() {
		System.out.println("반납되었습니다.");
	}
	
	/**
	 * 예약목록
	 */
	private void rsvLsit() {
		//예약 도서 목록 출력
		
		
		
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("예약책순번, 정보");
			System.out.println("<뒤로(Q/q)>");
			try {
				String input = sc.next().toLowerCase();
				switch(input) {
				case "0":
					rsvCancel();	//예약취소
					break;
				case "q":
					return;		//뒤로가기
				}
			} catch (Exception e) {
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	/**
	 * 예약 취소 메서드
	 */
	private void rsvCancel() {
		System.out.println("예약이 취소되었습니다.");
	}
}
