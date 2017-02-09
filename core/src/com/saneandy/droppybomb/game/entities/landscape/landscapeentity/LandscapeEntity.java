package com.saneandy.droppybomb.game.entities.landscape.landscapeentity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.game.DroppyBombRegistry;
import com.saneandy.droppybomb.game.elements.BaseElementData;
import com.saneandy.droppybomb.game.entities.DroppyBombEntity;

import java.util.ArrayList;

/**
 * Created by Andrew on 19/10/2016.
 */

public abstract class LandscapeEntity  implements DroppyBombEntity {
    public static final String TAG = LandscapeEntity.class.getName();

    private Vector2 pos;
    private Vector2 offset;
    private boolean isExploding = false;
    private boolean hasExploded = false;
    private float explodecount;
    private ArrayList<BaseElementData> landElements;

    public abstract ArrayList<BaseElementData> generateElements();

    public abstract ArrayList<BaseElementData> generateExplodeElements();

    public LandscapeEntity(Vector2 createAtPos)
    {
        setPos(createAtPos);
        isExploding = false;
        hasExploded = false;
        explodecount = 2f;
        offset = new Vector2(0.0f, 0.0f);
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(getPos().x, getPos().y, 20f, 20f);
    }

    @Override
    public boolean getIsExploding() {
        return isExploding;
    }

    @Override
    public boolean getHasExploded() {
        return hasExploded;
    }

    @Override
    public void render(float delta, ShapeRenderer renderer) {
        for (BaseElementData ele: getLandElements()) {

            Vector2 newPos = new Vector2(pos.x + offset.x, pos.y + offset.y);
            ele.render(delta, newPos, renderer);
        }

    }

    @Override
    public void update(float delta) {
        if(isExploding) {
            explodecount -= delta;
            if(explodecount < 0f) {
                hasExploded = true;
            }
            for(BaseElementData ele: getLandElements()) {
                ele.applyGravity(delta);
            }
            return;
        }

    }

    @Override
    public void explode() {
        if(isExploding) {
            return;
        }
        isExploding = true;
        hasExploded = false;

        setLandElements(generateExplodeElements());
        DroppyBombRegistry.getExplodeSound().play(1.0f);

        for (BaseElementData ele: getLandElements()) {
            ele.setRotationOffsetScaleDeltas((float)(Math.random()-0.5)*120.0f, new Vector2((float)(Math.random()*360f)-180f, (float)(Math.random()*360f)-180f), 0f );
        }

    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public ArrayList<BaseElementData> getLandElements() {
        if(landElements == null) {
            setLandElements(generateElements());
        }
        return landElements;
    }

    public void setLandElements(ArrayList<BaseElementData> landElements) {
        this.landElements = landElements;
    }

    public Vector2 getOffset() {
        return offset;
    }

    public void setOffset(Vector2 offset) {
        this.offset = offset;
    }
}

