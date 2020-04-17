package com.pjc.warcaby;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Utils
{
    final public static byte TILE_SIZE = 7;
    final public static byte BORDER_SIZE = 9;

    public static Vector2 calcPos(Vector2 matrix)
    {
        return new Vector2(Utils.BORDER_SIZE + matrix.x * Utils.TILE_SIZE, Utils.BORDER_SIZE + Utils.TILE_SIZE * matrix.y);
    }

    public static boolean doesMouseContain(Sprite spr, Vector2 mousePos)
    {
        if((spr.getX() < mousePos.x) == true && ((spr.getX() + spr.getWidth()) > mousePos.x) == true)
            if(spr.getY() < mousePos.y == true && ((spr.getY() + spr.getHeight()) > mousePos.y) == true)
                return true;

        return false;
    }

    public static boolean doesMouseContain(Vector2 tilePos, int tileSize, Vector2 mousePos)
    {
        if((tilePos.x < mousePos.x) == true && ((tilePos.x + tileSize) > mousePos.x) == true)
            if((tilePos.y < mousePos.y) == true && ((tilePos.y + tileSize) > mousePos.y) == true)
                return true;

        return false;
    }

    public static int isBoxValid(Vector2 tilePos)
    {
        return (int)(tilePos.x + tilePos.y + 1)%2;
    }
}
