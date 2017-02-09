package com.saneandy.droppybomb.game.bombs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.DroppyBombRegistry;
import com.saneandy.droppybomb.game.elements.BaseElementData;
import com.saneandy.droppybomb.game.elements.CircleData;
import com.saneandy.droppybomb.game.elements.RectData;
import com.saneandy.droppybomb.game.elements.TriangleData;
import com.saneandy.droppybomb.game.entities.DroppyBombEntity;
import com.saneandy.droppybomb.game.entities.Explosion;
import com.saneandy.droppybomb.game.entities.Smoke;

import java.util.ArrayList;

/**
 * Created by Andrew on 19/10/2016.
 */

public class ClusterBomb extends Bomb {

    public static final String TAG = ClusterBomb.class.getName();

    private float xvelocity;

    private Color red;
    private Color green;

    public ClusterBomb(Vector2 startPos, float xvel) {

        super(startPos);

        xvelocity = xvel;

        green = new Color(0.0f, 1.0f, 0.0f, 1.0f);
        red = new Color(1.0f, 0.0f, 0.0f, 1.0f);


        bombElements.add(new RectData(4f, 12.0f, 3f, 6f, red, green, red, green));
        bombElements.add(new TriangleData(4f, 18.0f, 4.0f, 20.0f, 7f, 18.0f, red));

        bombElements.add(new RectData(13f, 12.0f, 3f, 6f, red, green, red, green));
        bombElements.add(new TriangleData(13f, 18.0f, 16.0f, 20.0f, 16f, 18.0f, red));

        bombElements.add(new CircleData(10.0f, 14.0f, 3f, red));
        bombElements.add(new CircleData(10.0f, 3.0f, 3f, red));

        bombElements.add(new RectData(7f, 3.0f, 6f, 11f, red, green, red, green));

        for (BaseElementData ele:bombElements) {
            ele.scale = 1.0f;
        }
    }


    @Override
    public ArrayList<DroppyBombEntity> getSmoke() {
        ArrayList<DroppyBombEntity> retVal = new ArrayList<DroppyBombEntity>();
        retVal.add(new Smoke(pos.cpy(), new Vector2(0f, 0f), Color.GRAY));
        if(isExploding) {
            retVal.add(new Explosion(pos.cpy(), new Vector2(0f, 0f)));

        }
        return retVal;
    }

    @Override
    public void explode() {
        if(isExploding) {
            return;
        }
        smokecount = -0.1f;
        isExploding = true;

        bombElements = new ArrayList<BaseElementData>();

        bombElements.add(new CircleData(10.0f, 10.0f, 9f, Color.RED));
        bombElements.add(new CircleData(10.0f, 10.0f, 7f, Color.ORANGE));
        bombElements.add(new CircleData(10.0f, 10.0f, 5f, Color.YELLOW));
        bombElements.add(new CircleData(10.0f, 10.0f, 3f, Color.WHITE));

        for (BaseElementData ele:bombElements) {
            ele.scale = 1.0f;
        }

        Rectangle bbox = getBoundingBox();
        bbox.x -= Constants.BLOCK_SIZE *1.5f;
        bbox.y -= Constants.BLOCK_SIZE *1.5f;
        bbox.width += Constants.BLOCK_SIZE *3f;
        bbox.height += Constants.BLOCK_SIZE *3f;

        if(DroppyBombRegistry.getLand() != null) {
            for (DroppyBombEntity dpe : DroppyBombRegistry.getLand().getLandEntities()) {
                if (dpe.getBoundingBox().overlaps(bbox) && !dpe.getIsExploding()) {
                    addScore();
                    dpe.explode();
                }
            }
        }

        // Explode the plane if in range!
        DroppyBombEntity dpe = DroppyBombRegistry.getElementSet().get(0);
        if (dpe.getBoundingBox().overlaps(bbox)) {

            dpe.explode();
        }

        for(float x=bbox.x+(float)(Math.random()*7f); x <bbox.x+bbox.width ;x += Constants.BLOCK_SIZE +(float)(Math.random()*7f) - 3.5f ) {
            for(float y=bbox.y+(float)(Math.random()*7f); y <bbox.y+bbox.width ;y += Constants.BLOCK_SIZE +(float)(Math.random()*7f) - 3.5f ) {
                DroppyBombRegistry.addElement(new Explosion(new Vector2(x, y), new Vector2(0f, -0.1f)));
            }
        }


    }


    @Override
    public void update(float delta) {
        for (BaseElementData ele:bombElements) {
            ele.update(delta);

        }

        isdarken -= delta * 4f;
        if (isdarken > 0.0f) {
            red = red.mul(1f - (delta * Constants.COLOURCYCLESPEED));
            green = green.mul(1f + (delta * Constants.COLOURCYCLESPEED));
        } else {
            green = green.mul(1f - (delta * Constants.COLOURCYCLESPEED));
            red = red.mul(1f + (delta * Constants.COLOURCYCLESPEED));
            if (isdarken < -1.0f) {
                isdarken = 1.0f;
                green.r = 0.0f; green.g = 1.0f; green.b = 0.0f; green.a = 1.0f;
                red.r = 1.0f; red.g = 0.0f; red.b = 0.0f; red.a = 1.0f;
            }
        }

        if(isStatic) {
            return;
        }

        smokecount -= delta;

        if(pos.y > Constants.LAND_HEIGHT && !isExploding) {
            pos.y += descent * delta;
            pos.x += xvelocity * delta;
            descent += Constants.G_ACCELERATION*delta;
        }

        if(isExploding) {
            for (BaseElementData ele:bombElements) {
                ele.scale *= (1f + (2.4*delta));

            }
            explodecount -= delta;
            if(explodecount < 0f) {
                setHasExploded();
            }
            return;
        }

        if(pos.y <= Constants.LAND_HEIGHT) {
            if(!isExploding) {
                explode();
                explodecount = 1.0f;
                smokecount = -0.1f;
            }
            return;
        }


    }

}
