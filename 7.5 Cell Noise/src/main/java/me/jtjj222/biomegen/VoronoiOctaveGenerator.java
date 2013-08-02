package me.jtjj222.biomegen;

import org.bukkit.World;

/**
 * A noise generator that uses multiple scaled octaves of Voronoi noise to
 * generate fractal (small features are similar to large ones) noise
 * 
 * This is based on the bukkit octave generator, to make it easier to use
 */
public class VoronoiOctaveGenerator {
	
	private double xScale, yScale, zScale;
	private final VoronoiNoiseGenerator[] octaves;
	
	public VoronoiOctaveGenerator(int numOctaves, boolean useDistance) {
		this.octaves = new VoronoiNoiseGenerator[numOctaves];
		createOctaves(useDistance);
	}

	private void createOctaves(boolean useDistnace) {
		for (int i=0; i < octaves.length; i++) {
			octaves[i] = new VoronoiNoiseGenerator(0l, (short) 1);
			octaves[i].setUseDistance(useDistnace);
		}		
	}

	public double getXScale() {
		return xScale;
	}

	public void setXScale(double scale) {
		xScale = scale;
	}

	public double getYScale() {
		return yScale;
	}

	public void setYScale(double scale) {
		yScale = scale;
	}

	public double getZScale() {
		return zScale;
	}

	public void setZScale(double scale) {
		zScale = scale;
	}
	
	public void setScale(double scale) {
		setXScale(scale);
		setYScale(scale);
		setZScale(scale);
	}

	public void setSeed(long seed) {
		for (VoronoiNoiseGenerator gen : octaves) {
			gen.setSeed(seed);
		}
	}
	
	public void setWorld(World world) {
		setSeed(world.getSeed());
	}

	public double noise(double x, double y, double frequency, double amplitude) {
		return noise(x, y, frequency, amplitude, false);
	}

	public double noise(double x, double y, double frequency, double amplitude,
			boolean normalized) {
		double result = 0;
		double amp = 1;
		double freq = 1;
		double max = 0;
		x *= xScale;
		y *= yScale;

		for (VoronoiNoiseGenerator octave : octaves) {
			result += octave.noise(x, y, freq) * amp;
			max += amp;
			freq *= frequency;
			amp *= amplitude;
		}
		if (normalized) {
			result /= max;
		}
		
		return result;
	}

	public double noise(double x, double y, double z, double frequency,
			double amplitude) {
		return noise(x, y, z, frequency, amplitude, false);
	}

	/// To generate fractal noise, we calculate the noise of each octave at 
	/// an increasingly larger/smaller frequency and amplitude.
	public double noise(double x, double y, double z, double frequency,
			double amplitude, boolean normalized) {
		double result = 0;
		double amp = 1;
		double freq = 1;
		double max = 0;
		x *= xScale;
		y *= yScale;
		z *= zScale;

		for (VoronoiNoiseGenerator octave : octaves) {
			result += octave.noise(x, y, z, freq) * amp;
			max += amp;
			freq *= frequency;
			amp *= amplitude;
		}
		if (normalized) {
			result /= max;
		}
		
		return result;
	}
}
