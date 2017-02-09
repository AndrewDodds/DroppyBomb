package com.saneandy.droppybomb.game.entities.landscape.landscapeentity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.elements.BaseElementData;
import com.saneandy.droppybomb.game.elements.RectLineData;

import java.util.ArrayList;

/**
 * Created by Andrew on 07/11/2016.
 */

public class Grass extends LandscapeEntity {

    private GrassType grassType = GrassType.GRASS;

    public Grass(Vector2 createAtPos, GrassType type) {
        super(createAtPos);
        grassType = type;
    }

    @Override
    public ArrayList<BaseElementData> generateElements() {
        ArrayList<BaseElementData> retVal = new ArrayList<BaseElementData>();

        Color lightGreen = new Color(0.5f, 1.0f, 0.5f, 1.0f);
        Color darkGreen = new Color(0.0f, 0.6f, 0.0f, 1.0f);
        Color red = new Color(1.0f, 0.4f, 0.4f, 1.0f);
        Color darkred = new Color(0.8f, 0.3f, 0.3f, 1.0f);

        for(float x=0.0f; x< Constants.BLOCK_SIZE; x+=1.0f) {
            if(Math.random() > 0.3) {
                if(Math.random() > 0.2) {
                    retVal.add(new RectLineData( x, 0.0f, x, 3.0f, 1.0f, lightGreen));
                }
                else {
                    retVal.add(new RectLineData( x, 0.0f, x, 6.0f, 1.0f, darkGreen));
                }
            }
            if(grassType == GrassType.SHROOM) {
                if(Math.random() > 0.85) {
                    float y = (float)((Math.random() + 0.5d) * 4.0d);
                    retVal.add(new RectLineData( x, 0.0f, x, y, 2.0f, darkGreen));
                    retVal.add(new RectLineData( x-1.5f, y, x+1.5f, y, 2.0f, darkred));
                }
            }
        }

        return retVal;
    }

    @Override
    public ArrayList<BaseElementData> generateExplodeElements() {
        ArrayList<BaseElementData> retVal = new ArrayList<BaseElementData>();

        for (int i = 0; i < 6; i++) {
            float x = (float)(Math.random() * 20f);
            float y = (float)(Math.random() * 20f);
            float len = (float)(Math.random() * 7f)+1f;
            retVal.add(new RectLineData( x, y, x+len, y, 2.2f, Color.BROWN));
        }
        for (int i = 0; i < 13; i++) {
            float x = (float)(Math.random() * 20f);
            float y = (float)(Math.random() * 20f);
            float len = (float)(Math.random() * 7f)+1f;
            retVal.add(new RectLineData( x, y, x+len, y, 2.2f, Color.GREEN));
        }

        return retVal;
    }

    public Grass(Vector2 createAtPos) {
        super(createAtPos);
    }
}
