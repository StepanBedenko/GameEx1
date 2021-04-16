import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Game myGame = new StarfishGame();
		LwjglApplication launcher =
				new LwjglApplication(myGame, "Starfish Collector", 800,600);
	}
}
