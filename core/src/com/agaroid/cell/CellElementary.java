package com.agaroid.cell;

import com.agaroid.Cell;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class CellElementary extends Cell {
    private static final int mass = 5 ;

    
	public CellElementary(SpriteBatch batch, ShapeRenderer shapeRenderer,
			BitmapFont font, double posX, double posY) {
		super(batch,shapeRenderer,posX,posY,mass);
	}
}
