package com.saneandy.droppybomb.game.bombs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.DroppyBombRegistry;
import com.saneandy.droppybomb.game.elements.BaseElementData;
import com.saneandy.droppybomb.game.entities.DroppyBombEntity;

import java.util.ArrayList;

/**
 * Created by Andrew on 01/11/2016.
 */

public abstract class Bomb implements DroppyBombEntity {
    public boolean isStatic = false;
    public static final String TAG = Bomb.class.getName();

    Vector2 pos;
    boolean isExploding = false;
    private boolean hasExploded = false;
    float descent;
    float explodecount;
    float smokecount;
    float isdarken;
    int score;
    int incscore;
    int uuid;
    ArrayList<BaseElementData> bombElements;

    public Bomb(Vector2 startPos) {
        pos = startPos;
        isExploding = false;
        hasExploded = false;
        descent = -35.0f;
        explodecount = 1f;
        smokecount = 0.01f;
        bombElements = new ArrayList<BaseElementData>();
        isdarken = 1.0f;
        score = 0;
        incscore = 1;
        uuid = (int)(Math.random()*1000d);
    }

    public boolean needSmoke() {
        if (pos.y < Constants.LAND_HEIGHT) {
            return false;
        }
        if (smokecount < 0.0f) {

            smokecount = 0.5f;
            if(isExploding)
                smokecount = 0.2f;
            return true;
        }
        return false;
    }

    public abstract  ArrayList<DroppyBombEntity> getSmoke();

    @Override
    public boolean getIsExploding() {
        return isExploding;
    }

    @Override
    public boolean getHasExploded() {
        return hasExploded;
    }

    void setHasExploded() {
        hasExploded = true;

        Gdx.app.log(TAG, "Add score:"+score+ " inc score:"+incscore+" bomb:"+uuid);

        DroppyBombRegistry.setScore(DroppyBombRegistry.getScore() + score);
    }

    public void addScore() {
        score += incscore++;
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(pos.x, pos.y, Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
    }

    @Override
    public void render(float delta, ShapeRenderer renderer) {
        for (BaseElementData ele:bombElements) {
            ele.render(delta, pos, renderer);
        }
    }

}
