package com.saneandy.droppybomb.game.bombs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.DroppyBombRegistry;
import com.saneandy.droppybomb.game.elements.CircleData;
import com.saneandy.droppybomb.game.entities.DroppyBombEntity;
import com.saneandy.droppybomb.game.entities.Explosion;
import com.saneandy.droppybomb.game.entities.Smoke;
import com.saneandy.droppybomb.game.elements.BaseElementData;
import com.saneandy.droppybomb.game.elements.RectData;
import com.saneandy.droppybomb.game.elements.TriangleData;

import java.util.ArrayList;

/**
 * Created by Andrew on 19/10/2016.
 */

public class HighExplosiveBomb extends Bomb {

    public static final String TAG = HighExplosiveBomb.class.getName();

    private float smokecount;
    private ArrayList<Rectangle> rects;

    public HighExplosiveBomb(Vector2 startPos) {
        super(startPos);

        rects = new ArrayList<Rectangle>();
        rects.add(new Rectangle(2.5f, 3.0f, 3f, 12f));
        rects.add(new Rectangle(5.5f, 3.0f, 3f, 12f));
        rects.add(new Rectangle(8.5f, 3.0f, 3f, 12f));
        rects.add(new Rectangle(11.5f, 3.0f, 3f, 12f));
        rects.add(new Rectangle(14.5f, 3.0f, 3f, 12f));
        rects.add(new Rectangle(2.5f, 3.0f, 0f, 12f));

        rectElements(0.0f);

        for (BaseElementData ele:bombElements) {
            ele.scale = 1.0f;
        }
    }

    private void rectElements(float delta) {
        bombElements = new ArrayList<BaseElementData>();
        bombElements.add(new TriangleData(10f, 20.0f, 5.0f, 20.0f, 10.0f, 15.0f, Color.BLACK, Color.YELLOW, Color.YELLOW));
        bombElements.add(new TriangleData(10f, 20.0f, 15.0f, 20.0f, 10.0f, 15.0f, Color.BLACK, Color.YELLOW, Color.YELLOW));
        bombElements.add(new TriangleData(8.5f, 3.0f, 11.5f, 3.0f, 10.0f, 0.0f, Color.YELLOW, Color.YELLOW, Color.BLACK));

        boolean isblack = true;
        for(Rectangle r : rects) {
            if(r.width >= 3f || r.x > 10.0f) {
                r.x += delta * Constants.BOMBROTATESPEED;
            }
            else {
                r.width += delta * Constants.BOMBROTATESPEED;
                if(r.width > 3f) {
                    r.x += r.width - 3f;
                    r.width = 3f;
                }
            }

            if(r.x + r.width >= 17.5f) {
                r.width = 17.5f - r.x;
                if(r.width < 0.0f) {
                    r.x = 2.5f;
                    r.width = 0.0f;
                }
            }
            if (isblack) {
                bombElements.add(new RectData(r.x, r.y, r.width, r.height, Color.BLACK));
                isblack = false;
            }
            else {
                bombElements.add(new RectData(r.x, r.y, r.width, r.height, Color.YELLOW));
                isblack = true;
            }

        }
    }

    @Override
    public boolean needSmoke() {
        if (smokecount < 0.0f) {
            smokecount = 0.5f;
            if(isExploding)
                smokecount = 0.4f;
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
        bbox.x -= Constants.BLOCK_SIZE *2f;
        bbox.y -= Constants.BLOCK_SIZE *2f;
        bbox.width += Constants.BLOCK_SIZE *4f;
        bbox.height += Constants.BLOCK_SIZE *4f;

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
        rectElements(delta);

        for (BaseElementData ele:bombElements) {
            ele.update(delta);
        }

        if(isStatic) {
            return;
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
            return;
        }


    }

}
