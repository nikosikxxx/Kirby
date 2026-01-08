package ru.innovationcampus.vsu25.nikitina_v_v.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ImageView extends View{
    Texture texture;

    public ImageView(float x, float y, float width, float height, String ImagePath) {
        super(x,y, width, height);
        texture = new Texture(ImagePath);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }
    @Override
    public void dispose() {
        texture.dispose();
    }
}
