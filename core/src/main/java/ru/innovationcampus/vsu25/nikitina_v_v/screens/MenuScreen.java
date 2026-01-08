package ru.innovationcampus.vsu25.nikitina_v_v.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.innovationcampus.vsu25.nikitina_v_v.GameResources;
import ru.innovationcampus.vsu25.nikitina_v_v.MyGdxGame;
import ru.innovationcampus.vsu25.nikitina_v_v.views.ButtonView;
import ru.innovationcampus.vsu25.nikitina_v_v.views.ImageView;
import ru.innovationcampus.vsu25.nikitina_v_v.views.MovingBackgroundView;

public class MenuScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    ButtonView startButtonView;
    ButtonView settingsButtonView;
    ButtonView exitButtonView;
    ImageView tittleView;

    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        backgroundView = new MovingBackgroundView(GameResources.MENU_BACKGROUND_IMG_PATH);
        tittleView = new ImageView(400,450, 500,250,GameResources.TITTLE_IMG_PATH);
        startButtonView = new ButtonView(350,310,600,150, GameResources.BUTTON_START_IMG_PATH);
        settingsButtonView = new ButtonView(350, 180, 600, 150,  GameResources.BUTTON_SETTINGS_IMG_PATH);
        exitButtonView = new ButtonView(350, 50, 600, 150,  GameResources.EXIT_IMG_PATH);
    }
    @Override
    public void render(float delta) {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        myGdxGame.batch.begin();

        handleInput();

        backgroundView.draw(myGdxGame.batch);
        tittleView.draw(myGdxGame.batch);
        exitButtonView.draw(myGdxGame.batch);
        settingsButtonView.draw(myGdxGame.batch);
        startButtonView.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (startButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)){
                myGdxGame.setScreen(myGdxGame.gameScreen);
            }
            if (exitButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)){
                Gdx.app.exit();
            }
//            if (settingsButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
//                myGdxGame.setScreen(myGdxGame.settingsScreen);
//            }
        }
    }
}
