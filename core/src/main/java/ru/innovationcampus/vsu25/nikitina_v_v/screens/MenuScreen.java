package ru.innovationcampus.vsu25.nikitina_v_v.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.innovationcampus.vsu25.nikitina_v_v.GameResources;
import ru.innovationcampus.vsu25.nikitina_v_v.MyGdxGame;
import ru.innovationcampus.vsu25.nikitina_v_v.views.ButtonView;
import ru.innovationcampus.vsu25.nikitina_v_v.views.MovingBackgroundView;

public class MenuScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    ButtonView startButtonView;
    ButtonView settingsButtonView;
    ButtonView exitButtonView;

    public MenuScreen(MyGdxGame myGdxGame) {
//        this.myGdxGame = myGdxGame;
//        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
//        startButtonView = new ButtonView(140,646,440,70,myGdxGame.pauseButtonFont, GameResources.BUTTON_LONG_IMG_PATH, "start");
//        settingsButtonView = new ButtonView(140, 551, 440, 70, myGdxGame.pauseButtonFont, GameResources.BUTTON_LONG_IMG_PATH, "settings");
//        exitButtonView = new ButtonView(140, 456, 440, 70, myGdxGame.pauseButtonFont, GameResources.BUTTON_LONG_IMG_PATH, "exit");
    }
    @Override
    public void render(float delta) {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        myGdxGame.batch.begin();

        handleInput();

        backgroundView.draw(myGdxGame.batch);
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
