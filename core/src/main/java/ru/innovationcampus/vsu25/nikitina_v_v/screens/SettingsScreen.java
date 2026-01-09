package ru.innovationcampus.vsu25.nikitina_v_v.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

import ru.innovationcampus.vsu25.nikitina_v_v.GameResources;
import ru.innovationcampus.vsu25.nikitina_v_v.GameSettings;
import ru.innovationcampus.vsu25.nikitina_v_v.MyGdxGame;
import ru.innovationcampus.vsu25.nikitina_v_v.managers.MemoryManager;
import ru.innovationcampus.vsu25.nikitina_v_v.views.ButtonView;
import ru.innovationcampus.vsu25.nikitina_v_v.views.ImageView;
import ru.innovationcampus.vsu25.nikitina_v_v.views.MovingBackgroundView;
import ru.innovationcampus.vsu25.nikitina_v_v.views.TextView;

public class SettingsScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    TextView tittleView;
    TextView musicView;
    TextView soundsView;
    TextView clearView;
    ButtonView returnButton;
    ImageView blackoutImageView;


    public SettingsScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        backgroundView = new MovingBackgroundView(GameResources.MENU_BACKGROUND_IMG_PATH);
        tittleView = new TextView(myGdxGame.bigWhiteFont, 325, 500, "Settings");
        musicView = new TextView(myGdxGame.commonWhiteFont, 325, 417, "music: " + "ON");
        soundsView = new TextView(myGdxGame.commonWhiteFont, 325, 358, "sound: " + "ON");
        clearView = new TextView(myGdxGame.commonWhiteFont, 325, 299, "clear records");
        returnButton = new ButtonView(350, 125, 600, 150, GameResources.BUTTON_RETURN_IMG_PATH);
        blackoutImageView = new ImageView(300, 100, 700, 500, GameResources.FULL_PINK_OUT_IMG_PATH);
    }

    @Override
    public void render(float delta) {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();
        handleInput();

        backgroundView.draw(myGdxGame.batch);
        blackoutImageView.draw(myGdxGame.batch);
        tittleView.draw(myGdxGame.batch);
        returnButton.draw(myGdxGame.batch);
        musicView.draw(myGdxGame.batch);
        soundsView.draw(myGdxGame.batch);
        clearView.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }
    private String translateStateToText(boolean state) {
        return state ? "ON" : "OFF";
    }
    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (returnButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.menuScreen);
            }
            if (clearView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveTableOfRecords(new ArrayList<>());
                clearView.setText("clear records");
            }
            if (musicView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveMusicSettings(!MemoryManager.loadIsMusicOn());
                musicView.setText("music: " + translateStateToText(MemoryManager.loadIsMusicOn()));
                myGdxGame.audioManager.updateMusicFlag();
            }
            if (soundsView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveSoundSettings(!MemoryManager.loadIsSoundOn());
                soundsView.setText("sound: " + translateStateToText(MemoryManager.loadIsSoundOn()));
                myGdxGame.audioManager.updateSoundFlag();
            }
        }
    }

}
