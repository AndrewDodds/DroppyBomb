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

public class Smoke implements DroppyBombEntity {

    public static final String TAG = com.saneandy.droppybomb.game.entities.Plane.class.getName();

    private Vector2 pos;
    private Vector2 velocity;
    private Color col;
    private boolean isExploding = true;
    private boolean hasExploded = false;
    private float explodecount;
    private ArrayList<BaseElementData> smokeElements;

    public Smoke(Vector2 startPos, Vector2 startvelocity, Color colour) {
        pos = startPos;
        velocity = startvelocity;
        isExploding = true;
        hasExploded = false;
        explodecount = 1.1f;
        col = colour;
        smokeElements = new ArrayList<BaseElementData>();

        smokeElements.add(new ArcData(10f, 13.0f, 1f, 340.0f, 210.0f, colour));
        smokeElements.add(new ArcData(12f, 11.0f, 1f, 280.0f, 210.0f, colour));
        smokeElements.add(new ArcData(12f, 9.0f, 1f, 200.0f, 210.0f, colour));
        smokeElements.add(new ArcData(10f, 7.0f, 1f, 140.0f, 210.0f, colour));
        smokeElements.add(new ArcData(8f, 9.0f, 1f, 80.0f, 210.0f, colour));
        smokeElements.add(new ArcData(8f, 11.0f, 1f, 20.0f, 210.0f, colour));

        for (BaseElementData ele:smokeElements) {
            ele.scale = 1.0f;
        }
        for (BaseElementData ele:smokeElements) {
           // ele.setRotationOffsetScaleDeltas((float)Math.random()*0.2f, new Vector2(0f, 0f), 0.01f );
            ele.setRotationOffsetScaleDeltas((float)Math.random()*12f, new Vector2(0f, 0f), 0.6f );
        }
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
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(pos.x, pos.y, 20f, 20f);
    }

    @Override
    public void update(float delta) {

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

