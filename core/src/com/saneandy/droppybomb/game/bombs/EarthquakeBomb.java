package com.saneandy.droppybomb.game.bombs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.DroppyBombRegistry;
import com.saneandy.droppybomb.game.elements.BaseElementData;
import com.saneandy.droppybomb.game.elements.RectData;
import com.saneandy.droppybomb.game.elements.TriangleData;
import com.saneandy.droppybomb.game.entities.DroppyBombEntity;
import com.saneandy.droppybomb.game.entities.Explosion;
import com.saneandy.droppybomb.game.entities.Smoke;

import java.util.ArrayList;

/**
 * Created by Andrew on 19/10/2016.
 */

public class EarthquakeBomb extends Bomb {

    public static final String TAG = EarthquakeBomb.class.getName();

    private Vector2 offset;

    public EarthquakeBomb(Vector2 startPos) {
        super(startPos);
        explodecount = 2f;

        offset = new Vector2(0.0f, 0.0f);

        bombElements.add(new TriangleData(5f, 17.0f, 7.0f, 20.0f, 7.0f, 14.0f, Color.BLACK, Color.BLACK, Color.WHITE));
        bombElements.add(new TriangleData(15f, 17.0f, 12.0f, 20.0f, 12.0f, 14.0f, Color.BLACK, Color.BLACK, Color.WHITE));
        bombElements.add(new RectData(7f, 4.0f, 6.0f, 16f, Color.BLACK, Color.WHITE, Color.BLACK, Color.WHITE));
        bombElements.add(new RectData(2f, 0.0f, 18f, 4f, Color.BLACK, Color.WHITE, Color.BLACK, Color.WHITE));

        bombElements.add(new TriangleData(5f, 4.0f, 7.0f, 4.0f, 7.0f, 6.0f, Color.RED));
        bombElements.add(new TriangleData(15f, 4.0f, 13.0f, 4.0f, 13.0f, 6.0f, Color.RED));

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

        if(DroppyBombRegistry.getLand() != null) {
            DroppyBombRegistry.getLand().startEarthquake();
        }
        smokecount = -0.1f;

        for (BaseElementData ele:bombElements) {
            ele.setRotationOffsetScaleDeltas((float)Math.random()*2.0f, new Vector2((float)(Math.random()*0.005f)-0.0025f, (float)(Math.random()*0.005f)-0.0025f), 0f );
        }

    }

    @Override
    public void render(float delta, ShapeRenderer renderer) {
        Vector2 renderPos = pos.cpy();
        renderPos.x += offset.x;
        renderPos.y += offset.y;
        for (BaseElementData ele:bombElements) {
            ele.render(delta, renderPos, renderer);
        }
   }


    @Override
    public void update(float delta) {
        for (BaseElementData ele:bombElements) {
            ele.update(delta);
        }

        isdarken -= 10f*delta;
        if (isdarken < 0.0f) {
            isdarken = 1.0f;
            offset = new Vector2(((float)Math.random()*4f) - 2f,((float)Math.random()*4f) - 2f);
        }

        if(isStatic) {
            return;
        }

        smokecount -= delta;

        if(pos.y > Constants.LAND_HEIGHT) {
            pos.y += descent * delta;
            descent += Constants.G_ACCELERATION*delta;
        }

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
                smokecount = -0.1f;
            }
            return;
        }


    }

}
