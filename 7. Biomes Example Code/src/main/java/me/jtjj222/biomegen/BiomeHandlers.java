package me.jtjj222.biomegen;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import me.jtjj222.biomegen.noisegenerators.BiomeNoiseGenerator;

import org.bukkit.World;

/**
 * This stores the biome handlers for a world (because bukkit never
 * gives us a list of worlds using the generator, we need to
 *  create biome handles as needed)
 */
public class BiomeHandlers {

	private HashMap<Biomes, BiomeNoiseGenerator> handlers = new HashMap<Biomes, BiomeNoiseGenerator>(Biomes.values().length);
	
	// Would love to know of an architecture that doesn't involve so much reflection :P
	public BiomeHandlers(World world) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		for (Biomes biome : Biomes.values()) {
			handlers.put(biome, biome.generatorClass.getConstructor(World.class).newInstance(world));
		}
	}
	
	public BiomeNoiseGenerator getHandler(Biomes biome) {
		return handlers.get(biome);
	}
	
}
