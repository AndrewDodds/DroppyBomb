package com.saneandy.droppybomb.game.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;

/**
 * Created by Andrew on 19/10/2016.
 */

public class RectData extends BaseElementData {

    public Color[] colour;
    public Vector2 pos;
    public Vector2 size;

    public RectData(float x, float y, float width, float height, Color col1, Color col2, Color col3, Color col4) {
        super();

        colour = new Color[] {col1, col2, col3, col4};

        pos = new Vector2(x, y);
        size = new Vector2(width, height);
    }

    public RectData(float x, float y, float width, float height, Color col1) {
        this(x, y, width, height, col1, col1, col1, col1);
    }

    @Override
    public Vector2 getRenderPos(Vector2 position) {

        float x = pos.x + offset.x + position.x; //- (float)(size.x*0.5*scale);
        float y = pos.y + offset.y + position.y; //- (float)(size.y*0.5*scale);
        return new Vector2(x, y);
    }

    @Override
    public void render(float delta, Vector2 position, ShapeRenderer renderer) {
        update(delta);

        Vector2 calculatedPos = getRenderPos(position);
        float x = calculatedPos.x;
        float y = calculatedPos.y;
        renderer.rect(x, y, (size.x*scale), (size.y*scale), colour[0], colour[1], colour[2], colour[3]);

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

}

