package common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import rental.*;
import reserve.*;
import notice.*;
import member.*;
import hope.*;
import blackList.*;
import book.*;
import bookLGU.*;
import admin.*;
import game.*;

public class ViewClass {
	private IAdminService ias = IAdminServiceImpl.getInstance();
	private IBlackListService ibls = IBlackListServiceImpl.getInstance();
	private IBookLGUService iblgus = IBookLGUServiceImpl.getInstance();
	private IBookService ibs = IBookServiceImpl.getInstance();
	private IHopeService ihs = IHopeServiceImpl.getInstance();
	private IMemberService ims = IMemberServiceImpl.getInstance();
	private INoticeService ins = INoticeServiceImpl.getInstance();
	private IRentalService irts = IRentalServiceImpl.getInstance();
	private IReserveService irsvs = IReserveServiceImpl.getInstance();
	
	private MemberVO nowMember = null;
	private AdminVO adMember = null;
	Scanner sc = new Scanner(System.in);
	
	/**
	 * AES256 암호화
	 * @author 민태원
	 * @since 2020.11.16
	 */
	private String secretKey = "asdfasdfasdfasdf";	// 비밀키
	private AES256 aes = new AES256(secretKey);
	
	/**
    * 시작배너
    * @author 민태원
    * @since 2020.11.04
    */
	private void showBanner(String str) { 
		System.out.println();
		System.out.println("==================================================================");
		System.out.println("\t\t\t JJUGURI LIBRARY");
		System.out.println("──────────────────────────────────────────────────────────────────");
		System.out.println("\t\t\t" + str);
		System.out.println("──────────────────────────────────────────────────────────────────");
	}

	/**
	 * 종료메서드
	 * @author 민태원
	 * @since 2020.11.04
	 */
	private void endProgram(){
		showBanner("\n\t\t프로그램을 종료합니다. 이용해 주셔서 감사합니다.\n");
		System.exit(0);
	}
	
	/**
	 * 현재 날짜 가져오기 메서드
	 * @return 현재 날짜를 문자열로 반환
	 * @author 민태원
	 * @since 2020.11.07
	 */
	public String getDate(){
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(new Date());
        return df.format(cal.getTime());
	}
	
