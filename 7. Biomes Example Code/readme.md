# About
This is the code for the 7th tutorial in the series of bukkit terrain generation, about biomes.

More info <insert link when written>

# How to build

I chose to use Maven to handle the projects. It is a tool that lets you manage dependencies and how your project builds across multiple systems. To build, you can either install a maven plugin for your ide (eclipse users can check out [m2eclipse](http://www.eclipse.org/m2e/), netbeans comes with it already) or run this command in the project directory:
```
mvn package
```
Then find your jar file in target/ folder

# How to help out #
I am currently working on:
+ The 7.5th tutorial, which will cover Voronoi (aka cell) noise (to make better looking biomes, and some cool looking terrain)
+ The 8th tutorial, which will cover interpolation (to fix speed issues, and make the world look a bit smoother)
+ The 9th tutorial, which will explain how .schematic files work, how Minecraft's nbt format works, and how to load schematics
+ The 9.5th tutorial, which will explain how to generate random buildings (little note here, I need to do some research on this topic)
Any help would be appreciated. If you know of a cool topic, or have something to help out with, pm me on the bukkit forums (@jtjj222)

# What's in the folders #
```
| pom.xml - This file tells maven how to build the project
| src
 \ me
  \ jtjj222
   \ biomegen
    \ BasicChunkGenerator.java    The chunk generator used to create the world
    | BiomeGenerator.java         Controls generating the temperature and rainfall values for the biomes
    | BiomeHandlers.java          Controls the biome handlers that are used in a world
    | Biomes.java                 A list of supported biomes, along with their handlers, temperatures, and rainfall values
    | noisegenerators
     \ BiomeNoiseGenerator.java   A superclass for each specific biome's handler
     | <biome>NoiseGenerator.java A class that handles generating the noise values for this biome
```
