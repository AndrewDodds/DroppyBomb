package com.saneandy.droppybomb.game.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;

/**
 * Created by Andrew on 19/10/2016.
 */

public class ArcData extends BaseElementData {

    public static final String TAG = ArcData.class.getName();

    public Color colour;
    public Vector2 pos;
    public float size;
    public float start;
    public float deg;
    public int numSegments;

    public ArcData(float x, float y, float radius, float startDeg, float degrees, Color col, int numSegs) {
        super();

        colour = col;
        pos = new Vector2(x, y);
        size = radius;
        start = startDeg;
        deg = degrees;
        numSegments =  numSegs;
    }

    public ArcData(float x, float y, float radius, float startDeg, float degrees, Color col) {
        this(x, y, radius, startDeg, degrees, col, Constants.DEFAULT_CIRCLE_SEGS);
    }

    @Override
    public Vector2 getRenderPos(Vector2 position) {
        float x = (((pos.x-10.0f)*scale)+10.0f) + offset.x + position.x;
        float y = (((pos.y-10.0f)*scale)+10.0f) + offset.y + position.y;

        return new Vector2(x, y);
    }

    @Override
    public void brighten(float delta) {
        colour = colour.mul(1f + (delta * Constants.COLOURCYCLESPEED));
    }

    @Override
    public void darken(float delta) {

        colour = colour.mul(1f - (delta * Constants.COLOURCYCLESPEED));

    }

    @Override
    public void fade(float delta) {
        //colour.add(0.0f, 0.0f, 0.0f, -alpha);
        colour.a *=  (1f - (delta * Constants.COLOURCYCLESPEED));

        //Gdx.app.log(TAG, "Alpha:" + colour.a);
        //alpha *= (1f - (delta * Constants.COLOURCYCLESPEED));
        //colour.add(0.0f, 0.0f, 0.0f, alpha);
    }

    @Override
    public void render(float delta, Vector2 position, ShapeRenderer renderer) {
        update(delta);

        renderer.setColor(colour);
        Vector2 calculatedPos = getRenderPos(position);
        float x = calculatedPos.x;
        float y = calculatedPos.y;
        renderer.arc(x, y, (size*scale), start, deg, numSegments);
    }
}
