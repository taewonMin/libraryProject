package package_Main;

import java.util.*;
import java.util.regex.Pattern;

import package_VO.*;

public class View {
	private MemberVO nowMember = null;
	IServiceImpl is = new IServiceImpl();
	
	Scanner sc = new Scanner(System.in);
	
	/**
	 * 시작 메서드
	 * @author 민태원
	 * @since 2020.11.05
	 */
	void startMethod() {
		//로그인,회원가입
		while(true) {
			System.out.println("[1]로그인");
			System.out.println("[2]회원가입");
			System.out.println("[3]종료");
			
			int input = 0;
			try {
				input = sc.nextInt();
			}catch(Exception e) {
				System.out.println("숫자만 입력해주세요.");
				continue;
			}
			
			switch(input) {
			case 1:
//				loginSessionView();
				homeView();
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
    * @author 민태원
    * @since 2020.11.04
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
    * @author 민태원
    * @since 2020.11.04
    */
   private void endProgram(){
      showBanner("\n\t프로그램을 종료합니다. 이용해 주셔서 감사합니다.\n");
      System.exit(0);
   }
	
   
   
   /**
    * 로그인 메서드 - 아이디와 비밀번호값을 받아 database와 비교 후 true이면 homeView로
    * @author 조애슬
    * @since 2020-11-04
    */
   private void loginSessionView(){ 
      Map<String, String> login = new HashMap<>();//id 와 pw를 묶을 Map 생성
      
//      String mem_id = is.scanID(); //아이디입력, 올바른 값인지 검사 후 반환
//      String mem_pw = is.scanPW(); //비밀번호입력,  올바른 값인지 검사 후 반환
//      login.put(mem_id, mem_pw); //입력받은 id,pw Map에 넣음
//      if(is.loginMatch(login)){ //id와 pw값을 db로 넘겨서 회원확인
//    	  homeView();
//      }else{
//    	  System.out.println("아이디나 비밀번호가 틀렸습니다.");
//      }
      
  }
	
   /**
    * 회원가입 메서드(ex1) ->MemberVO
    * @author 조애슬
    * @param 스캐너로 MemberVO정보 입력
    * @return boolean
    * @since 2020-11-05
    */
   private void joinMemberView(){
 	  showBanner("회원가입");
 	  MemberVO params = new MemberVO(); //묶어서 저장할 MemberVO params 생성
 	  
 	  
// 	  String mem_id = is.scanID(); //아이디, 패스워드등을 입력받는 메서드들을 통해 변수에넣음
// 	  String mem_pw = is.scanPW();
// 	  String mem_name = is.scanNM();
// 	  String mem_bir = is.scanBIR();
// 	  String mem_tel = is.scanTel();
// 	  String mem_email = is.scanEM();
 	  String mem_id = "check";
 	  String mem_pw = "asdf";
 	  String mem_name = "네임";
 	  String mem_bir = "111.11.11";
 	  String mem_tel = "010-2020-5215";
 	  String mem_email = "awet@awet.ewa";
 	  
 	  //boolean idUnique = is.idUniqCheck(mem_id); //아이디 중복체크
 	 // if(idUnique==false){//중복아니면
	  params.setMem_id(mem_id);//memberVO멤버에 다 값을 넣고
	  params.setMem_pw(mem_pw);
	  params.setMem_name(mem_name);
	  params.setMem_bir(mem_bir);
	  params.setMem_tel(mem_tel);
	  params.setMem_email(mem_email);
	  
	  if(is.createMember(params)){;
	  	System.out.println("회원가입성공");//입력한 한회원의모든정보(MemberVO)DB로넘겨줌
	  	System.out.println();
	  }else{//중복이면
		  System.out.println("회원가입 실패");
	  }	  
   }

   /**아이디입력
    * @author 조애슬
    * @return String mem_id
    */
   String scanID(){ return null;};//아이디 입력받음
   
   
   /**패스워드입력
    * @author 조애슬
    * @return String mem_pw
    */
   String scanPW(){return null;};//패스워드 입력받음
   
   
   /**이름입력
    * @author 조애슬
    * @return String mem_name
    */
   String scanNM(){return null;};//이름 입력받음
   
   /**이메일입력
    * @author 조애슬
    * @return String mem_email
    */
   String scanEM(){return null;};//이메일 입력받음
   
   /**전화번호입력
    * @author 조애슬
    * @return String mem_tel
    */
   String scanTel(){return null;};//전화번호 입력받음
   
   /**생일입력
    * @author 조애슬
    * @return String mem_bir
    */
   String scanBIR(){return null;};//생일 입력받음
	
	/**
	 * 메인화면 - 검색, 게시판, 마이페이지, 반납
	 * @author 민태원
	 * @since 2020.11.04
	 */
	private void homeView() {
		while(true) {
			System.out.println("[1]도서검색");
		    System.out.println("[2]게시판");
		    System.out.println("[3]마이페이지");
		    System.out.println("[4]반납");
		    System.out.println("[0]로그아웃");
		    
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
	 * @author 송지은
	 * @도서명 or 장르 검색 창으로 이동
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
	 * 도서명으로 검색
	 * @author 송지은
	 * @return input(검색명)
	 */
   String bookNameSearchView() {
      String input = "";
      showBanner("도서명 검색");
      System.out.println("<뒤로(Q/q)>");
      System.out.println("도서명: ");
      
      input = sc.next();
      //List<BookVO> bookList();
      return input;
   }
   
   /**
	 * 도서명 검색 후 목록
	 * @author 송지은
	 * BookVO List형 출력
	 */
	void bookNameCheckView(){
		// 도서 검색 후 목록에서 [1]~[?] 입력시 도서 상세 보기 페이지
		showBanner("도서 검색 리스트");
		int input = 0;
		try {
			input = sc.nextInt();
		} catch (Exception e) {
			System.out.println("다시 입력해주세요");
			return;
		}
		
		switch (input) {
		case 1:
			
			break;

		default:
			break;
		}
	}
   
	/**
	 * 장르별 검색
	 * @author 송지은 
	 * @return book_theme List형변환
	 */
	void genreSearchView() {
		showBanner("장르별 검색");
	
		int input = 0;
		// sql
		List<BookLGUVO> bookLGUList = null;
//		bookLGUList = is.bookLGUList();

		// 카테고리 리스트 출력
		for (int i = 0; i < bookLGUList.size(); i++) {
			System.out.println("\t\t\t[" + i + "] "
					+ bookLGUList.get(i).getBook_theme());
		}
		
		System.out.println("<뒤로(Q/q)>");
		System.out.println("───────────────────────────────────────────────────────");
	}
	
	/**
	 * 장르별 책 목록
	 * @author 송지은 
	 * @return book_theme List형변환
	 */
	void genreSearchListView(){
		//선택한 장르에 맞는 리스트 목록 출력
		showBanner("장르별 책 목록");
		System.out.println("<뒤로(Q/q)>");
	}
	
	/**
	 * 도서 상세 페이지
	 * @author 송지은
	 * 전체 BookVO List형 출력
	 */
	void bookDetaView(){
		showBanner("도서 상세 페이지");
		System.out.println("<뒤로(Q/q)>");
	}
	
	
	
	
	
/////////////////////////////게시판//////////////////////////////////   
   /**
    * 게시판메서드
    * @author 조애슬
    * @since 2020-11-04
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
    * @since 2020-11-05
    */
   private void noticeView(){
	   	showBanner("공지사항");

//	   	if(is.noticeList()){ // 공지사항 출력
	   	//출력성공
//	   	}
      
	   	while(true){
	   		System.out.println("=============================");
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
			case 1 : case 2: case 3: case 4: case 5:
//				is.openNoDetail(input); 	//공지사항의 글번호를 누르면 그값을 가지고 공지상세보기 메서드로
			case 0 :
				return;
			default : 
				System.out.println("다시 입력해주세요");
				break;
			}
      }
   }
   
   /**
    * 공지사항 상세페이지
    * @author 조애슬
    * @since 2020-11-05
    */
   private void noticeDetailView(){}

   /**
    * 희망도서목록 메서드
    * @author 조애슬
    * @since 2020.11.05
    */
   private void hopeView(){
     
//	  if(is.hopeList()){//도서출력성공} // 희망도서목록출력
     
	      while(true){
	         System.out.println("=============================");
	         System.out.println("글등록:1 \t 뒤로 : <Q/q>");
	         System.out.println("=============================");
	         int input = 0;
	         try{
	           input = sc.nextInt();
	         }
	         catch(Exception e){
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
//   }
   
   /**
    * 희망도서등록메서드
    * @author 조애슬
    * @since 2020.11.05
    */
   private void hopeRegiView(){
      
	  showBanner("희망도서 등록");
//      if(is.hopeListAdd()){
    	  //등록성공
//      }//희망도서등록메서드
      
      System.out.println("메인메뉴로 돌아갑니다.");
      homeView();
   }


////////////////////////////////////////////마이페이지//////////////////////////////////////////////////
	/**
	 * 마이페이지
	 * @author 민태원
	 * @since 2020.11.04
	 */
	private void myPageView() {
		while(true) {
			System.out.println("[1]정보수정");
			System.out.println("[2]대출도서목록");
			System.out.println("[3]예약도서목록");
			System.out.println("[0]뒤로");
			
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
			MemberVO mv = is.printProfile(nowMember.getMem_id());
			System.out.println("성명 : "+mv.getMem_name());
			System.out.println("ID : "+mv.getMem_id());
			System.out.println("생년월일 : "+mv.getMem_bir());
			System.out.println("전화번호 : "+mv.getMem_tel());
			System.out.println("이메일 : "+mv.getMem_email());
			System.out.println("바꿀 정보 선택");
			System.out.println("비밀번호(1) 전화번호(2) 이메일(3) 탈퇴하기(9) 뒤로가기(0)");
			
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못된 입력입니다.");
				continue;
			}
			Map<String, String> myInfo = new HashMap<>();
			switch(input) {
			case 1:
				//비밀번호
				myInfo.put("mem_id", updatePW());
				break;
			case 2:
				//이메일
				myInfo.put("mem_email", updateEmail());
				break;
			case 3:
				//전화번호
				myInfo.put("mem_tel", updatePhone());
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
		}
	}
	
	/**
	 * 비밀번호 변경 메서드 
	 * 출력 : "새로운 비밀번호를 입력하세요"
	 * @return 비밀번호 확인되면 새로운 비밀번호 반환, 아니면 기존의 비밀번호 반환
	 * @author 민태원
	 * @since 2020.11.05
	 */
	private String updatePW(){
		System.out.print("새로운 비밀번호를 입력하세요 : ");
		String pw1 = sc.next();
		System.out.print("한번더 입력하세요 : ");
		String pw2 = sc.next();
		if(pw1.equals(pw2)){
			return pw1;
		}
		System.out.println("비밀번호가 다릅니다.");
		return nowMember.getMem_pw();
	}
	
	/**
	 * 이메일 변경 메서드
	 * 출력 : "새로운 이메일을 입력하세요"
	 * @return 형식에 맞는 이메일이면 이메일이 저장된 문자열 반환, 아니면 기존 이메일 반환
	 * @author 민태원
	 * @since 2020.11.05
	 */
	private String updateEmail(){
		String email = sc.next();
		String checkEmail = "^[a-zA-Z](\\w|-|_|\\\\|\\.)*@\\w{1,7}\\.[a-zA-Z]{2,3}(\\.kr)?";
		if(Pattern.matches(checkEmail, email)){
			return email;
		}
		return nowMember.getMem_email();
	}
	
	/**
	 * 전화번호 변경 메서드
	 * 출력 : "새로운 전화번호를 입력하세요"
	 * @return 형식에 맞는 전화번호이면 전화번호가 저장된 문자열 반환, 아니면 기존의 전화번호 반환
	 * @author 민태원
	 * @since 2020.11.05
	 */
	private String updatePhone(){
		String tel = sc.next();
		String checkTel = "01[016-9]-[1-9]\\d{3}-\\d{4}";
		if(Pattern.matches(checkTel, tel)){
			return tel;
		}
		return nowMember.getMem_tel();
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
	
	/**
	 * 예약 취소 메서드 
	 * 출력: "취소하시겠습니까? (y/n)" => y면 예약취소 / n면 return
	 * @param book_id 예약된 도서의 아이디
	 * @author 민태원
	 * @since 2020.11.04
	 */
	void rsvCancel(String book_id){
		
	}
	

/////////////////////////////////////////////관리자 페이지//////////////////////////////////////////////////
	/**
	 * 관리자메인 메서드
	 * 
	 * @author 김태규
	 * @since 2020.11.04
	 */
	public void adminView() {
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
				/**
				 * 로그아웃하면 로그인화면으로 돌아가기위한 메소드
				 */
				startMethod();
				break;
			}
			case 1: {
				/**
				 * 게시관 관리view을 보여주는 메소드
				 */
				noiceMainView();
				return;
			}
			case 2: {
				/**
				 * 책 관리 view을 보여주는 메소드
				 */
				bookView();
				return;
			}
			case 3: {
				/**
				 * 회원관리view을 보여주는 메소드
				 */
				memberView();
				return;

			}
			default:
				System.out.println("잘못입력하였습니다");
			}
		}
	}

	/**
	 * 게시관 관리 메서드
	 * 
	 * @author 김태규
	 * @since 2020.11.04
	 */
	public void noiceMainView() {
		while (true) {
			System.out.println("[1]\t 공지사항");
			System.out.println("[2]\t 희망도서목록");
			System.out.println("[0]\t 종료");
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
				/**
				 * 관리자view을 보여주는 메소드
				 */
				adminView();
				break;
			}
			case 1: {
				/**
				 * 공지사항view을 보여주는 메소드
				 */
				noiceView();
				return;
			}
			case 2: {
				/**
				 * 희망도서목록view을 보여주는 메소드
				 */
				hopeBookView();
				return;
			}
			default:
				System.out.println("잘못입력하였습니다");
				return;
			}
		}
	}

	/**
	 * 공지사항 관리
	 * 
	 * @author 김태규
	 * @since 2020.11.04
	 */
	public void noiceView() {
		while (true) {
			/**
			 * 공지리스트 띄워주는 메소드
			 */
			noiceMainView();

			System.out.println("[1]\t 공지 조회");
			System.out.println("[2]\t 공지 추가");
			System.out.println("[3]\t 공지 삭제");
			System.out.println("[0]\t 종료");
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
				/**
				 * 게시판view을 보여주는 메소드
				 */
				noiceMainView();
				break;
			}
			case 1: {
				/**
				 * 공지읽은 리스트의 번호를 받아서 출력하는 메소드
				 */
				System.out.print("읽을 공지의 번호 : ");
				int num2 = 0;
				try {
					num2 = sc.nextInt();
				} catch (Exception e) {
					System.out.println("잘못입력하였습니다");
					continue;
				}
//				noiceReadMethod(num2);
				return;
			}
			case 2: {
				/**
				 * 공지추가 메소드
				 */
//				noiceAddMethod();
				return;
			}
			case 3: {
				/**
				 * 공지삭제 메소드 삭제할 공지의 번호를 받아서 공지를 판단
				 */
				System.out.print("삭제할 공지의 번호 : ");
				int num3 = 0;
				try {
					num3 = sc.nextInt();
				} catch (Exception e) {
					System.out.println("잘못입력하였습니다");
					continue;
				}
//				noiceDeltleMethod(num3);
				return;
			}
			default:
				System.out.println("잘못입력하였습니다");
				return;

			}
		}
	}

	/**
	 * 희망 도서 목록 관리 뷰
	 * 
	 * @author 김태규
	 * @since 2020.11.04
	 */
	public void hopeBookView() {
		while (true) {
			/**
			 * 희망 도서 메소드 번호,책이름,저자,출판사 등을 보여준다
			 */
			noiceMainView();

			System.out.println("[1]\t 희망도서 승인 ");
			System.out.println("[2]\t 희망도서 부결 ");
			System.out.println("[0]\t 관리자 화면");
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
				/**
				 * 게시판 관리로 되돌아 가기 메소드
				 */
				noiceMainView();
				break;
			}
			case 1: {
				/**
				 * 도서 승인 메소드 승인 도서의 번호 번호를 받아 메소드안에서 추가한다
				 */
				System.out.print("승인 도서의 번호 : ");
				int num2 = 0;
				try {
					num2 = sc.nextInt();
				} catch (Exception e) {
					System.out.println("잘못입력하였습니다");
					continue;
				}
//				hopeBookAddMethod(num2);
				return;
			}
			case 2: {
				/**
				 * 도서 부결 메소드 부결 도서의 번호를 받아서 메소드안에서 리스트를삭제
				 */
				System.out.print("부결 도서의 번호 : ");
				int num2 = 0;
				try {
					num2 = sc.nextInt();
				} catch (Exception e) {
					System.out.println("잘못입력하였습니다");
					continue;
				}
//				hopeBookeDeltleMethod(num2);
				return;
			}
			default:
				System.out.println("잘못입력하였습니다");
				return;
			}
		}
	}
	
	/**
	 * 도서 관리 뷰
	 * 
	 * @author 김태규
	 * @since 2020.11.04
	 */
	public void bookView() {
		while (true) {
			System.out.println("[1]\t 전체 도서리스트 확인");
			System.out.println("[2]\t 등록");
			System.out.println("[0]\t 관리자 화면");
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
				/**
				 * 관리자 메인 화면 메소드 관리자 메인 화면 메소드 돌아가기
				 */
				adminView();
				break;
			}
			case 1: {
				/**
				 * 전체 도서리스트 출력 메소드
				 */
				bookListMethod();
				return;
			}
			case 2: {
				/**
				 * 도서 등록 메소드
				 */
//				bookAddMethod();
				System.out.println("책이 등록되었습니다");
				return;
			}
			default:
				System.out.println("잘못입력하였습니다");
				return;
			}
		}
	}

	/**
	 * 책리스트 출력  
	 * @author 김태규
	 * @since 2020.11.05
	 */
	public void bookListMethod() { 
		System.out.println("리스트 출력");
    }
	/**
	 * 책 등록
	 * @author 김태규
	 * @since 2020.11.05
	 */
	public void bookMethod() { 
		System.out.println("책이 등록되었습니다");
    }
	
