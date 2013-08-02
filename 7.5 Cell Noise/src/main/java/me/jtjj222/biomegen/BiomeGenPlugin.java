package me.jtjj222.biomegen;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class BiomeGenPlugin extends JavaPlugin {
    
	private static BiomeGenPlugin instance;
	
	public void onEnable() {
		instance = this;
	}
	
	public void onDisable() {}
	
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String genId) {
	     return new BasicChunkGenerator();
	}
	
	public static BiomeGenPlugin getInstance() {
		return instance;
	}
	
}
