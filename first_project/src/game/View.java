package game;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class View {
	Scanner sc = new Scanner(System.in);
	Database db = new Database();
	List<CityVO> list = db.list;
	DecimalFormat formatter = new DecimalFormat("###,###,###");
	
	int now = 0;// 사용자윈치
	int nowMoney = 5000000;//사용자돈
//	int nowMoney = 3000000;//사용자돈
	int nowPc = 0;//컴퓨터위치
	int nowPcMoney = 5000000;//컴퓨터돈
	String nowTurn = "사용자";//현재턴 처음엔 사용자턴
	int count = 1;
	
	boolean end = false;


	void showBanner(String title){
		System.out.println("──────────────────────────────────────────────────");
		System.out.println("                  JJURUMABLE                      ");
		System.out.println("──────────────────────────────────────────────────");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■ "+title+" ■■■■■■■■■■■■■■■■■■■■■");
	
	}
	
	void bigBanner(String banner){
		System.out.println("■■■■■■■■■■■■■■■■■■■■■ "+banner+" ■■■■■■■■■■■■■■■■■■■■■");
	}
	
	void littleBanner(String banner){
		System.out.println("─────────────────── "+banner+" ───────────────────");
	}
	
	void startMethod() {
		//로그인,회원가입
		while(true) {
			showBanner("환영합니다");
			System.out.println("[1]게임시작");
			System.out.println("[2]게임설명");
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
				gameStartView();
				break;
			case 2:
				infoView();
				break;
			case 3:
				return;//프젝 초기화면으로
			default:
				System.out.println("다시 입력해주세요.");
			}
		}
	}//startMathod
	
	/**
	 * 게임설명메서드
	 */
	void infoView() {
		System.out.println("\n이 게임은 글자크기 14pt에 최적화 되어있습니다."
				+ "\n(상단메뉴-window-Preferences-General-Basic-Text font)"
				+ "\n\n두개의 주사위를 던져 나오는 도시의 건물을 구매하고,"
				+ "\n상대방이 그 건물을 지나갈때 통행세를 받는 형식입니다.\n"
				+ "자산이 천만원 이상이 되거나 상대방이 파산하면 승리합니다.\n\n"
				+ "- 주사위 두개의 숫자가 같으면 한번 더 던질 수 있습니다. \n"
				+ "- 무인도에 갇히면 3턴을 쉬게됩니다\n" + "- 공항에 도착하면 원하는 도시로 여행이 가능합니다.\n"
				+ "- 내 건물이 있는 지역에 도착하면 건물을 추가하여 통행료를 두배로 받을 수 있습니다.\n"
				+ "- 시작지점에 도착하면 밀린 재산세를 납부해야 합니다.\n\n"
				+ "** 사용자의 말 : o, 사용자의 건물 : □ **\n"
				+ "** 컴퓨터의 말 : x, 컴퓨터의 건물 : ■ **");
	}

	void gameStartView(){
		showBanner("게임 시작");

		// 초기화//
		end= false;
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setSpot(" ");
			list.get(i).setComSpot(" ");
			list.get(i).setJuin(" ");
			list.get(i).setMulti("  ");
		}

		now = 0;
		nowPc = 0;
		nowMoney = 5000000;
		nowPcMoney = 5000000;
		nowTurn = "사용자";
		count = 1;

		list.get(0).setSpot("o");
		list.get(0).setComSpot("x");
		// /

		map();// 초반엔 나의 턴

		while (true) {
			myTurn();
			count = 1;
			if (end) {
				break;
			}
			com();
			count = 1;
		}
	}
	
	/**
	 * 나의 턴
	 */
	void myTurn(){
		
		nowTurn= "사용자";
		bigBanner("나의 턴");
		System.out.println("주사위를 던지려면 아무키나 누르세요 (나가려면 0)");
		String input = null;
		while(true){
		try {
			input = sc.next();
		}catch(Exception e) {
			System.out.println("다시 입력하세요");
			sc = new Scanner(System.in);
			break;
		}
		
		switch (input) {
		case "0":
			end = true;
			return;

		default :
			dice();
			System.out.println("▶ 이동중 …");
			sleep(3);
			move ();
			return;
		}
		}//while
	}
	
	/**
	 * 컴퓨터퓨터의 턴
	 */
	public void com(){
		nowTurn = "컴퓨터";
		bigBanner("컴퓨터의 턴");
		System.out.println("컴퓨터의 턴입니다.");
		dice();
		System.out.println("▶ 이동중 …");
		sleep(3);
		move();
		
		return;
	}

	
	/**
	 * 주사위 던지는 메서드
	 * @return
	 */
	public void dice(){
		
		while(true){
		
		Map<String, Integer> dice = new HashMap<String, Integer>();
		int dice1 = (int) (Math.random()*6+1);
		int dice2 = (int) (Math.random()*6+1);
		
		dice.put("dice1", dice1);
		dice.put("dice2", dice2);
		
		System.out.println("첫번째 주사위 : "+ dice1);
		System.out.println("두번째 주사위 : "+ dice2);
		
		int result = dice1+dice2;//움직여야할값
		
		littleBanner(result + "칸 이동합니다");
		
		if(nowTurn == "사용자"){
		now += result; }
		else{nowPc += result;}
		
		if(dice1==dice2){//더블이면
			System.out.println("더블입니다. 한번 더 던집니다.");
			continue;
		}else{
			break; //더블이 아닌경우 빠져나오기
		}
		
		}
		
		return;
	}
	
	/**
	 * 가는 메서드
	 */
	public void move(){
		int go;
		if(nowTurn=="사용자"){
			go = now%12;			
		}else{
			go = nowPc%12;			
		}
		map(go);
		return;
	}
	
	/**
	 * 전체 맵
	 */
	void map(int go){
		
		
		String check = null;
		
		if(nowTurn == "사용자"){
			//여기서 초기화.
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setSpot(" ");
			}
		
			check = "o";
			list.get(go).setSpot(check);
			if(list.get(go).getName()=="세계여행"){
			tripView();
			move();
			return;
			}//if세계여행
			else if(list.get(go).getName()=="무인도"){
				island();
				return;
			}//if무인도
			else if(list.get(go).getName()=="시작"){
				tax();
				return;
			}//시작
			else{
				
				if(list.get(go).getJuin()=="■"){
					pass(go);
				}	
			}
		}
		else{
			//여기서 초기화	
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setComSpot(" ");
			}	
			check = "x";
			list.get(go).setComSpot(check);///
			if(list.get(go).getName()=="세계여행"){
				tripView();
				move();
				return;
				}//if세계여행
				else if(list.get(go).getName()=="무인도"){
					island();
					return;
				}
				else if(list.get(go).getName()=="시작"){
					tax();
					return;
				}//시작
				else{
					if(list.get(go).getJuin()=="□"){ //상대방의 땅이면 
						pass(go);//통행료내러
					}	
				}
			}
		map();
		if(nowTurn=="컴퓨터"){
			System.out.println("▶ 살까말까 고민 중 …");
			sleep(5);}
		buyView(go);
		return;
	}
	
	void pass(int num){
		System.out.println("통행료를 냅니다.");
		int pass;
		pass = (int) (list.get(num).getPrice()*0.5);
		if(nowTurn=="사용자"){
			nowMoney -= pass;
			nowPcMoney += pass;
			System.out.println(list.get(num).getName()+ "을 지나는 통행료"+formatter.format(pass)+"원을 지불하여 "+ formatter.format(nowMoney) +"가 남았습니다.");
			System.out.println("상대방의 자산이 "+ formatter.format(nowPcMoney) + "가 되었습니다.");
		}else{
			nowPcMoney -= pass;
			nowMoney+=pass;
			System.out.println(list.get(num).getName()+ "을 지나는 통행료"+formatter.format(pass)+"원을 지불하여 "+ formatter.format(nowPcMoney) +"가 남았습니다.");
			System.out.println("나의 자산이 "+ formatter.format(nowMoney) + "가 되었습니다.");
		}
		isOk();
		count = 2;
	}
	
	   void map(){

	       System.out.println("┌───────────┬───────────┬───────────┬───────────┐");
	       System.out.println("         무인도     　       부  산 　            제주도         　　인　천");
	      System.out.println("│     "+list.get(9).getSpot()+list.get(9).getComSpot()+"    │     "+list.get(8).getSpot()+list.get(8).getComSpot()+"    │     "+list.get(7).getSpot()+list.get(7).getComSpot()+"    │     "+list.get(6).getSpot()+list.get(6).getComSpot()+"    │");
	      System.out.println("│    "+list.get(9).getJuin()+list.get(9).getMulti()+"    │    "+list.get(8).getJuin()+list.get(8).getMulti()+"    │    "+list.get(7).getJuin()+list.get(7).getMulti()+"    │    "+list.get(6).getJuin()+list.get(6).getMulti()+"    │");
	      System.out.println("├───────────┼───────────┴───────────┼───────────┤");
	      System.out.println("          서 울                                                                대  전       ");
	      System.out.println("│     "+list.get(10).getSpot()+list.get(10).getComSpot()+"    │                       │     "+list.get(5).getSpot()+list.get(5).getComSpot()+"    │");
	      System.out.println("│    "+list.get(10).getJuin()+list.get(10).getMulti()+"    │                       │   "+list.get(5).getJuin()+list.get(5).getMulti()+"     │");
	      System.out.println("├───────────┤      JJURUMABLE       ├───────────┤");
	      System.out.println("          울 산                                                                광  주       ");
	      System.out.println("│     "+list.get(11).getSpot()+list.get(11).getComSpot()+"    │                       │     "+list.get(4).getSpot()+list.get(4).getComSpot()+"    │");
	      System.out.println("│    "+list.get(11).getJuin()+list.get(11).getMulti()+"    │                       │   "+list.get(4).getJuin()+list.get(4).getMulti()+"     │");
	      System.out.println("├───────────┼───────────┬───────────┼───────────┤");
	      System.out.println("   　     시작            　 대 구 　               세 종            　공  항");
	      System.out.println("│     "+list.get(0).getSpot()+list.get(0).getComSpot()+"    │     "+list.get(1).getSpot()+list.get(1).getComSpot()+"    │     "+list.get(2).getSpot()+list.get(2).getComSpot()+"    │     "+list.get(3).getSpot()+list.get(3).getComSpot()+"    │");
	      System.out.println("│    "+list.get(0).getJuin()+list.get(0).getMulti()+"    │    "+list.get(1).getJuin()+list.get(1).getMulti()+"    │    "+list.get(2).getJuin()+list.get(2).getMulti()+"    │    "+list.get(3).getJuin()+list.get(3).getMulti()+"    │");
	      System.out.println("└───────────┴───────────┴───────────┴───────────┘");

	      return;
	   }
	
	/**구매 뷰
	 * 
	 */
	public void buyView(int num){
		
		
		int input = 0;
		while(true){
			try {
				if(nowTurn=="사용자"){
					if(list.get(num).getJuin()=="□"){
						//System.out.println("이미 소유한 지역이기 때문에 다음턴으로 넘어갑니다.");
						multiView(num);
						break;}
					System.out.println(list.get(num).getName()+"에 건물을 구매하시겠습니까?");
					System.out.println("(이미 주인이 있는 건물은 2배의 가격으로 매입합니다.)");
					System.out.println("현 자산 : " + formatter.format(nowMoney) + "원");
					System.out.println("[1] 구매 (" + formatter.format(list.get(num).getPrice()*count) + "원)");
					System.out.println("[2] 구매하지않음");
					input = sc.nextInt();					
				}
				//else if(count==1){
				else{
					if(list.get(num).getJuin()=="■"){
						//System.out.println("이미 소유한 지역이기 때문에 다음턴으로 넘어갑니다.");
						multiView(num);
						break;}
					input = (int) (Math.random()*3);}
				//}
			}catch(Exception e) {
				System.out.println("다시 입력하세요");
				sc = new Scanner(System.in);
				break;
			}
			
			switch (input) {
			case 1:
				buy(num,count);//산다
				return;
			case 2:
				System.out.println("건물을 구매하지 않으셨습니다. \n상대방의 턴으로 넘어갑니다.");
				return;

			default :
				break;
			}
			}//while
		return;
	}
	
	/**
	 * 구매 메서드
	 */
	public void buy(int num, int count){
		int price = list.get(num).getPrice()*count;	
		int money = 0;
		
		if(nowTurn == "사용자"){//사용자 구매
			list.get(num).setJuin("□");
			nowMoney -= price;
			money = nowMoney;
			list.get(num).setMulti("  ");//혹시 증축된게 있다면 없애준다.
		}else{
			if(nowPcMoney<price){//자산보다 건물가가 높을때
				System.out.println("컴퓨터의 자산이 부족해 구매하지 않습니다.");
				return;
			}else{//컴 구매
			list.get(num).setJuin("■");
			nowPcMoney -= price;
			money = nowPcMoney;
			list.get(num).setMulti("  ");//혹시 증축된게 있다면 없애준다.
			}//자산이 있을때 구매
		}
			
		System.out.println("▶"+list.get(num).getName()+"건물을 구매합니다");
		isOk();//파산아닌지 검사
		System.out.println("▶"+formatter.format(price)+"원을 지출하여 "+ formatter.format(money) + "원이 되었습니다.");
		map();
		return;
		
		
		
	}
	
	
	
	void build(int buyNum){
		
		int price = 0;
		String check;
		if(nowTurn=="사용자"){
			check = "□";
		}else{
			check = "■";
		}
		
		list.get(buyNum).setJuin(check);
		
		
		return;
	}
	
	void tripView(){
			map();
		while(true) {
			littleBanner("국내여행을 떠납니다");
			System.out.println("원하는 지역을 선택하세요");
			System.out.println("[1] 대구\t[2] 세종\t[3] 광주\t[4] 대전");
			System.out.println("[5] 인천\t[6] 울산\t[7] 부산\t[8] 서울\t[9] 제주도");
			int result = 0;
			int input = 0;
			try {
				if(nowTurn=="사용자"){
				input = sc.nextInt();}
				else{System.out.println("▶ 고르는 중 …");
					sleep(5);
					input = (int) (Math.random()*9+1);}
			}catch(Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			
			switch (input) {
			case 1:
				result = 10;
				break;
			case 2:
				result = 11;
				break;
			case 3:
				result = 1;
				break;
			case 4:
				result = 2;
				break;
			case 5:
				result = 3;
				break;
			case 6:
				result = 8;
				break;
			case 7:
				result = 5;
				break;
			case 8:
				result = 7;
				break;
			case 9:
				result = 4;
				break;
			default:
				break;
			}
			
			if(nowTurn == "사용자"){
				now += result; }
				else{nowPc += result;}
			break;
		}
		
	}
	
	
	void island(){
		map();
		System.out.println("무인도에 갇혔습니다.\n세 번 쉬어갑니다.");
		if (nowTurn=="사용자") {
			com();
			com();
		//	com();
		//	nowTurn = "사용자";
		}else{
			myTurn();
			myTurn();
		//	myTurn();
		//	nowTurn = "컴퓨터";
		}
	}
	
	void tax(){
		map();
		System.out.println("");
		System.out.println("밀린 재산세를 납부합니다.");
		if(nowTurn=="사용자"){
			int tax = 0;
			littleBanner("고지서");
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getJuin()=="□"){//건물이 있는
					System.out.println(list.get(i).getName() + " : " + formatter.format(list.get(i).getPrice()*0.1));
					tax+=list.get(i).getPrice()*0.1;
				}
			}
			System.out.println("총 "+ formatter.format(tax) + "원을 납부합니다.");
			System.out.println("납부 후 재산이 "+ formatter.format(nowMoney-tax)+"가 되었습니다.");
			System.out.println("─────────────────────────────────────────");
		}else{
			int tax = 0;
			littleBanner("고지서");
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getJuin()=="■"){//건물이 있는
					System.out.println(list.get(i).getName() + " : " + formatter.format(list.get(i).getPrice()*0.1));
					tax+=list.get(i).getPrice()*0.1;
				}
			}
			System.out.println("총 "+ formatter.format(tax) + "원을 납부합니다.");
			System.out.println("납부 후 재산이 "+ formatter.format(nowPcMoney-tax)+"가 되었습니다.");
			System.out.println("───────────────────────────────────────────────");
		}
		isOk();
		return;
	}
	
	void isOk(){
	      if(nowTurn=="사용자"){//사용자인경우
	         if(nowMoney>=10000000){//천달성
	               System.out.println("자산이 천만원을 넘어 승리하셨습니다.");
	               bigBanner("WIN");
	               end = true;
	               return;
	         }else if(nowMoney<0){//파산
	            System.out.println(nowTurn + "의 자산이"+formatter.format(nowMoney)+"가 되어 파산하였습니다.");
	            bigBanner("DEFEAT");
	            end = true;
	            return;
	            }
	            else{//0과 1000사이
	               return;
	            }
	      }//사용자
	      else{//컴퓨터
	         if(nowPcMoney>=10000000){
	            System.out.println("컴퓨터의 자산이 천만원을 넘겨 패배하셨습니다.");
	            bigBanner("DEFEAT");
	            end = true;
	            return;
	         }else if(nowPcMoney<0){
	            System.out.println(nowTurn + "의 자산이"+formatter.format(nowPcMoney)+"가 되어 파산하였습니다.");
	            bigBanner("WIN");
	            end = true;
	            return;
	         }else{
	            return;
	         }
	      }
	   }
	
	void multiView (int num){
		int input = 0;
		while(true){
			try {
				if(nowTurn=="사용자"){
					System.out.println(list.get(num).getName()+"에 건물을 추가할까요?");
					System.out.println("(통행료가 두배가 됩니다.)");
					System.out.println(" 현 자산 : " + formatter.format(nowMoney) + "원");
					System.out.println("[1] 추가 (" + formatter.format(list.get(num).getPrice()/2) + "원)");
					System.out.println("[2] 추가하지않음");
					input = sc.nextInt();					
				}
				else{
					input = (int) (Math.random()*3);}
			}catch(Exception e) {
				System.out.println("다시 입력하세요");
				sc = new Scanner(System.in);
				break;
			}
			
			switch (input) {
			case 1:
				multi(num);//추가
				return;
			case 2:
				System.out.println("건물을 추가하지 않으셨습니다. \n상대방의 턴으로 넘어갑니다.");
				return;

			default :
				break;
			}
			}//while
		
	}
	
	void multi(int num){
		if(nowTurn=="사용자"){//사용자
			if(list.get(num).getMulti()=="  "){//한번도 추가를 안한상태
				list.get(num).setMulti(" □");
				list.get(num).setPrice(list.get(num).getPrice()*2);//가격*2로 바꿈
			}else if(list.get(num).getMulti()==" □"){//한번은 추가 한 상태
				list.get(num).setMulti("□□");
				list.get(num).setPrice(list.get(num).getPrice()*2);//가격*2로 바꿈
			}else{//두번 모두 추가한 상태
				System.out.println("더이상 지을 수 없습니다.");
			}
		}else{//컴
			if(list.get(num).getMulti()=="  "){//한번도 추가를 안한상태
				list.get(num).setMulti(" ■");
				list.get(num).setPrice(list.get(num).getPrice()*2);//가격*2로 바꿈
			}else if(list.get(num).getMulti()==" ■"){//한번은 추가 한 상태
				list.get(num).setMulti("■■");
				list.get(num).setPrice(list.get(num).getPrice()*2);//가격*2로 바꿈
			}else{//두번 모두 추가한 상태
				System.out.println("더이상 지을 수 없습니다.");
			}
		}
		isOk();
		return;
	}
	
	void sleep (int num){
		String whiteSpot = "○  ";
		//String blackSpot = "●";
		System.out.print("▶ Loading ");
		for (int i = 0; i < num; i++) {
			System.out.print(whiteSpot);
			//System.out.println("Loading "+ whiteSpot + blackSpot);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//whiteSpot += "○";
		}
		System.out.println();
	}
	
}
