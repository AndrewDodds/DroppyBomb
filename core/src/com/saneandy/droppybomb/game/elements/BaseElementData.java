package com.saneandy.droppybomb.game.elements;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;

/**
 * Created by Andrew on 19/10/2016.
 */

public abstract class BaseElementData {

    public float rotation;
    public Vector2 offset;
    public float scale;

    float deltarotation;
    Vector2 deltaoffset;
    float deltascale;

    float alpha;

    public BaseElementData() {
        rotation = 0.0f;
        offset = new Vector2(0.0f, 0.0f);
        scale = 1.0f;
        alpha = 1.0f;

        deltarotation = 0.0f;
        deltaoffset = new Vector2(0.0f, 0.0f);
        deltascale = 0.0f;
    }

    public void setRotationOffsetScaleDeltas(float drotation, Vector2 doffset, float dscale) {
        deltarotation = drotation;
        deltaoffset = doffset;
        deltascale = dscale;
    }

    public void update(float delta) {
        rotation += deltarotation * delta;
        offset.x += deltaoffset.x * delta;
        offset.y += deltaoffset.y * delta;

        scale += deltascale  * delta;
    }

    public void applyGravity(float delta) {
        deltaoffset.y += Constants.G_ACCELERATION * delta;
    }


    public abstract void render(float delta, Vector2 position, ShapeRenderer renderer);


    public abstract Vector2 getRenderPos(Vector2 position);

    public abstract void brighten(float delta);

    public abstract void darken(float delta);

    public abstract void fade(float delta);



}
