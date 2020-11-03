package package_Database;

import java.util.*;

import package_VO.*;

public class Database {
	private List<AdminVO> 		adminList 		= new ArrayList<AdminVO>();
	private List<MemberVO> 		memberList		= new ArrayList<MemberVO>();
	private List<BookVO>        bookList      	= new ArrayList<BookVO>();
	private List<BookLGUVO>     bookLGUList     = new ArrayList<BookLGUVO>();
	private List<NoticeVO> 		noticeList 		= new ArrayList<NoticeVO>();
	private List<BlackListVO>	backlist 		= new ArrayList<BlackListVO>();
	private List<HopeVO> 		hopeList 		= new ArrayList<HopeVO>();
	private List<RentalVO> 		rentalList 		= new ArrayList<RentalVO>();
	
	
	private static Database database = new Database();
	
	public static Database getDatabase() {
		return database;
	}
	
	// 관리자 목록 초기화
	{
		AdminVO admin = new AdminVO();
		admin.setadmin_id("admin");
		admin.setadmin_pw("a1234");
		adminList.add(admin);
	}
		
	// 회원정보 목록 초기화
   {
      MemberVO member1 = new MemberVO();
      member1.setMem_id("abcd01");
      member1.setMem_name("오지란");
      member1.setMem_pw("1234");
      member1.setMem_bir("1980-01-12");
      member1.setMem_email("abcd01@naver.com");
      member1.setMem_tel("010-1234-5678");
      member1.setRent_count(10);
      memberList.add(member1);
      
      MemberVO member2 = new MemberVO();
      member2.setMem_id("qwer09");
      member2.setMem_name("설마음");
      member2.setMem_pw("8546");
      member2.setMem_bir("1999-12-07");
      member2.setMem_email("qwer90@naver.com");
      member2.setMem_tel("010-5555-4678");
      member2.setRent_count(50);
      memberList.add(member2);
      
      MemberVO member3 = new MemberVO();
      member3.setMem_id("nex1032");
      member3.setMem_name("김미소");
      member3.setMem_pw("5462");
      member3.setMem_bir("1987-05-01");
      member3.setMem_email("nex1032@gamil.com");
      member3.setMem_tel("010-4468-1957");
      member3.setRent_count(77);
      memberList.add(member3);
      
      MemberVO member4 = new MemberVO();
      member4.setMem_id("wow7777");
      member4.setMem_name("이영옥");
      member4.setMem_pw("57536");
      member4.setMem_bir("2020-01-05");
      member4.setMem_email("wow7777@gmail.com");
      member4.setMem_tel("010-9999-8542");
      member4.setRent_count(2);
      memberList.add(member4);
      
      MemberVO member5 = new MemberVO();
      member5.setMem_id("number1");
      member5.setMem_name("이영준");
      member5.setMem_pw("1111");
      member5.setMem_bir("1850-03-21");
      member5.setMem_email("number1@naver.com");
      member5.setMem_tel("010-7895-1325");
      member5.setRent_count(1300);
      memberList.add(member5);
      
      MemberVO member6 = new MemberVO();
      member6.setMem_id("yung22");
      member6.setMem_name("이성연");
      member6.setMem_pw("4658sdq");
      member6.setMem_bir("1982-09-09");
      member6.setMem_email("yung22@naver.com");
      member6.setMem_tel("010-0545-6489");
      member6.setRent_count(135);
      memberList.add(member6);
      
      MemberVO member7 = new MemberVO();
      member7.setMem_id("sum09054");
      member7.setMem_name("박유식");
      member7.setMem_pw("qad656");
      member7.setMem_bir("1988-08-23");
      member7.setMem_email("sum09054");
      member7.setMem_tel("010-4562-7893");
      member7.setRent_count(465);
      memberList.add(member7);
      
      MemberVO member8 = new MemberVO();
      member8.setMem_id("han6666");
      member8.setMem_name("고귀남");
      member8.setMem_pw("jhan421");
      member8.setMem_bir("1988-04-30");
      member8.setMem_email("han6666@naver.com");
      member8.setMem_tel("010-9635-1247");
      member8.setRent_count(400);
      memberList.add(member8);
      
      MemberVO member9 = new MemberVO();
      member9.setMem_id("jia76");
      member9.setMem_name("김지아");
      member9.setMem_pw("13d5w3");
      member9.setMem_bir("1976-02-28");
      member9.setMem_email("joa76@naver.com");
      member9.setMem_tel("010-4432-3391");
      member9.setRent_count(190);
      memberList.add(member9);
      
      MemberVO member10 = new MemberVO();
      member10.setMem_id("sheep133");
      member10.setMem_name("양철");
      member10.setMem_pw("sheep99");
      member10.setMem_bir("1999-12-26");
      member10.setMem_email("sheep@naver.com");
      member10.setMem_tel("010-3333-9987");
      member10.setRent_count(80);
      memberList.add(member10);
   }
	
	
	   
