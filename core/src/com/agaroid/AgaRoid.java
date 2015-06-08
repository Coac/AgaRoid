package com.agaroid;


import java.net.URISyntaxException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;




import com.agaroid.cell.CellElementary;
import com.agaroid.cell.CellPlayer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;


public class AgaRoid extends Game {
	boolean started = false;
	int deltaX;
	int deltaY;
	
	SpriteBatch batch;
	SpriteBatch cameraBatch;
	BitmapFont font;
	ShapeRenderer shapeRenderer;
	OrthographicCamera cam;


	
	CellPlayer cell;
	ArrayList<CellPlayer> enemyCells;
	CellElementary cellElementary;

	Trap traptest ;
	
	Socket socket;

	private int minimumAccel = 2;
	 
	@Override
	public void create () {
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		cameraBatch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
             
        
        cell = new CellPlayer(batch, shapeRenderer, font, "Coac", 100,100, 100);

        enemyCells = new ArrayList<CellPlayer>();
        enemyCells.add(new CellPlayer(batch, shapeRenderer, font, "erer", 200, 400, 30));
        
        cellElementary = new CellElementary(batch,shapeRenderer, font, 100,100);

        traptest = new Trap(batch,shapeRenderer,0,100);
        
        
		socketInit();

        
	}

	private void socketInit() {
		try {
			socket = IO.socket("http://localhost:3000");
		
	    	socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
	
	    	  @Override
	    	  public void call(Object... args) {
	
	    	  }
	
	    	});
	    	socket.on("welcome", new Emitter.Listener() {
	
	    	  @Override
	    	  public void call(Object... args) {
	    		  
	    		  System.out.println("welcome");
	    		  JSONObject obj = new JSONObject(args[0].toString());
	    	
	    		  cell.setHue(obj.getInt("hue"));
	    		  cell.setId(obj.getString("id"));

	    		  obj = new JSONObject();
	    		  obj.put("name", cell.getUsername());
	    		  obj.put("hue", cell.getHue());
	    		  obj.put("id", cell.getId());
	    		 
	    		  
	    		  socket.emit("gotit", obj);
	    		  started = true;
	    		 
	    	  }
	
	    	});

	    	socket.on("serverTellPlayerMove", new Emitter.Listener() {
	    		
	    	  @Override
	    	  public void call(Object... args) {
	    		  JSONObject obj = new JSONObject(args[0].toString());
	    		  

    			  System.out.println(obj);
	    		  cell.setX(obj.getDouble("x"));
	    		  cell.setY(obj.getDouble("y"));

	    		  cam.position.set((float)cell.getX(), (float)cell.getY(), 0);
	    		 // render();
	    		
	    		  
	    	  }
	
	    	});

	    	socket.on("serverUpdateAll", new Emitter.Listener() {
	    		
	    	  @Override
	    	  public void call(Object... args) {
	    	  		enemyCells.clear();
	    		  JSONArray  objs = new JSONArray(args[0].toString());
	    		  for (int i =0 ; i<objs.length() ; i++ ) {
	    		  	JSONObject  obj = objs.getJSONObject(i);
	    		  	enemyCells.add(new CellPlayer(batch, shapeRenderer, font, obj.getString("name"), obj.getDouble("x"), obj.getDouble("y"), obj.getInt("mass")));
	    		  }

	    	  }
	
	    	});
	    	
