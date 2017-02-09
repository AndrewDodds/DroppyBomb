package com.saneandy.droppybomb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.saneandy.droppybomb.Constants;
import com.saneandy.droppybomb.game.DroppyBombRegistry;
import com.saneandy.droppybomb.game.entities.DroppyBombEntity;
import com.saneandy.droppybomb.game.text.CircFont;
import com.saneandy.droppybomb.game.text.RenderableWord;
import com.saneandy.droppybomb.game.ui.BombButton;
import com.saneandy.droppybomb.game.ui.BombType;
import com.saneandy.droppybomb.game.ui.SpeedButton;

import java.util.ArrayList;

/**
 * Created by Andrew on 17/10/2016.
 *
 * Screen to allow people to buy bombs inbetween levels.
 */

public class BuyingScreen extends InputAdapter implements Screen{

    public static final String TAG = BuyingScreen.class.getName();

    private ArrayList<BombButton> bombButtons;
    private ArrayList<RenderableWord> bombTitles;
    private SpeedButton speedButton;

    private FitViewport viewport;
    private ShapeRenderer shapeRender;
    private CircFont myFont;

    private float countdown = 1.0f;

    private  boolean isLeadIn;
    private boolean isLeadOut;

    private RenderableWord title;
    private RenderableWord title2;

    private  boolean initialised = false;

    public BuyingScreen() {

    }

