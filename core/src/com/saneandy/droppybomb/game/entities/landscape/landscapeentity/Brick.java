package com.saneandy.droppybomb.game.entities.landscape.landscapeentity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.game.elements.BaseElementData;
import com.saneandy.droppybomb.game.elements.RectData;
import com.saneandy.droppybomb.game.elements.RectLineData;
import com.saneandy.droppybomb.game.elements.TriangleData;

import java.util.ArrayList;

/**
 * Created by Andrew on 19/10/2016.
 */

public class Brick extends LandscapeEntity {

    public Brick(Vector2 createAtPos) {
        super(createAtPos);
    }

    private BrickType brickType = BrickType.FULLBLOCK;
    private float brightness = 1.0f;
    private Color lightGrey = new Color(0.8f, 0.8f, 0.8f, 1.0f);
    private Color grey = new Color(0.6f, 0.6f, 0.6f, 1.0f);
    private Color darkGrey = new Color(0.3f, 0.3f, 0.3f, 1.0f);

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


        switch(brickType) {
            case FULLBLOCK:
                retVal.add(new RectData(0f, 0f, 20.0f, 20.0f, grey));

                for(float x=0f; x<20f; x+=7f) {
                    for(float y=0f; y<19f; y+=10f) {
                        retVal.add(new RectData(x, y+1, 6.0f, 4.0f, lightGrey));
                        retVal.add(new RectData(x+1, y, 6.0f, 4.0f, darkGrey));
                        retVal.add(new RectData(x+1, y+1, 5.0f, 3.0f, grey));
                    }
                }

                for(float x=3.5f; x<12f; x+=7f) {
                    for(float y=5f; y<19f; y+=10f) {
                        retVal.add(new RectData(x, y+1, 6.0f, 4.0f, lightGrey));
                        retVal.add(new RectData(x+1, y, 6.0f, 4.0f, darkGrey));
                        retVal.add(new RectData(x+1, y+1, 5.0f, 3.0f, grey));
                    }
                }

                for(float y=5f; y<19f; y+=10f) {
                    retVal.add(new RectData(0.0f, y + 1, 2.5f, 4.0f, lightGrey));
                    retVal.add(new RectData(0.0f, y, 3.5f, 4.0f, darkGrey));
                    retVal.add(new RectData(0.0f, y + 1, 2.5f, 3.0f, grey));

                    retVal.add(new RectData(14f, y + 1, 3.5f, 4.0f, lightGrey));
                    retVal.add(new RectData(15f, y, 6.0f, 2.5f, darkGrey));
                    retVal.add(new RectData(15f, y + 1, 2.5f, 3.0f, grey));

                }
                break;
            case WINDOW:
                retVal.add(new RectData(0f, 0f, 20.0f, 20.0f, grey));

                for(float x=0f; x<20f; x+=7f) {
                    for(float y=0f; y<19f; y+=10f) {
                        retVal.add(new RectData(x, y+1, 6.0f, 4.0f, lightGrey));
                        retVal.add(new RectData(x+1, y, 6.0f, 4.0f, darkGrey));
                        retVal.add(new RectData(x+1, y+1, 5.0f, 3.0f, grey));
                    }
                }

                for(float x=3.5f; x<12f; x+=7f) {
                    for(float y=5f; y<19f; y+=10f) {
                        retVal.add(new RectData(x, y+1, 6.0f, 4.0f, lightGrey));
                        retVal.add(new RectData(x+1, y, 6.0f, 4.0f, darkGrey));
                        retVal.add(new RectData(x+1, y+1, 5.0f, 3.0f, grey));
                    }
                }

                for(float y=5f; y<19f; y+=10f) {
                    retVal.add(new RectData(0.0f, y + 1, 2.5f, 4.0f, lightGrey));
                    retVal.add(new RectData(0.0f, y, 3.5f, 4.0f, darkGrey));
                    retVal.add(new RectData(0.0f, y + 1, 2.5f, 3.0f, grey));

                    retVal.add(new RectData(14f, y + 1, 3.5f, 4.0f, lightGrey));
                    retVal.add(new RectData(15f, y, 6.0f, 2.5f, darkGrey));
                    retVal.add(new RectData(15f, y + 1, 2.5f, 3.0f, grey));

                }
                retVal.add(new RectData(4f, 4f, 12.0f, 12.0f, lightGrey));
                retVal.add(new RectData(5f, 5f, 10.0f, 10.0f, Color.CYAN));
                break;
            case BATTLEMENT:
                retVal.add(new RectData(0f, 0f, 20.0f, 15.0f, grey));

                for(float x=0f; x<20f; x+=7f) {
                    for(float y=0f; y<19f; y+=10f) {
                        retVal.add(new RectData(x, y+1, 6.0f, 4.0f, lightGrey));
                        retVal.add(new RectData(x+1, y, 6.0f, 4.0f, darkGrey));
                        retVal.add(new RectData(x+1, y+1, 5.0f, 3.0f, grey));
                    }
                }

                float y=5f;
                for(float x=3.5f; x<12f; x+=7f) {
                    retVal.add(new RectData(x, y+1, 6.0f, 4.0f, lightGrey));
                    retVal.add(new RectData(x+1, y, 6.0f, 4.0f, darkGrey));
                    retVal.add(new RectData(x+1, y+1, 5.0f, 3.0f, grey));
                }

                retVal.add(new RectData(0.0f, y + 1, 2.5f, 4.0f, lightGrey));
                retVal.add(new RectData(0.0f, y, 3.5f, 4.0f, darkGrey));
                retVal.add(new RectData(0.0f, y + 1, 2.5f, 3.0f, grey));

                retVal.add(new RectData(14f, y + 1, 3.5f, 4.0f, lightGrey));
                retVal.add(new RectData(15f, y, 6.0f, 2.5f, darkGrey));
                retVal.add(new RectData(15f, y + 1, 2.5f, 3.0f, grey));

                retVal.add(new RectData(3f, 15f, 3.5f, 5.0f, lightGrey));
                retVal.add(new RectData(4f, 15f, 2.5f, 4f, darkGrey));
                retVal.add(new RectData(4f, 16f, 2.5f, 3.0f, grey));

                retVal.add(new RectData(13f, 15f, 3.5f, 5.0f, lightGrey));
                retVal.add(new RectData(14f, 15f, 2.5f, 4f, darkGrey));
                retVal.add(new RectData(14f, 16f, 2.5f, 3.0f, grey));

                break;
        }
        return retVal;
    }

    public BrickType getBrickType() {
        return brickType;
    }

    public void setBrickType(BrickType brickType) {
        this.brickType = brickType;

        setLandElements(generateElements());

    }


    public void setBrightness(float brightness) {
        lightGrey = new Color(0.8f*brightness, 0.8f*brightness, 0.8f*brightness, 1.0f);
        grey = new Color(0.6f*brightness, 0.6f*brightness, 0.6f*brightness, 1.0f);
        darkGrey = new Color(0.4f*brightness, 0.4f*brightness, 0.4f*brightness, 1.0f);

        this.brightness = brightness;
    }


}
