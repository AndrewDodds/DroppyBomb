package com.saneandy.droppybomb.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.DroppyBombRegistry;
import com.saneandy.droppybomb.game.bombs.Bomb;
import com.saneandy.droppybomb.game.entities.DroppyBombEntity;
import com.saneandy.droppybomb.game.entities.landscape.Landscape;
import com.saneandy.droppybomb.game.entities.Plane;
import com.saneandy.droppybomb.game.text.CircFont;
import com.saneandy.droppybomb.game.text.RenderableWord;
import com.saneandy.droppybomb.game.ui.BombButton;
import com.saneandy.droppybomb.game.ui.BombType;
import com.saneandy.droppybomb.game.ui.SpeedButton;

import java.util.ArrayList;

/**
 * Created by Andrew on 17/10/2016.
 *
 * Main game screen, handles landscape and main game loop
 */

public class GameScreen extends InputAdapter implements Screen{
    public static final String TAG = GameScreen.class.getName();

    private ShapeRenderer shapeRender;
    private FitViewport viewport;
    private CircFont myFont;

    private ArrayList<BombButton> bombButtons;
    private SpeedButton speedButton;

    private boolean initialised = false;

    public GameScreen() {

    }

    private void setUpBombButtons() {
        bombButtons = new ArrayList<BombButton>();

        bombButtons.add(new BombButton(new Vector2(Constants.WORLD_WIDTH/2f, Constants.WORLD_HEIGHT - Constants.BUTTON_SIZE - 10.0f), new Vector2(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE), BombType.NORMAL));
        bombButtons.get(0).setSelected();
        bombButtons.add(new BombButton(new Vector2(Constants.WORLD_WIDTH/2f + Constants.BUTTON_SIZE  + 1.0f, Constants.WORLD_HEIGHT - Constants.BUTTON_SIZE - 10.0f), new Vector2(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE), BombType.HIGHEXPLOSIVE));
        bombButtons.add(new BombButton(new Vector2(Constants.WORLD_WIDTH/2f + (2f*(Constants.BUTTON_SIZE  + 1.0f)), Constants.WORLD_HEIGHT - Constants.BUTTON_SIZE - 10.0f), new Vector2(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE), BombType.DAISYCUTTER));
        bombButtons.add(new BombButton(new Vector2(Constants.WORLD_WIDTH/2f + (3f*(Constants.BUTTON_SIZE  + 1.0f)), Constants.WORLD_HEIGHT - Constants.BUTTON_SIZE - 10.0f), new Vector2(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE), BombType.CLUSTERBOMB));
        bombButtons.add(new BombButton(new Vector2(Constants.WORLD_WIDTH/2f + (4f*(Constants.BUTTON_SIZE  + 1.0f)), Constants.WORLD_HEIGHT - Constants.BUTTON_SIZE - 10.0f), new Vector2(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE), BombType.MISSILE));
        bombButtons.add(new BombButton(new Vector2(Constants.WORLD_WIDTH/2f + (5f*(Constants.BUTTON_SIZE  + 1.0f)), Constants.WORLD_HEIGHT - Constants.BUTTON_SIZE - 10.0f), new Vector2(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE), BombType.HOMINGBOMB));
        bombButtons.add(new BombButton(new Vector2(Constants.WORLD_WIDTH/2f + (6f*(Constants.BUTTON_SIZE  + 1.0f)), Constants.WORLD_HEIGHT - Constants.BUTTON_SIZE - 10.0f), new Vector2(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE), BombType.EARTHQUAKEBOMB));
        bombButtons.add(new BombButton(new Vector2(Constants.WORLD_WIDTH/2f + (7f*(Constants.BUTTON_SIZE  + 1.0f)), Constants.WORLD_HEIGHT - Constants.BUTTON_SIZE - 10.0f), new Vector2(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE), BombType.NUCLEAR));

        speedButton = new SpeedButton(new Vector2(Constants.WORLD_WIDTH - (1.5f*Constants.BUTTON_SIZE ), Constants.WORLD_HEIGHT - Constants.BUTTON_SIZE - 10.0f), new Vector2(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE));
    }

