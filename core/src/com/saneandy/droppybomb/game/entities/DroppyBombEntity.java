package com.saneandy.droppybomb.game.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Andrew on 18/10/2016.
 */

public interface DroppyBombEntity {

    Rectangle getBoundingBox();

    boolean getIsExploding();

    boolean getHasExploded();

    void render(float delta, ShapeRenderer renderer);

    void update(float delta);

    void explode();


}
