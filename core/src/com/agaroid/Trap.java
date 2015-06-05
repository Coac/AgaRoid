package com.agaroid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Etienne on 05/06/2015.
 */
public class Trap {
    private static final int mass=30;
    private static final int rect=25;

    private static final Color coCircle=new Color(0,100,0,1);

    private static final Color coRect=new Color(0,0,0,1);



    public Trap(SpriteBatch batch,
                ShapeRenderer shapeRenderer, int posX, int posY){
        super();
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;
        this.posX = posX;
        this.posY = posY;
    }
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;

    GlyphLayout glyphLayout;

    private String username;
    int posX = 200;
    int posY = 200;

    Color color;



    public  void rendererDraw() {
        shapeRenderer.setColor(coCircle);
        shapeRenderer.circle(posX, posY, mass);
        shapeRenderer.setColor(coRect);
        shapeRenderer.rect(posX-rect/2, posY-rect/2,rect,rect);
    }
}
