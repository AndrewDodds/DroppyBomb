package com.saneandy.droppybomb;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.saneandy.droppybomb.game.DroppyBombRegistry;
import com.saneandy.droppybomb.screens.BuyingScreen;
import com.saneandy.droppybomb.screens.TitleScreen;
import com.saneandy.droppybomb.screens.GameScreen;

public class DroppyBombGame extends Game {

	private int currentMode = Constants.TITLE_SCREEN;

	public static final String TAG = DroppyBombGame.class.getName();

	private TitleScreen titleScreen;
	private GameScreen gameScreen;
	private BuyingScreen buyScreen;

	@Override
	public void create() {
		Gdx.app.log(TAG, "create() called");
		DroppyBombRegistry.init(this);
		titleScreen = new TitleScreen();
		gameScreen = new GameScreen();
		buyScreen = new BuyingScreen();
		setScreen(titleScreen);

	}


	@Override
	public void dispose() {
		Gdx.app.log(TAG, "dispose() called");
		super.dispose();
	}

	public void setTitleScreen() {
		if(currentMode == Constants.TITLE_SCREEN) {
			return;
		}

		currentMode = Constants.TITLE_SCREEN;
		setScreen(titleScreen);

	}

    public void setGameScreen() {
        if(currentMode == Constants.GAME_SCREEN) {
            return;
        }

        currentMode = Constants.GAME_SCREEN;
        setScreen(gameScreen);

    }

    public void setBuyScreen() {
        if(currentMode == Constants.BUY_SCREEN) {
            return;
        }

        currentMode = Constants.BUY_SCREEN;
        setScreen(buyScreen);

    }

}
