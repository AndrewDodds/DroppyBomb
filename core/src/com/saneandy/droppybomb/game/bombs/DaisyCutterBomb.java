package com.saneandy.droppybomb.game.bombs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
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

public class DaisyCutterBomb extends Bomb {

    public static final String TAG = DaisyCutterBomb.class.getName();



    private Rectangle explodeRect;

    public DaisyCutterBomb(Vector2 startPos) {

        super(startPos);

        Color orange = Color.ORANGE.cpy();
        Color orange2 = Color.ORANGE.cpy();
        Color yellow = Color.YELLOW.cpy();
        Color black = Color.BLACK.cpy();
        Color red = Color.RED.cpy();

        bombElements.add(new TriangleData(2f, 16.0f, 6.0f, 20.0f, 10.0f, 16.0f, orange));
        bombElements.add(new TriangleData(2f, 16.0f, 6.0f, 12.0f, 10.0f, 16.0f, orange));

        bombElements.add(new TriangleData(18f, 16.0f, 14.0f, 20.0f, 10.0f, 16.0f, orange));
        bombElements.add(new TriangleData(18f, 16.0f, 14.0f, 12.0f, 10.0f, 16.0f, orange));

        bombElements.add(new TriangleData(10f, 16.0f, 6.0f, 12.0f, 14.0f, 12.0f, yellow));

        bombElements.add(new RectData(0f, 2.0f, 20f, 10f, black));
        bombElements.add(new RectData(1f, 3.0f, 18f, 8f, orange2));

        bombElements.add(new TriangleData(5f, 2.0f, 15f, 2.0f, 10.0f, 0.0f, yellow, red, orange));

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


    private void explodeData() {
        bombElements = new ArrayList<BaseElementData>();

        bombElements.add(new RectData(explodeRect.x, explodeRect.y, explodeRect.width, explodeRect.height, Color.ORANGE));
        bombElements.add(new RectData(explodeRect.x+1f, explodeRect.y+0.5f, explodeRect.width-2f, explodeRect.height-1f, Color.YELLOW));
        bombElements.add(new RectData(explodeRect.x+3, explodeRect.y+1, explodeRect.width-6f, explodeRect.height-2f, Color.WHITE));
    }

    @Override
    public void explode() {
        if(isExploding) {
            return;
        }
        smokecount = -0.1f;
        isExploding = true;

        explodeRect = new Rectangle(0f, 4f, 20f, 6f);

        explodeData();

        for (BaseElementData ele:bombElements) {
            ele.scale = 1.0f;
        }

        Rectangle bbox = getBoundingBox();
        bbox.x -= Constants.BLOCK_SIZE *5f;
        bbox.width += Constants.BLOCK_SIZE *10f;

        if(DroppyBombRegistry.getLand() != null) {
            for(DroppyBombEntity dpe : DroppyBombRegistry.getLand().getLandEntities()) {
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
            for(float y=bbox.y+(float)(Math.random()*7f); y <bbox.y+bbox.height ;y += Constants.BLOCK_SIZE +(float)(Math.random()*7f) - 3.5f ) {
                DroppyBombRegistry.addElement(new Explosion(new Vector2(x, y), new Vector2(0f, -0.1f)));
            }
        }


    }

    @Override
    public void update(float delta) {
        for (BaseElementData ele:bombElements) {
            ele.update(delta);

            if(bombElements.indexOf(ele) == 6) {
                isdarken -= delta;
                if (isdarken > 0.0f) {
                    ele.darken(delta);
                } else {
                    ele.brighten(delta);
                    if (isdarken < -1.0f) {
                        isdarken = 1.0f;
                    }
                }
            }
        }

        if(isStatic) {
            return;
        }

        if(isExploding) {
            explodeRect.x -= 90f*delta;
            explodeRect.width += 180f*delta;

            explodeRect.y -= 20*delta;
            explodeRect.width += 40f*delta;
            explodeData();
        }

        smokecount -= delta;

        if(pos.y > Constants.LAND_HEIGHT && !isExploding) {
            pos.y += descent * delta;
            descent += Constants.G_ACCELERATION*delta;
        }

        if(isExploding) {
            for (BaseElementData ele:bombElements) {
                ele.scale *= (1f + (2.7*delta));
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
        }


    }

}
