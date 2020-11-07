package package_Main;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.*;

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
			System.out.println("==============<입장>==============");
			System.out.println("[1]로그인");
			System.out.println("[2]회원가입");
			System.out.println("[3]종료");
			
			int input = 0;
			try {
				input = sc.nextInt();
			}catch(Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
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
    * @author 민태원
    * @since 2020.11.04
    */
   private void showBanner(String str) { 
      System.out.println();
      System.out.println("===================================");
      System.out.println("\t JJUGURI LIBRARY");
      System.out.println("───────────────────────────────────");
      System.out.println("\t" + str);
      System.out.println("───────────────────────────────────");
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
    * @since 2020-11-06
    */
   private void loginSessionView(){
      Map<String, String> params = new HashMap<>();//id 와 pw를 묶을 Map 생성
      
      String mem_id = scanID(); //아이디입력, 올바른 값인지 검사 후 반환
      String mem_pw = scanPW(); //비밀번호입력,  올바른 값인지 검사 후 반환
      
      params.put("mem_id", mem_id); //입력받은 id,pw Map에 넣음
      params.put("mem_pw", mem_pw); //입력받은 id,pw Map에 넣음
      
      nowMember = is.loginMatch(params);
      
      if(nowMember==null){ //id와 pw값을 db로 넘겨서 회원확인
    	 // nowMember = 해당회원의 MemberVO가져와야함
    	  showBanner("아이디나 비밀번호가 틀렸습니다.");
    	 
      }else{
    		  if(is.adminMatch(params)){//관리자일때
    		  adminView();
    	  }else{//일반회원로그인
    		  
    		  showBanner(params.get("mem_id")+"님 환영합니다.");
    		  homeView();
    	  }
      }
      
  }
	
   /**
    * 회원가입 메서드(ex1) ->MemberVO
    * @author 조애슬
    * @param 스캐너로 MemberVO정보 입력
    * @return boolean
    * @since 2020-11-06
    */
   private void joinMemberView(){
 	  showBanner("회원가입");
 	  MemberVO params = new MemberVO(); //묶어서 저장할 MemberVO params 생성
 	  
 	  String mem_id = scanID(); //아이디, 패스워드등을 입력받는 메서드들을 통해 변수에넣음
 	  int isUnique = is.idUniqCheck(mem_id); //아이디 중복체크
 	  if(isUnique==0){//중복아니면 나머지 값 입력받고
 	  
	 	  String mem_pw = scanPW();
	 	  String mem_name = scanNM();
	 	  String mem_bir = scanBIR();
	 	  String mem_tel = scanTel();
	 	  String mem_email = scanEM();
 	  
 		  params.setMem_id(mem_id);//memberVO멤버에 다 값을 넣고
 		  params.setMem_pw(mem_pw);
 		  params.setMem_name(mem_name);
 		  params.setMem_bir(mem_bir);
 		  params.setMem_tel(mem_tel);
 		  params.setMem_email(mem_email);
 
 		
 		  	if(is.createMember(params)){ //db에 정상적으로 들어가면
 			  showBanner("성공적으로 가입되었습니다.\n\t\t\t다시 로그인 해 주세요.");}//입력한 한회원의모든정보(MemberVO)DB로넘겨줌
 		  	
 	  	}else{//아이디값이 중복이면
 	  			System.out.println("중복된 아이디입니다.");
 		  return;
 	  	}	  
   }

   /**아이디입력
    * @author 조애슬
    * @return String mem_id
    * @since 2020.11.06
    */
   String scanID(){ 
	   	System.out.println("아이디를 입력하세요");
		while(true){
	         try {
	        	String mem_id=sc.next();//이걸 여기에넣어야됨
	            if(RegEx.checkMem_id(mem_id)){
	            return mem_id;
	            }
	            else{
	            System.out.println("5~15자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.");
	            continue;
	            }
	            
	         } catch (Exception e) {
	            System.out.println("5~15자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.");
	            continue;
	         }   
	      }
   }//아이디 입력받음
   
   
   /**패스워드입력
    * @author 조애슬
    * @return String mem_pw
    * @since 2020.11.06
    */
   String scanPW(){
	   	System.out.println("비밀번호를 입력하세요");
		
		while(true){
	         try {
	        	String mem_pw=sc.next();//이걸 여기에넣어야됨
	            if(RegEx.checkMem_pw(mem_pw)){
	            return mem_pw;
	            }
	            else{
	            System.out.println("8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
	            continue;
	            }
	            
	         } catch (Exception e) {
	            System.out.println("8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
	            continue;
	         }   
	      }
   }//패스워드 입력받음
   
   
   /**이름입력
    * @author 조애슬
    * @return String mem_name
    * @since 2020.11.06
    */
   String scanNM(){
	   	System.out.println("이름을 입력하세요");
		while(true){
	         try {
	        	String mem_name=sc.next();	
	            if(RegEx.checkMem_name(mem_name)){
	            return mem_name;
	            }
	            else{
	            System.out.println("2~5자 한글만 사용가능합니다.  (특수기호, 공백 사용 불가)");
	            continue;
	            }
	            
	         } catch (Exception e) {
	            System.out.println("2~5자 한글만 사용가능합니다.  (특수기호, 공백 사용 불가)");
	            continue;
	         }   
	      }
   }//이름 입력받음
   
   /**이메일입력
    * @author 조애슬
    * @return String mem_email
    * @since 2020.11.06
    */
   String scanEM(){
	   	System.out.println("이메일을 입력하세요");
		while(true){
	         try {
	        	String mem_email=sc.next();	
	            if(RegEx.checkMem_email(mem_email)){
	            return mem_email;
	            }
	            else{
	            System.out.println("올바른 형식의 이메일이 아닙니다. (영문자로 시작, 예- smile123@naver.com )");
	            continue;
	            }
	            
	         } catch (Exception e) {
	            System.out.println("올바른 형식의 이메일이 아닙니다. (영문자로 시작, 예- smile123@naver.com )");
	            continue;
	         }
		}
   }//이메일 입력받음
   
   /**전화번호입력
    * @author 조애슬
    * @return String mem_tel
    * @since 2020.11.06
    */
   String scanTel(){
	   	System.out.println("전화번호를 입력하세요");
		while(true){
	         try {
	        	String mem_tel=sc.next();
	            if(RegEx.checkMem_tel(mem_tel)){
	            return mem_tel;
	            }
	            else{
	            System.out.println("올바른 형식의 전화번호가 아닙니다. (예-010-1111-1111)");
	            continue;
	            }
	            
	         } catch (Exception e) {
	            System.out.println("올바른 형식의 전화번호가 아닙니다. (예-010-1111-1111)");
	            continue;
	         }
		}
   }//전화번호 입력받음
   
   /**생일입력
    * @author 조애슬
    * @return String mem_bir
    * @since 2020.11.06
    */
   String scanBIR(){
	   	System.out.println("생일을 입력하세요");	
		while(true){
	         try {
	        	String mem_bir=sc.next();
	            if(RegEx.checkMem_bir(mem_bir)){
	            return mem_bir;
	            }
	            else{
	            System.out.println("올바른 형식의 생년월일이 아닙니다. (예-1990-01-01)");
	            continue;
	            }
	            
	         } catch (Exception e) {
	            System.out.println("올바른 형식의 생년월일이 아닙니다. (예-1990-01-01)");
	            continue;
	         }
		}
   }//생일 입력받음
	
	/**
	 * 메인화면 - 검색, 게시판, 마이페이지, 반납
	 * @author 민태원
	 * @since 2020.11.04
	 */
	private void homeView() {
		while(true) {
			System.out.println("==============<HOME>==============");
			System.out.println("[1]도서검색");
		    System.out.println("[2]게시판");
		    System.out.println("[3]마이페이지");
		    System.out.println("[4]반납");
		    System.out.println("[0]로그아웃");
		    
		    int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못된 입력입니다.");
				sc = new Scanner(System.in);
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
				if(!nowMember.isActivate())	// 탈퇴했으면
					return;
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
	 * 도서 검색 메뉴
	 * @author 송지은
	 */
	public void bookSearchView(){
		Scanner in = new Scanner(System.in);
		String input;
		while(true){
			showBanner("도서검색");
			System.out.println("[1]도서명 검색");		
			System.out.println("[2]장르명 검색");
			System.out.println("───────────────────────────────────────────────────────");
			
			try{
				input = in.next();
				switch(Integer.parseInt(input)){
				case 1 :
					bookSearch(); //도서명 검색
					break;
				case 2 :
					 genreSearchView(); // 장르 검색
					break;
				default : 
					System.out.println("다시 입력해주세요");
					break;
				}
			}catch(Exception e){
				System.out.println("숫자를 입력해주세요.");
				continue;
			}
			return;
		}
		
	}
	
	/**
	 * 도서명 검색 메서드
	 * 검색 조건: 도서명의 한 글자라도 일치하는 것이 있다면 도서 리스트 띄우기
	 * @author 송지은
	 */
	void bookSearch() {
		showBanner("도서명 검색");
		Scanner scn = new Scanner(System.in);
		List<BookVO> bookNameListVer2 = null;
		boolean selectBookResult =true;
		System.out.println("도서명 검색: ");
		String bo_name = scn.next();
		bo_name = bo_name.replace(" ", "");
		
		System.out.println(bo_name);
		while (true) {
				bookNameListVer2 = is.bookNameListVer2(bo_name);
				for (int i = 0; i < bookNameListVer2.size(); i++) {
					showBanner("검색 도서");
					if (bookNameListVer2.get(i).getBook_name().contains(bo_name)) {
						System.out.println("도서 번호: " + bookNameListVer2.get(i).getBook_id());
						System.out.println("도서명: "+ bookNameListVer2.get(i).getBook_name());
						System.out.println("작가: "+bookNameListVer2.get(i).getBook_author());
						System.out.println("출판사: "+ bookNameListVer2.get(i).getBook_publisher());
						
					}
					try{
						selectBookResult = book_detail1(bo_name);
					}catch(InputMismatchException e){
						System.out.println("숫자를 입력해주세요.");
					}catch(NumberFormatException ee){
				
					}
				}while (selectBookResult==true); 
				break;
			}
			
		}
	
	/**
	 * 도서 상세 페이지 메서드
	 * @author 송지은
	 * @param bo_id 도서ID
	 * @return BookVO List형 반환
	 */
	boolean book_detail1(String bo_name) {
		String bookID = is.inputBook2();
		System.out.println(bookID);
		List<BookVO> bookList = is.booklList();
		for(BookVO dbSlr : is.booklList(bookID)) {
			
			if(dbSlr.getBook_id().equals(bookID)) {
				showBanner("도서정보");
				System.out.println("도서번호: "+dbSlr.getBook_id());
				System.out.println("도서명: "+dbSlr.getBook_name());
				System.out.println("작가: "+dbSlr.getBook_author());
				System.out.println("출판사: "+dbSlr.getBook_publisher());
				System.out.println("줄거리: "+dbSlr.getBook_summary());	
				System.out.println("───────────────────────────────────────────────────────");
				break;
			}else {
				System.out.println("도서 상세 페이지가 없습니다.");
				return false;
			}
		}
		return true;
	}
   
	/**
	 * 도서 장르 조회 및 해당 도서 목록 리스트 메서드
	 * @author 송지은
	 * 
	 */
	void genreSearchView() {
		showBanner("장르 선택");
		int bo_id;
		List<BookVO> bookNameListVer1 = null;
		boolean selectBookResult = true;
		bo_id = book_choice();
		do {	
			bookNameListVer1 = is.bookNameListVer1(bo_id);
			if(bookNameListVer1.size()==0) {
				System.out.println("해당 장르의 도서가 없습니다.");
				bo_id=0;
			}else{
				do {
					showBanner("도서 목록");
					for(int i=0; i < bookNameListVer1.size(); i++) {	
						System.out.println("["+bookNameListVer1.get(i).getBook_id()+"]"
								+bookNameListVer1.get(i).getBook_name());
					}
					try{
						selectBookResult = book_detail(bo_id);
					}catch(InputMismatchException e){
						System.out.println("숫자를 입력해주세요.");
					}catch(NumberFormatException ee){
				
					}
				}while (selectBookResult==true); 
			}
		}while(bo_id==0);	
		
	}
	/**
	 * 도서 상세 페이지 메서드
	 * @author 송지은
	 * @param bo_id 도서ID
	 * @return BookVO List형 반환
	 */
	boolean book_detail(int bo_id) {
		String bookID = is.inputBook();
		List<BookLGUVO> bookLGUList = is.bookLGUList();
		String bookName = "";
		
		for(BookVO dbSlr : is.booklList(bookID)) {
			if(dbSlr.getBook_LGU().equals(bookID) && dbSlr.getBook_LGU().equals(String.valueOf(bo_id))) {
				for(BookLGUVO bookVO : bookLGUList) {
					if(dbSlr.getBook_LGU().equals(bookVO.getBook_LGU())) {
						bookName = bookVO.getBook_theme();
					}
				}
				showBanner("도서정보");
				System.out.println("도서번호: "+dbSlr.getBook_id());
				System.out.println("도서명: "+dbSlr.getBook_name());
				System.out.println("작가: "+dbSlr.getBook_author());
				System.out.println("출판사: "+dbSlr.getBook_publisher());
				System.out.println("줄거리: "+dbSlr.getBook_summary());	
				System.out.println();
				break;
		}else {
			System.out.println("선택하신 번호의 도서 상세 페이지가 없습니다.");
			return false;
		}
	}
	return true;
}
	/**
	 * 장르 카테고리 선택 메서드
	 * @author 송지은
	 * @return
	 */
	int book_choice() {
		int result =0;
		try {
			bookcheckView();
			result = is.scanCID();
			return result;
		}catch (Exception e) {
			System.out.println("숫자를 입력하세요.");
			System.out.println();
			genreSearchView();
		}
		return result;
	}
	
	/**
	 * 장르 카테고리 리스트 출력 메서드
	 * @author 송지은
	 */
	void bookcheckView() {
		List<BookLGUVO> lguList = null;
		lguList = is.bookLGUList();
		
		String num="";
		for(int i=0; i<lguList.size(); i++) {
			System.out.println("["+lguList.get(i).getBook_LGU()+"]"+lguList.get(i).getBook_theme());
		}
	}
	
/////////////////////////////검색된 도서 대여/예약하기/////////////////////////
	//bookVO의 state가 true이면 바로 대여
	//false이면 예약
	//대여 또는 예약에 성공하면 1, 아니면 0
	private int tryRental(String book_id){
		BookVO bv = is.readBook(book_id);
		if(bv.isBook_state()){	//true이면(대여가능한 상태이면)
			System.out.println("해당 도서가 대여가능한 상태입니다.\n대여하시겠습니까? (Y/N)");
			String check = sc.next();
			if("Y".equals(check.toUpperCase())){
				return rentalBook(bv);
			}else{
				System.out.println("취소되었습니다.\n");
			}
		}else{ //대여불가하면 예약
			System.out.println("해당 도서가 이미 대여중입니다.\n예약하시겠습니까? (Y/N)");
			String check = sc.next();
			if("Y".equals(check.toUpperCase())){
				// 자기가 자기책을 예약하는 건 불가능해
				//메서드
				return reserveBook(bv);
			}else{
				System.out.println("취소되었습니다.\n");
			}
		}
		return 0;
	}
	
	/**
	 * 책 대여 메서드
	 * @param bv 대여할 책의 모든 정보
	 * @return 대여에 성공하면 1, 아니면 0
	 * @author 민태원
	 * @since 2020.11.07
	 */
	private int rentalBook(BookVO bv){
		Map<String,String> map = new HashMap<>();
		map.put("mem_id", nowMember.getMem_id());
		map.put("book_id", bv.getBook_id());
		RentalVO rv = is.createRentalVO(map);
		if(rv!=null){
			System.out.println("대여가 완료되었습니다.");
			System.out.println("도서명 : " + bv.getBook_name());
			System.out.println("대여일 : " + rv.getRental_start());
			System.out.println("반납일 : " + rv.getRental_end());
			bv.setBook_state(false);
			return 1;
		}else{
			System.out.println("대여에 실패하였습니다..");
		}
		return 0;
	}
	
	/**
	 * 책 예약 메서드
	 * @param bv 예약할 책의 모든 정보
	 * @return 예약에 성공하면 1, 아니면 0
	 * @author 민태원
	 * @since 2020.11.07
	 */
	private int reserveBook(BookVO bv){
		Map<String,String> map = new HashMap<>();
		map.put("mem_id", nowMember.getMem_id());
		map.put("book_id", bv.getBook_id());
		String rentalDate = is.createReserveVO(map);
		if(rentalDate!=null){
			System.out.println("예약이 완료되었습니다.");
			System.out.println("예약도서명 : " + bv.getBook_name());
			System.out.println("대여가능일 : " + rentalDate);
			return 1;
		}else{
			System.out.println("예약에 실패하였습니다..");
		}
		return 0;
	}
	
/////////////////////////////게시판//////////////////////////////////
	/**
    * 게시판메서드
    * @author 조애슬
    * @since 2020-11-04
    */
   private void boardView(){
      while(true){
    	 showBanner("게시판");
         System.out.println("사용하실 메뉴를 선택해주세요");
         System.out.println("[1] 공지사항");
         System.out.println("[2] 희망도서목록");
         System.out.println("[0] 뒤로");
         System.out.println("=======================================================");
         
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
	   	is.noticeList();//공지출력
	   	while(true){
	   		System.out.println("=======================================================");
	   		System.out.println("[0] 뒤로");
	   		System.out.println("=======================================================");
	   		int input = 0;
			try{
			   input = scanNo();
			}catch(Exception e){
			   System.out.println("숫자를 입력해주세요."); //숫자아닌거 거르고
			   continue;
			}
			switch(input){
			//숫자긴 한데 공지사항외의 글번호를 누르는건 open에서 걸러야할듯..?		
			case 0 :
				return;
			default : 
				is.openNoDetail(input); 	//공지사항의 글번호를 누르면 그값을 가지고 공지상세보기 메서드로
//				System.out.println("다시 입력해주세요");
				break;
			}
      }
   }
   
   /**
    * 공지사항 자세히 볼 글번호 입력 
    * @return int
    * @author 조애슬
    * @since 2020-11-06
    */
   int scanNo(){
	   System.out.println("열람할 글번호를 입력하세요");
	   Scanner input = new Scanner(System.in);
	   int no = input.nextInt();
	   return no;
   }
   
   /**
    * 희망도서목록 메서드
    * @author 조애슬
    * @since 2020.11.05
    */
   private void hopeView(){
	   showBanner("희망도서 목록");
	   is.hopeList();
	   
      while(true){
         System.out.println("=======================================================");
         System.out.println("[0] 뒤로  \t [1] 글등록 [2] 글삭제 [3] 상세보기");
         System.out.println("=======================================================");
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
        	 hopeRegiView();//글등록
        	 return;
         case 2 :
        	 int removeNo = hopeRemoveView();//글삭제할 번호 받아서
        	 String mem_id = nowMember.getMem_id();
        	 if(is.hopeRemoveView(mem_id,removeNo)){ //글 삭제하러
        		System.out.println("글 삭제가 완료되었습니다.");//본인의글인경우
        		//hopeView();
        		return;
        	 }else{
        		 System.out.println("회원님의 글이 아니기 때문에 삭제할 수 없습니다.");
        		 return;
        	 }
        	// break;
         case 3 :
        	 int hopeNo = hopeDetailView();//글상세보기 번호받아서
        	 is.hopeDetailView(hopeNo);//글번호가지고 희망상세출력하러
        	 break;
         default : 
        	 System.out.println("다시 입력해주세요");
         }
      }
  }
   
   /**희망도서 상세볼 글번호 입력
    * @author 조애슬
    * @return 
    * @since 2020-11-07
    */
   int hopeDetailView(){
	   while(true) {
		   System.out.println("자세히 볼 글번호를 입력하세요");
		   int hopeNo=0;
		   try {
			   hopeNo = sc.nextInt();
		   } catch (Exception e) {
			   System.out.println("숫자만 입력하세요.");
			   continue;
		   }
		   return hopeNo;
	   }
   }
   
   /**
    *희망도서 삭제할 글 번호 입력
    *@author 조애슬
    *@since 2020-11-07
    */
   
   int hopeRemoveView(){
	   while(true) {
		    System.out.println("삭제 할 글번호를 입력하세요");
		    int hopeNo = 0;
		    try {
		    	hopeNo = sc.nextInt();
			
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				continue;
			}
		    return hopeNo;
	   }
   }
   
   /**
    * 희망도서등록메서드
    * @author 조애슬
    * @since 2020.11.05
    */
   private void hopeRegiView(){
	  showBanner("희망도서 등록");
	  Map<String,String> params = new HashMap<String, String>();//희망도서입력을 위한 map
	  
	  String hope_name = scanHopeName();
	  String hope_memId = nowMember.getMem_id();
	  String hope_author = scanHopeAuthor();
	  String hope_publisher = scanHopePublisher();
	  String hope_content = scanHopeContent();
	  
	  params.put("hope_name", hope_name);
	  params.put("hope_memId", hope_memId);
	  params.put("hope_author", hope_author);
	  params.put("hope_publisher", hope_publisher);
	  params.put("hope_content", hope_content);
	  
	  if(is.hopeListAdd(params)){
		  showBanner("희망도서가 정상적으로 등록되었습니다.");
	  }
	 
      //return;
	  hopeView();
   }
   
   /**희망도서제목 입력
    * @author 조애슬
    * @return String hope_name
    * @since 2020-11-06
    */
   String scanHopeName(){
	   System.out.println("희망하는 도서명을 입력하세요");
		Scanner input = new Scanner(System.in);
		String hope_name=input.next();
		return hope_name;
   };
   /**희망도서작가 입력
    * @author 조애슬
    * @return String hope_author
    * @since 2020-11-06
    */
   String scanHopeAuthor(){
	   System.out.println("작가를 입력하세요");
	   Scanner input = new Scanner(System.in);
	   String hope_author=input.next();
	   return hope_author;
   };
   
   /**희망도서 출판사 입력
    * @author 조애슬
    * @return String hope_publisher
    * @since 2020-11-06
    */
   String scanHopePublisher(){
	   System.out.println("도서의 출판사를 입력하세요");
	   Scanner input = new Scanner(System.in);
	   String hope_publisher=input.next();
	   return hope_publisher;
   };
   
   /**희망이유 입력
    * @author 조애슬
    * @return String hope_content
    * @since 2020-11-06
    */
   String scanHopeContent(){
	   System.out.println("희망하는 이유를 입력하세요");
	   Scanner input = new Scanner(System.in);
	   String hope_content=input.next();
	   return hope_content;
   };


////////////////////////////////////////////마이페이지//////////////////////////////////////////////////
	/**
	 * 마이페이지
	 * @author 민태원
	 * @since 2020.11.04
	 */
	private void myPageView() {
		while(true) {
			System.out.println("=============<MYPAGE>=============");
			System.out.println("[1]정보수정");
			System.out.println("[2]대출도서목록");
			System.out.println("[3]예약도서목록");
			System.out.println("[0]뒤로");
			
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력해주세요.");
				sc = new Scanner(System.in);
				continue;
			}
			
			switch(input) {
			case 1:
				updateInfoView();	//정보수정
				if(!nowMember.isActivate())	//탈퇴했으면
					return;
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
			System.out.println("==============<내 정보>==============");
			//내 정보 출력 메서드
			MemberVO mv = is.readMember(nowMember.getMem_id());
			printMyInfo(mv);
			System.out.println("-----------------------------------");
			System.out.println("바꿀 정보 선택");
			System.out.println("[1]비밀번호 [2]전화번호 [3]이메일\n[9]탈퇴하기 [0]뒤로가기");
			
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력해주세요.");
				sc = new Scanner(System.in);
				continue;
			}
			Map<String, String> myInfo = new HashMap<>();
			myInfo.put("mem_id", nowMember.getMem_id());
			switch(input) {
			case 1:
				//비밀번호
				myInfo.put("mem_pw", updatePW());
				break;
			case 2:
				//전화번호
				myInfo.put("mem_tel", updatePhone());
				break;
			case 3:
				//이메일
				myInfo.put("mem_email", updateEmail());
				break;
			case 9:
				//탈퇴 메서드
				if(withdrawal(nowMember.getMem_id())==1){
					System.out.println("\n탈퇴되었습니다.\n");
					return;
				}
				break;
			case 0:
				return;		//뒤로가기
			default : 
	        	 System.out.println("다시 입력해주세요");
	        	 continue;
			}
			if(is.updateMember(myInfo)==1){
				System.out.println("\n내 정보가 수정되었습니다.\n");
			}
		}
	}
	
	/**
	 * 내 정보 출력 메서드(아이디,이름,생년월일,이메일,전화번호,대출횟수)
	 * @param mem_id 나의 아이디
	 * @author 민태원
	 * @since 2020.11.04
	 * @return 회원정보 반환
	 */
	private void printMyInfo(MemberVO mv){
		System.out.println("성명 : "+mv.getMem_name());
		System.out.println("ID : "+mv.getMem_id());
		System.out.println("생년월일 : "+mv.getMem_bir());
		System.out.println("전화번호 : "+mv.getMem_tel());
		System.out.println("이메일 : "+mv.getMem_email());
	}
	
	/**
	 * 비밀번호 변경 메서드 
	 * @return 비밀번호 확인되면 새로운 비밀번호 반환, 아니면 null 반환
	 * @author 민태원
	 * @since 2020.11.06
	 */
	private String updatePW(){
		System.out.print("새로운 비밀번호를 입력하세요 : ");
		String pw1 = sc.next();
		if(RegEx.checkMem_pw(pw1)){
			System.out.print("한번더 입력하세요 : ");
			String pw2 = sc.next();
			if(pw1.equals(pw2)){
				return pw1;
			}
			System.out.println("\n비밀번호가 다릅니다.\n");
		}else
			System.out.println("올바른 형식의 비밀번호가 아닙니다. (8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.)");
		return null;
	}	
	
	/**
	 * 전화번호 변경 메서드
	 * @return 형식에 맞는 전화번호이면 전화번호가 저장된 문자열 반환, 아니면 null 반환
	 * @author 민태원
	 * @since 2020.11.05
	 */
	private String updatePhone(){
		System.out.print("바꾸실 번호를 입력하세요 : ");
		String tel = sc.next();
		if(RegEx.checkMem_tel(tel))
			return tel;
		System.out.println("올바른 형식의 전화번호가 아닙니다. (예-010-1111-1111)");
		return null;
	}
	
	/**
	 * 이메일 변경 메서드
	 * @return 형식에 맞는 이메일이면 이메일이 저장된 문자열 반환, 아니면 null 반환
	 * @author 민태원
	 * @since 2020.11.05
	 */
	private String updateEmail(){
		System.out.print("바꾸실 이메일을 입력하세요 : ");
		String email = sc.next();
		if(RegEx.checkMem_email(email))
			return email;
		System.out.println("올바른 형식의 이메일이 아닙니다. (영문자로 시작, 예- smile123@naver.com )");
		return null;
	}
	
	/**
	 * 회원 탈퇴 메서드
	 * 출력: "정말 탈퇴하시겠습니까? (Y/N)" => y면 회원정보 삭제 / n이면 return
	 * @param mem_id 탈퇴할 멤버의 아이디
	 * @return 삭제에 성공하면 1, 아니면 0 
	 * @author 민태원
	 * @since 2020.11.06
	 */
	int withdrawal(String mem_id){
		System.out.println("정말 탈퇴하시겠습니까? (Y/N)");
		String check = sc.next();
		if("Y".equals(check.toUpperCase()))
			return is.deleteMember(mem_id);
		return 0;
	}
	
////대여	
	/**
	 * 대출목록
	 * @author 민태원
	 * @since 2020.11.06
	 */
	private void rentalListView() {
		while(true) {
			System.out.println("======================<나의 대여 목록>======================");
			//대출도서리스트 출력 메서드
			List<Map<String,String>> myList = printRentalList(nowMember.getMem_id());
			if(myList.size()==0){
				System.out.println("대여중인 도서가 없습니다.");
				System.out.println("--------------------------------------------------------");
				System.out.println("[0]뒤로");
			}else{
				System.out.println("--------------------------------------------------------");
				System.out.println("[0]뒤로");
				System.out.println("반납할 도서를 선택해 주세요.");
			}
			
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력해주세요.");
				sc = new Scanner(System.in);
				continue;
			}
			
			if(input==0){
				return;
			}else if(0<input && input<=myList.size()){
				//도서번호입력받아 반납 메서드
				Map<String, String> map = myList.get(input-1);
				returnBook(map.get("book_id"));
			}else{
				System.out.println("다시 입력해주세요");
			}
		}
	}
	
	/**
	 * 나의 대출도서목록 출력 메서드
	 * @param mem_id 나의 아이디
	 * @return 대여 정보를 리스트로 반환
	 * @author 민태원
	 * @since 2020.11.06
	 */
	private List<Map<String, String>> printRentalList(String mem_id){
		List<Map<String, String>> myList = is.readRentalList(mem_id);
		for(Map<String, String> map : myList){
			System.out.print("["+map.get("rental_no")+"](도서명)"+map.get("book_name")+"  ");
			System.out.print("(저자)"+map.get("book_author")+"  ");
			System.out.println("(출판사)"+map.get("book_publisher")+"  ");
			System.out.println("대여일 : " + map.get("start_date"));
			System.out.println("반납일 : " + map.get("end_date"));
		}
		return myList;
	}
///반납	
	/**
	 * 도서의 아이디를 입력받아 반납하는 메서드
	 * 출력 : "반납하시겠습니까? (y/n)" => y면 반납 / n면 return
	 * @param book_id 반납할 도서의 id
	 * @author 민태원
	 * @since 2020.11.06
	 */
	void returnBook(String book_id){
		//rentalList에서 book을 찾고 list에서 삭제
		System.out.println("반납하시겠습니까? (Y/N)");
		String check = sc.next();
		if("Y".equals(check.toUpperCase())){
			if(is.returnBook(book_id)==1){
				System.out.println("\n도서가 반납되었습니다.\n");
				return;
			}
		}
		System.out.println("반납 취소");
	}
	
///예약	
	/**
	 * 예약목록
	 * @author 민태원
	 * @since 2020.11.06
	 */
	private void rsvLsitView() {
		while(true) {
			System.out.println("==============<나의 예약 목록>==============");
			//예약 도서 목록 출력
			List<Map<String, String>> rsvList = printBookList(nowMember.getMem_id());
			
			if(rsvList.size()==0){
				System.out.println("예약중인 도서가 없습니다.");
				System.out.println("----------------------------------------");
				System.out.println("[0]뒤로");
			}else{
				System.out.println("----------------------------------------");
				System.out.println("[0]뒤로");
				System.out.println("예약을 취소할 책의 번호를 입력해주세요.");
			}
			
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력해주세요.");
				sc = new Scanner(System.in);
				continue;
			}
			
			//도서번호입력받아 예약취소 메서드
			if(input==0){
				return;
			}else if(0<input && input<=rsvList.size()){
				//도서번호입력받아 반납 메서드
				Map<String, String> map = rsvList.get(input-1);
				rsvCancel(map.get("book_id"));
			}else{
				System.out.println("다시 입력해주세요");
			}
		}
	}
	
	/**
	 * 나의 예약도서목록 출력 메서드
	 * @param mem_id 나의 아이디
	 * @return 예약정보를 리스트로 반환
	 * @author 민태원
	 * @since 2020.11.06
	 */
	private List<Map<String, String>> printBookList(String mem_id){
		List<Map<String, String>> rsvList = is.readReserveList(mem_id);
		// [번호]도서명, 저자, 출판사, 대여가능일자
		for(Map<String, String> map : rsvList){
			System.out.print("["+map.get("rsv_no")+"](도서명)"+map.get("book_name")+"  ");
			System.out.print("(저자)"+map.get("book_author")+"  ");
			System.out.println("(출판사)"+map.get("book_publisher")+"  ");
			System.out.println("대여가능일 : " + map.get("end_date"));
		}
		return rsvList;
	}
	
	/**
	 * 예약 취소 메서드 
	 * 출력: "취소하시겠습니까? (y/n)" => y면 예약취소 / n면 return
	 * @param book_id 예약된 도서의 아이디
	 * @author 민태원
	 * @since 2020.11.04
	 */
	void rsvCancel(String book_id){
		System.out.println("예약을 취소하시겠습니까? (Y/N)");
		String check = sc.next();
		if("Y".equals(check.toUpperCase())){
			Map<String, String> map = new HashMap<>();
			map.put("mem_id", nowMember.getMem_id());
			map.put("book_id", book_id);
			if(is.cancelReserve(map)==1){
				System.out.println("\n예약이 취소되었습니다.\n");
				return;
			}
		}
		System.out.println("취소를 취소한다.\n");
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
				 * 로그아웃하면 로그인화면으로 돌아가기
				 */
				return;
			}
			case 1: {
				/**
				 * 게시관 관리view을 보여주는 메소드
				 */
				noiceMainView();
				break;
			}
			case 2: {
				/**
				 * 책 관리 view을 보여주는 메소드
				 */
				bookView();
				break;
			}
			case 3: {
				/**
				 * 회원관리view을 보여주는 메소드
				 */
				memberView();
				break;

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
				return;
			}
			case 1: {
				/**
				 * 공지사항view을 보여주는 메소드
				 */
				noiceView();
				break;
			}
			case 2: {
				/**
				 * 희망도서목록view을 보여주는 메소드
				 */
				hopeBookView();
				break;
			}
			default:
				System.out.println("잘못입력하였습니다");
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
			is.noticList();

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
				return;
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
				is.noiceReadMethod(num2);
				break;
			}
			case 2: {
				/**
				 * 공지추가 메소드
				 */
				noiceAddMethod();
				System.out.println("추가되었습니다.");
				break;
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
				is.noiceDeltleMethod(num3);
				break;
			}
			default:
				System.out.println("잘못입력하였습니다");

			}
		}
	}
	

	/**
	 * 공지리스트를 출력
	 * 
	 * @author 김태규
	 * @return
	 */
	void noiceListView() {
		// sql
		List<NoticeVO> noiceListView = null;
//		noiceListView = is.noticeList();

		// 공지 리스트 출력
		for (int i = 0; i < noiceListView.size(); i++) {
			System.out.println("\t\t\t[" + i + "] "+noiceListView.get(i).getNotice_no() + noiceListView.get(i).getNotice_title()+noiceListView.get(i).getNotice_date());
		}
	}

	/**
	 * 입력 받은 공지리스트를 출력
	 * 
	 * @author 김태규
	 * @return
	 */
	void noiceReadMethod(int num) {
		// sql
		List<NoticeVO> nv = null;
		// nv = is.noticeList();
		// 공지 리스트 출력 num가 카운된 i값과 같은수를 찾아서 그 번호만 출력한다.
		for (int i = 0; i < nv.size(); i++) {
			if (num == i) {
				System.out.println("\t\t\t[" + nv.get(i).getNotice_no() + "] "
						+ nv.get(i).getNotice_title());
				System.out.println("\t\t\t[ 내용 ] "
						+ nv.get(i).getNotice_content());
				System.out.println("\t\t\t[ 작성날짜 ] "
						+ nv.get(i).getNotice_date());
			}
		}
	}
	/**
	 * 공지를 추가한다.
	 * 
	 * @author 김태규
	 * @return
	 */
	void noiceAddMethod() {
		NoticeVO nv = new NoticeVO();
		Date time = new Date(); // 날짜를 구한다.
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-mm-dd");// 날짜 형식
		String timeset = fm.format(time);

		System.out.print("공지 제목 : ");
		String nt = sc.next();
		System.out.print("공지 내용 : ");
		String nc = sc.next();

		nv.setAdmin_id("ad1234");
		nv.setNotice_no(0);
		nv.setNotice_title(nt);
		nv.setNotice_content(nc);
		nv.setNotice_date(timeset);

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
			hopeBookListView();

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
				return;
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
				is.hopeBookAddMethod(num2);
				break;
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
				is.hopeBookeDeltleMethod(num2);
				break;
			}
			default:
				System.out.println("잘못입력하였습니다");
				return;
			}
		}
	}

	/**
	 * 희망 도서 리스트 뷰
	 * 
	 * @author 김태규
	 * @since 2020.11.04
	 */
	public void hopeBookListView() {
		// sql
		List<HopeVO> hopeListView = null;
		// hopeListView = is.hopeList();
		// 희망 리스트 출력
		for (int i = 0; i < hopeListView.size(); i++) {
			System.out.println(hopeListView.get(i).getHope_id());
			System.out.println(hopeListView.get(i).getHope_name());
			System.out.println(hopeListView.get(i).getHope_content());
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
			System.out.println("[2]\t 도서 등록");
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
				return;
			}
			case 1: {
				/**
				 * 전체 도서리스트 출력 메소드
				 */
				is.bookListMethod();
				break;
			}
			case 2: {
				/**
				 * 도서 등록 메소드
				 */
				bookAddMethod();
				System.out.println("책이 등록되었습니다");
				break;
			}
			default:
				System.out.println("잘못입력하였습니다");
			}
		}
	}

	/**
	 * 책 등록
	 * 
	 * @author 김태규
	 * @since 2020.11.05
	 */
	private void bookAddMethod() {
		Map<String, String> params = new HashMap<String, String>();// 희망도서입력을 위한
																	// map
		System.out.print("도서 제목 : ");
		String bn = sc.next();
		System.out.print("도서 작가 : ");
		String ba = sc.next();
		System.out.print("도서 분류 : ");
		String bl = sc.next();
		System.out.print("도서 출판사 : ");
		String bp = sc.next();
		System.out.print("도서 내용 : ");
		String bs = sc.next();

		params.put("book_name", bn);
		params.put("book_author", ba);
		params.put("book_summary", bl);
		params.put("book_publisher", bp);
		params.put("book_LGU", bs);

		if (is.hopeListAdd(params)) {
			showBanner("희망도서가 정상적으로 등록되었습니다.");
		}
	}

	/**
	 * 책리스트 출력
	 * 
	 * @author 김태규
	 * @since 2020.11.05
	 */
	public void bookListMethod() {
		// sql
		List<BookVO> bookListView = null;
//		bookListView = is.bookList();
		// 책 출력
		for (int i = 0; i < bookListView.size(); i++) {
			System.out.println("\t\t\t[" + bookListView.get(i).getBook_id()
					+ "]");
			System.out.println("\t\t\t[" + bookListView.get(i).getBook_name()
					+ "]");
			System.out.println("\t\t\t[" + bookListView.get(i).getBook_author()
					+ "]");
			System.out.println("\t\t\t[" + bookListView.get(i).getBook_LGU()
					+ "]");
			System.out.println("\t\t\t["
					+ bookListView.get(i).getBook_publisher() + "]");
			System.out.println("\t\t\t["
					+ bookListView.get(i).getBook_summary() + "]");
		}
	}

	// 회원리스트
	/**
	 * 회원 관리 뷰
	 * 
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
				return;
			}
			case 1: {
				/**
				 * 전체 회원리스트 뷰 메소드
				 */
				memberListView();
				break;
			}
			case 2: {
				/**
				 * 전체 블랙리스트 뷰 메소드
				 */
				blackListView();
				break;
			}
			default:
				System.out.println("잘못입력하였습니다");
			}
		}

	}

	/**
	 * 회원 리스트 뷰
	 * 
	 * @author 김태규
	 * @since 2020.11.04
	 */
	public void memberListView() {
		/**
		 * 전체 회원리스트 출력 메소드
		 */
		is.memList();

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
			return;
		}
		case 1: {
			/**
			 * 블랙리스트에 추가 메소드
			 */
			System.out.print("블랙 리스트에 추가될 회원 아이디 : ");
			String id = null;
			try {

				id = sc.next();
			} catch (Exception e) {
				System.out.println("잘못입력하였습니다");
				return;
			}
			is.createBlackList(id);
			break;
		}
		default:
			System.out.println("잘못입력하였습니다");
		}
	}

	/**
	 * 블랙 리스트 뷰
	 * 
	 * @author 김태규
	 * @since 2020.11.04
	 */
	public void blackListView() {
		while (true) {

			/**
			 * 블랙리스트 리스트 출력 메소드
			 */
			is.readBlack();

			System.out.println("[1]\t 블랙리스트 삭제 ");
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
				return;
			}
			case 1: {
				/**
				 * 블랙리스트에 삭제 메소드
				 */
				System.out.print("삭제할 블랙리스트의 번호 : ");
				int num2 = 0;
				try {
					num2 = sc.nextInt();

				} catch (Exception e) {
					System.out.println("잘못입력하였습니다");
					continue;
				}
				//is.blackDeltleMethod();
				break;
			}

			default:
				System.out.println("잘못입력하였습니다");
			}
		}
	}
}