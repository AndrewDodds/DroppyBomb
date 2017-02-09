package com.saneandy.droppybomb.game.entities.landscape.landscapeentity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.game.elements.BaseElementData;
import com.saneandy.droppybomb.game.elements.RectData;
import com.saneandy.droppybomb.game.elements.ArcData;
import com.saneandy.droppybomb.game.elements.RectLineData;
import com.saneandy.droppybomb.game.elements.TriangleData;

import java.util.ArrayList;

/**
 * Created by Andrew on 19/10/2016.
 */

public class Rock  extends LandscapeEntity {

    public Rock(Vector2 createAtPos) {
        super(createAtPos);
    }

    private RockType rockType = RockType.FULLBLOCK;
    private float brightness = 1.0f;
    private Color lightGrey = new Color(0.8f, 0.8f, 0.8f, 1.0f);
    private Color grey = new Color(0.6f, 0.6f, 0.6f, 1.0f);
    private Color darkGrey = new Color(0.3f, 0.3f, 0.3f, 1.0f);
    private Color lightorange = new Color(1.0f, 0.7f, 0.1f, 1.0f);
    private Color orange = new Color(1.0f, 0.5f, 0.0f, 1.0f);
    private Color verylightorange = new Color(1.0f, 9f, 0.3f, 1.0f);

    @Override
    public ArrayList<BaseElementData> generateExplodeElements() {
        ArrayList<BaseElementData> retVal = new ArrayList<BaseElementData>();

        for (int i = 0; i < 10; i++) {
            float x = (float)(Math.random() * 20f);
            float y = (float)(Math.random() * 20f);
            float len = (float)(Math.random() * 7f)+1f;
            retVal.add(new RectLineData( x, y, x+len, y, 2.2f, darkGrey));
        }
        for (int i = 0; i < 20; i++) {
            float x = (float)(Math.random() * 20f);
            float y = (float)(Math.random() * 20f);
            float len = (float)(Math.random() * 7f)+1f;
            retVal.add(new RectLineData( x, y, x+len, y, 2.2f, lightGrey));
        }

        return retVal;
    }

