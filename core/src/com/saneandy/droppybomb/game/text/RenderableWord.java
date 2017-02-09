package com.saneandy.droppybomb.game.text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;


public class RenderableWord {
	
	private ArrayList<RenderableLetter> letters;
	private float yPosition;
	private ArrayList<Float> xPositions;

	private float zoomLevel;
	
	public RenderableWord(CircFont font, String s, float xPos, float yPos, Color[] cols, float scale) {
		char[] characters = s.toCharArray();
		
		int colIdx = 0;
		
		yPosition = yPos;
		float xPosTemp = xPos;
		letters = new ArrayList<RenderableLetter>();
		xPositions = new ArrayList<Float>();
		
		for(int i=0; i< characters.length; i++) {
			float[][] linesforletter = font.getStrokesForLetter(characters[i], scale);
			if (linesforletter != null) {
				letters.add(new RenderableLetter(linesforletter, cols[colIdx++]));
				xPositions.add(xPosTemp);
				if(colIdx >= cols.length)
					colIdx = 0;
			} 			
			xPosTemp += 13.0f*scale;
		}
	}

    public void render(ShapeRenderer shapeRender, float scale, float linewidth) {
        for(int i=0; i< letters.size(); i++) {
            RenderableLetter letter = letters.get(i);
            letter.render(shapeRender, xPositions.get(i), yPosition, scale, linewidth);
        }
    }

    public void render(ShapeRenderer shapeRender, float scale, float linewidth, float zoomlevel, float alpha) {
        for(int i=0; i< letters.size(); i++) {
            RenderableLetter letter = letters.get(i);
            letter.render(shapeRender, xPositions.get(i), yPosition, scale, linewidth, zoomlevel, alpha);
        }
    }

}
