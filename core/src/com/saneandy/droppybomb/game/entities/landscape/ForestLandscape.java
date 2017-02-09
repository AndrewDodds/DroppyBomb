package com.saneandy.droppybomb.game.entities.landscape;

import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.entities.DroppyBombEntity;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.Grass;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.LeafType;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.Leaves;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.Trunk;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.TrunkType;

import java.util.ArrayList;

/**
 * Created by Andrew on 07/11/2016.
 */

public class ForestLandscape implements LandscapeGenerator {

    private ArrayList<DroppyBombEntity> generateTree(float xPos, float difficulty) {
        ArrayList<DroppyBombEntity> retVal = new ArrayList<DroppyBombEntity>();

        float height = (float)Math.random() * Constants.WORLD_HEIGHT * difficulty;
        float width = height/3.0f;

        boolean veryFirst = true;
        for(float y = Constants.LAND_HEIGHT; y < height; y += Constants.BLOCK_SIZE) {
            if (y <= height/2) {
                retVal.add(new Trunk(new Vector2(xPos, y)));
                if(veryFirst) {
                    Trunk t = new Trunk(new Vector2(xPos-10.0f, y));
                    t.setTrunkType(TrunkType.LEFTANGLESTEEP);
                    retVal.add(t);
                    t = new Trunk(new Vector2(xPos+Constants.BLOCK_SIZE, y));
                    t.setTrunkType(TrunkType.RIGHTANGLESTEEP);
                    retVal.add(t);
                    veryFirst = false;
                }
            }

            if (y > height/2) {
                boolean first = true;
                if(width > Constants.BLOCK_SIZE/1.5f) {
                    retVal.add(new Leaves(new Vector2(xPos, y)));
                }
                for(float x = xPos + width; x > xPos ; x -= Constants.BLOCK_SIZE) {
                    if (x > Constants.MARGIN && x < Constants.WORLD_WIDTH - Constants.MARGIN ) {
                        Leaves toAdd = new Leaves(new Vector2(x, y));
                        if(first) {
                            first = false;
                            toAdd.setLeafType(LeafType.RIGHTANGLE);
                        }
                        retVal.add(toAdd);
                    }
                }
                first = true;
                for(float x = xPos - width; x < xPos; x += Constants.BLOCK_SIZE) {
                    if (x > Constants.MARGIN && x < Constants.WORLD_WIDTH - Constants.MARGIN ) {
                        Leaves toAdd = new Leaves(new Vector2(x, y));
                        if(first) {
                            toAdd.setLeafType(LeafType.LEFTANGLE);
                            first = false;
                        }

                        retVal.add(toAdd);
                    }
                }
                width *=0.7f;
            }
        }

        return retVal;
    }

    private ArrayList<DroppyBombEntity> generateSteepTree(float xPos, float difficulty) {
        ArrayList<DroppyBombEntity> retVal = new ArrayList<DroppyBombEntity>();

        float height = (float)Math.random() * Constants.WORLD_HEIGHT * difficulty;
        float width = height/5.0f;

        boolean veryFirst = true;
        for(float y = Constants.LAND_HEIGHT; y < height; y += Constants.BLOCK_SIZE) {
            if (y <= height/2) {
                retVal.add(new Trunk(new Vector2(xPos, y)));
                if(veryFirst) {
                    Trunk t = new Trunk(new Vector2(xPos-10.0f, y));
                    t.setTrunkType(TrunkType.LEFTANGLESTEEP);
                    retVal.add(t);
                    t = new Trunk(new Vector2(xPos+Constants.BLOCK_SIZE, y));
                    t.setTrunkType(TrunkType.RIGHTANGLESTEEP);
                    retVal.add(t);
                    veryFirst = false;
                }
            }

            if (y > height/2 ) {
                boolean first = true;
                if(width > Constants.BLOCK_SIZE/1.5f) {
                    Leaves toAdd = new Leaves(new Vector2(xPos, y));
                    toAdd.setBrightness(0.75f);
                    retVal.add(toAdd);
                }
                for(float x = xPos + width; x > xPos ; x -= Constants.BLOCK_SIZE) {
                    if (x > Constants.MARGIN && x < Constants.WORLD_WIDTH - Constants.MARGIN ) {
                        Leaves toAdd = new Leaves(new Vector2(x, y));
                        toAdd.setBrightness(0.75f);
                        if(first) {
                            first = false;
                            toAdd.setLeafType(LeafType.RIGHTANGLESTEEP);
                        }
                        retVal.add(toAdd);
                    }
                }
                first = true;
                for(float x = xPos - width; x < xPos; x += Constants.BLOCK_SIZE) {
                    if (x > Constants.MARGIN && x < Constants.WORLD_WIDTH - Constants.MARGIN ) {
                        Leaves toAdd = new Leaves(new Vector2(x, y));
                        toAdd.setBrightness(0.75f);
                        if(first) {
                            toAdd.setPos(new Vector2(x+(0.5f* Constants.BLOCK_SIZE), y));
                            toAdd.setLeafType(LeafType.LEFTANGLESTEEP);
                            first = false;
                        }

                        retVal.add(toAdd);
                    }
                }
                width *=0.8f;
            }
        }

        return retVal;
    }

    @Override
    public ArrayList<DroppyBombEntity> generate(float difficulty) {
        ArrayList<DroppyBombEntity> retVal = new ArrayList<DroppyBombEntity>();

        for(float x = Constants.MARGIN; x < Constants.WORLD_WIDTH - Constants.MARGIN; x += Constants.BLOCK_SIZE*(2/difficulty)*((0.2*Math.random())+0.9f)) {
            if(Math.random() > 0.4) {
                retVal.addAll(generateTree(x, difficulty));
            }
            else {
                retVal.addAll(generateSteepTree(x, difficulty));
            }
        }
        for(float x = Constants.MARGIN; x < Constants.WORLD_WIDTH - Constants.MARGIN; x += Constants.BLOCK_SIZE) {
            retVal.add(new Grass(new Vector2(x, Constants.LAND_HEIGHT)));
        }

        return retVal;
    }

}