	//도서분류 목록 초기화
   {
	   BookLGUVO bl1 = new BookLGUVO();
	   bl1.setBook_LGU("NO");
	   bl1.setBook_theme("소설");
	   bookLGUList.add(bl1);
	   
	   BookLGUVO bl2 = new BookLGUVO();
	   bl2.setBook_LGU("PO");
	   bl2.setBook_theme("시");
	   bookLGUList.add(bl2);
	   
	   BookLGUVO bl3 = new BookLGUVO();
	   bl3.setBook_LGU("ES");
	   bl3.setBook_theme("에세이");
	   bookLGUList.add(bl3);
	   
	   BookLGUVO bl4 = new BookLGUVO();
	   bl4.setBook_LGU("EC");
	   bl4.setBook_theme("경제");
	   bookLGUList.add(bl4);
	   
	   BookLGUVO bl5 = new BookLGUVO();
	   bl5.setBook_LGU("IM");
	   bl5.setBook_theme("자기계발");
	   bookLGUList.add(bl5);
	   
	   BookLGUVO bl6 = new BookLGUVO();
	   bl6.setBook_LGU("HE");
	   bl6.setBook_theme("역사");
	   bookLGUList.add(bl6);
	   
	   BookLGUVO bl7 = new BookLGUVO();
	   bl7.setBook_LGU("PL");
	   bl7.setBook_theme("정치");
	   bookLGUList.add(bl7);
	   
	   BookLGUVO bl8 = new BookLGUVO();
	   bl8.setBook_LGU("AR");
	   bl8.setBook_theme("예술");
	   bookLGUList.add(bl8);
	   
	   BookLGUVO bl9 = new BookLGUVO();
	   bl9.setBook_LGU("SI");
	   bl9.setBook_theme("과학");
	   bookLGUList.add(bl9);
	   
	   BookLGUVO bl10 = new BookLGUVO();
	   bl10.setBook_LGU("IT");
	   bl10.setBook_theme("컴퓨터");
	   bookLGUList.add(bl10);
   }
   
// 도서정보 초기화
   {
	   BookVO bv1 = new BookVO();
	   bv1.setBook_id("1");
	   bv1.setBook_name("달러구트 꿈 백화점");
	   bv1.setBook_author("이미예");
	   bv1.setBook_theme("판타지");
	   bv1.setBook_LGU("NO");
	   bv1.setBook_publisher("팩토리나인");
	   bv1.setBook_summary("주문하신 꿈은 매진입니다.");
	   bookList.add(bv1);
	   
	   BookVO bv2 = new BookVO();
	   bv2.setBook_id("2");
	   bv2.setBook_name("나의 하루는 4시 30분에 시작된다");
	   bv2.setBook_author("김유진");
	   bv2.setBook_theme("자기계발");
	   bv2.setBook_LGU("IM");
	   bv2.setBook_publisher("토네이도");
	   bv2.setBook_summary("하루를 두 배로 사는 단 하나의 습관");
	   bookList.add(bv2);
	   
	   BookVO bv3 = new BookVO();
	   bv3.setBook_id("3");
	   bv3.setBook_name("어떻게 말해줘야 할까");
	   bv3.setBook_author("오은영");
	   bv3.setBook_theme("가정");
	   bv3.setBook_LGU("ES");
	   bv3.setBook_publisher("김영사");
	   bv3.setBook_summary("버럭하지 않고 분명하게 알려주는 방법");
	   bookList.add(bv3);
	   
	   BookVO bv4 = new BookVO();
	   bv4.setBook_id("4");
	   bv4.setBook_name("트렌드 코리아 2021");
	   bv4.setBook_author("김난도");
	   bv4.setBook_theme("경제");
	   bv4.setBook_LGU("EC");
	   bv4.setBook_publisher("미래의 창");
	   bv4.setBook_summary("설명이 필요없는 '트렌드서'의 대명사");
	   bookList.add(bv4);
	   
	   BookVO bv5 = new BookVO();
	   bv5.setBook_id("5");
	   bv5.setBook_name("아우슈비츠의 치과의사");
	   bv5.setBook_author("벤저민 제이콥스");
	   bv5.setBook_theme("역사");
	   bv5.setBook_LGU("HE");
	   bv5.setBook_publisher("서해문집");
	   bv5.setBook_summary("나는 유대인이고, 141129번 수용자였으며, 수용소 내 치과의사였다.");
	   bookList.add(bv5);
	   
	   BookVO bv6 = new BookVO();
	   bv6.setBook_id("6");
	   bv6.setBook_name("마음챙김의 시");
	   bv6.setBook_author("류시화");
	   bv6.setBook_theme("시");
	   bv6.setBook_LGU("PO");
	   bv6.setBook_publisher("수오서재");
	   bv6.setBook_summary("사회적 거리두기와 삶에 대한 성찰이 어느 때보다 필요한 지금, 손대신 시를 건내는 것은 어떤가");
	   bookList.add(bv6);
	   
	   BookVO bv7 = new BookVO();
	   bv7.setBook_id("7");
	   bv7.setBook_name("트루 리버럴리즘");
	   bv7.setBook_author("디드러 낸슨 매클로스키");
	   bv7.setBook_theme("정치");
	   bv7.setBook_LGU("PL");
	   bv7.setBook_publisher("7분의 언덕");
	   bv7.setBook_summary("자유주의가 더 정의롭고 더 번영하는 세상을 만드는 이유");
	   bookList.add(bv7);
	   
	   BookVO bv8 = new BookVO();
	   bv8.setBook_id("8");
	   bv8.setBook_name("나의 작은 정원");
	   bv8.setBook_author("차유정");
	   bv8.setBook_theme("예술");
	   bv8.setBook_LGU("AR");
	   bv8.setBook_publisher("동양북스");
	   bv8.setBook_summary("12색 물감으로 완성하는 수채화 컬러링 북");
	   bookList.add(bv8);
	   
	   BookVO bv9 = new BookVO();
	   bv9.setBook_id("9");
	   bv9.setBook_name("내게 다가온 수학의 시간들");
	   bv9.setBook_author("장우석");
	   bv9.setBook_theme("과학");
	   bv9.setBook_LGU("SI");
	   bv9.setBook_publisher("한권의 책");
	   bv9.setBook_summary("아름답고 고통스러웠던 어느 수학 교사의 자전적 이야기");
	   bookList.add(bv9);
	   
	   BookVO bv10 = new BookVO();
	   bv10.setBook_id("10");
	   bv10.setBook_name("너무 외로운 사람들을 위한 책");
	   bv10.setBook_author("오시마 노부요리");
	   bv10.setBook_theme("자기계발");
	   bv10.setBook_LGU("IM");
	   bv10.setBook_publisher("메이트북스");
	   bv10.setBook_summary("함께 있어도 외로운 당신을 위한 심리처방전");
	   bookList.add(bv10);
   }

