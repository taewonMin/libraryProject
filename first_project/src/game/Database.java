package game;

import java.util.ArrayList;
import java.util.List;

public class Database {

	public static List<CityVO> list = new ArrayList<>();
	
	{
		CityVO cv0 = new CityVO();
		cv0.setName("시작");
		cv0.setPrice(0);
		list.add(cv0);
		
		
		CityVO cv1 = new CityVO();
		cv1.setName("대구");
		cv1.setPrice(700000);
		list.add(cv1);
		
		
		CityVO cv2 = new CityVO();
		cv2.setName("세종");
		cv2.setPrice(600000);
		list.add(cv2);
		
		
		CityVO cv3 = new CityVO();
		cv3.setName("세계여행");
		cv3.setPrice(0);
		list.add(cv3);
		
		
		CityVO cv4 = new CityVO();
		cv4.setName("광주");
		cv4.setPrice(600000);
		list.add(cv4);
		
		
		CityVO cv5 = new CityVO();
		cv5.setName("대전");
		cv5.setPrice(800000);
		list.add(cv5);
		
		
		CityVO cv6 = new CityVO();
		cv6.setName("인천");
		cv6.setPrice(900000);
		list.add(cv6);
		
		
		CityVO cv7 = new CityVO();
		cv7.setName("제주도");
		cv7.setPrice(800000);
		list.add(cv7);
		
		
		CityVO cv8 = new CityVO();
		cv8.setName("부산");
		cv8.setPrice(700000);
		list.add(cv8);
		
		
		CityVO cv9 = new CityVO();
		cv9.setName("무인도");
		cv9.setPrice(0);
		list.add(cv9);
		
		
		CityVO cv10 = new CityVO();
		cv10.setName("서울");
		cv10.setPrice(1000000);
		list.add(cv10);
		
		
		CityVO cv11 = new CityVO();
		cv11.setName("울산");
		cv11.setPrice(800000);
		list.add(cv11);
		
		
		
	}
	
}
