package ru.innovationcampus.vsu25.nikitina_v_v.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.swing.text.View;

import ru.innovationcampus.vsu25.nikitina_v_v.GameSettings;

public class MovingBackgroundView {
    Texture texture;
    int texture1X;
    int texture2X;
    int speed = 2;
    public MovingBackgroundView(String pathToTexture) {
        texture1X = 0;
        texture2X = GameSettings.SCREEN_WIDTH;
        texture = new Texture(pathToTexture);
    }

    public void move() {
        texture1X -= speed;
        texture2X -= speed;
        if (texture1X <= -GameSettings.SCREEN_WIDTH) {
            texture1X = GameSettings.SCREEN_WIDTH;
        }
        if (texture2X <= -GameSettings.SCREEN_WIDTH) {
            texture2X = GameSettings.SCREEN_WIDTH;
        }
    }
    public void draw(SpriteBatch batch) {
        batch.draw(texture, texture1X, 0, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        batch.draw(texture, texture2X, 0, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
    }
    public void dispose() {
        texture.dispose();
    }
}
