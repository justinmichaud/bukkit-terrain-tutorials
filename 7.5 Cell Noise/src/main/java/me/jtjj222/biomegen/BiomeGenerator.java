package me.jtjj222.biomegen;

import java.util.Map;

import org.bukkit.World;

public class BiomeGenerator {
	
	private VoronoiOctaveGenerator temperatureGen, rainfallGen;
	
	public BiomeGenerator(World world) {
		// I used a scale of 1/300 instead of something like 1/600 or 1/1000
		// to make the biome edges easier to see
		temperatureGen = new VoronoiOctaveGenerator(32, true);
		temperatureGen.setScale(1.0/100.0);
		temperatureGen.setWorld(world);
		
		rainfallGen = new VoronoiOctaveGenerator(32, true);
		rainfallGen.setScale(1.0/105.0);
		rainfallGen.setSeed(world.getSeed() + 1);
	}
	
	public Map<Biomes, Double> getBiomes(int realX, int realZ) {
		return Biomes.getBiomes(Math.abs((temperatureGen.noise(realX, realZ, 0.5, 0.5, true))*50.0), Math.abs((rainfallGen.noise(realX, realZ, 0.5, 0.5, true))*50.0));
	}

}
