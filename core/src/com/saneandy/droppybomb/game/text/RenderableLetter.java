package com.saneandy.droppybomb.game.text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class RenderableLetter {
	float[][] lines;
	Color colour;
    Color brightCol;
    Color darkCol;


	public RenderableLetter(float[][] coords, Color col) {
        colour = col.cpy();
        brightCol = new Color(col.r + 0.2f, col.g + 0.2f, col.b + 0.2f, col.a);
        darkCol = new Color(col.r - 0.2f, col.g - 0.2f, col.b - 0.2f, col.a);
        lines = coords;
	}

	public void render(ShapeRenderer shapeRender, float posx, float posy, float scale, float linewidth) {
		if(lines == null)
			return;

		render(shapeRender, posx, posy, scale, linewidth, 1.0f, 1.0f);

	}

	public void render(ShapeRenderer shapeRender, float posx, float posy, float scale, float linewidth, float zoomlevel, float alpha) {
		if(lines == null)
			return;

        float shadowConst = linewidth*0.7f;

		for(int i=0; i<lines.length; i++) {
            float x1 = (lines[i][0]*scale) + posx;
            float y1 = (lines[i][1]*scale) + posy;
            float x2 = (lines[i][2]*scale) + posx;
            float y2 = (lines[i][3]*scale) + posy;

            float x1a = ((lines[i][0]+shadowConst)*scale) + posx;
            float y1a = ((lines[i][1]-shadowConst)*scale) + posy;
            float x2a = ((lines[i][2]+shadowConst)*scale) + posx;
            float y2a = ((lines[i][3]-shadowConst)*scale) + posy;

            if(zoomlevel != 1.0f) {
				float midx = (x1 + x2) / 2.0f;
				float midy = (y1 + y2) / 2.0f;
                x1 = ((x1-midx)*zoomlevel)+midx;
                x2 = ((x2-midx)*zoomlevel)+midx;
                y1 = ((y1-midy)*zoomlevel)+midy;
                y2 = ((y2-midy)*zoomlevel)+midy;
                x1a = ((x1a-midx)*zoomlevel)+midx;
                x2a = ((x2a-midx)*zoomlevel)+midx;
                y1a = ((y1a-midy)*zoomlevel)+midy;
                y2a = ((y2a-midy)*zoomlevel)+midy;
			}
			// rectline.. setcolour
			colour.a = alpha;
			shapeRender.setColor(darkCol);
            shapeRender.rectLine(x1a, y1a, x2a, y2a, scale*linewidth*zoomlevel);
            shapeRender.setColor(colour);
            shapeRender.rectLine(x1, y1, x2, y2, scale*linewidth*zoomlevel);
		}

		for(int i=0; i<lines.length; i++) {
			float x1 = (lines[i][0]*scale) + posx;
			float y1 = (lines[i][1]*scale) + posy;
			float x2 = (lines[i][2]*scale) + posx;
			float y2 = (lines[i][3]*scale) + posy;

            float x1a = ((lines[i][0]+shadowConst)*scale) + posx;
            float y1a = ((lines[i][1]-shadowConst)*scale) + posy;
            float x2a = ((lines[i][2]+shadowConst)*scale) + posx;
            float y2a = ((lines[i][3]-shadowConst)*scale) + posy;

            if(zoomlevel != 1.0f) {
				float midx = (x1 + x2) / 2.0f;
				float midy = (y1 + y2) / 2.0f;
				x1 = ((x1-midx)*zoomlevel)+midx;
				x2 = ((x2-midx)*zoomlevel)+midx;
				y1 = ((y1-midy)*zoomlevel)+midy;
				y2 = ((y2-midy)*zoomlevel)+midy;
                x1a = ((x1a-midx)*zoomlevel)+midx;
                x2a = ((x2a-midx)*zoomlevel)+midx;
                y1a = ((y1a-midy)*zoomlevel)+midy;
                y2a = ((y2a-midy)*zoomlevel)+midy;
			}
            shapeRender.setColor(colour);
            shapeRender.circle(x1a, y1a, scale*linewidth*zoomlevel);
            shapeRender.circle(x2a, y2a, scale*linewidth*zoomlevel);
            shapeRender.setColor(brightCol);
            shapeRender.circle(x1, y1, scale*linewidth*zoomlevel);
            shapeRender.circle(x2, y2, scale*linewidth*zoomlevel);
		}
	}
}
