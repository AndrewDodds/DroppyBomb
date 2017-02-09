package com.saneandy.droppybomb.game.entities.landscape;

import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.entities.DroppyBombEntity;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.Grass;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.GrassType;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.LeafType;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.Leaves;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.Mushroom;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.MushroomType;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.Trunk;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.TrunkType;

import java.util.ArrayList;

/**
 * Created by Andrew on 07/11/2016.
 */

public class MushroomLandscape implements LandscapeGenerator {

    private ArrayList<DroppyBombEntity> generateRedMushroom(float xPos, float difficulty) {
        ArrayList<DroppyBombEntity> retVal = new ArrayList<DroppyBombEntity>();

        float height = (float)Math.random() * Constants.WORLD_HEIGHT * difficulty * 0.8f;
        float width = height/4.0f;

        for(float y = Constants.LAND_HEIGHT; y < height; y += Constants.BLOCK_SIZE) {
            Mushroom toAdd = new Mushroom(new Vector2(xPos, y), MushroomType.STEM);
            retVal.add(toAdd);

            if (y > (height - 3f * Constants.BLOCK_SIZE)) {
                toAdd = new Mushroom(new Vector2(xPos - width, y), MushroomType.FULLRED);
                retVal.add(toAdd);
                toAdd = new Mushroom(new Vector2(xPos + width, y), MushroomType.FULLRED);
                retVal.add(toAdd);
            }
        }

        for (float x = xPos - width; x < xPos + width; x+= Constants.BLOCK_SIZE) {
            Mushroom toAdd = new Mushroom(new Vector2(x, height), MushroomType.FULLRED);
            retVal.add(toAdd);
        }

        return retVal;
    }

    private ArrayList<DroppyBombEntity> generateGreenMushroom(float xPos, float difficulty) {
        ArrayList<DroppyBombEntity> retVal = new ArrayList<DroppyBombEntity>();

        float height = (float)Math.random() * Constants.WORLD_HEIGHT * difficulty * 0.6f;
        float width = height/4.0f;

        for(float y = Constants.LAND_HEIGHT; y < height; y += Constants.BLOCK_SIZE) {
            Mushroom toAdd = new Mushroom(new Vector2(xPos, y), MushroomType.STEM);
            retVal.add(toAdd);
        }

        for(float y = Constants.LAND_HEIGHT + (0.5f*height); y < height; y += Constants.BLOCK_SIZE) {
            for (float x = xPos - width; x < Math.min(xPos, (xPos-width+(2f*Constants.BLOCK_SIZE))); x+= Constants.BLOCK_SIZE) {
                Mushroom toAdd = new Mushroom(new Vector2(x, y), MushroomType.FULLGREEN);
                retVal.add(toAdd);
            }
            for (float x = xPos + width; x > Math.max(xPos, (xPos+width-(2f*Constants.BLOCK_SIZE))); x-= Constants.BLOCK_SIZE) {
                Mushroom toAdd = new Mushroom(new Vector2(x, y), MushroomType.FULLGREEN);
                retVal.add(toAdd);
            }
            width *= 0.8;
        }
        for (float x = xPos - width; x < xPos + width; x+= Constants.BLOCK_SIZE) {
            Mushroom toAdd = new Mushroom(new Vector2(x, height), MushroomType.FULLGREEN);
            retVal.add(toAdd);
        }
        Mushroom toAdd = new Mushroom(new Vector2(xPos + width, height), MushroomType.FULLGREEN);
        retVal.add(toAdd);

        return retVal;
    }

    private ArrayList<DroppyBombEntity> generatePurpleMushroom(float xPos, float difficulty) {
        ArrayList<DroppyBombEntity> retVal = new ArrayList<DroppyBombEntity>();

        float height = (float)Math.random() * Constants.WORLD_HEIGHT * difficulty;
        float width = height/6.0f;

        for(float y = Constants.LAND_HEIGHT; y < height; y += Constants.BLOCK_SIZE) {
            Mushroom toAdd = new Mushroom(new Vector2(xPos, y), MushroomType.STEM);
            retVal.add(toAdd);

            if (y > (height - (2f * Constants.BLOCK_SIZE))) {
                for (float x = xPos - width; x <= xPos + width; x += Constants.BLOCK_SIZE) {
                    toAdd = new Mushroom(new Vector2(x, y), MushroomType.FULLPURPLE);
                    retVal.add(toAdd);
                }
                toAdd = new Mushroom(new Vector2(xPos + width, y), MushroomType.FULLPURPLE);
                retVal.add(toAdd);
            }
        }

        for (float x = xPos - width; x <= xPos + width; x += Constants.BLOCK_SIZE) {
            Mushroom toAdd = new Mushroom(new Vector2(x, height), MushroomType.FULLPURPLE);
            retVal.add(toAdd);
        }
        Mushroom toAdd = new Mushroom(new Vector2(xPos + width, height), MushroomType.FULLPURPLE);
        retVal.add(toAdd);

        return retVal;
    }

    @Override
    public ArrayList<DroppyBombEntity> generate(float difficulty) {
        ArrayList<DroppyBombEntity> retVal = new ArrayList<DroppyBombEntity>();

        for(float x = Constants.MARGIN; x < Constants.WORLD_WIDTH - Constants.MARGIN; x += Constants.BLOCK_SIZE*(2/difficulty)*((0.2*Math.random())+0.9f)) {
            double r = Math.random();
            if(Math.random() < 0.4) {
                retVal.addAll(generateRedMushroom(x, difficulty));
            }
            else if(Math.random() < 0.8) {
                retVal.addAll(generateGreenMushroom(x, difficulty));
            }
            else {
                retVal.addAll(generatePurpleMushroom(x, difficulty));
            }
        }
        for(float x = Constants.MARGIN; x < Constants.WORLD_WIDTH - Constants.MARGIN; x += Constants.BLOCK_SIZE) {
            retVal.add(new Grass(new Vector2(x, Constants.LAND_HEIGHT), GrassType.SHROOM));
        }

        return retVal;
    }

}
