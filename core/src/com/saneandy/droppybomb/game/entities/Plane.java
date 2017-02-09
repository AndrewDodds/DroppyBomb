package com.saneandy.droppybomb.game.entities;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.DroppyBombRegistry;
import com.saneandy.droppybomb.game.elements.BaseElementData;
import com.saneandy.droppybomb.game.elements.CircleData;
import com.saneandy.droppybomb.game.elements.RectData;
import com.saneandy.droppybomb.game.elements.RectLineData;
import com.saneandy.droppybomb.game.elements.TriangleData;
import com.saneandy.droppybomb.game.bombs.*;

import java.util.ArrayList;

/**
 * Created by Andrew on 18/10/2016.
 */

public class Plane implements DroppyBombEntity {
    public static final String TAG = Plane.class.getName();

    private Vector2 pos;
    private boolean hasLanded = false;
    private boolean isExploding = false;
    private boolean hasExploded = false;
    private float smokecount;
    private float propSize;
    private float propDir;
    private float explodeDescent;
    private RectLineData propellor;
    private float planeSoundRepeat;

    private ArrayList<BaseElementData> planeElements;


    public Plane() {

        if(DroppyBombRegistry.getCurrentscreen() == Constants.TITLE_SCREEN) {
            pos = new Vector2(Constants.PLANE_START_X, Constants.WORLD_HEIGHT/2f);
        }
        else {
            pos = new Vector2(Constants.PLANE_START_X, Constants.PLANE_START_Y);
        }
        hasLanded = false;
        propSize = 5.0f;
        propDir = -1f;
        explodeDescent = 0.0f;
        smokecount = 1.0f;
        planeElements = new ArrayList<BaseElementData>();

        planeElements.add(new CircleData(63.0f, 15.0f, 1.5f, Color.BLACK));
        planeElements.add(new RectData(15.0f, 10.5f, 48.0f, 9.0f, Color.GOLD, Color.GOLD, Color.OLIVE,Color.OLIVE));
        planeElements.add(new CircleData(45.0f, 19.5f, 3f, Color.BLACK));
        planeElements.add(new CircleData(15.0f, 10.5f, 2f, Color.BLACK));
        planeElements.add(new TriangleData(7.5f, 15.0f, 22.5f, 15.0f, 7.5f, 25.5f, Color.RED, Color.BLUE, Color.WHITE));
        planeElements.add(new TriangleData(52.5f, 10.0f, 46.5f, 10.0f, 49.5f, 7.0f, Color.BLACK, Color.BLACK, Color.BLACK));
        planeElements.add(new CircleData(49.5f, 7.0f, 3f, Color.BLACK));

        planeElements.add(new RectLineData(45.0f, 27.0f, 66.0f, 27.0f, 2.0f, Color.OLIVE));
        planeElements.add(new RectLineData(39.0f, 10.0f, 60.0f, 10.0f, 2.0f, Color.OLIVE));
        planeElements.add(new RectLineData(52.5f, 27.0f, 46.5f, 10.0f, 2.0f, Color.OLIVE));
        planeElements.add(new RectLineData(58.5f, 27.0f, 52.5f, 10.0f, 2.0f, Color.OLIVE));

        propellor = new RectLineData(65.25f, 15.0f + propSize, 65.25f,  15.0f - propSize, 1.0f, Color.DARK_GRAY);
        planeElements.add(propellor); // Pos 11
        planeElements.add(new CircleData(31.5f, 15.0f, 4f, Color.WHITE));
        planeElements.add(new CircleData(31.5f, 15.0f, 2.4f, Color.RED));

        for (BaseElementData ele:planeElements) {
            ele.scale = 1.0f;
        }

        DroppyBombRegistry.getPlaneSound().play(1.0f);

        planeSoundRepeat = 20.0f;


    }

    public ArrayList<Bomb> getBomb() {

        DroppyBombRegistry.getDropBombSound().play(1.0f);

        ArrayList<Bomb> retVal = new ArrayList<Bomb>();
        switch(DroppyBombRegistry.getCurrentbomb()) {
            case NORMAL:
                retVal.add(new NormalBomb(new Vector2(pos.x + 25.0f, pos.y - 10.0f)));
                break;
            case HIGHEXPLOSIVE:
                retVal.add(new HighExplosiveBomb(new Vector2(pos.x + 25.0f, pos.y - 10.0f)));
                break;
            case DAISYCUTTER:
                retVal.add(new DaisyCutterBomb(new Vector2(pos.x + 25.0f, pos.y - 10.0f)));
                break;
            case MISSILE:
                retVal.add(new MissileBomb(new Vector2(pos.x + 25.0f, pos.y - 10.0f)));
                break;
            case HOMINGBOMB:
                retVal.add(new HomingBomb(new Vector2(pos.x + 25.0f, pos.y - 10.0f)));
                break;
            case EARTHQUAKEBOMB:
                retVal.add(new EarthquakeBomb(new Vector2(pos.x + 25.0f, pos.y - 10.0f)));
                break;
            case NUCLEAR:
                retVal.add(new NuclearBomb(new Vector2(pos.x + 25.0f, pos.y - 10.0f)));
                break;
            case CLUSTERBOMB:
                for(float xVel = -70f; xVel < 71f; xVel += 35f) {
                    retVal.add(new ClusterBomb(new Vector2(pos.x + 25.0f, pos.y - 10.0f), xVel));
                }
                break;
        }

        return retVal;
    }

