package com.pjc.warcaby;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.*;

public class warcaby extends ApplicationAdapter
{
	int it = 0;

	SpriteBatch batch;
	Viewport view;
	Camera camera;
	Vector2 mousePos;

	Game game;

	public void update()
	{
		mousePos.x = ((Gdx.input.getX() - view.getScreenX())/(float) view.getScreenWidth())* view.getWorldWidth();
		mousePos.y = ((Gdx.input.getY() - view.getScreenY())/(float) view.getScreenHeight())* view.getWorldHeight();

		game.update();
	}

	@Override
	public void resize(int x, int y)
	{
		view.update(x, y);
	}

	@Override
	public void create()
	{
		camera = new OrthographicCamera();
		view = new FitViewport(128, 74, camera);

		mousePos = new Vector2(view.getScreenX() - Gdx.input.getX(), view.getScreenY() -  Gdx.input.getY());

		batch = new SpriteBatch();

		game = new Game(view, camera, mousePos);

		it = 0;

		Gdx.graphics.setWindowedMode((int)(Gdx.graphics.getDisplayMode().width*0.60), (int)(Gdx.graphics.getDisplayMode().height*0.60));
	}

	@Override
	public void render()
	{
		update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		game.draw(batch);

		batch.end();
	}
	
	@Override
	public void dispose()
	{
		batch.dispose();
	}
}
