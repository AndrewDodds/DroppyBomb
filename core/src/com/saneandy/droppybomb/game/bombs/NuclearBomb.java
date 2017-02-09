package com.saneandy.droppybomb.game.bombs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.DroppyBombRegistry;
import com.saneandy.droppybomb.game.elements.ArcData;
import com.saneandy.droppybomb.game.elements.BaseElementData;
import com.saneandy.droppybomb.game.elements.CircleData;
import com.saneandy.droppybomb.game.elements.RectLineData;
import com.saneandy.droppybomb.game.elements.TriangleData;
import com.saneandy.droppybomb.game.entities.DroppyBombEntity;
import com.saneandy.droppybomb.game.entities.Explosion;
import com.saneandy.droppybomb.game.entities.Smoke;

import java.util.ArrayList;

/**
 * Created by Andrew on 19/10/2016.
 */

public class NuclearBomb extends Bomb {

    public static final String TAG = NuclearBomb.class.getName();

    public NuclearBomb(Vector2 startPos) {
        super(startPos);
        explodecount = 2f;

        Color yellow = new Color(1.0f, 1.0f, 0.0f, 1.0f);

        bombElements.add(new TriangleData(3f, 20.0f, 2.0f, 10.0f, 8.0f, 10.0f, Color.BLACK, Color.DARK_GRAY, Color.WHITE));
        bombElements.add(new TriangleData(17f, 20.0f, 18.0f, 10.0f, 12.0f, 10.0f, Color.BLACK, Color.DARK_GRAY, Color.WHITE));
        bombElements.add(new CircleData(10.0f, 4.0f, 3f, Color.DARK_GRAY));
        bombElements.add(new CircleData(10.0f, 10.0f, 7f, Color.BLACK));
        bombElements.add(new CircleData(10.0f, 10.0f, 5f, Color.DARK_GRAY));

        bombElements.add(new RectLineData(10.0f, 20.0f, 10.0f, 11.0f, 1.5f, Color.BLACK));

        bombElements.add(new TriangleData(10.0f, 10.0f, 5.0f, 10.0f, 7.5f, 14.0f, yellow));
        bombElements.add(new TriangleData(10.0f, 10.0f, 15.0f, 10.0f, 12.5f, 14.0f, yellow));
        bombElements.add(new TriangleData(10.0f, 10.0f, 7.5f, 6.0f, 12.5f, 6.0f, yellow));
        bombElements.add(new CircleData(10.0f, 10.0f, 2f, Color.BLACK));
        bombElements.add(new CircleData(10.0f, 10.0f, 1.5f, yellow));

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
        isExploding = true;

        bombElements = new ArrayList<BaseElementData>();

        Color red = Color.RED.cpy();
        Color orange = Color.ORANGE.cpy();
        Color yellow = Color.YELLOW.cpy();
        Color white = Color.WHITE.cpy();

        bombElements.add(new CircleData(10.0f, 5.0f, 5f, red));
        bombElements.add(new CircleData(10.0f, 5.0f, 4f, orange));
        bombElements.add(new CircleData(10.0f, 5.0f, 3f, yellow));
        bombElements.add(new CircleData(10.0f, 5.0f, 2f, white));

        bombElements.add(new RectLineData(10.0f, 0.0f, 10.0f, 18f, 7f, red));
        bombElements.add(new RectLineData(10.0f, 0.0f, 10.0f, 18f, 5f, orange));
        bombElements.add(new RectLineData(10.0f, 0.0f, 10.0f, 18f, 3f, yellow));

        bombElements.add(new ArcData(10f, 14.0f, 7f, 345.0f, 210.0f, red));
        bombElements.add(new ArcData(10f, 14.0f, 5f, 345.0f, 210.0f, orange));
        bombElements.add(new ArcData(10f, 14.0f, 3f, 345.0f, 210.0f, yellow));

        for (BaseElementData ele:bombElements) {
            ele.scale = 1.0f;
        }

        for (BaseElementData ele:bombElements) {
            ele.setRotationOffsetScaleDeltas(0.0f, new Vector2(0f, 1f), 7f );
        }

        float explosionRadius = 15f * Constants.BLOCK_SIZE;

        if(DroppyBombRegistry.getLand() != null) {
            for (DroppyBombEntity dpe : DroppyBombRegistry.getLand().getLandEntities()) {
                Vector2 center = new Vector2(0.0f, 0.0f);
                center = dpe.getBoundingBox().getCenter(center);
                if (pos.dst(center) < explosionRadius && !dpe.getIsExploding()) {
                    addScore();
                    dpe.explode();
                }
            }
        }

        // Explode the plane if in range!
        DroppyBombEntity dpe = DroppyBombRegistry.getElementSet().get(0);
        Vector2 center = new Vector2(0.0f, 0.0f);
        center = dpe.getBoundingBox().getCenter(center);
        if (pos.dst(center) < explosionRadius) {
            dpe.explode();
        }


    }

    @Override
    public void update(float delta) {
        for (BaseElementData ele:bombElements) {
            ele.update(delta);

            if( !isExploding) {
                if(bombElements.indexOf(ele) == 6 ||
                   bombElements.indexOf(ele) == 7 ||
                   bombElements.indexOf(ele) == 8 ||
                   bombElements.indexOf(ele) == 10) {
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
            explodecount -= delta;
            if(explodecount < 0f) {
                setHasExploded();
            }
            for (BaseElementData ele:bombElements) {
                ele.fade(delta);
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
