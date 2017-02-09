package com.saneandy.droppybomb.game.text;

import com.badlogic.gdx.Gdx;

public class CircFont {

    public static final String TAG = CircFont.class.getName();

    private float [][][] vert;
	private int [][][] lines;
	private int NUM_CHARS = 39;
	
	private void init() {
		
		vert = new float[NUM_CHARS][][];
		lines = new int[NUM_CHARS][][];
	  
		vert[0] = new float[][]{{0f,0f},{2f,5f},{4f,10f},{6f,5f},{8f,0f}};  //A
		lines[0] =new int[][]{{0,2},{2,4},{1,3}};
		vert[1] = new float[][]{{0f,0f},{0f,10f},{8f,7.5f},{0f,5f},{8f,2.5f}};//B
		vert[1] = new float[][]{{0f,0f},{0f,10f},{6f,10f},{8f,8.5f},{8f,6.5f},{6f,5f},{0f,5f},{8f,3.5f},{8f,1.5f},{6f,0f}};//B
		lines[1] =new int[][]{{0,1},{1,2},{2,3},{3,4},{4,5},{5,6},{5,7},{7,8},{8,9},{9,0}};
		vert[2] = new float[][]{{8f,0f},{0f,0f},{0f,10f},{8f,10f}};//C
		lines[2] =new int[][]{{0,1},{1,2},{2,3}};
		vert[3] = new float[][]{{8f,1f},{5f,0f},{0f,0f},{0f,10f},{5f,10f},{8f,9f}};//D
		lines[3] =new int[][]{{0,1},{1,2},{2,3},{3,4},{4,5},{5,0}};
		vert[4] = new float[][]{{8f,0f},{0f,0f},{0f,10f},{8f,10f},{4f,5f},{0f,5f}};//E
		lines[4] =new int[][]{{0,1},{1,2},{2,3},{4,5}};
		vert[5] = new float[][]{{0f,0f},{0f,10f},{8f,10f},{4f,5f},{0f,5f}};//F
		lines[5] =new int[][]{{0,1},{1,2},{3,4}};
		vert[6] = new float[][]{{6f,0f},{0f,0f},{0f,10f},{7f,10f},{6f,3f},{4f,3f},{8f,3f}};//G
		lines[6] =new int[][]{{0,1},{1,2},{2,3},{0,4},{5,6}};
		vert[7] = new float[][]{{8f,0f},{0f,0f},{0f,10f},{8f,10f},{0f,5f},{8f,5f}};//H
		lines[7] =new int[][]{{0,3},{2,1},{4,5}};
		vert[8] = new float[][]{{3f,0f},{4f,0f},{5f,0f},{3f,10f},{4f,10f},{5f,10f}};//I
		lines[8] =new int[][]{{0,2},{1,4},{3,5}};
		vert[9] = new float[][]{{3f,0f},{4f,0f},{5f,1f},{3f,10f},{5f,10f}};//J
		lines[9] =new int[][]{{0,1},{1,2},{2,4},{3,4}};
		vert[10] = new float[][]{{0f,0f},{0f,10f},{0f,5f},{8f,10f},{8f,0f}};//K
		lines[10] =new int[][]{{0,1},{2,3},{2,4}};
		vert[11] = new float[][]{{0f,0f},{8f,0f},{0f,10f}};//L
		lines[11] =new int[][]{{0,1},{0,2}};
		vert[12] = new float[][]{{0f,0f},{8f,0f},{0f,10f},{8f,10f},{4f,7f}};//M
		lines[12] =new int[][]{{0,2},{2,4},{3,4},{1,3}};
		vert[13] = new float[][]{{0f,0f},{8f,0f},{0f,10f},{8f,10f}};//N
		lines[13] =new int[][]{{0,2},{2,1},{1,3}};
		vert[14] = new float[][]{{8f,0f},{0f,0f},{0f,10f},{8f,10f}};//O
		lines[14] =new int[][]{{0,1},{1,2},{2,3},{0,3}};
		vert[15] = new float[][]{{8f,6f},{0f,0f},{0f,10f},{8f,10f},{0f,6f}};//P
		lines[15] =new int[][]{{0,3},{1,2},{2,3},{4,0}};
		vert[16] = new float[][]{{8f,0f},{0f,0f},{0f,10f},{8f,10f},{6f,2f}};//Q
		lines[16] =new int[][]{{0,1},{1,2},{2,3},{0,3},{0,4}};
		vert[17] = new float[][]{{8f,6f},{0f,0f},{0f,10f},{8f,10f},{0f,6f},{8f,0f}};//R
		lines[17] =new int[][]{{0,3},{1,2},{2,3},{4,0},{4,5}};
		vert[18] = new float[][]{{8f,6f},{0f,0f},{0f,10f},{8f,10f},{0f,6f},{8f,0f}};//S
		lines[18] =new int[][]{{1,5},{5,0},{0,4},{4,2},{2,3}};
		vert[19] = new float[][]{{4f,0f},{0f,10f},{4f,10f},{8f,10f}};//T
		lines[19] =new int[][]{{0,2},{1,3}};
		vert[20] = new float[][]{{8f,0f},{0f,0f},{0f,10f},{8f,10f}};//U
		lines[20] =new int[][]{{0,1},{2,1},{0,3}};
		vert[21] = new float[][]{{4f,0f},{0f,10f},{8f,10f}};//V
		lines[21] =new int[][]{{0,1},{0,2}};
		vert[22] = new float[][]{{0f,0f},{8f,0f},{0f,10f},{8f,10f},{4f,7f}};//W
		lines[22] =new int[][]{{0,2},{0,4},{1,4},{1,3}};
		vert[23] = new float[][]{{8f,0f},{0f,0f},{0f,10f},{8f,10f}};//X
		lines[23] =new int[][]{{0,2},{1,3}};
		vert[24] = new float[][]{{4f,0f},{4f,6f},{0f,10f},{8f,10f}};//Y
		lines[24] =new int[][]{{0,1},{1,2},{1,3}};
		vert[25] = new float[][]{{8f,0f},{0f,0f},{0f,10f},{8f,10f}};//Z
		lines[25] =new int[][]{{0,1},{1,3},{2,3}};

		vert[26] = new float[][]{{4f,0f},{4f,10f},{3f,9f}};//1
		lines[26] =new int[][]{{0,1},{1,2}};
		vert[27] = new float[][]{{0f,8f},{4f,10f},{8f,8f},{0f,0f},{8f,0f}};//2
		lines[27] =new int[][]{{0,1},{1,2},{2,3},{4,3}};
		vert[28] = new float[][]{{8f,0f},{0f,0f},{0f,10f},{8f,10f},{4f,5f},{8f,5f}};//3
		lines[28] =new int[][]{{0,1},{2,3},{0,3},{4,5}};
		vert[29] = new float[][]{{6f,0f},{6f,10f},{0f,4f},{8f,4f}};//4
		lines[29] =new int[][]{{0,1},{1,2},{2,3}};
		vert[30] = new float[][]{{0f,0f},{6f,0f},{8f,1f},{8f,5f},{6f,6f},{0f,6f},{0f,10f},{8f,10f}};//5
		lines[30] =new int[][]{{0,1},{1,2},{2,3},{3,4},{4,5},{5,6},{6,7}};
		vert[31] = new float[][]{{8f,10f},{0f,10f},{0f,0f},{8f,0f},{8f,5f},{0f,5f}};//6
		lines[31] =new int[][]{{0,1},{1,2},{2,3},{3,4},{4,5}};
		vert[32] = new float[][]{{8f,10f},{0f,10f},{3f,0f}};//7
		lines[32] =new int[][]{{0,1},{0,2}};
		vert[33] = new float[][]{{8f,10f},{0f,10f},{0f,0f},{8f,0f},{8f,5f},{0f,5f}};//8
		lines[33] =new int[][]{{0,1},{1,2},{2,3},{3,0},{4,5}};
		vert[34] = new float[][]{{8f,10f},{0f,10f},{8f,0f},{8f,5f},{0f,5f}};//9
		lines[34] =new int[][]{{0,1},{1,4},{4,3},{0,2}};
		vert[35] = new float[][]{{8f,10f},{0f,10f},{0f,0f},{8f,0f}};//0
		lines[35] =new int[][]{{0,1},{1,2},{2,3},{3,0},{2,0}};

		vert[36] = new float[][]{{4f,2f},{4f,3f},{4f,7f},{4f,8f}};//:
		lines[36] =new int[][]{{0,1},{2,3}};
		vert[37] = new float[][]{{4f,0f},{4f,1f},{4f,2f},{4f,10f}};//!
		lines[37] =new int[][]{{0,1},{2,3}};
		vert[38] = new float[][]{{2f,5f},{6f,5f}};//-
		lines[38] =new int[][]{{0,1}};
	  
	}
	
