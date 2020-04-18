package com.pjc.warcaby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Game
{
    int it = 0;
    boolean currentPlayer = false; // false for P1; true for P2

    Viewport view;
    Camera camera;
    Vector2 mousePos;

	Texture texAtlas;
	Sprite sprPlayground;
	Sprite sprBoxUnderCursor;
	Vector2[][] boxes;
	TextureRegion[] texRegions;
	Pawn[] pawns;
	Vector2 curCursorBoxMatrix;
	int curSelected = -1;
	int[] curPawnPaths = {0, 0, 0, 0};
	Vector2[] pawnMoveVecs;

    public void update()
    {
		checkMouseActions();
    }

    public Game(Viewport v, Camera cam, Vector2 mp)
    {
        view = v;
        camera = cam;
        mousePos = mp;

		texAtlas = new Texture("WarcabyAtlas.png");

		texRegions = new TextureRegion[7];
		texRegions[0] = new TextureRegion(texAtlas, 74, 74); 				// Playground
		texRegions[1] = new TextureRegion(texAtlas, 81, 0, Utils.TILE_SIZE, Utils.TILE_SIZE);	// Invalid box
		texRegions[2] = new TextureRegion(texAtlas, 74, 0, Utils.TILE_SIZE, Utils.TILE_SIZE);	// Valid box
		texRegions[3] = new TextureRegion(texAtlas, 88, 0, Utils.TILE_SIZE, Utils.TILE_SIZE);	// White Pawn
		texRegions[4] = new TextureRegion(texAtlas, 95, 0, Utils.TILE_SIZE*2, Utils.TILE_SIZE*2);	// Black Pawn
		texRegions[5] = new TextureRegion(texAtlas, 88, 7, Utils.TILE_SIZE, Utils.TILE_SIZE);	// White Queen
		texRegions[6] = new TextureRegion(texAtlas, 95, 14, Utils.TILE_SIZE, Utils.TILE_SIZE);	// Black Queen

		sprPlayground = new Sprite(texRegions[0]);
		sprBoxUnderCursor = new Sprite(texRegions[2]);

		// Setting up matrix for the cursor
		boxes = new Vector2[Utils.TILE_AMOUNT_WIDTH][Utils.TILE_AMOUNT_WIDTH];

		for(int y = 0; y != Utils.TILE_AMOUNT_WIDTH; ++y)
			for(int x = 0; x != Utils.TILE_AMOUNT_WIDTH; ++x)
				boxes[x][y] = Utils.calcPosInWorld(new Vector2(x, y));
		curCursorBoxMatrix = new Vector2(0,0);

		// Setting up pawns
		pawns = new Pawn[24];

		for(int y = 0; y != 3; ++y)
			for(int x = 0; x != 8; ++x)
				if(Utils.isBoxValid(new Vector2(x, y)) == 1)
				{
					pawns[it] = new Pawn(new Vector2(x, y), false, texRegions[3]);
					pawns[pawns.length-1-it] = new Pawn(new Vector2(7-x, 7-y),true, texRegions[4]);
					++it;
				}
        it = 0;

		pawnMoveVecs = new Vector2[4];
    }

    public void draw(SpriteBatch batch)
    {
		sprPlayground.draw(batch);

		for(int i = 0; i != pawns.length; ++i)
			if(!pawns[i].isDead())
				pawns[i].draw(batch);

		if(curSelected != -1) sprBoxUnderCursor.draw(batch);
    }

    private void checkMouseActions()
	{
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
		{
			curSelected = Utils.checkIfPawnUnderCursor(mousePos, pawns);
			if (!currentPlayer)
				if (curSelected >= 12) curSelected = -1;
			if(currentPlayer)
				if (curSelected < 12) curSelected = -1;

			if (curSelected != -1)
			{
				Vector2 pawnWrl = pawns[curSelected].getPosWorld();
				sprBoxUnderCursor.setPosition(pawnWrl.x, pawnWrl.y);
			}
		}

		if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) && curSelected != -1)
		{
			Vector2 moveBox = Utils.calcPosInMatrix(mousePos);
			moveBox.x = (float) Math.floor(moveBox.x);
			moveBox.y = (float) Math.floor(moveBox.y);

			if(Utils.isBoxValid(moveBox) == 1)
			{

				pawns[curSelected].setPosMatrix(moveBox);
				currentPlayer = !currentPlayer;
				curSelected = -1;
			}
		}
	}
}
