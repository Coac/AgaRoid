package com.agaroid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Cell {
	public Cell(SpriteBatch batch,
			ShapeRenderer shapeRenderer, BitmapFont font,
			String username, int posX, int posY, int mass) {
		super();
		this.batch = batch;
		this.font = font;
		this.shapeRenderer = shapeRenderer;
		this.username = username;
		this.posX = posX;
		this.posY = posY;
		this.mass = mass;
		
		this.glyphLayout = new GlyphLayout();
        this.glyphLayout.setText(font, username);
        
        this.color = new Color((float)Math.random(),(float) Math.random(),(float) Math.random(), 1);
	}

	SpriteBatch batch;
	BitmapFont font;
	ShapeRenderer shapeRenderer;
	
	GlyphLayout glyphLayout;
	
	private String username;
	int posX = 200;
	int posY = 200;
	int mass = 200;
	
	Color color;
	
	public void batchDraw() {
		font.draw(batch, username, posX - glyphLayout.width /2, posY + glyphLayout.height /2);
	}
	
	public void rendererDraw() {
		shapeRenderer.setColor(color);
		shapeRenderer.circle(posX, posY, mass);
	}
	
	public void translate(int offsetX, int offsetY) {
		posX += offsetX;
		posY += offsetY;
	}
	
	
}
