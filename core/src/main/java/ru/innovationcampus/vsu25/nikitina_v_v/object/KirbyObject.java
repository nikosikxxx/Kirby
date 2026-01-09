package ru.innovationcampus.vsu25.nikitina_v_v.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

import ru.innovationcampus.vsu25.nikitina_v_v.GameSettings;

public class KirbyObject extends GameObject{
    int livesLeft;
    long lastShotTime;

    public KirbyObject(String texturePath, int x, int y, int width, int height,short cBits, World world) {
        super(texturePath, x, y, width, height, GameSettings.KIRBY_BIT, world);


        livesLeft = 4;
    }
    public int getLivesLeft() {
        return livesLeft;
    }
    public boolean canShoot(){
        if(TimeUtils.millis() - lastShotTime >= GameSettings.SHOOTING_COOL_DOWN){
            lastShotTime = TimeUtils.millis();
            return true;
        }
        return false;
    }

    private void putInFrame() {

        if (getY() > (425)) {
            setY(425);
        }
        if (getY() < 285) {
            setY(285);
        }
        if (getX() < (200)) {
            setX(200);
        }
        if (getX() >= (GameSettings.SCREEN_WIDTH + width / 2f)) {
            setX(0);
        }

    }
    public void move(Vector3 vector3) {
        float fx = (vector3.x - getX()) * GameSettings.SHIP_FORCE_RATIO;
        float fy = (vector3.y - getY()) * GameSettings.SHIP_FORCE_RATIO;
    }
        public void jump() {
        body.setLinearVelocity(new Vector2( 0, GameSettings.KIRBY_VELOCITY));
            body.setBullet(true);
//        body.applyLinearImpulse(new Vector2(0, 20f),new Vector2(getX(), getY()), true);
    }


    @Override
    public void hit() {
        livesLeft -= 1;
    }
    public boolean isAlive() {
        return livesLeft > 0;
    }
    @Override
    public void draw(SpriteBatch batch) {
        putInFrame();
        super.draw(batch);
    }
}
