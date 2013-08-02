package me.jtjj222.BasicTerrain;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class BasicTerrain extends JavaPlugin{

	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}
	
	/**
	 * 
	 * @param worldName
	 * The name of the world the generator is being applied to
	 * @param GenId
	 * The id (if any) specified by the user. It can be used if the plugin
	 * wants to have multiple generators in one plugin. More on this later.
	 * @return
	 * The ChunkGenerator that this plugin provides
	 */
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String GenId) {
		return new me.jtjj222.BasicTerrain.BasicChunkGenerator();
	}
	
}