    private void setUpBombButtons() {
        bombButtons = new ArrayList<BombButton>();

        float bombButX = Constants.WORLD_WIDTH/4f;
        float bombButY =  Constants.WORLD_HEIGHT/1.4f;

        bombButtons.add(new BombButton(new Vector2(bombButX, bombButY), new Vector2(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE), BombType.NORMAL));
        bombButtons.get(0).setSelected();
        bombButtons.add(new BombButton(new Vector2(bombButX, bombButY - (Constants.BUTTON_SIZE *1.2f)), new Vector2(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE), BombType.HIGHEXPLOSIVE));
        bombButtons.add(new BombButton(new Vector2(bombButX, bombButY - (Constants.BUTTON_SIZE *2.4f)), new Vector2(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE), BombType.DAISYCUTTER));
        bombButtons.add(new BombButton(new Vector2(bombButX, bombButY - (Constants.BUTTON_SIZE *3.6f)), new Vector2(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE), BombType.CLUSTERBOMB));
        bombButtons.add(new BombButton(new Vector2(bombButX, bombButY - (Constants.BUTTON_SIZE *4.8f)), new Vector2(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE), BombType.MISSILE));
        bombButtons.add(new BombButton(new Vector2(bombButX, bombButY - (Constants.BUTTON_SIZE *6.0f)), new Vector2(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE), BombType.HOMINGBOMB));
        bombButtons.add(new BombButton(new Vector2(bombButX, bombButY - (Constants.BUTTON_SIZE *7.2f)), new Vector2(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE), BombType.EARTHQUAKEBOMB));
        bombButtons.add(new BombButton(new Vector2(bombButX, bombButY - (Constants.BUTTON_SIZE *8.4f)), new Vector2(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE), BombType.NUCLEAR));

        bombTitles = new ArrayList<RenderableWord>();
        float typeTextX = Constants.WORLD_WIDTH/3f;
        float costTextX = Constants.WORLD_WIDTH/1.75f + 25f;
        float startYPos = Constants.WORLD_HEIGHT/1.4f + 23.0f;
        DroppyBombRegistry.setCurrentbomb(BombType.NORMAL);
        bombTitles.add(new RenderableWord(myFont, DroppyBombRegistry.getCurrentBombText(), typeTextX, startYPos, new Color[]{Color.BLACK}, 1.6f));
        bombTitles.add(new RenderableWord(myFont, " : "+DroppyBombRegistry.getCurrentBombCost(), costTextX, startYPos, new Color[]{Color.BLACK}, 1.6f));
        DroppyBombRegistry.setCurrentbomb(BombType.HIGHEXPLOSIVE);
        bombTitles.add(new RenderableWord(myFont, DroppyBombRegistry.getCurrentBombText(), typeTextX,  startYPos  - (Constants.BUTTON_SIZE *1.2f), new Color[]{Color.YELLOW}, 1.6f));
        bombTitles.add(new RenderableWord(myFont, " : "+DroppyBombRegistry.getCurrentBombCost(), costTextX, startYPos  - (Constants.BUTTON_SIZE *1.2f), new Color[]{Color.BLACK}, 1.6f));
        DroppyBombRegistry.setCurrentbomb(BombType.DAISYCUTTER);
        bombTitles.add(new RenderableWord(myFont, DroppyBombRegistry.getCurrentBombText(), typeTextX,  startYPos  - (Constants.BUTTON_SIZE *2.4f), new Color[]{Color.ORANGE}, 1.6f));
        bombTitles.add(new RenderableWord(myFont, " : "+DroppyBombRegistry.getCurrentBombCost(), costTextX,  startYPos  - (Constants.BUTTON_SIZE *2.4f), new Color[]{Color.BLACK}, 1.6f));
        DroppyBombRegistry.setCurrentbomb(BombType.CLUSTERBOMB);
        bombTitles.add(new RenderableWord(myFont, DroppyBombRegistry.getCurrentBombText(), typeTextX,  startYPos  - (Constants.BUTTON_SIZE *3.6f), new Color[]{Color.GREEN}, 1.6f));
        bombTitles.add(new RenderableWord(myFont, " : "+DroppyBombRegistry.getCurrentBombCost(), costTextX,  startYPos  - (Constants.BUTTON_SIZE *3.6f), new Color[]{Color.BLACK}, 1.6f));
        DroppyBombRegistry.setCurrentbomb(BombType.MISSILE);
        bombTitles.add(new RenderableWord(myFont, DroppyBombRegistry.getCurrentBombText(), typeTextX,  startYPos  - (Constants.BUTTON_SIZE *4.8f), new Color[]{Color.RED}, 1.6f));
        bombTitles.add(new RenderableWord(myFont, " : "+DroppyBombRegistry.getCurrentBombCost(), costTextX,  startYPos  - (Constants.BUTTON_SIZE *4.8f), new Color[]{Color.BLACK}, 1.6f));
        DroppyBombRegistry.setCurrentbomb(BombType.HOMINGBOMB);
        bombTitles.add(new RenderableWord(myFont, DroppyBombRegistry.getCurrentBombText(), typeTextX, startYPos  - (Constants.BUTTON_SIZE *6.0f), new Color[]{Color.CYAN}, 1.6f));
        bombTitles.add(new RenderableWord(myFont, " : "+DroppyBombRegistry.getCurrentBombCost(), costTextX, startYPos  - (Constants.BUTTON_SIZE *6.0f), new Color[]{Color.BLACK}, 1.6f));
        DroppyBombRegistry.setCurrentbomb(BombType.EARTHQUAKEBOMB);
        bombTitles.add(new RenderableWord(myFont, DroppyBombRegistry.getCurrentBombText(), typeTextX,  startYPos  - (Constants.BUTTON_SIZE *7.2f), new Color[]{Color.DARK_GRAY}, 1.6f));
        bombTitles.add(new RenderableWord(myFont, " : "+DroppyBombRegistry.getCurrentBombCost(), costTextX, startYPos  - (Constants.BUTTON_SIZE *7.2f), new Color[]{Color.BLACK}, 1.6f));
        DroppyBombRegistry.setCurrentbomb(BombType.NUCLEAR);
        bombTitles.add(new RenderableWord(myFont, DroppyBombRegistry.getCurrentBombText(), typeTextX,  startYPos  - (Constants.BUTTON_SIZE *8.4f), new Color[]{Color.OLIVE}, 1.6f));
        bombTitles.add(new RenderableWord(myFont, " : "+DroppyBombRegistry.getCurrentBombCost(), costTextX, startYPos  - (Constants.BUTTON_SIZE *8.4f), new Color[]{Color.BLACK}, 1.6f));


        speedButton = new SpeedButton(new Vector2(Constants.WORLD_WIDTH*0.75f, Constants.WORLD_HEIGHT - (Constants.BUTTON_SIZE *1.2f)), new Vector2(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE));
        bombTitles.add(new RenderableWord(myFont, "Done!", Constants.WORLD_WIDTH*0.75f +(Constants.BUTTON_SIZE *1.4f) ,  Constants.WORLD_HEIGHT - (Constants.BUTTON_SIZE *0.75f), new Color[]{Color.BLACK}, 1.8f));
    }

