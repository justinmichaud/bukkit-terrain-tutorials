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
	
	byte getBlock(int x, int y, int z, byte[][] chunk) {
		//if the Block section the block is in hasn't been used yet, allocate it
		if (chunk[y >> 4] == null)
			return 0; //block is air as it hasnt been allocated
		if (!(y <= 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0))
			return 0;
		try {
			return chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
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
		//where we will store our blocks
		byte[][] chunk = new byte[world.getMaxHeight() / 16][];
		
		SimplexOctaveGenerator overhangs = new SimplexOctaveGenerator(world,8);
		SimplexOctaveGenerator bottoms = new SimplexOctaveGenerator(world,8);
		
		overhangs.setScale(1/64.0); //little note: the .0 is VERY important
		bottoms.setScale(1/128.0);
		
		int overhangsMagnitude = 16; //used when we generate the noise for the tops of the overhangs
		int bottomsMagnitude = 32;
		
		for (int x=0; x<16; x++) {
			for (int z=0; z<16; z++) {
				int realX = x + ChunkX * 16;
				int realZ = z + ChunkZ * 16;
				
				int bottomHeight = (int) (bottoms.noise(realX, realZ, 0.5, 0.5) * bottomsMagnitude + 64);
				int maxHeight = (int) overhangs.noise(realX, realZ, 0.5, 0.5) * overhangsMagnitude + bottomHeight + 32;
				double threshold = 0.3;
				
				//make the terrain
				for (int y=0; y<maxHeight; y++) {
					if (y > bottomHeight) { //part where we do the overhangs
						double density = overhangs.noise(realX, y, realZ, 0.5, 0.5);
						
						if (density > threshold) setBlock(x,y,z,chunk,Material.STONE);
						
					} else {
						setBlock(x,y,z,chunk,Material.STONE);
					}
				}
				
				//turn the tops into grass
				setBlock(x,bottomHeight,z,chunk,Material.GRASS); //the top of the base hills
				setBlock(x,bottomHeight - 1,z,chunk,Material.DIRT);
				setBlock(x,bottomHeight - 2,z,chunk,Material.DIRT);
				
				for (int y=bottomHeight + 1; y>bottomHeight && y < maxHeight; y++ ) { //the overhang
					int thisblock = getBlock(x, y, z, chunk);
                    int blockabove = getBlock(x, y+1, z, chunk);
                    
                    if(thisblock != Material.AIR.getId() && blockabove == Material.AIR.getId()) {
                        setBlock(x, y, z, chunk, Material.GRASS);
                        if(getBlock(x, y-1, z, chunk) != Material.AIR.getId())
                            setBlock(x, y-1, z, chunk, Material.DIRT);
                        if(getBlock(x, y-2, z, chunk) != Material.AIR.getId())
                            setBlock(x, y-2, z, chunk, Material.DIRT);
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
