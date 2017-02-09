package com.saneandy.droppybomb.game.bombs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.DroppyBombRegistry;
import com.saneandy.droppybomb.game.elements.BaseElementData;
import com.saneandy.droppybomb.game.elements.CircleData;
import com.saneandy.droppybomb.game.entities.DroppyBombEntity;
import com.saneandy.droppybomb.game.entities.Explosion;
import com.saneandy.droppybomb.game.entities.Smoke;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.LandscapeEntity;

import java.util.ArrayList;

/**
 * Created by Andrew on 19/10/2016.
 */

public class HomingBomb extends Bomb {

    public static final String TAG = HomingBomb.class.getName();

    private Vector2 velocity;
    private Vector2 target;
    private float lifecount;

    private ArrayList<Vector2> circlepos = new ArrayList<Vector2>();

    private void findTargetPos() {
        target = new Vector2(Constants.WORLD_WIDTH/2f, -Constants.WORLD_HEIGHT*3f);
        float dist = pos.dst(target);

        if(DroppyBombRegistry.getLand() != null) {
            for (DroppyBombEntity dbe : DroppyBombRegistry.getLand().getLandEntities()) {
                LandscapeEntity le = (LandscapeEntity) dbe; // Should be safe..
                if (le.getPos().dst(pos) < dist) {
                    target = le.getPos();
                    dist = pos.dst(target);
                }
            }
        }
     }

    public HomingBomb(Vector2 startPos) {
        super(startPos);

        velocity = new Vector2(30f, -30f);

        findTargetPos();

        lifecount = 5.0f;

        circlepos = new ArrayList<Vector2>();
        circlepos.add(new Vector2(6f, 6f));
        circlepos.add(new Vector2(-6f, 6f));
        circlepos.add(new Vector2(6f, -6f));
        circlepos.add(new Vector2(-6f, -6f));

        rotate(0.0f);

        bombElements.add(new CircleData(10.0f, 10.0f, 5f, Color.BLACK));
        bombElements.add(new CircleData(10.0f, 10.0f, 3.5f, Color.FIREBRICK));
        bombElements.add(new CircleData(10.0f, 10.0f, 2f, Color.RED));


    }

    private void rotate(float delta) {
        bombElements = new ArrayList<BaseElementData>();

        for(Vector2 thisPos : circlepos) {
            thisPos.rotate(delta * 60f);
            bombElements.add(new CircleData(10.0f + thisPos.x, 10.0f + thisPos.y, 5f, Color.PURPLE));
            bombElements.add(new CircleData(10.0f + thisPos.x, 10.0f + thisPos.y, 3.5f, Color.BLUE));
            bombElements.add(new CircleData(10.0f + thisPos.x, 10.0f + thisPos.y, 2f, Color.CYAN));
        }

        bombElements.add(new CircleData(10.0f, 10.0f, 5f, Color.BLACK));
        bombElements.add(new CircleData(10.0f, 10.0f, 3.5f, Color.FIREBRICK));
        bombElements.add(new CircleData(10.0f, 10.0f, 2f, Color.RED));

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

        if(!isExploding) {
            rotate(delta);
        }

        if(isStatic) {
            return;
        }

        smokecount -= delta;

        if(!isExploding) {

            velocity.x += (target.x - pos.x) * delta;
            velocity.y += (target.y - pos.y) * delta;

            pos.x += velocity.x * delta;
            pos.y += velocity.y * delta;

            if(pos.y < Constants.LAND_HEIGHT) {
                explode();
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

        lifecount -= delta;
        if(lifecount < 0f) {
            if(!isExploding) {
                explode();
                explodecount = 1.0f;
                smokecount = -0.1f;
            }
            return;
        }


    }

}
