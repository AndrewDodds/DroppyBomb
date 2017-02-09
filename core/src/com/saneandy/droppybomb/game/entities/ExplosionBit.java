package com.saneandy.droppybomb.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.elements.ArcData;
import com.saneandy.droppybomb.game.elements.BaseElementData;
import com.saneandy.droppybomb.game.elements.CircleData;

import java.util.ArrayList;

/**
 * Created by Andrew on 25/10/2016.
 */

public class ExplosionBit implements DroppyBombEntity {

    public static final String TAG = ExplosionBit.class.getName();

    private Vector2 pos;

    private ArrayList<Color> cols;
    private boolean isExploding = true;
    private boolean hasExploded = false;
    private float explodecount;
    private float nextset;
    private int numelements;
    private ArrayList<ExElement> elements;
    private ArrayList<Vector2> elementPos;
    private ArrayList<Vector2> velocity;

    private class ExElement {
        CircleData circ;
        int colIdx;
        float timetoChange;
        float radius;
        Vector2 pos;
        boolean isdead;

        ExElement(Vector2 startPos) {
            pos = new Vector2(startPos.x, startPos.y);

            radius = 1.0f;
            colIdx = 6;
            circ = new CircleData(0.0f, 0.0f, radius, cols.get(colIdx));
            timetoChange = 0.2f;
            isdead = false;
        }

        void update(float delta) {
            radius += delta * 14f;
            timetoChange -= delta;
            while(timetoChange < 0) {
                timetoChange += 0.2f;
                if(colIdx > 0) {
                    colIdx--;
                }
                else {
                    isdead = true;
                }
            }
            circ = new CircleData(0.0f, 0.0f, radius, cols.get(colIdx));
        }

        void render(float delta, ShapeRenderer renderer) {
            circ.render(delta, pos, renderer);
        }
    }

    public ExplosionBit(Vector2 startPos, int numelements) {
        pos = new Vector2(startPos.x,startPos.y);
        elements = new ArrayList<ExElement>();
        velocity = new ArrayList<Vector2>();
        elementPos = new ArrayList<Vector2>();
        isExploding = true;
        hasExploded = false;
        explodecount = 2.5f;
        nextset = 0.1f;
        this.numelements = numelements;

        cols = new ArrayList<Color>();
        cols.add(new Color(1.0f, 0.0f, 0.0f, 1.0f));
        cols.add(new Color(1.0f, 0.4f, 0.0f, 1.0f));
        cols.add(new Color(1.0f, 0.7f, 0.0f, 1.0f));
        cols.add(new Color(1.0f, 1.0f, 0.0f, 1.0f));
        cols.add(new Color(1.0f, 1.0f, 0.4f, 1.0f));
        cols.add(new Color(1.0f, 1.0f, 0.7f, 1.0f));
        cols.add(new Color(1.0f, 1.0f, 1.0f, 1.0f));

        for(int i=0; i<numelements; i++) {
            velocity.add(new Vector2(((float)Math.random()*96f)-48f, 18f));

            float x = startPos.x + ((float)Math.random()*20f);
            float y = startPos.y + ((float)Math.random()*20f);
            elementPos.add(new Vector2(x, y));
            elements.add(new ExElement(new Vector2(x, y)));
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
        for (int i=elements.size()-1; i > -1; i--) {
            elements.get(i).render(delta, renderer);
        }
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(pos.x, pos.y, 20f, 20f);
    }

    @Override
    public void update(float delta) {

        ArrayList<ExElement> newSet = new ArrayList<ExElement>();
        for (ExElement ele: elements) {
            ele.update(delta);
            if(!ele.isdead) {
                newSet.add(ele);
            }
        }

        for(int i=0; i<numelements; i++) {

            elementPos.get(i).x += velocity.get(i).x * delta;
            elementPos.get(i).y += velocity.get(i).y * delta;

            velocity.get(i).y += Constants.G_ACCELERATION  * delta;
        }

        nextset -= delta;
        if(nextset < 0.0f) {
            nextset = 0.1f;
            for(int i=0; i<numelements; i++) {
                newSet.add(new ExElement(new Vector2(elementPos.get(i).x, elementPos.get(i).y)));
            }
        }

        elements = newSet;

        if(isExploding) {
            explodecount -= delta;
            if(explodecount < 0f) {
                hasExploded = true;
            }
            return;
        }



    }

}

