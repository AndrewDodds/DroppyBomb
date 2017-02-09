package com.saneandy.droppybomb.game.entities.landscape;

import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.entities.DroppyBombEntity;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.Brick;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.BrickType;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.Grass;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.LeafType;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.Leaves;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.Trunk;
import com.saneandy.droppybomb.game.entities.landscape.landscapeentity.TrunkType;

import java.util.ArrayList;

/**
 * Created by Andrew on 07/11/2016.
 *
 * Generates the castle landscape
 */

public class CastleLandscape implements LandscapeGenerator {

    private ArrayList<DroppyBombEntity> generateTree(float xPos, float difficulty) {
        ArrayList<DroppyBombEntity> retVal = new ArrayList<DroppyBombEntity>();

        float height = (float)Math.random() * Constants.WORLD_HEIGHT * difficulty * 0.35f;
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

    private ArrayList<DroppyBombEntity> generateCastle(float xPos, float difficulty) {
        ArrayList<DroppyBombEntity> retVal = new ArrayList<DroppyBombEntity>();

        float height = (float)Math.random() * Constants.WORLD_HEIGHT * difficulty;
        float width = height/2.5f;

        float bright = 0.57f + (float)(Math.random()*0.5);
        for(float y = Constants.LAND_HEIGHT; y < height - (2*Constants.BLOCK_SIZE); y += Constants.BLOCK_SIZE) {
            double r = Math.random();
            for (float x = xPos - width; x < xPos + width; x += Constants.BLOCK_SIZE) {
                Brick b = new Brick(new Vector2(x, y));
                b.setBrightness(bright);
                if (r > 0.3) {
                    retVal.add(b);
                } else {
                    b.setBrickType(BrickType.WINDOW);
                    retVal.add(b);
                }
            }
            float x = xPos + width;
            Brick b = new Brick(new Vector2(x, y));
            b.setBrightness(bright);
            if (r > 0.3) {
                retVal.add(b);
            } else {
                b.setBrickType(BrickType.WINDOW);
                retVal.add(b);
            }
        }

        boolean veryFirst = true;
        float y = height - (2*Constants.BLOCK_SIZE);
        for (float x = xPos - width; x < xPos + width; x += Constants.BLOCK_SIZE) {
            Brick b = new Brick(new Vector2(x, y));
            b.setBrightness(bright);
            if (veryFirst) {
                retVal.add(b);
                veryFirst = false;
            } else {
                b.setBrickType(BrickType.BATTLEMENT);
                retVal.add(b);
            }
        }
        float x = xPos + width;
        Brick b = new Brick(new Vector2(x, y));
        b.setBrightness(bright);
        retVal.add(b);
        y = height -  Constants.BLOCK_SIZE;
        b = new Brick(new Vector2(x, y));
        b.setBrickType(BrickType.BATTLEMENT);
        b.setBrightness(bright);
        retVal.add(b);
        x = xPos - width;
        b = new Brick(new Vector2(x, y));
        b.setBrickType(BrickType.BATTLEMENT);
        b.setBrightness(bright);
        retVal.add(b);

        return retVal;
    }

    private ArrayList<DroppyBombEntity> generateSmallCastle(float xPos, float difficulty) {
        ArrayList<DroppyBombEntity> retVal = new ArrayList<DroppyBombEntity>();

        float height = (float)Math.random() * Constants.WORLD_HEIGHT * difficulty * 0.5f;
        float width = height/3.5f;

        float bright = 0.75f + (float)(Math.random()*0.5);
        for(float y = Constants.LAND_HEIGHT; y < height - (2*Constants.BLOCK_SIZE); y += Constants.BLOCK_SIZE) {
            double r = Math.random();
            for (float x = xPos - width; x < xPos + width; x += Constants.BLOCK_SIZE) {
                Brick b = new Brick(new Vector2(x, y));
                b.setBrightness(bright);
                if (r > 0.3) {
                    retVal.add(b);
                } else {
                    b.setBrickType(BrickType.WINDOW);
                    retVal.add(b);
                }
            }
            float x = xPos + width;
            Brick b = new Brick(new Vector2(x, y));
            b.setBrightness(bright);
            if (r > 0.3) {
                retVal.add(b);
            } else {
                b.setBrickType(BrickType.WINDOW);
                retVal.add(b);
            }
        }

        boolean veryFirst = true;
        float y = height - (2*Constants.BLOCK_SIZE);
        for (float x = xPos - width; x < xPos + width; x += Constants.BLOCK_SIZE) {
            Brick b = new Brick(new Vector2(x, y));
            b.setBrightness(bright);
            if (veryFirst) {
                retVal.add(b);
                veryFirst = false;
            } else {
                b.setBrickType(BrickType.BATTLEMENT);
                retVal.add(b);
            }
        }
        float x = xPos + width;
        Brick b = new Brick(new Vector2(x, y));
        b.setBrightness(bright);
        retVal.add(b);
        y = height -  Constants.BLOCK_SIZE;
        b = new Brick(new Vector2(x, y));
        b.setBrickType(BrickType.BATTLEMENT);
        b.setBrightness(bright);
        retVal.add(b);
        x = xPos - width;
        b = new Brick(new Vector2(x, y));
        b.setBrickType(BrickType.BATTLEMENT);
        b.setBrightness(bright);
        retVal.add(b);

        return retVal;
    }

    @Override
    public ArrayList<DroppyBombEntity> generate(float difficulty) {
        ArrayList<DroppyBombEntity> retVal = new ArrayList<DroppyBombEntity>();

        for(float x = (3*Constants.MARGIN); x < Constants.WORLD_WIDTH - (3*Constants.MARGIN); x += 2f*Constants.BLOCK_SIZE*(2/difficulty)*((0.2*Math.random())+0.9f)) {
            if(Math.random() > 0.6) {
                retVal.addAll(generateCastle(x, difficulty));
            }
        }
        for(float x = (2*Constants.MARGIN); x < Constants.WORLD_WIDTH - (2*Constants.MARGIN); x += 2f*Constants.BLOCK_SIZE*(2/difficulty)*((0.2*Math.random())+0.9f)) {
            if(Math.random() > 0.4) {
                retVal.addAll(generateSmallCastle(x, difficulty));
            }
        }
        for(float x = Constants.MARGIN; x < Constants.WORLD_WIDTH - Constants.MARGIN; x += Constants.BLOCK_SIZE*(2/difficulty)*((0.2*Math.random())+0.9f)) {
            if(Math.random() > 0.6) {
                retVal.addAll(generateTree(x, difficulty));
            }
        }
        for(float x = Constants.MARGIN; x < Constants.WORLD_WIDTH - Constants.MARGIN; x += Constants.BLOCK_SIZE) {
            retVal.add(new Grass(new Vector2(x, Constants.LAND_HEIGHT)));
        }

        return retVal;
    }

}
