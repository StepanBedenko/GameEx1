import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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

    private float audioVolume;
    private Sound waterDrop;
    private Music instrumental;
    private Music oceanSurf;

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
                    if(!isTouchDownEvent(e))
                        return false;

                    instrumental.dispose();
                    oceanSurf.dispose();

                    StarfishGame.setActiveScreen(new LevelScreen());
                    return false;
                }
    );

        Button.ButtonStyle buttonStyle2 = new Button.ButtonStyle();

        Texture buttonTex2 = new Texture(Gdx.files.internal("audio.png"));
        TextureRegion buttonRegion2 = new TextureRegion(buttonTex2);
        buttonStyle2.up = new TextureRegionDrawable(buttonRegion2);

        Button muteButton = new Button(buttonStyle2);
        muteButton.setColor(Color.CYAN);

        muteButton.addListener(
                (Event e) ->
                {
                    if(!isTouchDownEvent(e))
                        return false;

                    audioVolume = 1 - audioVolume;
                    instrumental.setVolume(audioVolume);
                    oceanSurf.setVolume(audioVolume);

                    return true;
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
        uiTable.add(muteButton).top();
        uiTable.add(restartButton).top();

        waterDrop = Gdx.audio.newSound(Gdx.files.internal("Water_Drop.ogg"));
        instrumental = Gdx.audio.newMusic(Gdx.files.internal("Master_of_the_Feast.ogg"));
        oceanSurf = Gdx.audio.newMusic(Gdx.files.internal("Ocean_Waves.ogg"));

        audioVolume = 1.00f;
        instrumental.setLooping(true);
        instrumental.setVolume(audioVolume);
        instrumental.play();
        oceanSurf.setLooping(true);
        oceanSurf.setVolume(audioVolume);
        oceanSurf.play();
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
                waterDrop.play(audioVolume);
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
