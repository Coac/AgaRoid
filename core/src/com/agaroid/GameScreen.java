package com.agaroid;

import java.net.URISyntaxException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.agaroid.cell.CellElementary;
import com.agaroid.cell.CellPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

public class GameScreen implements Screen {
	
	boolean started = false;
	int deltaX;
	int deltaY;
	
	OrthographicCamera cam;


	
	CellPlayer cell;
	ArrayList<CellPlayer> enemyCells;
	ArrayList<CellElementary> foods;
	
	Socket socket;

	private int minimumAccel = 2;
	
	
	private AgaRoid game;
	
	public GameScreen(AgaRoid game){
	     this.game = game;  
	     cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	     game.shapeRenderer = new ShapeRenderer();

        game.font = new BitmapFont();
        game.font.setColor(Color.BLACK);
             
        
        cell = new CellPlayer(game.batch, game.shapeRenderer, game.font, "Coac", 100,100, 100, 5);

        enemyCells = new ArrayList<CellPlayer>();
        
        
        
        foods = new ArrayList<CellElementary>();
        
        
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
	    		  
	    		  JSONObject obj = new JSONObject(args[0].toString());
	    	
	    		  cell.setHue(obj.getInt("hue"));
	    		  cell.setId(obj.getString("id"));
	    		  cell.setX(obj.getDouble("x"));
	    		  cell.setY(obj.getDouble("y"));
	    		  cell.setMass(obj.getInt("mass"));

	    		  
	    		  obj.put("name", cell.getUsername());
	    		  obj.put("screenWidth",  Gdx.graphics.getWidth());
	    		  obj.put("screenHeight", Gdx.graphics.getHeight());
	
	    		  socket.emit("gotit", obj);
	    		  started = true;
	    		  cam.position.set((float)cell.getX(), (float)cell.getY(), 0);
	    		 
	    	  }
	
	    	});

	    	socket.on("serverTellPlayerMove", new Emitter.Listener() {
	    		
	    	  @Override
	    	  public void call(Object... args) {
	    		  //Player
	    		  JSONObject obj = new JSONObject(args[0].toString());
	    		  cell.setX(obj.getDouble("x"));
	    		  cell.setY(obj.getDouble("y"));
	    		  cam.position.set((float)cell.getX(), (float)cell.getY(), 0);
	    		
	    		  //PlayerList
	    		  synchronized(enemyCells) {
		    		  enemyCells.clear();
		    		  JSONArray  usersList = new JSONArray(args[1].toString());
		    		  for (int i =0 ; i<usersList.length() ; i++ ) {
		    			  JSONObject  user = usersList.getJSONObject(i);
		    			  enemyCells.add(new CellPlayer(game.batch, game.shapeRenderer, game.font, user.getString("name"), user.getDouble("x"), user.getDouble("y"), user.getInt("mass"), user.getInt("hue")));
		    		  }
	    		  }
	    			  
	    		  //Foods
	    		  synchronized(foods) {
		    		  foods.clear();
		    		  JSONArray  foodList = new JSONArray(args[2].toString());
		    		  for (int i =0 ; i<foodList.length() ; i++ ) {
		    			  JSONObject  food = foodList.getJSONObject(i);
		    			  foods.add(new CellElementary(game.batch, game.shapeRenderer, game.font, food.getDouble("x"), food.getDouble("y"), 0.5f));
		    		  }	
	    		  }
	    		  
	    	  }
	
	    	});

	    		

	    	
	    	socket.on("playerJoin", new Emitter.Listener() {
		    	  @Override
		    	  public void call(Object... args) {
		    		  synchronized(enemyCells) {
			    		  enemyCells.clear();
			    		  System.out.println(args[0].toString());
			    		  JSONArray  usersList = (new JSONObject(args[0].toString())).getJSONArray("playersList");
			    		  for (int i =0 ; i<usersList.length() ; i++ ) {
			    			  JSONObject  user = usersList.getJSONObject(i);
			    			  enemyCells.add(new CellPlayer(game.batch, game.shapeRenderer, game.font, user.getString("name"), user.getDouble("x"), user.getDouble("y"), user.getInt("mass"), user.getInt("hue")));
			    		  }
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
		    		  	enemyCells.add(new CellPlayer(game.batch, game.shapeRenderer, game.font, obj.getString("name"), obj.getDouble("x"), obj.getDouble("y"), obj.getInt("mass"), obj.getInt("hue")));
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
		    		  	enemyCells.add(new CellPlayer(game.batch, game.shapeRenderer, game.font, obj.getString("name"), obj.getDouble("x"), obj.getDouble("y"), obj.getInt("mass"), obj.getInt("hue")));
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
		    		  game.setScreen(new MainMenuScreen(game));
		    		  socket.emit("disconnect", "");
		    		  socket.disconnect();
		    		  socket.close();
		    		  dispose();
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
	public void render(float delta) {
		Gdx.gl.glClearColor(0.8f,0.8f,0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		handleInput();
		cam.update();
		
		game.batch.setProjectionMatrix(cam.combined);
		game.shapeRenderer.setProjectionMatrix(cam.combined);

		game.shapeRenderer.begin(ShapeType.Filled);
			game.shapeRenderer.setColor(1, 1, 0, 1);
			cell.rendererDraw();
			
			synchronized(enemyCells) {
				for(CellPlayer enemyCell : enemyCells)
					enemyCell.rendererDraw();
			}
			synchronized(foods) {
				for(CellElementary f : foods)
					f.rendererDraw();
			}
	    game.shapeRenderer.end();
		
		game.cameraBatch.begin();
			game.font.draw(game.cameraBatch, cam.position.toString(), 10, Gdx.graphics.getHeight() - 10);
			game.cameraBatch.end();
		
		game.batch.begin();
        	cell.batchDraw();
        	synchronized(enemyCells) {
	        	for(CellPlayer enemyCell : enemyCells)
					enemyCell.batchDraw();
        	}
        	game.batch.end();

        if(started) {
	        JSONObject target = new JSONObject();
	        target.put("x", deltaX);
	        target.put("y", deltaY );
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

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
