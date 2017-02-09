package com.saneandy.droppybomb.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.bombs.DaisyCutterBomb;
import com.saneandy.droppybomb.game.bombs.HighExplosiveBomb;
import com.saneandy.droppybomb.game.bombs.NormalBomb;
import com.saneandy.droppybomb.game.elements.BaseElementData;
import com.saneandy.droppybomb.game.elements.RectData;
import com.saneandy.droppybomb.game.elements.TriangleData;
import com.saneandy.droppybomb.game.entities.DroppyBombEntity;
import com.saneandy.droppybomb.game.text.CircFont;
import com.saneandy.droppybomb.game.text.RenderableWord;

import java.util.ArrayList;

/**
 * Created by Andrew on 26/10/2016.
 */

public class SpeedButton {
    private Vector2 pos;
    private Vector2 size;
    private SpeedType sType;
    private ArrayList<BaseElementData> speedElements;
    float offsetx = 0.0f;


    public SpeedButton(Vector2 position, Vector2 buttonsize ) {
        pos = position.cpy();
        size = buttonsize.cpy();
        sType = SpeedType.NORMAL;
        setViewComponents();
    }

    private void setViewComponents() {
        Vector2 center = new Vector2((Constants.BUTTON_SIZE/2.0f) + offsetx, Constants.BUTTON_SIZE/2.0f);

        speedElements = new ArrayList<BaseElementData>();

        switch(sType) {
            case NORMAL:
                speedElements.add(new TriangleData(center.x+4, center.y, center.x-2, center.y+8, center.x-2, center.y-8, Color.GREEN, Color.LIME, Color.TEAL ));

                break;
            case FAST:
                speedElements.add(new TriangleData(center.x-1, center.y, center.x-7, center.y+8, center.x-7, center.y-8, Color.GREEN, Color.LIME, Color.TEAL ));
                speedElements.add(new TriangleData(center.x+7, center.y, center.x+1, center.y+8, center.x+1, center.y-8, Color.YELLOW, Color.LIME, Color.FIREBRICK ));

                break;
            case EXTRAFAST:
                speedElements.add(new TriangleData(center.x-6, center.y, center.x-12, center.y+8, center.x-12, center.y-8, Color.GREEN, Color.LIME, Color.TEAL ));
                speedElements.add(new TriangleData(center.x+2, center.y, center.x-4, center.y+8, center.x-4, center.y-8, Color.YELLOW, Color.LIME, Color.FIREBRICK  ));
                speedElements.add(new TriangleData(center.x+10, center.y, center.x+4, center.y+8, center.x+4, center.y-8, Color.RED, Color.ROYAL, Color.FIREBRICK  ));

                break;
        }
    }


    public void render(float delta, ShapeRenderer renderer) {

        renderer.rect(pos.x, pos.y, size.x, size.y, Color.BLACK, Color.DARK_GRAY, Color.DARK_GRAY, Color.LIGHT_GRAY);
        if(sType == SpeedType.NORMAL) {
            renderer.rect(pos.x + 0.2f, pos.y + 0.2f, size.x - 0.4f, size.y - 0.4f, Color.DARK_GRAY, Color.DARK_GRAY, Color.GRAY, Color.GRAY);
        }
        else if(sType == SpeedType.FAST) {
            renderer.rect(pos.x + 0.2f, pos.y + 0.2f, size.x - 0.4f, size.y - 0.4f, Color.BLUE, Color.BLUE, Color.GRAY, Color.GRAY);
        }
        else {
            renderer.rect(pos.x + 0.2f, pos.y + 0.2f, size.x - 0.4f, size.y - 0.4f, Color.BLUE, Color.BLUE, Color.GOLD, Color.GOLD);
        }
        for(BaseElementData element: speedElements) {
            element.render(delta, pos, renderer);
        }
    }

    public boolean isHit(Vector2 point) {
        if(point.x >= pos.x && point.x <= (pos.x+size.x) ) {
            if(point.y >= pos.y && point.y <= (pos.y+size.y) ) {
                if(sType == SpeedType.NORMAL) {
                    sType = SpeedType.FAST;
                }
                else if(sType == SpeedType.FAST) {
                    sType = SpeedType.EXTRAFAST;
                }
                else {
                    sType = SpeedType.NORMAL;
                }

                setViewComponents();

                return true;
            }
        }
        return false;
    }

    public SpeedType getSpeedType() {
        return sType;
    }

    public void update(float delta) {
        offsetx += (delta*2);
        if (offsetx > 6.0f) {
            offsetx = -4.0f;
        }

        setViewComponents();
    }


}
