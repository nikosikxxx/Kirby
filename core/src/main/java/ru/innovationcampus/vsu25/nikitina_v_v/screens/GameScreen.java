package ru.innovationcampus.vsu25.nikitina_v_v.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.innovationcampus.vsu25.nikitina_v_v.GameResources;
import ru.innovationcampus.vsu25.nikitina_v_v.GameSettings;
import ru.innovationcampus.vsu25.nikitina_v_v.MyGdxGame;
import ru.innovationcampus.vsu25.nikitina_v_v.views.MovingBackgroundView;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    MovingBackgroundView movingBackgroundView;
    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        movingBackgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
    }
    public void show() {}
    @Override
    public void render(float delta) {
        movingBackgroundView.move();
        draw();
    }
    public void draw() {
        ScreenUtils.clear(0,0,0,0);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();

        movingBackgroundView.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }
    @Override
    public void dispose() {
    }

}
