package com.saneandy.droppybomb.game.entities.landscape.landscapeentity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.elements.BaseElementData;
import com.saneandy.droppybomb.game.elements.CircleData;
import com.saneandy.droppybomb.game.elements.RectData;
import com.saneandy.droppybomb.game.elements.RectLineData;
import com.saneandy.droppybomb.game.elements.TriangleData;

import java.util.ArrayList;

/**
 * Created by Andrew on 19/10/2016.
 */

public class Mushroom extends LandscapeEntity {

    public Mushroom(Vector2 createAtPos, MushroomType type) {
        super(createAtPos);
        mushroomType = type;
    }

    private MushroomType mushroomType;

    @Override
    public ArrayList<BaseElementData> generateExplodeElements() {
        ArrayList<BaseElementData> retVal = new ArrayList<BaseElementData>();

        switch(mushroomType) {
            case FULLRED:
                for (int i = 0; i < 30; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    float len = (float) (Math.random() * 7f) + 1f;
                    retVal.add(new RectLineData(x, y, x + len, y, 2.2f, Color.RED));
                }
                break;
            case FULLGREEN:
                for (int i = 0; i < 30; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    float len = (float) (Math.random() * 7f) + 1f;
                    retVal.add(new RectLineData(x, y, x + len, y, 2.2f, Color.GREEN));
                }
                break;
            case FULLPURPLE:
                for (int i = 0; i < 30; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    float len = (float) (Math.random() * 7f) + 1f;
                    retVal.add(new RectLineData(x, y, x + len, y, 2.2f, Color.PURPLE));
                }
                break;
            case STEM:
                for (int i = 0; i < 30; i++) {
                    float x = (float) (Math.random() * 20f);
                    float y = (float) (Math.random() * 20f);
                    float len = (float) (Math.random() * 7f) + 1f;
                    retVal.add(new RectLineData(x, y, x + len, y, 2.2f, Color.FIREBRICK));
                }
                break;
        }
        return retVal;
    }

    @Override
    public ArrayList<BaseElementData> generateElements() {
        ArrayList<BaseElementData> retVal = new ArrayList<BaseElementData>();

        switch(mushroomType) {
            case FULLRED:
                retVal.add(new CircleData(10.0f, 10.0f, 14.0f, Color.RED));

                for (int i = 0; i < 20; i++) {
                    retVal.add(new RectData( (float)(Math.random() * 22f) - 1.0f, (float)(Math.random() * 22f) - 1.0f, 2.0f, 2.0f, Color.WHITE));
                }
                break;
            case FULLGREEN:
                retVal.add(new CircleData(10.0f, 10.0f, 14.0f, Color.GREEN, 13));
                for (int i = 0; i < 7; i++) {
                    float x = (float)(Math.random() * 24f)- 2.0f;
                    float y = (float)(Math.random() * 24f)- 2.0f;
                    retVal.add(new CircleData(x, y, 2.5f, Color.YELLOW, Constants.DEFAULT_CIRCLE_SEGS));
                 }
                break;
            case FULLPURPLE:
                retVal.add(new CircleData(10.0f, 10.0f, 14.0f, Color.PURPLE, 8));
                for (int i = 0; i < 15; i++) {
                    float x = (float)(Math.random() * 22f)- 1.0f;
                    float y = (float)(Math.random() * 22f)- 1.0f;
                    retVal.add(new RectData(x, y, 6.0f, 1.5f, Color.GOLD));
                }
                break;
            case STEM:
                retVal.add(new RectData(0.0f, 0.0f, 20.0f, 20.0f, Color.FIREBRICK));
               for (int i = 0; i < 10; i++) {
                   float x = (float)(Math.random() * 22f)- 1.0f;
                   float y = (float)(Math.random() * 22f)- 1.0f;
                   retVal.add(new RectData(x, y, 1.0f, 3.5f, Color.BROWN));
                }
                break;

        }


        return retVal;
    }

    public MushroomType getMushroomType() {
        return mushroomType;
    }

    public void setMushroomType(MushroomType mushroomType) {
        this.mushroomType = mushroomType;

        setLandElements(generateElements());

    }


 }
