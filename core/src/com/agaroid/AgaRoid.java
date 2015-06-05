package com.agaroid;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


public class AgaRoid extends ApplicationAdapter {
	SpriteBatch batch;
	SpriteBatch cameraBatch;
	BitmapFont font;
	ShapeRenderer shapeRenderer;
	OrthographicCamera cam;


	
	CellPlayer cell;
	CellPlayer enemyCell;
	CellElementary cellElementary;

	Trap traptest ;

	private int minimumAccel = 2;
	 
	@Override
	public void create () {
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		cameraBatch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
             
        
        cell = new CellPlayer(batch, shapeRenderer, font, "Coac", 0,0, 100);
        enemyCell = new CellPlayer(batch, shapeRenderer, font, "erer", 200, 400, 30);
        
        cellElementary = new CellElementary(batch,shapeRenderer, font, 100,100);

        traptest = new Trap(batch,shapeRenderer,0,100);

        
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		handleInput();
		cam.update();
		
		batch.setProjectionMatrix(cam.combined);
		shapeRenderer.setProjectionMatrix(cam.combined);

		shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1, 1, 0, 1);
			cell.rendererDraw();
			enemyCell.rendererDraw();
			cellElementary.rendererDraw();
	        traptest.rendererDraw();
		shapeRenderer.end();
		
		cameraBatch.begin();
			font.draw(cameraBatch, cam.position.toString(),10, Gdx.graphics.getHeight()-10);
		cameraBatch.end();
		
		batch.begin();
        	cell.batchDraw();
        	enemyCell.batchDraw();
        batch.end();
		 
	}
	
	
	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            cam.zoom -= 0.02;
        }
        
		if (Gdx.input.getAccelerometerX() > minimumAccel || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.translate(-3, 0, 0);
            cell.translate(-3, 0);
        }
        if (Gdx.input.getAccelerometerX() < -minimumAccel || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.translate(3, 0, 0);
            cell.translate(3, 0);
        }
        if (Gdx.input.getAccelerometerY() > minimumAccel || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.translate(0, -3, 0);
            cell.translate(0, -3);
        }
        if (Gdx.input.getAccelerometerY() < -minimumAccel || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.translate(0, 3, 0);
            cell.translate(0, 3);
    
        }
        
	}
}
