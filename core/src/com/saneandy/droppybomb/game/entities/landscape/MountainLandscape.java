package com.saneandy.droppybomb.game.entities.landscape;

import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.entities.DroppyBombEntity;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.Leaves;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.Rock;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.RockType;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.Trunk;

import java.util.ArrayList;

/**
 * Created by Andrew on 07/11/2016.
 */

public class MountainLandscape implements LandscapeGenerator {
    private ArrayList<DroppyBombEntity> generateHill(float xPos, float difficulty) {
        ArrayList<DroppyBombEntity> retVal = new ArrayList<DroppyBombEntity>();

        float height = (float) Math.random() * Constants.WORLD_HEIGHT * difficulty;
        float width = height / 3.0f;
        int wint = (int)width / Constants.BLOCK_SIZE.intValue();
        width = wint * Constants.BLOCK_SIZE;

        for (float y = Constants.LAND_HEIGHT; y < height; y += Constants.BLOCK_SIZE) {
            boolean isfirst = true;
            for (float x = xPos - width; x < xPos + width; x += Constants.BLOCK_SIZE) {
                if (x > Constants.MARGIN && x < Constants.WORLD_WIDTH - Constants.MARGIN) {
                    Rock toAdd = new Rock(new Vector2(x, y));
                    if(isfirst) {
                        toAdd.setRockType(RockType.LEFTANGLE);
                        isfirst = false;
                    }
                    else if((x + Constants.BLOCK_SIZE) >= xPos + width) {
                        toAdd.setRockType(RockType.RIGHTANGLE);
                    }
                    if(y > (Constants.WORLD_HEIGHT/2f)) {
                        toAdd.setBrightness(1.25f);
                    }
                    retVal.add(toAdd);
                }
                isfirst = false;
            }
            width -= Constants.BLOCK_SIZE;
        }

        return retVal;
    }

    private ArrayList<DroppyBombEntity> generateVolcano(float xPos, float difficulty) {
        ArrayList<DroppyBombEntity> retVal = new ArrayList<DroppyBombEntity>();

        boolean righthanded = false;
        if(Math.random() > 0.5d)
            righthanded = true;

        boolean useSteep = false;

        float height = (float) Math.random() * Constants.WORLD_HEIGHT * difficulty;
        float width = height / 3.0f;
        int wint = (int)width / Constants.BLOCK_SIZE.intValue();
        width = wint * Constants.BLOCK_SIZE;

        for (float y = Constants.LAND_HEIGHT; y < height; y += Constants.BLOCK_SIZE) {
            boolean isfirst = true;

            if(width < (Constants.BLOCK_SIZE * 2.0f)) {
                float x = xPos - width;
                if (x > Constants.MARGIN && x < Constants.WORLD_WIDTH - Constants.MARGIN) {
                    Rock toAdd = new Rock(new Vector2(x, y));
                    if (!righthanded) {
                        toAdd.setRockType(RockType.LEFTANGLESTEEPLAVA);
                    } else {
                        toAdd.setRockType(RockType.LEFTANGLESTEEP);
                    }
                    retVal.add(toAdd);
                }
                x = xPos - width +(Constants.BLOCK_SIZE * 0.5f);
                if (x > Constants.MARGIN && x < Constants.WORLD_WIDTH - Constants.MARGIN) {
                    Rock toAdd = new Rock(new Vector2(x, y));
                    toAdd.setRockType(RockType.LAVA);
                    retVal.add(toAdd);
                }
                x = xPos - width + (Constants.BLOCK_SIZE * 1.5f);
                if (x > Constants.MARGIN && x < Constants.WORLD_WIDTH - Constants.MARGIN) {
                    Rock toAdd = new Rock(new Vector2(x, y));
                    if (righthanded) {
                        toAdd.setRockType(RockType.RIGHTANGLESTEEPLAVA);
                    } else {
                        toAdd.setRockType(RockType.RIGHTANGLESTEEP);
                    }
                    retVal.add(toAdd);
                }
            }
            else {
                for (float x = xPos - width; x < xPos + width; x += Constants.BLOCK_SIZE) {
                    if (x > Constants.MARGIN && x < Constants.WORLD_WIDTH - Constants.MARGIN) {
                        Rock toAdd = new Rock(new Vector2(x, y));
                        if (isfirst) {
                            if (!righthanded) {
                                toAdd.setRockType(RockType.LEFTANGLELAVA);
                            } else {
                                toAdd.setRockType(RockType.LEFTANGLE);
                            }
                            isfirst = false;
                        } else if ((x + Constants.BLOCK_SIZE) >= xPos + width) {
                            if (righthanded) {
                                toAdd.setRockType(RockType.RIGHTANGLELAVA);
                            } else {
                                toAdd.setRockType(RockType.RIGHTANGLE);
                            }
                        }

                        if (y > (Constants.WORLD_HEIGHT / 2f)) {
                            toAdd.setBrightness(1.25f);
                        }
                        retVal.add(toAdd);
                    }
                    isfirst = false;
                }
            }
            width -= Constants.BLOCK_SIZE;
            if(width < Constants.BLOCK_SIZE ) {
                break;
            }
        }

        return retVal;
    }

    private ArrayList<DroppyBombEntity> generateSteepHill(float xPos, float difficulty) {
        ArrayList<DroppyBombEntity> retVal = new ArrayList<DroppyBombEntity>();

        float height = (float) Math.random() * Constants.WORLD_HEIGHT * difficulty;
        float width = height / 3.0f;
        int wint = (int)width / Constants.BLOCK_SIZE.intValue();
        width = wint * Constants.BLOCK_SIZE;

        for (float y = Constants.LAND_HEIGHT; y < height; y += Constants.BLOCK_SIZE) {
            boolean isfirst = true;
            for (float x = xPos - width; x < xPos + width; x += Constants.BLOCK_SIZE/2) {
                if (x > Constants.MARGIN && x < Constants.WORLD_WIDTH - Constants.MARGIN) {
                    Rock toAdd = new Rock(new Vector2(x, y));
                    toAdd.setBrightness(0.8f);
                    if(isfirst) {
                        toAdd.setRockType(RockType.LEFTANGLESTEEP);

                    }
                    else if((x + (Constants.BLOCK_SIZE/2)) >= xPos + width) {
                        toAdd.setRockType(RockType.RIGHTANGLESTEEP);
                    }
                    else if((x + Constants.BLOCK_SIZE) >= xPos + width) {
                        toAdd.setPos(new Vector2(x-(Constants.BLOCK_SIZE/2), y));
                    }
                    retVal.add(toAdd);
                }
                isfirst = false;
            }
            width -= (Constants.BLOCK_SIZE/2f);
        }

        return retVal;
    }


    @Override
    public ArrayList<DroppyBombEntity> generate(float difficulty) {
        ArrayList<DroppyBombEntity> retVal = new ArrayList<DroppyBombEntity>();

        for (float x = Constants.MARGIN; x < Constants.WORLD_WIDTH - Constants.MARGIN; x += Constants.BLOCK_SIZE * (4 / difficulty)) {
            retVal.addAll(generateSteepHill(x, difficulty));
        }

        for (float x = Constants.MARGIN; x < Constants.WORLD_WIDTH - Constants.MARGIN; x += Constants.BLOCK_SIZE * (2 / difficulty)) {
            if(Math.random() > 0.35d) {
                retVal.addAll(generateHill(x, difficulty));
            }
            else
            {
                retVal.addAll(generateVolcano(x, difficulty));

            }
        }

        return retVal;

    }
}
