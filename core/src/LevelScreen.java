import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LevelScreen extends BaseScreen{
    private Turtle turtle;
    private boolean win;
    private Label starfishLabel;

    public void initialize(){
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture(Gdx.files.internal("undo.png"));
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable(buttonRegion);

        Button restartButton = new Button(buttonStyle);
        restartButton.setColor(Color.CYAN);

        restartButton.addListener(
                (Event e) ->
                {
                    if( !(e instanceof InputEvent) ||
                    !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    StarfishGame.setActiveScreen(new LevelScreen());
                    return false;
                }
    );

        starfishLabel =
                new Label("Starfish left:", BaseGame.labelStyle);
        starfishLabel.setColor(Color.CYAN);

        BaseActor ocean = new BaseActor(0,0,mainStage);
        ocean.loadTexture("water-border.jpg");
        ocean.setSize(1200,900);
        BaseActor.setWorldBounds(ocean);

        new Shark(200,200,mainStage);

        new Starfish(400,400,mainStage);
        new Starfish(500,100,mainStage);
        new Starfish(100,450,mainStage);
        new Starfish(200,250,mainStage);

        new Rock(200,150,mainStage);
        new Rock(100,300,mainStage);
        new Rock(300,350,mainStage);
        new Rock(450,20,mainStage);

        turtle = new Turtle(20,20,mainStage);

        win = false;

        uiTable.pad(10);
        uiTable.add(starfishLabel).top();
        uiTable.add().expandX().expandY();
        uiTable.add(restartButton).top();
    }

    public void update(float dt){
        starfishLabel.setText("Starfish left: " +
                BaseActor.count(mainStage,"Starfish"));

        for(BaseActor rockActor : BaseActor.getList(mainStage, "Rock")){
            turtle.preventOverlap(rockActor);
        }

        for(BaseActor starfishActor : BaseActor.getList(mainStage, "Starfish")) {
            Starfish starfish = (Starfish) starfishActor;
            if (turtle.overlaps(starfish) && !starfish.collected) {
                starfish.collected = true;
                starfish.clearActions();
                starfish.addAction(Actions.fadeOut(1));
                starfish.addAction(Actions.after(Actions.removeActor()));

                Whirlpool whirl = new Whirlpool(0, 0, mainStage);
                whirl.centerAtActor(starfish);
                whirl.setOpacity(0.25f);
            }
        }

            if(BaseActor.count(mainStage, "Starfish") == 0 && !win){
                win = true;
                BaseActor youWinMessage = new BaseActor(0,0,uiStage);
                youWinMessage.loadTexture("you-win2.png");
                youWinMessage.centerAtPosition(400,300);
                youWinMessage.setOpacity(0);
                youWinMessage.addAction(Actions.delay(1));
                youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
            }
        }
    }