	/**
	 * n일 후의 날짜 구하는 메서드
	 * @param dayInfo 더할 날짜 문자열, 더할 날의 수를 Map으로 반환
	 * @return 반납일자를 문자열로 반환
	 * @author 민태원
	 * @since 2020.11.07
	 */
	public String getEndDate(Map<String, Object> dayInfo){
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = fm.parse((String)dayInfo.get("day"));
		} catch (ParseException e) {
			System.out.println("날짜변환 실패..");
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, (int)dayInfo.get("addDay"));
		return fm.format(cal.getTime());
	}
	
	/**
	 * 시작 메서드
	 * @author 민태원
	 * @since 2020.11.05
	 */
	void startMethod() {
		//로그인,회원가입
		while(true) {
			showBanner("쭈구리 도서관 입장");
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
	 * 로그인 메서드 - 아이디와 비밀번호값을 받아 database와 비교 후 true이면 homeView로
	 * 
	 * @author 조애슬
	 * @since 2020-11-06
	 */
	private void loginSessionView() {
		Map<String, String> params = new HashMap<>();// id 와 pw를 묶을 Map 생성

		String mem_id = scanID(); // 아이디입력, 올바른 값인지 검사 후 반환
		String mem_pw = scanPW(); // 비밀번호입력, 올바른 값인지 검사 후 반환

		params.put("mem_id", mem_id); // 입력받은 id,pw Map에 넣음
		params.put("mem_pw", mem_pw); // 입력받은 id,pw Map에 넣음

		nowMember = ims.loginMatch(params);

		if (nowMember.getMem_id() == null) { // id와 pw값을 db로 넘겨서 회원확인
			// nowMember = 해당회원의 MemberVO가져와야함
			if (ias.adminMatch(params) != null) {// 관리자일때
				adMember = ias.readAdmin(mem_id);
//				 System.out.println(adMember.getadmin_id() + "님 환영합니다");
				showBanner("관리자계정으로 접속하였습니다.");
				adminView();
			} else {
				System.out.println("아이디나 비밀번호가 틀렸습니다.");
			}
		} else if (nowMember.getIsActivate() == "F") {
			System.out.println("로그인할 수 없는 계정입니다.");
			return;
		} else {
			showBanner(nowMember.getMem_id() + "님 환영합니다.");
			homeView();
		}
	}

	/**
	 * 회원가입 메서드(ex1) -> MemberVO
	 * @author 조애슬
	 * @since 2020-11-06
	 */
	private void joinMemberView() {
		showBanner("회원가입");
		MemberVO params = new MemberVO(); // 묶어서 저장할 MemberVO
														// params 생성

		String mem_id = scanID(); // 아이디, 패스워드등을 입력받는 메서드들을 통해 변수에넣음
		AdminVO av = ias.readAdmin(mem_id);
		int isUnique = ims.idUniqCheck(mem_id); // 아이디 중복체크
		if (isUnique == 0 && av == null) {// 중복아니면 나머지 값 입력받고

			String mem_pw = scanPW();
			String mem_name = scanNM();
			String mem_bir = scanBIR();
			String mem_tel = scanTel();
			String mem_email = scanEM();

			params.setMem_id(mem_id);// memberVO멤버에 다 값을 넣고
			params.setMem_pw(mem_pw);
			params.setMem_name(mem_name);
			params.setMem_bir(mem_bir);
			params.setMem_tel(mem_tel);
			params.setMem_email(mem_email);

			if (ims.createMember(params) > 0) { // db에 정상적으로 들어가면
				//이메일 보내기
				String title = mem_name + "님 안녕하세요! JJUGURI 도서관에 오신 것을 환영합니다!";
				String content = "저희 JJUGURI 도서관에 가입을 해주셔서 감사합니다."
					+ "\n 현재 코로나19 사태로 하루 2회 오전 8시-9시, 오후 12시-1시에 방역을 실시 중입니다.  "
					+ "\n 방역 중에는 도서관을 이용할 수 없음을 알려드리고, 입출입시에는 반드시 발열 체크 및"
					+ "\n 마스크를 꼭! 착용해주시기 바랍니다."
					+ "\n 또한, 희망도서 신청시 구체적인 사유를 남겨주시면 감사하겠습니다."
					+ "\n 오늘도 즐거운 하루 되세요.";
				String email = mem_email;
				String filePath = null;
				Email mail = new Email(title, content, email, filePath);
				mail.sendNaver();
				showBanner("성공적으로 가입되었습니다.\n\t\t\t로그인해 주세요.");
			}// 입력한 한회원의모든정보(MemberVO)DB로넘겨줌

		} else {// 아이디값이 중복이면
			System.out.println("중복된 아이디입니다.");
			return;
		}
	}

	/**
	 * 아이디입력
	 * @author 조애슬
	 * @return String mem_id
	 * @since 2020.11.06
	 */
	String scanID() {
		System.out.println("아이디를 입력하세요");
		while (true) {
			try {
				String mem_id = sc.next();// 이걸 여기에넣어야됨
				if (RegExClass.checkMem_id(mem_id)) {
					return mem_id;
				} else {
					System.out
							.println("5~15자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.");
					continue;
				}

			} catch (Exception e) {
				System.out.println("5~15자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.");
				continue;
			}
		}
	}// 아이디 입력받음
   
   
	/**
	 * 패스워드입력
	 * 
	 * @author 조애슬
	 * @return String mem_pw
	 * @since 2020.11.06
	 */
	String scanPW() {
		System.out.println("비밀번호를 입력하세요");

		while (true) {
			try {
				String mem_pw = sc.next();// 이걸 여기에넣어야됨
				if (RegExClass.checkMem_pw(mem_pw)) {
					return aes.encrypt(mem_pw);
				} else {
					System.out.println("8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
					continue;
				}

			} catch (Exception e) {
				System.out.println("8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
				continue;
			}
		}
	}// 패스워드 입력받음
   
   
	/**
	 * 이름입력
	 * 
	 * @author 조애슬
	 * @return String mem_name
	 * @since 2020.11.06
	 */
	String scanNM() {
		System.out.println("이름을 입력하세요");
		while (true) {
			try {
				String mem_name = sc.next();
				if (RegExClass.checkMem_name(mem_name)) {
					return mem_name;
				} else {
					System.out.println("2~5자 한글만 사용가능합니다. (특수기호, 공백 사용 불가)");
					continue;
				}

			} catch (Exception e) {
				System.out.println("2~5자 한글만 사용가능합니다. (특수기호, 공백 사용 불가)");
				continue;
			}
		}
	}// 이름 입력받음

	/**
	 * 이메일입력
	 * 
	 * @author 조애슬
	 * @return String mem_email
	 * @since 2020.11.06
	 */
	String scanEM() {
		System.out.println("이메일을 입력하세요");
		while (true) {
			try {
				String mem_email = sc.next();
				if (RegExClass.checkMem_email(mem_email)) {
					return mem_email;
				} else {
					System.out
							.println("올바른 형식의 이메일이 아닙니다. (영문자로 시작, 예- smile123@naver.com )");
					continue;
				}

			} catch (Exception e) {
				System.out
						.println("올바른 형식의 이메일이 아닙니다. (영문자로 시작, 예- smile123@naver.com )");
				continue;
			}
		}
	}// 이메일 입력받음

	/**
	 * 전화번호입력
	 * 
	 * @author 조애슬
	 * @return String mem_tel
	 * @since 2020.11.06
	 */
	String scanTel() {
		System.out.println("전화번호를 입력하세요");
		while (true) {
			try {
				String mem_tel = sc.next();
				if (RegExClass.checkMem_tel(mem_tel)) {
					return mem_tel;
				} else {
					System.out.println("올바른 형식의 전화번호가 아닙니다. (예-010-1111-1111)");
					continue;
				}

			} catch (Exception e) {
				System.out.println("올바른 형식의 전화번호가 아닙니다. (예-010-1111-1111)");
				continue;
			}
		}
	}// 전화번호 입력받음

	/**
	 * 생일입력
	 * 
	 * @author 조애슬
	 * @return String mem_bir
	 * @since 2020.11.06
	 */
	String scanBIR() {
		System.out.println("생년월일을 입력하세요. (예-1990-01-01)");
		while (true) {
			try {
				String mem_bir = sc.next();
				if (RegExClass.checkMem_bir(mem_bir)) {
					return mem_bir;
				} else {
					System.out.println("올바른 형식의 생년월일이 아닙니다. (예-1990-01-01)");
					continue;
				}

			} catch (Exception e) {
				System.out.println("올바른 형식의 생년월일이 아닙니다. (예-1990-01-01)");
				continue;
			}
		}
	}// 생일 입력받음
   
/////////////////////////////////////////////////////HOME/////////////////////////////////////////////////////   
   /**
	 * 메인화면 - 검색, 게시판, 마이페이지, 반납
	 * @author 민태원
	 * @since 2020.11.04
	 */
	private void homeView() {
		while(true) {
			showBanner("HOME");
			System.out.println("[1]도서검색");
		    System.out.println("[2]게시판");
		    System.out.println("[3]마이페이지");
		    System.out.println("[4]반납");
		    System.out.println("[5]게임하러가기");
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
				if(nowMember==null)	// 탈퇴했으면
					return;
				break;
			case 4:
				rentalListView();	//반납 - 대출목록
				break;
			case 5:
				gameView();
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
	 * @author 송지은
	 * 도서명과 장르 검색으로 나뉨
	 */
	public void bookSearchView() {
		String input;
		while (true) {
			showBanner("도서검색");
			System.out.println("[1]도서명 검색");
			System.out.println("[2]장르명 검색");
			System.out.println("[0]뒤로");

			try {
				input = sc.next();
				switch (Integer.parseInt(input)) {
				case 1:
					bookSearch(); // 도서명 검색
					break;
				case 2:
					genreSearchView(); // 장르 검색
					break;
				case 0:
					return;
				default:
					System.out.println("다시 입력해주세요");
					continue;
				}
			} catch (Exception e) {
				System.out.println("숫자를 입력해주세요.");
				continue;
			}
		}
	}

	/**
	 * @author 송지은 도서명 입력을 받아서 검색 후, 해당되는 도서 목록을 출력해주는 메서드
	 */
	private void bookSearch() {
		showBanner("도서명 검색");

		List<BookVO> list = new ArrayList<>();
		BookVO vo = new BookVO();
		
		while (true) {
			Scanner scn = new Scanner(System.in);
			System.out.println("[0] 뒤로");
			System.out.println("도서명: ");
			
			String book_name = scn.nextLine();
			
			list = ibs.bookSearch(book_name);
			
			vo.setBook_name(book_name);
			
			if(book_name.equals("0")){
				return;
			}
			if (list.size() == 0) {
				System.out.println("검색된 도서가 없습니다.");
				continue;
			} else {
				showBanner("도서명  검색");
				System.out.println(list.size() + "권이 검색되었습니다");
				System.out.println("--------------------------------------------------");
				break;
			}
		}
		int count=1;
		for (int name = 0; name < list.size(); name++) {
			System.out.println("[" +count++ + "] 도서명 : " + list.get(name).getBook_name());
			System.out.println("--------------------------------------------------");
		}
		
		book_detail1(list);
	}
	
	/**
	 * @author 송지은
	 * 도서명으로 검색 후, 도서 리스트에서 원하는 번호 선택 하면 도서 상세 리스트를 보여주는 메서드
	 */
	private void book_detail1(List<BookVO> list) {
		int input = 0;

		System.out.println("선택하실 도서 번호 입력해주세요.");
		try {
			input = sc.nextInt();
		} catch (Exception e) {
			System.out.println("숫자만 입력하세요.");
			sc = new Scanner(System.in);
			return;
		}
		input--;
		
		while(true){
			showBanner("도서 상세 조회");
			System.out.println("도서번호 : " + list.get(input).getBook_id());
			System.out.println("도서명 : " + list.get(input).getBook_name());
			System.out.println("작가명 : " + list.get(input).getBook_author());
			System.out.println("출판사 : " + list.get(input).getBook_publisher());
			System.out.println("줄거리 : " + list.get(input).getBook_summary());
			System.out.print("대여상태 : ");
			if (list.get(input).getBook_state().equals("T")) {
				System.out.println("대여 가능");
			} else {
				System.out.println("대여중");
			}
			System.out.println("--------------------------------------------------");
			System.out.println("[1] 대여/예약하기  [0] 뒤로");
			int input2 = 0;
			try {
				input2 = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			switch(input2){
			case 0 :
				return;
			case 1:
				tryRental(list.get(input).getBook_id());
				return;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	/**
	 * @author 송지은
	 * 도서 분류 리스트 출력 메서드
	 */
	private List<BookLGUVO> lguList() {

		List<BookLGUVO> list = new ArrayList<>();
		list = iblgus.lguList();

		for (int index = 0; index < list.size(); index++) {
			System.out.print("[" + list.get(index).getBook_LGU() + "] ");
			System.out.print(list.get(index).getBook_theme());
			System.out.println();
		}

		return list;
	}
	
	/**
	 * @author 송지은
	 * 장르 리스트에서 선택후, 해당 장르에 해당하는 도서를 출력하는 메서드
	 */
	private void genreSearchView() {
		showBanner("장르 검색");
		lguList();
		
		List<BookVO> list = new ArrayList<>();
		System.out.println("──────────────────────────────────────────────────────────────────");
		int book_lgu=0;
		while(true){
			System.out.println("검색하실 장르 번호를 입력하세요.");
			try {
				book_lgu = sc.nextInt();
			} catch (Exception e) {
				sc = new Scanner(System.in);
				continue;
			}
			list = iblgus.themeList(book_lgu);
			
			if(list.isEmpty()){														
				System.out.println("유효한 장르 번호를 입력하세요.");
				continue;
			}	
			showBanner("장르 검색");
			int count=1;
			for (int index = 0; index < list.size(); index++) {		
				System.out.println("[" + count++ +"] 도서명 : "+ list.get(index).getBook_name());
				System.out.println("--------------------------------------------------");
			}
			
			int input =0;                                  
			while(true){
				System.out.println("[0] 뒤로");
				System.out.println("선택하실 도서 번호 입력해주세요.");
				try {
					input = sc.nextInt();
				} catch (Exception e) {
					System.out.println("숫자만 입력하세요.");
					sc = new Scanner(System.in);
					continue;
				}
				if(input == 0){
					return;
				}
				if(input > list.size()){
					System.out.println("잘못된 입력입니다.");
					continue;
				}
				input--;
				break;
			}
			book_detail2(list, input);
			break;
		}
	}
	
	/**
    * @author 송지은
    * 도서 장르 검색후, 원하는 도서번호를 입력했을 때 상세 내역을 출력하는 메서드
    */
   private void book_detail2(List<BookVO> list, int input) {
		showBanner("도서 상세");
		System.out.println("도서번호: " + list.get(input).getBook_id());
		System.out.println("도서명: " + list.get(input).getBook_name());
		System.out.println("작가명: " + list.get(input).getBook_author());
		System.out.println("출판사: " + list.get(input).getBook_publisher());
		System.out.println("줄거리: " + list.get(input).getBook_summary());
		System.out.print("대여 가능 여부: ");
		if(list.get(input).getBook_state().equals("T")){
			System.out.println("대여 가능");
		}else{
			System.out.println("대여중");
		}
		System.out.println("--------------------------------------------------");
		System.out.println("[1] 대여/예약하기  [0] 뒤로");
		while(true){
			int input2 = 0;
			try {
				input2 = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요");
				sc = new Scanner(System.in);
			}
			switch (input2) {
			case 0:
				return;
			case 1:
				tryRental(list.get(input).getBook_id());
				return;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
   }
	
/////////////////////////////검색된 도서 대여/예약하기/////////////////////////
	/**
	 * 대여 또는 예약하기
	 * bookVO의 state가 true이면 바로 대여, false이면 예약
	 * @param book_id 도서의 아이디
	 * @author 민태원
	 * @since 2020.11.09
	 */
	private void tryRental(int book_id){
		BookVO bv = ibs.readBook(book_id);
		if(bv.getBook_state().equals("T")){	//true이면(대여가능한 상태이면)
			System.out.println("해당 도서가 대여가능한 상태입니다.");
			rentalBook(bv);
		}else{ //대여불가하면 예약
			System.out.println("해당 도서가 이미 대여중입니다.");
			reserveBook(bv);
		}
	}
	
	/**
	 * 책 대여 메서드
	 * @param bv 대여할 책의 모든 정보
	 * @author 민태원
	 * @since 2020.11.07
	 */
	private void rentalBook(BookVO bv){
		System.out.println("대여하시겠습니까? (Y/N)");
		sc = new Scanner(System.in);
		String check = sc.next();
		if("Y".equals(check.toUpperCase())){
			Map<String,Object> map = new HashMap<>();
			map.put("mem_id", nowMember.getMem_id());
			map.put("book_id", bv.getBook_id());
			//대여시작일 - 현재날짜
			map.put("start_date", getDate());
			//반납일
			Map<String, Object> dayInfo = new HashMap<>();
		    dayInfo.put("day", getDate());
		    dayInfo.put("addDay", 14);
			map.put("end_date", getEndDate(dayInfo));
			
			RentalVO rv = irts.createRental(map);
			
			if(rv!=null){
				System.out.println("대여가 완료되었습니다.");
				System.out.println("도서명 : " + bv.getBook_name());
				System.out.println("대여일 : " + rv.getRental_start());
				System.out.println("반납일 : " + rv.getRental_end());
				bv.setBook_state("F");
				ibs.updateBook(bv);
				
				//회원 대여 횟수 갱신
				Map<String, String> myInfo = new HashMap<>();
				myInfo.put("mem_id", nowMember.getMem_id());
				myInfo.put("rent_cnt", String.valueOf(nowMember.getRent_count()+1));
				ims.updateMember(myInfo);
			}else{
				System.out.println("대여에 실패하였습니다..");
			}
		}else{
			System.out.println("취소되었습니다.\n");
		}
	}
	
	/**
	 * 책 예약 메서드
	 * @param bv 예약할 책의 모든 정보
	 * @author 민태원
	 * @since 2020.11.07
	 */
	private void reserveBook(BookVO bv){
		System.out.println("예약하시겠습니까? (Y/N)");
		sc = new Scanner(System.in);
		String check = sc.next();
		if("Y".equals(check.toUpperCase())){
			// 자기가 자기책을 예약하는 건 불가능해
			RentalVO rv = irts.readRentalVO(bv.getBook_id());	//예약자 확인
			if(rv != null && nowMember.getMem_id().equals(rv.getMem_id())) {
				System.out.println("이미 내가 대여한 책입니다.");
				return;
			}
			
			Map<String,Object> map = new HashMap<>();
			map.put("mem_id", nowMember.getMem_id());
			map.put("book_id", bv.getBook_id());
			
			if(irsvs.createReserveVO(map)==1){
				System.out.println("예약이 완료되었습니다.");
				System.out.println("예약도서명 : " + bv.getBook_name());
			}else{
				System.out.println("예약에 실패하였습니다..");
			}
		}else{
			System.out.println("취소되었습니다.\n");
		}
	}
	
/////////////////////////////게시판//////////////////////////////////
	/**
	 * 게시판메서드
	 * 
	 * @author 조애슬
	 * @since 2020-11-04
	 */
	private void boardView() {
		while (true) {
			showBanner("게시판");
			System.out.println("[1] 공지사항");
			System.out.println("[2] 희망도서목록");
			System.out.println("[0] 뒤로");

			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자를 입력해주세요.");
				sc = new Scanner(System.in);
				continue;
			}
			switch (input) {
			case 1:
				noticeView();
				break;
			case 2:
				hopeView();
				break;
			case 0:
				return;
			default:
				System.out.println("다시 입력해주세요");
			}
		}
	}

	/**
	 * 공지사항 메서드
	 * 
	 * @author 조애슬
	 * @since 2020-11-05
	 */
	private void noticeView() {
		showBanner("공지사항");
		List<NoticeVO> nl = ins.noticeList();// 공지출력 리스트 받아와서 여기서출력

		for (int i = 0; i < nl.size(); i++) {
			System.out.print("글번호 : " + nl.get(i).getNotice_no());
			System.out.println("\t제목 : " + nl.get(i).getNotice_title());
			System.out
					.println("──────────────────────────────────────────────────────────────────");
		}

		while (true) {
			System.out.println("[0] 뒤로");
			int input = 0;
			try {
				input = scanNo();
			} catch (Exception e) {
				System.out.println("숫자를 입력해주세요."); // 숫자아닌거 거르고
				sc = new Scanner(System.in);
				continue;
			}
			switch (input) {
			// 숫자긴 한데 공지사항외의 글번호를 누르는건 open에서 걸러야할듯..?
			case 0:
				return;
			default:
				NoticeVO nv = ins.openNoDetail(input); // 공지사항의 글번호를 누르면 그값을 가지고
														// 공지상세보기 메서드로 int?
				if (nv == null) {
					System.out.println("유효한 글 번호를 입력하세요");
				} else {
					noticeDetail(nv);
				}
				break;
			}
		}
	}

	/**
	 * 공지사항 자세히 볼 글번호 입력
	 * 
	 * @return int
	 * @author 조애슬
	 * @since 2020-11-06
	 */
	int scanNo() {
		while (true) {
			System.out.println("열람할 글번호를 입력하세요");
			int no = 0;
			try {
				no = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력해주세요");
				sc = new Scanner(System.in);
				continue;
			}
			return no;
		}
	}
	
	/**
	 * 공지사항 상세 출력
	 * @author 조애슬
	 */
	void noticeDetail(NoticeVO nv){
		System.out.println("──────────────────────────────────────────────────────────────────");
		System.out.println("글번호 : "+nv.getNotice_no());
		System.out.println("작성자 : "+nv.getAdmin_id());
		System.out.println("작성일 : "+nv.getNotice_date().substring(0,10));
		System.out.println("제목 : "+nv.getNotice_title());
		System.out.println("내용 : "+nv.getNotice_content());
		System.out.println("──────────────────────────────────────────────────────────────────");
	}

	/**
	 * 희망도서목록 메서드
	 * 
	 * @author 조애슬
	 * @since 2020.11.05
	 */
	private void hopeView() {
		showBanner("희망도서 목록");
		List<HopeVO> hl = ihs.hopeList(); //희망도서출력.. list로 받아야
		for (int i = 0; i < hl.size(); i++) {
			System.out.println("글번호 : " + hl.get(i).getHope_id()+"\t추천수 : "+hl.get(i).getThumb());
			System.out.println("작성자 : " + hl.get(i).getMem_id());
			System.out.println("책제목 : " + hl.get(i).getHope_name());
			System.out.println("작가 : " + hl.get(i).getHope_author());
			System.out.println("출판사 : " + hl.get(i).getHope_publisher());
			System.out.println("-----------------------------------------------");
		}

		while (true) {
			System.out.println("[1] 글등록 [2] 글삭제 [3] 상세보기 [0] 뒤로");
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자를 입력해주세요.");
				sc = new Scanner(System.in);
				continue;
			}
			switch (input) {
			case 0:
				return;
			case 1:
				hopeRegiView();// 등록받을 내용 받으러
				return;
			case 2:
				int removeNo = hopeRemoveNum();// 글삭제할 번호 받아서
				String mem_id = nowMember.getMem_id();
				if (ihs.hopeRemoveView(mem_id, removeNo)>0) { // 글 삭제되면 양수
					System.out.println("글 삭제가 완료되었습니다.");// 본인의글인경우
					// hopeView();
					return;
				} else {
					System.out.println("회원님의 글이 아니거나 유효한 번호가 아닙니다.\n");
					continue;//츄가
				}
			case 3:
				hopeDetailView(hl);//희망상세보기 뷰
				return;
			default:
				System.out.println("다시 입력해주세요");
			}
		}
	}
	
	/**
    * 희망도서 상세볼 view
    * @author 조애슬
    * @since 2020-11-13
    */
   void hopeDetailView(List<HopeVO> hl){
	   int hopeNo = hopeDetailNum();// 글상세보기 번호받아서
	   HopeVO hv = ihs.hopeDetailView(hopeNo);// 글번호가지고 희망상세가지고오기
      while(true){
      if(hv==null){
         System.out.println("유효한 번호를 입력하세요");
         sc = new Scanner(System.in);
         hopeNo = hopeDetailNum();// 글상세보기 번호받아서
         hv = ihs.hopeDetailView(hopeNo);
         continue;
         }
      //else{hopeDetailView(hv);
         break;
         //}
      }
      hopeDetailView(hv); 
      
      while (true) {
         System.out.println("[0] 뒤로  \t [1] 추천하기 ");
         int input = 0;
         try {
            input = sc.nextInt();
         } catch (Exception e) {
            System.out.println("숫자를 입력해주세요.");
            sc = new Scanner(System.in);
            continue;
         }
         switch (input) {
         case 0:
            return;
         case 1:
            hopeThumb(hv.getHope_id());
            return;
         default:
            System.out.println("다시 입력해주세요");
         }
      }
      
   }
	
//	/**
//	 * 희망글수정
//	 * @author 조애슬
//	 */
//	void hopeUpdate(){
//		while(true){
//			System.out.println("글 제목을 입력하세요");
//			
//		}
//	}
	
   /**
	 * 희망글추천
	 * @author 조애슬
	 */
	void hopeThumb(int hope_id){
		while(true){
		System.out.println("이 글을 추천하시겠습니까?(y/n)");
		String input =null;
		try {
			input = sc.next();
			
		} catch (Exception e) {
			System.out.println("유효한 문자를 입력하세요");
			sc = new Scanner(System.in);
			continue;// 츄가
		}
		switch (input) {
		case "y": case "Y":
			if(ihs.hopeThumb(hope_id)>0){
				System.out.println("추천되었습니다.");
				return;
			}else{
				System.out.println("추천에 실패하였습니다.");
			};
			break;
		case "n": case "N":
			System.err.println("추천하지 않으셨습니다.");
			return;
		default:
			System.out.println("유효한 문자를 입력하세요");
			
			break;
		}
		}//while
	}

	/**
	 * 희망도서 상세볼 글번호 입력
	 * 
	 * @author 조애슬
	 * @return
	 * @since 2020-11-07
	 */
	int hopeDetailNum() {
		while (true) {
			System.out.println("자세히 볼 글번호를 입력하세요");
			int hopeNo = 0;
			try {
				hopeNo = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			return hopeNo;
		}
	}
	
	/**
	 * 희망도서 상세 출력
	 * @author 조애슬
	 */
	void hopeDetailView(HopeVO hv){
		System.out.println("글번호 : " + hv.getHope_id());
		System.out.println("작성자 : " + hv.getMem_id());
		System.out.println("책제목 : " + hv.getHope_name());
		System.out.println("작가 : " + hv.getHope_author());
		System.out.println("출판사 : " + hv.getHope_publisher());
		System.out.println("희망하는 이유 : " + hv.getHope_content());
		System.out.println("-----------------------------------------------");
	}

	/**
	 * 희망도서 삭제할 글 번호 입력
	 * 
	 * @author 조애슬
	 * @since 2020-11-07
	 */
	int hopeRemoveNum() {
		while (true) {
			System.out.println("삭제 할 글번호를 입력하세요");
			int hopeNo = 0;
			try {
				hopeNo = sc.nextInt();

			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			return hopeNo;
		}
	}
	
	/**
	 * 희망도서등록메서드
	 * 
	 * @author 조애슬
	 * @since 2020.11.05
	 */
	private void hopeRegiView() {
		System.out.println("===============<희망도서 등록>================");
		Map<String, String> params = new HashMap<String, String>();// 희망도서입력을 위한
																	// map
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

		if (ihs.hopeListAdd(params)>0) {//희망도서 등록 db가서 등록한 후 int 받아서 옴
			showBanner("희망도서가 정상적으로 등록되었습니다.");
		}

		// return;
		hopeView();
	}
	
	/**
	 * 희망도서제목 입력
	 * 
	 * @author 조애슬
	 * @return String hope_name
	 * @since 2020-11-06
	 */
	String scanHopeName() {
		while (true) {
			System.out.println("희망하는 도서명을 입력하세요");
			Scanner input = new Scanner(System.in);
			String hope_name = input.nextLine();
			if(hope_name.length()==0){
				System.out.println("1글자 이상 입력하세요.\n");
				continue;
			} else if (hope_name.length() < 11) {
				return hope_name;
			} else {
				System.out.println("10글자 이내로 입력하세요");
				continue;
			}
		}
	}
	
	/**
	 * 희망도서작가 입력
	 * 
	 * @author 조애슬
	 * @return String hope_author
	 * @since 2020-11-06
	 */
	String scanHopeAuthor() {
		while (true) {
			Scanner sc = new Scanner(System.in);
			System.out.println("작가를 입력하세요");
			String hope_author = sc.nextLine();
			if(hope_author.length()==0){
				System.out.println("1글자 이상 입력하세요.\n");
				continue;
			} else if (hope_author.length() < 16) {
				return hope_author;
			} else {
				System.out.println("15글자 이내로 입력하세요");
				continue;
			}
		}
	}

	/**
	 * 희망도서 출판사 입력
	 * 
	 * @author 조애슬
	 * @return String hope_publisher
	 * @since 2020-11-06
	 */
	String scanHopePublisher() {
		while (true) {
			System.out.println("도서의 출판사를 입력하세요");
			Scanner input = new Scanner(System.in);
			String hope_publisher = input.nextLine();
			if(hope_publisher.length()==0){
				System.out.println("1글자 이상 입력하세요.\n");
				continue;
			} else if (hope_publisher.length() < 16) {
				return hope_publisher;
			} else {
				System.out.println("15글자 이내로 입력하세요");
				
				continue;
			}
		}
	}

	/**
	 * 희망이유 입력
	 * 
	 * @author 조애슬
	 * @return String hope_content
	 * @since 2020-11-06
	 */
	String scanHopeContent() {
		while (true) {
			System.out.println("희망하는 이유를 입력하세요");
			Scanner input = new Scanner(System.in);
			String hope_content = input.nextLine();
			if(hope_content.length()==0){
				System.out.println("1글자 이상 입력하세요.\n");
				continue;
			} else if (hope_content.length() < 101) {
				return hope_content;
			} else {
				System.out.println("100글자 이내로 입력하세요");
				continue;
			}
		}
	}
   
////////////////////////////////////////////마이페이지//////////////////////////////////////////////////
   /**
	 * 마이페이지
	 * @author 민태원
	 * @since 2020.11.04
	 */
	private void myPageView() {
		while(true) {
			showBanner("마이페이지");
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
				if(nowMember==null)	//탈퇴했으면
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
			showBanner("내 정보");
			//내 정보 출력 메서드
			MemberVO mv = ims.readMember(nowMember.getMem_id());
			printMyInfo(mv);
			System.out.println("-------------------------------------------------------");
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
					nowMember = null;
					return;
				}
				break;
			case 0:
				return;		//뒤로가기
			default : 
	        	 System.out.println("다시 입력해주세요");
	        	 continue;
			}
			if(ims.updateMember(myInfo)==1){
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
		System.out.println("생년월일 : "+mv.getMem_bir().substring(0,10));
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
		String result = null;
		System.out.print("새로운 비밀번호를 입력하세요 : ");
		String pw1 = sc.next();
		if(RegExClass.checkMem_pw(pw1)){
			System.out.print("한번더 입력하세요 : ");
			String pw2 = sc.next();
			if(pw1.equals(pw2)){
				result = aes.encrypt(pw1);
			}else{
				System.out.println("\n비밀번호가 다릅니다.\n");
			}
		}else{
			System.out.println("올바른 형식의 비밀번호가 아닙니다. (8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.)");
		}
		return result;
	}
	
	/**
	 * 전화번호 변경 메서드
	 * @return 형식에 맞는 전화번호이면 전화번호가 저장된 문자열 반환, 아니면 null 반환
	 * @author 민태원
	 * @since 2020.11.05
	 */
	private String updatePhone(){
		String tel = null;
		System.out.print("바꾸실 번호를 입력하세요 : ");
		tel = sc.next();
		if(!RegExClass.checkMem_tel(tel)){
			tel = null;
			System.out.println("올바른 형식의 전화번호가 아닙니다. (예-010-1111-1111)");
		}
		return tel;
	}
	
	/**
	 * 이메일 변경 메서드
	 * @return 형식에 맞는 이메일이면 이메일이 저장된 문자열 반환, 아니면 null 반환
	 * @author 민태원
	 * @since 2020.11.05
	 */
	private String updateEmail(){
		String email = null;
		System.out.print("바꾸실 이메일을 입력하세요 : ");
		email = sc.next();
		if(!RegExClass.checkMem_email(email)){
			System.out.println("올바른 형식의 이메일이 아닙니다. (영문자로 시작, 예- smile123@naver.com )");
			email = null;
		}
		return email;
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
		int result = 0;
		System.out.println("정말 탈퇴하시겠습니까? (Y/N)");
		String check = sc.next();
		if("Y".equals(check.toUpperCase())){
			List<RentalVO> myList = irts.readRentalList(nowMember.getMem_id());
			if(myList.size() > 0){
				System.out.println("대여중인 도서가 있습니다. 도서를 모두 반납해야 탈퇴가 가능합니다.");
				int myBook = myList.size();
				for(RentalVO rv : myList){
					BookVO bv = ibs.readBook(rv.getBook_id());
					System.out.println("대여중인 도서의 수 : " + myBook--);
					System.out.println("도서명 : " + bv.getBook_name());
					if(!returnBook(rv.getBook_id())){
						System.out.println("탈퇴가 취소되었습니다.");
						return result;
					}
				}
			}
			result = ims.deleteMember(mem_id);
		}
		return result;
	}

////대여	
	/**
	 * 대출목록
	 * @author 민태원
	 * @since 2020.11.06
	 */
	private void rentalListView() {
		while(true) {
			showBanner("나의 대여목록");
			//대출도서리스트 출력 메서드
			List<RentalVO> myList = printRentalList(nowMember.getMem_id());
			if(myList.size()==0){
				System.out.println("대여중인 도서가 없습니다.");
				System.out.println("[0]뒤로");
			}else{
				rentalListDetailView(myList);
				return;
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
			}else{
				System.out.println("다시 입력해주세요");
			}
		}
	}
	
	/**
    * 대출목록 내보내기 뷰
    * @author 조애슬
    * @since 2020-11-16
    */
   public void rentalListDetailView(List<RentalVO> myList){
		System.out.println("[1]반납하기 [2]대여목록 전송하기 [0]뒤로");
		int input = 0;
		try {
			input = sc.nextInt();
		} catch (Exception e) {
			System.out.println("숫자만 입력해주세요.");
			sc = new Scanner(System.in);
			return;
		}

		switch (input) {
		case 1:
			bookReturnView(myList);
			break;
		case 2:
			fileExport();
			break;
		case 0:
			return;
		default:
			break;
		}
   }
	
	/**
	 * 나의 대출도서목록 출력 메서드
	 * @param mem_id 나의 아이디
	 * @return 대여 정보를 리스트로 반환
	 * @author 민태원
	 * @since 2020.11.06
	 */
	private List<RentalVO> printRentalList(String mem_id){
		List<RentalVO> myList = irts.readRentalList(mem_id);
		int listNum = 1;
		for(RentalVO rv : myList){
			BookVO bv = ibs.readBook(rv.getBook_id());
			System.out.print("["+(listNum++)+"](도서명)"+bv.getBook_name()+"  ");
			System.out.print("(저자)"+bv.getBook_author()+"  ");
			System.out.println("(출판사)"+bv.getBook_publisher()+"  ");
			System.out.println("대여일 : " + rv.getRental_start().substring(0, 10));
			System.out.println("반납일 : " + rv.getRental_end().substring(0, 10));
			System.out.println("--------------------------------------------------------");
		}
		return myList;
	}
	
	/**
	 * 도서반납 뷰
	 * @author 민태원
	 * @param List<RentalVO>
	 **/
	public void bookReturnView(List<RentalVO> myList){
		while (true) {
			System.out.println("반납할 도서를 선택해 주세요.");
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력해주세요.");
				sc = new Scanner(System.in);
				continue;
			}
			if (input == 0) {
				return;
			} else if (0 < input && input <= myList.size()) {
				// 도서번호입력받아 반납 메서드
				returnBook(myList.get(input - 1).getBook_id());
				return;
			} else {
				System.out.println("다시 입력해주세요");
			}
		} 	
	}
	
	/**
	 * 대출목록 파일 내보내기 메서드
	 * @author 민태원
	 * @since 2020.11.17
	 */
	void fileExport(){
		while(true){
			System.out.println("[1]엑셀파일로 보내기(.xlsx) [2]PDF파일로 보내기(.pdf) [0]뒤로");
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			switch (input) {
			case 1:
				rentalExcelOut();
				return;
			case 2:
				rentalPdfOut();
				return;
			case 0:
				return;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	/**
	 * 대여목록 출력하는 뷰
	 * @author 조애슬
	 * @since 2020-11-16
	 */
	void rentalExcelOut() {
		System.out.println("원하는 파일명을 입력하세요");
		String rPath = sc.next();
		Map<String, String> params = new HashMap<String, String>();
		params.put("mem_id", nowMember.getMem_id());
		params.put("rPath", rPath);
		irts.rentalExcelout(params);
		
		//이메일 보내기
		String title = "나의 대여 목록(엑셀)";
		String content = "나의 모든 대여 도서 목록입니다.";
		String email = nowMember.getMem_email();
		String filePath = ".\\file\\"+rPath+".xlsx";
		Email mail = new Email(title, content, email, filePath);
		mail.sendNaver();
	}
	
	/**
	 * 대여목록 pdf파일로 전송하기
	 * @author 민태원
	 * @since 2020.11.17
	 */
	void rentalPdfOut(){
		System.out.println("원하는 파일명을 입력하세요");
		String rPath = sc.next();
		Map<String, String> params = new HashMap<>();
		params.put("mem_id", nowMember.getMem_id());
		params.put("rPath", rPath);
		irts.createRentalPdf(params);
		System.out.println("파일변환 성공!");
		
		//이메일 보내기
		String title = "나의 대여 목록(PDF)";
		String content = "나의 모든 대여 도서 목록입니다.";
		String email = nowMember.getMem_email();
		String filePath = ".\\file\\"+rPath+".pdf";
		Email mail = new Email(title, content, email, filePath);
		mail.sendNaver();
	}
	
///반납	
	/**
	 * 도서의 아이디를 입력받아 반납하는 메서드
	 * 출력 : "반납하시겠습니까? (y/n)" => y면 반납 / n면 return
	 * @param book_id 반납할 도서의 id
	 * @author 민태원
	 * @since 2020.11.06
	 */
	boolean returnBook(int book_id){
		boolean result = false;
		//rentalList에서 book을 찾고 list에서 삭제
		System.out.println("반납하시겠습니까? (Y/N)");
		String check = sc.next();
		if("Y".equals(check.toUpperCase())){
			//대여테이블에서 삭제
			if(irts.deleteRental(book_id)==1){
				System.out.println("\n도서가 반납되었습니다.\n");
				result = true;
			}else{
				System.out.println("반납 실패..");
			}
			//반납할 도서를 예약한 사람이 있는지 확인
			ReserveVO rsv = irsvs.readReserveVO(book_id);
			if(rsv != null){
				//있으면 가장 먼저 예약한 사람을 대여테이블에 추가
				Map<String, Object> newRsv = new HashMap<>();
				newRsv.put("mem_id", rsv.getMem_id());
				newRsv.put("book_id", rsv.getBook_id());
				//대여가능일
				newRsv.put("start_date", getDate());
				//반납일
			    Map<String, Object> dayInfo = new HashMap<>();
			    dayInfo.put("day", getDate());
			    dayInfo.put("addDay", 14);
				newRsv.put("end_date", getEndDate(dayInfo));
				
				irts.createRental(newRsv);
				
				//대출된 도서는 예약테이블에서 삭제
				irsvs.deleteReserve(newRsv);
		   	}else {
		   		// 예약자가 없으면 도서를 대여가능으로 변경
		   		BookVO bv = ibs.readBook(book_id);
		   		bv.setBook_state("T");
		   		ibs.updateBook(bv);
		   	}
		}else{
			System.out.println("반납 취소");
		}
		return result;
	}
	
///예약	
	/**
	 * 예약목록
	 * @author 민태원
	 * @since 2020.11.06
	 */
	private void rsvLsitView() {
		while(true) {
			showBanner("나의 예약목록");
			//예약 도서 목록 출력
			List<ReserveVO> rsvList = printBookList(nowMember.getMem_id());
			
			if(rsvList.size()==0){
				System.out.println("예약중인 도서가 없습니다.");
				System.out.println("[0]뒤로");
			}else{
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
				rsvCancel(rsvList.get(input-1).getBook_id());
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
	 * @since 2020.11.12
	 */
	private List<ReserveVO> printBookList(String mem_id){
		List<ReserveVO> rsvList = irsvs.readReserveList(mem_id);
		// [번호]도서명, 저자, 출판사, 대여가능일자
		int bookNum = 1;
		for(ReserveVO rsv : rsvList){
			BookVO bv = ibs.readBook(rsv.getBook_id());
			System.out.print("["+(bookNum++)+"](도서명)"+bv.getBook_name()+"  ");
			System.out.print("(저자)"+bv.getBook_author()+"  ");
			System.out.println("(출판사)"+bv.getBook_publisher()+"  ");
			System.out.println("--------------------------------------------------------");
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
	void rsvCancel(int book_id){
		System.out.println("예약을 취소하시겠습니까? (Y/N)");
		String check = sc.next();
		if("Y".equals(check.toUpperCase())){
			Map<String, Object> map = new HashMap<>();
			map.put("mem_id", nowMember.getMem_id());
			map.put("book_id", book_id);
			if(irsvs.deleteReserve(map)==1){
				System.out.println("\n예약이 취소되었습니다.\n");
				return;
			}
		}
		System.out.println("취소를 취소한다.\n");
	}
	
/////////////////////////////////////////////////////////////////////관리자 페이지//////////////////////////////////////////////////
	/**
	 * 관리자메인 메서드
	 * 
	 * @author 김태규
	 * @since 2020.11.04
	 */
	public void adminView() {
		while (true) {
			showBanner("관리자 페이지");
			System.out.println("[1] 게시판 관리");
			System.out.println("[2] 도서 관리");
			System.out.println("[3] 회원 관리");
			System.out.println("[0] 로그아웃");
			System.out.print("번호를 입력하시오 : ");

			int num = 0;
			try {
				num = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못입력하였습니다");
				sc = new Scanner(System.in);
				continue;
			}

			switch (num) {
			case 0:
				//로그아웃하면 로그인화면으로 돌아가기
				return;
			case 1:
				//게시관 관리view을 보여주는 메소드
				noiceMainView();
				break;
			case 2:
				//책 관리 view을 보여주는 메소드
				bookView();
				break;
			case 3:
				//회원관리view을 보여주는 메소드
				memberView();
				break;
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
			showBanner("게시판 관리");
			System.out.println("[1] 공지사항");
			System.out.println("[2] 희망도서목록");
			System.out.println("[0] 뒤로가기");
			System.out.print("번호를 입력하시오 : ");
			int num = 0;
			try {
				num = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못입력하였습니다");
				sc = new Scanner(System.in);
				continue;
			}
			switch (num) {
			case 0:
				//관리자view을 보여주는 메소드
				return;
			case 1:
				//공지사항view을 보여주는 메소드
				noiceView();
				break;
			case 2:
				//희망도서목록view을 보여주는 메소드
				hopeBookView();
				break;
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
			showBanner("공지사항");
			//공지리스트 띄워주는 메소드
			noticeList();

			System.out.println("[1] 공지 상세 조회");
			System.out.println("[2] 공지 추가");
			System.out.println("[3] 공지 삭제");
			System.out.println("[0] 뒤로가기");
			System.out.print("번호를 입력하시오 : ");
			int num = 0;
			try {
				num = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못입력하였습니다");
				sc = new Scanner(System.in);
				continue;
			}
			switch (num) {
			case 0:
				//게시판view을 보여주는 메소드
				return;
			case 1:
				//공지읽은 리스트의 번호를 받아서 출력하는 메소드
				System.out.print("읽을 공지의 번호 : ");
				int num2 = 0;
				try {
					num2 = sc.nextInt();
				} catch (Exception e) {
					System.out.println("잘못입력하였습니다.");
					sc = new Scanner(System.in);
					continue;
				}
				NoticeVO nv = ins.openNoDetail(num2);
				if(nv != null){
					printNoticeDeatil(num2);
				}else{
					System.out.println("해당 번호의 공지글이 없습니다.");
				}
				break;
			case 2: 
				//공지추가 메소드
				noticeAdd();
				break;
			case 3:
				//공지삭제 메소드 삭제할 공지의 번호를 받아서 공지를 판단
				System.out.print("삭제할 공지의 번호 : ");
				int num3 = 0;
				try {
					num3 = sc.nextInt();
				} catch (Exception e) {
					System.out.println("잘못입력하였습니다");
					sc = new Scanner(System.in);
					continue;
				}
				if (ins.deleteNotice(num3)==1) {
					System.out.println("공지가 삭제되었습니다.");
				}else{
					System.out.println("삭제 실패..");
				}
				break;
			default:
				System.out.println("잘못입력하였습니다");
			}
		}
	}

	/**
	 * 공지사항 출력
	 * @return 공지 전체 목록
	 * @author 민태원
	 * @since 2020.11.12
	 */
	private List<NoticeVO> noticeList() {
		List<NoticeVO> list = ins.noticeList();
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i).getNotice_no()+". ");
			System.out.println(list.get(i).getNotice_title());
			System.out.println("------------------------------------------------------------------");
		}
		return list;
	}
	
	/**
	 * 공지사항 상세보기
	 * @param notice_no 공지 번호
	 * @author 민태원
	 * @since 2020.11.12 
	 */
	private void printNoticeDeatil(int notice_no) {
		while(true){
			showBanner("공지사항 상세보기");
			NoticeVO nv = ins.openNoDetail(notice_no);
			if (nv != null) {
				noticeDetail(nv);
				System.out.println("[0] 뒤로가기");
				int input = 0;
				try {
					input = sc.nextInt();
				} catch (Exception e) {
					System.out.println("숫자만 입력하세요");
					sc = new Scanner(System.in);
					continue;
				}
				if(input == 0){
					return;
				}
			}else{
				System.out.println("해당 글이 없습니다.");
			}
		}
	}

	/**
	 * 공지를 추가한다.
	 * @author 김태규
	 */
	void noticeAdd() {
		NoticeVO nv = new NoticeVO();
		sc = new Scanner(System.in);
		System.out.println("공지 타이틀을 입력하세요");
		String nt = sc.nextLine();
		System.out.println("공지 내용을 입력하세요");
		String nc = sc.nextLine();
		
		nv.setNotice_title(nt);
		nv.setNotice_content(nc);
		nv.setNotice_date(getDate());
		nv.setAdmin_id(adMember.getadmin_id());
		
		if(ins.createNotice(nv)==1){
			System.out.println("공지가 추가되었습니다.");
		}else{
			System.out.println("공지 추가 실패");
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
			//희망 도서 메소드 번호,책이름,저자,출판사 등을 보여준다
			showBanner("희망도서 목록");
			adminHopeView();

			System.out.println("[1] 희망도서 승인 ");
			System.out.println("[2] 희망도서 부결 ");
			System.out.println("[0] 뒤로가기");
			System.out.print("번호를 입력하시오 : ");
			int num = 0;
			try {
				num = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못입력하였습니다");
				sc = new Scanner(System.in);
				continue;
			}
			switch (num) {
			case 0:
				//게시판 관리로 되돌아 가기 메소드
				return;
			case 1:
				hopeBookAdd();
				break;
			case 2:
				hopeBookDelete();
				break;
			default:
				System.out.println("잘못입력하였습니다");
			}
		}
	}
	
	/**
	 * 희망도서 목록 출력 메서드
	 * @author 민태원
	 * @since 2020.11.13
	 */
	void adminHopeView(){
		List<HopeVO> hl = ihs.hopeList(); //희망도서출력.. list로 받아야
		if(hl.size()==0){
			System.out.println("글이 없습니다.");
			System.out.println("-----------------------------------------------");
		}else{
			for (int i = 0; i < hl.size(); i++) {
				System.out.println("글번호 : " + hl.get(i).getHope_id());
				System.out.println("작성자 : " + hl.get(i).getMem_id());
				System.out.println("책제목 : " + hl.get(i).getHope_name());
				System.out.println("작가 : " + hl.get(i).getHope_author());
				System.out.println("출판사 : " + hl.get(i).getHope_publisher());
				System.out.println("희망하는 이유 : " + hl.get(i).getHope_content());
				System.out.println("-----------------------------------------------");
			}
		}
	}
	
	/**
	 * @author 김태규
	 */
	private void hopeBookAdd() {
		//도서 승인 메소드 승인 도서의 번호 번호를 받아 메소드안에서 추가한다
		System.out.print("승인 도서의 번호 : ");
		int num2 = 0;
		try {
			num2 = sc.nextInt();
		} catch (Exception e) {
			System.out.println("잘못입력하였습니다");
			return;
		}
		if(num2!=0){
			approveHope(num2);
		}
	}
	
	/**
	 * 희망도서 승인 메서드
	 * @param hope_id 승인할 희망도서 아이디
	 * @author 민태원
	 * @since 2020.11.13
	 */
	void approveHope(int hope_id){
		// 입력한 번호의 희망도서 번호가 존재하는지 체크
		HopeVO hv = ihs.hopeDetailView(hope_id);
		if(hv != null){
			System.out.println("글이존재함");
			// BookVO 생성후 추가
			BookVO bv = new BookVO();
			bv.setBook_name(hv.getHope_name());
			bv.setBook_author(hv.getHope_author());
			bv.setBook_publisher(hv.getHope_publisher());
			bv.setBook_LGU(1);	// 분류코드 처리해야함
			bv.setBook_summary("줄거리"); // 줄거리 처리해야함
			
			if(ibs.createBook(bv)==1){
				// HopeVO에서 삭제한다 / 성공하면 승인되었습니다 출력
				if (ihs.deleteHope(hope_id)==1){
					System.out.println("희망도서가 승인되었습니다.");
				}else{
					System.out.println("승인 실패(희망도서 삭제 실패)");
				}
			}else{
				System.out.println("승인 실패(도서 추가 실패)");
			}
		}else{
			System.out.println("해당 번호의 도서가 존재하지 않습니다.");
		}
	}
	
	/**
	 * 희망도서 부결 메서드
	 * @author 민태원
	 * @since 2020.11.13
	 */
	void hopeBookDelete(){
		//도서 부결 메소드 부결 도서의 번호를 받아서 메소드안에서 리스트를삭제
		System.out.print("부결 도서의 번호 : ");
		int num3 = 0;
		try {
			num3 = sc.nextInt();
		} catch (Exception e1) {
			System.out.println("잘못입력하였습니다");
			return;
		}
		if (ihs.deleteHope(num3) == 1) {
			System.out.println("희망도서가 부결되었습니다.");
		} else {
			System.out.println("삭제 실패..");
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
			showBanner("도서 관리");
			//전체 도서리스트 출력 메소드
			bookList();
			System.out.println("[1] 도서 상세 정보");
			System.out.println("[2] 도서 등록");
			System.out.println("[3] 도서목록 전송하기");
			System.out.println("[0] 뒤로가기");
			System.out.print("번호를 입력하시오 : ");
			int num = 0;
			try {
				num = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못입력하였습니다");
				sc = new Scanner(System.in);
				continue;
			}
			switch (num) {
			case 0:
				//관리자 메인 화면 메소드 관리자 메인 화면 메소드 돌아가기
				return;
			case 1:
				//도서상세 출력 
				System.out.print("도서 번호 : ");
				int book_no = 0;
				try {
					book_no = sc.nextInt();
				} catch (Exception e) {
					System.out.println("잘못입력하였습니다.");
					sc = new Scanner(System.in);
					continue;
				}
				BookVO bv = ibs.readBook(book_no);
				if (bv == null) {
					System.out.println("해당 번호가 없습니다");
				} else {
					bookRead(book_no);
				}
				break;
			case 2:
				//도서 등록 뷰
				bookAddView();
				break;
			case 3:
				//도서목록 전송 메소드
				bookOut();
				break;
			default:
				System.out.println("잘못입력하였습니다");
			}
		}
	}

	/**
	 * 도서리스트 출력
	 * 
	 * @author 김태규
	 * @return
	 */
	private List<BookVO> bookList() {
		while(true){
			List<BookVO> list = ibs.bookList();
			for (int i = 0; i < list.size(); i++) {
				System.out.println("["+list.get(i).getBook_id()+"]");
				System.out.println("제목 : " + list.get(i).getBook_name());
				System.out.println("작가 : " + list.get(i).getBook_author());
				System.out.println("-----------------------------------------------");
			}
			return list;
		}
	}
	
	/**
	 * 도서 상세 출력
	 * 
	 * @author 김태규
	 */
	private void bookRead(int book_id) {
		while (true) {
			showBanner("도서 상세 상세보기");
			BookVO bv = ibs.readBook(book_id);
			if (bv != null) {
				bookDetail(bv);
				System.out.println("[1] 도서 삭제  [0] 뒤로가기");
				int input = 0;
				try {
					input = sc.nextInt();
				} catch (Exception e) {
					System.out.println("숫자만 입력하세요");
					sc = new Scanner(System.in);
					continue;
				}
				switch(input){
				case 0:
					return;
				case 1:
					bookDelete(book_id);
					return;
				default:
					System.out.println("잘못된 입력입니다.");
				}
			} else {
				System.out.println("해당 도서가 없습니다.");
				return;
			}
		}

	}
	
	/**
	 * 도서 상세 출력
	 * 
	 * @author 김태규
	 */
	private void bookDetail(BookVO bv) {
		System.out.println("도서번호 : " + bv.getBook_id());
		System.out.println("도서명 : " + bv.getBook_name());
		System.out.println("작가 : " + bv.getBook_author());
		System.out.println("내용 : " + bv.getBook_summary());
		System.out.println("출판사: " + bv.getBook_publisher());
		System.out.println("분류 : " + iblgus.readBookLGU(bv.getBook_LGU()).getBook_theme());
		System.out.println("-----------------------------------------------");
	}
	
	/**
	 * 도서 등록
	 * 
	 * @author 김태규
	 * @since 2020.11.05
	 */
	private void bookAddMethod() {
		BookVO bv = new BookVO();
		System.out.print("도서 제목 : ");
		String bn = sc.next();
		System.out.print("도서 작가 : ");
		String ba = sc.next();
		List<BookLGUVO> list = lguList();
		System.out.print("도서 분류 : ");
		int bl = 0;
		while(true){
			try {
				bl = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			if(0< bl && bl <= list.size()){
				break;
			}else{
				System.out.println("잘못된 입력입니다.");
			}
		}
		System.out.print("도서 출판사 : ");
		String bp = sc.next();
		System.out.println("도서 내용 : ");
		String bs = sc.next();

		bv.setBook_name(bn);
		bv.setBook_author(ba);
		bv.setBook_summary(bs);
		bv.setBook_publisher(bp);
		bv.setBook_LGU(bl);

		if (ibs.createBook(bv) == 1) {
			System.out.println("도서가 정상적으로 등록되었습니다.");
		} else {
			System.out.println("도서가 등록실패 되었습니다.");
		}
	}
	
	/**
	 * 도서 삭제 메서드
	 * @param book_id 삭제할 도서의 아이디
	 * @author 김태규
	 */
	private void bookDelete(int book_id) {
		//삭제할 도서를 대여중인 사람이 있을 경우
		if(irts.readRentalVO(book_id)!=null){
			System.out.println("대여중인 도서입니다.");
			return;
		}else{
			System.out.println("정말 삭제하시겠습니까? (Y/N)");
			String input = sc.next();
			if(input.toUpperCase().equals("Y")){
				if (ibs.deleteBook(book_id) == 1) {
					System.out.println("도서가 삭제되었습니다.");
				} else {
					System.out.println("삭제 실패..");
				}
			}else{
				System.out.println("삭제 취소");
			}
		}
	}
	
	/**
	 * 도서목록 전송 메서드
	 * @author 민태원
	 * @since 2020.11.17
	 */
	void bookOut(){
		while(true){
			System.out.println("[1]엑셀파일로 전송하기(.xlsx) [2]PDF파일로 전송하기(.pdf) [0]뒤로");
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			switch (input) {
			case 1:
				bookExcelout();
				return;
			case 2:
				bookPdfOut();
				return;
			case 0:
				return;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	/**
	 * 도서리스트 엑셀출력 메서드
	 * @author 조애슬
	 * @since 2020-11-16
	 */
	void bookExcelout(){
		System.out.println("원하는 파일명을 입력하세요");
		String fname = sc.next();
		ibs.bookExcelout(fname);
		
		//이메일로 전송
		String title = "전체 도서 목록(엑셀 파일)";
		String content = "전체 도서 목록을 첨부합니다.";
		String email = adMember.getAdmin_email();
		String filePath = ".\\file\\"+fname+".xlsx";
		Email mail = new Email(title, content, email, filePath);
		mail.sendNaver();
	}
	
	/**
	 * 도서리스트 pdf출력 메서드
	 * @author 민태원
	 * @since 2020.11.17
	 */
	void bookPdfOut(){
		System.out.println("원하는 파일명을 입력하세요");
		String fname = sc.next();
		ibs.bookPdfOut(fname);
		
		//이메일로 전송
		String title = "전체 도서 목록(엑셀 파일)";
		String content = "전체 도서 목록을 첨부합니다.";
		String email = adMember.getAdmin_email();
		String filePath = ".\\file\\"+fname+".pdf";
		Email mail = new Email(title, content, email, filePath);
		mail.sendNaver();
	}
	
	/**도서등록뷰
	 * 
	 * @author 조애슬
	 */
	void bookAddView(){
		while (true) {
			System.out.println("[1] 도서 등록 [2] 도서 한번에 등록 (.xlsx) [0] 뒤로");
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요");
				sc = new Scanner(System.in);
				continue;
			}
			switch(input){
			case 0:
				return;
			case 1:
				bookAddMethod();
				return;
			case 2:
				bookExcelAdd();
				return;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	/**
    * 도서목록 엑셀파일로 한번에 추가하기
    */
   void bookExcelAdd(){
		while (true) {
			showBanner("도서목록 불러오기");
			// 전체 도서리스트 출력 메소드
			bookList();
			System.out.println("[1] 도서목록 불러오기(.xlsx)");
			System.out.println("[0] 뒤로가기");
			System.out.print("번호를 입력하시오 : ");
			int num = 0;
			try {
				num = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못입력하였습니다");
				sc = new Scanner(System.in);
				continue;
			}
			switch (num) {
			case 0:
				//
				return;
			case 1:
				// 도서목록 출력
				System.out.println("불러올 경로를 입력하세요 ('\\'는 '\\\'로 입력해주세요)");
				String bPath = sc.next();
				ibs.bookExcelAdd(bPath);
				break;
			default:
				System.out.println("잘못입력하였습니다");
			}
		}
   }

//////////// 회원리스트
	/**
	 * 회원 관리 뷰
	 * 
	 * @author 김태규
	 * @since 2020.11.04
	 */
	public void memberView() {
		while (true) {
			showBanner("회원 관리");
			System.out.println("[1] 회원리스트 확인");
			System.out.println("[2] 블랙리스트 확인");
			System.out.println("[3] 회원리스트 전송하기");			
			System.out.println("[0] 뒤로가기");
			System.out.print("번호를 입력하시오 : ");
			int num = 0;
			try {
				num = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못입력하였습니다");
				sc = new Scanner(System.in);
				continue;
			}
			switch (num) {
			case 0:
				//관리자 메인 화면 메소드 관리자 메인 화면 메소드 돌아가기
				return;
			case 1:
				//전체 회원리스트 뷰 메소드
				memberListView();
				break;
			case 2:
				//전체 블랙리스트 뷰 메소드
				blackListView();
				break;
			case 3:
				//회원리스트 출력메서드
				memberOut();
				break;
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
		while (true) {
			//전체 회원리스트 출력 메소드
			showBanner("전체 회원 목록");
			memberList();

			System.out.println("[1] 블랙리스트 등록 ");
			System.out.println("[0] 뒤로가기");
			System.out.print("번호를 입력하시오 : ");
			int num = 0;
			try {
				num = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못입력하였습니다");
				sc = new Scanner(System.in);
				continue;
			}
			switch (num) {
			case 0:
				//회원 관리 화면 돌아가기
				return;
			case 1: 
				//블랙리스트에 추가 메소드
				createBlackList();
				break;
			default:
				System.out.println("잘못입력하였습니다");
				break;
			}
		}
	}
	
	/**
	 * 회원 리스트 뷰
	 * 
	 * @author 김태규
	 * @since 2020.11.04
	 */
	private void memberList() {
		List<MemberVO> mv = ims.memberList();
		for (int i = 0; i < mv.size(); i++) {
			System.out.println("회원ID\t[" + mv.get(i).getMem_id() + "]");
			System.out.println("이름\t[" + mv.get(i).getMem_name() + "]");
			System.out.println("비밀번호\t[" + mv.get(i).getMem_pw() + "]");
			System.out.println("생년월일\t[" + mv.get(i).getMem_bir().substring(0,10) + "]");
			System.out.println("이메일\t[" + mv.get(i).getMem_email() + "]");
			System.out.println("전화번호\t[" + mv.get(i).getMem_tel() + "]");
			System.out.println("대출횟수\t[" + mv.get(i).getRent_count() + "]");
			System.out.println("==============================");
		}
	}
	
	/**
	 * 회원리스트 출력
	 * @author 민태원
	 * @since 2020.11.17
	 */
	void memberOut(){
		while(true){
			System.out.println("[1]엑셀파일로 전송하기(.xlsx) [2]PDF파일로 전송하기(.pdf) [0]뒤로");
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			switch (input) {
			case 1:
				memberExcelOut();
				return;
			case 2:
				memberPdfOut();
				return;
			case 0:
				return;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	/**
	 * 회원리스트 엑셀출력
	 * @author 조애슬
	 */
	void memberExcelOut(){
		System.out.println("원하는 파일명을 입력하세요");
		String mname = sc.next();
		ims.memberExcelout(mname);

		// 이메일 보내기
		String title = "모든 회원 목록(엑셀)";
		String content = "전체 도서 목록 첨부합니다.";
		String email = adMember.getAdmin_email();
		String filePath = ".\\file\\" + mname + ".xlsx";
		Email mail = new Email(title, content, email, filePath);
		mail.sendNaver();
	}
	
	/**
	 * 회원리스트 pdf출력
	 * @author 민태원
	 * @since 2020.11.17
	 */
	void memberPdfOut(){
		System.out.println("원하는 파일명을 입력하세요");
		String fname = sc.next();
		ims.memberPdfOut(fname);

		// 이메일 보내기
		String title = "모든 회원 목록(PDF)";
		String content = "전체 도서 목록 첨부합니다.";
		String email = adMember.getAdmin_email();
		String filePath = ".\\file\\" + fname + ".pdf";
		Email mail = new Email(title, content, email, filePath);
		mail.sendNaver();
	}

	/**
	 * 블랙리스트 추가
	 * @param num2
	 * @return
	 * @author 김태규
	 */
	private void createBlackList() {
		System.out.print("블랙 리스트에 추가될 회원 아이디 : ");
		String id = null;
		try {
			id = sc.next();
		} catch (Exception e) {
			System.out.println("잘못입력하였습니다");
			sc = new Scanner(System.in);
			return;
		}
		if (ibls.idcheack(id)!=1) {
			BlackListVO bv = new BlackListVO();

			bv.setMem_id(id);
			bv.setBlack_day(getDate());
			Map<String, Object> day = new HashMap<>();
			day.put("day", getDate());
			day.put("addDay", 7);
			bv.setBlack_end(getEndDate(day));
			bv.setAdmin_id(adMember.getadmin_id());

			if (ibls.createBlackList(bv) == 1) {
				System.out.println("블랙리스트에 추가되었습니다.");
				Map<String,String> myInfo = new HashMap<>();
				myInfo.put("mem_id", id);
				myInfo.put("isactivate", "F");
				ims.updateMember(myInfo);
			} else {
				System.out.println("블랙리스트 추가 실패..");
			}
		}else{
			System.out.println("이미 블랙리스트에 등록되었거나 없는 아이디입니다.");
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
			showBanner("블랙리스트 목록");
			blackListList();

			System.out.println("[1] 블랙리스트 삭제 ");
			System.out.println("[0] 뒤로가기");
			int num = 0;
			try {
				num = sc.nextInt();
			} catch (Exception e) {
				System.out.println("잘못입력하였습니다");
				sc = new Scanner(System.in);
				continue;
			}
			switch (num) {
			case 0:
				//회원 관리 화면 돌아가기
				return;
			case 1:
				//블랙리스트에서 삭제 메소드
				deleteBlackList();
				break;
			default:
				System.out.println("잘못입력하였습니다");
			}
		}
	}

	/**
	 * 블랙리스트 목록 조회 메서드
	 * @return
	 * @author 김태규
	 */
	private void blackListList() {
		List<BlackListVO> list = ibls.blackList();
		for (int i = 0; i < list.size(); i++) {
			System.out.println("[" + list.get(i).getBlack_id() + "]");
			System.out.println("회원 아이디 : " + list.get(i).getMem_id());
			System.out.println("등록일 : " + list.get(i).getBlack_day().substring(0,10));
			System.out.println("종료일 : " + list.get(i).getBlack_end().substring(0,10));
			System.out.println("==============================");
		}
	}
	
	/**
	 * 블랙리스트에서 삭제 메서드
	 * @author 김태규
	 * @since 2020.11.13
	 */
	private void deleteBlackList(){
		System.out.print("삭제할 블랙리스트의 번호 : ");
		int black_id = 0;
		try {
			black_id = sc.nextInt();
		} catch (Exception e) {
			System.out.println("숫자만 입력하세요.");
			sc = new Scanner(System.in);
			return;
		}
		BlackListVO blv = ibls.readBlackList(black_id);
		if(blv!=null){
			Map<String,String> myInfo = new HashMap<>();
			myInfo.put("mem_id", blv.getMem_id());
			myInfo.put("isactivate", "T");
			if (ibls.blackDeltle(black_id) == 1) {
				System.out.println("블랙리스트에서 삭제되었습니다.");
				ims.updateMember(myInfo);
			}
		}else {
			System.out.println("아이디를 찾을 수 없습니다.");
		}
	}
	
	
	
/////////////////////////////////////////////////////게임///////////////////////////////////////	
	private void gameView(){
		while(true){
			showBanner("게임 선택");
			System.out.println("[1]JJuruButton");
			System.out.println("[2]BlackJJu");
			System.out.println("[3]JJURUMABLE");
			System.out.println("[4]JJutNori");
			System.out.println("[0]뒤로");
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			switch (input) {
			case 1:
				MiniGame.startGame();
				break;
			case 2:
				BlackJack bj = BlackJack.getInstance();
				bj.GameStart();
				break;
			case 3:
				JJurumable.startGame();
				break;
			case 4:
				Yutnori nori = Yutnori.getInstance();
				nori.startGame();
				break;
			case 0:
				return;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	
	
	
	
}