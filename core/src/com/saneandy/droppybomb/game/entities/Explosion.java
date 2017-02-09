package com.saneandy.droppybomb.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.game.elements.ArcData;
import com.saneandy.droppybomb.game.elements.BaseElementData;

import java.util.ArrayList;

/**
 * Created by Andrew on 25/10/2016.
 */

public class Explosion implements DroppyBombEntity {

    public static final String TAG = Plane.class.getName();

    private Vector2 pos;
    private Vector2 velocity;
    private Color red;
    private Color yellow;
    private boolean isExploding = true;
    private boolean hasExploded = false;
    private float explodecount;
    private ArrayList<BaseElementData> smokeElements;
    private ExplosionBit eBit;

    public Explosion(Vector2 startPos, Vector2 startvelocity) {
        pos = new Vector2(startPos.x,startPos.y);
        velocity = startvelocity;
        isExploding = true;
        hasExploded = false;
        explodecount = 1.7f;
        smokeElements = new ArrayList<BaseElementData>();

        red = new Color(1.0f, 0.0f, 0.0f, 0.6f);
        yellow = new Color(1.0f, 1.0f, 0.0f, 0.6f);

        smokeElements.add(new ArcData(10f, 13.0f, 0.5f, 340.0f, 210.0f, red));
        smokeElements.add(new ArcData(12f, 11.0f, 0.6f, 280.0f, 210.0f, red));
        smokeElements.add(new ArcData(12f, 9.0f, 0.4f, 200.0f, 210.0f, red));
        smokeElements.add(new ArcData(10f, 7.0f, 0.35f, 140.0f, 210.0f, red));
        smokeElements.add(new ArcData(8f, 9.0f, 0.6f, 80.0f, 210.0f, red));
        smokeElements.add(new ArcData(8f, 11.0f, 0.5f, 20.0f, 210.0f, red));

        smokeElements.add(new ArcData(10f, 12.0f, 0.5f, 320.0f, 210.0f, yellow));
        smokeElements.add(new ArcData(11f, 10.5f, 0.6f, 300.0f, 210.0f, yellow));
        smokeElements.add(new ArcData(11f, 9.5f, 0.4f, 220.0f, 210.0f, yellow));
        smokeElements.add(new ArcData(10f, 8.0f, 0.35f, 160.0f, 210.0f, yellow));
        smokeElements.add(new ArcData(9f, 9.5f, 0.6f, 100.0f, 210.0f, yellow));
        smokeElements.add(new ArcData(9f, 10.5f, 0.5f, 40.0f, 210.0f, yellow));

        for (BaseElementData ele:smokeElements) {
            ele.scale = 1.0f;
        }
        for (BaseElementData ele:smokeElements) {
            ele.setRotationOffsetScaleDeltas((float)Math.random()*42f, new Vector2(0f, 0f), 4.077f );
        }

        eBit = new ExplosionBit(startPos, 4);
    }

    @Override
    public boolean getIsExploding() {
        return isExploding;
    }

    @Override
    public boolean getHasExploded() {
        return hasExploded;
    }

    @Override
    public void explode() {
        if(isExploding) {
            return;
        }
        isExploding = true;
        hasExploded = false;
    }

    @Override
    public void render(float delta, ShapeRenderer renderer) {
        for (BaseElementData ele:smokeElements) {
            ele.render(delta, pos, renderer);
        }
        eBit.render(delta, renderer);
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(pos.x, pos.y, 20f, 20f);
    }

    @Override
    public void update(float delta) {
        eBit.update(delta);

        for (BaseElementData ele:smokeElements) {
            ele.update(delta);
        }

        pos.y +=  velocity.y * delta;
        pos.x +=  velocity.x * delta;

        if(isExploding) {
            explodecount -= delta;
            if(explodecount < 0f) {
                hasExploded = true;
            }
            return;
        }




    }

}

