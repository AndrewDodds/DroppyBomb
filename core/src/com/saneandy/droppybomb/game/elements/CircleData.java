package com.saneandy.droppybomb.game.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;

/**
 * Created by Andrew on 19/10/2016.
 */

public class CircleData extends BaseElementData {

    public Color colour;
    public Vector2 pos;
    public float size;
    public int numSegments;

    public CircleData(float x, float y, float radius, Color col, int numSegs) {
        super();

        colour = col;
        pos = new Vector2(x, y);
        size = radius;
        numSegments =  numSegs;
    }

    public CircleData(float x, float y, float radius, Color col) {
        this(x, y, radius, col, Constants.DEFAULT_CIRCLE_SEGS);
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
        colour.add(0.0f, 0.0f, 0.0f, -alpha);
        alpha *= (1f - (delta * Constants.COLOURCYCLESPEED));
        colour.add(0.0f, 0.0f, 0.0f, alpha);
    }

    @Override
    public Vector2 getRenderPos(Vector2 position) {
        float x = pos.x + offset.x + position.x;
        float y = pos.y + offset.y + position.y;

        return new Vector2(x, y);
    }

    @Override
    public void render(float delta, Vector2 position, ShapeRenderer renderer) {
        update(delta);

        renderer.setColor(colour);
        Vector2 calculatedPos = getRenderPos(position);
        float x = calculatedPos.x;
        float y = calculatedPos.y;
        renderer.circle(x, y, (size*scale), numSegments);
    }
}
