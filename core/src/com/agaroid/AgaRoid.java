package com.agaroid;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class AgaRoid extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont font;
	ShapeRenderer shapeRenderer;
	GlyphLayout glyphLayout;
	
	String username = "usernameusernameusernameusername";
	int posX = 200;
	int posY = 200;
	int mass = 200;
	
	
	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();    
        font = new BitmapFont();
        font.setColor(Color.RED);
        
        glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, username);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		

		shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1, 1, 0, 1);
			shapeRenderer.circle(posX, posY, mass);
		shapeRenderer.end();
		
		
		batch.begin();
        	font.draw(batch, username, posX - glyphLayout.width /2, posY + glyphLayout.height /2);
        batch.end();
		 
	}
}