	private float[][] getLinesForIndex(int idx, float scale) { // List of [x1, y1, x2, y2] to define strokes for letters.
		float[][] retVal = new float[lines[idx].length][4];
		for(int i=0; i<lines[idx].length; i++) { // Subtraction to center the letters
            retVal[i][0] = (vert[idx][lines[idx][i][0]][0]-4.0f)*scale;
			retVal[i][1] = (vert[idx][lines[idx][i][0]][1]-5.0f)*scale;
			retVal[i][2] = (vert[idx][lines[idx][i][1]][0]-4.0f)*scale;
			retVal[i][3] = (vert[idx][lines[idx][i][1]][1]-5.0f)*scale;
		}
		return retVal;
	}
	
	private static int indexForChar(char c) {
		switch (c) {
		case 'a':
		case 'A':
			return 0;
		case 'b':
		case 'B':
			return 1;
		case 'c':
		case 'C':
			return 2;
		case 'd':
		case 'D':
			return 3;
		case 'e':
		case 'E':
			return 4;
		case 'f':
		case 'F':
			return 5;
		case 'g':
		case 'G':
			return 6;
		case 'h':
		case 'H':
			return 7;
		case 'i':
		case 'I':
			return 8;
		case 'j':
		case 'J':
			return 9;
		case 'k':
		case 'K':
			return 10;
		case 'l':
		case 'L':
			return 11;
		case 'm':
		case 'M':
			return 12;
		case 'n':
		case 'N':
			return 13;
		case 'o':
		case 'O':
			return 14;
		case 'p':
		case 'P':
			return 15;
		case 'q':
		case 'Q':
			return 16;
		case 'r':
		case 'R':
			return 17;
		case 's':
		case 'S':
			return 18;
		case 't':
		case 'T':
			return 19;
		case 'u':
		case 'U':
			return 20;
		case 'v':
		case 'V':
			return 21;
		case 'w':
		case 'W':
			return 22;
		case 'x':
		case 'X':
			return 23;
		case 'y':
		case 'Y':
			return 24;
		case 'z':
		case 'Z':
			return 25;

		case '1':
			return 26;
		case '2':
			return 27;
		case '3':
			return 28;
		case '4':
			return 29;
		case '5':
			return 30;
		case '6':
			return 31;
		case '7':
			return 32;
		case '8':
			return 33;
		case '9':
			return 34;
		case '0':
			return 35;

		case ':':
			return 36;
		case '!':
			return 37;
		case '-':
			return 38;
		}
		return -1;
	}
	
	public float[][] getStrokesForLetter(char c, float scale) {
		int idx = indexForChar(c);
        //Gdx.app.log(TAG, "c:"+c );
		if(idx > -1){
			return getLinesForIndex(idx, scale);
		}
		return null;
	}
	
	public CircFont() {
		init();
	}
	

}
