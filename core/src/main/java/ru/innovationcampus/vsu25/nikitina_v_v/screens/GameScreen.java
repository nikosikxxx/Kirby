package ru.innovationcampus.vsu25.nikitina_v_v.screens;

import static com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable.draw;
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
import ru.innovationcampus.vsu25.nikitina_v_v.object.BulletObject;
import ru.innovationcampus.vsu25.nikitina_v_v.object.KirbyObject;
import ru.innovationcampus.vsu25.nikitina_v_v.views.ButtonView;
import ru.innovationcampus.vsu25.nikitina_v_v.views.LiveView;
import ru.innovationcampus.vsu25.nikitina_v_v.views.MovingBackgroundView;
import ru.innovationcampus.vsu25.nikitina_v_v.views.RecordsListView;
import ru.innovationcampus.vsu25.nikitina_v_v.views.TextView;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    GameSession gameSession;
    MovingBackgroundView movingBackgroundView;
    KirbyObject kirbyObject;
    ArrayList<BulletObject> bulletArray;
    ButtonView shoot;
    ButtonView jump;
    ButtonView pause;
    ButtonView homeButton;
    ButtonView homeButton2;
    ButtonView continueButton;
    LiveView liveView;
    TextView scoretextView;
    ContactManager contactManager;
    RecordsListView recordsListView;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        gameSession = new GameSession();
        movingBackgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        bulletArray = new ArrayList<>();
        shoot = new ButtonView(50, 5, 150, 150, GameResources.BUTTON_SHOOT_IMG_PATH);
        jump = new ButtonView(1075, 5, 150,150, GameResources.BUTTON_JUMP_IMG_PATH);
        pause = new ButtonView(1190, 630, 75,75,GameResources.BUTTON_PAUSE_IMG_PATH);
        homeButton = new ButtonView(1190, 630, 75,75,GameResources.EXIT_IMG_PATH);
        homeButton2 = new ButtonView(500, 300, 75,75,GameResources.EXIT_IMG_PATH);
        continueButton = new ButtonView(800, 500, 75,75,GameResources.BUTTON_START_IMG_PATH);
        liveView = new LiveView(600, 700);
        contactManager = new ContactManager(myGdxGame.world);
        scoretextView = new TextView(myGdxGame.commonWhiteFont, 50, 1215);
        recordsListView = new RecordsListView(myGdxGame.commonWhiteFont, 690, 742);
    }
    public void show() {restartGame();}
    private void restartGame() {
//        for (int i = 0; i< trashArray.size(); i++) {
//            myGdxGame.world.destroyBody(trashArray.get(i).body);
//            trashArray.remove(i--);
//        }
//        for (int i = 0; i< superTrashArray.size(); i++) {
//            myGdxGame.world.destroyBody(superTrashArray.get(i).body);
//            superTrashArray.remove(i--);
//        }
        if (kirbyObject != null) {
            myGdxGame.world.destroyBody(kirbyObject.body);
        }
        kirbyObject = new KirbyObject(KIRBY_IMG_PATH, 200, 225, KIRBY_WIDTH, KIRBY_HEIGHT, KIRBY_BIT, myGdxGame.world);
        bulletArray.clear();
        gameSession.startGame();
    }
    @Override
    public void render(float delta) {
        handleInput();
        if (gameSession.state == GameState.PLAYING){
            if (!kirbyObject.isAlive()) {
                gameSession.endGame();
                recordsListView.setRecords(MemoryManager.loadRecordsTable());
            }


        updateBullets();
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
                                GameResources.BULLET_IMG_PATH,kirbyObject.getX()  + kirbyObject.width / 2 + 20, kirbyObject.getY(),
                                GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,

                                myGdxGame.world
                            );
                            bulletArray.add(laserBullet);
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
                    }
                case ENDED:
                    if (homeButton2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
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
        for (BulletObject bullet : bulletArray) bullet.draw(myGdxGame.batch);
        kirbyObject.draw(myGdxGame.batch);
        shoot.draw(myGdxGame.batch);
        jump.draw(myGdxGame.batch);
        pause.draw(myGdxGame.batch);
        liveView.draw(myGdxGame.batch);
        scoretextView.draw(myGdxGame.batch);
        liveView.draw(myGdxGame.batch);

        if (gameSession.state == GameState.PAUSED) {
            homeButton.draw(myGdxGame.batch);
            continueButton.draw(myGdxGame.batch);
        } else if (gameSession.state == GameState.ENDED) {
            recordsListView.draw(myGdxGame.batch);
            homeButton2.draw(myGdxGame.batch);

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

    @Override
    public void dispose() {
    }

}
