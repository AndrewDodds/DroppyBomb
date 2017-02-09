package com.saneandy.droppybomb.game.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;

/**
 * Created by Andrew on 19/10/2016.
 */

public class TriangleData extends BaseElementData {

    public Color[] colour;
    public Vector2 pos;
    public Vector2 pos2;
    public Vector2 pos3;

    @Override
    public Vector2 getRenderPos(Vector2 position) {
        Vector2 midpoint = new Vector2((pos.x+pos2.x+pos3.x)/3f, (pos.y+pos2.y+pos3.y)/3f);

        float x1 = (midpoint.x * scale) + offset.x + position.x;
        float y1 = (midpoint.y * scale) + offset.y + position.y;
        return new Vector2(x1, y1);
    }

    public TriangleData(float x, float y, float x2, float y2, float x3, float y3, Color col1, Color col2, Color col3) {
        super();

        colour = new Color[] {col1, col2, col3};

        pos = new Vector2(x, y);
        pos2 = new Vector2(x2, y2);
        pos3 = new Vector2(x3, y3);

    }

    public TriangleData(float x, float y, float x2, float y2, float x3, float y3, Color col) {
        this(x, y, x2, y2, x3, y3, col, col, col);
    }

    @Override
    public void brighten(float delta) {
        for(Color c : colour) {
            c.mul(1f + (delta * Constants.COLOURCYCLESPEED));
        }
    }

    @Override
    public void darken(float delta) {
        for(Color c : colour) {
            c.mul(1f - (delta * Constants.COLOURCYCLESPEED));
        }

    }

    @Override
    public void fade(float delta) {

        for(Color c : colour) {
            c.add(0.0f, 0.0f, 0.0f, -alpha);
            alpha *= (1f - (delta * Constants.COLOURCYCLESPEED));
            c.add(0.0f, 0.0f, 0.0f, alpha);
        }
    }

    @Override
    public void render(float delta, Vector2 position, ShapeRenderer renderer) {
        update(delta);

        Vector2 midpoint = new Vector2((pos.x+pos2.x+pos3.x)/3f, (pos.y+pos2.y+pos3.y)/3f);

        Vector2 rotPos1 = new Vector2((pos.x-midpoint.x), (pos.y-midpoint.y));
        Vector2 rotPos2 = new Vector2((pos2.x-midpoint.x), (pos2.y-midpoint.y));
        Vector2 rotPos3 = new Vector2((pos3.x-midpoint.x), (pos3.y-midpoint.y));

        rotPos1 = rotPos1.rotate(this.rotation);
        rotPos2 = rotPos2.rotate(this.rotation);
        rotPos3 = rotPos3.rotate(this.rotation);

        rotPos1.x += midpoint.x;
        rotPos1.y += midpoint.y;
        rotPos2.x += midpoint.x;
        rotPos2.y += midpoint.y;
        rotPos3.x += midpoint.x;
        rotPos3.y += midpoint.y;

        float x1 = (rotPos1.x * scale) + offset.x + position.x;
        float y1 = (rotPos1.y * scale) + offset.y + position.y;
        float x2 = (rotPos2.x * scale) + offset.x + position.x;
        float y2 = (rotPos2.y * scale) + offset.y + position.y;
        float x3 = (rotPos3.x * scale) + offset.x + position.x;
        float y3 = (rotPos3.y * scale) + offset.y + position.y;

        renderer.triangle(x1, y1, x2, y2, x3, y3, colour[0], colour[1], colour[2]);
    }

}
