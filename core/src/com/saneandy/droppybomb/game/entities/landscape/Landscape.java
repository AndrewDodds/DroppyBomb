package com.saneandy.droppybomb.game.entities.landscape;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.DroppyBombRegistry;
import com.saneandy.droppybomb.game.bombs.Bomb;
import com.saneandy.droppybomb.game.entities.DroppyBombEntity;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.LandscapeEntity;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.Leaves;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.Rock;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.Trunk;

import java.util.ArrayList;

/**
 * Created by Andrew on 19/10/2016.
 */

public class Landscape {

    private ArrayList<DroppyBombEntity> landEntities;
    private boolean isEarthquake = false;
    private float eqTimer = 0.0f;
    private float maineqTimer = 0.0f;
    private Vector2 eqOffset;

    private float countdown = 3.0f;

    private boolean isLeadIn;

    public Landscape(LandscapeType type, float difficulty) {

        LandscapeGenerator lg = null;

        switch(type) {
            case FOREST:
                lg = new ForestLandscape();
                break;
            case HILLS:
                lg = new MountainLandscape();
                break;
            case MUSHROOM:
                lg = new MushroomLandscape();
                break;
            case CASTLE:
                lg = new CastleLandscape();
                break;


        }

        isLeadIn = true;

        isEarthquake = false;
        eqOffset = new Vector2(0.0f, 0.0f);

        landEntities = lg.generate(difficulty);
    }

    public void processCollisions() {

        if(isLeadIn) {
            return;
        }

        ArrayList<DroppyBombEntity> otherEntities = DroppyBombRegistry.getElementSet();
        ArrayList<DroppyBombEntity> explodeEntities = new ArrayList<DroppyBombEntity>();

        for(DroppyBombEntity dpe : landEntities) {
            for(DroppyBombEntity other : otherEntities) {

                if (dpe.getBoundingBox().overlaps(other.getBoundingBox())) {
                    if(!dpe.getIsExploding()) {
                        dpe.explode();
                        if (other instanceof Bomb) {
                            ((Bomb) other).addScore();
                        }
                    }
                    explodeEntities.add(other);

                }
            }
        }

        for(DroppyBombEntity other:explodeEntities) {
            other.explode();
        }
    }

    public ArrayList<DroppyBombEntity> getLandEntities() {
        return landEntities;
    }

    public void render(float delta, ShapeRenderer renderer) {
        if(!isLeadIn) {
            for(DroppyBombEntity dpe : landEntities) {
                LandscapeEntity le = (LandscapeEntity)dpe;
                le.setOffset(eqOffset);
                le.render(delta, renderer);
            }
        }
        else {
            for(DroppyBombEntity dpe : landEntities) {
                LandscapeEntity le = (LandscapeEntity)dpe;

                float xdist = le.getPos().x - (Constants.WORLD_WIDTH/2.0f);
                float ydist = le.getPos().y - (Constants.WORLD_HEIGHT/2.0f);

                Vector2 offset = new Vector2(xdist * countdown, ydist * countdown );

                le.setOffset(offset);
                le.render(delta, renderer);
            }

        }
    }

    private void dropEntities(float delta) {

        for(DroppyBombEntity dpe : landEntities) {
            Rectangle r = dpe.getBoundingBox();
            Vector2 rc =  new Vector2(r.x + (0.5f*r.getWidth()), r.y);
            rc.y -= 1f;
            boolean supported = false;
            for(DroppyBombEntity dpe2 : landEntities) {

                if(dpe2.getBoundingBox().contains(rc)) {
                    supported = true;
                    break;
                }
            }
            if(!supported) {
                LandscapeEntity le = (LandscapeEntity)dpe;
                Vector2 npos = le.getPos();
                npos.y -= delta * 50f;
                if(npos.y < Constants.LAND_HEIGHT) {
                    npos.y = Constants.LAND_HEIGHT;
                }
                le.setPos(npos);
            }

        }
    }

    public int update(float delta) {
        int removed = 0;

        if(isLeadIn) {
            countdown -= delta;
            if (countdown <= 0.0f) {
                isLeadIn = false;
            }
        }

        ArrayList<DroppyBombEntity> temp = new ArrayList<DroppyBombEntity>();
        for(DroppyBombEntity dpe : landEntities) {
            dpe.update(delta);
            if (!dpe.getHasExploded()) {
                temp.add(dpe);
            }
            else {
                removed++;
            }
        }
        landEntities = temp;

        if(isEarthquake) {
            eqTimer -= delta;
            maineqTimer -= delta;
            if(eqTimer < 0.0f) {
                eqTimer = Constants.EATHQUAKESPEED;
                eqOffset = new Vector2( ((float)Math.random() * Constants.BLOCK_SIZE) - 0.5f*Constants.BLOCK_SIZE, ((float)Math.random() * Constants.BLOCK_SIZE) - 0.5f*Constants.BLOCK_SIZE  );
            }
            if(maineqTimer < 0.0f) {
                isEarthquake = false;
                eqOffset = new Vector2(0.0f, 0.0f);
            }

            dropEntities(delta);
        }



        return removed;
    }

    public void startEarthquake() {
        isEarthquake = true;
        eqTimer = Constants.EATHQUAKESPEED;
        maineqTimer = Constants.EATHQUAKEDURATION;
        eqOffset = new Vector2( ((float)Math.random() * Constants.BLOCK_SIZE) - 0.5f*Constants.BLOCK_SIZE, ((float)Math.random() * Constants.BLOCK_SIZE) - 0.5f*Constants.BLOCK_SIZE  );
    }


    public boolean isLeadIn() {
        return isLeadIn;
    }
}
