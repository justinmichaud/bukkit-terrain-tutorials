package me.jtjj222.biomegen.noisegenerators;

import org.bukkit.World;

public class SwampNoiseGenerator extends BiomeNoiseGenerator {

	public SwampNoiseGenerator(World world) {
		super(world);
	}
	
	@Override
	public double get2dNoise(double x, double z) {
		return 1;
	}
	
	@Override
	public double get3dNoise(double x, double y, double z) {
		return 0;
	}

}
