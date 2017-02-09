package com.saneandy.droppybomb.game.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;

/**
 * Created by Andrew on 19/10/2016.
 */

public class RectLineData extends BaseElementData {

    public Color colour;
    public Vector2 pos;
    public Vector2 pos2;
    public float thewidth;

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
        Vector2 midpoint = new Vector2(pos.x+pos2.x/2f, pos.y+pos2.y/2f);

        Vector2 rotPos1 = new Vector2((pos.x-midpoint.x), (pos.y-midpoint.y));
        Vector2 rotPos2 = new Vector2((pos2.x-midpoint.x), (pos2.y-midpoint.y));

        rotPos1 = rotPos1.rotate(this.rotation);
        rotPos2 = rotPos2.rotate(this.rotation);

        rotPos1.x *= scale;
        rotPos1.y *= scale;
        rotPos2.x *= scale;
        rotPos2.y *= scale;

        rotPos1.x += (midpoint.x*scale);
        rotPos1.y += midpoint.y;
        rotPos2.x += (midpoint.x*scale);
        rotPos2.y += midpoint.y;

        float x1 = rotPos1.x + offset.x + position.x;
        float y1 = rotPos1.y + offset.y + position.y;
        return new Vector2(x1, y1);

    }

    public RectLineData(float x, float y, float x2, float y2, float width, Color col) {
        super();

        colour =  col;

        pos = new Vector2(x, y);
        pos2 = new Vector2(x2, y2);

        thewidth = width;
    }


    @Override
    public void render(float delta, Vector2 position, ShapeRenderer renderer) {
        update(delta);

        Vector2 midpoint = new Vector2((pos.x+pos2.x)/2f, (pos.y+pos2.y)/2f);

        Vector2 rotPos1 = new Vector2((pos.x-midpoint.x), (pos.y-midpoint.y));
        Vector2 rotPos2 = new Vector2((pos2.x-midpoint.x), (pos2.y-midpoint.y));

        rotPos1 = rotPos1.rotate(this.rotation);
        rotPos2 = rotPos2.rotate(this.rotation);

        rotPos1.x *= scale;
        rotPos1.y *= scale;
        rotPos2.x *= scale;
        rotPos2.y *= scale;

        rotPos1.x += midpoint.x;
        rotPos1.y += midpoint.y;
        rotPos2.x += midpoint.x;
        rotPos2.y += midpoint.y;

        float x1 = rotPos1.x + offset.x + position.x;
        float y1 = rotPos1.y + offset.y + position.y;
        float x2 = rotPos2.x + offset.x + position.x;
        float y2 = rotPos2.y + offset.y + position.y;



        renderer.setColor(colour);
        renderer.rectLine(x1, y1, x2, y2, thewidth * scale);
    }
}
