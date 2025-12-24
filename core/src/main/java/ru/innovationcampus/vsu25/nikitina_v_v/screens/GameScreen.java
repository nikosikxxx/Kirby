package ru.innovationcampus.vsu25.nikitina_v_v.screens;

import static ru.innovationcampus.vsu25.nikitina_v_v.GameResources.KIRBY_IMG_PATH;
import static ru.innovationcampus.vsu25.nikitina_v_v.GameSettings.KIRBY_BIT;
import static ru.innovationcampus.vsu25.nikitina_v_v.GameSettings.KIRBY_HEIGHT;
import static ru.innovationcampus.vsu25.nikitina_v_v.GameSettings.KIRBY_WIDTH;
import static ru.innovationcampus.vsu25.nikitina_v_v.GameSettings.SCREEN_HEIGHT;
import static ru.innovationcampus.vsu25.nikitina_v_v.GameSettings.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.innovationcampus.vsu25.nikitina_v_v.GameResources;
import ru.innovationcampus.vsu25.nikitina_v_v.GameSettings;
import ru.innovationcampus.vsu25.nikitina_v_v.MyGdxGame;
import ru.innovationcampus.vsu25.nikitina_v_v.object.KirbyObject;
import ru.innovationcampus.vsu25.nikitina_v_v.views.MovingBackgroundView;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    MovingBackgroundView movingBackgroundView;
    KirbyObject kirbyObject;
    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        kirbyObject = new KirbyObject(KIRBY_IMG_PATH, 250, 275, KIRBY_WIDTH, KIRBY_HEIGHT, KIRBY_BIT, myGdxGame.world);
        movingBackgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
    }
    public void show() {}
    @Override
    public void render(float delta) {
        handleInput();
        movingBackgroundView.move();
        myGdxGame.stepWorld();
        draw();
    }
    private void handleInput() {
        if (Gdx.input.isTouched()) {
            kirbyObject.kick();
        }
    }
    public void draw() {
        ScreenUtils.clear(0,0,0,0);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();


        movingBackgroundView.draw(myGdxGame.batch);
        kirbyObject.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }
    @Override
    public void dispose() {
    }

}
