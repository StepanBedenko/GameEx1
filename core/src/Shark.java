import com.badlogic.gdx.scenes.scene2d.Stage;

public class Shark extends BaseActor{
    public Shark(float x, float y, Stage s){
        super(x,y,s);
        setAcceleration(100);
        setMaxSpeed(100);
        setDeceleration(100);
        String[] fileNames = {"sharky.png"};

        loadAnimationFromFiles(fileNames, 0.1f,true);
        setBoundaryPolygon(8);
    }

    public void act(float dt){
        accelerateAtAngle(180);

        applyPhysics(dt);

        boundToWorld();
    }
}