    private void resetGraphics() {
        initialised = false;
        shapeRender = new ShapeRenderer();
        viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        myFont = new CircFont();

    }

    private void init() {
        setUpBombButtons();
        DroppyBombRegistry.setCurrentbomb(BombType.NORMAL);

        DroppyBombRegistry.clearElements();
        DroppyBombRegistry.addElement(new Plane());


        DroppyBombRegistry.setLand(new Landscape(DroppyBombRegistry.getCurrentLandscape(), DroppyBombRegistry.getDifficulty()));

        DroppyBombRegistry.reconcileElements();
        initialised = true;

     }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);

        resetGraphics();

        DroppyBombRegistry.initGameScreen();

        init();
     }

    private void updatePlane() {
        Plane p = (Plane)DroppyBombRegistry.getElementSet().get(0); // Always OK, only constant element

        if(p.getHasExploded()) {
            Gdx.app.log(TAG, "Plane has exploded:" );
            initialised = false;
            DroppyBombRegistry.persistHiScore();
            DroppyBombRegistry.initTitleScreen();
            DroppyBombRegistry.getTheGame().setTitleScreen();
            return;
        }

        if(p.getHasLanded()) {
            Gdx.app.log(TAG, "Plane has landed:" );
            DroppyBombRegistry.getPlaneSound().stop();
            DroppyBombRegistry.getVictorySound().play(1.0f);
            DroppyBombRegistry.setBombCounter(0.0f);
            DroppyBombRegistry.incLevel();
            initialised = false;
            DroppyBombRegistry.initBuyingScreen();
            DroppyBombRegistry.getTheGame().setBuyScreen();

            return;
        }

        if(p.needSmoke()){

            for(DroppyBombEntity dpe : p.getSmoke()) {
                DroppyBombRegistry.addElement(dpe);
            }
        }

    }

    private void update(float delta) {
        if(!initialised)
            return;

        if(!DroppyBombRegistry.getLand().isLeadIn()) {

            updatePlane();

            for (DroppyBombEntity dbe : DroppyBombRegistry.getElementSet()) {
                dbe.update(delta);
                if (dbe.getHasExploded() && !(dbe instanceof Plane)) {
                    DroppyBombRegistry.removeElement(dbe);
                }

                if (dbe instanceof Bomb) {
                    Bomb b = (Bomb) dbe;
                    if (b.needSmoke()) {
                        for (DroppyBombEntity dpe : b.getSmoke()) {
                            DroppyBombRegistry.addElement(dpe);
                        }
                    }
                }
            }


            DroppyBombRegistry.reconcileElements();
        }

        if(DroppyBombRegistry.getLand() != null) {
            int destroyed = DroppyBombRegistry.getLand().update(delta);

            if (destroyed > 0) {
                Gdx.app.log(TAG, "Blocks destroyed:" + destroyed);
            }
        }

        speedButton.update(delta);

        for(BombButton b : bombButtons) {
            b.update(delta);
        }

    }

    private void dropBomb(Plane p) {

        if(DroppyBombRegistry.canDropBomb() && DroppyBombRegistry.getBombCounter() < 0.0f) {
            DroppyBombRegistry.setBombCounter(Constants.BOMB_COUNDOWN_START);

            DroppyBombRegistry.dropbomb();
            ArrayList<Bomb> bombs = p.getBomb();
            for (Bomb b : bombs) {
                DroppyBombRegistry.addElement(b);
            }
        }

    }

    private void processInput(float delta) {
        if(!initialised)
            return;

        if(DroppyBombRegistry.getLand().isLeadIn())
            return;

        Plane p = (Plane)DroppyBombRegistry.getElementSet().get(0); // Always OK, only constant element

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            for(DroppyBombEntity dpe : DroppyBombRegistry.getElementSet()) {
                if(!dpe.getIsExploding()) {
                    dpe.explode();
                }
            }
        }

        DroppyBombRegistry.setBombCounter(DroppyBombRegistry.getBombCounter()-delta);
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            dropBomb(p);
        }

        DroppyBombRegistry.getLand().processCollisions();
        DroppyBombRegistry.reconcileElements();
    }


    private void topTextDisplay() {

        RenderableWord title =  new RenderableWord(myFont, "Score: "+DroppyBombRegistry.getScore(), Constants.BLOCK_SIZE, Constants.WORLD_HEIGHT-Constants.BLOCK_SIZE, new Color[]{Color.ORANGE}, 1.4f);
        RenderableWord title2 = new RenderableWord(myFont, "   High: "+DroppyBombRegistry.getHiScore(), Constants.BLOCK_SIZE*11, Constants.WORLD_HEIGHT-Constants.BLOCK_SIZE, new Color[]{Color.ORANGE}, 1.4f);
        RenderableWord title3 = new RenderableWord(myFont, "Bomb : "+DroppyBombRegistry.getCurrentBombText(), Constants.BLOCK_SIZE, Constants.WORLD_HEIGHT-(Constants.BLOCK_SIZE*2), new Color[]{Color.ORANGE}, 1.4f);
        RenderableWord title4 = new RenderableWord(myFont, "Level: "+DroppyBombRegistry.getLevelMain()+"-"+DroppyBombRegistry.getLevelSub(), Constants.BLOCK_SIZE, Constants.WORLD_HEIGHT-(Constants.BLOCK_SIZE*3), new Color[]{Color.ORANGE}, 1.4f);

        title.render(shapeRender, 1f, 2f);
        title2.render(shapeRender, 1f, 2f);
        title3.render(shapeRender, 1f, 2f);
        title4.render(shapeRender, 1f, 2f);

    }

    @Override
    public void render(float delta) {

        switch(speedButton.getSpeedType()){
            case FAST:
                delta *=3f;
                break;
            case EXTRAFAST:
                delta *=9f;
                break;
        }

        update(delta);
        processInput(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        if(!initialised)
            return;

        viewport.apply();

        shapeRender.setProjectionMatrix(viewport.getCamera().combined);
        shapeRender.begin(ShapeRenderer.ShapeType.Filled);

        //Draw the sky..
        shapeRender.rect(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, Color.CYAN, Color.CYAN, Color.BLUE, Color.BLUE);

        for(DroppyBombEntity dpe : DroppyBombRegistry.getElementSet()) {
            dpe.render(delta, shapeRender);
        }

        DroppyBombRegistry.getLand().render(delta, shapeRender);

        Color darkGreen = new Color(0.0f, 0.5f, 0.0f, 1.0f);
        shapeRender.rect(0, 0, Constants.WORLD_WIDTH, Constants.LAND_HEIGHT, Color.GREEN, Color.GREEN, darkGreen, darkGreen);

        for(BombButton b:bombButtons) {
            b.render(delta, shapeRender );
        }
        speedButton.render(delta, shapeRender);

        topTextDisplay();

        shapeRender.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

    }


    /*
    Start input adaptation
     */
    @Override
    public boolean keyDown(int keycode) {

        return true;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(!initialised)
            return true;

        if(DroppyBombRegistry.getLand().isLeadIn())
            return true;

        Vector2 worldClick = viewport.unproject(new Vector2(screenX, screenY));

        Gdx.app.log(TAG, "touchDown:" + worldClick.toString());

        BombButton clickedButton = null;
        for(BombButton b:bombButtons) {

            if(b.isHit(worldClick)) {
                b.setSelected();
                clickedButton = b;
                DroppyBombRegistry.setCurrentbomb(b.getBombType());
            }
        }

        if (clickedButton != null) {
            for (BombButton b : bombButtons) {
                if (!b.equals(clickedButton)) {
                    b.setUnSelected();
                }
            }
        }
        else {
            if (!speedButton.isHit(worldClick)) {
                Plane p = (Plane)DroppyBombRegistry.getElementSet().get(0); // Always OK, only constant element
                dropBomb(p);

                DroppyBombRegistry.reconcileElements();
            }

        }


        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return true;
    }



    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        shapeRender.dispose();
    }

    @Override
    public void dispose() {
        shapeRender.dispose();
    }
}
