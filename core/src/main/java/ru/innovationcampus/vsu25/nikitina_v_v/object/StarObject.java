package ru.innovationcampus.vsu25.nikitina_v_v.object;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import ru.innovationcampus.vsu25.nikitina_v_v.GameSettings;

public class StarObject extends GameObject{
    int livesLeft;
    public StarObject(String texturePath, int width, int height, World world) {
        super(texturePath, GameSettings.SCREEN_WIDTH + width,
            235, width, height, GameSettings.OBSTACLES_BIT, world);
        body.setLinearVelocity(new Vector2(-GameSettings.OBSTACLES_VELOCITY-2,0 ));
        body.setGravityScale(0f);
        body.setType(BodyDef.BodyType.KinematicBody);
        livesLeft = 1;

    }
    public boolean isInFrame() {
        return getY() > 0;
    }
    @Override
    public void hit(){
        livesLeft -=1;

    }
    public boolean isAlive() {
        return livesLeft > 0;
    }
}
