package com.saneandy.droppybomb.game.entities.landscape;

import com.saneandy.droppybomb.game.entities.DroppyBombEntity;

import java.util.ArrayList;

/**
 * Created by Andrew on 07/11/2016.
 */

public interface LandscapeGenerator {

    ArrayList<DroppyBombEntity> generate(float difficulty);
}
