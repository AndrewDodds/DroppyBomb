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

public class Trunk extends LandscapeEntity {

    public Trunk(Vector2 createAtPos) {
        super(createAtPos);
        trunkType = TrunkType.FULLBLOCK;
    }

    private TrunkType trunkType;

    @Override
    public ArrayList<BaseElementData> generateExplodeElements() {
        ArrayList<BaseElementData> retVal = new ArrayList<BaseElementData>();

        for (int i = 0; i < 10; i++) {
            float x = (float)(Math.random() * 20f);
            float y = (float)(Math.random() * 20f);
            float len = (float)(Math.random() * 7f)+1f;
            retVal.add(new RectLineData( x, y, x+len, y, 2.2f, Color.BROWN));
        }
        retVal.add(new RectLineData(2.0f, 3.0f, 2.0f, 12.0f, 1.0f, Color.BLACK));
        retVal.add(new RectLineData(5.0f, 6.0f, 6.0f, 17.0f, 1.2f, Color.BLACK));
        retVal.add(new RectLineData(7.0f, 0f, 7.0f, 8.0f, 1.8f, Color.BLACK));
        retVal.add(new RectLineData(7.0f, 17.0f, 7.0f, 20.0f, 1.5f, Color.BLACK));
        retVal.add(new RectLineData(12.0f, 5.0f, 12.0f, 17.0f, 1.8f, Color.BLACK));
        retVal.add(new RectLineData(15.0f, 9.0f, 17.0f, 19.0f, 1.8f, Color.BLACK));

        return retVal;
    }

    @Override
    public ArrayList<BaseElementData> generateElements() {
        ArrayList<BaseElementData> retVal = new ArrayList<BaseElementData>();

        switch(trunkType) {
            case FULLBLOCK:
                retVal.add(new RectData(0.0f, 0.0f, 20.0f, 20.0f, Color.ORANGE, Color.BROWN, Color.ORANGE, Color.BROWN));
                retVal.add(new RectLineData(2.0f, 3.0f, 2.0f, 12.0f, 1.0f, Color.BLACK));
                retVal.add(new RectLineData(5.0f, 6.0f, 6.0f, 17.0f, 1.2f, Color.BLACK));
                retVal.add(new RectLineData(7.0f, 0f, 7.0f, 8.0f, 1.8f, Color.BLACK));
                retVal.add(new RectLineData(7.0f, 17.0f, 7.0f, 20.0f, 1.5f, Color.BLACK));
                retVal.add(new RectLineData(12.0f, 5.0f, 12.0f, 17.0f, 1.8f, Color.BLACK));
                retVal.add(new RectLineData(15.0f, 9.0f, 17.0f, 19.0f, 1.8f, Color.BLACK));
                break;
            case LEFTANGLESTEEP:
                retVal.add(new TriangleData(0.0f, 0.0f, 10.0f, 20.0f, 10.0f, 0.0f, Color.BROWN, Color.ORANGE, Color.BROWN));
                retVal.add(new RectLineData(5.0f, 1.0f, 6.0f, 3.0f, 1.2f, Color.BLACK));
                retVal.add(new RectLineData(7.0f, 0f, 7.0f, 4.0f, 1.8f, Color.BLACK));
                retVal.add(new RectLineData(12.0f, 5.0f, 12.0f, 11.0f, 1.8f, Color.BLACK));
                retVal.add(new RectLineData(15.0f, 9.0f, 17.0f, 17.0f, 1.8f, Color.BLACK));
                break;

            case RIGHTANGLESTEEP:
                retVal.add(new TriangleData(0.0f, 0.0f, 10.0f, 0.0f, 0.0f, 20.0f, Color.BROWN, Color.ORANGE, Color.BROWN));
                retVal.add(new RectLineData(2.0f, 3.0f, 2.0f, 12.0f, 1.0f, Color.BLACK));
                retVal.add(new RectLineData(5.0f, 6.0f, 6.0f, 10.0f, 1.2f, Color.BLACK));
                retVal.add(new RectLineData(7.0f, 0f, 7.0f, 8.0f, 1.8f, Color.BLACK));
                break;
        }

        return retVal;
    }

    public TrunkType getTrunkType() {
        return trunkType;
    }

    public void setTrunkType(TrunkType trunkType) {
        this.trunkType = trunkType;

        setLandElements(generateElements());

    }

}
