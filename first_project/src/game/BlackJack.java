package game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

class Card {
	private String suit, rank;// 카드 문양 및 숫자
	private int score;// 점수

	Card(String suit, String rank) {
		this.suit = suit;
		this.rank = rank;
	}// 나온카드 저장

	String GetRank() {
		return rank;
	}// 변환을 하기위해서 반환

	int GetScore() {
		return score;
	}// 변환을 하기위해서 반환

	public void SetScore(int score) {
		this.score = score;
	}// 카드의 점수를 저장
	
	String CardPrint() {
		return rank + suit;
	}// 하나의 결과값
}

class Player {
	private Card cards[];
	private int doller, sumScore;

	Player(int money) {
		doller = money;
		this.cards = new Card[51];// 카드총수
	}

	Card[] GetCard() {
		return cards;
	}// 저장할 배열

	public void BetDoller(int betmoney) {
		doller -= betmoney;
	}// 배팅긍액만큼 돈뺴기

	int GetDoller() {
		return doller;
	} // 변환을 하기위해서 반환

	public void SumScore1(int sumScore) {
		this.sumScore += sumScore;
		if (sumScore == 0)
			this.sumScore = 0;
	}// 카드합 구하기

	int SumScore2() {
		return sumScore;
	}// 변환을 하기위해서 반환

	public void aScore() {
		this.sumScore -= 10;
	}// A가 있는 상태에서 22점이상일때
	
	public void WinerDoller(int bettingDoller) {
		doller += bettingDoller + bettingDoller;
	}// 이겼을때

	public void DrawDoller(int bettingDoller) {
		doller += bettingDoller;
	} // 비겼을 경우
}

public class BlackJack {
	Scanner input = new Scanner(System.in);
	private Player Player, Dealer;
	private int Betmoney;
	private String HS, Again;

	private static BlackJack bj = null;

	BlackJack() {
		Dealer = new Player(10000);
		Player = new Player(100);
	}// 초기 돈 세팅