    @Override
    public void show() {
        countdown = 10.0f;
        shapeRender = new ShapeRenderer();
        viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        myFont = new CircFont();


        init();

        Gdx.input.setInputProcessor(this);

    }

    private void init() {

        DroppyBombRegistry.clearElements();

        DroppyBombRegistry.reconcileElements();
        title = new RenderableWord(myFont, "Buy Bombs", 350.0f, Constants.WORLD_HEIGHT - 20, new Color[]{Color.BLACK, Color.BLUE}, 2.5f);
        title2 = new RenderableWord(myFont, "Click button to buy.", 260f, Constants.WORLD_HEIGHT - 60, new Color[]{Color.BLACK, Color.BLUE}, 2.0f);

        setUpBombButtons();

        initialised = true;

        isLeadIn = true;
        isLeadOut = false;

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
        shapeRender.rect(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, Color.FIREBRICK, Color.FIREBRICK, Color.CHARTREUSE, Color.CHARTREUSE);

        title.render(shapeRender, 1f, 2f, countdown, 1.0f);
        title2.render(shapeRender, 1f, 2f, countdown, 1.0f);

        topTextDisplay();

        for(DroppyBombEntity dpe : DroppyBombRegistry.getElementSet()) {
            dpe.render(delta, shapeRender);
        }

        for(BombButton b:bombButtons) {
            b.render(delta, shapeRender );
        }
        speedButton.render(delta, shapeRender);

        for(RenderableWord r:bombTitles) {
            r.render(shapeRender, 1f, 2f, countdown, 1.0f);
        }


        shapeRender.end();


    }

    private void update(float delta) {
        if (!initialised)
            return;

        for(BombButton b : bombButtons) {
            b.update(delta);
        }

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
                if (isLeadOut) {
                    isLeadOut = false;
                    initialised = false;
                    DroppyBombRegistry.getTheGame().setGameScreen();
                }
            }
        }

    }

    private void topTextDisplay() {

        RenderableWord title = new RenderableWord(myFont, "Score: "+DroppyBombRegistry.getScore(), Constants.WORLD_WIDTH*0.02f, Constants.WORLD_HEIGHT - (Constants.BUTTON_SIZE *0.6f), new Color[]{Color.BLACK}, 1.8f);

        title.render(shapeRender, 1f, 2f, countdown, 1.0f);

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(!initialised)
            return true;

        Vector2 worldClick = viewport.unproject(new Vector2(screenX, screenY));

        Gdx.app.log(TAG, "touchDown:" + worldClick.toString());

        BombButton clickedButton = null;
        for(BombButton b:bombButtons) {

            if(b.isHit(worldClick)) {
                b.setSelected();
                clickedButton = b;
                DroppyBombRegistry.setCurrentbomb(b.getBombType());
                DroppyBombRegistry.buyCurrentBomb();
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
            if (speedButton.isHit(worldClick)) {
                Gdx.app.log(TAG, "About to enter game:" );
                isLeadOut = true;
            }

        }

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
    public void dispose(){
    }
}
