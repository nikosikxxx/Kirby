package ru.innovationcampus.vsu25.nikitina_v_v.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

import ru.innovationcampus.vsu25.nikitina_v_v.GameSettings;

public class KirbyObject extends GameObject{
    int livesleft;
    long lastShotTime;

    public KirbyObject(String texturePath, int x, int y, int width, int height,short cBits, World world) {
        super(texturePath, x, y, width, height, GameSettings.KIRBY_BIT, world);
        livesleft = 3;
    }
    public int getLivesleft() {
        return livesleft;
    }
    public boolean canShoot(){
        if(TimeUtils.millis() - lastShotTime >= GameSettings.SHOOTING_COOL_DOWN){
            lastShotTime = TimeUtils.millis();
            return true;
        }
        return false;
    }

    private void putInFrame() {
        if (getY() < 265) {
            setY(265);
        }
        if (getY() <= (height/2)) {
            setY(height/2);
        }
        if (getX() < ( -width / 2f)) {
            setX(GameSettings.SCREEN_WIDTH);
        }
        if (getX() >= (GameSettings.SCREEN_WIDTH + width / 2f)) {
            setX(0);
        }

    }
    public void move(Vector3 vector3) {
        float fx = (vector3.x - getX()) * GameSettings.SHIP_FORCE_RATIO;
        float fy = (vector3.y - getY()) * GameSettings.SHIP_FORCE_RATIO;
    }
        public void kick() {
        body.applyLinearImpulse(new Vector2(0, 2f),new Vector2(getX(), getY()), true);
    }
    @Override
    public void hit() {
        livesleft -= 1;
    }
    @Override
    public void draw(SpriteBatch batch) {
        putInFrame();
        super.draw(batch);
    }
}
