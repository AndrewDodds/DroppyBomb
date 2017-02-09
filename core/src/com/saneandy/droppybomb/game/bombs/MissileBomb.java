package com.saneandy.droppybomb.game.bombs;

import com.badlogic.gdx.graphics.Color;
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

public class MissileBomb extends Bomb {

    public static final String TAG = MissileBomb.class.getName();

    private float speedright;
    private float starty;
    private Color black = Color.BLACK.cpy();

    public MissileBomb(Vector2 startPos) {
        super(startPos);
        speedright = 120.0f;
        descent = -15.0f;
        starty = startPos.y+10f;

        bombElements.add(new TriangleData(4f, 7.0f, 7.0f, 7.0f, 4f, 5.0f, Color.GOLDENROD, Color.FIREBRICK, Color.GOLD));
        bombElements.add(new TriangleData(4f, 13.0f, 7.0f, 13.0f, 4f, 15.0f, Color.GOLDENROD, Color.FIREBRICK, Color.GOLD));

        bombElements.add(new CircleData(16.0f, 10.0f, 3f, black));
        bombElements.add(new CircleData(4.0f, 10.0f, 3f, Color.RED));

        bombElements.add(new RectData(4f, 7.0f, 12f, 6f, Color.RED, Color.BLACK, Color.RED, Color.BLACK));

        for (BaseElementData ele:bombElements) {
            ele.scale = 1.0f;
        }
    }

    @Override
    public boolean needSmoke() {
        if (smokecount < 0.0f) {
            smokecount = 0.25f;
            if(isExploding)
                smokecount = 0.2f;
            return true;
        }
        return false;
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

        isdarken -= delta * 1f;
        if (isdarken > 0.0f) {
            black = black.lerp(Color.YELLOW, (delta * 3f));
        } else {
            if (isdarken < -1.0f) {
              //  Gdx.app.log(TAG, "Black is black..:");
                black.r = 0.0f;
                black.g = 0.0f;
                black.b = 0.0f;
                black.a = 1.0f;
                isdarken = 1.0f;
            }
        }

        if(isStatic) {
            return;
        }

        smokecount -= delta;

        if(pos.x < Constants.WORLD_WIDTH + Constants.BLOCK_SIZE && !isExploding) {
            pos.x += speedright * delta;
            speedright -= Constants.G_ACCELERATION*delta*0.9f;
            pos.y += descent * delta;
            if (pos.y > starty) {
                descent -= delta * 15f;
            }
            else {
                descent += delta * 15f;

            }


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

        if(pos.x > Constants.WORLD_WIDTH+(10f*Constants.BLOCK_SIZE)) {
            if(!isExploding) {
                explode();
                explodecount = 1.0f;
                smokecount = -0.1f;
            }
            return;
        }


    }

}
