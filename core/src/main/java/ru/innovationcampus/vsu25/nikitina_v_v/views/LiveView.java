package ru.innovationcampus.vsu25.nikitina_v_v.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.innovationcampus.vsu25.nikitina_v_v.GameResources;

public class LiveView extends View{
    Texture texture;
    Texture texture1;
    int leftLives;
    public LiveView(float x, float y) {
        super(x, y);
        texture = new Texture(GameResources.HEART_IMG_PATH);
        texture1 = new Texture(GameResources.HEART_OBV_IMG_PATH);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        leftLives = 0;
    }
    public void setLeftLives(int leftLives) {
        this.leftLives = leftLives;
    }
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture1,500, 650, 250, 50);
        if (leftLives>0) batch.draw(texture, 499.4f, 650, 60, 50);
        if (leftLives>1) batch.draw(texture,563 ,650, 60, 50);
        if (leftLives>2) batch.draw(texture, 627,650, 60, 50);
        if (leftLives>3) batch.draw(texture, 690,650, 60, 50);
    }
}
