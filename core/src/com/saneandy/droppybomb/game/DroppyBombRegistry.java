package com.saneandy.droppybomb.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.DroppyBombGame;
import com.saneandy.droppybomb.game.entities.DroppyBombEntity;
import com.saneandy.droppybomb.game.entities.landscape.Landscape;
import com.saneandy.droppybomb.game.entities.landscape.LandscapeType;
import com.saneandy.droppybomb.game.ui.BombType;

import java.util.ArrayList;
import java.util.HashMap;

import static com.saneandy.droppybomb.game.ui.BombType.NORMAL;

/**
 * Created by Andrew on 01/11/2016.
 *
 * Game global stuff. Held in a central location so we can keep track of what is going on.
 *
 * Also helps to manage the addition and removal of entities from the main game world, which
 * stops having to worry about concurrency errors.
 */

public class DroppyBombRegistry {


    private static int currentscreen;
    private static BombType currentbomb = BombType.NORMAL;
    private static float difficulty;
    private static int levelMain;
    private static int levelSub;
    private static float bombCounter = 0.0f;
    private static int score = 0;
    private static int hiscore = 0;
    private static float speedup = 1.0f;
    private static HashMap<BombType, Integer> bombsAvailable;

    private static Landscape land;
    private static DroppyBombGame theGame;
    // World element model
    private static ArrayList<DroppyBombEntity> elementSet;
    private static ArrayList<DroppyBombEntity> elementToAddSet;
    private static ArrayList<DroppyBombEntity> elementToRemoveSet;

    private static Sound dropBombSound;
    private static Sound planeSound;
    private static Sound explodeSound;
    private static Sound victorySound;
    private static Sound deathSound;

    private static Preferences prefs;


    private static void initBombs() {
        bombsAvailable = new HashMap<BombType, Integer>();
        bombsAvailable.put(BombType.NORMAL, 999);
        bombsAvailable.put(BombType.HIGHEXPLOSIVE, 2);
        bombsAvailable.put(BombType.CLUSTERBOMB, 0);
        bombsAvailable.put(BombType.DAISYCUTTER, 2);
        bombsAvailable.put(BombType.MISSILE, 0);
        bombsAvailable.put(BombType.HOMINGBOMB, 1);
        bombsAvailable.put(BombType.EARTHQUAKEBOMB, 0);
        bombsAvailable.put(BombType.NUCLEAR, 0);
    }

    public static LandscapeType getCurrentLandscape() {

        switch (levelSub) {
            case 1:
            default:
                return LandscapeType.HILLS;
            case 2:
                return LandscapeType.FOREST;
            case 3:
                return LandscapeType.MUSHROOM;
            case 4:
                return LandscapeType.CASTLE;
        }
    }

    public static void incLevel() {
        levelSub += 1;
        if (levelSub > 4) {
            levelSub = 1;
            levelMain += 1;
            difficulty += 0.1;
        }
    }

    public static String getCurrentBombText() {
        switch (currentbomb) {
            case NORMAL:
                return "Normal";
            case HIGHEXPLOSIVE:
                return "High Explosive";
            case CLUSTERBOMB:
                return "Cluster Bomb";
            case DAISYCUTTER:
                return "Daisy Cutter";
            case MISSILE:
                return "Missile";
            case HOMINGBOMB:
                return "Homing";
            case EARTHQUAKEBOMB:
                return "Earthquake";
            case NUCLEAR:
                return "Nuclear";
        }
        return "unknown";
    }

    public static int getCurrentBombCost() {
        switch (currentbomb) {
            case NORMAL:
                return 10;
            case HIGHEXPLOSIVE:
                return 150;
            case CLUSTERBOMB:
                return 600;
            case DAISYCUTTER:
                return 200;
            case MISSILE:
                return 200;
            case HOMINGBOMB:
                return 300;
            case EARTHQUAKEBOMB:
                return 1000;
            case NUCLEAR:
                return 5000;
        }
        return 0;
    }

    public static void buyCurrentBomb() {
        if (getCurrentBombCost() <= score) {
            score -= getCurrentBombCost();

            Integer remaining = bombsAvailable.get(currentbomb);
            remaining++;
            bombsAvailable.put(currentbomb, remaining);
        }
    }

    public static int bombsLeft(BombType b) {
        return bombsAvailable.get(b);
    }

    public static boolean canDropBomb() {
        return bombsAvailable.get(currentbomb) > 0;
    }

    public static void dropbomb() {
        Integer remaining = bombsAvailable.get(currentbomb);
        remaining--;
        bombsAvailable.put(currentbomb, remaining);
    }

    private static void initSounds() {
        dropBombSound = Gdx.audio.newSound(Gdx.files.internal("sounds/377110__jzbest__original.wav"));
        planeSound = Gdx.audio.newSound(Gdx.files.internal("sounds/40810__fresco__old-plane-in-the-distance.wav"));
        explodeSound = Gdx.audio.newSound(Gdx.files.internal("sounds/209984__zimbot__explosionbombblastambiente.wav"));
        victorySound = Gdx.audio.newSound(Gdx.files.internal("sounds/249524__limetoe__badass-victory.wav"));
        deathSound = Gdx.audio.newSound(Gdx.files.internal("sounds/136765__mitchelk__explode001.wav"));
    }