	    	socket.on("playerJoin", new Emitter.Listener() {
		    	  @Override
		    	  public void call(Object... args) {
		    		  enemyCells.clear();
		    		  System.out.println(args[0].toString());
		    		  JSONObject  o = new JSONObject(args[0].toString());
		    		  JSONArray objs = o.getJSONArray("playersList");
		    		  for (int i =0 ; i<objs.length() ; i++ ) {
		    		  	JSONObject  obj = objs.getJSONObject(i);
		    		  	enemyCells.add(new CellPlayer(batch, shapeRenderer, font, obj.getString("name"), obj.getDouble("x"), obj.getDouble("y"), obj.getInt("mass")));
		    		  }
		    	  }
		
	    	});
	    	socket.on("playerDied", new Emitter.Listener() {
		    	  @Override
		    	  public void call(Object... args) {
		    		  enemyCells.clear();
		    		  System.out.println(args[0].toString());
		    		  JSONObject  o = new JSONObject(args[0].toString());
		    		  JSONArray objs = o.getJSONArray("playersList");
		    		  for (int i =0 ; i<objs.length() ; i++ ) {
		    		  	JSONObject  obj = objs.getJSONObject(i);
		    		  	enemyCells.add(new CellPlayer(batch, shapeRenderer, font, obj.getString("name"), obj.getDouble("x"), obj.getDouble("y"), obj.getInt("mass")));
		    		  }
		    	  }
		
	    	});
	    	socket.on("playerDisconnect", new Emitter.Listener() {
		    	  @Override
		    	  public void call(Object... args) {
		    		  enemyCells.clear();
		    		  System.out.println(args[0].toString());
		    		  JSONObject  o = new JSONObject(args[0].toString());
		    		  JSONArray objs = o.getJSONArray("playersList");
		    		  for (int i =0 ; i<objs.length() ; i++ ) {
		    		  	JSONObject  obj = objs.getJSONObject(i);
		    		  	enemyCells.add(new CellPlayer(batch, shapeRenderer, font, obj.getString("name"), obj.getDouble("x"), obj.getDouble("y"), obj.getInt("mass")));
		    		  }
		    	  }
		
	    	});
	    	
	    	socket.on("connect_failed", new Emitter.Listener() {
	    		
	    	  @Override
	    	  public void call(Object... args) {
	    		  System.out.println("connect_failed");
	    	  }
	
	    	});
	    	
	    	socket.on("RIP", new Emitter.Listener() {
	    		
		    	  @Override
		    	  public void call(Object... args) {
		    		  started = false;
		    	  }
		
	    	});
	    	
	    	socket.on("playerDisconnect", new Emitter.Listener() {
	    		
		    	  @Override
		    	  public void call(Object... args) {
		    		  System.out.println("playerDisconnect");
		    	  }
		
	    	});
	    	
	    	socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
	
	    	  @Override
	    	  public void call(Object... args) {}
	
	    	});
	    	
	    	socket.connect();
    	
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			for(CellPlayer enemyCell : enemyCells)
				enemyCell.rendererDraw();
			cellElementary.rendererDraw();
	        traptest.rendererDraw();
		shapeRenderer.end();
		
		cameraBatch.begin();
			font.draw(cameraBatch, cam.position.toString(), 10, Gdx.graphics.getHeight() - 10);
		cameraBatch.end();
		
		batch.begin();
        	cell.batchDraw();
        	for(CellPlayer enemyCell : enemyCells)
				enemyCell.batchDraw();
        batch.end();


        if(started) {
	        JSONObject target = new JSONObject();
	        target.put("x", cell.getX() + deltaX);
	        target.put("y", cell.getY() + deltaY );
	        socket.emit("0", target);
        }
	}
	
	
	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            cam.zoom -= 0.02;
        }
        deltaX = 0;
        deltaY = 0;
		if (Gdx.input.getAccelerometerX() > minimumAccel || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
           // cam.translate(-3, 0, 0);
            //cell.translate(-3, 0);
            deltaX = -30;
        }
        if (Gdx.input.getAccelerometerX() < -minimumAccel || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
           // cam.translate(3, 0, 0);
           // cell.translate(3, 0);
            deltaX = 30;
        }
        if (Gdx.input.getAccelerometerY() > minimumAccel || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
           // cam.translate(0, -3, 0);
            //cell.translate(0, -3);
            deltaY= -30;
        }
        if (Gdx.input.getAccelerometerY() < -minimumAccel || Gdx.input.isKeyPressed(Input.Keys.UP)) {
           // cam.translate(0, 3, 0);
           //.translate(0, 3);
            deltaY = 30;
    
        }
        
	}
}
