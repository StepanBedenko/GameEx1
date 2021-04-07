import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Game game = new StarfishCollector();
		LwjglApplication launcher = new LwjglApplication(game, "Starfish Collector", 800,600);
	}
}