// 회원리스트
	/**
	 * 회원 관리 뷰
	 * @author 김태규
	 * @since 2020.11.04
	 */
	public void memberView() {
		while (true) {
			System.out.println("[1]\t 회원리스트 확인");
			System.out.println("[2]\t 블랙리스트 확인");
			System.out.println("[0]\t 관리자 화면");
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
				/**
				 * 관리자 메인 화면 메소드 관리자 메인 화면 메소드 돌아가기
				 */
				adminView();
				break;
			}
			case 1: {
				/**
				 * 전체 회원리스트 뷰 메소드
				 */
				memberListView();
				return;
			}
			case 2: {
				/**
				 * 전체 블랙리스트 뷰 메소드
				 */
				blackListView();
				return;
			}
			default:
				System.out.println("잘못입력하였습니다");
				return;
			}
		}

	}

	/**
	 * 회원 리스트 뷰
	 * @author 김태규
	 * @since 2020.11.04
	 */
	public void memberListView() {
		/**
		 * 전체 회원리스트 출력 메소드
		 */
//		memberListMethod();

		System.out.println("[1]\t 블랙리스트 등록 ");
		System.out.println("[0]\t 돌아가기");
		System.out.print("번호를 입력하시오 : ");
		int num = 0;
		try {
			num = sc.nextInt();
		} catch (Exception e) {
			System.out.println("잘못입력하였습니다");
			return;
		}
		switch (num) {
		case 0: {
			/**
			 * 회원 관리 화면 돌아가기
			 */
			memberView();
			break;
		}
		case 1: {
			/**
			 * 블랙리스트에 추가 메소드
			 */

			System.out.print("블랙 리스트에 추가될 회원 번호 : ");
			int num2 = 0;
			try {
				num2 = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못입력하였습니다");
				return;
			}
//			blackAddMethod(num2);
			return;
		}
		default:
			System.out.println("잘못입력하였습니다");
			return;
		}
	}

	/**
	 * 블랙 리스트 뷰
	 * @author 김태규
	 * @since 2020.11.04
	 */
	public void blackListView() {
		while (true) {

			/**
			 * 블랙리스트 리스트 출력 메소드
			 */
//			blackListReadView();

			System.out.println("[1]\t 블랙리스트 등록 ");
			System.out.println("[0]\t 돌아가기");
			int num = 0;
			try {
				num = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못입력하였습니다");
				continue;
			}
			switch (num) {
			case 0: {
				/**
				 * 회원 관리 화면 돌아가기
				 */
				memberView();
				break;
			}
			case 1: {
				/**
				 * 블랙리스트에 추가 메소드
				 */
				System.out.print("삭제할 블랙리스트의 번호 : ");
				int num2 = 0;
				try {
					num2 = sc.nextInt();
				} catch (Exception e) {
					System.out.println("잘못입력하였습니다");
					continue;
				}
//				blackDeltleMethod(num2);// 블랙리스트(삭제)
				return;
			}

			default:
				System.out.println("잘못입력하였습니다");
				return;
			}
		}
	}
}