package com.agaroid.cell;

import com.agaroid.Cell;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Etienne on 05/06/2015.
 */

public class CellElementary extends Cell {
    private static final int mass = 10 ;
    public CellElementary(SpriteBatch batch,
                      ShapeRenderer shapeRenderer, BitmapFont font, int posX, int posY){
        super(batch,shapeRenderer,posX,posY,mass);
    }
}
