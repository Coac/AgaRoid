package com.agaroid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Cell {
	public Cell(SpriteBatch batch,
			ShapeRenderer shapeRenderer, int posX, int posY, int mass) {
		super();
		this.batch = batch;
		this.shapeRenderer = shapeRenderer;
		this.posX = posX;
		this.posY = posY;
		this.mass = mass;
        this.color = new Color((float)Math.random(),(float) Math.random(),(float) Math.random(), 1);
	}
	SpriteBatch batch;
    ShapeRenderer shapeRenderer;

    GlyphLayout glyphLayout;

	int posX = 200;
	int posY = 200;
	int mass = 200;
	
	Color color;

	
	public void rendererDraw() {
		shapeRenderer.setColor(color);
		shapeRenderer.circle(posX, posY, mass);
	}
	
	public void translate(int offsetX, int offsetY) {
		posX += offsetX;
		posY += offsetY;
	}


	
	
}
