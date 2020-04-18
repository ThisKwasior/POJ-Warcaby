package com.pjc.warcaby;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Pawn
{
    private Vector2 position; // position in the grid relative to world
    private Vector2 positionMatrix; // position in the 8x8 matrix
    private boolean side = false; // false = white; true = black
    private TextureRegion texRegionSpr; // 0 for regular, 1 for queen
    private Sprite spr;
    private boolean isDead = false;

    public Pawn(Vector2 mat, boolean pawnSide, TextureRegion texSpr)
    {
        positionMatrix = mat;
        position = Utils.calcPosInWorld(positionMatrix);
        side = pawnSide;
        texRegionSpr = texSpr;
        spr = new Sprite(texRegionSpr);
        spr.setPosition(position.x, position.y);
        if(side == true)
            spr.setScale(0.5f);
        spr.setOrigin(0.f, 0.f);
    }

    public void setTexRegion(TextureRegion tex) {texRegionSpr.setRegion(tex);}

    public void setPosMatrix(Vector2 posGrid)
    {
        positionMatrix = posGrid;
        position = Utils.calcPosInWorld(positionMatrix);
        spr.setPosition(position.x, position.y);
    }

    public Vector2 getPosMatrix() {return positionMatrix;}

    public Vector2 getPosWorld() {return position;}

    public boolean getSide() {return side;}

    public void setDeath(boolean status) {isDead = status;}

    public boolean isDead() {return isDead;}

    public void draw(SpriteBatch batch)
    {
        spr.draw(batch);
    }
}