	public static BlackJack getInstance(){
		if(bj==null){
			bj = new BlackJack();
		}
		return bj;
	}
//1
	public void GameStart() {
		String[] TrumpCard = { "(Diamonds)", "(Hearts)", "(Clubs)", "(Spades)" };
		String[] rank = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
		int num = 1;
		while (true) {
			ResetCard(Dealer);// 카드 세팅
			ResetCard(Player);
			StartMenu();// 시작화면 출력
			Betting();// 배팅
			CardSet(TrumpCard, rank);// 초기 카드 뽑기
			CardMenu(num);// 카드 메뉴 출력
			HitStand(TrumpCard, rank, num);// 히트or스탠드 할지 물어보고 뽑아주거나
			if(GameOver()){// 게임이 종료되는 함수
				break;
			}
			Again(num);
		}
	}// 하나의 블랙젝의 게임을 정리해 놓은 함수
//2
	public void ResetCard(Player card) {
		for (int i = 0; i < card.GetCard().length; i++) {
			card.GetCard()[i] = new Card(null, null);
		}
	}
//3
	public void StartMenu() {
		System.out.println("┏━━━━━━━━━Jack'sBlackJackGame━━━━━━━━━━┓");
		System.out.println("Dealer(" + Dealer.GetDoller() + "$) ");
		System.out.println("Player(" + Player.GetDoller() + "$) ");
		System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

	}
//4
	public void Betting() {
		while (true) {
			System.out.print("How much money do you want to bet?");
			int bet=0;
			
			try {
				bet = input.nextInt();
			} catch (Exception e) {
				System.out.println("잘못 입력하였습니다.");
				input = new Scanner(System.in);
				continue;
			}
			
			if ((Dealer.GetDoller() - bet) <= 0) {
				bet = Dealer.GetDoller();
				System.out.println("Not enough money!! ALL IN!!");
			} // 돈이 없거나 올인했을경우
			else if ((Player.GetDoller() - bet) <= 0) {
				bet = Player.GetDoller();
				System.out.println("Not enough money!! ALL IN!!");
			}
			if (bet <= 0)
				System.out.println("잘못입력되었습니다.");
			else {
				Betmoney = bet;// 입력받은 수가 0이하가 아니면 배팅달러라는 변수에 money값 넣어줌
				break;
			}
		}
		Dealer.BetDoller(Betmoney);// 딜러 돈 뺴기 위함
		Player.BetDoller(Betmoney);
	}
//5
	public void CardSet(String[] number, String[] rank) {
		Random random = new Random();
		for (int i = 0; i < 2; i++) {
			Dealer.GetCard()[i] = new Card(number[random.nextInt(number.length)], rank[random.nextInt(rank.length)]);
		}
		for (int i = 0; i < 2; i++) {
			Player.GetCard()[i] = new Card(number[random.nextInt(number.length)], rank[random.nextInt(rank.length)]);
		}
	}//새로운 카드 준비
//6
	public void CardMenu(int num) {
		System.out.println("┏━━━━━━━━━Jack'sBlackJackGame━━━━━━━━━━┓");
		System.out.print("Dealer(" + Dealer.GetDoller() + "$) ");
		if (num == 1)
			System.out.println(Dealer.GetCard()[0].CardPrint() + " XX");
		else if (num == 2) {
			for (int i = 0; i < Dealer.GetCard().length; i++) {
				if (Dealer.GetCard()[i].CardPrint().equals("nullnull"))
					break;
				System.out.print(Dealer.GetCard()[i].CardPrint() + " ");
			}
		}
		System.out.println();
		System.out.print("Player(" + Player.GetDoller() + "$) ");
		for (int i = 0; i < Player.GetCard().length; i++) {
			if (Player.GetCard()[i].CardPrint().equals("nullnull"))
				break;
			System.out.print(Player.GetCard()[i].CardPrint() + " ");
		}
		System.out.println();
		System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
	}//1: 딜러카드  2: 플레이어 카드
//7
	public void HitStand(String[] trumpCard, String[] rank, int num) {
		Random random = new Random();
		int count = 2;//카드가 두장있으니깐 2부터 시작
		while (true) {
			System.out.print("Hit or Stand? (H/S): ");
			try {
				HS = input.next();
			} catch (Exception e) {
				System.out.println("잘못 입력하였습니다.");
				input = new Scanner(System.in);
				continue;
			}
			if (HS.equalsIgnoreCase("h") && HS.equalsIgnoreCase("H")) {
				for (int i = count; i < Player.GetCard().length; i++) {
					if (i == 2) {
						Player.GetCard()[i] = new Card(trumpCard[random.nextInt(trumpCard.length)],rank[random.nextInt(rank.length)]);sumScore(Player);
					}
				}
				count++;
				for (int i = 2; i < Dealer.GetCard().length; i++) {
					sumScore(Dealer);// 점수 총합을 구하는 함수 사용
					if (Dealer.SumScore2() >= 17)
						break;// 플레이어 총 점수가 17이상이 되었을 때 카드ㄴㄴ
					Dealer.GetCard()[i] = new Card(trumpCard[random.nextInt(trumpCard.length)],rank[random.nextInt(rank.length)]);
				}
				if (Dealer.SumScore2() == 22 && Player.SumScore2() == 22) {
					num = 1;
					CardMenu(num);
				}
				else if (Player.SumScore2() > 21) {
					num = 1;
					Winer();
					ScoreMoney();
					CardMenu(num);
					break;
				}
				else {
					num = 1;
					CardMenu(num);
				}
			}else if(HS.equalsIgnoreCase("s")&& HS.equalsIgnoreCase("S")){		
 				num = 2;//스탠드를 했을 때 최종 결과 메뉴를 출력해주기 위해 입력한 print값	
 				sumScore(Player);//플레이어의 최종 점수 계산하는 함수
 				for (int i = 2; i < Dealer.GetCard().length; i++) {
					sumScore(Dealer);// 점수 총합을 구하는 함수 사용
					if (Dealer.SumScore2() >= 17)
						break;// 플레이어 총 점수가 17이상이 되었을 때 카드ㄴㄴ
					Dealer.GetCard()[i] = new Card(trumpCard[random.nextInt(trumpCard.length)],rank[random.nextInt(rank.length)]);
				}
 				Winer();//누가 이겼는지 결과를 출력해주는 함수.
 				ScoreMoney();//돈 계산 해주는 함수
 				CardMenu(num);//결과 값을 출력해주는 함수
 				break;
 				}
			else{
				System.out.println("잘못입력하였습니다");
			}
		}
	}
//8
	public void sumScore(Player player) {
		player.SumScore1(0);
		for (int i = 0; i < player.GetCard().length; i++) {
			if (player.GetCard()[i].CardPrint().equals("nullnull"))
				break;
			CardScore(player, i);//
			player.SumScore1(player.GetCard()[i].GetScore());
		}
		AScore(player);
	}// 각 플레이어와 딜러의 점수를 합하는 함수
//9
	public void CardScore(Player player, int i) {
		switch (player.GetCard()[i].GetRank()) {
		case "A":player.GetCard()[i].SetScore(11);break;
		case "2":player.GetCard()[i].SetScore(2);break;
		case "3":player.GetCard()[i].SetScore(3);break;
		case "4":player.GetCard()[i].SetScore(4);break;
		case "5":player.GetCard()[i].SetScore(5);break;
		case "6":player.GetCard()[i].SetScore(6);break;
		case "7":player.GetCard()[i].SetScore(7);break;
		case "8":player.GetCard()[i].SetScore(8);break;
		case "9":player.GetCard()[i].SetScore(9);break;
		case "10":player.GetCard()[i].SetScore(10);break;
		case "K":player.GetCard()[i].SetScore(10);break;
		case "Q":player.GetCard()[i].SetScore(10);break;
		case "J":player.GetCard()[i].SetScore(10);break;
		}
	}
//10
	public void AScore(Player player) {
		for (int i = 0; i <= player.GetCard().length; i++) {
			if (player.GetCard()[i].CardPrint().equals("nullnull"))
				break;
			if (player.SumScore2() > 21 && player.GetCard()[i].GetRank().equals("A"))
				player.aScore();// 각 점수의 합이 21이 넘고, 뽑은 카드에서 "A"가 있을 경우 있는 10점 빼기
		}
	}
//11
	public void Winer() {
		if (Dealer.SumScore2() > 21 && Player.SumScore2() > 21)
			System.out.println("equal points");
		else if (Player.SumScore2() > 21)
			System.out.println("Player busted");
		else if (Dealer.SumScore2() < Player.SumScore2() || Dealer.SumScore2() > 21)
			System.out.println("Player Wins");
		else if (Dealer.SumScore2() > Player.SumScore2())
			System.out.println("Dealer Wins");
		else
			System.out.println("equal points");
	}// 누가 이겼는지
//12
	public void ScoreMoney() {
		if (Dealer.SumScore2() == Player.SumScore2() || Dealer.SumScore2() > 21 && Player.SumScore2() > 21) {
			Dealer.DrawDoller(Betmoney);
			Player.DrawDoller(Betmoney);
		} else if (Player.SumScore2() > 21) {
			Dealer.WinerDoller(Betmoney);
		} else if (Dealer.SumScore2() > 21 || Dealer.SumScore2() < Player.SumScore2()) {
			Player.WinerDoller(Betmoney);
		} else
			Dealer.WinerDoller(Betmoney);
	}// 점수 비교를 하고 돈의 결과를 결정
//13
	public boolean GameOver() {
		if (Player.GetDoller() == 0) {
			System.out.println("┏━━━━━━━━━━━━━━━━ Jack's BlackJack Game ━━━━━━━━━━━━━━━━━┓");
			System.out.println("     You lost EVERYTHING!! Idiot!");
			System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
			System.out.println("exiting program");
		} else if (Dealer.GetDoller() == 0) {
			System.out.println("┏━━━━━━━━━━━━━━━━ Jack's BlackJack Game ━━━━━━━━━━━━━━━━━┓");
			System.out.println("      You got ALL!! Best Player!");
			System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
			System.out.println("exiting program");
		}
		return true;
	}
//14
	public void Again(int num) {
		while (true) {
			System.out.print("Play Again? (Y/N)?");	
			try {
				Again = input.next();
			} catch (Exception e) {
				System.out.println("잘못 입력하였습니다.");
				input = new Scanner(System.in);
				continue;
			}
			if (Again.equals("y") || Again.equals("Y")) {
//				num = 1;
				break;}
			else if (Again.equals("n") || Again.equals("N")) {
				System.out.println("┏━━━━━━━━━━━━━━━━ Jack's BlackJack Game ━━━━━━━━━━━━━━━━━┓");
				System.out.print("      You earned ");
				if (Player.GetDoller() == 100)
					System.out.println(0 + "$");
				else if (Player.GetDoller() < 100)
					System.out.println("-" + (100 - Player.GetDoller()) + "$");
				else
					System.out.println("+" + (Player.GetDoller() - 100) + "$");
				System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
				System.out.println("exiting program...");
				BufferedWriter out;// 남은 돈 저장
				String DealerMoney = Dealer.GetDoller() + "";
				String PlayerMoney = Player.GetDoller() + "";
				try {
					out = new BufferedWriter(new FileWriter("money.txt", true));
					out.write(DealerMoney);// 남은 딜러의 돈을 입력
					out.newLine();// 한칸 띄우기 위해 쓴 식
					out.write(PlayerMoney);// 남은 플레이어의 돈을 입력
					out.newLine();
					out.close();
				}catch (IOException e) {e.printStackTrace();}
				System.exit(0);// 프로그램 종료
			}
		}
	}
//15
	public void StartDoller(String dealerMoney, String playerMoney) {
		int number1 = Integer.parseInt(dealerMoney);// Integer 정수
		Dealer = new Player(number1);
		int number2 = Integer.parseInt(playerMoney);// Integer 정수
		Player = new Player(number2);
	}// 게임을 이어했을경우 저장된 돈가져오기

}