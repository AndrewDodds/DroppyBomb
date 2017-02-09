package com.saneandy.droppybomb.game.entities.landscape.landscapeentity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.game.elements.TriangleData;
import com.saneandy.droppybomb.game.elements.BaseElementData;
import com.saneandy.droppybomb.game.elements.RectData;
import com.saneandy.droppybomb.game.elements.RectLineData;

import java.util.ArrayList;
import java.lang.Math;

/**
 * Created by Andrew on 19/10/2016.
 */

public class Leaves  extends LandscapeEntity {

    public Leaves(Vector2 createAtPos) {
        super(createAtPos);
        leafType = LeafType.FULLBLOCK;
    }

    private LeafType leafType;
    private float brightness = 1.0f;
    private Color lightGreen = new Color(0.5f, 1.0f, 0.5f, 1.0f);
    private Color darkGreen = new Color(0.0f, 0.6f, 0.0f, 1.0f);

    @Override
    public ArrayList<BaseElementData> generateExplodeElements() {
        ArrayList<BaseElementData> retVal = new ArrayList<BaseElementData>();

        for (int i = 0; i < 10; i++) {
            float x = (float)(Math.random() * 20f);
            float y = (float)(Math.random() * 20f);
            float len = (float)(Math.random() * 7f)+1f;
            retVal.add(new RectLineData( x, y, x+len, y, 2.2f, Color.BROWN));
        }
        for (int i = 0; i < 20; i++) {
            float x = (float)(Math.random() * 20f);
            float y = (float)(Math.random() * 20f);
            float len = (float)(Math.random() * 7f)+1f;
            retVal.add(new RectLineData( x, y, x+len, y, 2.2f, Color.GREEN));
        }

        return retVal;
    }

    @Override
    public ArrayList<BaseElementData> generateElements() {
        ArrayList<BaseElementData> retVal = new ArrayList<BaseElementData>();


        switch(leafType) {
            case FULLBLOCK:
                retVal.add(new RectData(0.0f, 0.0f, 20.0f, 20.0f, lightGreen, darkGreen, lightGreen, darkGreen));

                for (int i = 0; i < 10; i++) {
                    retVal.add(new RectData( (float)(Math.random() * 20f), (float)(Math.random() * 20f), 1.0f, 1.0f, Color.BROWN));
                }
                for (int i = 0; i < 20; i++) {
                    retVal.add(new RectData( (float)(Math.random() * 20f), (float)(Math.random() * 20f), 1.0f, 1.3f, Color.GREEN));
                }
                break;
            case LEFTANGLE:
                retVal.add(new TriangleData(0.0f, 0.0f, 20.0f, 20.0f, 20.0f, 0.0f, lightGreen, darkGreen, lightGreen));
                for (int i = 0; i < 10; i++) {
                    float x = (float)(Math.random() * 20f);
                    float y = (float)(Math.random() * 20f);
                    if(x>y) {
                        retVal.add(new RectData(x, y, 1.0f, 1.0f, Color.BROWN));
                    }
                }
                for (int i = 0; i < 20; i++) {
                    float x = (float)(Math.random() * 20f);
                    float y = (float)(Math.random() * 20f);
                    if(x>y) {
                        retVal.add(new RectData(x, y, 1.0f, 1.3f, Color.GREEN));
                    }
                }
                break;
            case RIGHTANGLE:
                retVal.add(new TriangleData(0.0f, 0.0f, 20.0f, 0.0f, 0.0f, 20.0f, lightGreen, darkGreen, lightGreen));
                for (int i = 0; i < 10; i++) {
                    float x = (float)(Math.random() * 20f);
                    float y = (float)(Math.random() * 20f);
                    if((20f-x)>y) {
                        retVal.add(new RectData(x, y, 1.0f, 1.0f, Color.BROWN));
                    }
                }
                for (int i = 0; i < 20; i++) {
                    float x = (float)(Math.random() * 20f);
                    float y = (float)(Math.random() * 20f);
                    if((20f-x)>y) {
                        retVal.add(new RectData(x, y, 1.0f, 1.3f, Color.GREEN));
                    }
                }
                break;
            case LEFTANGLESTEEP:
                retVal.add(new TriangleData(0.0f, 0.0f, 10.0f, 20.0f, 10.0f, 0.0f, lightGreen, darkGreen, lightGreen));
                for (int i = 0; i < 10; i++) {
                    float x = (float)(Math.random() * 20f);
                    float y = (float)(Math.random() * 20f);
                    if(x>(2f*y)) {
                        retVal.add(new RectData(x, y, 1.0f, 1.0f, Color.BROWN));
                    }
                }
                for (int i = 0; i < 20; i++) {
                    float x = (float)(Math.random() * 20f);
                    float y = (float)(Math.random() * 20f);
                    if(x>(2f*y)) {
                        retVal.add(new RectData(x, y, 1.0f, 1.3f, Color.GREEN));
                    }
                }
                break;

            case RIGHTANGLESTEEP:
                retVal.add(new TriangleData(0.0f, 0.0f, 10.0f, 0.0f, 0.0f, 20.0f, lightGreen, darkGreen, lightGreen));
                for (int i = 0; i < 10; i++) {
                    float x = (float)(Math.random() * 20f);
                    float y = (float)(Math.random() * 20f);
                    if(y < (20.0f-(2.0f*x))) {
                        retVal.add(new RectData(x, y, 1.0f, 1.0f, Color.BROWN));
                    }
                }
                for (int i = 0; i < 20; i++) {
                    float x = (float)(Math.random() * 20f);
                    float y = (float)(Math.random() * 20f);
                    if(y < (20.0f-(2.0f*x))) {
                        retVal.add(new RectData(x, y, 1.0f, 1.3f, Color.GREEN));
                    }
                }
                break;
        }


        return retVal;
    }

    public LeafType getLeafType() {
        return leafType;
    }

    public void setLeafType(LeafType leafType) {
        this.leafType = leafType;

        setLandElements(generateElements());

    }


    public void setBrightness(float brightness) {
        lightGreen = new Color(0.5f*brightness, 1.0f*brightness, 0.5f*brightness, 1.0f);
        darkGreen = new Color(0.0f, 0.6f*brightness, 0.0f, 1.0f);

        this.brightness = brightness;
    }
}
