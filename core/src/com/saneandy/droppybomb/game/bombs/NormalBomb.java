package com.saneandy.droppybomb.game.bombs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.DroppyBombRegistry;
import com.saneandy.droppybomb.game.entities.DroppyBombEntity;
import com.saneandy.droppybomb.game.entities.Explosion;
import com.saneandy.droppybomb.game.entities.Smoke;
import com.saneandy.droppybomb.game.elements.BaseElementData;
import com.saneandy.droppybomb.game.elements.CircleData;
import com.saneandy.droppybomb.game.elements.RectLineData;
import com.saneandy.droppybomb.game.elements.TriangleData;

import java.util.ArrayList;

/**
 * Created by Andrew on 19/10/2016.
 */

public class NormalBomb extends Bomb {

    public static final String TAG = NormalBomb.class.getName();

    public NormalBomb(Vector2 startPos) {
        super(startPos);

        bombElements.add(new TriangleData(3f, 20.0f, 2.0f, 10.0f, 8.0f, 10.0f, Color.BLACK, Color.DARK_GRAY, Color.WHITE));
        bombElements.add(new TriangleData(17f, 20.0f, 18.0f, 10.0f, 12.0f, 10.0f, Color.BLACK, Color.DARK_GRAY, Color.WHITE));
        bombElements.add(new CircleData(10.0f, 4.0f, 3f, Color.DARK_GRAY));
        bombElements.add(new CircleData(10.0f, 10.0f, 7f, Color.BLACK));
        bombElements.add(new CircleData(10.0f, 10.0f, 5f, Color.DARK_GRAY));
        bombElements.add(new CircleData(10.0f, 10.0f, 3f, Color.GRAY));
        bombElements.add(new CircleData(10.0f, 10.0f, 1f, Color.LIGHT_GRAY));
        bombElements.add(new RectLineData(10.0f, 20.0f, 10.0f, 11.0f, 1.5f, Color.BLACK));

        for (BaseElementData ele:bombElements) {
            ele.scale =  1.0f;
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

        for (BaseElementData ele:bombElements) {
            ele.setRotationOffsetScaleDeltas((float)Math.random()*2.0f, new Vector2((float)(Math.random()*0.005f)-0.0025f, (float)(Math.random()*0.005f)-0.0025f), 0f );
        }

        Rectangle bbox = getBoundingBox();

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

        if(isStatic) {
            return;
        }

        smokecount -= delta;

        pos.y += descent * delta;
        descent += Constants.G_ACCELERATION*delta;

        if(isExploding) {
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
            }
            smokecount = -0.1f;
            return;
        }


    }

}