	//게시글 초기화
	{
		NoticeVO notice = new NoticeVO();
		notice.setNotice_no(1);
		notice.setNotice_title("제목");
		notice.setNotice_content("내용");
		noticeList.add(notice);
		
		NoticeVO notice1 = new NoticeVO();
		notice.setNotice_no(2);
		notice.setNotice_title("제목1");
		notice.setNotice_content("내용1");
		noticeList.add(notice);
		
		NoticeVO notice2 = new NoticeVO();
		notice.setNotice_no(3);
		notice.setNotice_title("제목2");
		notice.setNotice_content("내용2");
		noticeList.add(notice);
		
		NoticeVO notice3 = new NoticeVO();
		notice.setNotice_no(4);
		notice.setNotice_title("제목3");
		notice.setNotice_content("내용3");
		noticeList.add(notice);
		
		NoticeVO notice4 = new NoticeVO();
		notice.setNotice_no(5);
		notice.setNotice_title("제목4");
		notice.setNotice_content("내용4");
		noticeList.add(notice);
		
		NoticeVO notice5 = new NoticeVO();
		notice.setNotice_no(6);
		notice.setNotice_title("제목5");
		notice.setNotice_content("내용5");
		noticeList.add(notice);
		
		NoticeVO notice6 = new NoticeVO();
		notice.setNotice_no(7);
		notice.setNotice_title("제목6");
		notice.setNotice_content("내용6");
		noticeList.add(notice);
		
		NoticeVO notice7 = new NoticeVO();
		notice.setNotice_no(8);
		notice.setNotice_title("제목7");
		notice.setNotice_content("내용7");
		noticeList.add(notice);
		
		NoticeVO notice8 = new NoticeVO();
		notice.setNotice_no(9);
		notice.setNotice_title("제목8");
		notice.setNotice_content("내용8");
		noticeList.add(notice);
		
		NoticeVO notice9 = new NoticeVO();
		notice.setNotice_no(10);
		notice.setNotice_title("제목9");
		notice.setNotice_content("내용9");
		noticeList.add(notice);
	}
}
