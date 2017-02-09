package com.saneandy.droppybomb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.DroppyBombRegistry;
import com.saneandy.droppybomb.game.bombs.Bomb;
import com.saneandy.droppybomb.game.entities.DroppyBombEntity;
import com.saneandy.droppybomb.game.entities.Plane;
import com.saneandy.droppybomb.game.text.CircFont;
import com.saneandy.droppybomb.game.text.RenderableWord;
import com.saneandy.droppybomb.game.ui.BombType;

import java.util.ArrayList;

/**
 * Created by Andrew on 17/10/2016.
 *
 * Game title screen
 */

public class TitleScreen extends InputAdapter implements Screen{

    public static final String TAG = TitleScreen.class.getName();

    private FitViewport viewport;
    private ShapeRenderer shapeRender;
    private CircFont myFont;

    private float countdown;

    private boolean isLeadIn;
    private boolean isLeadOut;

    private ArrayList<RenderableWord> title;

    private boolean initialised = false;
    private boolean hasBomb = false;

    public TitleScreen() {

    }

    @Override
    public void show() {
        countdown = 10.0f;

        shapeRender = new ShapeRenderer();
        viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        myFont = new CircFont();

        isLeadIn = true;
        isLeadOut = false;

        DroppyBombRegistry.initTitleScreen();

        init();

        Gdx.input.setInputProcessor(this);


    }

    private void init() {

        DroppyBombRegistry.clearElements();

        DroppyBombRegistry.addElement(new Plane());

        DroppyBombRegistry.reconcileElements();

        title = new ArrayList<RenderableWord>();
        title.add(new RenderableWord(myFont, "Droppy Bomb!", 220f, Constants.WORLD_HEIGHT - 30, new Color[]{Color.BLACK, Color.BLUE}, 4.0f));
        title.add(new RenderableWord(myFont, "SaneAndy Software 2017.", 225f, Constants.WORLD_HEIGHT - 80, new Color[]{Color.PURPLE, Color.BLUE, Color.FIREBRICK}, 2.0f));
        title.add(new RenderableWord(myFont, "Sounds by zimbot, mitchelk, limetoe, fresco and jzbest of FreeSound.org", 140f, Constants.WORLD_HEIGHT - 100, new Color[]{Color.BROWN, Color.BLUE}, 0.8f));
        title.add(new RenderableWord(myFont, "High Score: "+DroppyBombRegistry.getHiScore(), 280f, Constants.WORLD_HEIGHT-140, new Color[]{Color.BLACK}, 2.0f));
        title.add(new RenderableWord(myFont, "Press Space or tap to start.", 170f, Constants.WORLD_HEIGHT - 180, new Color[]{Color.BLACK, Color.BLUE}, 2.0f));


        initialised = true;

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        viewport.apply();

        shapeRender.setProjectionMatrix(viewport.getCamera().combined);
        shapeRender.begin(ShapeRenderer.ShapeType.Filled);
        // We can do even more interesting things with colors, like specifying a color for each corner!
        shapeRender.rect(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, Color.CYAN, Color.CYAN, Color.GREEN, Color.GREEN);

        for(RenderableWord w:title) {
            w.render(shapeRender, 1f, 2f, countdown, 1.0f);
        }

        for(DroppyBombEntity dpe : DroppyBombRegistry.getElementSet()) {
            dpe.render(delta, shapeRender);
        }

        Color darkGreen = new Color(0.0f, 0.5f, 0.0f, 1.0f);
        shapeRender.rect(0, 0, Constants.WORLD_WIDTH, Constants.LAND_HEIGHT, Color.GREEN, Color.GREEN, darkGreen, darkGreen);

        shapeRender.end();


    }

    private void cycleBombType() {
        switch(DroppyBombRegistry.getCurrentbomb()) {
            case NORMAL:
                DroppyBombRegistry.setCurrentbomb(BombType.HIGHEXPLOSIVE);
                break;
            case HIGHEXPLOSIVE:
                DroppyBombRegistry.setCurrentbomb(BombType.DAISYCUTTER);
                break;
            case DAISYCUTTER:
                DroppyBombRegistry.setCurrentbomb(BombType.CLUSTERBOMB);
                break;
            case CLUSTERBOMB:
                DroppyBombRegistry.setCurrentbomb(BombType.MISSILE);
                break;
            case MISSILE:
                DroppyBombRegistry.setCurrentbomb(BombType.HOMINGBOMB);
                break;
            case HOMINGBOMB:
                DroppyBombRegistry.setCurrentbomb(BombType.EARTHQUAKEBOMB);
                break;
            case EARTHQUAKEBOMB:
                DroppyBombRegistry.setCurrentbomb(BombType.NUCLEAR);
                break;
            case NUCLEAR:
                DroppyBombRegistry.setCurrentbomb(BombType.NORMAL);
                break;
            default:
                DroppyBombRegistry.setCurrentbomb(BombType.NORMAL);
                break;

        }
    }

    private void updatePlane() {
        Plane p = (Plane)DroppyBombRegistry.getElementSet().get(0); // Always OK, only constant element

        if(p.getBoundingBox().x > Constants.WORLD_WIDTH/4f && p.getBoundingBox().x < (Constants.WORLD_WIDTH/4f)+10.0f) {
            hasBomb = false;
        }

        if(p.getBoundingBox().x > (Constants.WORLD_WIDTH/2f)-70f && !hasBomb) {
            ArrayList<Bomb> bombs = p.getBomb();
            for(Bomb b:bombs) {
                DroppyBombRegistry.addElement(b);
            }
            cycleBombType();
            hasBomb = true;
        }

        if(p.needSmoke()){
            for(DroppyBombEntity dpe : p.getSmoke()) {
                DroppyBombRegistry.addElement(dpe);
            }
        }


     }

    private void update(float delta) {
        if (!initialised)
            return;

        updatePlane();

        for(DroppyBombEntity dbe : DroppyBombRegistry.getElementSet()) {
            dbe.update(delta);
            if(dbe.getHasExploded() && !(dbe instanceof Plane)) {
                DroppyBombRegistry.removeElement(dbe);
            }

            if(dbe instanceof Bomb) {
                Bomb b = (Bomb) dbe;
                if(b.needSmoke()) {
                    for(DroppyBombEntity dpe : b.getSmoke()) {
                        DroppyBombRegistry.addElement(dpe);
                    }
                }
            }
        }
        DroppyBombRegistry.reconcileElements();

        if(isLeadIn || isLeadOut) {

            if(isLeadIn) {
                countdown *= (1f - delta);
            }
            else {
                countdown *= (1f + delta);

            }

            if(countdown < 1.01f) {
                countdown = 1f;
                if(isLeadIn) {
                    isLeadIn = false;
                }
            }
            if(countdown > 10f) {
                if(isLeadOut) {
                    isLeadOut = false;
                    initialised = false;
                    DroppyBombRegistry.getTheGame().setGameScreen();
                }
            }

            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) ){
            isLeadOut = true;
        }


    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(!initialised)
            return true;

        Vector2 worldClick = viewport.unproject(new Vector2(screenX, screenY));

        if(isLeadIn || isLeadOut) {
            Gdx.app.log(TAG, "touchDown:" + worldClick.toString());
            return true;
        }

        Gdx.app.log(TAG, "About to enter game:" );
        isLeadOut = true;

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

    }

    @Override
    public void dispose() {

    }
}
