package ru.innovationcampus.vsu25.nikitina_v_v.object;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ru.innovationcampus.vsu25.nikitina_v_v.GameSettings;

public class BulletObject extends GameObject{
    private  boolean wasHit;
    public BulletObject(int x, int y, int width, int height, String texturePath, World world) {
        super(texturePath, x, y, width, height, GameSettings.BULLET_BIT, world);
        body.setLinearVelocity(new Vector2(GameSettings.BULLET_VELOCITY, 0));
        body.setBullet(true);
        wasHit = false;
    }
    public boolean hasToBeDestroyed(){
        return wasHit || (getX() + width / 2 > GameSettings.SCREEN_WIDTH);
    }
    @Override
    public void hit(){
        wasHit = true;
    }

}
