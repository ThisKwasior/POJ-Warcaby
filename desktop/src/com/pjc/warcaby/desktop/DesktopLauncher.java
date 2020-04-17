package com.pjc.warcaby.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pjc.warcaby.warcaby;

public class DesktopLauncher
{
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 128;
		config.height = 74;
		config.title = "Warcaby";
		config.resizable = true;
		final LwjglApplication lwjgl = new LwjglApplication(new warcaby(), config);
	}
}
