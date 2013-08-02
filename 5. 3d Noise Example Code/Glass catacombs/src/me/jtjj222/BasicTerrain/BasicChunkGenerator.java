package me.jtjj222.BasicTerrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.*;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class BasicChunkGenerator extends ChunkGenerator {

	/**
	 * 
	 * @param x
	 * X co-ordinate of the block to be set in the array
	 * @param y
	 * Y co-ordinate of the block to be set in the array
	 * @param z
	 * Z co-ordinate of the block to be set in the array
	 * @param chunk
	 * An array containing the Block id's of all the blocks in the chunk. The first offset
	 * is the block section number. There are 16 block sections, stacked vertically, each of which
	 * 16 by 16 by 16 blocks.
	 * @param material
	 * The material to set the block to.
	 */
	void setBlock(int x, int y, int z, byte[][] chunk, Material material) {
		//if the Block section the block is in hasn't been used yet, allocate it
		if (chunk[y >> 4] == null)
			chunk[y >> 4] = new byte[16 * 16 * 16];
		if (!(y <= 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0))
			return;
		try {
			chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (byte) material
					.getId();
		} catch (Exception e) {
			// do nothing
		}
	}

	@Override
	/**
	 * @param world
	 * The world the chunk belongs to
	 * @param rand
	 * Don't use this, make a new random object using the world seed (world.getSeed())
	 * @param biome
	 * Use this to set/get the current biome
	 * @param ChunkX and ChunkZ
	 * The x and z co-ordinates of the current chunk.
	 */
	public byte[][] generateBlockSections(World world, Random rand, int ChunkX,
			int ChunkZ, BiomeGrid biome) {
		
		SimplexOctaveGenerator gen1 = new SimplexOctaveGenerator(world,8);
		gen1.setScale(1/64.0); //how "scaled" the noise generator should be.
		
		
		byte[][] chunk = new byte[world.getMaxHeight() / 16][];
		
		for (int x=0; x<16; x++) { 
			for (int z=0; z<16; z++) {
				
				int realX = x + ChunkX * 16; //used so that the noise function gives us
				int realZ = z + ChunkZ * 16; //different values each chunk

				int maxHeight = (int) (gen1.noise(realX, realZ, 0.5, 0.5) * 16 + 128);
				for (int y=64;y<maxHeight;y++) {
					double density = gen1.noise(realX,y, realZ, 0.5, 0.5);
					if (density <= 0.3 && density >= 0.2) {
						setBlock(x,y,z,chunk,Material.GLASS);
					}
					 
				}
			}
		}
		return chunk;
	}
	/**
	 * Returns a list of all of the block populators (that do "little" features)
	 * to be called after the chunk generator
	 */
    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        ArrayList<BlockPopulator> pops = new ArrayList<BlockPopulator>();
        //Add Block populators here
        return pops;
    }
}