    public static void init(DroppyBombGame g) {
        prefs = Gdx.app.getPreferences("DroppyBombPreferences");
        theGame = g;
        currentscreen = Constants.TITLE_SCREEN;
        currentbomb = NORMAL;
        setDifficulty(0.6f);
        setBombCounter(0.0f);
        setScore(0);
        speedup = 1.0f;
        initBombs();
        levelMain = 1;
        levelSub = 1;
        initSounds();
        hiscore = prefs.getInteger("hiscore", 0);

    }

    public static void initGameScreen() {
        currentscreen = Constants.GAME_SCREEN;
        currentbomb = NORMAL;
        setBombCounter(0.0f);

        elementSet = new ArrayList<DroppyBombEntity>();
        elementToAddSet = new ArrayList<DroppyBombEntity>();
        elementToRemoveSet = new ArrayList<DroppyBombEntity>();
        land = null;
        speedup = 1.0f;

    }

    public static void initTitleScreen() {
        currentscreen = Constants.TITLE_SCREEN;
        currentbomb = NORMAL;
        setDifficulty(0.6f);
        levelMain = 1;
        levelSub = 1;
        setBombCounter(0.0f);
        setScore(0);
        elementSet = new ArrayList<DroppyBombEntity>();
        elementToAddSet = new ArrayList<DroppyBombEntity>();
        elementToRemoveSet = new ArrayList<DroppyBombEntity>();
        land = null;
        speedup = 1.0f;
        initBombs();
    }

    public static void initBuyingScreen() {
        currentscreen = Constants.BUY_SCREEN;
        currentbomb = NORMAL;
        setBombCounter(0.0f);
        elementSet = new ArrayList<DroppyBombEntity>();
        elementToAddSet = new ArrayList<DroppyBombEntity>();
        elementToRemoveSet = new ArrayList<DroppyBombEntity>();
        land = null;
        speedup = 1.0f;
    }

    public static void addElement(DroppyBombEntity dbe) {
        elementToAddSet.add(dbe);
    }

    public static void removeElement(DroppyBombEntity dbe) {
        elementToRemoveSet.add(dbe);
    }

    public static void reconcileElements() {

        if(elementToAddSet.isEmpty() &&elementToRemoveSet.isEmpty() ) {
            return;
        }
        ArrayList<DroppyBombEntity> newelementSet = new ArrayList<DroppyBombEntity>();
        for(DroppyBombEntity dbe:elementSet) {
            if(!elementToRemoveSet.contains(dbe)) {
                newelementSet.add(dbe);
            }
        }
        newelementSet.addAll(elementToAddSet);

        elementToAddSet = new ArrayList<DroppyBombEntity>();
        elementToRemoveSet = new ArrayList<DroppyBombEntity>();

        elementSet = newelementSet;
    }

    public static void clearElements() {
        elementSet = new ArrayList<DroppyBombEntity>();
    }

    public static int getCurrentscreen() {
        return currentscreen;
    }

    public static void setCurrentscreen(int currentscreen) {
        DroppyBombRegistry.currentscreen = currentscreen;
    }


    public static BombType getCurrentbomb() {
        return currentbomb;
    }

    public static void setCurrentbomb(BombType currentbomb) {
        DroppyBombRegistry.currentbomb = currentbomb;
    }

    public static float getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(float difficulty) {
        DroppyBombRegistry.difficulty = difficulty;
    }

    public static float getBombCounter() {
        return bombCounter;
    }

    public static void setBombCounter(float bombCounter) {
        DroppyBombRegistry.bombCounter = bombCounter;
    }

    public static int getScore() {
        return score;
    }

    public static int getHiScore() {
        return hiscore;
    }

    public static void persistHiScore() {
        prefs.putInteger("hiscore", hiscore);
        prefs.flush();
    }

    public static void setScore(int score) {
        DroppyBombRegistry.score = score;
        if (score > hiscore) {
            hiscore = score;
        }
    }

    public static Landscape getLand() {
        return land;
    }

    public static void setLand(Landscape land) {
        DroppyBombRegistry.land = land;
    }

    public static DroppyBombGame getTheGame() {
        return theGame;
    }

    public static void setTheGame(DroppyBombGame theGame) {
        DroppyBombRegistry.theGame = theGame;
    }

    public static ArrayList<DroppyBombEntity> getElementSet() {

        return elementSet;
    }

    public static float getSpeedup() {
        return speedup;
    }

    public static void setSpeedup(float speedup) {
        DroppyBombRegistry.speedup = speedup;
    }

    public static int getLevelMain() {
        return levelMain;
    }

    public static void setLevelMain(int levelMain) {
        DroppyBombRegistry.levelMain = levelMain;
    }

    public static int getLevelSub() {
        return levelSub;
    }

    public static void setLevelSub(int levelSub) {
        DroppyBombRegistry.levelSub = levelSub;
    }

    public static Sound getDropBombSound() {
        return dropBombSound;
    }

    public static Sound getPlaneSound() {
        return planeSound;
    }

    public static Sound getExplodeSound() {
        return explodeSound;
    }

    public static Sound getVictorySound() {
        return victorySound;
    }

    public static Sound getDeathSound() {
        return deathSound;
    }
}
