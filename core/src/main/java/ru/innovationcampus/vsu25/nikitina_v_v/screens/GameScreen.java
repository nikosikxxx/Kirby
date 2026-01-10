package ru.innovationcampus.vsu25.nikitina_v_v.screens;

import static ru.innovationcampus.vsu25.nikitina_v_v.GameResources.KIRBY_IMG_PATH;
import static ru.innovationcampus.vsu25.nikitina_v_v.GameSettings.KIRBY_BIT;
import static ru.innovationcampus.vsu25.nikitina_v_v.GameSettings.KIRBY_HEIGHT;
import static ru.innovationcampus.vsu25.nikitina_v_v.GameSettings.KIRBY_WIDTH;
import static ru.innovationcampus.vsu25.nikitina_v_v.GameSettings.SCREEN_HEIGHT;
import static ru.innovationcampus.vsu25.nikitina_v_v.GameSettings.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

import ru.innovationcampus.vsu25.nikitina_v_v.GameResources;
import ru.innovationcampus.vsu25.nikitina_v_v.GameSession;
import ru.innovationcampus.vsu25.nikitina_v_v.GameSettings;
import ru.innovationcampus.vsu25.nikitina_v_v.GameState;
import ru.innovationcampus.vsu25.nikitina_v_v.MyGdxGame;
import ru.innovationcampus.vsu25.nikitina_v_v.managers.ContactManager;
import ru.innovationcampus.vsu25.nikitina_v_v.managers.MemoryManager;
import ru.innovationcampus.vsu25.nikitina_v_v.object.CloudObject;
import ru.innovationcampus.vsu25.nikitina_v_v.object.BulletObject;
import ru.innovationcampus.vsu25.nikitina_v_v.object.IceCreamObject;
import ru.innovationcampus.vsu25.nikitina_v_v.object.KirbyObject;
import ru.innovationcampus.vsu25.nikitina_v_v.object.StarObject;
import ru.innovationcampus.vsu25.nikitina_v_v.views.ButtonView;
import ru.innovationcampus.vsu25.nikitina_v_v.views.ImageView;
import ru.innovationcampus.vsu25.nikitina_v_v.views.LiveView;
import ru.innovationcampus.vsu25.nikitina_v_v.views.MovingBackgroundView;
import ru.innovationcampus.vsu25.nikitina_v_v.views.RecordsListView;
import ru.innovationcampus.vsu25.nikitina_v_v.views.TextView;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    GameSession gameSession;
    MovingBackgroundView movingBackgroundView;
    KirbyObject kirbyObject;
    ArrayList<IceCreamObject> iceCreamArray;
    ArrayList<CloudObject> cloudArray;
    ArrayList<BulletObject> bulletArray;
    ArrayList<StarObject> starArray;
    ButtonView shoot;
    ButtonView jump;
    ButtonView pause;
    ButtonView homeButton;
    ButtonView continueButton;
    ButtonView restartButton;
    LiveView liveView;
    ImageView fullPinkOut;
    ImageView pinkOut;
    TextView scoretextView;
    TextView recordText;
    ContactManager contactManager;
    RecordsListView recordsListView;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        gameSession = new GameSession();
        movingBackgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        bulletArray = new ArrayList<>();
        iceCreamArray = new ArrayList<>();
        cloudArray = new ArrayList<>();
        starArray = new ArrayList<>();
        shoot = new ButtonView(50, 5, 150, 150, GameResources.BUTTON_SHOOT_IMG_PATH);
        jump = new ButtonView(1075, 5, 150,150, GameResources.BUTTON_JUMP_IMG_PATH);
        pause = new ButtonView(1190, 630, 75,75,GameResources.BUTTON_PAUSE_IMG_PATH);
        homeButton = new ButtonView(40, 250, 600,150,GameResources.BUTTON_HOME_IMG_PATH);
        continueButton = new ButtonView(660, 250, 600,150,GameResources.BUTTON_CONTINUE_IMG_PATH);
        restartButton = new ButtonView(660,250,600,150, GameResources.BUTTON_RESTART_IMG_PATH);
        fullPinkOut = new ImageView(0,0,GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT, GameResources.FULL_PINK_OUT_IMG_PATH);
        pinkOut = new ImageView(0, 20, SCREEN_WIDTH, SCREEN_HEIGHT, GameResources.PINK_OUT_IMG_PATH);
        liveView = new LiveView(600, 700);
        contactManager = new ContactManager(myGdxGame.world);
        scoretextView = new TextView(myGdxGame.commonWhiteFont, 50, 640);
        recordText = new TextView(myGdxGame.bigWhiteFont, 525,670, "Records");
        recordsListView = new RecordsListView(myGdxGame.commonWhiteFont, 640, 600);
    }
    public void show() {restartGame();}
    private void restartGame() {
        for (int i = 0; i< iceCreamArray.size(); i++) {
            myGdxGame.world.destroyBody(iceCreamArray.get(i).body);
            iceCreamArray.remove(i--);
        }
        for (int i = 0; i< cloudArray.size(); i++) {
            myGdxGame.world.destroyBody(cloudArray.get(i).body);
            cloudArray.remove(i--);
        }
        for (int i = 0; i< starArray.size(); i++) {
            myGdxGame.world.destroyBody(starArray.get(i).body);
            starArray.remove(i--);
        }
        if (kirbyObject != null) {
            myGdxGame.world.destroyBody(kirbyObject.body);
        }
        gameSession.score = 0;
        gameSession.destructedTrashNumber = 0;
        scoretextView.setText("Score:" + gameSession.getScore());

        kirbyObject = new KirbyObject(KIRBY_IMG_PATH, 200, 225, KIRBY_WIDTH, KIRBY_HEIGHT, KIRBY_BIT, myGdxGame.world);
        bulletArray.clear();
        gameSession.startGame();
    }
    @Override
    public void render(float delta) {
        handleInput();
        if (gameSession.state == GameState.PLAYING){
            if (gameSession.shouldSpawnIceCream()) {
                IceCreamObject iceCreamObject = new IceCreamObject(GameResources.ICE_CREAM_IMG_PATH,
                    100, 75, myGdxGame.world);
                iceCreamArray.add(iceCreamObject);
            }
            if (gameSession.shouldSpawnCloud()) {
                CloudObject cloudObject = new CloudObject(GameResources.CLOUD_IMG_PATH,
                    175, 225, myGdxGame.world);
                cloudArray.add(cloudObject);
            }
            if (gameSession.shouldSpawnStar()) {
                StarObject starObject = new StarObject(GameResources.STAR_IMG_PATH,
                    100, 75, myGdxGame.world);
                starArray.add(starObject);
            }
            if (!kirbyObject.isAlive()) {
                gameSession.endGame();
                recordsListView.setRecords(MemoryManager.loadRecordsTable());
            }
        updateBullets();
        updateIceCream();
        updateCloud();
        updateStar();
        movingBackgroundView.move();
        if (gameSession.state == GameState.PLAYING) {
            gameSession.updateScore();
            scoretextView.setText("Score: " + gameSession.getScore());
        }
        liveView.setLeftLives(kirbyObject.getLivesLeft());
        myGdxGame.stepWorld();
        }
        draw();
    }
    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            switch (gameSession.state) {
                case PLAYING:
                    if (pause.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.pauseGame();
                    }
                    if (shoot.isHit(myGdxGame.touch.x, myGdxGame.touch.y)){
                        if (kirbyObject.canShoot()) {
                            BulletObject laserBullet = new BulletObject(
                                GameResources.BULLET_IMG_PATH,kirbyObject.getX()  + kirbyObject.width / 2 + 20, kirbyObject.getY() - 20,
                                GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,

                                myGdxGame.world
                            );
                            bulletArray.add(laserBullet);
                            if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.shootSound.play(0.1f);
                        }
//            if (myGdxGame.audioManager.isSoundOn) {
//                myGdxGame.audioManager.shootSound.play(0.2f);
//            }
                    }
                    if (jump.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        kirbyObject.jump();
                    }
                    kirbyObject.move(myGdxGame.touch);
                    break;
                case PAUSED:
                    if (continueButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.resumeGame();
                    }
                    if (homeButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }break;
                case ENDED:
                    if (homeButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                    if(restartButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        restartGame();
                    }
                    break;
        }

    }
    }
    public void draw() {
        ScreenUtils.clear(0,0,0,0);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();


        movingBackgroundView.draw(myGdxGame.batch);
        pinkOut.draw(myGdxGame.batch);
        for (BulletObject bullet : bulletArray) bullet.draw(myGdxGame.batch);
        for (IceCreamObject iceCreamObject : iceCreamArray) iceCreamObject.draw(myGdxGame.batch);
        for (CloudObject cloudObject : cloudArray) cloudObject.draw(myGdxGame.batch);
        for (StarObject starObject : starArray) starObject.draw(myGdxGame.batch);
        kirbyObject.draw(myGdxGame.batch);
        shoot.draw(myGdxGame.batch);
        jump.draw(myGdxGame.batch);
        pause.draw(myGdxGame.batch);
        liveView.draw(myGdxGame.batch);
        scoretextView.draw(myGdxGame.batch);
        liveView.draw(myGdxGame.batch);

        if (gameSession.state == GameState.PAUSED) {
            fullPinkOut.draw(myGdxGame.batch);
            homeButton.draw(myGdxGame.batch);
            continueButton.draw(myGdxGame.batch);
        } else if (gameSession.state == GameState.ENDED) {
            fullPinkOut.draw(myGdxGame.batch);
            recordsListView.draw(myGdxGame.batch);
            homeButton.draw(myGdxGame.batch);
            restartButton.draw(myGdxGame.batch);
            recordText.draw(myGdxGame.batch);

        }

        myGdxGame.batch.end();
    }
    private void updateBullets() {
        for (int i = 0; i < bulletArray.size(); i++) {
            if (bulletArray.get(i).hasToBeDestroyed()) {
                myGdxGame.world.destroyBody(bulletArray.get(i).body);
                bulletArray.remove(i--);
            }
        }
    }
    private void updateIceCream() {
        for (int i = 0; i < iceCreamArray.size(); i++) {
            if (!iceCreamArray.get(i).isInFrame() || !iceCreamArray.get(i).isAlive()) {
                myGdxGame.world.destroyBody(iceCreamArray.get(i).body);
                iceCreamArray.remove(i--);
            }
        }
    }
    private void updateCloud() {
        for (int i = 0; i < cloudArray.size(); i++) {
            if (!cloudArray.get(i).isInFrame() || !cloudArray.get(i).isAlive()) {
                myGdxGame.world.destroyBody(cloudArray.get(i).body);
                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.cloud.play(0.4f);
                cloudArray.remove(i--);
            }
        }
    }
    private void updateStar() {
        for (int i = 0; i < starArray.size(); i++) {
            if (!starArray.get(i).isInFrame() || !starArray.get(i).isAlive()) {
                gameSession.destructionRegistration();
                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.explosionSound.play(0.4f);
                myGdxGame.world.destroyBody(starArray.get(i).body);
                starArray.remove(i--);
            }
        }
    }

    @Override
    public void dispose() {
        kirbyObject.dispose();
    }

}
