package me.jtjj222.biomegen.noisegenerators;

import org.bukkit.World;

public class HillsNoiseGenerator extends BiomeNoiseGenerator {

	public HillsNoiseGenerator(World world) {
		super(world);
		this.generator.setScale(1.0/64.0);
		this.generator.setYScale(1.0/72.0);
		this.magnitude = 14.0;
	}

}
