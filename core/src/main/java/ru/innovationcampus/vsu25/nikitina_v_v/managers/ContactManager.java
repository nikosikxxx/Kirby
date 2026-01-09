package ru.innovationcampus.vsu25.nikitina_v_v.managers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import ru.innovationcampus.vsu25.nikitina_v_v.GameSettings;
import ru.innovationcampus.vsu25.nikitina_v_v.object.BulletObject;
import ru.innovationcampus.vsu25.nikitina_v_v.object.GameObject;
import ru.innovationcampus.vsu25.nikitina_v_v.object.StarObject;

public class ContactManager {
    World world;
    public ContactManager(World world) {
        this.world = world;

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                int cDef = fixA.getFilterData().categoryBits;
                int cDef2 = fixB.getFilterData().categoryBits;

                if (cDef == GameSettings.OBSTACLES_BIT && cDef2 == GameSettings.KIRBY_BIT
                    || cDef2 == GameSettings.OBSTACLES_BIT && cDef == GameSettings.KIRBY_BIT) {
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).hit();
                }
                if (cDef == GameSettings.OBSTACLES_BIT && cDef2 == GameSettings.BULLET_BIT
                    || cDef2 == GameSettings.OBSTACLES_BIT && cDef == GameSettings.BULLET_BIT) {
                    if (fixA.getUserData() instanceof BulletObject) {
                        ((GameObject) fixA.getUserData()).hit();
                    }
                    if (fixB.getUserData() instanceof BulletObject) {
                        ((GameObject) fixB.getUserData()).hit();
                    }
                }
                if (cDef == GameSettings.STARS_BIT && cDef2 == GameSettings.KIRBY_BIT
                    || cDef2 == GameSettings.STARS_BIT && cDef == GameSettings.KIRBY_BIT
                    || cDef == GameSettings.STARS_BIT && cDef2 == GameSettings.BULLET_BIT
                    || cDef2 == GameSettings.STARS_BIT && cDef == GameSettings.BULLET_BIT) {
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).hit();
                }
                if (cDef == GameSettings.OBSTACLES_BIT && cDef2 == GameSettings.STARS_BIT
                    || cDef2 == GameSettings.OBSTACLES_BIT && cDef == GameSettings.STARS_BIT) {
                    if (fixA.getUserData() instanceof StarObject) {
                        ((GameObject) fixA.getUserData()).hit();
                    }
                    if (fixB.getUserData() instanceof StarObject) {
                        ((GameObject) fixB.getUserData()).hit();
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }
}
