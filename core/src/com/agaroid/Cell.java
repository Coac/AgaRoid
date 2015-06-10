package com.agaroid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Cell {
	protected SpriteBatch batch;
	protected GlyphLayout glyphLayout;
	protected ShapeRenderer shapeRenderer;

	protected Color color;
	protected int hue = 300;
	protected double posX = 200;
	protected double posY = 200;
	protected int mass = 200;

	public Cell(SpriteBatch batch,
			ShapeRenderer shapeRenderer, double d, double e, int mass) {
		super();
		this.batch = batch;
		this.shapeRenderer = shapeRenderer;
		this.posX = d;
		this.posY = e;
		this.mass = mass;
        this.color = new Color((float)Math.random(),(float) Math.random(),(float) Math.random(), 1);
	}
	
	public void rendererDraw() {
		shapeRenderer.setColor(color);
		shapeRenderer.circle((float)posX, (float)posY, mass);
	}
	
	public void translate(int offsetX, int offsetY) {
		posX += offsetX;
		posY += offsetY;
	}
	
	public void setHue(int h) {
		this.hue = h;
		
	}
	public int getHue() {
		return hue;
	}
	
	public double getX() {
		return this.posX;
	}
	
	public double getY() {
		return this.posY;
	}
	
	public void setX(double x) {
		posX = x;
	}
	public void setY(double y) {
		posY = y;
	}
	
	public int getMass() {
		return mass;
	}
	
	public void setMass(int m) {
		this.mass = m;
	}
}
