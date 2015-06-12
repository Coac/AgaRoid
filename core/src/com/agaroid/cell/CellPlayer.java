package com.agaroid.cell;

import com.agaroid.Cell;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class CellPlayer extends Cell {
	private BitmapFont font;

	private String username;
	private String id;

	public CellPlayer(SpriteBatch batch, ShapeRenderer shapeRenderer,
			BitmapFont font, String username, double d, double e, int mass,
			float hue) {
		super(batch, shapeRenderer, d, e, mass, hue);
		this.username = username;
		this.font = font;
		this.glyphLayout = new GlyphLayout();
		this.glyphLayout.setText(font, username);
	}

	public void batchDraw() {
		font.draw(batch, username, (float) posX - glyphLayout.width / 2,
				(float) posY + glyphLayout.height / 2);
	}

	public String getUsername() {
		return this.username;
	}

	public void setId(String i) {
		this.id = i;
	}

	public String getId() {
		return this.id;
	}

}