    public boolean getHasLanded() {
        return hasLanded;
    }

    public boolean needSmoke() {
        if (smokecount < 0.0f) {
            smokecount = 0.3f;
            if(isExploding)
                smokecount = 0.08f;
            return true;
        }
        return false;
    }

    public ArrayList<DroppyBombEntity> getSmoke() {
        ArrayList<DroppyBombEntity> retVal = new ArrayList<DroppyBombEntity>();
        retVal.add(new Smoke(pos.cpy(), new Vector2(0f, 0f), Color.WHITE));
        if(isExploding) {
            retVal.add(new com.saneandy.droppybomb.game.entities.Explosion(pos.cpy(), new Vector2(0f, 0f)));
            for (BaseElementData ele:planeElements) {
                retVal.add(new Smoke(ele.getRenderPos(pos).cpy(), new Vector2(0f, 0.1f), Color.DARK_GRAY));
            }
        }
        return retVal;
    }


    @Override
    public Rectangle getBoundingBox() {

        return new Rectangle(pos.x, pos.y, 50f, 20f);
    }

    @Override
    public boolean getIsExploding() {
        return isExploding;
    }

    @Override
    public boolean getHasExploded() {
        return hasExploded;
    }

    public ArrayList<BaseElementData> generateExplodeElements() {
        ArrayList<BaseElementData> retVal = new ArrayList<BaseElementData>();

        for (int i = 0; i < 10; i++) {
            float x = (float)(Math.random() * 20f);
            float y = (float)(Math.random() * 20f);
            float len = (float)(Math.random() * 7f)+1f;
            retVal.add(new RectLineData( x, y, x+len, y, 3.2f, Color.BROWN));
        }
        for (int i = 0; i < 10; i++) {
            float x = (float)(Math.random() * 20f);
            float y = (float)(Math.random() * 20f);
            float len = (float)(Math.random() * 7f)+1f;
            retVal.add(new RectLineData( x, y, x+len, y, 3.2f, Color.RED));
        }

        return retVal;
    }

    @Override
    public void explode() {
        if(isExploding) {
            return;
        }
        isExploding = true;
        hasExploded = false;

        DroppyBombRegistry.getPlaneSound().stop();

        for (BaseElementData ele:planeElements) {
            ele.setRotationOffsetScaleDeltas((float)Math.random()*60.0f, new Vector2((float)(Math.random()*0.5f)-0.25f, (float)(Math.random()*8f)-4f), 0f );
        }

        ArrayList<BaseElementData> explodeElements = generateExplodeElements();
        for (BaseElementData ele:explodeElements) {
            ele.setRotationOffsetScaleDeltas((float)(Math.random())*60.0f, new Vector2((float)(Math.random()*240f)-102f, (float)(Math.random()*240f)-120f), 0f );
        }

        planeElements.addAll(explodeElements);

        DroppyBombRegistry.getDeathSound().play(1.0f);

    }

    @Override
    public void render(float delta, ShapeRenderer renderer) {
        for (BaseElementData ele:planeElements) {
            ele.render(delta, pos, renderer);
        }
    }

    @Override
    public void update(float delta) {
         for (BaseElementData ele:planeElements) {
            ele.update(delta);
        }

        planeSoundRepeat -= delta;
        if(planeSoundRepeat <= 0.0f) {
            planeSoundRepeat = 20.0f;
            DroppyBombRegistry.getPlaneSound().play(1.0f);
        }

        smokecount -= delta;

        if(isExploding) {
            explodeDescent -= (50.0f* delta);
            pos.y += explodeDescent * delta;
            //Gdx.app.log(TAG, "exploding: y "+pos.y+ "explodeDescent :"+explodeDescent );
            if(pos.y < 0f) {
                hasExploded = true;
            }
            return;
        }


        if(pos.y <= Constants.LAND_HEIGHT && pos.x > (Constants.WORLD_WIDTH - (2*Constants.PLANE_SIZE_X))) {
            hasLanded = true;
            return;
        }


        pos.x += Constants.PLANE_SPEED_X * delta;
        if (pos.x > Constants.WORLD_WIDTH) {
            pos.x = -Constants.PLANE_SIZE_X;

            if(DroppyBombRegistry.getCurrentscreen() == Constants.GAME_SCREEN) {
                pos.y -= Constants.PLANE_SPEED_Y;
                if (pos.y < Constants.LAND_HEIGHT) {
                    pos.y = Constants.LAND_HEIGHT - 0.01f;
                }
            }
        }

        propSize += (delta * propDir * Constants.PROPSPEED);
        if(propDir < 0f) {
            if (propSize < 1) {
                propDir = 1.0f;
            }
        }
        if(propDir > 0f) {
            if (propSize > 5.0f) {
                propDir = -1.0f;
            }
        }

        propellor = new RectLineData(65.25f, 15.0f + propSize, 65.25f,  15.0f - propSize, 1.0f, Color.DARK_GRAY);
        planeElements.set(11, propellor); // Pos 9


    }
}
