package com.pjc.warcaby;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Utils
{
    final public static byte TILE_AMOUNT_WIDTH = 8;
    final public static byte TILE_SIZE = 7;
    final public static byte PLAYGROUND_SIZE = TILE_AMOUNT_WIDTH * TILE_SIZE;
    final public static byte BORDER_SIZE = 9;

    public static Vector2 calcPosInWorld(Vector2 matrix)
    {
        return new Vector2(Utils.BORDER_SIZE + matrix.x * Utils.TILE_SIZE, Utils.BORDER_SIZE + Utils.TILE_SIZE * matrix.y);
    }

    public static Vector2 calcPosInMatrix(Vector2 world)
    {
        Vector2 ret = new Vector2(0, 0);

        ret.x = ((world.x - BORDER_SIZE)/PLAYGROUND_SIZE)*TILE_AMOUNT_WIDTH;
        ret.y = TILE_AMOUNT_WIDTH - (((world.y - BORDER_SIZE)/PLAYGROUND_SIZE)*TILE_AMOUNT_WIDTH);

        return ret;
    }

    public static boolean doesMouseContain(Sprite spr, Vector2 mousePos)
    {
        if((spr.getX() < mousePos.x) && ((spr.getX() + spr.getWidth()) > mousePos.x))
            return spr.getY() < mousePos.y && ((spr.getY() + spr.getHeight()) > mousePos.y);

        return false;
    }

    public static boolean doesMouseContain(Vector2 tilePos, int tileSize, Vector2 mousePos)
    {
        if((tilePos.x < mousePos.x) && ((tilePos.x + tileSize) > mousePos.x))
            return (tilePos.y < mousePos.y) && ((tilePos.y + tileSize) > mousePos.y);

        return false;
    }

    public static int isBoxValid(Vector2 tilePos)
    {
        return (int)(tilePos.x + tilePos.y + 1)%2;
    }

    public static int[] checkAvailablePaths(int pawnID, Pawn[] pawns)
    {
        // LEFT-UP / RIGHT-UP / LEFT-DOWN / RIGHT-DOWN
        int[] directions = {0, 0, 0, 0};

        Vector2 pawnPos = pawns[pawnID].getPosMatrix();
        boolean side = pawns[pawnID].getSide();

        // Checking if we can even go further. If not = -1
        if(pawnPos.x == 0 || pawnPos.y == 7) directions[0] = -1;
        if(pawnPos.x == 7 || pawnPos.y == 7) directions[1] = -1;
        if(pawnPos.x == 0 || pawnPos.y == 0) directions[2] = -1;
        if(pawnPos.x == 7 || pawnPos.y == 0) directions[3] = -1;

        // If any direction is obscured by a teammate or enemy.
        // 1 for teammate
        // 2 for enemy

        for(int i = 0; i != pawns.length; ++i)
        {
            Vector2 curPawn = pawns[i].getPosMatrix();
            boolean curSide = pawns[i].getSide();

            int pawnStat = 1;
            if(curSide != side) pawnStat = 2;

            if(curPawn.x == pawnPos.x-1 && curPawn.y == pawnPos.y+1)
                directions[0] = pawnStat;
            else if(curPawn.x == pawnPos.x+1 && curPawn.y == pawnPos.y+1)
                directions[1] = pawnStat;
            else if(curPawn.x == pawnPos.x-1 && curPawn.y == pawnPos.y-1)
                directions[2] = pawnStat;
            else if(curPawn.x == pawnPos.x+1 && curPawn.y == pawnPos.y-1)
                directions[3] = pawnStat;
        }

        // Check if you can beat the enemy
        // 3 if so

        for(int i = 0; i != 4; ++i)
        {
            if(directions[i] == 2)
                for(int j = 0; j != pawns.length; ++j)
                {
                    Vector2 curPawnPos = pawns[j].getPosMatrix();
                    int x = 0, y = 0;

                    if(i == 0) {x = -1;y = 1;}
                    if(i == 1) {x = 1;y = 1;}
                    if(i == 2) {x = -1;y = -1;}
                    if(i == 3) {x = 1;y = -1;}

                    if(curPawnPos.x == pawnPos.x+x && curPawnPos.y == pawnPos.y+y)
                            directions[i] = 3;
                }
        }

        return directions;
    }

    static int checkIfPawnUnderCursor(Vector2 mousePos, Pawn[] pawns)
    {
        int pawn = -1;
        Vector2 mouseMatrix = calcPosInMatrix(mousePos);
        mouseMatrix.x = (float) Math.floor(mouseMatrix.x);
        mouseMatrix.y = (float) Math.floor(mouseMatrix.y);

        for(int i = 0; i != pawns.length; ++i)
        {
            Vector2 pawnPos = pawns[i].getPosMatrix();

            if(!pawns[i].isDead())
                if(pawnPos.x == mouseMatrix.x && pawnPos.y == mouseMatrix.y)
                {
                    pawn = i;
                    break;
                }
        }

        return pawn;
    }

    // 0 - move successful
    // -1 - move unsuccessful
   /* static int processMove(Vector2 moveVec, Pawn[] pawns, int pawnID, Vector2)
    {

    }*/

    static float dotProduct(Vector2 a, Vector2 b)
    {
        return a.x * b.x + a.y * b.y;
    }
}
