import gameLogic.*;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Hello World");
		
		GameConfig gameConfig = new GameConfig();
		
		int levels = 4;
		Game g;
		
		for(int i = 1; i <= levels; i++)
			g = new Game(4, gameConfig);
		
	}
}
