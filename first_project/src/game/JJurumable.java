package game;

public class JJurumable {
	private static JJurumable jju = null;
	
	public static JJurumable getInstance(){
		if(jju==null){
			jju = new JJurumable();
		}
		return jju;
	}
	
	public static void startGame(){
		View v = new View();
		v.startMethod();
	}
}
