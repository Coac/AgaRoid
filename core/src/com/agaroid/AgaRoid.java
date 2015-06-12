package com.agaroid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class AgaRoid extends Game {
	boolean started = false;
	int deltaX;
	int deltaY;

	SpriteBatch batch;
	SpriteBatch cameraBatch;
	BitmapFont font;
	ShapeRenderer shapeRenderer;
	OrthographicCamera cam;

	@Override
	public void create() {

		cam = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		cameraBatch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.BLACK);

		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}
