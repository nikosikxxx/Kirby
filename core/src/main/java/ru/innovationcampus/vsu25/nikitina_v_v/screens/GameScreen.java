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
import ru.innovationcampus.vsu25.nikitina_v_v.GameSettings;
import ru.innovationcampus.vsu25.nikitina_v_v.MyGdxGame;
import ru.innovationcampus.vsu25.nikitina_v_v.object.BulletObject;
import ru.innovationcampus.vsu25.nikitina_v_v.object.KirbyObject;
import ru.innovationcampus.vsu25.nikitina_v_v.views.ButtonView;
import ru.innovationcampus.vsu25.nikitina_v_v.views.MovingBackgroundView;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    MovingBackgroundView movingBackgroundView;
    KirbyObject kirbyObject;
    ArrayList<BulletObject> bulletArray;
    ButtonView shoot;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        kirbyObject = new KirbyObject(KIRBY_IMG_PATH, 250, 275, KIRBY_WIDTH, KIRBY_HEIGHT, KIRBY_BIT, myGdxGame.world);
        movingBackgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        bulletArray = new ArrayList<>();
        shoot = new ButtonView(20, 200, 200, 75, GameResources.BUTTON_IMG_PATH);
    }
    public void show() {bulletArray.clear();}
    @Override
    public void render(float delta) {
        handleInput();

        updateBullets();
        movingBackgroundView.move();
        myGdxGame.stepWorld();
        draw();
    }
    private void handleInput() {
        if (Gdx.input.isTouched()) {
//            kirbyObject.kick();
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (shoot.isHit(myGdxGame.touch.x, myGdxGame.touch.y)){
                if (kirbyObject.canShoot()) {
                    BulletObject laserBullet = new BulletObject(
                        kirbyObject.getX()  + kirbyObject.width / 2, kirbyObject.getY(),
                        GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,
                        GameResources.BULLET_IMG_PATH,
                        myGdxGame.world
                    );
                    bulletArray.add(laserBullet);
                }
//            if (myGdxGame.audioManager.isSoundOn) {
//                myGdxGame.audioManager.shootSound.play(0.2f);
//            }
            }
            kirbyObject.move(myGdxGame.touch);
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