    @Override
    public ArrayList<BaseElementData> generateElements() {
        ArrayList<BaseElementData> retVal = new ArrayList<BaseElementData>();


        switch(rockType) {
            case LAVA:
                retVal.add(new RectData(0f, 0f, 20.0f, 20.0f, grey));
                retVal.add(new ArcData(10f, 20f, 9.0f, 165.0f, 210.0f, orange));
                retVal.add(new ArcData(10f, 20f, 6.0f, 165.0f, 210.0f, lightorange));
                retVal.add(new ArcData(10f, 20f, 3.0f, 165.0f, 210.0f, verylightorange));


                for (int i = 0; i < 5; i++) {
                    retVal.add(new RectData((float) (Math.random() * 20f), (float) (Math.random() * 20f), 1.0f, 1.0f, lightorange));
                }
                for (int i = 0; i < 10; i++) {
                    retVal.add(new RectData((float) (Math.random() * 20f), (float) (Math.random() * 20f), 1.0f, 1.3f, verylightorange));
                }
                break;
            case FULLBLOCK:
                retVal.add(new RectData(0f, 0f, 20.0f, 20.0f, grey));

                for (int i = 0; i < 5; i++) {
                    retVal.add(new RectData((float) (Math.random() * 20f), (float) (Math.random() * 20f), 1.0f, 1.0f, lightGrey));
                }
                for (int i = 0; i < 10; i++) {
                    retVal.add(new RectData((float) (Math.random() * 20f), (float) (Math.random() * 20f), 1.0f, 1.3f, darkGrey));
                }
                break;
            case LEFTANGLE:
                retVal.add(new TriangleData(0.0f, 0.0f, 20.0f, 20.0f, 20.0f, 0.0f, grey));
                retVal.add(new RectLineData(0.0f, 0.0f, 20.0f, 20.0f, 2.0f, lightGrey));
                for (int i = 0; i < 10; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    if (x > y) {
                        retVal.add(new RectData(x, y, 1.0f, 1.0f, Color.BLACK));
                    }
                }
                for (int i = 0; i < 20; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    if (x > y) {
                        retVal.add(new RectData(x, y, 1.0f, 1.3f, lightGrey));
                    }
                }
                break;
            case LEFTANGLELAVA:
                retVal.add(new TriangleData(0.0f, 0.0f, 20.0f, 20.0f, 20.0f, 0.0f, grey));
                retVal.add(new RectLineData(0.0f, 0.0f, 20.0f, 20.0f, 4.0f, lightorange));
                for (int i = 0; i < 6; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    if (x > y) {
                        retVal.add(new RectData(x, y, 1.0f, 1.0f, Color.BLACK));
                    }
                }
                for (int i = 0; i < 12; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    if (x > y) {
                        retVal.add(new RectData(x, y, 1.0f, 1.3f, lightorange));
                    }
                }
                break;
            case RIGHTANGLE:
                retVal.add(new RectLineData(20.0f, 0.0f, 0.0f, 20.0f, 1.0f, darkGrey));
                retVal.add(new TriangleData(0.0f, 0.0f, 20.0f, 0.0f, 0.0f, 20.0f, grey));
                for (int i = 0; i < 10; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    if ((20f - x) > y) {
                        retVal.add(new RectData(x, y, 1.0f, 1.0f, Color.BLACK));
                    }
                }
                for (int i = 0; i < 20; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    if ((20f - x) > y) {
                        retVal.add(new RectData(x, y, 1.0f, 1.3f, lightGrey));
                    }
                }
                break;
            case RIGHTANGLELAVA:
                retVal.add(new TriangleData(0.0f, 0.0f, 20.0f, 0.0f, 0.0f, 20.0f, grey));
                retVal.add(new RectLineData(20.0f, 0.0f, 0.0f, 20.0f, 4.0f, lightorange));
                for (int i = 0; i < 6; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    if ((20f - x) > y) {
                        retVal.add(new RectData(x, y, 1.0f, 1.0f, Color.BLACK));
                    }
                }
                for (int i = 0; i < 12; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    if ((20f - x) > y) {
                        retVal.add(new RectData(x, y, 1.0f, 1.3f, lightorange));
                    }
                }
                break;
            case LEFTANGLESTEEP:
                retVal.add(new TriangleData(0.0f, 0.0f, 10.0f, 20.0f, 10.0f, 0.0f, grey));
                retVal.add(new RectLineData(0.0f, 0.0f, 10.0f, 20.0f, 2.0f, lightGrey));
                for (int i = 0; i < 10; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    if (x > (2f * y)) {
                        retVal.add(new RectData(x, y, 1.0f, 1.0f, Color.BLACK));
                    }
                }
                for (int i = 0; i < 20; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    if (x > (2f * y)) {
                        retVal.add(new RectData(x, y, 1.0f, 1.3f, lightGrey));
                    }
                }
                break;

            case LEFTANGLESTEEPLAVA:
                retVal.add(new TriangleData(0.0f, 0.0f, 10.0f, 20.0f, 10.0f, 0.0f, grey));
                retVal.add(new RectLineData(0.0f, 0.0f, 10.0f, 20.0f, 4.0f, lightorange));
                for (int i = 0; i < 10; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    if (x > (2f * y)) {
                        retVal.add(new RectData(x, y, 1.0f, 1.0f, Color.BLACK));
                    }
                }
                for (int i = 0; i < 20; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    if (x > (2f * y)) {
                        retVal.add(new RectData(x, y, 1.0f, 1.3f, lightGrey));
                    }
                }
                break;

            case RIGHTANGLESTEEP:
                retVal.add(new TriangleData(0.0f, 0.0f, 10.0f, 0.0f, 0.0f, 20.0f, grey));
                retVal.add(new RectLineData(10.0f, 0.0f, 0.0f, 20.0f, 1.0f, darkGrey));
                for (int i = 0; i < 10; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    if (y < (20.0f - (2.0f * x))) {
                        retVal.add(new RectData(x, y, 1.0f, 1.0f, Color.BLACK));
                    }
                }
                for (int i = 0; i < 20; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    if (y < (20.0f - (2.0f * x))) {
                        retVal.add(new RectData(x, y, 1.0f, 1.3f, lightGrey));
                    }
                }
                break;
            case RIGHTANGLESTEEPLAVA:
                retVal.add(new TriangleData(0.0f, 0.0f, 10.0f, 0.0f, 0.0f, 20.0f, grey));
                retVal.add(new RectLineData(10.0f, 0.0f, 0.0f, 20.0f, 4.0f, lightorange));
                for (int i = 0; i < 10; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    if (y < (20.0f - (2.0f * x))) {
                        retVal.add(new RectData(x, y, 1.0f, 1.0f, Color.BLACK));
                    }
                }
                for (int i = 0; i < 20; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    if (y < (20.0f - (2.0f * x))) {
                        retVal.add(new RectData(x, y, 1.0f, 1.3f, lightGrey));
                    }
                }
                break;
        }
        return retVal;
    }

    public RockType getRockType() {
        return rockType;
    }

    public void setRockType(RockType rockType) {
        this.rockType = rockType;

        setLandElements(generateElements());

    }


    public void setBrightness(float brightness) {
        lightGrey = new Color(0.8f*brightness, 0.8f*brightness, 0.8f*brightness, 1.0f);
        grey = new Color(0.6f*brightness, 0.6f*brightness, 0.6f*brightness, 1.0f);
        darkGrey = new Color(0.4f*brightness, 0.4f*brightness, 0.4f*brightness, 1.0f);

        this.brightness = brightness;
    }


}
