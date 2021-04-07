import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class StarfishCollector extends GameBeta {
    private Turtle turtle;
    private Starfish starfish;
    private BaseActor ocean;

    private boolean win;

    public void initialize()
    {
        ocean = new BaseActor(0,0, mainStage);
        ocean.loadTexture("water.jpg");
        ocean.setSize(800,600);

        starfish = new Starfish(380,380,mainStage);

        turtle = new Turtle(20,20,mainStage);
    }

    public void update(float dt)
    {


    }

}
