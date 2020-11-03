package package_Main;

import java.util.*;
import package_VO.*;

public class View {
	
	void startMethod() {
		//로그인,회원가입
		
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("1. 로그인");
			System.out.println("2. 회원가입");
			try {
				int n = sc.nextInt();
				switch(n) {
				case 1:
					signIn();
					break;
				case 2:
					signUp();
					break;
				}
			}catch(Exception e) {
				System.out.println("숫자만 입력해주세요.");
			}
			
		}
	}
	
	/**
	 * 로그인 메서드
	 */
	void signIn() {
		System.out.println("아이디를 입력하세요");
		System.out.println("패스워드를 입력하세요");
		//로그인 성공하면
		home();
		//실패하면 재입력
	}
	
	/**
	 * 회원가입 메서드
	 */
	void signUp() {
		System.out.println("정보입력");
	}
	
	/**
	 * 메인화면 - 검색, 게시판, 마이페이지, 반납
	 */
	void home() {
		System.out.println("1. 검색");
		System.out.println("2. 게시판");
		System.out.println("3. 마이페이지");
		System.out.println("4. 반납");
		
		Scanner sc = new Scanner(System.in);
		while(true) {
			try {
				int n = sc.nextInt();
				switch(n) {
				case 1:
//					search();	//검색
					break;
				case 2:
//					board();	//게시판
					break;
				case 3:
					myPage();	//마이페이지
					break;
				case 4:
//					returnBook();	//반납
					break;
				}
			} catch (Exception e) {
				System.out.println("숫자만 입력해주세요");
			}
		}
	}
	
	/**
	 * 마이페이지
	 */
	void myPage() {
		System.out.println("1. 정보수정");
		System.out.println("2. 대출목록");
		System.out.println("3. 예약목록");
		
		Scanner sc = new Scanner(System.in);
		while(true) {
			try {
				int n = sc.nextInt();
				switch(n) {
				case 1:
//					search();	//검색
					break;
				case 2:
//					board();	//게시판
					break;
				case 3:
					myPage();	//마이페이지
					break;
				}
			} catch (Exception e) {
				System.out.println("숫자만 입력해주세요");
			}
		}
	}
}
