package com.saneandy.droppybomb.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.saneandy.droppybomb.game.DroppyBombRegistry;
import com.saneandy.droppybomb.game.bombs.Bomb;
import com.saneandy.droppybomb.game.bombs.ClusterBomb;
import com.saneandy.droppybomb.game.bombs.DaisyCutterBomb;
import com.saneandy.droppybomb.game.bombs.EarthquakeBomb;
import com.saneandy.droppybomb.game.bombs.HomingBomb;
import com.saneandy.droppybomb.game.bombs.MissileBomb;
import com.saneandy.droppybomb.game.bombs.NuclearBomb;
import com.saneandy.droppybomb.game.bombs.NormalBomb;
import com.saneandy.droppybomb.game.bombs.HighExplosiveBomb;
import com.saneandy.droppybomb.game.text.CircFont;
import com.saneandy.droppybomb.game.text.RenderableWord;

/**
 * Created by Andrew on 26/10/2016.
 */

public class BombButton {
    private Vector2 pos;
    private Vector2 size;
    private Bomb item;

    private BombType bType;
    private boolean isSelected = false;
    private CircFont font = null;
   // private RenderableWord titletext;

    public BombButton(Vector2 position, Vector2 buttonsize, BombType bombType ) {
        pos = position.cpy();
        size = buttonsize.cpy();
        bType = bombType;
        font = new CircFont();

        float xPosOffset = 0.27f;
        float yPosOffset = 0.1f;

        switch(bType) {
            case NORMAL:
                item = new NormalBomb(new Vector2(position.x + (xPosOffset*size.x), position.y + (yPosOffset*size.y)) );

                break;
            case HIGHEXPLOSIVE:
                item = new HighExplosiveBomb(new Vector2(position.x + (xPosOffset*size.x), position.y + (yPosOffset*size.y)) );

                break;
            case DAISYCUTTER:
                item = new DaisyCutterBomb(new Vector2(position.x + (xPosOffset*size.x), position.y + (yPosOffset*size.y)) );

                break;
            case CLUSTERBOMB:
                item = new ClusterBomb(new Vector2(position.x + (xPosOffset*size.x), position.y + (yPosOffset*size.y)) ,0f);

                break;
            case MISSILE:
                item = new MissileBomb(new Vector2(position.x + (xPosOffset*size.x), position.y + (yPosOffset*size.y)));

                break;
            case HOMINGBOMB:
                item = new HomingBomb(new Vector2(position.x + (xPosOffset*size.x), position.y + (yPosOffset*size.y)));

                break;
            case EARTHQUAKEBOMB:
                item = new EarthquakeBomb(new Vector2(position.x + (xPosOffset*size.x), position.y + (yPosOffset*size.y)));

                break;
            case NUCLEAR:
                item = new NuclearBomb(new Vector2(position.x + (xPosOffset*size.x), position.y + (yPosOffset*size.y)));

                break;
        }

        item.isStatic = true;
    }


    public void render(float delta, ShapeRenderer renderer) {
        String nl = new Integer(DroppyBombRegistry.bombsLeft(bType)).toString();
        RenderableWord titletext = null;
        renderer.rect(pos.x, pos.y, size.x, size.y, Color.BLACK, Color.DARK_GRAY, Color.DARK_GRAY, Color.LIGHT_GRAY);
        float scale = 1.0f;
        if(isSelected) {
            scale = 1.2f;

            titletext = new RenderableWord(font, nl, pos.x + 9f, pos.y + 40f, new Color[]{Color.BLACK}, 1.2f);

            renderer.rect(pos.x + 0.2f, pos.y + 0.2f, size.x - 0.4f, size.y - 0.4f, Color.GREEN, Color.GREEN, Color.CORAL, Color.CORAL);
        }
        else {
            titletext = new RenderableWord(font, nl, pos.x + 9f, pos.y + 40f, new Color[]{Color.CYAN}, 1.2f);
            renderer.rect(pos.x + 0.2f, pos.y + 0.2f, size.x - 0.4f, size.y - 0.4f, Color.DARK_GRAY, Color.DARK_GRAY, Color.GRAY, Color.GRAY);
        }

        item.render(delta, renderer);

        titletext.render(renderer, scale, 1.3f);
    }

    public boolean isHit(Vector2 point) {
        if(point.x >= pos.x && point.x <= (pos.x+size.x) ) {
            if(point.y >= pos.y && point.y <= (pos.y+size.y) ) {
                DroppyBombRegistry.getDropBombSound().play(1.0f);

                return true;
            }
        }
        return false;
    }

    public void setSelected() {
        isSelected = true;
    }
    public void setUnSelected() {
        isSelected = false;
    }

    public BombType getBombType() {
        return bType;
    }

    public void update(float delta) {
        item.update(delta);
    }

}
